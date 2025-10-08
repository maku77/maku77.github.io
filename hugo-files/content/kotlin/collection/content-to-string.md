---
title: "Kotlinメモ: プリミティブ型配列 (IntArray) の内容を見やすく出力する (contentToString)"
url: "/p/4j2zidk/"
date: "2020-02-26"
tags: ["kotlin"]
aliases: ["/kotlin/collection/content-to-string.html"]
---

配列クラスの contentToString メソッド
----

配列を扱うためのクラスである `Array<T>` や `IntArray` などのインスタンスを、`println()` などで出力しようとすると、JVM 表現で分かりにくい文字列が出力されてしまいます（例: `[I@2c7b84de`）。

このような場合は、Kotlin が拡張関数として用意している **`contentToString()`** メソッドを使用すると、読みやすい形式で配列の内容を出力できます。

{{< code lang="kotlin" title="実装例" >}}
// ジェネリック型配列の場合
val arr1: Array<Int> = arrayOf(1, 2, 3)
println(arr1)
println(arr1.contentToString())

// プリミティブ型配列の場合も同様
val arr2: IntArray = intArrayOf(1, 2, 3)
println(arr2)
println(arr2.contentToString())
{{< /code >}}

{{< code title="実行結果" >}}
[Ljava.lang.Integer;@31befd9f
[1, 2, 3]
[I@2c7b84de
[1, 2, 3]
{{< /code >}}

ちなみに、`contentToString()` 拡張関数の実装は、`java.util.Arrays.toString(this)` を呼び出しているだけです。


List/Set/Map はデフォルトで綺麗に出力される
----

`List` や `Set`、`Map` などのジェネリッククラスは、このようなことをしなくても、デフォルトの `toString()` が見やすい出力をしてくれます（継承元の `AbstractCollection.toString()` などで実装されています）。

```kotlin
val list: List<Int> = listOf(1, 2, 3)
println(list)  //=> [1, 2, 3]

val set: Set<Int> = setOf(1, 2, 3)
println(set)  //=> [1, 2, 3]

val map: Map<String, Int> = mapOf("one" to 1, "two" to 2)
println(map)  //=> {one=1, two=2}
```

