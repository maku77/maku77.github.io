---
title: "Kotlinメモ: 基本的な型の一覧"
url: "p/hqup7mp/"
date: "2019-01-23"
tags: ["kotlin"]
aliases: [/kotlin/basic/types.html]
---

Kotlin には、Java のプリミティブ型に相当する `int` や `boolean` といった型は存在せず、すべてがオブジェクトです。
例えば、32 ビット整数を扱いたい場合は、一貫して `Int` と記述すればよく、Java のように `int` と `Integer` を使い分ける必要はありません（内部で必要に応じて Java の `int` や `Integer` 相当のものとして扱われます）。

* 数値型
    * **`Byte`** ... 8ビット整数
    * **`Short`** ... 16ビット整数
    * **`Int`** ... 32ビット整数
    * **`Long`** ... 64ビット整数
    * **`Float`** ... 32ビット浮動小数点数
    * **`Double`** ... 64ビット浮動小数点数
* 文字、文字列
    * **`Char`** ... 文字
    * **`String`** ... 文字列
* 論理型
    * **`Boolean`**
* 列挙型
    * **`enum class`** で定義
* 特殊型
    * **`Any`** ... Java の `Object` に相当。すべてのクラスのスーパークラスです（正確には Null 許容型のスーパークラスは `Any` ではなく `Any?`）。
    * **`Unit`** ... Java の `void` に相当。意味を持たない値を表現します。`Any` は `Unit` のスーパークラスでもあります。
    * **`Nothing`** ... インスタンスが存在しないことを表現します。ある関数の戻り値が `Nothing` と定義されている場合、その関数から `return` されることはないことを示しています（内部で無限ループしているとか、必ず例外を投げるとか）。`Nothing` はすべてのクラスのサブクラスであるとされています。
    * **`プラットフォーム型`** ... Java で定義された型で、`@NonNull` や `@Nullable` といったアノテートがされていないものです。Kotlin の言語仕様上は `String!` のような `!` 付きの型として表現され、Null 非許容型 (`String`) と Null 許容型 (`String?`) のどちらとして扱うかは実装者に委ねられています。Kotlin コード上で明示的に `String!` と書くことはできません。

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

