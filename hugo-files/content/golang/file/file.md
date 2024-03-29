---
title: "Golang でファイルを読み書きする (os, io)"
linkTitle: "ファイルを読み書きする (os, io)"
url: "p/6eimpsv/"
date: "2017-09-08"
tags: ["Go"]
description: "Go 言語でファイルの読み書きを行うには、os パッケージや io パッケージを使用します。"
aliases: /hugo/go/file.html
---

Go 言語でファイルの読み書きを行うには、__`os`__ パッケージや __`io`__ パッケージを使用します。

ファイルからバイトデータを読み出す（io.Reader）
----

既存のファイルを読み込み用にオープンするには、__`os.Open`__ 関数を使用して下記のようにします。

```go
// ファイルを読み取りオープン
file, err := os.Open("./input.txt")  // *os.File
if err != nil {
	log.Fatal(err)
}
defer file.Close()  // 関数を抜けるときに自動実行
```

__`defer`__ キーワードでクローズ処理を登録しておくことで、関数から抜けるときに自動的にファイルクローズを実行してくれるようになります。
ファイルを扱うときは、常にこのように記述しておくことで、関数の途中で `return` したときなどのクローズ忘れを防ぐことができます。
ファイルを開いてすぐに実行すべきイディオムとして覚えておきましょう。

ファイルのオープンに失敗すると、２番目の戻り値として `error` オブジェクトが返され、下記のようなエラーメッセージを表示して終了 (`log.Fatal`) します。

```
2017/09/08 23:43:40 open ./input.txt: The system cannot find the file specified.
```

ファイルのオープンに成功したら、取得した `os.File` オブジェクトの __`Read`__ メソッドを使用して読み出し処理を行います（`Read` は `io.Reader` インタフェースで定義されているメソッドです）。
`Read` メソッドは、引数で渡した `[]byte` スライスにファイルの内容を読み込みます。
一度に読み込むサイズは、パラメータとして渡すスライスの長さ (`len(s)`) になるため、あらかじめ `make` 関数などを使ってスライス長を確保しておく必要があります。
`Read` 関数は、ファイルの末尾まで読み込み終わると、__`0, io.EOF`__ を返します。
２番目の戻り値が `io.EOF` になるまで繰り返し `Read` を実行するようにすれば、すべてのデータを読み出すことができます。

```go
// 10 バイト分のバッファを用意
buf := make([]byte, 10)

// ファイルから読み出し
for {
	count, err := file.Read(buf)
	if err == io.EOF {
		break  // Reached to EOF
	}
	if err != nil {
		log.Fatal(err)
	}
	fmt.Printf("%q\n", buf[:count])
}
```

下記は、サンプルの入力ファイルと、実行可能な全体のコード、実行結果です。
ここでは、分かりやすさのために入力ファイルとしてテキストファイル (`input.txt`) を使用していますが、`Read` メソッドによる読み出しは、通常はバイナリファイルを扱うことを想定しています。

{{< code title="input.txt" >}}
AAA BBB CCC
DDD EEE FFF
GGG HHH III
{{< /code >}}

{{< code lang="go" title="sample.go" >}}
package main

import (
	"fmt"
	"io"
	"log"
	"os"
)

func main() {
	// ファイルを読み取りオープン
	file, err := os.Open("./input.txt")  // *os.File
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()  // 関数脱出時に実行

	// ファイルから読み出し
	buf := make([]byte, 10)
	for {
		count, err := file.Read(buf)
		if err == io.EOF {
			break  // Reached to EOF
		}
		if err != nil {
			log.Fatal(err)
		}
		fmt.Printf("%q\n", buf[:count])
	}
}
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ go run sample.go
"AAA BBB CC"
"C\nDDD EEE "
"FFF\nGGG HH"
"H III\n"
{{< /code >}}


ファイルを一度に読み出す (os.ReadFile)
----

`io.Reader` インタフェースで定義されている `Read` メソッドは、指定したサイズのバイトデータしか読み込めませんが、[os.ReadFile](https://pkg.go.dev/os#ReadFile) を使用すると、ファイル全体を一度に読み出すことができます。

```go
package main

import (
	"fmt"
	"log"
	"os"
)

func main() {
	bytes, err := os.ReadFile("./input.txt")
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println(string(bytes))
	// os.Stdout.Write(bytes)
}
```

ちなみに、`os.ReadFile` はファイルの読み出し専用ですが、代わりに [io.ReadAll](https://pkg.go.dev/io#ReadAll) を使用すると、`io.Reader` を実装した任意のインスタンスから全データを読み出すことができます。
次の例では、`string.Reader` からすべてのテキストを読み出しています。

```go
r := strings.NewReader("AAA\nBBB\nCCC")
bytes, err := io.ReadAll(r)
if err != nil {
	log.Fatal(err)
}
fmt.Printf("%s\n", bytes)
```


テキストファイルを 1 行ずつ読み出す (bufio.Scanner)
----

`Read([]byte)` 関数を使ったファイルの読み出しは、byte スライスによる読み出しになってしまうため、テキストファイルを 1 行ずつ読み出したい場合などに不便です。
テキストファイルを 1 行ずつ読み出すときは [bufio.Scanner](https://pkg.go.dev/bufio#Scanner) を使用すると簡単です。
`bufio.Scanner` を使用すると、テキストファイルの内容を 1 行ごとに `string` オブジェクトの形で取得していくことができます。

`bufio.Scanner` オブジェクトは、下記のファクトリ関数で生成することができます。
`io.File` オブジェクトは `Read` メソッドを実装しているため、このファクトリ関数のパラメータとして渡すことができます。

```go
func bufio.NewScanner(r io.Reader) *Scanner
```

下記に、テキストファイル `input.txt` を 1 行ずつ読み出すサンプルコードを示します。

{{< code lang="go" title="sample.go" >}}
package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
)

func main() {
	// ファイルを読み出し用にオープン
	file, err := os.Open("./input.txt")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	// 1 行ずつ読み出し
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		line := scanner.Text()
		fmt.Println(line)
	}

	// スキャン時のエラーをハンドル
	if err := scanner.Err(); err != nil {
		log.Fatal(err)
	}
}
{{< /code >}}

`scanner.Scan` のループ処理を抜けた後に、__`scanner.Err`__ メソッドでエラーの有無をチェックする必要があることに注意してください。

{{< code title="実行結果" >}}
$ go run sample.go
AAA BBB CCC
DDD EEE FFF
GGG HHH III
{{< /code >}}


ファイルにバイトデータを書き込む (io.Writer, os.WriteFile)
----

### io.Writer の Write メソッドを使う方法

ファイルを書き込み用にオープン（新規作成）するには、__`os.Create`__ 関数を使用します（読み出しのときは `os.Open`）。
`os.Create` はファイルのオープンに成功すると __`*os.File`__ を返します。
`os.File` の `Write([]byte)` メソッドを使用して、バイトデータを書き込むことができます。

```go
package main

import (
	"log"
	"os"
)

func main() {
	// ファイルを書き込み用にオープン (mode=0666)
	file, err := os.Create("./output.txt")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	data := []byte("Hello\nWorld")
	_, err = file.Write(data)
	if err != nil {
		log.Fatal(err)
	}
}
```

### os.WriteFile 関数を使う方法

[os.WriteFile](https://pkg.go.dev/os#WriteFile) 関数を使うと、ファイル名と書き込むバイトデータを指定するだけで、ファイルへの出力を一気に済ませることができます。

```go
package main

import (
	"log"
	"os"
)

func main() {
	bytes := []byte("Hello\nWorld")
	if err := os.WriteFile("./output.txt", bytes, 0666); err != nil {
		log.Fatal(err)
	}
}
```


ファイルに 1 行ずつテキストを書き込む (WriteString)
----

`os.File` が実装している `io.Writer` インタフェースの `Write([]byte)` メソッドは、バイトデータの書き込みを想定しているため、テキストデータの書き込みには不便です（`string` を `[]byte` に変換する手間がかかります）。

`os.File` は、実は __`WriteString(string)`__ というメソッドを備えており、これを使用すると、指定したテキストデータ (`string`) をそのまま書き込むことができます。

```go
package main

import (
	"log"
	"os"
)

func main() {
	// ファイルを書き込み用にオープン (mode=0666)
	file, err := os.Create("./output.txt")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	// テキストを書き込む
	_, err = file.WriteString("Hello\nWorld\n")
	if err != nil {
		log.Fatal(err)
	}
}
```

- 参考: [Golang で JSON 形式の文字列やファイルを扱う (encoding/json)](/p/dsbs9p5/)

