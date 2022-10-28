---
title: "Golang のパニックによるエラー処理 (panic, recover)"
linkTitle: "パニックによるエラー処理 (panic, recover)"
url: "p/j47aswy/"
date: "2017-09-11"
tags: ["Go"]
description: "Go 言語には try-catch による例外処理の仕組みは存在しませんが、panic という仕組みが用意されています。"
aliases: /hugo/go/panic.html
---

Go 言語には `try ~ catch` による例外処理の仕組みは存在しませんが、__`panic`__ という仕組みが用意されています。

パニックとは？
----

Go 言語では、関数呼び出し時に発生するエラーは、`error` オブジェクトを戻り値として返す方法が採用されています（参考: [関数を定義する](/p/kswy47a/)）。
一方で、実行を継続できないランタイムエラー（スライスの範囲外アクセスなど）が発生した場合には、__パニック (panic)__ を発生させる仕組みになっています。

```go
vals := []int{10, 20, 30}
println(vals[3])
// panic: runtime error: index out of range [3] with length 3
```

パニックが発生すると、デフォルトでは __プログラム全体が終了します__。
一般的なアプリケーションではパニックは発生させるべきではなく、関数内でエラーが発生したときは `error` オブジェクトを返すことが推奨されています。


パニックを発生させる (panic)
----

関数の中から明示的にパニックを発生させるには、__`panic`__ 関数を呼び出します。
`panic` 関数には、エラーの内容を示す `error` オブジェクトを渡すことができます。

```go
import "errors"

func dumpBook(b *Book) {
	if b == nil {
		panic(errors.New("Book cannot be nil"))
	}
	// ...
}
```

このようにパニックの仕組みは簡単に使えてしまいますが、実行を継続できないケースに限って使用すべきです。
通常シーケンスで発生するエラーに関しては、戻り値として `error` オブジェクトを返すようにし、呼び出し側で簡単にハンドルできるようにしておきましょう。

`panic` 関数の代わりに、`log` パッケージの __`log.Panicln`__ 関数や __`log.Panicf`__ 関数を使用すると、時刻付きのログを出力したあとで、パニックを発生させてくれます。

```go
import "log"

func foo() {
	log.Panicln("Something wrong happened")
}
```


パニックから復帰する (recover)
----

関数内の処理で、何らかのパニックが発生したとしても、あらかじめ `defer` 登録された処理は、関数を抜ける前に実行されます。
この `defer` 処理の中で __`recover`__ 関数を呼び出すと、パニック発生時に `panic` 関数に渡されたオブジェクト（通常は `error` オブジェクト）を取得することができます。
パニック発生時には、通常はプログラム全体が終了しますが、`defer` 処理の中で `recover` を呼び出すように実装した場合、プログラムの実行は継続されます。

```go
package main

import "log"

func hello() {
	// パニックをハンドルするための処理を defer 登録
	defer func() {
		if err := recover(); err != nil {
			log.Printf("ぎゃー: %v", err)
		}
	}()

	// out of range エラーをわざと発生させる
	println([]int{}[0])
}

func main() {
	hello()
	println("パニックが発生してもここまで処理が継続される")
}
```

{{< code title="実行結果" >}}
2017/09/11 23:21:10 ぎゃー: runtime error: index out of range
2017/09/11 23:21:10 パニックが発生してもここまで処理が継続される
{{< /code >}}

- 参考: [Handling panics - The Go Programming Language](https://golang.org/ref/spec#Handling_panics)

