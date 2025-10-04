---
title: "Kotlinメモ: インデックス演算子を定義して配列のようにアクセス可能なクラスを作る (index operator)"
url: "p/rtajezj/"
date: "2020-01-16"
tags: ["kotlin"]
aliases: /kotlin/operator/operator-index.html
---

インデックス演算子の実体は set と get
----

コレクション系のクラスでは、`[]` を使ったインデックス指定（キー指定）による要素へのアクセスが可能になっていると便利です。
例えば、Kotlin の配列やリストは次のように要素を参照することができます。

```kotlin
val value = arr[index]  // get する場合
arr[index] = value      // set する場合
```

このようなインデックスアクセスを可能にするには、そのクラスに **インデックス演算子 (index operator)** を定義します。
コード上で `[]` によるインデックスアクセスを行うと、内部的には次のように `get()` メソッドや `set()` メソッドが呼び出されます。

| コード上の記述 | こう呼び出される |
| ---- | ---- |
| `value = arr[i]` | `value = arr.get(i)` |
| `arr[i] = value` | `arr.set(i, value)` |

つまり、実際にインデックス演算子として定義すべきメソッドの名前は `get()` や `set()` です。


get 用のインデックス演算子を定義する
----

次の `Pos` クラスでは、インデックス指定によるプロパティ参照を可能にするための `get()` 関数を定義しています。
演算子のオーバーロードなので、関数定義の先頭に **`operator`** キーワードを付けるのを忘れないでください。

```kotlin
data class Pos(val x: Double, val y: Double) {
    operator fun get(index: Int): Double = when(index) {
        0 -> x
        1 -> y
        else -> throw IndexOutOfBoundsException("Invalid index $index")
    }
}

fun main() {
    val pos = Pos(1.5, 2.5)
    println(pos[0])    //=> 1.5
    println(pos[1])    //=> 2.5
    println(pos[2])    //=> Exception
    println(pos["x"])  //=> Compile Error
}
```

上記の例では、`get()` のパラメータを `Int` 型にしましたが、任意の型で定義することができます。
次の例では、`Int` 型の代わりに `String` 型を使用して定義しています。

```kotlin
data class Pos(val x: Double, val y: Double) {
    operator fun get(key: String): Double = when(key) {
        "x" -> x
        "y" -> y
        else -> throw IndexOutOfBoundsException("Invalid index $key")
    }
}

fun main() {
    val pos = Pos(1.5, 2.5)
    println(pos["x"])  //=> 1.5
    println(pos["y"])  //=> 2.5
    println(pos["z"])  //=> Exception
    println(pos[100])  //=> Compile Error
}
```

演算子は拡張関数として追加することもできるので、3rd パーティ製のライブラリが提供しているクラスなどに、後付けする形でインデックス演算子を追加できます。

```kotlin
// このクラスは外部ライブラリとして提供されていて修正できないとする
data class Pos(val x: Double, val y: Double)

// 拡張関数を追加して [] によるインデックスアクセスを可能にする
operator fun Pos.get(index: Int): Double { /* ... */ }
```


set 用のインデックス演算子を定義する
----

値をセット（代入）するためのインデックス演算子 (`set`) も同様に定義することができます。
当然ですが、値を保持するための内部的なフィールドは mutable な型で定義しておく必要があります。
次の `MutablePos` クラスは前述の `Pos` クラスと似ていますが、フィールドの `x`、`y` の値を後から変更できるように、`val` 定義から `var` 定義に変えています。

```kotlin
data class MutablePos(var x: Double, var y: Double) {
    operator fun set(index: Int, value: Double) {
        when (index) {
            0 -> x = value
            1 -> y = value
            else -> throw IndexOutOfBoundsException("Invalid index $index")
        }
    }
}

fun main() {
    val pos = MutablePos(1.5, 2.5)
    pos[0] = 1.2345
    println(pos)  //=> MutablePos(x=1.2345, y=2.5)
}
```


Kotlin の Map と MutableMap の例
----

Java の `Map` オブジェクトから要素を取得するには、`map.get(key)` のように、`get` と明示的に記述する必要がありました。
これに比べ、Kotlin の `Map` では、`map[key]` のような簡潔な記述ができるようになっています。
これは、Kotlin の `Map` インタフェースで、次のようなインデックス演算子が定義されているからです。

```kotlin
public interface Map<K, out V> {
    // ...
    public abstract operator fun get(key: K): V?
}

public inline operator fun <K, V> MutableMap<K, V>.set(key: K, value: V): Unit {
    put(key, value)
}
```


複数のパラメータを取るインデックス演算子を定義する
----

多次元配列的なコレクションクラスを作る場合、インデックス演算子のパラメータ数も複数にすることができます。
例えば、2 次元行列を表現する `Matrix` クラスであれば、各要素を参照するときに、`obj[1, 2]` のように行インデックスと列インデックスを指定してアクセスできます。

```kotlin
class Matrix() {
    operator fun get(row: Int, col: Int): Int {
        // 実際は保持しているフィールドの値を返すように実装する
        return 1000 + row + col
    }
}

fun main() {
    val mat = Matrix()
    println(mat[2, 4])  //=> 1006
}
```

`set` の定義もほぼ同様ですが、最後のパラメータで代入する値を受け取ります。

```kotlin
class MutableMatrix() {
    operator fun set(row: Int, col: Int, value: Int) {
        // 実際は保持しているフィールドの値を変更する
        println("$row 行 $col 列の値を $value に変更します")
    }
}

fun main() {
    val mat = MutableMatrix()
    mat[2, 4] = 100
}
```

