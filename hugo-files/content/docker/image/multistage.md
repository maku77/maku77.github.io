---
title: "Docker のマルチステージビルドで軽量のアプリ実行用イメージを作成する"
url: "p/z3n4hye/"
date: "2022-03-01"
tags: ["Docker"]
aliases: "/docker/multistage-build"
---

マルチステージビルドとは？
----

ある GitHub のリポジトリに、`Dockerfile` と `src/hello.go`（Go 言語のコード）が入っているとします。

```
myapp/
  +-- Dockerfile   （アプリのビルド＆実行コンテナイメージの生成用）
  +-- src/hello.go （Hello World アプリのソースコード）
```

この `Dockerfile` で作成したいのは、Go ソースコードをビルドしてできた `hello` アプリを実行するための Docker イメージです。
つまり、この `Dockerfile` ファイルには、次のようなイメージ生成手順を記述することになります。

1. `src/hello.go` をビルドして、実行ファイル `hello` を生成する。
2. `hello` を実行するための Docker コンテナイメージを生成する。

ここで 1 つ疑問が出てきます。
最終的な Docker イメージでは `hello` アプリの実行環境さえ整っていればよいはずですが、上記の手順通りに Docker イメージを構築すると、Go 言語のビルド環境まで含まれてしまいそうです。
`hello` アプリを実行するための軽量なイメージを作るにはどうしたらよいでしょうか？
このようなケースで便利なのが、Docker の __マルチステージビルド__ です。

なお、アプリのソースコード (`src/hello.go`) には何を使ってもよいのですが、ここでは次のような簡単な Hello World コードを使うことにします。

{{< code lang="go" title="src/hello.go" >}}
package main

import "fmt"

func main() {
    fmt.Printf("Hello World\n")
}
{{< /code >}}


シングルステージビルドの場合
----

マルチステージビルドの効果を実感するために、まずはシングルステージによるビルド（従来のビルド方法）で Docker コンテナをビルドしてみます。

{{< code lang="docker" title="Dockerfile（シングルステージビルド）" >}}
FROM golang:1.17

WORKDIR /work
COPY src .
RUN go build hello.go

CMD ["./hello"]
{{< /code >}}

Go ソースコードのビルドを行うために、`golang:1.17` をベースイメージとして使用しています。
ビルド手順はシンプルで、ホスト側の `src` ディレクトリ以下の Go ソースコードをコンテナ側の `/work` にコピーして、`go build` でビルドしているだけです。

次のように実行すれば、Docker イメージ (`img-hello`) が生成されます。

```console
$ docker image build -t img-hello .
```

完成したイメージからコンテナを起動してみます。
`Dockerfile` に `CMD` 命令を記述しているので、次のようにするとデフォルトで `./hello` アプリが実行されます。
`--rm` オプションを指定しておくと、実行後にコンテナを自動で削除してくれるので、一時的にコンテナを起動したいときに便利です。

```console
$ docker container run --rm img-hello
Hello World
```

やったー！ うまく動いたー！ さっそくこの実行イメージを配布しよう！

・・・・・・

ちょっと待って！！！
作った Docker イメージ (`img-hello`) のサイズを見てみましょう。

```console
$ docker image ls
REPOSITORY  TAG     IMAGE ID      CREATED             SIZE
img-hello   latest  bcfd5668d079  About a minute ago  943MB
```

単純な Hello World アプリを実行したいだけなのに、Docker イメージのサイズが 1GB 弱もあります。
これは、ベースイメージの `golang` に Go 言語用のビルド環境がたくさん詰まっているからです。
Hello World アプリを実行するだけであれば、Go 言語のビルド環境は必要ないはずです。
そこで、マルチステージビルドの出番です。


マルチステージビルドの場合
----

マルチステージビルドを行うには、`Dockerfile` に __複数の `FROM` 命令__ を含めます。
つまり、複数のベースイメージを切り替えながらビルドを進めていきます。
最終的なイメージは、最後の `FROM` 命令で指定したベースイメージをもとに生成されます。
構造的には次のような感じになります。

```docker
FROM xxx
...

FROM yyy
...

FROM zzz
...
```

この場合、最終的に生成される Docker イメージは、ベースイメージ `zzz` を使って構築されます。
つまり、`zzz` には、アプリを実行するのに必要かつ最小限のベースイメージを指定すればよいことになります。

今回の `hello` アプリの場合は、次のような `Dockerfile` になります。
Go 言語のビルドでは、対象 OS やアーキテクチャを指定できるので、ここでは Linux の AMD64 環境をターゲットとして指定しています (`GOOS=linux GOARCH=amd64`)。
もちろん、実行環境のベースイメージはこのアーキテクチャに合わせる必要があります。

{{< code lang="docker" title="Dockerfile（マルチステージビルド）" >}}
# 1st ステージ -- Go ソースコードをビルドするためのステージ
FROM golang:1.17 AS builder
WORKDIR /work
COPY src .
RUN GOOS=linux GOARCH=amd64 go build hello.go

# 2nd ステージ -- 最終的な実行イメージを生成するためのステージ
FROM amd64/alpine:3.15
WORKDIR /bin
COPY --from=builder /work/hello .
CMD ["./hello"]
{{< /code >}}

1st ステージは、`golang:1.17` イメージを使って Go ソースコードをビルドする役割を担います。
`FROM` 命令で、次のように `AS builder` としてエイリアス名 (`builder`) を割り当てていますが、これは後段のステージで、1st ステージ内で生成したファイルを参照しやすくするためのものです。

```docker
FROM golang:1.17 AS builder
```

2nd ステージは最終的な Docker イメージを生成する役割を担うので、`FROM` 命令で軽量な Alpine Linux を指定しています。

```docker
FROM alpine:3.15
```

そして、1st ステージで生成したアプリの実行ファイル `/work/hello` を `/bin/hello` にコピーしています。

```docker
WORKDIR /bin
COPY --from=builder /work/hello .
```

ここの `--from=builder` オプションで、1st ステージのファイルを参照することを示しています。
1st ステージの `FROM` 命令でエイリアス名を付けていない場合は、`--from=0` のように番号で参照することもできますが、できるだけエイリアス名を付けておいた方がよいでしょう。

ビルド方法は、シングルステージビルドの場合と同様です。

```console
$ docker image build -t img-hello .
```

実行方法も同じです。

```console
$ docker container run --rm img-hello
Hello World
```

最後に、マルチステージビルドによって生成された Docker イメージのサイズを確認してみます。

```console
$ docker image ls
REPOSITORY  TAG     IMAGE ID      CREATED             SIZE
img-hello   latest  638578ac0bdc  About a minute ago  7.37MB
```

なんと、イメージサイズが 100 分の 1 以下になりました！
このサイズであれば気楽に配布できそうです (^-^)

