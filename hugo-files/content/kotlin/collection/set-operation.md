---
title: "Kotlinメモ: セットの集合演算を行う"
url: "/p/oc2j8u9/"
date: "2020-01-17"
tags: ["kotlin"]
aliases: ["/kotlin/collection/set-operation.html"]
---

`Set` オブジェクト同士を `+` や `-` などで演算を行うと、和集合や差集合を求めることができます。


和集合 (union)
----

**`+`** 演算子、あるいは **`union()`** メソッドを用いることで、セット同士の和集合（いずれかのセットに含まれている要素からなる集合）を求めることができます。
`union()` は infix 関数として定義されているので、演算子のように用いることができます。

```kotlin
val s1 = setOf(1, 2, 3, 4)
val s2 = setOf(3, 4, 5, 6)

println(s1 + s2)       // [1, 2, 3, 4, 5, 6]
println(s1 union s2)   // 同上
println(s1.union(s2))  // 同上
```

共通集合／積集合 (intersection)
----

**`intersect`** メソッドを用いることで、セット同士の共通集合（両方のセットに含まれている要素からなる集合）を求めることができます。
`intersect()` は infix 関数として定義されているので、演算子のように用いることができます。

```kotlin
val s1 = setOf(1, 2, 3, 4)
val s2 = setOf(3, 4, 5, 6)

println(s1 intersect s2)   // [3, 4]
println(s1.intersect(s2))  // 同上
```

差集合 (difference)
----

**`-`** 演算子、あるいは **`minus()`** メソッド、**`subtract()`** メソッドを用いることで、2 つのセットの差集合（左辺に指定したセットにしか存在しない要素からなる集合）を求めることができます。
`subtract()` は infix 関数として定義されているので、演算子のように用いることができます。

```kotlin
val s1 = setOf(1, 2, 3, 4)
val s2 = setOf(3, 4, 5, 6)

println(s1 - s2)          // [1, 2]
println(s1.minus(s2))     // 同上
println(s1 subtract s2)   // 同上
println(s1.subtract(s2))  // 同上
```

差集合は、左辺と右辺の値を入れ替えると結果が変わってくることに注意してください。

```kotlin
println(s1 - s2)       // [1, 2]
println(s2 - s1)       // [5, 6]
```

