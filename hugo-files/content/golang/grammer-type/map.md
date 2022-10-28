---
title: "マップを扱う (map)"
url: "p/5cgjnqt/"
date: "2017-09-05"
tags: ["Go"]
description: "Go 言語でマップを定義するには、map キーワードを使用します。"
aliases: /hugo/go/map.html
---

Go 言語でマップを定義するには、__`map`__ キーワードを使用します。

マップを定義する (map)
----

Go 言語のマップ型は、__`map[キーの型]値の型`__ のように表現します。
どこにもスペースをいれないことに注意してください（`go fmt` コマンドで自動的にこのようにフォーマットされます）。

例えば、キーの型を `string`、値の型を `int` とするマップは次のように作成できます。

{{< code lang="go" title="空のマップを作る方法" >}}
var m = map[string]int{}
m := map[string]int{}      // 関数内ではこの省略形が使える
m := make(map[string]int)  // make を使う方法
{{< /code >}}

次のように初期値を指定せずにマップを作成するとゼロ値 (nil) になります。
要素数ゼロのマップとして参照はできますが、要素を追加しようとすると panic が発生します。

{{< code lang="go" title="あまり意味のない nil マップの作成方法" >}}
var m map[string]int  // これはゼロ値 (nil) になる
println(len(m))       // 0
m["maku"] = 14        // panic
{{< /code >}}

[配列やスライス](/p/cjosvz3/) と同様、初期値を同時に設定してしまうこともできます（前述の例では初期値を空っぽ `{}` にしています）。
下記のように複数行に分けて初期値を記述する場合、最後の要素の後ろのカンマは省略できないことに注意してください。

```go
m := map[string]int{
	"maku": 14,
	"puni": 7,
	"hemu": 10,
}

fmt.Printf("%v\n", m)  //=> map[maku:14 puni:7 hemu:10]
```

マップ要素を参照するときは、配列やスライスと同様に `[]` を使用してキーを指定します。
存在しないキーを指定して新しい値を設定することもできます。

```go
i := m["maku"]   // 既存の要素の取得
m["panyo"] = 20  // 新しい要素を追加
```


マップの要素を for ループで処理する (range)
----

マップの要素は、配列やスライスと同様に for ループと __`range`__ を使ってひとつずつ取得することができます。

```go
m := map[string]int{
	"maku": 14,
	"puni": 7,
	"hemu": 10,
}

for k, v := range m {
	fmt.Printf("key: %s, value: %d\n", k, v)
}
```

{{< code title="実行結果" >}}
key: maku, value: 14
key: puni, value: 7
key: hemu, value: 10
{{< /code >}}


あるキーが存在するかどうか調べる
----

マップに存在していないキーを指定すると、値の型の __ゼロ値__ が返されます（例えば `int` 型の値であれば 0 です）。

```go
i := m["panyo"]  //=> 0
```

そのため、マップの値を参照してゼロ値が返された場合、「キーが存在しない」のか、あるいは「値として 0 が格納されている」のかを区別することができません。
キーがもともと存在していたのかどうかを判別するには、__2 つ目の戻り値として返される bool 値__ を参照します。

```go
val, ok := m["maku"]
if ok {
	println(val)
} else {
	println("見つからない")
}
```

取得した値を if ブロックの中でしか参照しないのであれば、下記のように変数スコープを限定してしまうのがよいです。

```go
if val, ok := m["maku"]; ok {
	println(val)
} else {
	println("見つからない")
}
```

キーの存在のみを調べたいときは、1 つ目の戻り値をアンダースコア (`_`) で受け取って無視します。

```go
if _, ok := m["maku"]; ok {
	println("あるよ")
}
```


マップの要素を削除する (delete)
----

組み込み関数の [delete](https://golang.org/ref/spec#Deletion_of_map_elements) を使用すると、マップから指定したキーの要素を削除することができます。

```go
m := map[string]int{
	"maku": 100,
	"pani": 200,
}

println(len(m))    //=> 2
println(m["maku"]) //=> 100

delete(m, "maku")  // キー "maku" を削除する

println(len(m))    //=> 1（サイズが減っている）
println(m["maku"]) //=> 0（存在しないキーを参照するとゼロ値が返される）
```

削除しようとしたキーが存在しなかった場合は、何も実行されません（エラーにはなりません）。


マップの値としてマップを持つ
----

マップの値にマップを持つマップを定義することもできます。

{{< code lang="go" title="値として map[string]int というマップを持つマップを定義する" >}}
m := map[string]map[string]int{
	"maku": {"aaa": 10, "bbb": 20},
	"puni": {"aaa": 30, "bbb": 40},
	"hemu": {"aaa": 50, "bbb": 60},
}

fmt.Println(m["maku"]["aaa"])  //=> 10
{{< /code >}}

この場合も、型名にはどこにもスペースを入れてはいけません。
若干読みにくいですが、気合いで読み解きましょう。


マップのキーでソートした順に要素を取り出す
----

Go 言語には、マップをキー順にソートしてループ処理する簡潔な方法はないようです。
以下のサンプルでは、キーのリストを自力で作成し、そのリストをソートしています。

{{< code lang="go" title="main.go" >}}
package main

import "fmt"
import "sort"

func main() {
	m := map[string]int{
		"ccc": 10,
		"aaa": 20,
		"bbb": 30,
	}

	// ソート済みのキーリストを作成する
	keys := make([]string, 0, len(m))
	for k := range m {
		keys = append(keys, k)
	}
	sort.Strings(keys)  // キーの型が int なら sort.Ints とする

	// キーリストの順番通りに値を列挙する
	for _, k := range keys {
		fmt.Println("key:", k, "value:", m[k])
	}
}
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ go run main.go
key: aaa value: 20
key: bbb value: 30
key: ccc value: 10
{{< /code >}}

