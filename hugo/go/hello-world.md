---
title: Go 言語で Hello World をコンパイル、実行する
created: 2017-08-30
description: "ここでは、Go 言語を使用して簡単な Hello World プログラムを作成し、コンパイル、実行してみます。"
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

