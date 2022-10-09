---
title: "構造体のコンストラクタ（ファクトリ関数）を定義する"
url: "p/6dhkoru/"
date: "2017-09-01"
tags: ["Go"]
description: "Go 言語で構造体用のコンストラクタを作成したいときは、通常の関数の形でファクトリ関数を実装します。"
aliases:
  - /hugo/go/constructor
---

Go 言語には構造体のコンストラクタ用の文法は用意されていません。
通常の関数の形で構造体のファクトリ関数を実装します。
ただ、簡単なプログラムであれば、下記のような初期化リストを使ったオブジェクト生成で間に合ってしまいます。

```go
b := Book{Title: "Title", Price: 2500}
```

任意のパラメータからオブジェクトを生成できるようにしたい場合は、__`New` で始まる名前のファクトリ関数を作成し、構造体のポインタを返す__ のが慣例となっています。

{{< code lang="go" title="book/book.go" >}}
package book

type Book struct {
	Title  string
	Author string
	Price  int
}

func NewAuthorlessBook(title string) *Book {
	return &Book{
		Title:  title,
		Author: "Unknown Author",
		Price:  100,
	}
}
{{< /code >}}

上記では、`book` パッケージの中で `Book` 構造体とそのファクトリ関数を定義してみました。
外部の `main` パッケージから使用する場合は、下記のような感じのコードになります。

{{< code lang="go" title="main.go" >}}
package main

import "fmt"
import "local.packages/book"

func main() {
	b := book.NewAuthorlessBook("The World")
	fmt.Printf("%+v\n", b)
}
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ go run main.go
&{Title:The World Author:Unknown Author Price:100}
{{< /code >}}

