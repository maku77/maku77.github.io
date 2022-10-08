---
title: "型キャストと型アサーションによる型変換"
url: "p/jruz369/"
date: "2017-09-11"
tags: ["Go"]
description: "Go 言語では暗黙的な型キャストは許されていません。明示的な型変換関数を使用するか、Type Assertion という仕組みを使用して型の変換を行う必要があります。"
aliases:
  - /hugo/go/cast
---

Go 言語では暗黙的な型キャストは許されていません。明示的な型変換関数を使用するか、Type Assertion という仕組みを使用して型の変換を行う必要があります。

型キャスト
----

Go 言語では暗黙的な型変換は許されていないため、下記のような異なる型の変数への代入はコンパイルエラーになります。

```go
var i int = 100
var f float64 = i  // cannot use i (type int) as type float64
```

このようなケースでは、__`型名(値)`__ という形で明示的な __型キャスト__ を行います。

```go
var i int = 100
var f float64 = float64(i)  // OK
```

下記は様々な型変換の例です。

{{< code lang="go" title="int8 → int32" >}}
var a uint8 = 100
var b uint32 = uint32(a)
{{< /code >}}

{{< code lang="go" title="int32 → int8" >}}
var a uint32 = 1234567890
var b uint8 = uint8(a)
fmt.Println(b)  // 210（情報が欠落する）
{{< /code >}}

{{< code lang="go" title="string → []byte" >}}
var str string = "ABC"
var bytes []byte = []byte(str)
fmt.Println(bytes)  // [65 66 67]
{{< /code >}}

{{< code lang="go" title="[]byte → string" >}}
bytes := []byte{65, 66, 67}
str := string(bytes)
fmt.Println(str)  // "ABC"
{{< /code >}}


型アサーション (Type Assertion) による型変換
----

Go 言語で任意の型の変数（型の定まっていない変数）は、空インタフェース型 (__`interface{}`__) として表現されます。
このような値を、特定の型 `T` の変数に代入するには、__型アサーション (Type Assertion)__ という仕組みを使用する必要があります。
型アサーションは、下記のような構文で実行します。

```go
var x = i.(T)
x := i.(T)  // 関数内であればこの省略形で OK
```

これにより、空インタフェース型 (`interface{}`) の変数 `i` が、型 `T` の変数 `x` に代入されます。
空インタフェース型の `i` を、型 `T` に変換できない場合は [パニック](/p/j47aswy/) が発生します。

下記の `doGreet` 関数は、パラメータで受け取った任意の型のオブジェクトに対して、`Greet` メソッドを実行します。
空インタフェースは `Greet` メソッドを備えていないので、まずは、`i.(Greeter)` という型アサーションで `Greeter` 型に変換してから `Greet` メソッドを呼び出しています。

```go
type Greeter interface {
	Greet()
}

func doGreet(i interface{}) {
	g := i.(Greeter)
	g.Greet()
}
```

上記の `doGreet` 関数は、`Greet()` メソッドを実装したオブジェクトを渡された場合にうまく動作します。
下記のサンプルでは、`Greet()` メソッドを実装した `People` 構造体のオブジェクトを、`doGreet` 関数に渡しています。

```go
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
```

逆に、`Greet()` メソッドを実装していないオブジェクトを `doGreet` 関数に渡すと、型アサーションによる型変換に失敗し、パニックが発生します。

```go
doGreet("Hoge")  // panic: interface conversion:
                 // string is not main.Greeter: missing method Greet
```

このように、実際に実行するまでパニックが発生するかどうか分からないような関数は、安心して実行することができません。
そこで、型アサーションには、型変換がうまくいったかどうかを調べる構文が用意されています（[マップ](/p/5cgjnqt/) のキーの有無を確認する構文と同じです）。

```go
x, ok := i.(T)
```

上記のように、2 つの戻り値を受け取るように型アサーションを実行すると、2 番目の戻り値 (bool) で、型変換に成功したかどうかを判別することができます。
型変換に失敗してもパニックは発生せず、`x` には型 `T` のゼロ値が格納されます。

下記の `doGreet` 関数は、渡された `interface{}` オブジェクトを `Greeter` 型に変換できるか確認し、変換できた場合のみ `Greet()` メソッドを呼び出しています。

```go
func doGreet(i interface{}) {
	if g, ok := i.(Greeter); ok {
		g.Greet()
	} else {
		fmt.Printf("Type %T is not Greeter\n", i)
	}
}

func main() {
	doGreet(&People{"Maku"})  //=> Hello, I am Maku
	doGreet(100)              //=> Type int is not Greeter
	doGreet(0.5)              //=> Type float64 is not Greeter
	doGreet("Hoge")           //=> Type string is not Greeter
}
```

ちなみに、より多くの型への型変換を試みなければならないケース (`Printf` のような関数の実装）では、[型スイッチの構文](/p/x6adgjn/) を使用するとより簡潔に記述することができます。

