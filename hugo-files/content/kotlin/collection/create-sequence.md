---
title: "Kotlinメモ: 配列とリストの生成方法まとめ（連番からなる配列やリストを作成する）"
url: "/p/fo685z4/"
date: "2020-02-26"
tags: ["kotlin"]
aliases: ["/kotlin/collection/create-sequence.html"]
---

空の配列やリスト
----

リストや配列のファクトリ関数である `listOf` や `arrayOf` の引数に何も指定しないと、空の配列やリストを作成することができます。

### 空の List/Set/Map を生成

```kotlin
val x = listOf<Int>()                //=> List<Int>
val x = mutableListOf<Int>()         //=> MutableList<Int>

val x = setOf<Int>()                 //=> Set<Int>
val x = mutableSetOf<Int>()          //=> MutableSet<Int>

val x = mapOf<String, Int>()         //=> Map<String, Int>
val x = mutableMapOf<String, Int>()  //=> MutableMap<String, Int>
```

Java の可変配列である `ArrayList` を生成する `arrayListOf()` という関数も存在しますが、通常は `MutableList` の方を使えばいいので、上記の例には含めていません。

### 空の配列を生成

```kotlin
// ジェネリック型配列
val x = arrayOf<Int>()     //=> Array<Int>
val x = arrayOf<Double>()  //=> Array<Double>

// プリミティブ型配列
val x = intArrayOf()       //=> IntArray
val x = doubleArrayOf()    //=> DoubleArray
```

### emptyXxx メソッドを使う

immutable（不変）かつ empty（要素数 0）な配列やリストを、`arrayOf` や `listOf` で個別に作成するのはあまり意味がありません（要素を追加できないのでどのインスタンスも同様のものになってしまう）。
このような場合は、次のような `emptyXxx` 系メソッドを使うと効率がよいです。

```kotlin
val x = emptyArray<Int>()        //=> Array<Int>
val x = emptyList<Int>()         //=> List<Int>
val x = emptySet<Int>()          //=> Set<Int>
val x = emptyMap<String, Int>()  //=> Map<String, Int>
```

これらのメソッドは、Kotlin が定義しているシングルトンインスタンス（`EmptyList` など）を返すので、空のコレクションインスタンスがいくつも作られるのを防ぐことができます。

ちなみに、空のプリミティブ型配列を作る `emptyIntArray()` のような関数はないみたいです（なので `intArrayOf()` で代用）。


各要素の初期値を明示する
----

リストや配列のファクトリ関数の引数には、任意の要素数の初期値を指定することができます。

### 初期値を指定して List/Set/Map を生成

```kotlin
val x: List<Int> = listOf(1, 2, 3)
val x: MutableList<Int> = mutableListOf(1, 2, 3)

val x: Set<Int> = setOf(1, 2, 3)
val x: MutableSet<Int> = mutableSetOf(1, 2, 3)

val x: Map<String, Int> = mapOf("one" to 1, "two" to 2)
val x: MutableMap<String, Int> = mutableMapOf("one" to 1, "two" to 2)
```

### 初期値を指定して配列を生成

```kotlin
val x: Array<Int> = arrayOf(1, 2, 3)
val x: IntArray = intArrayOf(1, 2, 3)
```


サイズだけ指定する（デフォルト値で初期化）
----

プリミティブ型配列のコンストラクタの引数に整数を 1 つだけ指定すると、そのサイズの配列が生成されます。
各要素の値はデフォルト値の 0 で初期化されます（`CharArray` の場合は null 文字を表す `\u0000` になります）。

```kotlin
val x = ByteArray(3)    //=> [0, 0, 0]
val x = ShortArray(3)   //=> [0, 0, 0]
val x = IntArray(3)     //=> [0, 0, 0]
val x = LongArray(3)    //=> [0, 0, 0]
val x = FloatArray(3)   //=> [0.0, 0.0, 0.0]
val x = DoubleArray(3)  //=> [0.0, 0.0, 0.0]
val x = CharArray(3)    //=> ['\u0000', '\u0000', '\u0000']
```

デフォルト値の 0 を使ってすべての要素を初期化する仕組みがあるのは、このようなプリミティブ型配列のみです。
`List<Int>` や `Array<Int>` で、すべての要素を 0 に初期化する場合は、次のようにラムダ式で各要素に  0 を設定してやる必要があります。

```kotlin
val x: List<Int> = List(3) { 0 }    //=> [0, 0, 0]
val x: Array<Int> = Array(3) { 0 }  //=> [0, 0, 0]
```

ちょっと特殊な例として、すべての要素を null で初期化した配列を作るユーティリティ関数として `arrayOfNulls()` というのも用意されています。

```kotlin
val x: Array<Int?> = arrayOfNulls(3)  //=> [null, null, null]
val x = Array<Int?>(3) { null }       //=> 同上
```

ちなみに、`ArrayList` クラスのコンストラクタにも整数値を 1 つ指定することができますが、これは初期キャパシティ (`initialCapacity`) を示すもので、要素数を指定するものではありません（要素数は 0 になります）。


ラムダによる初期化（連番からなる配列やリストを生成する）
----

コレクション系クラスのコンストラクタには、各要素の初期値を設定するためのラムダ式を渡すことができます。
ラムダ式のパラメータ (`it`) で各要素のインデックス番号 (0, 1, 2 ...) を参照できるので、この値を使って連番からなる配列やリストを生成することができます。


```kotlin
// ジェネリック型コレクション
val x: List<Int> = List(3) { it }  //=> [0, 1, 2]
val x: MutableList<Int> = MutableList(3) { it + 1 }  //=> [1, 2, 3]
val x: Array<String> = Array(3) { "ID-${it + 1}" } // => ["ID-1", "ID-2", "ID-3"]

// プリミティブ型配列も同様
val x = IntArray(3) { it * 2 }  //=> [0, 2, 4]
val x = DoubleArray(3) { Math.sqrt(it.toDouble()) }  //=> [0.0, 1.0, 1.414213]
val x = CharArray(3) { 'A' + it }  // => ['A', 'B', 'C']
```

ちなみに、`Set` や `Map` の初期化はこのようなラムダ式では行えないようです（ラムダを受け取るコンストラクタが存在しない）。

- 参考: [プリミティブ型の配列には IntArray や LongArray などの専用クラスを使うことを検討する](/p/az53za7/)

