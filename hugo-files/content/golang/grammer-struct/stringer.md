---
title: "fmt.Println による構造体の出力をカスタマイズする (Stringer)"
url: "p/kziyhzg/"
date: "2022-11-05"
tags: ["Golang"]
---

Golang での構造体の出力
----

Golang の構造体の内容は、`fmt` パッケージの各種関数で出力できるようになっています。
次のコードでは、`Game` 構造体の内容をいくつかの方法で出力しています。

```go
package main

import "fmt"

type Game struct {
	Title string
	Price int
}

func main() {
	g := &Game{Title: "ドンキーコング", Price: 4500}

	fmt.Println(g)         // &{ドンキーコング 4500}
	fmt.Printf("%v\n", g)  // &{ドンキーコング 4500}
	fmt.Printf("%+v\n", g) // &{Title:ドンキーコング Price:4500}
	fmt.Printf("%#v\n", g) // &main.Game{Title:"ドンキーコング", Price:4500}
}
```

こういった出力で十分であればよいのですが、異なる出力が欲しいときは、次に説明する `Stringer` インタフェースを実装することで対応できます。


String 関数による出力のカスタマイズ
----

`fmt` パッケージで構造体を文字列表現に変換するとき、内部で `String` 関数が呼び出されます。
`String` 関数は、`fmt` パッケージの [Stringer](https://pkg.go.dev/fmt#Stringer) インタフェースとして定義されています。

```go
type Stringer interface {
    String() string
}
```

例えば、`Game` 構造体の出力をカスタマイズしたいときは、次のように `String()` 関数を実装します。

```go
type Game struct {
	Title string
	Price int
}

// Game 構造体の文字列表現
func (g Game) String() string {
	return fmt.Sprintf("「%s」 %d円", g.Title, g.Price)
}
```

すると、`fmt` パッケージの各種関数による出力は次のように変化します。

```go
func main() {
	g := &Game{Title: "ドンキーコング", Price: 4500}
	fmt.Println(g)         // 「ドンキーコング」 4500円
	fmt.Printf("%v\n", g)  // 「ドンキーコング」 4500円
	fmt.Printf("%+v\n", g) // 「ドンキーコング」 4500円
	fmt.Printf("%#v\n", g) // &main.Game{Title:"ドンキーコング", Price:4500}
}
```

Golang のソースコード表現を表す __`%#v`__ の出力だけは変化しないようです。

構造体の内容をきれいな JSON 形式で出力したければ、`json` パッケージを使って次のように実装できます（参考: [Golang で JSON を扱う](/p/dsbs9p5/)）。

```go
func (g Game) String() string {
	bytes, err := json.MarshalIndent(g, "", "  ")
	if err != nil {
		// Cyclic data structures が見つかった時の対策
		return g.Title
	}
	return string(bytes)
}
```

{{< code lang="json" title="出力例" >}}
{
  "Title": "ドンキーコング",
  "Price": 4500
}
{{< /code >}}

ただ、デバッグ用途の出力であれば、素直に出力専用の関数を定義した方がよさそうです。

```go
func PrintGame(g *Game) {
	bytes, _ := json.MarshalIndent(g, "", "  ")
	fmt.Println(string(bytes))
}
```

