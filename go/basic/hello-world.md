---
title: "Go 言語で Hello World をコンパイル、実行する"
url: "p/nuz369c/"
permalink: "p/nuz369c/"
date: "2017-08-30"
tags: ["Go"]
description: "ここでは、Go 言語を使用して簡単な Hello World プログラムを作成し、コンパイル、実行してみます。"
redirect_from:
  - /hugo/go/hello-world
---

Go 言語で Hello World を実装する
----

Go 言語のソースコードファイルの拡張子は、慣例として `.go` を使用します。

#### hello.go

~~~ go
package main

import "fmt"

func main() {
	fmt.Println("Hello Go")
}
~~~

エントリポイントとなる Go プログラムは、`main` パッケージとして作成し、`main` 関数を含んでいる必要があります（`main.main` と表現します）。


Hello World を実行する
----

Go 言語はコンパイル型の言語ですが、`go run` コマンドを使用すると、ソースコードファイルを指定してそのまま実行することができます。

~~~
$ go run hello.go
Hello Go
~~~

ビルドして実行ファイル（Windows なら `hello.exe`）を作成するには、`go build` コマンドを使用します。

~~~
$ go build hello.go
$ ./hello
Hello Go
~~~

作成された実行ファイルは、同じ環境（OS、アーキテクチャ）であれば、Go の処理系がインストールされていなくてもそのまま実行することができます。
別の OS 用にビルドする場合は、クロスコンパイルの機能を使用します。
Go のクロスコンパイルがサポートしている OS と CPU アーキテクチャは、[こちらのドキュメント](https://golang.org/doc/install/source#environment)に記述されています。

