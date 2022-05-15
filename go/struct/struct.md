---
title: "構造体を定義する (struct)"
url: "p/8z2o63r/"
permalink: "p/8z2o63r/"
date: "2017-09-05"
tags: ["Go"]
descrition: "Go 言語の構造体は、struct というキーワードを使って定義します。"
redirect_from:
  - /hugo/go/struct
---

構造体の基本
----

構造体は、複数のデータをまとめて扱うためのデータ構造です。
下記の例では、本のタイトル情報と著者、値段をフィールドとして持つ `Book` という構造体を定義しています。

```go
type Book struct {
	Title   string
	Authors []string
	Price   int
}
```

[組込み型に別名を付ける](/p/cuxyj8c) ときには `type Age int` のように記述しますが、この後ろの `int` の部分が `struct {...}` という記述に置き換わったと考えればよいでしょう。

定義した構造体は下記のように使用することができます。

```go
// Book 構造体を定義する
type Book struct {
	Title   string
	Authors []string
	Price   int
}

func main() {
	// Book オブジェクトを生成する
	b := Book{
		Title:   "Golang ABC",
		Authors: []string{"Maku", "Moja"},
		Price:   2500,
	}

	// 各フィールドの値を参照する
	fmt.Println(b.Title)    // Golang ABC
	fmt.Println(b.Authors)  // [Maku Moja]
	fmt.Println(b.Price)    // 2500
}
```

この例では、各フィールドの __名前を大文字で始めている__（例えば `Title`）ので、そのフィールドは別のパッケージからも参照可能なフィールドとして公開されます。
逆に小文字で始める（例えば `title`）ように定義すると、そのフィールドは同じパッケージ内のコードからのみ参照できるようになります（≒ 同じディレクトリ内の .go ファイルであれば参照できる）。

ちなみに、オブジェクトを生成するときに、下記のようにフィールド名を省略して初期値を設定することもできます。
その場合は、構造体定義時と同じ順序で、すべてのフィールドの初期値を指定する必要があります。

```go
b := Book{"Golang ABC", []string{"Maku", "Moja"}, 2500}
```

フィールド名を指定する方法であれば、任意のフィールドの初期値を省略してオブジェクトを生成することができます。
省略したフィールドの値は、その型の [ゼロ値](/p/5dhkoru/) となります。

```go
// すべてのフィールドを省略
b1 := Book{}  //=> "", [], 0

// Title フィールドだけ初期値を指定
b2 := Book{Title: "Golang"}  //=> "Golang", [], 0
```


構造体へのポインタ
----

関数に渡された構造体データのフィールドを書き換えたいときは、ポインタ型で渡す必要があります（そうしないとコピーが渡されてしまいます）。
下記サンプルの、`raisePrice` 関数は、渡された `Book` オブジェクトの `Price` フィールドの値を２倍に書き換えます。

```go
func raisePrice(b *Book) {
	b.Price *= 2
}

func main() {
	b := Book{
		Title: "Golang ABC",
		Price: 2500,
	}
	raisePrice(&b)
	fmt.Println(b.Price)  //=> 5000（2倍になってる）
}
```

C/C++ の文法とは異なり、ポインタ経由のフィールドアクセスにもドット (`.`) を使用していることに注目してください。

```
C/C++ の場合:  b->Price
   Go の場合:  b.Price
```

Go 言語では、ポインタ経由のアクセスも同じ記法を使えるようにすることで、コードをシンプルに保てるようにしています。
この性質を利用すると、上記の `main` 関数は下記のように書き直すことができます。
変数 `b` を最初からポインタ型として定義してしまうことで、それ以降のコードで `&b` のような形でアドレス取得する手間を省くことができます（ここでは一か所だけですけどね＾＾）。

```go
func main() {
	b := &Book{
		Title: "Golang ABC",
		Price: 2500,
	}
	raisePrice(b)
	fmt.Println(b.Price)
}
```

ここでのサンプルコードでは、`raisePrice` 関数のパラメータとして `Book` ポインタを受け取るように実装しましたが、オブジェクト指向風に実装するのであれば、[メソッドの形で実装](/p/4behkor/) してしまえばシンプルになります。


new 関数による初期化
----

すべてのフィールドをゼロ値で初期化したオブジェクトを生成するときは、`new(型)` という組込み関数を使って C++ 風の書き方をすることもできます。
`new` 関数の戻り値は、指定した型のポインタとなります。

#### 空のオブジェクトを生成する方法いろいろ

```go
var b *Book = new(Book)
var b *Book = &Book{}
b := new(Book)
b := &Book{}
```

`&Book{}` の形式を使うとタイプ数を削減できますが、`new(Book)` の方がわかりやすいかもしれません。

