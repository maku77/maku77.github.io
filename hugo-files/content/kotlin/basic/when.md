---
title: "Kotlinメモ: when 式による条件分岐"
url: "p/v2ezcum/"
date: "2019-01-23"
tags: ["kotlin"]
aliases: [/kotlin/basic/when.html]
---

when 式の基本
----

Kotlin の **`when`** 式は、Java の `switch` 文に相当するものです。
Java の `switch` は、列挙型、文字列、数値しか扱えませんが、Kotlin の `when` では任意のオブジェクトで分岐を行うことができます。

ポイントは式 (expression) であるというところで、条件分岐後の評価結果をそのまま変数に代入したり、関数の戻り値として使うことができます。

```kotlin
val n = 1
val str = when(n) {
    1 -> "one"
    2 -> "two"
    else -> "other"
}

println(str)  //=> one
```

Java とは異なり、分岐後の処理は fall through されないので、`break` 文は不要です。
デフォルトで、値が一致した部分のコードのみが実行されるようになっています。

分岐後の処理を複数行にわたって記述したい場合は、次のように矢印 (`->`) の右側を括弧 `{` `}` で囲んでブロックを作ります。
ブロックの中で最後に評価された式の値が、`when` 式全体の評価結果として使われます。

```kotlin
val a = 1
when (a) {
    1 -> {
        println("one")
        exec_one()
    }
    2 -> {
        println("two")
        exec_two()
    }
    else -> {
        println("other")
        exec_other()
    }
}
```


列挙型の値で when 分岐
----

列挙型の値により `when` 分岐させる場合は、すべての項目を網羅するように記述するか、`else` でその他のケースをまとめて処理するように記述する必要があります（どちらかを満たさないとコンパイルエラーになります）。

{{< code lang="kotlin" title="例: Color.WHITE がカバーされていないのでコンパイルエラー" >}}
enum class Color { RED, GREEN, BLUE, WHITE }

fun getRgbStr(color: Color) = when(color) {
    Color.RED -> "#FF0000"
    Color.GREEN -> "#00FF00"
    Color.BLUE -> "#0000FF"
}
{{< /code >}}

複数の値で同じ処理を実行したい場合は、次のようにカンマで値を列挙します。

{{< code lang="kotlin" title="例: 候補値を列挙" >}}
when (color) {
    Color.RED, Color.ORANGE, Color.YELLOW -> "暖色"
    Color.BLUE, Color.AQUA -> "寒色"
    Color.GREEN, Color.PURPLE -> "中性色"
}
{{< /code >}}


if-else 連鎖の代わりに when を使用する
----

`when` 式の矢印の左側に、直接条件式を記述する形で分岐させることもできます。
この場合は、`when` の直後にパラメータを指定しません。

```kotlin
val a = 1
val b = 2

when {
    a == 1 -> println("one")
    (a == 2) || (b == 2) -> println("two")
    (a == 3) && (b == 3) -> println("three")
    else -> println("other")
}
```

これは、連続する `if-else` を短く記述するためのショートカット記法だと考えることができます。
上記の `when` による分岐は、下記のような `if-else` の連鎖と同様です。

```kotlin
if (a == 1) {
    println("one")
} else if ((a == 2) || (b == 2)) {
    println("two")
} else if ((a == 3) && (b == 3)) {
    println("three")
} else {
    println("other")
}
```


in による範囲指定との組み合わせ
----

{{< code lang="kotlin" title="例: 文字コードの範囲" >}}
fun getCharType(c: Char) = when (c) {
    in '0'..'9' -> "Digit"
    in 'a'..'z', in 'A'..'Z' -> "Letter"
    else -> "UNKNOWN"
}

fun main() {
    println(getCharType('X'))  //=> Letter
}
{{< /code >}}

{{< code lang="kotlin" title="例: 値があるセットに含まれているか" >}}
val SEGA = setOf("DreamCast", "Saturn")
val NINTENDO = setOf("Switch", "DS", "NES")

val message = when ("Saturn") {
    in SEGA -> "セガのハードです"
    in NINTENDO -> "任天堂のハードです"
    else -> "何それ？"
}
{{< /code >}}


expression-body function での when 式の利用
----

条件分岐が主な処理となる関数は、[expression-body](/p/ttacror/#expression-body) として `when` 式を使うことで簡潔に記述することができます。

```kotlin
enum class Color { RED, GREEN, BLUE }

fun getRgbStr(color: Color) = when(color) {
    Color.RED -> "#FF0000"
    Color.GREEN -> "#00FF00"
    Color.BLUE -> "#0000FF"
}
```


