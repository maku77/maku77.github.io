---
title: "Golang で CSV 形式の文字列やファイルを扱う (encoding/csv)"
linkTitle: "CSV 形式の文字列やファイルを扱う (encoding/csv)"
url: "p/6k5m3iz/"
date: "2022-09-20"
tags: ["CSV", "Golang"]
---

Golang 標準パッケージの [encoding/csv](https://pkg.go.dev/encoding/csv) を使用すると、CSV 形式のテキストやファイルを Golang の string 配列で読み書きできます。
CSV はカンマ区切りの単純なフォーマットなので、自力でテキストファイルを読み書きすれば済んでしまいそうですが、改行やダブルクォート (`"`) 含むフィールドの扱い方 ([RFC 4180](https://www.rfc-editor.org/rfc/rfc4180.html)) など、微妙に考慮しないといけない部分もあるので、やはり専用の Reader/Writer 実装を使った方が安心です。

```go
import "encoding/csv"
```

このパッケージは、主に次のクラスを提供しています。

- `csv.Reader` ... CSV の読み出しに使用
- `csv.Writer` ... CSV の書き込みに使用

パッケージ名に `csv` という名前が使われていますが、オプション指定によりタブ区切り形式の TSV データを扱うこともできます（後述）。


CSV ファイル／テキストを読み出す (csv.Reader)
----

CSV 形式のファイルやテキストを読み出すには、__`csv.NewReader`__ コンストラクタで __`csv.Reader`__ を生成します。

```go
func csv.NewReader(r io.Reader) *csv.Reader
```

このコンストラクタは、データソースを読み出すための `io.Reader` を受け取るようになっているので、CSV テキストを扱いたいときは `strings.Reader` を、CSV ファイルを扱いたいときは `os.File` を渡してやれば OK です（どちらも `io.Reader` のメソッド `Read` を実装しています）。
あとは、次のようなメソッドで各レコードを文字列スライスとして読み出すことができます。

```go
// 1 つのレコードを読み出す
func (r *csv.Reader) Read() (record []string, err error)

// すべてのレコードを読み出す
func (r *csv.Reader) ReadAll() (records [][]string, err error)
```

### CSV ファイル読み出しの実装例

{{< code lang="csv" title="input.csv（入力ファイル）" >}}
name,age,blood_type
Asuka,14,A
Maya,24,A
Ritsuko,30,B
{{< /code >}}

{{< code lang="go" title="main.go" >}}
package main

import (
	"encoding/csv"
	"fmt"
	"io"
	"log"
	"os"
)

func main() {
	file, err := os.Open("input.csv")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	reader := csv.NewReader(file)
	for {
		record, err := reader.Read() // CSV を 1 レコードずつ読み込み
		if err == io.EOF {
			break // 最後まで読み出した
		}
		if err != nil {
			log.Fatal(err) // 読み出し時にエラー発生
		}

		// 1 レコード分のデータを出力（record は []string 型）
		fmt.Println(record)
	}
}
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ go run main.go
[name age blood_type]
[Asuka 14 A]
[Maya 24 A]
[Ritsuko 30 B]
{{< /code >}}

### 2 次元スライスに一括で読み出す (os.ReadAll)

`(*csv.Reader) Read` の代わりに __`(*csv.Reader) ReadAll`__ メソッドを使用すると、CSV の全レコードを 2 次元スライス (`[][]string`) 変数に一括で読み出すことができます。
大きな CSV ファイルを扱わないことが分かっているのであれば、こちらを使った方が手っ取り早いかもしれません。

{{< code lang="go" hl_lines="2" >}}
reader := csv.NewReader(file)
records, err := reader.ReadAll()
if err != nil {
	log.Fatal(err)
}

// records は [][]string 形式のデータ
for _, fields := range records {
	fmt.Println(fields) // fields は []string（CSV の 1 レコード）
}
{{< /code >}}

### CSV テキストを扱う

文字列変数に格納された CSV 形式のテキストを扱いたいときは、文字列から生成した __`strings.Reader`__ を `csv.NewReader` 関数に渡して、`csv.Reader` を生成します。
残りの処理は、CSV ファイルを扱う場合と同様です。

{{< code lang="go" title="main.go" hl_lines="6" >}}
text := `name,age,blood_type
Asuka,14,A
Maya,24,A
Ritsuko,30,B
`
reader := csv.NewReader(strings.NewReader(text))
{{< /code >}}

### 入力時のオプション

`csv.Reader` の各種オプションプロパティで、CSV 読み込み時の振る舞いを制御できます。
これにより、TSV（タブ区切り）形式やコメント (`#`) 入りのファイルを扱うことができます。

```go
reader := csv.NewReader(file)
reader.Comma = '\t' // 区切り文字（タブ文字を指定すれば TSV ファイルを扱える）
reader.Comment = '#' // この文字で始まる行をコメント行とみなす
reader.TrimLeadingSpace = true // 各フィールドの先頭の空白を削除する
```


CSV ファイルを書き込む (csv.Writer)
----

文字列スライスを CSV ファイルに出力したいときは、書き込み用にオープン (`os.Create`) した `os.File` インスタンスを __`csv.NewWriter`__ 関数に渡して、__`csv.Writer`__ を生成します。
__`(*osv.Writer) Write`__ メソッドを呼び出すごとに、1 つの文字列スライス（CSV レコード）を出力します。
ただし、出力処理は内部的にバッファリングされるので、最後に __`(*osv.Writer) Flush`__ メソッドを呼び出す必要があることに注意してください。
次の例では、`defer` で呼び出すようにしています。

```go
package main

import (
	"encoding/csv"
	"log"
	"os"
)

func main() {
	// 出力用にファイルをオープン
	file, err := os.Create("output.csv")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	// CSV 形式でデータを書き込む
	writer := csv.NewWriter(file)
	defer writer.Flush() // 内部バッファのフラッシュは必須
	writer.Write([]string{"AAA", "BBB", "CCC"})
	writer.Write([]string{"100", "200", "300"})
	writer.Write([]string{"XXX", "YYY", "ZZZ"})
}
```

{{< code lang="csv" title="output.csv（出力結果）" >}}
AAA,BBB,CCC
100,200,300
XXX,YYY,ZZZ
{{< /code >}}

文字列の 2 次元スライスをまとめて書き出したい場合は、`Write` の代わりに __`WriteAll`__ メソッドを使用します。
`WriteAll` を使う場合は内部で `Flush` を呼び出してくれるので、明示的に `Flush` を呼び出す必要はありません。

{{< code lang="go" hl_lines="8" >}}
records := [][]string{
	{"AAA", "BBB", "CCC"},
	{"100", "200", "300"},
	{"XXX", "YYY", "ZZZ"},
}

writer := csv.NewWriter(file)
writer.WriteAll(records)
{{< /code >}}

TSV 形式で出力したいときは、`csv.Writer` の __`Comma`__ プロパティにタブ文字 (`\t`) を指定してから `Write` / `WriteAll` メソッドを呼び出します。

```go
writer := csv.NewWriter(file)
writer.Comma = '\t'
```

