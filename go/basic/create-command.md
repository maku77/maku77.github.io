---
title: "Go でコマンドラインツールを作って GitHub で公開する"
url: "p/ow258be"
permalink: "p/ow258be"
date: "2022-04-10"
tags: ["Go"]
redirect_from:
  - /hugo/go/module
---

何をするか？
----

ここでは、Go 言語の HelloWorld として、`go-hello` というコマンドを作って、GitHub で公開できるようにしてみます。
この手順が完了すると、世界中のユーザーが次のように `go-hello` コマンドをインストールできるようになります。

```console
$ go install github.com/<User>/go-hello
```


go-hello プロジェクトの作成
----

まず、適当なディレクトリにプロジェクト用のディレクトリを作成します。

```console
$ mkdir -p ~/go-hello
$ cd ~/go-hello
```

__`go mod`__ コマンドで、プロジェクトのルートディレクトリに __`go.mod`__ ファイルを作成します。
引数として、モジュール名を指定します。
GitHub で公開するのであれば次のような感じで指定します（`maku77` の部分は自分の GitHub ユーザー ID に変更してください）。

```console
$ go mod init github.com/maku77/go-hello
```

#### 作成された go.mod の内容

```
module github.com/maku77/go-hello

go 1.18
```

Go は、`go.mod` ファイルのあるディレクトリをモジュールルートとみなします。


go-hello コマンドの実装
----

### 実装

Go 言語では実行可能なコマンドは main パッケージの `main` 関数として実装します。
ファイル名は何でもよいのですが、ここでは `main.go` として作成します。

#### go-hello/main.go

```go
package main

import "fmt"

func main() {
	fmt.Println("Hello, world!")
}
```

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

`go install` コマンドを使うと、`go-hello` 実行ファイルが __`$GOPATH/bin`__（あるいは `$GOBIN` で指定したディレクトリ）にインストールされます。
つまり、コマンドをグローバルインストールしたことになります。

```console
$ go install
$ `go env GOPATH`/bin/go-hello
Hello, world!
```

インストールしたコマンドが必要なければ最後に削除しておきます。

```console
$ rm `go env GOPATH`/bin/go-hello
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

```console
$ go install github.com/maku77/go-hello@latest
```

下記のように実行ファイルができていれば、うまくインストールできています。

```console
$ ls `go env GOPATH`/bin
go-hello
```

インストール先のディレクトリ（`$GOBIN` や `$GOPATH/bin`）にパスを通しておけば、どのディレクトリからでも簡単にコマンドを実行できるようになります。

- 参考: [go install のコマンドインストール先にパスを通す (GOBIN, GOPATH/bin)](./gobin.html)

