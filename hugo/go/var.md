---
title: "変数を定義する (var)／ゼロ値について"
date: "2017-09-01"
description: "Go 言語では明示的に型を指定した変数定義と、コンパイラによる型推論を利用した変数定義を行うことができます。"
---

var キーワードを使用した変数定義の基本
----

Go 言語では、__`var`__ キーワードを使用して、`var 変数名 型名` というフォーマットで変数定義します。
Pascal や Ada のように、型名を後ろに指定することに注意してください。

~~~ go
var x int
~~~

同じ型の変数を複数定義するときは、下記のように型名を一度だけ記述するだけで済みます（逆に、`var x int, y int, z int` のように冗長に記述することはできません）。

~~~ go
var x, y, z int
~~~

括弧を使用して複数行に分けて定義することもできます。

~~~ go
var (
	name string
	age  int
)
~~~


変数の初期値を指定する
----

__`=`__ キーワードを使用して、変数の初期値を指定することができます。

~~~ go
var x int = 100
var a, b int = 100, 200
var (
	name string = "Rei Ayanami"
	age  int    = 14
)
~~~

Go には型推論の仕組みが備わっているため、変数の初期値を指定する場合は、型の記述を省略することができます。

~~~ go
var x = 100
var a, b = 100, 200
var (
	name = "Rei Ayanami"
	age  = 14
)
~~~

関数の中で変数を定義するときは、__`:=`__ を使用することで `var` と型の記述を両方とも省略することができます。

~~~ go
func hello() {
	s := "yatta-ne"
	x, y := 100, 200
	// ...
}
~~~

ちなみに、変数の型は "fmt.Printf" 関数のフォーマット文字列で `%T` を指定して確認することができます。

~~~ go
func main() {
	x := 100
	fmt.Printf("%T\n", x)  //=> int
}
~~~


変数のグルーピング
----

`var ( ... )` というシンタックスは、変数のグルーピングのために利用することもできます。
下記は、本家の [Effective Go (Commentary)](https://golang.org/doc/effective_go.html#commentary) からの抜粋ですが、エラー情報を表す変数を `var ( ... )` でグループ化して定義し、グループに対してコメントを付加しています。


~~~ go
// Error codes returned by failures to parse an expression.
var (
    ErrInternal      = errors.New("regexp: internal error")
    ErrUnmatchedLpar = errors.New("regexp: unmatched '('")
    ErrUnmatchedRpar = errors.New("regexp: unmatched ')'")
    ...
)
~~~


ゼロ値
----

変数の定義時に初期値を指定しなかった場合、それぞれの型のゼロ値 (Zero Value) が初期値として設定されます。

| 型 | ゼロ値 |
| ---- | ---- |
| 整数 | `0` |
| 浮動小数点数 | `0.0` |
| 文字列 (string) | `""`（空文字列） |
| 真偽値 (bool) | `false` |
| 構造体 (struct) | 各フィールドがゼロ値の構造体 |
| 配列 | 各要素がゼロ値の配列 |
| その他（ポインタ、スライス、マップ、関数、インタフェース、チャネル） | `nil` |

例えば、`var i int` とだけ記述した場合、`i` の初期値は 0 になります。

~~~ go
func main() {
	var i int
	fmt.Println(i);  //=> 0
}
~~~

