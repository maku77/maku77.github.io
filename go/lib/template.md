---
title: "テンプレート機能を使用する (text/template, html/template)"
url: "p/z8behko/"
permalink: "p/z8behko/"
date: "2017-09-10"
tags: ["Go"]
description: "Go には標準パッケージとしてテンプレート機能が用意されています。テンプレート機能は、定型の Web ページ作成などに活用できます。"
redirect_from:
  - /hugo/go/template
---


２つのテンプレートパッケージ
----

Go 言語には、組込みのテンプレート・パッケージとして、`text/template` と `html/template` パッケージが搭載されています。
Web ページの構築に使用する場合は、パラメータを HTML エスケープ処理してくれる `html/template` パッケージの方を利用します。

- [text/template Package](https://golang.org/pkg/text/template/)
- [html/template Package](https://golang.org/pkg/html/template/)


テンプレート機能の基本的な使い方
----

### Template オブジェクトの生成

テンプレート機能を使用するには、まずは __`Template`__ オブジェクトを生成します。
テンプレートファイルを使用する場合は __`template.ParseFiles`__ 関数、文字列データをテンプレートとして使用する場合は __`template.Parse`__ 関数を使用します。

```go
t, err := template.ParseFiles("./template.html")
if err != nil {
	log.Fatal(err)
}
```

テンプレートファイルのパース処理が成功することが分かっている場合は、次のように __`template.Must`__ 関数を組み合わせて使用することで、エラー処理の記述を省略することができます（エラーになった場合は panic が発生します）。

```go
t := template.Must(template.ParseFiles("./template.html"))
```

### テンプレートへの値の埋め込み

テンプレートへの値の埋め込みは、`Template` オブジェクトの __`Execute`__ メソッドによって行います。
第一引数には出力先、第二引数には埋め込むデータを渡します。

```go
data := "Hello World"
if err := t.Execute(os.Stdout, data); err != nil {
	log.Fatal(err)
}
```

渡されたデータは、テンプレートファイル内の、__`{{ "{{" }} . }}`__ という部分に展開されます。
この構文は、[Hugo](/hugo/) という静的サイトジェネレーターを使っている場合はおなじみかもしれません。

下記はシンプルなテンプレートファイルの例です。

#### template.html

```html
<h1>{{ "{{" }} . }}</h1>
```

#### 出力結果

```html
<h1>Hello World</h1>
```

### 全体のコード

#### sample.go

```go
package main

import (
	"html/template"
	"log"
	"os"
)

func main() {
	// テンプレートの読み込み
	t := template.Must(template.ParseFiles("./template.html"))

	// 値の埋め込み
	data := "Hello World"
	if err := t.Execute(os.Stdout, data); err != nil {
		log.Fatal(err)
	}
}
```


テンプレートへの入力データとしてマップや構造体を使用する
----

`Template` オブジェクトの `Execute` メソッドには、単純な文字列だけではなく、マップや構造体を渡すこともできます（こちらの方が一般的）。
下記のコードでは、３つのキーを持つマップを渡しています。

```go
data := map[string]int{
	"key1": 100,
	"key2": 200,
	"key3": 300,
}

if err := t.Execute(os.Stdout, data); err != nil {
	log.Fatal(err)
}
```

渡されたデータは、テンプレート内で `{{ "{{" }} .キー名 }}` で参照することができます。

```html
<ul>
  <li>{{ "{{" }} .key1 }}
  <li>{{ "{{" }} .key2 }}
  <li>{{ "{{" }} .key3 }}
</ul>
```

次のサンプルコードでは、構造体のオブジェクトをテンプレートに渡しています。

```go
type Book struct {
	Title  string
	Author string
}
data := Book{Title: "Golang", Author: "Maku"}

if err := t.Execute(os.Stdout, data); err != nil {
	log.Fatal(err)
}
```

テンプレートに渡された構造体の各フィルードの値も、マップの場合と同様な形式でアクセスすることができます。

```html
<ul>
  <li>{{ "{{" }} .Title }}
  <li>{{ "{{" }} .Author }}
</ul>
```

構造体のフィールドとして、構造体やマップを持っているようなケースでは、次のようにドットで連鎖させることで参照できます。

```html
<h1>{{ "{{" }} .Site.Title }}</h1>
```


テンプレート内でのループ処理
----

`range` キーワードを使用すると、テンプレートファイル内でループ処理を行うことができます。

#### template.html

```html
<ul>
  {{ "{{" }} range . }}
    <li>{{ "{{" }} . }}
  {{ "{{" }} end }}
</ul>
```

#### sample.go

```go
package main

import "html/template"
import "os"

func main() {
	t := template.Must(template.ParseFiles("./template.html"))
	data := []string{"AAA", "BBB", "CCC"}
	if err := t.Execute(os.Stdout, data); err != nil {
		log.Fatal(err)
	}
}
```

#### 出力結果

```html
<ul>
    <li>AAA
    <li>BBB
    <li>CCC
</ul>
```

Go 言語のように、インデックスと値を取得しながらループ処理することもできます。

```html
<ul>
  {{ "{{" }} range $i, $val := . }}
    <ul>{{ "{{" }} $i }} : {{ "{{" }} $val }}
  {{ "{{" }} end }}
</ul>
```

#### 出力結果

```html
<ul>
    <ul>0 : AAA
    <ul>1 : BBB
    <ul>2 : CCC
</ul>
```


テンプレートの中からテンプレートをインクードする
----

テンプレートファイルの中で、`template` 関数を使用すると、別のテンプレートファイルの内容をそこに展開することができます。

#### template.html

```html
<h1>Hello</h1>
<p>
  {{ "{{" }} template "partial.html" . }}
</p>
```

#### partial.html

```html
Hello, <b>{{ "{{" }} . }}</b>
```

入れ子構造で読み込むテンプレートファイルも、`template.ParseFiles` 関数で指定しておく必要があります。

#### sample.go

```go
package main

import "html/template"
import "os"

func main() {
	t := template.Must(template.ParseFiles("template.html", "partial.html"))
	if err := t.Execute(os.Stdout, "Maku"); err != nil {
		panic(err)
	}
}
```

#### 実行結果

```html
<h1>Hello</h1>
<p>
  Hello, <b>Maku</b>
</p>
```

その他
----

### コメント

テンプレートファイルの中で、__`{{ "{{/*" }}`__ と __`*/}}`__ で囲んだ部分はコメントと見なされ、テンプレートのコンパイル時に削除されます。

```html
{{ "{{" }}/* これはコメント */}}
```

### 前後の空白を削除

テンプレート内で値を出力するときに、__`{{ "{{-" }}`__ と __`-}}`__ で囲むようにすると、前後のスペース（前のタグからそこまでと、そこから後ろのタグまでのスペース）を削除して出力することができます。

#### template.html（置換部分の前後のスペースを削除）

```html
<p>
  {{ "{{" }}- . -}}
</p>
```

#### 出力結果

```html
<p>Hello</p>
```

前方のスペースのみ、あるいは、後方のスペースのみを削除することもできます。

#### template.html（置換部分の前のスペースだけ削除）

```html
<p>
  {{ "{{" }}- . }}
</p>
```

#### 出力結果

```html
<p>Hello
</p>
```

