---
title: "Kotlinメモ: 配列やリストから null 以外の要素のみを抽出する (filterNotNull, mapNotNull)"
url: "/p/wz5cnqq/"
date: "2019-04-24"
tags: ["kotlin"]
aliases: ["/kotlin/collection/filter-not-null.html"]
---

filterNotNull 関数による null 要素の削除
----

Kotlin の [filterNotNull 関数](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/filter-not-null.html) を使用すると、`null` 要素の混じった配列やリストから、`null` 以外の要素だけを抽出することができます。

```kotlin
val list1 = listOf(100, null, 200, null, 300)
val list2 = list1.filterNotNull()
println(list1)  //=> [100, null, 200, null, 300]
println(list2)  //=> [100, 200, 300]
```

下記のように記述するのとほぼ同様ですが、**`filterNotNull` を使うと戻り値の型が少し変わります**。

```kotlin
val list2 = list1.filter { it != null }
```

`filterNotNull` 関数の定義を見てみてください。

```kotlin
fun <T : Any> Array<out T?>.filterNotNull(): List<T>
fun <T : Any> Iterable<T?>.filterNotNull(): List<T>
```

戻り値の `List` が保持する要素の型が `T?` から `T` に変わっており、**もはや Nullable ではありません**。
最初に挙げた例を、冗長に型付きで記述すると下記のようになります。

```kotlin
val list1: List<Int?> = listOf(100, null, 200, null, 300)
val list2: List<Int> = list1.filterNotNull()
```

`null` を排除した `List<Int>` に変換した後は、`sum()` や `average()` などの計算用関数を null チェックなしで思う存分呼び出せます。

```kotlin
fun showValidNumberStats(numbers: List<Int?>) {
    val validNumbers = numbers.filterNotNull()
    println("Sum: ${validNumbers.sum()}")
    println("Avg: ${validNumbers.average()}")
}
```


mapNotNull 関数（map と filterNotNull の一括処理）
----

コレクションの要素を `map` 関数で変換し、その結果から `null` 要素を取り除くという処理を行う場合、単純に考えると `map` → `filterNotNull` と順番に実行することになります。
次の例では、`pages` コレクションから `category` プロパティの値を収集し、`null` 値を取り除いています。

```kotlin
val categories = pages.map { it.category }.filterNotNull()
```

Kotlin には、この 2 つの操作を一発で行うための **`mapNotNull`** 関数が用意されています。

```kotlin
val categories = pages.mapNotNull { it.category }
```

{{< code lang="kotlin" title="全体のコード" >}}
data class Page(val title: String, val category: String? = null)

fun main() {
    val pages = listOf(
        Page("Title1", "Category1"),
        Page("Title2", "Category2"),
        Page("Title3"))

    val categories = pages.mapNotNull { it.category }
    println(categories)
}
{{< /code >}}

{{< code title="実行結果" >}}
[Category1, Category2]
{{< /code >}}

