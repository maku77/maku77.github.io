---
title: "Golang の if による条件分岐"
linkTitle: "if による条件分岐"
url: "p/pw258be/"
date: "2017-08-31"
tags: ["Go"]
description: "Go 言語の if 文の構文は、Java や C++ とほぼ同じですが、括弧の使用ルールが微妙に異なります。"
aliases: /hugo/go/if
---

Go 言語の `if` 文の構文は、Java や C++ とほぼ同じですが、括弧の使用ルールが微妙に異なります。

if 文の構文
----

Go 言語の `if` 文は、条件部を括弧で囲みません。
一方で、実行部分のブロックを囲む中括弧（`{` と `}`）は省略することができません。

```go
x, y := 100, 200
if x > y {
	fmt.Println("x is larger than y")
} else if x < y {
	fmt.Println("x is smaller than y")
} else {
	fmt.Println("x is equal to y")
}
```

比較演算子としては、Java や C++ と同様の `==`、`!=`、`<`、`<=`、`>`、`>=` が使用できます。
文字列の比較も `==` 演算子で行えます。
論理演算子も同様に、`||`、`&&`、`!` を使用できます。

```go
s := "hemu"
x, y := 100, 200
if s == "hemu" && x < y {
	// ...
}
```

ちなみに Go 言語には Java や C++ のような三項演算子 (`a ? b : c`) は用意されていません。
言語として三項演算子や `if` 式を採用してしまうと、複雑なコードが書かれやすいというのが理由っぽいです（適度に使えば便利なんですけどね^^;）。

- 参考: [Why does Go not have the ?: operator?](https://go.dev/doc/faq#Control_flow)


if スコープの変数
----

```go
if 変数定義; 条件式 {
	// ...
}
```

という形で変数定義を行うと、その変数のスコープを `if` あるいは、`else` のブロック内に制限することができます。
例えば、下記のように記述すると、`err` 変数は `if` の条件式と処理ブロックの中でのみ参照できるようになります。

```go
if err := recover(); err != nil {
	log.Fatal(err)
}
```

次の例では、マップ変数 `m` にキー `AAA` が存在するときのみ、その値を参照しています。

```go
m := map[string]int{
	"AAA": 100,
	"BBB": 200,
	"CCC": 300,
}

if val, ok := m["AAA"]; ok {
	fmt.Println(val)
}
```

