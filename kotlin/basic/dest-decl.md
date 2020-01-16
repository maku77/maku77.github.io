---
title: "分解宣言 (destructuring declarations) による Pair 要素や Triple 要素の分解"
date: "2020-01-16"
---

Pair クラスと Triple クラス
----

Kotlin は 2 つの値、あるいは 3 つの値を保持するための簡易クラスとして、`Pair` クラスと `Triple` クラスを用意しています。
`Pair` オブジェクトの 1 番目と 2 番目の要素には、それぞれ `first`、`second` というプロパティでアクセスできます。

```kotlin
val pair = "one" to 1
println(pair.first)   //=> "one"
println(pair.second)  //=> 1
```

`Triple` オブジェクトの要素も同様に、`first`、`second`、`third` というプロパティでアクセスできます。

```kotlin
val triple = Triple("one", "two", "three")
println(triple.first)   //=> "one"
println(triple.second)  //=> "two"
println(triple.third)   //=> "three"
```


分解宣言でオブジェクトの各プロパティを別々の変数に代入する
----

Kotlin の **分解宣言 (destructuring declarations)** と呼ばれている形で変数を定義すると、オブジェクトの各プロパティが保持している値を、別々の変数に一度に代入することができます。
分解宣言の方法は簡単で、代入文の左辺に複数の変数を括弧で囲んで記述するだけです。
次の例では、`Pair` オブジェクトの `first` プロパティと `second` プロパティの値を、それぞれ `x`、`y` という変数に取り出しています。

```kotlin
val pair = 100 to 200  // Pair(100, 200) と同じ

val (x, y) = pair  // 分解宣言で代入
println(x)  //=> 100
println(y)  //=> 200
```

`Triple` オブジェクトに関しても同様です。

```kotlin
val triple = Triple(100, 200, 300)
val (x, y, z) = triple
```


Pair オブジェクトを使って多値関数を実現する
----

関数が `Pair` や `Triple` を戻り値として返す場合も同様に、複数の変数に直接代入してしまうことができます。
これは、あたかも複数の戻り値を返す関数（多値関数）のように振る舞います。

```kotlin
// 簡易的な多値関数
fun getPosition(): Pair<Int, Int> = 100 to 200

// 返された Pair の各プロパティを別々の変数で受け取る
val (x, y) = getPosition()
println(x)  //=> 100
println(y)  //=> 200
```

ただし、このように関数の戻り値を分解宣言で受け取るときは、変数の順序を間違えないように注意する必要があります。
例えば、次のように変数の名前を逆にしてしまうと、見つけにくい不具合になります。

```kotlin
val (y, x) = getPosition()
```

なので、`Pair` オブジェクトを多値関数の実装のために使用するのは、どちらかというとアンチパターンになります。
Kotlin ではデータクラスを簡単に定義することができるので、通常は次のように戻り値を表す型を定義してしまった方が安全です。

```kotlin
data class Position(val x: Int, val y: Int)
fun getPosition(): Position = Position(100, 200)

val pos: Position = getPosition()
val (x, y) = getPosition()
```

最終行では、前述の例と同様に分解宣言による代入を行っていますが、変数の名前を逆転させてしまった場合には、IDE（開発環境）が警告メッセージを表示してくれるので、不具合が入る可能性がグッと下がります。


分解宣言の仕組み
----

### データクラスによる componentN の自動実装

```kotlin
val triple = Triple(100, 200, 300)
val (x, y, z) = triple
```

上記のような分解宣言は、内部的には実は下記のように処理されます（実際にこのように記述しても動作します）。

```kotlin
val triple = Triple(100, 200, 300)
val x = triple.component1()
val y = triple.component2()
val z = triple.component3()
```

このように振舞うためには、`Triple` クラスが `componentN` というメソッドを提供していなければいけないはずですが、`Triple` クラスの定義を見てもそれらしき実装は見当たりません。

```kotlin
public data class Triple<out A, out B, out C>(
    public val first: A,
    public val second: B,
    public val third: C
)
```

実は、Kotlin では、クラスをデータクラスとして定義すると、そのプロパティにアクセスするための `componentN` オペレータ関数を自動的に定義してくれるようになっています。
つまり、データクラスのプロパティはデフォルトで分解宣言による代入が可能です。

分解宣言の代表的な使用例として、コレクション系クラスの `withIndex()` 関数があります。

```kotlin
val arr = arrayOf("one", "two", "three")
for ((index, value) in arr.withIndex()) {
    println("$index -> $value")
}
```

#### 実行結果

```
0 -> one
1 -> two
2 -> three
```

`withIndex()` 関数は、`IndexedValue` というクラスのオブジェクトを順番に返すのですが、このクラスもデータクラスとして定義されているので、そのプロパティ (`index` と `value`) を分解宣言で取り出せるというわけです。

```kotlin
data class IndexedValue<out T>(public val index: Int, public val value: T)
```

### データクラス以外の componentN の実装

データクラス以外のオブジェクトを分解宣言時の右辺に置けるようにするには、`componentN` 系のメソッドを自力で実装する必要があります。

次のコードは `Map` の要素をループ処理する例ですが、ここで `key` と `value` のペアを取り出すときも分解宣言の仕組みが利用されています。

```kotlin
val map = mapOf(1 to "one", 2 to "two")

for ((key, value) in map) {
    println("$key -> $value")
}
```

`Map` および `Map.Entry` は、データクラスではなくインタフェースなので、`componentN` 系のメソッドが実装されておらず、そのままでは分解宣言の右辺には置くことができません。
そこで、Kotlin の標準ライブラリでは、`Map` インタフェースと `Map.Entry` インタフェースを次のように拡張し、分解宣言を利用したループ処理を可能にしています。

```kotlin
inline operator fun <K, V> Map<out K, V>.iterator(): Iterator<Map.Entry<K, V>> = entries.iterator()
inline operator fun <K, V> Map.Entry<K, V>.component1(): K = key
inline operator fun <K, V> Map.Entry<K, V>.component2(): V = value
```

