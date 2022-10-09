---
title: "Golang の構造体にタグ情報を追加する (struct tags)"
linkTitle: "構造体にタグ情報を追加する (struct tags)"
url: "p/hxhzfbs/"
date: "2022-09-03"
tags: ["Go"]
---

構造体タグ (struct tags) の基本
----

Go 言語の構造体 (struct) では、各フィールドの末尾に、__タグ (struct tags)__ と呼ばれるメタ情報を付加することができます。
JSON データを扱うプログラムの構造体で、`` `json:"id"` `` のような記述があるのを見かけたことがあるかもしれません。
下記の例は、タグを追加した `Book` 構造体の例です。

```go
type Book struct {
	Title string `label:"タイトル"`
	Price int    `label:"価格"`
}
```

各タグは、__`タグ名:"タグ値"`__ というフォーマットの文字列リテラルで設定します。
__コロン (`:`) の前後にスペースを入れてはいけません__。
[Golang の言語仕様 (Struct types)](https://go.dev/ref/spec#Struct_types) では、任意の文字列リテラルを配置できると記述されていますが、VS Code などの Lint 系プラグインでは、上記のようなフォーマットで記述していないと警告が出たりするので、このフォーマットで記述しておくのが無難です。
通常はダブルクォート (`"`) のエスケープをしなくて済むように、バッククォート (`` ` ``) で囲んだ [Raw string literals（生文字列リテラル）](https://go.dev/ref/spec#String_literals) を使います。


構造体タグを参照する
----

構造体の各フィールドに付加したタグを参照するには、`reflect` パッケージを使ったリフレクションを利用します。
次の例では、各フィールドの `label` タグの値を出力しています。

{{< code lang="go" title="main.go" >}}
package main

import (
	"fmt"
	"reflect"
)

type Book struct {
	Title string `label:"タイトル"`
	Price int    `label:"価格"`
}

func main() {
	book := Book{Title: "Golang入門", Price: 2000}

	// リフレクションで構造体の各フィールドをループ処理
	t := reflect.TypeOf(book)
	for i := 0; i < t.NumField(); i++ {
		fieldName := t.Field(i).Name
		tag := t.Field(i).Tag
		fmt.Printf("%s フィールドの label タグの値は「%s」だよ\n",
			fieldName, tag.Get("label"))
	}
}
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ go run main.go
Title フィールドの label タグの値は「タイトル」だよ
Price フィールドの label タグの値は「価格」だよ
{{< /code >}}

上記の例では、タグ値の取得に __`tag.Get`__ メソッドを使用していますが、設定されていないタグ名を指定すると、戻り値は空文字列 (`""`) になります。
`tag.Get` の代わりに __`tag.Lookup`__ メソッドを使用すると、タグの有無を判別することができます。
タグ値として空文字列を設定することに意味を持たせたい場合はこちらを使用します。

```go
if label, ok := tag.Lookup("label"); ok {
	fmt.Printf("%s フィールドの label タグの値は「%s」だよ\n", fieldName, label)
} else {
	fmt.Printf("%s フィールドに label タグはないよ\n", fieldName)
}
```


複数のタグ情報を設定する
----

1 つのフィールドに複数のタグ情報を設定したいときは、`タグ名:"値"` をスペースで区切って並べます。

```go
type Book struct {
	Title string `json:"title" label:"タイトル"`
	Price int    `json:"price" label:"価格"`
}
```

各タグの値を参照するには、前述の例のように `tag.Get` あるいは `tag.Lookup` メソッドを使用します。

```go
jsonTag := reflect.TypeOf(book).Field(0).Tag.Get("json")
labelTag := reflect.TypeOf(book).Field(0).Tag.Get("label")
```


タグ値の解釈方法は自由
----

タグ値は文字列形式の値ですが、この文字列をどのように解釈するかは自由です。
例えば、Golang の `encoding/json` 標準パッケージは、`json` タグに指定した文字列を JSON のプロパティ名として使用しますが、カンマで区切ってオプション設定を行えるようになっています。

```golang
type Book struct {
	Title  string `json:"title"`           // ベーシックな使い方
	Price  int    `json:"price,omitempty"` // ゼロ値の場合はこのフィールドを出力しない
	Author string `json:"-"`               // このフィールドは出力しない
}
```

- 参考: [Golang で JSON 形式の文字列やファイルを扱う](/p/dsbs9p5/)

