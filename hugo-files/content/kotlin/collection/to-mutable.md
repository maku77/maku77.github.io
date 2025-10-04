---
title: "Kotlinメモ: immutable なコレクションを mutable に変換する (toMutableList, toMutableSet, toMutableMap)"
url: "p/r5f583b/"
date: "2019-09-17"
tags: ["kotlin"]
aliases: ["/kotlin/collection/to-mutable.html"]
---

Kotlin の `List` インタフェースや `Map` インタフェースは immutable（不変）なインタフェースとして定義されており、`add` や `set` といった要素を変更するメソッドが定義されていません。

toMutableList
----

`listOf()` などで生成した immutable（不変）なリストから mutable なリストを生成するには、**`toMutableList()`** メソッドを使用します。

```kotlin
val list1 = listOf(1, 2, 3)
val list2 = list1.toMutableList()
list2.add(4)  // list2 += 4 でも OK

println(list1)  //=> [1, 2, 3]
println(list2)  //=> [1, 2, 3, 4]
```

`toMutableList()` はリストのコピーを作成するため、元の `list1` 変数が参照しているリストの内容は変更されていないことに注意してください。


toMutableSet / toMutableMap
----

`toMutableList()` と同様に、セット用には **`toMutableSet()`**、マップ用には **`toMutableMap()`** が用意されています。
下記は、`setOf()` で作成した immutable（不変）なセットを mutable なセットに変換する例です。

```kotlin
val set1 = setOf(1, 2, 3)
val set2 = set1.toMutableSet()
set2.add(4)  // set2 += 4 でも OK

println(set1)  //=> [1, 2, 3]
println(set2)  //=> [1, 2, 3, 4]
```

使用するメソッドが `toMutableList()` から `toMutableSet()` に変わっただけです。

マップの場合も同様です。

```kotlin
val map1 = mapOf("one" to 1, "two" to 2, "three" to 3)
val map2 = map1.toMutableMap()
map2.put("four", 4)  // map2 += "four" to 4 でも OK

println(map1)  //=> {one=1, two=2, three=3}
println(map2)  //=> {one=1, two=2, three=3, four=4}
```

要素の追加に使用するメソッドが `add()` から `put()` に変わるくらいです。


関連記事
----

- [Kotlinメモ: クラス内の `MutableList` を immutable な `List` にして公開する](/p/9fwrpnu/)

