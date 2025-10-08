---
title: "Kotlinメモ: コレクション内の最小・最大の値を見つける (min, max, minBy, maxBy)"
url: "/p/kqpvcpj/"
date: "2019-04-26"
tags: ["kotlin"]
aliases: ["/kotlin/collection/min-max.html"]
---


リスト内の最小値、最大値を見つける
----

[min 関数](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/min.html) および [max 関数](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/max.html) を使用すると、配列やコレクション（map を除く）の中から、最小値を持つ要素、最大値を持つ要素を取り出すことができます。

```kotlin
val list = listOf(3, 1, 5, 2, 4)
println("min = ${list.min()}, max = ${list.max()}")  //=> min = 1, max = 5
```

Comparable な要素であれば min(), max() で取り出せる
----

配列やコレクションから `min`/`max` 関数で要素を取り出すには、要素の比較方法が明確になっている必要があります。
もちろん数値の要素であれば問題なく `min`/`max` 関数で取り出せますが、それ以外のオブジェクトでも **`Comparable`** インタフェースさえ実装していれば、`min`/`max` 関数で取り出せるようになります。

下記の `Book` クラスは、`Comparable` インタフェースを実装しているため、コレクションに格納した後で、`min`/`max` 関数で取り出すことができます。
ここでは、`price` プロパティの値で大小関係を判定するように実装しています。

```kotlin
data class Book(val title: String, val price: Int) : Comparable<Book> {
    override fun compareTo(other: Book) = this.price - other.price
}

fun main() {
    val books = listOf(
        Book("Title1", 500),
        Book("Title2", 300),
        Book("Title3", 100)
    )
    println(books.min()?.title)  //=> Title3
    println(books.max()?.title)  //=> Title1
}
```


比較するプロパティをその都度指定する (minBy, maxBy)
----

`min`/`max` 関数の代わりに、[minBy](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/min-by.html)/[maxBy](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/max-by.html) 関数を使用すると、関数の呼び出し時に**ラムダ式で比較する値を指定**することができます。

{{< code lang="kotlin" title="例: price プロパティが最小のものを取り出す" >}}
val cheapestBook = books.minBy { it.price }
{{< /code >}}

`minBy`/`maxBy` 関数を使用すると、`Comparable` でない要素を含むコレクションから（あるプロパティが）最小・最大である要素を取り出すことができます。
また、呼び出し時に比較に使用するプロパティを指定できるので、必要に応じて参照するプロパティを変えて最小値・最大値を取り出すことができます。

下記の例では、`Book` 要素を含むリストから、`price` プロパティが最小・最大の要素を取り出しています。
ここで定義している `Book` クラスは `Comparable` を実装していないので、`minBy`/`maxBy` を使い、比較に使用するプロパティをラムダ式で指定しています。

```kotlin
// Book クラス。Comparable インタフェースを実装してない。
data class Book(val title: String, val price: Int)

fun main() {
    val books = listOf(
        Book("Title1", 500),
        Book("Title2", 300),
        Book("Title3", 100)
    )
    val min = books.minBy { it.price }  // 最安値の本を探す
    val max = books.maxBy { it.price }  // 最高値の本を探す
    println(min?.title)  //=> Title3
    println(max?.title)  //=> Title1
}
```

