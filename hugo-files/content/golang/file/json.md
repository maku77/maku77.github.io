---
title: "Golang で JSON 形式の文字列やファイルを扱う (encoding/json)"
linkTitle: "JSON 形式の文字列やファイルを扱う (encoding/json)"
url: "p/dsbs9p5/"
date: "2022-09-04"
tags: ["Golang", "JSON"]
---

{{% private %}}
- [The Go Blog - JSON and Go](https://go.dev/blog/json)
{{% /private %}}

Golang 標準パッケージの [encoding/json](https://pkg.go.dev/encoding/json) を使用すると、Golang のオブジェクトと JSON テキストを相互に変換することができます。

```go
import "encoding/json"
```

Go と JSON の型を変換するとき、次のように対応付けられます（Channel など、一部扱えないデータはあります）。

| Go の型 | JSON の型 |
| ---- | ---- |
| `bool` | boolean |
| `float64` | number |
| `string` | string |
| `[]interface{}` | 配列 |
| `map[string]interface{}` | オブジェクト |
| `nil` | null |

デフォルトでは、Go の構造体のフィールド名がそのまま JSON のプロパティ名になりますが、構造体フィールドのタグで [JSON のプロパティ名を指定する](#struct-tag) ことができます。
構造体のフィールドがポインタ型である場合は、ポインタが指す実際の値を使って JSON データに変換してくれます。


Go オブジェクト → JSON 文字列
----

__`json.Marshal`__ 関数を使用すると、任意の Go オブジェクトを JSON 形式の文字列データに変換できます。
変換後のデータは、具体的には UTF-8 エンコーディング形式のバイト配列 (`[]byte`) です。

```go
func json.Marshal(v any) ([]byte, error)
```

次の例では、`Person` 構造体のデータを JSON 文字列に変換しています。

```go
type Person struct {
	Name string
	Age  int
}

func main() {
	p := Person{"まく", 14}
	bytes, err := json.Marshal(p)
	if err != nil {
		log.Fatal(err)
	}

	fmt.Println(string(bytes)) //=> `{"Name":"まく","Age":14}`
}
```

改行やインデントを含む整形された JSON テキストが欲しい場合は、`json.Marshal` の代わりに __`json.MarshalIndent`__ 関数を使用します。

```go
bytes, err := json.MarshalIndent(p, "", "  ")
```


JSON 文字列 → Go オブジェクト
----

JSON 形式の文字列データ (`[]byte`) を Go のオブジェクトに変換するには、__`json.Unmarshal`__ 関数を使用します。

```go
func json.Unmarshal(data []byte, v any) error
```

次の例では、JSON 文字列を `Person` 構造体のデータに変換しています。

```go
type Person struct {
	Name string
	Age  int
}

func main() {
	jsonBytes := []byte(`{"Name":"まく","Age":14}`)
	var p Person
	if err := json.Unmarshal(jsonBytes, &p); err != nil {
		log.Fatal(err)
	}

	fmt.Printf("%+v\n", p) //=> {Name:まく Age:14}
	fmt.Println(p.Name)    //=> まく
	fmt.Println(p.Age)     //=> 14
}
```


Go オブジェクト → JSON ファイル
----

Go の構造体を JSON ファイルに出力するには、`os.File` オブジェクトを __`json.NewEncoder`__ に渡して `Encoder` を作成し、__`(*json.Encoder).Encode`__ メソッドでデータを書き込みます。

```go
func json.NewEncoder(w io.Writer) *json.Encoder
func (*json.Encoder).Encode(v any) error
```

次の例では、`Person` 構造体の内容を `person.json` ファイルに出力しています。

{{< code lang="go" title="main.go" >}}
package main

import (
	"encoding/json"
	"log"
	"os"
)

type Person struct {
	Name string
	Age  int
}

func main() {
	// 書き込むデータ
	p := Person{"まく", 14}

	// JSON ファイルを新規作成（既に存在する場合は上書き）
	file, err := os.Create("person.json")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	// JSON ファイルに書き込み
	encoder := json.NewEncoder(file)
	if err := encoder.Encode(p); err != nil {
		log.Fatal(err)
	}
}
{{< /code >}}

{{< code lang="json" title="出力結果 (person.json)" >}}
{"Name":"まく","Age":14}
{{< /code >}}

改行やインデントを含む整形された JSON ファイルとして出力したい場合は、`Encode` メソッドで出力する前に、__`SetIndent`__ メソッドでインデントの設定を行います。

```go
encoder := json.NewEncoder(file)
encoder.SetIndent("", "  ")  // スペース 2 文字をインデントに使う
```

{{< code lang="json" title="出力結果 (person.json)" >}}
{
  "Name": "まく",
  "Age": 14
}
{{< /code >}}

- 参考: [Golang でファイルを読み書きする (os, io)](/p/6eimpsv/)


JSON ファイル → Go オブジェクト
----

JSON ファイルを読み込んで、Go のオブジェクトに変換するには、`os.File` オブジェクトを __`json.NewDecoder`__ に渡して `Decoder` を作成し、__`(*json.Decoder).Decode`__ メソッドで Go の変数へデータを展開します。

```go
func json.NewDecoder(r io.Reader) *json.Decoder
func (*json.Decoder).Decode(v any) error
```

### JSON のスキーマが完全に分かっている場合

次の例では、`person.json` ファイルの内容を `Person` 構造体に変換しています。

{{< code lang="json" title="person.json（入力ファイル）" >}}
{
  "Name": "まく",
  "Age": 14
}
{{< /code >}}

{{< code lang="go" title="main.go" >}}
package main

import (
	"encoding/json"
	"log"
	"os"
)

type Person struct {
	Name string
	Age  int
}

func main() {
	// JSON ファイルを読み出し用にオープン
	file, err := os.Open("person.json")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	// JSON ファイルの内容を Person 構造体データとして読み出す
	var p Person
	decoder := json.NewDecoder(file)
	if err := decoder.Decode(&p); err != nil {
		log.Fatal(err)
	}

	log.Printf("%+v\n", p) //=> {Name:まく Age:14}
}
{{< /code >}}

### JSON のスキーマが不明な場合

具体的な構造体の型を設定せずに、__`map[string]any`__ 型の変数に JSON ファイルの内容を読み出すこともできます。
これは、キーの型が文字列で、値の型が不明 (`any` / `interface{}`) なマップです。
この場合、少なくとも JSON ルート階層のフィールドはキー名で参照できますが、その値は `any` 型になるので、実際に値を扱う段階で型アサーションにより具体的な型を指定する必要があります。
あるいは、リフレクションで各フィールドの情報を列挙することが可能です。

```go
// JSON ファイルの内容を map 型の Go オブジェクトとして読み出す
var rawJson map[string]any
decoder := json.NewDecoder(file)
if err := decoder.Decode(&rawJson); err != nil {
	log.Fatal(err)
}

// ルート階層のフィールドを参照する（値は any 型なので型アサーションして使う）
name := rawJson["Name"].(string)
age := rawJson["Age"].(float64)
fmt.Println(name)
fmt.Println(age)

// リフレクションで内容を調べても OK
refVal := reflect.ValueOf(rawJson)
for it := refVal.MapRange(); it.Next(); {
	fmt.Printf("key = %s\n", it.Key())
}
```

- 参考: [Golang でファイルを読み書きする (os, io)](/p/6eimpsv/)


JSON のプロパティ名を指定する {#struct-tag}
----

Golang の構造体は、[各フィールドにタグという情報を追加](/p/hxhzfbs/) できるようになっています。
`encoding/json` パッケージは、構造体の __`json`__ タグを読み取って、JSON データに変換したときのプロパティ名として使用するようになっています。

```go
type Person struct {
	Name string `json:"name"`
	Age  int    `json:"age"`
}
```

上記ように `json` タグ設定した構造体を、`encoding/json` パッケージの各種メソッドで JSON に変換すると、次のような感じの JSON データになります。
`json` タグで指定した通り、プロパティ名がすべて小文字になっていることが分かります。

```json
{"name":"まく","age":14}
```

さらに、`json` タグでは次のようなオプション指定もできるようになっています（__`omitempty`__ や __`-`__ の部分）。

```go
type Book struct {
	Title  string `json:"title"`           // ベーシックな使い方
	Price  int    `json:"price,omitempty"` // ゼロ値の場合はこのフィールドを出力しない
	Author string `json:"-"`               // このフィールドは出力しない
}
```

- 参考: [Golang の構造体にタグ情報を追加する (struct tags)](/p/hxhzfbs/)

