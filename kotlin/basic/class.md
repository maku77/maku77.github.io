---
title: "クラスを定義する (class)"
date: "2019-04-24"
---

単純なデータクラスの定義
----

単純なデータを保持するだけのクラス (value object) は、Kotlin では下記のように **`data`** キーワードを使用して非常にシンプルに定義することができます。

```kotlin
data class Book(var title: String)
```

これだけで、`title` フィールドを持つ `Book` クラスが定義されます。
Kotlin ではフィールドの可視性を省略すると、デフォルトで public になるので、下記のようにフィールドの値は自由に読み書きできます。

```kotlin
var book = Book("Kotlin Bootcamp")
println(book.title)
book.title = "..."
```

Java とは異なり、コンストラクタを呼び出すときの `new` キーワードも必要ないため、コードがとてもシンプルになりあｍす。

上記のようにフィールドにアクセスできるのは、自動的に getter/setter メソッドが定義されて呼び出されているからです。
Kotlin でデータクラスとして定義したクラスを Java から使用する場合、getter/setter メソッドは、`book.getTitle()`、`book.setTitle("...")` のように呼び出します。

データクラスとして定義したクラスには、下記のようなメソッドが自動的に定義されます。

* `hashCode()` メソッド
* `equals()` メソッド
* `clone()` メソッド

このおかげで、データクラスのオブジェクトは、そのままハッシュ系のコンテナクラスに格納することができます。


リードオンリーなフィールドを定義する
----

クラスの定義時に各フィールドを **`val`** キーワードを使って定義すると、そのフィールドはリードオンリーになります（getter のみが定義される）。
逆に **`var`** キーワードを使って定義すると、getter/setter の両方が定義されます。

```kotlin
class Book(val title: String, var price: Int)
val b = Book("Kotlin ABC", 1000)
println("$b.title costs ${b.price} yen")
b.title = "Hoge"  // NG: コンパイルエラー
b.price = 2000  // OK
```


カスタムアクセサを定義する (set() / get())
----

getter メソッドや setter メソッドの内容を明示的に実装したい場合は、下記のように **`get()`** や **`set()`** メソッドを定義します。
この例では読み出し専用の `isExpensive` プロパティを定義しています。

```kotlin
class Book(val title: String, var price: Int) {
    val isExpensive: Boolean
        get() {
            return price > 1000
        }
}

// 使用例
val b = Book("Kotlin ABC", 1500)
println(b.isExpensive)
```

上記の `isExpensive` の実装は、1 つの式の評価結果を `return` しているだけなので、expression-body の構文を使って、下記のように簡潔に記述することもできます。

```kotlin
val isExpensive: Boolean
    get() = price > 1000
```

