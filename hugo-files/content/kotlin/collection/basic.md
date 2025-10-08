---
title: "Kotlinメモ: コレクションの基本的な使い方 (List, Set, Map)"
url: "/p/by55kee/"
date: "2019-06-03"
tags: ["kotlin"]
aliases: ["/kotlin/collection/basic.html"]
---

リスト (List)
----

Kotlin の `List` は、Java の `List` をベースにしたコレクションです。
一連の要素を格納された順に保持しており、前から順番に要素を取り出すことができます。
不変 (immutable) なリストは、Kotlin 組み込みの **`listOf`** 関数で作成することができます。

```kotlin
val list: List<Int> = listOf(1, 2, 3)
for (x in list) {
    println(x)
}
```

リスト生成後に要素を追加・削除したい場合は、**`mutableListOf`** 関数でリストを作成しておく必要があります。

```kotlin
val list: MutableList<Int> = mutableListOf(1, 2, 3)
list.add(4)
list.add(5)
list.removeAt(0)
list.removeAt(0)
println(list)  //=> [3, 4, 5]
```


セット (Set)
----

Kotlin の `Set` は、Java の `Set` をベースにしたコレクションです。
リストと似ていますが、セットは重複しない要素のみを保持し、順序の概念を持ちません。
不変 (immutable) なセットは、Kotlin 組み込みの **`setOf`** 関数で作成することができます。

```kotlin
val set: Set<Int> = setOf(1, 2, 3, 1, 2)
println(set)  //=> [1, 2, 3]
```

重複する要素は保持しないので、1 と 2 がひとつしか格納されていないところに着目してください。

セット生成後に要素を追加・削除したい場合は、**`mutableSetOf`** 関数でセットを作成しておく必要があります。

```kotlin
val set: MutableSet<Int> = mutableSetOf(1, 2, 3)
set.add(1)
set.add(4)
println(set)  //=> [1, 2, 3, 4]
```

マップ (Map)
----

Kotlin の `Map` は、Java の `Map` をベースにしたコレクションです。
キーと値のペアを保持し、順序の概念はありません。
不変 (immutable) なマップは、Kotlin 組み込みの **`mapOf`** 関数で作成することができます。

```kotlin
val map: Map<String, Int> = mapOf("AAA" to 1, "BBB" to 2, "CCC" to 3)
println(map["AAA"])  //=> 1

// ループ処理（forEach メソッドでも可）
for ((k, v) in map) {
    println("$k -> $v")
}
```

マップ生成後に要素を追加・削除したい場合は、**`mutableMapOf`** 関数でマップを作成しておく必要があります。

```kotlin
val map: MutableMap<String, Int> = mutableMapOf("AAA" to 1, "BBB" to 2, "CCC" to 3)
map["AAA"] = 5
map["DDD"] = 10
for ((k, v) in map) {
    println("$k -> $v")
}
```

この例から分かるように、要素の変更と追加は、どちらも `map[キー] = 値` という記述で実行できます（Kotlin の 内部で `set` メソッドの呼び出しに変換されています）。


immutable と mutable について
----

Kotlin では、コレクション系のクラスを扱うとき、immutable（不変）なインタフェースと mutable（可変）なインタフェースを明示的に使い分けます。
詳しくは下記の記事を参照してください。

* [immutable なコレクションと mutable なコレクション (`List`, `Set`, `Map`)](/p/9557oxs/)

