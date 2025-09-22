---
title: "Kotlinメモ: オブジェクト宣言でシングルトンを作成する (object declaration)"
url: "p/wc8f9y8/"
date: "2019-05-14"
tags: ["kotlin"]
aliases: [/kotlin/basic/object-declarations.html]
description: "object キーワードを使用すると、クラス定義とインスタンスの生成を同時に行うことができます。"
---

オブジェクト宣言によるシングルトン実装
----

Java のシングルトンに相当するものは、Kotlin では**オブジェクト宣言 (object declrations)** を使用して簡単に実装することができます。

```kotlin
object Log {
    fun info(text: String) = println(text)
    fun error(text: String) = System.err.println(text)
}
```

上記の例では、`object Log` という部分で `Log` という名前のオブジェクトを作成しています。
その後ろに続く `{ ... }` のブロックがクラス定義の内容です。
無名のクラス定義があり、そのインスタンスとして `Log` という名前のオブジェクトを生成していると考えるとよいでしょう。

`Log` オブジェクトのメソッドを呼び出すコードは下記のようになります。

```kotlin
fun main() {
    Log.info("Hello")
    Log.error("Something bad happened")
}
```

オブジェクト名が大文字で始まっていて、クラス名のように見えるので、まるで Java の static メソッド呼び出しのように見えますが、実際には `Log` という名前のオブジェクトのメソッド呼び出しているにすぎません。
**Kotlin には `static` フィールドというものは存在しません**（`@JvmStatic` という魔法のキーワードはありますが）。

オブジェクト宣言では、通常のクラス定義と同様に、メソッドやプロパティ、初期化ブロック (`init`) などの定義を行うことができますが、**プライマリ・コンストラクタやセカンダリ・コンストラクタの定義を行うことはできません**（クラス定義とインスタンス化が同時に行われるため、他の場所でコンストラクタを呼び出すタイミングがないからです）。

コンストラクタは定義できないので、下記のようにインスタンスを作成することはできません。

```kotlin
val log = Log()  // NG（インスタンス化はできない）
```

よって、オブジェクト宣言によって作成されたオブジェクトは、シングルトンオブジェクトとして使用することができます。


オブジェクト宣言で継承を行う
----

オブジェクト宣言 (object declaration) では、普通のクラス実装と同様、別のクラスの継承や、インタフェースの実装を行うことができます。
あくまでクラス定義とインスタンス生成を実装を同時に行っているだけで、このあたりの制約はありません（コンストラクタは定義できないので、コンストラクタでパラメータを渡せない点には注意）。

```kotlin
object DefaultListener : MouseAdapter() {
  override fun mouseClicked(e: MouseEvent) {
    // ...
  }

  override fun mouseEntered(e: MouseEvent) {
    // ...
  }
}
```

**ステートを持たないアルゴリズムなどは、オブジェクト宣言によって実装してしまう**のがよいでしょう。
下記の例では、`Book` クラスのオブジェクトを値段で比較するための `Comparator` を実装しています。

```kotlin
data class Book(val title: String, val price: Int)

object BookPriceComparator : Comparator<Book> {
    override fun compare(a: Book, b: Book): Int {
        return a.price - b.price
    }
}

fun main() {
    val books = listOf(Book("A", 300), Book("B", 100), Book("C", 200))
    val sorted = books.sortedWith(BookPriceComparator)
    println(sorted)
}
```

{{< code title="実行結果" >}}
[Book(title=B, price=100), Book(title=C, price=200), Book(title=A, price=300)]
{{< /code >}}

このように、あるクラスに特化した `Comparator` を作成する場合は、次のようにオブジェクト宣言をネストさせてしまった方が分かりやすいかもしれません。

```kotlin
data class Book(val title: String, val price: Int) {
    object PriceComparator : Comparator<Book> {
        // ...
    }
}
```


Java からの参照 (INSTANCE)
----

オブジェクト宣言 (object declaration) によって作成されたオブジェクトを Java のコードから参照したい場合は、**`INSTANCE`** キーワードを使用します。

例えば、下記のようにオブジェクト宣言で定義した `Log` オブジェクトがあるとします。

{{< code lang="kotlin" title="Log.kt" >}}
package com.example.util

object Log {
    fun info(text: String) = println(text)
    fun error(text: String) = System.err.println(text)
}
{{< /code >}}

このシングルトンオブジェクトは、Java からは **`Log.INSTANCE`** という名前で参照できます。

{{< code lang="java" title="JavaMain.java" >}}
import com.example.util.Log;

public class JavaMain {
    public static void main(String... args) {
        Log.INSTANCE.info("Hello");
        Log.INSTANCE.error("World");
    }
}
{{< /code >}}

どうしても、Java から `Log.info(...)` のように static メソッドとして参照したい場合は、Kotlin 側でメソッドを定義するときに **`@JvmStatic`** を付けます。

```kotlin
object Log {
    @JvmStatic fun info(text: String) = println(text)
    @JvmStatic fun error(text: String) = System.err.println(text)
}
```

