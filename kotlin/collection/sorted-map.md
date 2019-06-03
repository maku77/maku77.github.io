---
title: "マップをソートしてループ処理する (toSortedMap)"
date: "2019-06-03"
---

マップをキーでソートする
----

マップの要素を出力するときに、キー順にソートして出力したいことはよくあると思います。
このような場合、**`toSortedMap`** を使用すると、キーでソートされたマップ (**`SortedMap`**) に変換することができます。

```kotlin
val map = mapOf("CCC" to 3, "AAA" to 1, "BBB" to 2)
for ((k, v) in map.toSortedMap()) {
    println("$k -> $v")
}
```

#### 実行結果

```
AAA -> 1
BBB -> 2
CCC -> 3
```

`forEach` メソッドを使ってループを記述すると、処理の流れが左から右へ一方向になるので、若干可読性が上がるかもしれません。

```kotlin
map.toSortedMap().forEach { k, v ->
    println("$k -> $v")
}
```

**`sortedMapOf`** というファクトリ関数を使って、最初からマップインスタンスを `SortedMap` 型で生成してしまう方法もあります。

```kotlin
val map = sortedMapOf("CCC" to 3, "AAA" to 1, "BBB" to 2)
map.forEach { k, v ->
    println("$k -> $v")
}
```


マップを値でソートする
----

マップ要素の値でソートしたいことがごくたまにあります。
ちょっと工夫が必要なので、いくつか方法を紹介します。

### キー＆バリューのリストに変換してバリューでソートする方法

```kotlin
val map = mapOf("AAA" to 3, "BBB" to 1, "CCC" to 2)
val pairs = map.toList().sortedBy { it.second }
pairs.forEach { (k, v) -> println("$k, $v") }
```

#### 実行結果

```
BBB, 1
CCC, 2
AAA, 3
```

### toSortedMap() にカスタム Comparator を渡す方法

```kotlin
val map = mapOf("AAA" to 3, "BBB" to 1, "CCC" to 2)
val map2 = map.toSortedMap(
    Comparator { k1, k2 ->
        map[k1]!! - map[k2]!!
    }
)
map2.forEach { k, v -> println("$k, $v") }
```

