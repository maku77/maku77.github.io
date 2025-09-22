---
title: "Kotlinメモ: コンパニオンオブジェクトでクラスに静的メソッドを追加する (companion object)"
url: "p/n2jphu2/"
date: "2019-05-14"
tags: ["kotlin"]
aliases: [/kotlin/basic/companion-object.html]
---

コンパニオンオブジェクトの基本
----

Kotlin は言語仕様上、クラスに static なフィールドを持たせることはできませんが、**コンパニオンオブジェクト (companion object)** の仕組みを利用すると、Java の static メソッドと同様な振る舞いを実現できます。
コンパニオンオブジェクトは、クラス本体部分で下記のように `companion object` を使って定義します（無名のコンパニオンオブジェクト）。

```kotlin
data class Book(val title: String, val price: Int) {
    companion object {
        const val FREE_PRICE = 0
        fun newFreeBook(title: String) = Book(title, FREE_PRICE)
    }
}

fun main() {
    val book = Book.newFreeBook("Free Kotlin")
    println(book)  //=> Book(title=Free Kotlin, price=0)
}
```

`companion object` の後ろの `{}` の中は、通常のクラスの本体と同じように実装します（`private` なフィールドを定義することもできます）。
上記のようにコンパニオンオブジェクトを定義すると、`Book` クラスの中に暗黙的なシングルトンインスタンスが生成され、`Book.フィールド名` という形でアクセスできるようになります。
つまり、Java の static フィールドと同じ形でアクセスできます。
正確には Java の static フィールドとは異なり、内部でコンパニオンオブジェクトと呼ばれるオブジェクトが生成されているのですが、通常はあまり気にする必要はないでしょう。

{{% note title="コンパニオンオブジェクトは外側のクラスのインスタンスではない" %}}
コンパニオンオブジェクトはそれ自身がクラス定義を持っており、外側で定義されたクラスのインスタンスではないことに注意してください。
`Book.xxx()` というアクセス方法を見ると、あたかも `Book` クラスのシングルトンインスタンスが作られているかのように見えますが、`Book` インスタンスが作られているわけではありません。

`Book.xxx()` という記述は、実は `Book.Companion.xxx()` の省略記法です。
このことからも、コンパニオンオブジェクトは、`Book` クラスのインスタンスとは別物であることが分かります。
{{% /note %}}


コンパニオンオブジェクトに名前を付ける
----

[オブジェクト宣言でオブジェクトを生成する](/p/wc8f9y8/)場合と同様、コンパニオンオブジェクトにも名前を付けることができます。

下記の例では、`Book` クラスのコンパニオンオブジェクトに、`Factory` という名前を付けています。

```kotlin
data class Book(val title: String, val price: Int) {
    companion object Factory {
        const val FREE_PRICE = 0
        fun freeBook(title: String) = Book(title, FREE_PRICE)
    }
}
```

このコンパニオンオブジェクトには、`Book.Factory.フィールド名` という形でアクセスすることができます。

```kotlin
fun main() {
    val book = Book.Factory.freeBook("Free Kotlin")
    println(book)  //=> Book(title=Free Kotlin, price=0)
}
```

だがしかしっ、この場合でも、`Book.フィールド名` のように省略してアクセスすることが可能です。

```kotlin
val book = Book.freeBook("Free Kotlin")
```

コンパニオンオブジェクトはクラス内でひとつしか定義できません。
このような仕様だと、コンパニオンオブジェクトに名前を付ける意味はあまりないように感じますね。


Java からコンパニオンオブジェクトを参照する
----

Java のコードからコンパニオンオブジェクトにアクセスする場合は、**`Companion`** という名前の static フィールドとして参照します。

{{< code lang="java" title="Java" >}}
Book b = Book.Companion.newFreeBook("Free Kotlin");
{{< /code >}}

もし、コンパニオンオブジェクトに名前を付けているのであれば、`Companion` の代わりにその名前を使用します。
例えば、`Factory` という名前を付けているのであれば次のようにします。

{{< code lang="java" title="Java" >}}
Book b = Book.Factory.newFreeBook("Free Kotlin");
{{< /code >}}

Java のコードから、純粋な static メソッドとして `Book.newFreeBook()` という形で呼び出したい場合は、Kotlin 側でメソッドを定義するときに、**`@JvmStatic`** を付けて定義しておく必要があります。

{{< code lang="kotlin" title="Kotlin" >}}
data class Book(val title: String, val price: Int) {
    companion object Factory {
        const val FREE_PRICE = 0
        @JvmStatic fun newFreeBook(title: String) = Book(title, FREE_PRICE)
    }
}
{{< /code >}}

{{< code lang="java" title="Java" >}}
Book b = Book.newFreeBook("Free Kotlin");
{{< /code >}}


コンパニオンオブジェクトでインタフェースを実装する
----

コンパニオンオブジェクトは、多くの場合は何のインタフェースも実装しないオブジェクト定義ですが、下記のようにして何らかのインタフェースを実装したり、別のクラスを継承したりすることもできます。

```kotlin
interface YamlFactory<T> {
    fun create(yaml: String): T
}

data class Book(val title: String) {
    companion object : YamlFactory<Book> {
        override fun create(yaml: String): Book {
            // 本当は Yaml テキストをパースしてオブジェクトを生成する
            return Book("タイトル")
        }
    }
}
```

上記の `Book` クラスのコンパニオンオブジェクトは `YamlFactory<Book>` を実装しているため、`Book`（あるいは `Book.Companion`）を `YamlFactory<T>` のインスタンスとして使用することができます。

```kotlin
fun <T> createFromYaml(factory: YamlFactory<T>): T {
    val yamlText = "..."
    return factory.create(yamlText)
}

fun main() {
    // Yaml テキストから Book インスタンスを作成する
    val b = createFromYaml(Book)
}
```

コンパニオンオブジェクトに拡張関数を追加する (companion-object extension)
----

[拡張関数 (extension function) の仕組み](/p/du53m3v/) を使用すると、クラス定義の外から、そのクラスのインスタンスメソッドを追加することができます。
コンパニオンオブジェクトに対しても、この仕組みを使ってメソッドを追加することができます。
結果的に、**クラスに後付けで static メソッドを追加するようなこと**ができます。

次の例では、`Foo` のコンパニオンオブジェクト `Foo.Companion` に、後付けで `greet()` 関数を追加しています。
コンパニオンオブジェクトに拡張関数を追加する場合、コンパニオンオブジェクト名の `Companion` を省略できないことに注意してください（省略すると、クラスへのインスタンスメソッドの追加になってしまうため）。

```kotlin
class Foo {
    companion object
}

fun Foo.Companion.greet() = println("Hello")

fun main() {
    Foo.greet()  //=> Hello
}
```

コンパニオンオブジェクトに拡張関数を追加する場合、あらかじめ、そのクラスにコンパニオンオブジェクトが定義されている必要があります（上記のように空でも OK）。

