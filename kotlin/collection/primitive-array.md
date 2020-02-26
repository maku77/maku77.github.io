---
title: "プリミティブ型の配列には IntArray や LongArray などの専用クラスを使うことを検討する"
date: "2020-02-25"
---

いつ Int はプリミティブ型として扱われるか？
----

整数値を扱うとき、Java では `Integer` 型と `int` 型を使い分けていました。
一方 Kotlin では、一律で `Int` を使い、必要に応じて内部的にプリミティブ型（Java の `int`）として扱ってくれるようになっています。

この仕組みによって、プログラマは `Int` だけを使っていれば、自動的に効率的なプリミティブ型を使ってくれるようになるので非常にありがたいのですが、問題は、Kotlin がいつ `Int` をプリミティブ型として扱ってくれるのかということです。
下記の表は、Kotlin と Java の整数型の対応を示しています。

| Kotlin の型 | Java の型 |
| ---- | ---- |
| `Int` | `int` |
| `Int?` | `Integer` |
| `List<Int>` | `List<Integer>` |
| `Array<Int>` | `Integer[]` |
| `IntArray` | `int[]` |

簡単にまとめると、`Int?` のように nullable な型として扱う場合と、ジェネリクスクラスの型引数として使った場合は、プリミティブ型ではなく Java の `Integer` として扱われます。


プリミティブ型配列のためのクラス IntArray
----

プリミティブ型の配列を作りたい場合は、Java では `int[]` のように記述できていましたが、Kotlin の場合は **`IntArray`** のような専用の型を使用します。

```kotlin
val intArr1: IntArray = intArrayOf()  // これがプリミティブ型配列
println(intArr1.javaClass.simpleName)  // Java では int[] に相当

val intArr2: Array<Int> = arrayOf<Int>()  // これは Integer な配列
println(intArr2.javaClass.simpleName)  // Java では Integer[] に相当
```

多くのケースではジェネリクスクラスの `List<T>` を使っていれば問題ありませんが、大きなプリミティブ型要素を扱うケースや、パフォーマンスが重要になるケースでは `IntArray` のようなプリミティブ型配列を使うのがよいでしょう。
プリミティブ型配列には、`IntArray` 以外にも次のようなものが用意されています。

| Kotlin の型 | Java の型 |
| `CharArray` | `char[]` |
| `ByteArray` | `byte[]` |
| `ShortArray` | `short[]` |
| `IntArray` | `int[]` |
| `LongArray` | `long[]` |
| `FloatArray` | `float[]` |
| `DoubleArray` | `double[]` |

いずれも、`xxxArrayOf()` という名前のファクトリ関数を使って配列要素を初期化できます。

```kotlin
val charArr = charArrayOf('a', 'b')   // char[]
val byteArr = byteArrayOf(1, 2, 3)    // byte[]
val shortArr = shortArrayOf(1, 2, 3)  // short[]
val intArr = intArrayOf(1, 2, 3)      // int[]
val longArr = longArrayOf(1, 2, 3)    // long[]
val floatArr = floatArrayOf(1.0f, 2.0f, 3.0f)  // float[]
val doubleArr = doubleArrayOf(1.0, 2.0, 3.0)   // double[]
```

- 参考: [配列とリストの生成方法いろいろ（連番からなる配列やリストを作成する）](./create-sequence.html)

