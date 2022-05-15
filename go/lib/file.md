---
title: "ファイルからの読み出し／ファイルへの書き込みを行う (os, io)"
url: "p/6eimpsv/"
permalink: "p/6eimpsv/"
date: "2017-09-08"
tags: ["Go"]
description: "Go 言語でファイルの読み書きを行うには、os パッケージや io パッケージを使用します。"
redirect_from:
  - /hugo/go/file
---

ファイルからバイトデータを読み出す（Read）
----

既存のファイルを読み込み用にオープンするには、__`os.Open`__ 関数を使用して下記のようにします。

```go
// ファイルを読み取りオープン
file, err := os.Open("./input.txt")  // *os.File
if err != nil {
	log.Fatal(err)
}
defer file.Close()  // 関数脱出時に実行
```

__`defer`__ キーワードでクローズ処理を登録しておくことで、関数から抜けるときに自動的にファイルクローズを実行してくれるようになります。
ファイルを扱うときは、常にこのように記述しておくことで、関数の途中で `return` したときなどのクローズ忘れを防ぐことができます。
ファイルを開いてすぐに実行すべきイディオムとして覚えておきましょう。

ファイルのオープンに失敗すると、２番目の戻り値として `error` オブジェクトが返され、下記のようなエラーメッセージを表示して終了 (`log.Fatal`) します。

```
2017/09/08 23:43:40 open ./input.txt: The system cannot find the file specified.
```

ファイルのオープンに成功したら、取得した `os.File` オブジェクトの __`Read`__ メソッドを使用して読み出し処理を行います。
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

#### input.txt

```
AAA BBB CCC
DDD EEE FFF
GGG HHH III
```

#### sample.go

```go
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
```

#### 実行結果

```console
$ go run sample.go
"AAA BBB CC"
"C\nDDD EEE "
"FFF\nGGG HH"
"H III\n"
```


ファイルからテキストを 1 行ずつ読み出す (bufio.Scanner)
----

`Read([]byte)` 関数を使ったファイルの読み出しは、byte スライスによる読み出しになってしまうため、テキストファイルを 1 行ずつ読み出したい場合などに不便です。
テキストファイルを 1 行ずつ読み出すときは __`bufio.Scanner`__ を使用すると簡単です。
`bufio.Scanner` を使用すると、テキストファイルの内容を 1 行ごとに `string` オブジェクトの形で取得していくことができます。

`bufio.Scanner` オブジェクトは、下記のファクトリ関数で生成することができます。
`io.File` オブジェクトは `Read` メソッドを実装しているため、このファクトリ関数のパラメータとして渡すことができます。

```go
func bufio.NewScanner(r io.Reader) *Scanner
```

下記に、テキストファイル `input.txt` を 1 行ずつ読み出すサンプルコードを示します。

#### sample.go

```go
package main

import (
	"bufio"
	"fmt"
	"os"
)

func main() {
	// ファイルを読み出し用にオープン
	file, err := os.Open("./input.txt")
	if err != nil {
		panic(err)
	}
	defer file.Close()

	// 1 行ずつ読み出し
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		line := scanner.Text()
		fmt.Println(line)
	}
}
```

#### 実行結果

```
$ go run sample.go
AAA BBB CCC
DDD EEE FFF
GGG HHH III
```


ファイルにバイトデータを書き込む (Write)
----

ファイルを書き込み用にオープン（新規作成）するには、__`os.Create`__ 関数を使用します（読み出しのときは `os.Open`）。
`os.Create` はファイルのオープンに成功すると `*os.File` を返します。
`os.File` が実装する `Write([]byte)` 関数を使用して、バイトデータを書き込むことができます。

```go
package main

import "os"

func main() {
	// ファイルを書き込み用にオープン (mode=0666)
	file, err := os.Create("./output.txt")
	if err != nil {
		panic(err)
	}
	defer file.Close()

	data := []byte("Hello")
	_, err = file.Write(data)
	if err != nil {
		panic(err)
	}
}
```


ファイルにテキストを書き込む (WriteString)
----

`os.File` が実装している `io.Writer` インタフェースの `Write([]byte)` メソッドは、バイトデータの書き込みを想定しているため、テキストデータの書き込みには不便です（`string` を `[]byte` に変換する手間がかかります）。

`os.File` は、実は __`WriteString(string)`__ というメソッドを備えており、これを使用すると、指定したテキストデータ (`string`) をそのまま書き込むことができます。

```go
package main

import "os"

func main() {
	// ファイルを書き込み用にオープン (mode=0666)
	file, err := os.Create("./output.txt")
	if err != nil {
		panic(err)
	}
	defer file.Close()

	// テキストを書き込む
	_, err = file.WriteString("Hello\nWorld\n")
	if err != nil {
		panic(err)
	}
}
```

