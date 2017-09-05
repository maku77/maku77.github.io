---
title: "マップを扱う (map)"
created: 2017-09-05
description: "Go 言語でマップを定義するには、map キーワードを使用します。"
---

マップを定義する
----

Go 言語でマップを定義するときの構文は以下のようになります。

~~~
var 変数名 map[キーの型]値の型
~~~

例えば、文字列をキー、数値を値とするマップ (ここでは変数名 `m`) は以下のように定義できます。

~~~ go
var m map[string]int
~~~

スライスなどと同様、初期値を同時に設定してしまうこともできます。
下記のように複数行に分けて記述する場合、最後の要素の後ろのカンマは省略できないことに注意してください。

~~~ go
m := map[string]int{
	"maku": 14,
	"moja": 7,
	"hemu": 10,
}

fmt.Printf("%v\n", m)  //=> map[maku:14 moja:7 hemu:10]
~~~

マップ要素を参照するときは、配列やスライスと同様に `[]` を使用してキーを指定します。
存在しないキーを指定して新しい値を設定することもできます。

~~~ go
i := m["maku"]   // 既存の要素の取得
m["panyo"] = 20  // 新しい要素を追加
~~~


マップの要素を for ループで処理する
----

マップの要素は、配列やスライスと同様に for ループでひとつずつ取得することができます。

~~~ go
m := map[string]int{
	"maku": 14,
	"moja": 7,
	"hemu": 10,
}

for k, v := range m {
	fmt.Println("key:", k, "value:", v)
}
~~~


あるキーが存在するかどうか調べる
----

マップに存在していないキーを指定すると、値の型のゼロ値が返されます（例えば `int` 型の値であれば 0 です）。

~~~ go
i := m["panyo"]  //=> 0
~~~

ただし、これだと、もともとそのキーの値として 0 が格納されていたのか、キーが存在しなかったのかを区別することができません。
キーがもともと存在していたのかどうかを判別するには、２つ目の戻り値として返される bool 値を受け取るようにします。

~~~ go
val, ok := m["maku"]
if ok {
	println(val)
} else {
	println("見つからない")
}
~~~

取得した値を if ブロックの中でしか参照しないのであれば、下記のように変数スコープを限定してしまうのがよいですね。

~~~ go
if val, ok := m["maku"]; ok {
	println(val)
} else {
	println("見つからない")
}
~~~

もちろん、１つ目の戻り値を無視して、キーの存在のみを調べることもできます。

~~~ go
_, ok = m["maku"]
if ok {
	// ...
}
~~~


マップの要素を削除する
----

組み込み関数の [delete](https://golang.org/ref/spec#Deletion_of_map_elements) を使用すると、マップから指定したキーの要素を削除することができます。

~~~ go
println(m["maku"])  //=> 14
delete(m, "maku")
println(m["maku"])  //=> 0（ゼロ値）
~~~

削除しようとしたキーが存在しなかった場合は、何も実行されません（エラーにはなりません）。


マップの値としてマップを持つ
----

マップの値にマップを持つマップを定義することもできます。

#### 例: 値として map[string]int というマップを持つマップを定義する

~~~ go
m := map[string]map[string]int{
	"maku": {"aaa": 10, "bbb": 20},
	"moja": {"aaa": 30, "bbb": 40},
	"hemu": {"aaa": 50, "bbb": 60},
}

fmt.Println(m["maku"]["aaa"])  //=> 10
~~~


マップのキーでソートした順に要素を取り出す
----

Go 言語ではマップをキー順にソートしてループ処理する簡潔な方法がないようです。
以下のサンプルでは、キーのリストを自力で作成し、そのリストをソートしています。

#### sample.go

~~~ go
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
~~~

#### 実行例

~~~
$ go run sample.go
key: aaa value: 20
key: bbb value: 30
key: ccc value: 10
~~~

