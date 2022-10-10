---
title: "メソッドを定義する（レシーバ付き関数）"
url: "p/4behkor/"
date: "2017-09-05"
tags: ["Go"]
description: "Go 言語のメソッドの定義方法は、Java や C++ とは異なり、関数にレシーバを指定するという文法を使用します。"
aliases: /hugo/go/method.html
---

Go 言語にはクラスは存在しませんが、構造体型にメソッドを追加するという方法で同様のことを実現することができます。
メソッドは次のような文法で定義します。

```go
func (レシーバ) 関数名(パラメータ) 戻り値 {
    ...
}
```

通常の関数定義の関数名の前に、`(レシーバ)` という部分が追加されただけです。
例えば、下記の `raisePrice` メソッドは、レシーバとして `Book` オブジェクトを受け取り、そのフィールド値を書き換えます。
パラメータと戻り値はありません。

```go
func (b *Book) raisePrice() {
	b.Price *= 2
}
```

フィールド値を書き換える場合は、このようにレシーバをポインタ型で指定する必要があります。
このメソッドを呼び出すには、次のようにレシーバとして渡すオブジェクトにドットを付けて呼び出します。

```go
b.raisePrice()  // raisePrice メソッドの呼び出し
```

下記は、完全に動作するサンプルコードです。

```go
package main

// Book 構造体を定義
type Book struct {
	Title string
	Price int
}

// Book 構造体にメソッドを追加
func (b *Book) raisePrice() {
	b.Price *= 2
}

func main() {
	b := Book{Title: "Golang", Price: 2500}
	println(b.Price) // 2500
	b.raisePrice()
	println(b.Price) // 5000
}
```

