---
title: "Kotlinメモ: 比較演算子を定義してオブジェクト同士の比較やソートを行えるようにする (equals, compareTo)"
url: "p/ojwakt8/"
date: "2020-01-17"
tags: ["kotlin"]
aliases: /kotlin/operator/operator-compare.html
---

オブジェクトを `==`、`!=`、`>`、`<`、`>=`、`<=` などの比較演算子で大小比較したいときや、ソート系の関数を適用できるようにするには、**`equals`** メソッドや、**`compareTo`** メソッドを定義します。


同値比較のための演算子（==、!=）に対応する (equals)
----

Kotlin ではオブジェクトに対して `==` や `!=` を適用すると、内部的に `equals()` メソッドの呼び出しに変換されます。
`equals()` メソッドのシグネチャは `Any` クラスで次のように定義されています。

#### Any クラスの equals() 定義

```kotlin
public open operator fun equals(other: Any?): Boolean
```

このため、あらゆるクラスのインスタンスは `==` や `!=` による比較を行えるようになっていますが、デフォルトの振る舞いは単なる参照の比較であり、オブジェクトが持つ各フィールドの内容は考慮されません。
標準ライブラリで提供されているクラス（`BigDecimal` など）が、`==` 演算子でうまく比較できるのは、`equals()` メソッドが適切に実装されているからです。

独自クラスを作成するときは、`equals()` メソッドをオーバーライドすることで、`==` 演算子と `!=` 演算子の振る舞いをカスタマイズすることができます。
下記は独自の複素数クラス (`Complex`) で `equals()` メソッドをオーバーライドし、各フィールドの値（実数部と虚数部）を比較するようにしています。

```kotlin
class Complex(val re: Int, val im: Int) {
    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is Complex) return false
        return other.re == re && other.im == im
    }
}

fun main() {
    val c1 = Complex(1, 2)
    val c2 = Complex(1, 2)
    println(c1 == c2) // => true
    println(c1 != c2) // => false
}
```

ここでは、参考のために `equals` メソッドの実装例を示しましたが、Kotlin ではクラス定義時に **`data`** プレフィックスを付けて**データクラスとして定義すると、自動的に `equals` メソッドが実装される**ため、下記のように `equals` メソッドの定義を省略できます。

```kotlin
data class Complex(val re: Int, val im: Int)
```

データクラスのデフォルトの `equals()` 実装では、各フィールドの値が順番に比較されていきます（この例では `re` と `im` が等しいか確認される）。
上記の `Complex` クラスのように、値を保持するだけの単純なクラスであれば、データクラスが提供してくれる `equals()` メソッドの振る舞いをそのまま利用することができます。

### == 演算子と === 演算子の違い

Java では `==` は参照の比較になりますが、Kotlin で参照の比較を行いたい場合は、`===` 演算子（3 つのイコール）の方を使用する必要があります（`==` や `!=` の方は `equals()` 呼び出しになるため）。

```kotlin
val c1 = Complex(1, 2)
val c2 = Complex(1, 2)
println(c1 == c2)   // => true（内容は等しい）
println(c1 === c2)  // => false（参照は異なる）
```

Kotlin では、`==` と `===` による等価性の違いの概念を、次のような用語で呼び分けています。

- Structural equality ... 内容が等しいことを示す（`equals()` メソッドによる比較で true を返すかどうか）
- Referential equality ... 参照が等しいことを示す（2 つの変数が単一のオブジェクトを指し示すかどうか）

`Int` などのプリミティブ型の場合、`==` と `===` の意味は同じになります。

```kotlin
val a: Int = 100
val b: Int = 100
println(a == b)   //=> true
println(a === b)  //=> true
```

参照比較用の `===` 演算子をオーバーロードすることはできません。


大小比較のための演算子（＞、＜、≧、≦）に対応する (compareTo)
----

`Comparable` インタフェース（の `compareTo()` メソッド）を実装したクラスは、そのクラスのインスタンス同士を `>` や `<` などの比較演算子で比較できるようになります。
比較演算子の呼び出しは、Kotlin コンパイラによって `compareTo()` メソッドの呼び出しに置き換えられるため、`Comparable` インタフェースを実装していないクラスのインスタンスを比較しようとするとコンパイルエラーになります。
`Comparable` インタフェースの実装は、コレクションクラスのソート系メソッドでも利用されます。

### compareTo() の実装例

次のコードでは、複素数クラス `Complex` に `Comparable` インタフェースを実装し、オブジェクト同士の大小比較を行えるようにしています。
複素数同士の大小比較はナンセンスですが、ここでは、実数部→虚数部の順で比較することにします。

```kotlin
data class Complex(val re: Int, val im: Int) : Comparable<Complex> {
    override fun compareTo(other: Complex): Int {
        return if (re == other.re) {
            im - other.im
        } else {
            re - other.re
        }
    }
}

fun main() {
    val c1 = Complex(1, 2)
    val c2 = Complex(2, 1)
    val c3 = Complex(1, 3)
    println(c1 < c2)   // => true
    println(c1 > c2)   // => false
    println(c1 <= c3)  // => true
}
```

Kotlin の `Char` クラスや `String` クラスも `Comparable` インタフェースを実装しているので、下記のような大小比較が可能になっています。

```kotlin
println('A' < 'B') // => true
println("AAA" < "BBB") // => true
```

### compareValuesBy() による実装の簡素化

ちなみに、上記の比較関数 (`compareTo()`) では `re` → `im` プロパティの順に値を比較していますが、このような定型処理は下記のように、Kotlin の **`compareValuesBy()`** 関数を使って簡単に記述することができます。

```kotlin
override fun compareTo(other: Complex): Int {
    // this と other を、re フィールド → im フィールドの順に比較する
    return compareValuesBy(this, other, Complex::re, Complex::im)
}
```


コレクション系クラスのソート
----

### sort と sorted

コレクション（`List`、`Set`、`Array` など）に格納された要素が `Comparable` インタフェースを実装していれば、`sort()` メソッドや `sorted()` メソッドを使ってソートすることができます。


```kotlin
data class Complex(val re: Int, val im: Int) : Comparable<Complex> {
    override fun compareTo(other: Complex): Int {
        return compareValuesBy(this, other, Complex::re, Complex::im)
    }
}


fun main() {
    val list = listOf(
        Complex(4, 2),
        Complex(1, 3),
        Complex(2, 5)
    )
    val sorted1 = list.sorted()  // 昇順ソート
    val sorted2 = list.sortedDescending()  // 降順ソート
    println(sorted1)
    println(sorted2)
}
```

{{< code title="実行結果" >}}
[Complex(re=1, im=3), Complex(re=2, im=5), Complex(re=4, im=2)]
[Complex(re=4, im=2), Complex(re=2, im=5), Complex(re=1, im=3)]
{{< /code >}}

`sorted` 系メソッドが上記のように、ソート結果を戻り値で返すのに対し、`sort` 系メソッドは自分自身のリストを書き換えます（リスト生成に `listOf` ではなく `mutableListOf` を使う必要があります）。

```kotlin
fun main() {
    val list = mutableListOf(
        Complex(4, 2),
        Complex(1, 3),
        Complex(2, 5)
    )
    list.sort()  // 昇順ソート（自分自身を書き換え）
    println(list)
    list.sortDescending()  // 降順ソート（自分自身を書き換え）
    println(list)
}
```

### いろんな条件でソートする (sortBy, sortWith)

状況によって異なるルールでソートしたいときは、`sortBy (sortedBy)`、`sortWith (sortedWith)` などのメソッドを使い、パラメータでソートルールを指定します。

特定のフィールドの値でソートしたいときは、`sortBy (sortedBy)` を使うのが簡単です。
フィールドは、[メンバー参照](../basic/member-reference.html) の形で指定できます。

```kotlin
data class Book(val title: String, val price: Int)

fun main() {
    val books = listOf(
        Book("Title3", 1500),
        Book("Title1", 2000),
        Book("Title2", 1000)
    )
    val booksInTitleOrder = books.sortedBy(Book::title)  // title 順にソート
    val booksInPriceOrder = books.sortedBy { it.price }  // price 順にソート
    println(booksInTitleOrder)
    println(booksInPriceOrder)
}
```

複数のフィールドの値を使った、もう少し複雑なソートを行いたい場合は、`sortWith (sortedWith)` メソッドに `Comparator` オブジェクトを渡します。

```kotlin
data class Book(val title: String, val price: Int) {
    companion object {
        /** 価格、タイトルの順でソートするための比較関数 */
        val PRICE_ORDER = Comparator<Book> { b1, b2 ->
            if (b1.price != b2.price) {
                b1.price - b2.price
            } else {
                b1.title.compareTo(b2.title)
            }
        }
    }
}

fun main() {
    val books = listOf(
        Book("Title3", 1500),
        Book("Title1", 2000),
        Book("Title2", 1000)
    )
    val booksInPriceOrder = books.sortedWith(Book.PRICE_ORDER)
    println(booksInPriceOrder)
}
```

Kotlin は、`Comparator` オブジェクトを生成するためのユーティリティメソッド [compareBy](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.comparisons/compare-by.html) を提供しているので、上記の `PRICE_ORDER` オブジェクトは次のように簡単に生成できます。

```kotlin
data class Book(val title: String, val price: Int) {
    companion object {
        /** 価格、タイトルの順でソートするための比較関数 */
        val PRICE_ORDER: Comparator<Book> = compareBy(Book::price, Book::title)
    }
}
```

もちろん、`sortWith (sortedWith)` メソッドを呼び出すときに、動的に `Comparator` オブジェクトを生成することもできます。

```kotlin
val booksInPriceOrder = books.sortedWith(compareBy(Book::price, Book::title))
```

