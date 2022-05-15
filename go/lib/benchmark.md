---
title: "ベンチマークを行う (testing.B)"
url: "p/29dgjnq/"
permalink: "p/29dgjnq/"
date: "2017-09-12"
tags: ["Go"]
description: "Go 言語には、ベンチマークを行うための仕組みが標準機能として搭載されています。"
redirect_from:
  - /hugo/go/benchmark
---

Go のベンチマーク機能の基本的な使い方
----

Go にはテストフレームワーク（`testing` パッケージ）の一機能として、ベンチマークを行う仕組みが搭載されています。
ここでは、下記のような構造体を、「値渡し」した場合と、「ポインタ渡し」した場合のパフォーマンスの違いを調べてみましょう。

~~~ go
type Book struct {
	Title string
	Author string
	Price int
}

func CallByValue(b Book) {
}

func CallByPointer(b *Book) {
}
~~~

ベンチマーク実行用の関数は、下記のような名前で作成します（`XxxYyyZzz` の部分を任意の名前に設定）。

~~~ go
func BenchmarkXxxYyyZzz(b *testing.B)
~~~

例えば、下記のような感じで実装します。

~~~ go
// CallByValue の実行速度を計測
func BenchmarkCallByValue(b *testing.B) {
	book := Book{"Golang", "Maku", 1500}
	for i := 0; i < b.N; i++ {
		CallByValue(book)
	}
}

// CallByPointer の実行速度を計測
func BenchmarkCallByPointer(b *testing.B) {
	book := Book{"Golang", "Maku", 1500}
	for i := 0; i < b.N; i++ {
		CallByPointer(&book)
	}
}
~~~

上記のように、ループ回数に `b.N` を指定しておくと、有意な実行時間が計測できるまで繰り返し実行してくれるようになります。

ベンチマーク用の実装ファイルは、ファイル名のプレフィックスを `_test.go` としておく必要があります（あくまでベンチマークはテストの一部という位置付けなので）。
下記に全体のコードを示しておきます。

#### sample_test.go

~~~ go
package main

import "testing"

type Book struct {
	Title string
	Author string
	Price int
}

func CallByValue(b Book) {
}

func CallByPointer(b *Book) {
}

func BenchmarkCallByValue(b *testing.B) {
	book := Book{"Golang", "Maku", 1500}
	for i := 0; i < b.N; i++ {
		CallByValue(book)
	}
}

func BenchmarkCallByPointer(b *testing.B) {
	book := Book{"Golang", "Maku", 1500}
	for i := 0; i < b.N; i++ {
		CallByPointer(&book)
	}
}
~~~

ベンチマークを実行するときは、`go test` コマンドを `-bench` プレフィックスを付けて実行します。

~~~
$ go test -bench .
goos: windows
goarch: amd64
BenchmarkCallByValue-8          2000000000      1.62 ns/op
BenchmarkCallByPointer-8        2000000000      0.27 ns/op
PASS
ok      /Users/maku/sandbox     5.060s
~~~

この結果は、`CallByValue` と `CallByPointer` がそれぞれ 2000000000 回実行され、１回あたりの実行にかかった時間が 1.62 ns と、0.27 ns であったことを示しています（ポインタ渡しの方が高速だということ）。


メモリの使用効率を調べる
----

ベンチマークを実行するときに、`-benchmem` フラグを追加で指定すると、実行速度だけではなく、メモリ割り当ての統計情報も一緒に計測してくれます。
下記のサンプルコードは、スライスの `append` 処理のベンチマークを取っています。
`FuncA` の方は、初期サイズ 0 からの `append` の繰り返し、`FuncB` の方は、あらかじめ初期容量を確保しおいてからの `append` の繰り返しを行っています。

#### sample_test.go

~~~ go
package main

import "testing"

const SIZE = 10000

func FuncA() {
	s := []int{}
	for i := 0; i < SIZE; i++ {
		s = append(s, 1)
	}
}

func FuncB() {
	s := make([]int, 0, SIZE)
	for i := 0; i < SIZE; i++ {
		s = append(s, 1)
	}
}

func BenchmarkFuncA(b *testing.B) {
	for i := 0; i < b.N; i++ {
		FuncA()
	}
}

func BenchmarkFuncB(b *testing.B) {
	for i := 0; i < b.N; i++ {
		FuncB()
	}
}
~~~

次のようにベンチマークを起動します。
`-benchmem` オプションは最後に指定することに注意してください。

~~~
$ go test -bench . -benchmem
goos: windows
goarch: amd64
BenchmarkFuncA-8      30000     40035 ns/op     386296 B/op     20 allocs/op
BenchmarkFuncB-8     200000      9985 ns/op      81920 B/op      1 allocs/op
PASS
ok      /Users/maku/sandbox     3.807s
~~~

ベンチマーク結果の `386296 B/op  20 allocs/op` というところは、386296 バイトのメモリを使用したということ、20 回のメモリアロケーションを行ったということを示しています。
この結果、`FuncA` が 20 回もスライスの容量を拡張しているのに対し、`FuncB` は最初に `make` 関数で容量を確保してから一度も拡張されていないということが読み取れます。

プログラムの実行速度が思わしくないときは、このように `-benchmem` オプションを追加してベンチマーク実行すれば、メモリ割り当てが影響しているかどうかを調べることができます。


セットアップに時間がかかるときは b.ResetTimer
----

テスト用データの構築に時間がかかるようなケースで、それにかかった時間をベンチマーク結果には含めたくない場合は、セットアップ処理が終わった時点で `b.ResetTimer()` を呼ぶようにします。

~~~ go
func BenchmarkBigLen(b *testing.B) {
	big := NewBig()
	b.ResetTimer()  // ここから計測開始
	for i := 0; i < b.N; i++ {
		big.Len()
	}
}
~~~

