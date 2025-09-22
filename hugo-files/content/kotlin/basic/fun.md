---
title: "Kotlinメモ: 関数を定義する (fun)"
url: "p/ttacror/"
date: "2019-01-22"
tags: ["kotlin"]
aliases: [/kotlin/basic/fun.html]
---

関数定義の基本
----

Kotlin で関数を定義するには **`fun`** キーワードを使用します。
Java ではすべてのメソッドは何らかのクラスに属す必要がありますが、Kotlin の場合はファイルのトップレベルに関数を定義することができます。


次の例は、2 つの整数 (`Int`) を受け取り、戻り値として整数 (`Int`) を返す関数の定義例です。
パラメータの型はパラメータ名の後ろに記述します。

{{< code lang="kotlin" title="関数の定義" >}}
fun max(a: Int, b: Int): Int {
    return if (a > b) a else b
}
{{< /code >}}

Kotlin では、`if` が式 (expression) 扱いになっているため、上記のように `if` 式の評価結果をそのまま `return` することができます。

関数の呼び出し方法は Java と同様です。

{{< code lang="kotlin" title="関数の呼び出し" >}}
println(max(5, 10))  //=> 10
{{< /code >}}

パラメータや戻り値のない関数は下記のように記述できます。

```kotlin
fun greet() {
    println("Hello")
}
```

戻り値がないときは関数の型は上記のように省略できますが、`: Unit` と明示することもできます（Java の `void` に相当）。


より短く関数を記述する (expression-body funtion) {#expression-body}
----

下記のように `{` と `}` で囲まれた本文を持つ関数を、block body を持つ関数 **(block-body function)** と呼びます。

```kotlin
fun max(a: Int, b: Int): Int {
    return if (a > b) a else b
}
```

本文に 1 つの式 (expression) しか持たない場合、次のように **`=`** を使って短く記述することができます。

```kotlin
fun max(a: Int, b: Int) = if (a > b) a else b
```

このように記述した関数を、expression body を持つ関数 **(expression-body function)** と呼びます。
expression-body function の形で関数を定義する場合、Kotlin コンパイラの型推論 (type inference) 機能が働くため、多くのケースで上記のように関数の戻り値の型を省略することができます。

上記の例では関数が短いので一行で記述していますが、expression-body function であっても複数行にわたって記述することができます。

```kotlin
fun max(a: Int, b: Int) =
    if (a > b) {
        a
    } else {
        b
    }
```

```kotlin
fun getLongCommandName(c: Char) = when (c) {
    'c' -> "create"
    'd' -> "delete"
    'e' -> "edit"
    else -> "unknown"
}
```


null 許容型パラメータ
----

Kotlin の関数のパラメータは、デフォルトでは `null` を渡せないようになっています。
`null` を渡そうとすると、`Null can not be a value of a non-null type String` といったコンパイルエラーが発生します。

パラメータとして `null` を渡すことを許容するには、パラメータの型名に **`?`** をつけて明示する必要があります。

```kotlin
fun greet(name: String?) {
    println("Hello, $name")
}
fun main(args: Array<String>) {
    greet(null)  //=> "Hello, null"
}
```


名前付き引数
----

下記のように 2 つのパラメーター（`title` と `author`）が同じ `String` 型であるとします。

```kotlin
fun showBook(title: String, author: String) {
    println("Title: $title")
    println("Author: $author")
}
```

この関数は次のように呼び出すことができますが、この呼び出しコードを見ただけではどちらのパラメータが `title` なのか `author` なのか判別がつきません。

```kotlin
showBook("Kotlin", "Maku")
```

Kotlin の **名前付き引数** の構文を使用すると、下記のようにパラメータ名とセットで値を渡すことができます。
名前付き引数を使用した場合は、パラメータの指定順序は任意になります。

```kotlin
showBook(title="Kotlin", author="Maku")
showBook(author="Maku", title="Kotlin")
```

{{% note %}}
残念ながら Java で記述されたメソッドに関しては、Kotlin から名前付き引数の仕組みを使って呼び出すことはできません。
Java8 のクラスファイルとしては名前付き引数の仕組みはサポートされているのですが、Kotlin は互換性のために Java6 を採用しているためこのような制約があります（2019年現在）。
{{% /note %}}


デフォルト引数
----

関数のパラメータにデフォルト値を設定しておくと、関数呼び出し時に引数が省略された場合にその値が使用されるようになります。
下記の例では、2 番目のパラメータにデフォルト値 `"UNKNOWN"` を設定しています。

```kotlin
fun showBook(title: String, author: String = "UNKNOWN") {
    println("Title: $title")
    println("Author: $author")
}

fun main() {
    showBook("Kotlin")
}
```

{{< code title="実行結果" >}}
Title: Kotlin
Author: UNKNOWN
{{< /code >}}

また、名前付き引数とデフォルト引数の仕組みを組み合わせて使用することで、任意のパラメータだけを指定して関数を呼び出せるようになります。

```kotlin
fun showBook(title: String = "UNKNOWN", author: String = "UNKNOWN", price: Int = 0) {
    println("Title: $title")
    println("Author: $author")
    println("Price: $price")
}

fun main() {
    showBook(title="Kotlin", price=3000)  // author だけ省略
}
```

