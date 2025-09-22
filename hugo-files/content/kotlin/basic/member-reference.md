---
title: "Kotlinメモ: メンバ参照、結合メンバ参照を理解する"
url: "p/r2gqqnt/"
date: "2019-06-03"
tags: ["kotlin"]
aliases: [/kotlin/basic/member-reference.html]
---

メンバ参照 (Member references)
----

Kotlin では、**`クラス名::プロパティ名`** あるいは **`クラス名::関数名`** という記述をメンバ参照 (Member reference) と呼び、あるレシーバオブジェクトに対して、どのメンバを参照するかを示すことができます。

`Book` クラスの `price` プロパティを参照するメンバ参照は、

```kotlin
Book::price
```

と記述することができ、これは下記のようなラムダ式と同じ意味を持ちます。

```kotlin
{ b: Book -> b.price }
```

つまり、`Book` オブジェクトをパラメータとして受け取り、その `price` プロパティの値を返す関数です。
メンバ参照を変数に格納しておくと、後から通常の関数のように呼び出すことができます。

```kotlin
data class Book(val title: String, val price: Int)

// price プロパティの値を参照するメンバ参照
val getPrice = Book::price

fun main() {
    val book = Book("Title", 1000)
    println(getPrice(book))  //=> 1000
}
```

メンバ参照は、コレクション系のメソッドに適用できるケースがよくあります。
下記の例では、リストをソートする関数 `sortedBy` に、メンバ参照オブジェクトを渡しています。

```kotlin
// price プロパティの値を参照するメンバ参照
val getPrice = Book::price

val books = listOf(
    Book("Title1", 300),
    Book("Title2", 500),
    Book("Title3", 100))
val sortedBooks = books.sortedBy(getPrice)
```

このように単純なケースでは、次のように直接ラムダ式を渡してもシンプルに記述できます。

```kotlin
books.sortedBy { it.price }
```

ただ、メンバ参照オブジェクトを変数やパラメータ経由で渡すようなケースでは、メンバ参照の記法を使った方が短く記述できます。
型の推論が働かないため、デフォルトのイテレータ変数 (`it`) を使えないからです。

```kotlin
val getPrice = Book::price  // Good
val getPrice = { b: Book -> b.price }  // OK（冗長）
val getPrice = { it.price }  // NG（コンパイルエラー）
```

パラメータを持つインスタンスメソッドをメンバ参照を使って呼び出す場合は、第二引数以降にメソッドのパラメータとして渡す値を指定します。
第一引数では、レシーバオブジェクトを指定しなければいけないからです。

```kotlin
data class Person(val name: String) {
    fun greet(name: String) {
        println("Hello, $name. I am ${this.name}.")
    }
}

val g = Person::greet

fun main() {
    val p = Person("Maku")
    g(p, "Hemu")
}
```


トップレベル関数の参照 (Reference to the Top-level function)
----

トップレベルに定義した関数の参照は、クラス名を省略した形で **`::関数名`** と記述することで取得することができます。
下記の例では、`greet` 関数の参照を、`g` 変数に取得しています。

```kotlin
fun greet(name: String) = println("Hello, $name")

fun main() {
    val g = ::greet
    g("Maku")  //=> "Hello, Maku"
}
```

関数の参照は、ラムダ式を受け取るように設計された関数に対して、定義済みの関数をパラメータとして渡す場合に便利です。
次の例では、`run` で実行する内容として、定義済みの `shout` 関数を渡しています。

```kotlin
fun shout() = println("WRYYY!!!")

fun main() {
    run(::shout)
}
```

`::関数名` という記法は、関数参照を表している（そこでは関数を呼び出さない）ということを覚えておきましょう。


コンストラクタ参照 (Constructor references)
----

**`::クラス名`** という記述は、クラスのコンストラクタへの参照を意味します。
トップレベル関数と同様の記法ですが、このコンストラクタ参照を呼び出すと、クラスのインスタンスを返します。

```kotlin
data class Book(val title: String, val price: Int)

fun main() {
    val createBook = ::Book
    val b = createBook("Title", 1000)
    println(b)
}
```

メンバ参照とは異なり、第一パラメータにレシーバオブジェクトを指定する必要がないことに注意してください（コンストラクタなので当然ですが）。


結合メンバ参照 (Bound member references)
----

クラスのメンバ参照は、`クラス名::メソッド名` のような形で取得しましたが、このクラス名の部分をインスタンス名に置き換えると、**結合メンバ参照 (Bound member reference)** となり、呼び出し時にインスタンスを指定せずに呼び出せるようになります。


```kotlin
class Counter(var count: Int = 0) {
    fun increment() = ++count
}

fun main() {
    val c = Counter()
    val inc = c::increment
    inc()
    inc()
    inc()
    println(c.count)  //=> 3
}
```

ちなみに、このようにインスタンスに結び付けられた参照を **Bound reference** と呼ぶのに対して、`クラス名::メソッド名` のような参照を **Unbound reference** と呼ぶことがあります。

