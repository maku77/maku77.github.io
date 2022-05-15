---
title: "型キャストと型アサーションによる型変換"
url: "p/jruz369/"
permalink: "p/jruz369/"
date: "2017-09-11"
tags: ["Go"]
description: "Go 言語では暗黙的な型キャストは許されていません。明示的な型変換関数を使用するか、Type Assertion という仕組みを使用して型の変換を行う必要があります。"
redirect_from:
  - /hugo/go/cast
---

型キャスト
----

Go 言語では暗黙的な型変換は許されていないため、下記のような異なる型の変数への代入はコンパイルエラーになります。

~~~ go
var i int = 100
var f float64 = i  // cannot use i (type int) as type float64
~~~

このようなケースでは、下記のように明示的な型キャストを行います。

~~~ go
var i int = 100
var f float64 = float64(i)  // OK
~~~

下記は様々な型変換の例です。

#### int8 → int32

~~~ go
var a uint8 = 100
var b uint32 = uint32(a)
~~~

#### int32 → int8

~~~ go
var a uint32 = 1234567890
var b uint8 = uint8(a)
fmt.Println(b)  // 210（情報が欠落する）
~~~

#### string → []byte

~~~ go
var s string = "ABC"
var arr []byte = []byte(s)
fmt.Println(arr)  // [65 66 67]
~~~

#### []byte → string

~~~ go
arr := []byte{65, 66, 67}
s := string(arr)
fmt.Println(s)  // "ABC"
~~~


型アサーション (Type Assertion) による型変換
----

空インタフェース型 (`interface{}`) の変数（型の定まっていない変数）を、特定の型 `T` の変数に代入するには、**型アサーション (Type Assertion)** という仕組みを使用する必要があります。
型アサーションは、下記のような構文で実行します。

~~~ go
x := i.(T)
~~~

あるいは、

~~~ go
var x T = i.(T)
~~~

これにより、空インタフェース型 (`interface{}`) の変数 `i` が、型 `T` の変数 `x` に代入されます。
空インタフェース型の `i` を、型 `T` に変換できない場合は[パニック](/p/j47aswy)が発生します。

下記の `doGreet` 関数は、パラメータで受け取った任意の型 (`interface{}`) のオブジェクトに対して、`Greet` メソッドを実行します。
空インタフェースは `Greet` メソッドを備えていないので、まずは、`i.(Greeter)` という型アサーションで `Greeter` 型に変換してから `Greet` メソッドを呼び出しています。

~~~ go
type Greeter interface {
	Greet()
}

func doGreet(i interface{}) {
	g := i.(Greeter)
	g.Greet()
}
~~~

上記の `doGreet` 関数は、`Greet()` メソッドを実装したオブジェクトを渡された場合にうまく動作します。
下記のサンプルでは、`Greet()` メソッドを実装した `People` 構造体のオブジェクトを、`doGreet` 関数に渡しています。

~~~ go
type People struct {
	Name string
}

func (this *People) Greet() {
	fmt.Printf("Hello, I am %s\n", this.Name)
}

func main() {
	p := &People{"Maku"}
	doGreet(p)  //=> "Hello, I am Maku"
}
~~~

逆に、`Greet()` メソッドを実装していないオブジェクトを `doGreet` 関数に渡すと、型アサーションによる型変換に失敗し、パニックが発生します。

~~~ go
doGreet("Hoge")  // panic: interface conversion: string is not main.Greeter: missing method Greet
~~~

このように、実際に実行するまでパニックが発生するかどうか分からないような関数は、安心して実行することができません。
そこで、型アサーションには、型変換がうまくいったかどうかを調べる構文が用意されています。

~~~ go
x, ok := i.(T)
~~~

上記のように、2 つの戻り値を受け取るように型アサーションを実行すると、2 番目の戻り値 (bool) で、型変換に成功したかどうかを判別することができます。
型変換に失敗してもパニックは発生せず、`x` には型 `T` のゼロ値が格納されます。

下記の `doGreet` 関数は、渡された `interface{}` オブジェクトを `Greeter` 型に変換できるか確認し、変換できた場合のみ `Greet()` メソッドを呼び出しています。

~~~ go
func doGreet(i interface{}) {
	if g, ok := i.(Greeter); ok {
		g.Greet()
	} else {
		fmt.Printf("%T is not Greeter\n", i)
	}
}

func main() {
	doGreet(&People{"Maku"})  //=> Hello, I am Maku
	doGreet(100)              //=> int is not Greeter
	doGreet(0.5)              //=> float64 is not Greeter
	doGreet("Hoge")           //=> string is not Greeter
}
~~~

ちなみに、より多くの型への型変換を試みなければならないケース (`Printf` のような関数の実装）では、[型スイッチの構文](/p/x6adgjn) を使用するとより簡潔に記述することができます。

