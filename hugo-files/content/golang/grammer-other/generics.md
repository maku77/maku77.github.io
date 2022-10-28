---
title: "Golang のジェネリクスで複数の型を扱える関数を定義する (generics)"
linkTitle: "ジェネリクスで複数の型を扱える関数を定義する (generics)"
url: "p/cp8o6m2/"
date: "2022-09-25"
tags: ["Go"]
---

Golang のジェネリクスを使用すると、複数の型を扱う関数を定義できます。
ジェネリクスは別の言語ではごく一般的な機能として提供されていますが、シンプルな言語仕様を理想としている Golang では当初提供されていませんでした。
ただ、ジェネリクスを望む声は多く、Golang ver 1.8 で導入されることになりました。

ジェネリクスを使わない場合
----

次のコードの `SumInt64` 関数と `SumFloat64` 関数は、両方とも数値スライスの要素を足し合わせる関数ですが、パラメーターの型が `int64` と `float64` で異なっているため、別々の関数として定義しています。

```go
package main

import "fmt"

// int64 スライスの全要素を足し合わせた結果を返します。
func SumInt64(vals []int64) int64 {
	var sum int64
	for _, v := range vals {
		sum += v
	}
	return sum
}

// float64 スライスの全要素を足し合わせた結果を返します。
func SumFloat64(vals []float64) float64 {
	var sum float64
	for _, v := range vals {
		sum += v
	}
	return sum
}

func main() {
	fmt.Println(SumInt64([]int64{100, 200, 300})) //=> 600
	fmt.Println(SumFloat64([]float64{0.1, 0.2, 0.3})) //=> 0.6
}
```

でも、`SumInt64` と `SumFloat64` の実装はほぼ同じなので、別の関数として実装するのは無駄な気がします。
さらに悲しいことに、次のように定義された `MyInt` 型のスライスを `SumInt64` 関数に渡そうとしてもエラーになります。

```go
type MyInt int64

fmt.Println(SumInt64([]MyInt{1, 2, 3})) // エラー（MyInt は int64 ではない）
```

そこで、ジェネリクスの出番です。


ジェネリクスの基本
----

ジェネリクス対応の関数を定義するには、関数名の後ろにブラケット (`[]`) で囲んだ __型パラメーター (type parameters)__ を記述し、その型を関数内で使用します。
次の `Sum` 関数は、`int64` と `float64` のどちらの型のスライスでも引数に指定できるようにしています。

```go
// 数値型スライスの全要素を足し合わせた結果を返します。
func Sum[T int64 | float64](vals []T) T {
	var sum T
	for _, v := range vals {
		sum += v
	}
	return sum
}
```

`[T int64 | float64]` という型パラメーターは、`int64` と `float64` のいずれかである型 `T` を定義しています。
型 `T` が実際にどの型として扱われるかは、コンパイル時に判断されます。
型 `T` は、関数のパラメータ、戻り値、本体の実装のどの部分でも使用することができます。

ジェネリクス対応した関数は、通常の関数と同じように呼び出すことができます。

```go
ints := []int64{100, 200, 300}
fmt.Println(Sum(ints))  //=> 600

floats := []float64{0.1, 0.2, 0.3}
fmt.Println(Sum(floats))  //=> 0.6
```

めでたし、めでたし！
といきたいところですが、ここで注意しないといけないのは、`[T int64 | float64]` のような型パラメータは、正確に `int64` 型と `float64` 型のどちらかしか扱えないということです。
例えば、次のように定義した `MyInt` 型を使おうとするとエラーになります。

```go
type MyInt int64
ints := []MyInt{100, 200, 300}
fmt.Println(Sum(ints)) // エラー (ノ_・、)
```

`MyInt` の本質的な型は `int64` なのに、それを `Sum` 関数で受け取れないのは悲しすぎます。
この問題に対応するために、Golang は __基礎型 (underlying type)__ という概念を導入しました。
上記の場合、`MyInt` の underlying type は `int64` です。
型パラメーターにおいて、underlying type が `int64` である型を示すためには、前にチルダを付けて、__`~int64`__ のように表現します。
次の `Sum` 関数の型パラメーター `T` は、underlying type が `int64` あるいは `float64` である型を扱うことができます。

```go
func Sum[T ~int64 | ~float64](vals []T) T {
	// ...
}

func main() {
	type MyInt int64
	ints := []MyInt{100, 200, 300}
	fmt.Println(Sum(ints)) //=> 600（MyInt スライスを渡せた！）
}
```

型パラメーターの定義するときは、利便性を考慮して underlying type で指定することが多くなると思います。


呼び出し時に型引数が必要な場合
----

前述のような型パラメーター `T` の実際の型はコンパイル時に自動的に判断（推測）されますが、呼び出し時に明示的に型を指定しないといけないケースがあります。
典型的には、引数から型パラメーターの型が推測できないケースです。
次のサンプルコードの `MakeRandomSlice` 関数は、指定したサイズのランダムなスライスを生成する関数ですが、生成すべきスライス要素の型 `T` は、`size` 引数からは推測できません。
そこで、呼び出し時に __`MakeRandomSlice[int32](3)`__ のように、どの型のスライスを生成するかを明示してやる必要があります。
このブラケットで囲んだ `[int32]` の部分を、__型引数 (type arguments)__ と呼びます。

```go
package main

import (
	"crypto/rand"
	"encoding/binary"
	"fmt"
)

// 指定した型、サイズのランダムスライスを生成します。
func MakeRandomSlice[T ~int32 | ~float32](size int) []T {
	var vals = make([]T, 0, size)
	var v T
	for i := 0; i < size; i++ {
		binary.Read(rand.Reader, binary.LittleEndian, &v)
		vals = append(vals, v)
	}
	return vals
}

func main() {
	// int32 型のランダムスライスを生成する
	ints := MakeRandomSlice[int32](3)
	fmt.Println(ints) //=> [-717388130 -1507011086 854848415]

	// float32 型のランダムスライスを生成する
	floats := MakeRandomSlice[float32](3)
	fmt.Println(floats) //=> [4.4246736e+08 7.834249e-16 1.5574595e+29]
}
```


制約インタフェース (constraint interface)
----

浮動小数点数を扱う型パラメーターは、次のような感じで定義できますが、複数の関数でこのような記述を毎回行うのは面倒です。

```go
[T ~float32 | ~float64]
```

次のようなインタフェース型を定義しておくと、型パラメーターをシンプルに記述できるようになります。
Golang のチュートリアルでは、こういったインタフェースのことを、__制約インタフェース (constraint interface)__ と呼んでいます。

```go
type Float interface {
	~float32 | ~float64
}
```

このように定義した制約インタフェース `Float` は、次のように型パラメーター部分で使用できます。

```go
func Max[T Float](a, b T) T {
	if a > b {
		return a
	}
	return b
}
```

実は、こういった基本的な型の制約インタフェースは、公式の [constraints パッケージ](https://pkg.go.dev/golang.org/x/exp/constraints) で定義されています。

```go
type Signed interface {
	~int | ~int8 | ~int16 | ~int32 | ~int64
}

type Unsigned interface {
	~uint | ~uint8 | ~uint16 | ~uint32 | ~uint64 | ~uintptr
}

type Integer interface {
	Signed | Unsigned
}

type Float interface {
	~float32 | ~float64
}

type Complex interface {
	~complex64 | ~complex128
}

type Ordered interface {
	Integer | Float | ~string
}
```

次のサンプルコードでは、`constraints` パッケージが提供する `constraints.Ordered` 制約インタフェースを使用しています。
このインタフェースは、大小比較が可能な基本型を表現しています（これには `string` 型も含まれます）。

```go
package main

import (
	"fmt"

	"golang.org/x/exp/constraints"
)

func Max[T constraints.Ordered](a, b T) T {
	if a > b {
		return a
	}
	return b
}

func main() {
	fmt.Println(Max(100, 200)) //=> 200
	fmt.Println(Max("A", "B")) //=> B
}
```


複数の型パラメーター
----

1 つの関数で複数の型パラメーターを扱いたいときは、カンマ (`,`) で区切って並べます。
次の `Sum` 関数は、数値型の値を持つマップを受け取り、その値の合計値を返します。

{{< code lang="go" hl_lines="10" >}}
package main

import "fmt"

type Number interface {
	~int64 | ~uint64 | ~float64
}

// マップの値を足し合わせます。
func Sum[K comparable, V Number](m map[K]V) V {
	var sum V
	for _, v := range m {
		sum += v
	}
	return sum
}

func main() {
	ints := map[string]int64{
		"AAA": 1,
		"BBB": 2,
		"CCC": 3,
	}
	fmt.Println(Sum(ints)) //=> 6
}
{{< /code >}}

__`comparable`__ というのは、Golang 組み込みのインタフェース型であり、このように型パラメーター部分で使うことが想定されています。
`comparable` は、その値が `==` や `!=` で比較することが可能であることを示しており、これはつまり、マップのキーとして扱える型であることを示しています。

