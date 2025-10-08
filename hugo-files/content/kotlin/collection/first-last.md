---
title: "Kotlinメモ: コレクションの最初・最後の要素を取得する (first, last)"
url: "/p/swvgt89/"
date: "2019-04-26"
tags: ["kotlin"]
aliases: ["/kotlin/collection/first-last.html"]
---

先頭要素を取得する (first)
----

Kotlin で配列やコレクションの先頭要素を取得するには、**`first()`** 拡張関数を使用します。

```kotlin
val array = arrayOf(1, 2, 3)
val list = listOf(1, 2, 3)
val set = setOf(1, 2, 3)

println(array.first())  //=> 1
println(list.first())  //=> 1
println(set.first())  //=> 1
```

配列あるいは Iterable なコレクションでなければいけないので、`Map` オブジェクトでは使用できません。

```kotlin
val map = mapOf("A" to 1)
println(map.first())  // NG
println(map.keys.first())  // これなら OK
```

`first()` のパラメータに条件式を渡すと、**最初にその条件に一致する値**を取り出すことができます。

{{< code lang="kotlin" title="例: 最初の偶数を見つける" >}}
val list = listOf(5, 1, 4, 2, 3)
println(list.first({ it % 2 == 0 }))  //=> 4
{{< /code >}}

