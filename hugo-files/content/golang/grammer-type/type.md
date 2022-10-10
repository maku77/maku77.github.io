---
title: "組込み型に独自の型名を付ける (type)"
url: "p/cuxyj8c/"
date: "2017-09-05"
lastmode: "2022-10-09"
tags: ["Go"]
description: "Go 言語で type キーワードを使用すると、組込み型に新しい名前を付けることができます。これは単なるエイリアスではなく新しい型として認識されるので、コンパイル時の型チェックの対象となり、コーディングミスを減らすことができます。"
changes:
  - 2022-10-09: 独自型のメリットとして、メソッドの追加について言及
aliases: /hugo/go/type.html
---

独自型を定義する
----

Go 言語で __`type`__ キーワードを使用すると、組込み型に新しい名前を付けることができます。
これは単なるエイリアスではなく新しい型として認識されるので、コンパイル時の型チェックの対象となり、コーディングミスを減らすことができます。
また、自身の値を操作対象とするメソッドを追加することができます。

次のような構文で、既存の組込み型をベースにして、新しい型を定義することができます。

```go
type 型名 既存の型
```

下記の例では、`string` 型と同じように使用できる `Title` 型と `Author` 型を定義しています。

```go
type Title string
type Author string
```


独自型で型安全なコードを記述する
----

独自に定義した型を関数のパラメータの型として使うことで、コンパイル時に正しい型のデータが渡されているかをチェックしてくれるようになります。

{{< code lang="go" title="独自型を使った型安全なコード" >}}
package main

import "fmt"

type Title string
type Author string

func printBook(title Title, author Author) {
	fmt.Printf("Title:'%s', Author:'%s'\n", title, author)
}

func main() {
	t := Title("Golang ABC")  // var t Title = "Golang ABC" でも OK
	a := Author("Maku")       // var a Author = "Maku" でも OK
	printBook(t, a)
}
{{< /code >}}

上記の `printBook` 関数は、パラメータとして `Title`、`Author` 型の値を受け取るように定義しているため、組み込み型の `string` 変数を渡そうとするとコンパイルエラーになります。

{{< code lang="go" title="エラーになる例" >}}
t := "Golang ABC"  // これは標準の string 型
a := "Maku"
printBook(t, a)  // Error: cannot use t (type string) as type Title
{{< /code >}}

このチェックのおかげで、`Title` と `Author` の順番を間違えて渡してしまうといったコーディングミスを防ぐことができます。
ただし、下記のように文字列リテラルを直接渡してしまうと、コンパイルエラーとしては検出してくれないので注意してください。

```go
printBook("Golang ABC", "Maku")  // これはエラーにならない
```


独自型にメソッドを追加する
----

新しい型を作成することのもうひとつの利点として、メソッドによる拡張があります。
`int` や `string` などの組み込み型にはメソッドを追加することはできませんが、独自の型には追加できます。
例えば、次の `Age` 型（中身は `int`）は `String()` メソッド（[fmt.Stringer インタフェース](https://pkg.go.dev/fmt#Stringer)）を実装し、`fmt.Println` などに渡したときの出力をカスタマイズしています。

```go
package main

import "fmt"

type Age int

func (a Age) String() string {
	return fmt.Sprintf("%d years old", a)
}

func main() {
	a := Age(14)
	fmt.Println(a) //=> 14 years old
}
```

