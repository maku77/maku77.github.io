---
title: "Kotlinメモ: データクラスを定義する (data class)"
url: "p/fc4unhr/"
date: "2019-05-13"
tags: ["kotlin"]
aliases: [/kotlin/basic/data-class.html]
---

データクラスとは
----

Kotlin で単純なプロパティを保持するだけのクラスは、下記のように簡単に定義することができます。

```kotlin
class Book(val title: String, val price: Int)
```

これだけでも十分便利ですが、さらに **`data`** キーワードを付けることにより、そのクラスは**データクラス**として定義されます。

```kotlin
data class Book(val title: String, val price: Int)
```

データクラスとして定義すると、プライマリ・コンストラクタで定義したフィールドを扱う、下記のようなメソッドが内部で自動生成されます。

- `fun toString() : String` ... オブジェクトの文字列表現を返す
- `fun equals(other: Any?) : Boolean` ... オブジェクトの同一性を判断する
- `fun hashCode() : Int` ... コレクションクラスなどが利用するハッシュ値を返す
- `fun copy(...) : T` ... オブジェクトのコピーを生成する

Java では、これらのメソッドを IDE などの力を借りるなどしてコードの形で実装する必要がありましたが、Kotlin では、クラス定義の先頭に **`data`** キーワードを付加するだけです。
ボイラープレート的なコードを記述する必要がないので、コードをシンプルに保つことができます。

データクラスのオブジェクトは、**そのまま `println` でわかりやすい表現で出力できるようになり、ハッシュ系のコレクションに追加できるようになります**。
下記のサンプルでは、データクラスの `println` が正しく動作していること、`HashSet` への格納が正しくできていることを確認しています。

```kotlin
data class Book(val title: String, val price: Int)

fun main() {
    val b = Book("Kotlin入門", 1000)
    println(b)  //=> Book(title=Kotlin入門, price=1000)

    val set = hashSetOf(
        Book("T1", 100),
        Book("T1", 100), // 同じ内容の本を追加
        Book("T2", 200))
    println(set.size)  //=> 2（内容が同一だという判断がちゃんとできている）
}
```

注意しなければいけないのは、自動生成される実装は、**プライマリ・コンストラクタで定義されたフィールドのみを扱う**ということです。
例えば、

```kotlin
data class Book(val title: String) {
    var price: Int = 0
}
```

このように定義されたクラスは、`equals()` の比較には `title` フィールドの値のみが参照されます。
クラス本体部分で定義されている `price` プロパティの値は無視されます（`price` が異なっても同一とみなされる）。
`equals()` メソッドに `price` フィールドの値まで認識させるには、下記のようにパラメータ部分で `val` キーワードを使ってフィールド定義する必要があります。

```kotlin
data class Book(val title: String, val price: Int = 0)
```

ハッシュ系のコレクションに格納する想定のデータクラスは、すべてのフィールドを `val` キーワードを付けて定義しておく必要があります。
キーとして扱うオブジェクトは immutable（不変）でなければならないからです。
インスタンス生成後に一部のフィールドだけを変更したい場合は、データクラスが提供する `copy()` メソッドを使用できます（後述）。


データクラスによる toString メソッドの実装
----

データクラスによって自動生成される `toString` は、**すべてのフィールドの値を定義順に出力**します。
下記のサンプルは、通常のクラスとして定義した `Book1` クラスと、`data` キーワードを付けて定義したクラス `Book2` クラスのインスタンスをそれぞれ `println` に渡した場合の出力結果の違いを示しています。

```kotlin
class Book1(val title: String, val price: Int)
data class Book2(val title: String, val price: Int)

fun main() {
    val b1 = Book1("Title1", 1000)
    val b2 = Book2("Title2", 2000)
    println(b1)  //=> Book1@60e53b93
    println(b2)  //=> Book2(title=Title2, price=2000)
}
```

通常クラス `Book1` の方の `toString` のデフォルト実装は、**`クラス名@ハッシュ値`** というあまり役に立たない文字列を返していることが分かります。
一方、データクラスとして定義した `Book2` の方の出力は、各フィールドの値までわかりやすく表示されています。
内部的に次のように実装されているのと同様の振る舞いをします。

```kotlin
class Book2(val title: String, val price: Int) {
    override fun toString(): String {
        return "Book2(title=$title, price=$price)"
    }
}
```

この仕組みは、フィールドとしてリストや配列を持っていたり、他のクラスのオブジェクトを含んでいる場合もうまく働きます（深い階層まで処理してくれる）。
次の `Book` クラスは、フィールドとして `String` のリスト、独自クラスのオブジェクトを持っています。

```kotlin
import java.net.URI

data class WebSite(val uri: URI, val title: String)
data class Book(val title: String, val authors: List<String>, val site: WebSite)

fun main() {
    val book = Book("タイトル", listOf("まく", "へむ"),
        WebSite(URI.create("https://example.com/"), "ウェブサイト"))
    println(book)
}
```

{{< code title="実行結果" >}}
Book(title=タイトル, authors=[まく, へむ], site=WebSite(uri=https://example.com/, title=ウェブサイト))
{{< /code >}}


データクラスによる equals メソッドの実装
----

データクラスによって実装される `equals` メソッドは、**プライマリ・コンストラクタで定義されたすべてのフィールドの一致をチェックする**ように実装されます。

まず、**データクラスではない**、通常クラスの `equals` の振る舞いを見てみます。

```kotlin
class Book1(val title: String, val price: Int)

fun main() {
    val a = Book1("Title", 1000)
    val b = Book1("Title", 1000)
    println(a == b)  //=> false
}
```

インスタンス `a` と `b` の内容は同一なのに、`equlas` (`==`) は `false` を返しています。
これは、通常クラスのデフォルト実装が参照の比較を行うからです。

<div class="note">
Java では <code>==</code> は参照の比較を表しますが、Kotlin では <code>equals</code> メソッドの呼び出しを表します。
Kotlin で参照の比較を行いたい場合は、<code>==</code> の代わりに <code>===</code> 演算子を使用してください。
</div>

下記は、`data` プレフィックスを付けてデータクラスとして定義した場合の振る舞いです。

```kotlin
data class Book2(val title: String, val price: Int)

fun main() {
    val a = Book2("Title", 1000)
    val b = Book2("Title", 1000)
    println(a == b)  //=> true
}
```

データクラスの `equals` 実装は、フィールド値を見て同一性を判断するため、インスタンス `a` と `b` の `==` 演算子による比較結果は `true` になります。

内部的に次のように実装されているのと同様の振る舞いをします。

```kotlin
data class Book2(val title: String, val price: Int) {
    override fun equals(other: Any?): Boolean {
        if (other !is Book2) return false
        return (title == other.title) && (price == other.price)
    }
}
```

ちなみに、`equals()` をオーバーライドしたら、`hashCode()` もオーバーライドしなければいけないのは Java と同様です。
Kotlin のデータクラスは `hashCode()` も自動で実装してくれます。


データクラスによる hashCode メソッドの実装
----

データクラスにより実装される `hashCode` は、プライマリ・コンストラクタで定義したフィールドの値がひとつでも異なる場合に、異なるハッシュ値を返すように実装されます。

下記は、データクラス**ではない**、通常クラスのインスタンスを `HashSet` に格納しています。

```kotlin
class Book1(val title: String, val price: Int)

fun main() {
    val a = Book1("Title", 1000)
    val b = Book1("Title", 1000)
    val set = hashSetOf(a)
    println(set.contains(b))  //=> false
}
```

インスタンス `a` と `b` が保持するフィールドの値は同一なのに、`set.contains()` による同一性チェックがうまく動作していません。
これは、デフォルトの `hashCode()` メソッドが異なるハッシュ値を返してしまっているからです。

ハッシュ系のコレクションクラス（`HashSet` や `HashMap`）における要素比較は、効率化のため、まずはハッシュ値を比較して (`hashCode()`)、等しい場合は具体的な内容を比較する (`equals()`) という実装になっています。
このため、基本的なルールとして、2 つのオブジェクトが等しいと見なすケースでは、`equals()` が true を返し、かつ `hashCode()` が同じ値を返すように実装しなければいけません。

クラス定義時に `data` キーワードを付けてデータクラスとして定義すると、プライマリ・コンストラクタで定義したすべてのフィールドの同一性を考慮した `equals()` と `hashCode()` が自動生成されます。
下記の `Book2` クラスはデータクラスとして定義されているので、`HashSet` に格納したときにも正しく動作しています。

```kotlin
data class Book2(val title: String, val price: Int)

fun main() {
    val a = Book2("Title", 1000)
    val b = Book2("Title", 1000)
    val set = hashSetOf(a)
    println(set.contains(b))  //=> true
}
```

仮に、`equals()` と `hashCode()` を自力で実装するとなると、下記のような感じになるでしょう。


```kotlin
class Book2(val title: String, val price: Int) {
    override fun equals(other: Any?): Boolean {
        if (other !is Book2) return false
        return (title == other.title) && (price == other.price)
    }

    override fun hashCode(): Int {
        return title.hashCode() * 31 + price
    }
}
```

Kotlin のデータクラスを使用すれば、このような決まりきったコードを書かなくて済みます。


データクラスによる copy メソッドの実装
----

データクラスが提供する **`copy`** メソッドは、既存のオブジェクトをもとに、指定したフィールドの値のみを変更したオブジェクトを生成します。
この機能は、クラスを immutable（不変）な設計にするのに役立ちます。

すべてのフィールドがリードオンリーである（`val` 定義された）データクラスは、スレッドセーフであったり、ハッシュ系コレクションのキーとして使えたりする利点があります。
一方、インスタンスを生成した後に、特定のフィールドだけを変更したいことがあるとちょっと困ります。

このようなときに便利なのが、データクラスが自動生成する `copy` メソッドです。
`copy` メソッドは下記のような感じの実装を提供し、既存のオブジェクトから指定したプロパティのみを変更した新しいオブジェクトを生成します。

```kotlin
class Book(val title: String, val price: Int) {
    fun copy(title: String = this.title, price: Int = this.price)
        = Book(title, price)
}
```

このメソッドを利用すれば、すべてのフィールドをリードオンリーにしつつ、必要なときには特定のフィールドの値だけ変更する（変更したオブジェクトを作成する）ということを簡単に行えるようになります。
コピー生成のコストはかかりますが、通常はクラスを immutable にすることのメリットの方が大きいでしょう。

下記の例では、データクラスによって自動生成された `copy` メソッドを使って、`price` フィールドの値だけを変更した `Book` オブジェクトを生成しています。

```kotlin
data class Book(val title: String, val price: Int)

fun main() {
    val a = Book("Title", 1000)
    val b = a.copy(price = 2000)  // price フィールドのみ変更する
    println(a)  //=>  Book(title=Title, price=1000)
    println(b)  //=>  Book(title=Title, price=2000)
}
```

あくまで、すべてのオブジェクトは immutable（不変）であるところがポイントです。

