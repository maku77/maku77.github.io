---
title: "コレクションから条件に一致する要素のみを取り出す (filter, filterNot, filterKeys, filterValues)"
date: "2019-06-03"
---

リストや配列のフィルタ (filter, filterNot)
----

コレクションの要素から、指定した条件を満たす要素のみを抽出するには、**`filter`** 関数を使用します。
次の例では、数値リストから偶数のみを抽出しています。

```kotlin
val nums = listOf(1, 2, 3, 4, 5)
val evens = nums.filter { it % 2 == 0 }
println(evens)  //=> [2, 4]
```

次の例では、文字列リストから "M" で始まる文字列のみを抽出しています。

```kotlin
val names = listOf("Maku", "Hemu", "Momo")
val ms = names.filter { it.startsWith("M") }
println(ms)  //=> [Maku, Momo]
```

`filter` の代わりに **`filterNot`** を使うと、指定した条件を満たさない要素のみを抽出することができます。

```
// M で始まらない文字列のみを抽出する
val ms = names.filterNot { it.startsWith("M") }
```

コレクション内から要素が null でないものを取り出したいときは、`filterNotNull` や `mapNotNull` を使用すると簡潔に記述できます。

- [配列やリストから null 以外の要素のみを抽出する (filterNotNull, mapNotNull)](./filter-not-null.html)


マップのフィルタ (filterKeys, filterValues)
----

マップの中から条件を満たす要素だけを抽出したい場合は、キーと値のどちらでフィルタするかによって **`filterKeys`** と **`filterValues`** を呼び分けます。

### キーでフィルタする場合 (filterKeys)

次の例では、マップ要素のキーに `Kotlin` という文字列を含んでいる要素を抽出しています。
`filterKeys` の戻り値はマップ型です。

```kotlin
val map = mapOf(
    "Kotlin ABC" to 2000,
    "Effective Java" to 2500,
    "Effective Kotlin" to 1500)

val map2 = map.filterKeys { key -> "Kotlin" in key }
println(map2)  //=> {Kotlin ABC=2000, Effective Kotlin=1500}
```

### 値でフィルタする場合 (filterValues)

次の例では、マップ要素の値が 2000 以上であるものを抽出しています。
`filterValues` の戻り値はマップ型です。

```kotlin
val map = mapOf(
    "Kotlin ABC" to 2000,
    "Effective Java" to 2500,
    "Effective Kotlin" to 1500)

val map2 = map.filterValues { value -> value >= 2000 }
println(map2)  //=> {Kotlin ABC=2000, Effective Java=2500}
```

もちろん、`value` という変数名は、デフォルトのイテレータ変数 `it` に置き換えることができます。

