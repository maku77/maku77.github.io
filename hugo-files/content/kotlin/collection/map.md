---
title: "Kotlinメモ: コレクションの要素をもとに別のリストを作成する (map)"
url: "/p/v5wjmyk/"
date: "2019-06-03"
tags: ["kotlin"]
aliases: ["/kotlin/collection/map.html"]
---

配列やリストに対する map
----

`map` 関数を使用すると、配列やリストなどのコレクション要素の値を使って、別のコレクションを生成することができます。
次の例では、数値リストから、各要素を 2 倍にした新しい数値リストを作成しています。

```kotlin
val nums = listOf(1, 2, 3)
val nums2 = nums.map { it * 2 }
println(nums2)  //=> [2, 4, 6]
```

`map` 関数の典型的な使用例として、あるクラスのインスタンスのリストから、特定のプロパティを抜き出したリストを作成する、というものがあります。
次の例では、`Book` インスタンスのリストから、`title` プロパティだけを抜き出したリストを作成しています。

```kotlin
data class Book(val title: String, val price: Int)

fun main() {
    val books = listOf(
        Book("Title1", 100),
        Book("Title2", 200),
        Book("Title3", 300))
    val titles = books.map { it.title }
    println(titles)  //=> ["Title1", "Title2", "Title3"]
}
```

上記の `books.map { it.title }` という部分は、[メンバ参照の記法](/p/r2gqqnt/) を使用して **`books.map(Book::title)`** と記述することもできます。


マップに対する map
----

マップ系の要素に対して `map` を実行した場合、ラムダ式に渡されるパラメータは **`Map.Entry`** 型のオブジェクトになり、`key` プロパティと `value` プロパティを使ってマップ要素のキー＆バリューを取り出せます。
戻り値はリストです。

次の例では、マップ要素のキーを、値の回数だけ繰り返して作成した文字列のリストを生成しています。
`String#repeat()` は、自分自身の文字列を指定回数だけ繰り返した文字列を作成します。

```kotlin
val map = mapOf("A" to 1, "B" to 2, "C" to 3)
val list = map.map { e -> e.key.repeat(e.value) }
println(list)
```

{{< code title="実行結果" >}}
[A, BB, CCC]
{{< /code >}}

