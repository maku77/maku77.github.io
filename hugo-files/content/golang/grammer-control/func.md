---
title: "Golang で関数を定義する (func)"
linkTitle: "関数を定義する (func)"
url: "p/kswy47a/"
date: "2017-09-01"
tags: ["Go"]
description: "Go 言語の関数定義はシンプルでありながら、複数の値を返すことができるなど、十分な機能を備えています。"
aliases: /hugo/go/func.html
---

Go 言語の関数定義はシンプルでありながら、複数の値を返すことができるなど、十分な機能を備えています。

関数定義の基本
----

Go 言語で関数を定義するときは、__`func`__ キーワードを使用します。

```go
func 関数名(パラメータ) 戻り値の型 {
	// ...
}
```

パラメータや、戻り値を持たない場合は、それぞれ省略することができます。
次の例は、メッセージを出力するだけの単純な関数の実装例です。

{{< code lang="go" title="パラメータも戻り値もない関数" >}}
func hello() {
	fmt.Println("Hello")
}
{{< /code >}}

次の関数は、２つの `int` 型パラメータを受け取り、足し合わせた結果を返します。

```go
func add(a, b int) int {
	return a + b
}
```


複数の戻り値を持つ関数を定義する（多値関数）
----

Go 言語の関数は、複数の戻り値を返すことができます。
その場合、戻り値の型をカンマで区切って並べ、括弧で囲みます。

```go
func swap(a, b int) (int, int) {
	return b, a
}

func main() {
	x, y := swap(10, 20)
	fmt.Println(x, y)  //=> 20, 10
}
```

複数の戻り値をひとつの変数で受け取ろうとすると、コンパイルエラーになります。

```go
x := swap(10, 20)  // Error: multiple-value swap() in single-value context
```

必要のない戻り値がある場合は、アンダースコア (__`_`__) を使って受け取ります。

```go
x, _ := swap(10, 20)  // 1 つ目の戻り値のみ欲しい場合
_, y := swap(10, 20)  // 2 つ目の戻り値のみ欲しい場合
```

ちなみに、Go 言語で変数値をスワップするときは次のように簡単に書けます。

```go
x, y = y, x
```


名前付き戻り値
----

関数の戻り値に名前をつけておくと、その名前の変数に代入した値を戻り値として返すことができます。
次の例では、２つの `int` 型の戻り値に、それぞれ `index` と `value` という名前を付けています。

```go
func findMax(arr []int) (index int, value int) { ... }
```

関数から `return` するときに、その時点で変数 `index` と変数 `value` に格納されている値が戻り値として扱われます。
それぞれの変数の初期値は、その型のゼロ値になります（例えば `int` であれば 0）。

```go
package main

import "fmt"

// 配列の中から最大値を持つ要素を検索し、そのインデックスと値を返します。
func findMax(arr []int) (index int, value int) {
	// index = 0
	// index のゼロ値は 0 なので上記の初期化処理は省略できる
	value = arr[0]
	for i := 1; i < len(arr); i++ {
		if value < arr[i] {
			value = arr[i]
			index = i
		}
	}
	return  // return index, value と同じ
}

func main() {
	arr := []int{3, 6, 100, 7, 8}
	i, v := findMax(arr)
	fmt.Println(i, v)  //=> 2, 100
}
```

上記のように、関数が同じ型の戻り値を複数返すようなケースでは、戻り値に名前を付けておくと、戻り値の順序を間違えて `return` してしまうようなミスを防ぐことができます。


エラーを返す関数を定義する
----

Go 言語には例外の仕組みがないため（ランタイムエラーを扱う `panic` は存在します）、関数内でエラーが発生した場合は、戻り値としてエラーを返すことでそれを表現します（成功時は `nil` を返します）。

```go
f, err := os.Open("/tmp/sample.txt")
if err != nil {
	log.Fatal(err)
}
// 正常シーケンス
defer f.Close()
// ...
```

自作の関数の中でエラーを返したいときは、戻り値の型を __`error`__ と定義し、エラーを __`errors.New`__ 関数で作成します。
下記の例では、フィボナッチ数列の n 番目の値を返す `fibonacci` 関数を定義しています。
パラメータに 1 より小さい値を指定された場合は、2 番目の戻り値でエラーを返すようにしています。

{{< code lang="go" hl_lines="11" >}}
package main

import (
	"errors"
	"fmt"
	"log"
)

func fibonacci(n int) (int, error) {
	if n < 1 {
		return 0, errors.New("fibonacci() must take a natural number")
	}
	x, y := 1, 1
	for ; n > 1; n-- {
		x, y = y, x+y
	}
	return x, nil
}

func main() {
	n, err := fibonacci(7)
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println(n)
}
{{< /code >}}

エラーメッセージを作成するときに、フォーマット文字列を指定したい場合は、`errors.New` 関数の代わりに __`fmt.Errorf`__ 関数を使用できます。

{{< code lang="go" hl_lines="3" >}}
func fibonacci(n int) (int, error) {
	if n < 1 {
		return 0, fmt.Errorf("fibonacci() cannot take a number %d", n)
	}
	//...
}
{{< /code >}}


可変長引数
----

関数のパラメータの定義で、__型名の前に `...` というプレフィックス__ を指定することで、可変長引数を表現することができます。

渡された引数は、関数内部ではスライスとして参照することができます。

```go
func sum(values ...int) (result int) {
	for _, v := range values {
		result += v
	}
	return
}

func main() {
	x := sum(1, 2, 3, 4, 5)
	println(x)  //=> 15
}
```

可変長引数を受け取る関数に対してスライスを渡したいときは、次のように __スライスの後ろに `...`__ を付けて展開して渡します。

```go
s := []int{1, 2, 3, 4, 5}
x := sum(s...)
```

（固定長の）配列を渡すときは、下記のように一度スライスに変換してから同じように渡すことができますが、これはもう少しよいやり方があるかも。。。

```go
arr := [...]int{1, 2, 3, 4, 5}
x := sum(arr[:]...)
```

