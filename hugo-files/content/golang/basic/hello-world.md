---
title: "Go 言語で Hello World をコンパイル、実行する"
url: "p/nuz369c/"
date: "2017-08-30"
lastmod: "2022-08-01"
tags: ["Go"]
description: "ここでは、Go 言語を使用して簡単な Hello World プログラムを作成し、コンパイル、実行してみます。"
aliases: /hugo/go/hello-world.html
---

ここでは、Go 言語を使用して簡単な Hello World プログラムを作成し、コンパイル＆実行してみます。

Go 言語で Hello World を実装する
----

Go 言語のソースコードファイルの拡張子は、__`.go`__ を使用します。

{{< code lang="go" title="hello.go" >}}
package main

import "fmt"

func main() {
	fmt.Println("Hello Go")
}
{{< /code >}}

エントリポイントとなる Go プログラムは、__`main`__ パッケージとして作成し、__`main`__ 関数を含んでいる必要があります（`main.main` と表現します）。
ファイル名は何でも構いません。


Hello World を実行する
----

Go 言語はコンパイル型の言語ですが、__`go run`__ コマンドを使用すると、ソースコードファイルを指定してそのまま実行することができます。

```console
$ go run hello.go
Hello Go
```

ビルドして実行ファイル（Windows なら `hello.exe`）を作成するには、__`go build`__ コマンドを使用します。

```console
$ go build hello.go
$ ./hello
Hello Go
```

作成された実行ファイルは、同じ環境（OS、アーキテクチャ）であれば、Go の処理系がインストールされていなくてもそのまま実行することができます。
別の OS 用にビルドする場合は、クロスコンパイルの機能を使用します。
Go のクロスコンパイルがサポートしている OS と CPU アーキテクチャは、[こちらのドキュメント](https://golang.org/doc/install/source#environment) に記述されています。


（応用）モジュール対応モードでアプリを作成する
----

いろいろな外部パッケージを活用して Go アプリを作る場合、モジュールとしてアプリを初期化します（__module-aware mode__ と呼びます）。
モジュールを初期化するには、モジュールルートにしたいディレクトリの下で __`go mod init <モジュールパス名>`__ を実行します。
Node.js アプリの開発経験があるなら、`npm init` のようなものだと考えると分かりやすいです。

{{< code lang="console" title="Go モジュールの初期化" >}}
$ mkdir hello && hello
$ go mod init hello  # GitHub で管理するなら github.com/ojisancancode/hello など
go: creating new go.mod: module hello
{{< /code >}}

上記のように、モジュールのルートディレクトリ (`hello`) に __`go.mod`__ ファイルが作成されれば成功です。
このファイルには、このモジュールの名前や、Go のバージョン、パッケージの依存情報 (dependency tracking) などが保存されます。

{{< code title="hello/go.mod" >}}
module hello

go 1.18
{{< /code >}}

シンプルな構成のアプリでは、モジュールのルートディレクトリに、__`main`__ パッケージとする `.go` ファイルを配置します。
ファイル名は何でもいいですが、`main.go` としておきます。

{{< code lang="go" title="hello/main.go" >}}
package main

import "fmt"

func main() {
	fmt.Println("Hello Go")
}
{{< /code >}}

モジュールのディレクトリ構成は次のようになっています。

- `hello/` （モジュールルート）
  - `main.go` （main 関数を含む main パッケージの実装）
  - `go.mod`  （go mod init で自動生成された）

このアプリを実行するには、モジュールルートから次のように `go run` を実行します。
モジュール対応モードの場合、`main` パッケージのディレクトリ名 (`.`) を指定するだけでよいことに注意してください。

```console
$ go run .
Hello Go
```

ビルドして実行ファイルを作りたい場合は、`go build` を実行します。
ここでは、パッケージ名の指定すら必要ありません。
出力される実行ファイルの名前はモジュール名に従って `hello`、あるいは `hello.exe` になります（`-o` オプションで変更できます）。

```console
$ go build
$ ./hello
Hello Go
```

これで、Go 言語での簡単な Hello World アプリの導入は完了ですが、ある程度の規模のアプリを開発する場合は、自分でパッケージ用のディレクトリを作ったり、外部パッケージをインポートして実装していくことになります。
Go 言語でのパッケージの概念に関しては、下記を参考にしてください。

- [パッケージの作成とインポート](/p/t269cgj/)

