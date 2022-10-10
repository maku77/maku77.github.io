---
title: "Go 言語のインタフェースの扱いを理解する (interface)"
url: "p/aimpsvz/"
date: "2017-09-11"
tags: ["Go"]
description: "Go 言語でインタフェースを実装するときは、Java などとは異なり、implements によるインタフェースの明示を行いません。Ruby や Python と同様に、ダックタイピングが採用されています。"
aliases:
  - /hugo/go/interface
---

Go 言語で、ある型にインタフェースを実装するときは、Java などとは異なり、`implements` キーワードを使ったインタフェースの明示は行いません。
Ruby や Python と同様に、同じシグネチャのメソッドを実装した時点で、そのインタフェースを備えているとみなされます。

インタフェースを定義する (type ... interface)
----

Go 言語でインタフェースを定義するときは、構造体 (struct) の定義と同様に __`type`__ キーワードを使用します。
下記は Go 言語で定義されているインタフェースの例です。

{{< code lang="go" title="fmt.Stringer インタフェース" >}}
type Stringer interface {
	String() string
}
{{< /code >}}

{{< code lang="go" title="io.Reader インタフェース" >}}
type Reader interface {
	Read(p []byte) (n int, err error)
}
{{< /code >}}

上記の例からも想像できるように、Go 言語では、1 つのメソッドだけを持つインタフェースの名前は、__`メソッド名＋er`__ とする規約となっています。

- `String` 関数だけを持つ → `Stringer` インタフェース
- `Read` 関数だけを持つ → `Reader` インタフェース
- `Write` 関数だけを持つ → `Writer` インタフェース
- `Format` 関数だけを持つ → `Formatter` インタフェース


インタフェースを使用する
----

インタフェースを引数として受け取る関数を定義すると、そのインタフェースが定義するメソッドを実装しているオブジェクトだけを渡せるようになります。

```go
func PrintSomething(s fmt.Stringer) {
	// String() メソッドを呼び出せることが保証されている
	fmt.Println(s.String())
}
```

Java などの言語では、あるクラスがあるインタフェースを実装していることを示すために `implements` キーワードを使用しますが、Go 言語では明示的にインタフェース名を指定して実装することはありません。
ただ単純に、インタフェースによって示されているメソッドを実装するだけで、その型はそのインタフェースを備えている（実装している）とみなされます。
このような思想は Ruby や Python でも採用されており、通称 __ダックタイピング__ と言われているものです（アヒルのように歩いて鳴けば、それはアヒルであるという考え方）。
例えば、下記の `Book` 構造体は、`String()` メソッドを実装しているため、Go コンパイラは `fmt.Stringer` インタフェースを備えているとみなします。

```go
type Book struct {
	Title  string
	Author string
}

func (this *Book) String() string {
	return fmt.Sprintf("%s : %s", this.Title, this.Author)
}
```

よって、この `Book` 構造体のインスタンスは、先に示した `PrintSomething` 関数に渡せることになります。

```go
b := &Book{Title: "Golang", Author: "Maku"}
PrintSomething(b)  //=> "Golang : Maku"
```

また、`Book` は `fmt.Stringer` インタフェースを備えているので、`fmt.Stringer` 型の変数に代入することができます。

```go
var s fmt.Stringer = &Book{Title: "Golang", Author: "Maku"}
println(s.String())
```


すべてのオブジェクトを示す空っぽインタフェース
----

ここで、次のようにメソッドを１つも持たない空っぽのインタフェース (empty interface) を考えてみます。

```go
interface{}
```

Go 言語では、インタフェースが示すメソッドを実装していれば、そのインタフェースを備えていると判断されます。
つまり、上記のような空インタフェースは、すべての型が備えているインタフェースということになります（Java における `Object` クラスに近い）。
関数のパラメータとして、__`inteface{}`__ 型を受け取るように実装されている場合は、__任意の型のオブジェクト__ を渡すことができるということを示しています。
例えば、`fmt.Println` 関数は、任意の型の引数を複数受け取るように実装されています。

```go
func Println(a ...interface{}) (n int, err error)
```

また、空インタフェース型の変数を定義すると、その変数にはどのような型の値でも代入できるようになります。

```go
var i interface{}  // 空インタフェース型の変数を定義
```

空インタフェースのゼロ値は `nil` で、型も `nil` となります。
下記のサンプルコードは、空インタフェースに色々な型の値を代入できること、代入される値によって変数の型が変化することを示しています。

```go
package main

import "fmt"

func printTypeAndValue(i interface{}) {
	fmt.Printf("%v (type:%T)\n", i, i)
}

func main() {
	var i interface{}
	printTypeAndValue(i)  // <nil> (type:<nil>)

	i = 100
	printTypeAndValue(i)  // 100 (type:int)

	i = "Hello"
	printTypeAndValue(i)  // Hello (type:string)
}
```

空インタフェースをパラメータとして受け取る関数の中で、[型スイッチ (Type Switch)](/p/x6adgjn) の仕組みを使用すれば、実際に渡された値の型によって処理を分岐することができます。

