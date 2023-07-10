---
title: "Go でコマンドラインツールを作って GitHub で公開する"
url: "p/whs2bjt/"
date: "2022-04-10"
tags: ["Go"]
aliases: /hugo/go/module.html
---

何をするか？
----

ここでは、Go 言語の HelloWorld として、`go-hello` というコマンドを作って、GitHub で公開できるようにしてみます。
この手順が完了すると、世界中のユーザーが次のように `go-hello` コマンドをインストールできるようになります。

```console
$ go install github.com/<User>/go-hello@latest
```


go-hello プロジェクトの作成
----

まず、適当なディレクトリにプロジェクト用のディレクトリを作成します。

```console
$ mkdir -p ~/go-hello
$ cd ~/go-hello
```

__`go mod`__ コマンドで、プロジェクトのルートディレクトリに __`go.mod`__ ファイルを作成します。
引数として、モジュール名（モジュールパス）を指定します。
GitHub で公開するのであれば次のような感じで指定します（`ojisancancode` の部分は自分の GitHub ユーザー ID に変更してください）。

```console
$ go mod init github.com/ojisancancode/go-hello
```

{{< code title="作成された go.mod の内容" >}}
module github.com/ojisancancode/go-hello

go 1.18
{{< /code >}}

Go は、`go.mod` ファイルのあるディレクトリをモジュールルートとみなします。


go-hello コマンドの実装
----

### 実装

Go 言語では実行可能なコマンドは `main` パッケージの `main` 関数として実装します。
ファイル名は何でもよいのですが、ここでは `main.go` として作成します。

{{< code lang="go" title="go-hello/main.go" >}}
package main

import "fmt"

func main() {
	fmt.Println("Hello, world!")
}
{{< /code >}}

### 動作確認

この Hello World プログラムは、__`go run`__ コマンドでファイル名かディレクトリパスを指定して直接実行できます。

```console
$ go run .
Hello, world!
```

__`go build`__ コマンドを実行すれば、実行ファイル (`go-hello`) を生成することができます。
実行ファイル名は `go mod init` で指定したモジュール名にしたがって自動的につけられますが、`-o` オプションで明示することもできます。

```console
$ go build
$ ./go-hello
Hello, world!
```

`go install` コマンドを使うと、`go-hello` 実行ファイルが __`$GOPATH/bin`__（あるいは `$GOBIN` で指定したディレクトリ）にインストールされます（あらかじめ `go build` しておく必要はありません）。
[Go 用の PATH 環境変数を設定](/p/s258beh/) してあれば、どのディレクトリからでもインストールした `go-hello` コマンドを実行できるようになります。

```console
$ go install
$ go-hello
Hello, world!
```

インストールしたコマンドが不要になった場合は、次のように単純に実行ファイルを削除すれば OK です。

```console
$ rm `go env GOPATH`/bin/go-hello

# GOBIN 環境変数を設定済みであれば下記でも OK
$ rm `go env GOBIN`/go-hello
```


GitHub で go-hello モジュールを公開する
----

作成した Go モジュールを GitHub で公開します。

### リポジトリの作成

まず、モジュール名に合わせて、[GitHub にリポジトリを作成](https://github.com/new) します。
ここでは、`<User>/go-hello` という名前で作成します。
`README.md` と Go 言語用の `.gitignore` を自動生成しておくと便利です。

### コミット＆プッシュ

リポジトリを作成したら、適当な作業用ディレクトリに `git clone` します。

```console
$ cd ~/gitwork
$ git clone https://github.com/<User>/go-hello
$ cd ~/gitwork/go-hello
```

前のステップで作成した `go.mod` や `main.go` ファイルをこのディレクトリにコピーしてコミット、プッシュします。
これで、GitHub への公開は完了です。

```console
$ git add .
$ git commit -m "Add main function"
$ git push
```

### GitHub からコマンドをインストールしてみる

GitHub への Go モジュールのデプロイが完了したら、GitHub から直接 `go install` してみます。
この際、取得するコードのバージョンをサフィックスとして指定する必要があります。
これは想定外のコードを使わないようにするための安全策です。
最新コードを信用してよいのであれば、次のように __`@latest`__ を指定してインストールします。

```console
$ go install github.com/ojisancancode/go-hello@latest
```

もう少しお行儀よくやるには、[GitHub のコードにバージョンタグを付けておき](/p/y2cmv5d/)、次のようにバージョンを指定してインストールします。

```console
$ go install github.com/ojisancancode/go-hello@v1.0.0
```

下記のように実行ファイルができていれば、うまくインストールできています。

```console
$ ls `go env GOPATH`/bin
go-hello

$ go-hello
Hello, world!
```


複数のコマンドを提供する
----

前述の例では、モジュール名を `github.com/ojisancancode/go-hello` として、`go-hello` という名前のコマンド作成しました。
1 つのモジュールで複数のコマンドを提供したい場合は、コマンドごとにディレクトリを作成して、その中にそれぞれの `main` パッケージを構成します。
[典型的なディレクトリ構成](https://github.com/golang-standards/project-layout/blob/master/README.md) としては、__`cmd`__ ディレクトリを作って、その中にコマンド名と同名のディレクトリを作成します。
例えば、`go-hello` モジュール内に `hello1` コマンドと `hello2` コマンドを作成するには次のようなディレクトリ構成にします。

```
go-hello/
    +- go.mod
    +- cmd/
        +- hello1/main.go （hello1 コマンド用の main パッケージ）
        +- hello2/main.go （hello2 コマンド用の main パッケージ）
```

{{< code lang="go" title="go-hello/cmd/hello1/main.go" >}}
package main

import "fmt"

func main() {
	fmt.Println("Hello 1")
}
{{< /code >}}

{{< code lang="go" title="go-hello/cmd/hello2/main.go" >}}
package main

import "fmt"

func main() {
	fmt.Println("Hello 2")
}
{{< /code >}}

各コマンド用のディレクトリを `go run` で指定すれば実行できます。
ディレクトリ名は `./` で始まる相対パスで指定することに注意してください。

```console
$ go run ./cmd/hello1
Hello1

$ go run ./cmd/hello2
Hello2
```

`go run` の代わりに `go install` を使えば、システムにコマンドをインストールできます。

```console
$ go install ./cmd/hello1
$ go install ./cmd/hello2

# 次のようにまとめてインストールすることも可能です

$ go install ./...
```

これらのコードを GitHub にプッシュして公開すれば、世界中の人が次のように簡単にコマンドをインストールできるようになります。

```console
$ go install github.com/ojisancancode/go-hello/cmd/hello1@v1.0.0
$ go install github.com/ojisancancode/go-hello/cmd/hello2@v1.0.0

# あるいは次のようにまとめてインストール

$ go install github.com/ojisancancode/go-hello/cmd/...@v1.0.0
```

