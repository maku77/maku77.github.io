---
title: "Golang の switch 条件分岐（switch の基本、便利な使い方、型スイッチ）"
linkTitle: "switch による条件分岐（switch の基本、便利な使い方、型スイッチ）"
url: "p/x6adgjn/"
date: "2017-08-31"
tags: ["Go"]
description: "Go 言語の switch 文は、Java や C++ に比べて簡潔に記述できるようになっています。"
aliases: /hugo/go/switch.html
---

Go 言語の __`switch`__ 文は、Java や C++ に比べて簡潔に記述できるようになっています。

switch 文の基本
----

Go 言語の __`switch`__ 文では、__`case`__ に複数の値をカンマで区切って指定することができます。
１つの `case` が実行されると自動的に `switch` 文を終了するため、C 言語のように `case` ごとに `break` と記述する必要はありません。
逆に、次の `case` を続けて実行したい場合は、明示的に __`fallthrough`__ と記述する必要があります。

```go
func checkNumber(i int) {
	switch i {
	case 0:
		fmt.Println("zero")
	case 2, 3, 5, 7:
		fmt.Println("primary number")
		fallthrough
	default:
		fmt.Println("good number")
	}
}
```

`if` 文の代わりに `switch` 文を使うことでコードを簡潔にできることがあります。

```go
func shouldEscape(c byte) bool {
	switch c {
	case ' ', '?', '&', '=', '#', '+', '%':
		return true
	}
	return false
}
```

`if` 文と同様に、`switch` 文でも変数のスコープをブロック内に絞った変数定義を行うことができます。
次の `os` 変数は、`switch` 文の中でのみ参照できます。

```go
switch os := runtime.GOOS; os {
case "darwin":
	fmt.Println("OS = OS X")
case "linux":
	fmt.Println("OS = Linux")
default:
	// freebsd, openbsd, plan9, windows...
	fmt.Printf("OS = %s\n", os)
}
```


連続する if else の代わりとして switch 文を使用する
----

__`switch` 文の条件部分を省略__ すると、`switch true` と記述するのと同様の振る舞いをします。
この記述方法は、連続した `if else` を簡潔に記述するために使用することができます。
典型的なのは、ある変数の値を大小比較したいケースです。

```go
func unhex(c byte) byte {
	switch {
	case '0' <= c && c <= '9':
		return c - '0'
	case 'a' <= c && c <= 'f':
		return c - 'a' + 10
	case 'A' <= c && c <= 'F':
		return c - 'A' + 10
	}
	return 0
}
```

もう 1 つ例を。

```go
func greet() {
	t := time.Now()
	switch {
	case t.Hour() < 12:
		fmt.Println("Good morning.")
	case t.Hour() < 17:
		fmt.Println("Good afternoon.")
	default:
		fmt.Println("Good evening.")
	}
}
```

上記の変数 `t` のスコープを `switch` 文の内部に絞りたいのであれば、下記のように変数定義します（セミコロンの後ろの条件部分だけを省略します）。

```go
switch t := time.Now(); {
case t.Hour() < 12:
	fmt.Println("Good morning.")
case t.Hour() < 17:
	fmt.Println("Good afternoon.")
default:
	fmt.Println("Good evening.")
}
```

ちなみに上記の `switch` を `if else` を使って書き換えると次のようになります。

```go
if t := time.Now(); t.Hour() < 12 {
	fmt.Println("Good morning!")
} else if t.Hour() < 17 {
	fmt.Println("Good afternoon.")
} else {
	fmt.Println("Good evening.")
}
```

まぁ、このくらいであればそれほど差はありませんが、それでも `switch` の方が可読性は高そうです。


型スイッチ (Type Switch)
----

Go 言語の関数で任意の型の引数を受け取るには、[空インタフェース型 interface{}](/p/aimpsvz/) を使用します。
オブジェクトの実際の型に基づいて分岐処理を行うには、次のように __型スイッチ (Type Switch)__ の仕組みを使います。

```go
func checkType(value interface{}) {
	switch v := value.(type) {
	case nil:
		fmt.Println("value is nil")
	case int:
		fmt.Printf("value is int (%d)\n", v)
	case float64:
		fmt.Printf("value is float64 (%f)\n", v)
	case func(int) string:
		fmt.Println("value is function that takes int and returns string")
	case bool, string:
		fmt.Println("value is bool or string")
	default:
		fmt.Printf("value has unknown type (%T)\n", v)
	}
}
```

上記の例では、`value` 変数の実際の型によって分岐処理を行っています。
さらに、分岐後は `v` 変数をその型の値として参照することができます。

型スイッチは、[型アサーション (Type Assertion)](/p/jruz369/) の特殊形態だと考えることができます。

