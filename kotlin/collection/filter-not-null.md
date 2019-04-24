---
title: "配列やリストから null 以外の要素のみを抽出する (filterNotNull)"
date: "2019-04-24"
---

Kotlin の [filterNotNull 関数](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/filter-not-null.html) を使用すると、`null` 要素の混じった配列やリストから、`null` 以外の要素だけを抽出することができます。

```kotlin
val list1 = listOf(100, null, 200, null, 300)
val list2 = list1.filterNotNull()
println(list1)  //=> [100, null, 200, null, 300]
println(list2)  //=> [100, 200, 300]
```

`filterNotNull` 関数の定義は下記のようになっています。

```kotlin
fun <T : Any> Array<out T?>.filterNotNull(): List<T>
fun <T : Any> Iterable<T?>.filterNotNull(): List<T>
```

戻り値の要素にはもはや `null` は含まれないため、戻り値のリスト要素の型は `T?` ではなく `T` になっていることに注意してください。
最初に挙げた例を、冗長に型付きで記述すると下記のようになります。

```kotlin
val list1: List<Int?> = listOf(100, null, 200, null, 300)
val list2: List<Int> = list1.filterNotNull()
```

`null` を排除した `List<Int>` に変換した後は、`sum()` や `average()` などの計算用関数を思う存分呼び出せます。

```kotlin
fun showValidNumberStats(numbers: List<Int?>) {
    val validNumbers = numbers.filterNotNull()
    println("Sum: ${validNumbers.sum()}")
    println("Avg: ${validNumbers.average()}")
}
```

