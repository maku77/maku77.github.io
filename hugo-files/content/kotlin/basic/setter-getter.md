---
title: "Kotlinメモ: クラスにプロパティのアクセサメソッドを定義する (set, get)"
url: "p/g2bj9zs/"
date: "2019-05-10"
tags: ["kotlin"]
aliases: [/kotlin/basic/setter-getter.html]
---

カスタムアクセサとは
----

Kotlin では、下記のようにクラスのプロパティを定義するだけで、自動的に setter/getter が生成されます。

```kotlin
data class Book(var title: String)

val b = Book("タイトル1")
b.title = "タイトル2"
```

多くの場合はこれだけで十分ですが、プロパティの値を読み書きする際に何らかの処理を行いたい場合は、プロパティのカスタムアクセサ (getter/setter) を定義する必要があります。


カスタムアクセサを定義する (set() / get())
----

プロパティにアクセスしたときの振る舞いは、**`get`**、**`set`** を実装することでカスタマイズできます。
下記の例では、`title` プロパティに対してカスタム getter、setter を定義しています。

```kotlin
class Book(title: String) {
    var title: String = title
        get() {
            println("get: $field")
            return field
        }
        set(value: String) {
            println("set: $field -> $value")
            field = value
        }
}
```

`print(book.title)` のようにプロパティの値を参照すると `get()` が呼び出され、`book.title = "あああ"` のようにプロパティの値を書き換えようとすると `set(value)` が呼び出されます。

**プロパティとして現在保持されている値は `field` キーワードで参照できます**。
setter の実装の中では、`field` に値を代入することで、プロパティが保持する値を変更します。
setter が呼び出されたときに、先に `field` の値を参照すれば、プロパティの値を変更する前の元の値を取得できます。
例えば、下記のようにすれば、**プロパティの値が実際に変更されるときのみ処理を行う** ということが実現できます。

```kotlin
class Book(title: String) {
    var title: String  = title
        set(value: String) {
            if (field != value) {
                println("値を $field から $value に変更したよ")
                field = value
            }
        }
}

val b = Book("あああ")
b.title = "あああ"  // 何も起こらない
b.title = "いいい"  //=> 値を あああ から いいい に変更したよ
```


setter のみを非公開 (private) にする
----

getter や setter の可視性は、デフォルトではプロパティそのものの可視性と等しくなります。
public なプロパティの getter と setter のうち一方を private にしたい場合、`set`、`get` の前に `private` キーワードを付けます。

次の `Connector` クラスは、`retryCount` プロパティの setter を非公開にしています。
`Connector` クラス内からのみプロパティの値を変更することができます。

```kotlin
class Connector {
    var retryCount: Int = 0
        private set

    fun retry() {
        ++retryCount
    }
}

val conn = Connector()
conn.retry()
conn.retry()
conn.retry()
println(conn.retryCount)  //=> 3
```

次の例では読み出し専用の `isExpensive` プロパティを定義しています。

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

上記の `isExpensive` の実装は、1 つの式の評価結果を `return` しているだけなので、[expression-body](/p/ttacror/#expression-body) の構文を使って、下記のように簡潔に記述することもできます。

```kotlin
val isExpensive: Boolean
    get() = price > 1000
```


派生プロパティ（実体の存在しない getter/setter）
----

プロパティの getter メソッドは、実際に値を保持する変数が存在していなくても定義することができます。
下記の読み取り専用の `fullName` プロパティは、実際に `fullName` 変数で値を保持しているわけではなく、別のプロパティ（`firstName` と `lastName`）を組み合わせた値を返すように実装しています。
getter のみを提供するプロパティは `val` で定義します。

```kotlin
class People(val firstName: String, val lastName: String) {
    val fullName: String
        get() = "$firstName $lastName"
}

fun main() {
    val p = People("Taro", "Yamada")
    println(p.fullName)  //=> Taro Yamada
}
```

下記の `MyTime` クラスは、実体としてはタイムスタンプを保持する `timeStamp` 変数しか持っていませんが、`Date` 型による getter および setter を提供しています。

```kotlin
import java.util.Date

class MyTime(var timeStamp: Long) {
    var date: Date
        get() = Date(timeStamp)
        set(value) {
            timeStamp = value.time
        }
}

fun main() {
    val time = MyTime(System.currentTimeMillis())
    println(time.timeStamp)  //=> 1569419191165
    println(time.date)  //=> Wed Sep 25 13:46:31 UTC 2019
}
```

このように、自分自身の値を保持しないプロパティのことを **派生プロパティ (derived property)** と呼びます。
Kotlin の プロパティは Java のようなフィールドではなく、アクセッサーであり、もっと簡単に言えばただのメソッドです。
とはいえ、本質的にはプロパティの用途はオブジェクトの状態を示すものであり、複雑な処理を伴うものは通常のメソッドとして定義すべきです。
メソッドとして定義したときに `setXxx()`、`getXxx()` と命名できるようなもののみプロパティとして定義する資格があると考えましょう。

