---
title: "Kotlinメモ: 配列やコレクションの要素をループ処理する (for-in, forEach, withIndex)"
url: "/p/9byrmzs/"
date: "2019-05-08"
tags: ["kotlin"]
aliases: ["/kotlin/collection/loop-collection.html"]
---

配列やリストをループ処理する (for, forEach)
----

配列やコレクションに格納された要素は、**`for`** や **`forEach`** を使ってループ処理することができます。
下記の例では配列の要素を `for` ループで処理しています。

{{< code lang="kotlin" title="for を使う方法" >}}
val arr = arrayOf("AAA", "BBB", "CCC")
for (elem in arr) {
    println(elem)
}
{{< /code >}}

配列やコレクション系のクラスには [forEach 拡張関数](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/for-each.html) が定義されているため、`for` の代わりに `forEach` メソッドを呼び出す形でループ処理することもできます。
ループ処理の内容はラムダ式の形 `{ elem -> ... }` で渡します。

{{< code lang="kotlin" title="forEach を使う方法" >}}
val list = listOf("AAA", "BBB", "CCC")
list.forEach { elem ->
    println(elem)
}
{{< /code >}}

ループ処理中の要素名（イテレータ）を省略した場合は、**`it`** で参照できます。

```kotlin
list.forEach {
    println(it)
}
```


インデックス付きでループ処理する (withIndex)
----

Kotlin には Java や C/C++ のような `for (int i = 0; i < n; ++i)` という形式の `for` ループは存在しません。
配列やリストの要素をインデックス付き (0, 1, 2, ...) でループ処理するには、**`withIndex()`** と組み合わせて下記のようにします。

```kotlin
val arr = arrayOf("AAA", "BBB", "CCC")
for ((index, elem) in arr.withIndex()) {
    println("arr[$index] = $elem")
}
```

{{< code title="実行結果" >}}
arr[0] = AAA
arr[1] = BBB
arr[2] = CCC
{{< /code >}}


マップ要素をループ処理する
----

マップのキーと値も `for` を使ってループ処理することができます。

```kotlin
val map = mapOf("foo" to 100, "bar" to 200, "hoge" to 300)
for ((key, value) in map) {
    println("$key => $value")
}
```

{{< code title="実行結果" >}}
foo => 100
bar => 200
hoge => 300
{{< /code >}}

