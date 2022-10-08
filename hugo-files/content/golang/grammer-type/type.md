---
title: "組込み型に独自の型名を付ける (type)"
url: "p/cuxyj8c/"
date: "2017-09-05"
tags: ["Go"]
description: "Go 言語で type キーワードを使用すると、組込み型に新しい名前を付けることができます。これは単なるエイリアスではなく新しい型として認識されるので、コンパイル時の型チェックの対象となり、コーディングミスを減らすことができます。"
aliases:
  - /hugo/go/type
---

Go 言語で __`type`__ キーワードを使用すると、組込み型に新しい名前を付けることができます。
これは単なるエイリアスではなく新しい型として認識されるので、コンパイル時の型チェックの対象となり、コーディングミスを減らすことができます。
次のような構文で、既存の組込み型をベースにして、新しい型を定義することができます。

```go
type 型名 既存の型
```

下記の例では、`string` 型と同じように使用できる `Title` 型と `Author` 型を定義しています。

```go
type Title string
type Author string
```

関数のパラメータ型として、これらの型を指定しておくと、コンパイル時に正しい型のデータが渡されているかをチェックしてくれるようになります。

```go
package main

import "fmt"

type Title string
type Author string

func printBook(title Title, author Author) {
	fmt.Printf("Title:'%s', Author:'%s'\n", title, author)
}

func main() {
	var t Title = "Golang ABC"
	var a Author = "Maku"
	printBook(t, a)
}
```

上記の `printBook` 関数は、パラメータとして `Title`、`Author` 型の値を受け取るように定義しているため、組み込み型の `string` 変数を渡そうとするとコンパイルエラーになります。

```go
t := "Golang ABC"
a := "Maku"
printBook(t, a)  // Error: cannot use t (type string) as type Title
```

このチェックのおかげで、Title と Author の順番を間違えて渡してしまうといったコーディングミスを防ぐことができます。
ただし、下記のように文字列リテラルを直接渡してしまうと、コンパイルエラーとしては検出してくれないので注意してください。

```go
printBook("Golang ABC", "Maku")  // これはエラーにならない
```

