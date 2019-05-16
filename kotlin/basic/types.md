---
title: "基本的な型の一覧"
date: "2019-01-23"
---

Kotlin には、Java のプリミティブ型に相当する `int` や `boolean` といった型は存在せず、すべてがオブジェクトです。

* 数値型
    * **`Long`** -- 64ビット整数
    * **`Int`** -- 32ビット整数
    * **`Short`** -- 16ビット整数
    * **`Byte`** -- 8ビット整数
    * **`Double`** -- 64ビット浮動小数点数
    * **`Float`** -- 32ビット浮動小数点数
* 文字、文字列
    * **`Char`** -- 文字
    * **`String`** -- 文字列
* 論理型
    * **`Boolean`**
* 列挙型
    * **`enum class`** で定義
* 特殊型
    * **`Any`** -- Java の `Object` に相当。すべてのクラスのスーパークラス。正確には Null 許容型のスーパークラスは `Any` ではなく `Any?`
    * **`Unit`** -- Java の `void` に相当。意味を持たない値を表現する。`Any` は `Unit` のスーパークラスでもある。
    * **`Nothing`** -- インスタンスが存在しないことを表現する。ある関数が `Nothing` を返すと定義された場合、その関数から `return` することはないことを示す（必ず例外を投げるとか）。`Nothing` はすべてのクラスのサブクラスである。
    * **`プラットフォーム型`** -- Java で定義された型で `@NonNull` や `@Nullable` といったアノテートがされていないもの。Kotlin 側では `String!` のような `!` 付きの型として表現され、Null 非許容型と Null 許容型のどちらとして扱うかは実装者に委ねられている。

下記は Kotlin で定義した変数が、Java のどの型に対応しているかを示しています。

```kotlin
// Null 非許容型
val i: Int = 1  //=> int
val d: Double = 0.1  //=> double
val f: Float = 0.1  //=> float
val o: Byte = 1  //=> byte
val b: Boolean = true  //=> boolean
val c: Char = 'X'  //=> char
val s: String = "ABC"  //=> String

// Null 許容型
val i_na: Int? = null  //=> Integer
val d_na: Double? = null  //=> Double
val f_na: Float? = null  //=> Float
val o_na: Byte? = null  //=> Byte
val b_na: Boolean? = null  //=> Boolean
val c_na: Char? = null  //=> Character
val s_na: String? = null  //=> String

// コレクション系
val list: List<Int> = listOf(1, 2, 3)  //=> List
val arr: IntArray = intArrayOf(1, 2, 3)  //=> int[]
```

