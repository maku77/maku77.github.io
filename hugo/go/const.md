---
title: "定数を定義する (const, iota)"
date: "2017-08-31"
description: "Go 言語の定数は、変数を定義するのと同じ感覚で定義することができます。"
---

定数定義の基本 (const)
----

Go 言語の定数は、__`const`__ キーワードを使用して定義します。

~~~ go
func main() {
	const foo = 100
	fmt.Println(foo)
}
~~~

`const ( ... )` というキーワードを使用すれば、下記のように複数行に渡る定数定義をまとめることができます。

~~~ go
const (
	hoge = 100
	fuga = 200
)
~~~


定数には型を指定しない
----

Go 言語で定数を定義するときは、`const FOO int = 100` のように型を明示することもできますが、通常は型を指定せずに `const FOO = 100` とだけ記述します。
こうして untyped なままで定義しておくことで、実際にその定数値を使用するときに適切な型の値として扱ってくれるようになります。

例えば、定数 `FOO` を `int` 型と明示して定義すると、下記の３行目で型のミスマッチが発生してエラーとなります。

~~~ go
const FOO int = 100
var a uint = 100
a += FOO  // invalid operation: a += FOO (mismatched types uint and int)
~~~

定数の型を指定しないようにしておけば、その定数が使用されるときに適切な型の定数として扱ってくれるのでエラーが発生しません。

~~~ go
const FOO = 100
var a uint = 100
a += FOO  // OK
~~~

というわけで、定数を定義するときは、型を指定しない方が便利に（かつ直感的に）使用できます。
必ず特定の型でしか使って欲しくない場合のみ、定数の型を明示するようにしましょう。


連番の定数を定義する (iota)
----

定数ジェネレータの [iota](https://pkg.go.dev/builtin#iota) を使用すると、連番からなる定数を簡単に定義することができます。

~~~ go
const (
	FAST   = iota //=> 0
	NORMAL        //=> 1
	SLOW          //=> 2
)
~~~

`iota` は呼び出されるごとに 0、1、2 という連番を返しますが、2 つ目以降の呼び出しは上記のように省略することができます。
0 始まりではなく、1 始まりの連番が欲しい場合は、次のように最初の値を __`_`__ で無視します。

~~~ go
const (
	_      = iota //=> 0
	FAST          //=> 1
	NORMAL        //=> 2
	SLOW          //=> 3
)
~~~

あるいは、次のように記述することもできます。

~~~ go
const (
	FAST   = iota + 1  //=> 1
	NORMAL             //=> 2
	SLOW               //=> 3
)
~~~

`iota` が返す値は、次の `const` キーワードが出現したときに 0 にリセットされます。

~~~ go
const (
	a = iota  //=> 0
	b         //=> 1
	c         //=> 2
)

const (
	d = iota  //=> 0
	e         //=> 1
	f         //=> 2
)
~~~

次のようにシフトと組み合わせて使用することもできます。

~~~ go
const (
	a = 1 << iota  //=> 1
	b = 1 << iota  //=> 2
	c = 1 << iota  //=> 4
	d = 1 << iota  //=> 8
)
~~~

上記のように計算を組み合わせた定義も、下記のように省略することができます（代入式が暗黙的に繰り返されます）。

~~~ go
const (
	a = 1 << iota  //=> 1
	b              //=> 2
	c              //=> 4
	d              //=> 8
)
~~~


定数値をパッケージ外に公開する
----

パッケージ外に公開する定数値は、名前を大文字で始めます。
逆に、小文字で始まる定数値は、同じパッケージ内からのみ参照可能です。

#### $GOPATH/src/mypkg/mypkg.go

~~~ go
package mypkg

const PublicConst = 100    // パッケージ外から mypkg.PublicConst で参照可能
const privateConst = 200   // mypkg 内からのみ参照可能
~~~

#### main.go

~~~ go
package main

import "fmt"
import "mypkg"

func main() {
	fmt.Println(mypkg.PublicConst)
}
~~~

標準パッケージの `math` パッケージも、円周率πを `math.Pi` という大文字で始まる定数名で公開しています。

~~~ go
package main

import "fmt"
import "math"

func main() {
	fmt.Println(math.Pi)  // 3.141592653589793
}
~~~


参考記事
----

* [Constants - The Go Blog](https://blog.golang.org/constants)

