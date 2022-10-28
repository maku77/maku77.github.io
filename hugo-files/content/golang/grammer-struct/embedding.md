---
title: "インタフェース埋め込みと構造体埋め込みによる拡張 (Embedding)"
url: "p/tbf357g/"
date: "2017-09-12"
tags: ["Go"]
description: "Go 言語には継承の仕組みはありませんが、埋め込み (Embedding) という仕組みによって型の拡張（結合）を行うことができます。"
aliases: /hugo/go/embedding.html
---

Go 言語には継承の仕組みはありませんが、__埋め込み (Embedding)__ という仕組みによって型の拡張（結合）を行うことができます。

インタフェース埋め込み (Interface Embedding)
----

Go 言語標準の `io` パッケージには、下記のような `Reader` インタフェースと `Writer` インタフェースが定義されています。

```go
type Reader interface {
	Read(p []byte) (n int, err error)
}

type Writer interface {
	Write(p []byte) (n int, err error)
}
```

ここで、`Read` メソッドと `Write` メソッドを両方とも備えるインタフェースを単純に定義しようとすると、下記のように記述することになります。

```go
type ReadWriter interface {
	Read(p []byte) (n int, err error)
	Write(p []byte) (n int, err error)
}
```

Go 言語では、このようなケースのために __埋め込み (Embedding)__ の仕組みが提供されており、次のように既存のインタフェースを再利用する形で新しいインタフェースを定義することができます。

```go
type ReadWriter interface {
	Reader
	Writer
}
```

`ReadWriter` インタフェースを備えるオブジェクトは、`ReadWriter` 型のパラメータを取る関数だけでなく、`Reader` 型のパラメータを取る関数や、`Writer` 型のパラメータを取る関数にも渡すことができます。
ちなみに、この `ReadWriter` インタフェースも標準の `io` パッケージで定義されています。


構造体埋め込み (Struct Embedding)
----

埋め込み (Embedding) の仕組みは、インタフェースだけでなく、構造体でも同様に使用することができます。
下記の例では、`Product` 構造体を埋め込む形で `Stock` 構造体を定義しています。
`Product` 構造体は、メソッドを１つ実装しています。

```go
type Product struct {
	Model string
	Name  string
	Price int
}

func (p *Product) AdLabel() string {
	return fmt.Sprintf("%s (%s)", p.Model, p.Name)
}

type Stock struct {
	*Product
	Quantity int
}
```

`Product` 構造体に定義されているフィールドやメソッドは、`Stock` オブジェクト経由で直接参照することができます。
`Stock` オブジェクトは下記のように生成します。

```  go
s := &Stock{
	Product: &Product{Model: "PR102", Name: "Printer", Price: 300},
	Quantity: 100,
}

fmt.Println(s.Model)      //=> PR102
fmt.Println(s.AdLabel())  //=> Printer (PR102)
```

オブジェクト生成時は、埋め込んだ構造体のオブジェクトを生成して初期化しなければいけないことに注意してください（初期化すべきフィールドの名前も、埋め込んだ構造体の型名と同じになります）。
次のように、埋め込んだ構造体のフィールドを直接初期化することはできません。

{{< code lang="go" title="間違った初期化方法" >}}
s := &Stock{
	Model: "PR102",  // 初期化時に Product のフィールドは参照できない
	Name: "Printer", // 初期化時に Product のフィールドは参照できない
	Price: 300,      // 初期化時に Product のフィールドは参照できない
	Quantity: 100,
}
{{< /code >}}

