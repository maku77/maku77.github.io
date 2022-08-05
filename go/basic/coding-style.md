---
title: "Go 言語のコーディングスタイル（コーディング規約）"
url: "p/rz47adg/"
permalink: "p/rz47adg/"
date: "2017-08-30"
tags: ["Go"]
description: "Go 言語には、標準のコーディング規約が用意されています。"
redirect_from:
  - /hugo/go/coding-style
---

Go 言語のコーディング規約
----

Go 言語におけるコーディングスタイルは、下記のドキュメントが参考になるでしょう。

- [Effective Go - The Go Programming Language](https://golang.org/doc/effective_go.html#formatting)

ポイントを簡単にまとめておきます。

* __インデントにはハードタブ（タブ文字）を使用__ し、ソフトタブ（半角スペース）を使用しない。
* １行あたりの文字数に制限はない。もちろん、長すぎる場合は改行してもよい。
* 連続した変数定義やコメントは縦に揃える
  ```go
  type T struct {
      name    string // name of the object
      value   int    // its value
  }
  ```
* 演算子の前後にスペースを入れない。演算子の優先順位を明確にするときのみスペースを入れる。その代わり余計な括弧を使わない。
  ```go
  x<<8 + y<<16
  x, y = y, x+y
  ```
* 命名規則
  * 公開メソッド、フィールド: `MexedCaps`（大文字で始める）
  * 非公開メソッド、フィールド: `mixedCaps`（小文字で始める）
  * コンストラクタ: `NewBook`（New + 生成対象の構造体名）
  * １つのメソッドを持つインタフェース: `Reader`（Read メソッドだけを持つインタフェース）

というように、いくつかのフォーマットルールがあるのですが、Go には下記のような自動整形ツールが付属しているので、これを実行して自動で整形してしまうのが手っ取り早いです。


コードを自動整形する (`go fmt` と `gofmt`)
----

Go 言語には、コーディング規約に従ってコードを自動整形するためのツール (__`go fmt`__) が標準で搭載されています。

##### 例: hello.go ファイルを直接フォーマットする

```console
$ go fmt hello.go
```

上記のように実行すると、`hello.go` ファイルの内容が整形されて書き換えられます。
__指定したソースコードが直接変更される__ ことに注意してください。

元のソースコードを書き換えずに標準出力へ整形結果を出力したい場合は、`go fmt` の代わりに __`gofmt`__ コマンドを使用します。

##### 例: hello.go のフォーマット結果を標準出力へ出力する

```console
$ gofmt hello.go
package main

import "fmt"

func main() {
	fmt.Println("Hello Go")
}
```

`go fmt` の振る舞いは、`gofmt -l -w` と同様のようです。

