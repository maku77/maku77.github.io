---
title: "Kotlinメモ: 列挙型を定義する (enum)"
url: "p/st6xako/"
date: "2019-04-24"
tags: ["kotlin"]
aliases: [/kotlin/basic/enum.html]
---

列挙型の基本 (enum class)
----

Kotlin の列挙型はクラスの一種であり（Java もそうですが）、`class` キーワードの前に **`enum`** を付けることで定義できます。

```kotlin
enum class Fruits {
    APPLE, BANANA, GRAPE
}

fun main() {
    val fruit = Fruits.APPLE
    println(fruit)  //=> APPLE
}
```

`when` 式による分岐で使用するのが典型的な使用例です。

```kotlin
fun getColor(fruit: Fruits) = when (fruit) {
    Fruits.APPLE -> "red"
    Fruits.BANANA -> "yellow"
    Fruits.GRAPE -> "purple"
}

fun main() {
    val f = Fruits.APPLE
    println("$f is ${getColor(f)}")  //=> APPLE is red
}
```

`when` 式の分岐において、すべての列挙値を網羅していない場合は、必ず **`else`** 分岐を含める必要があります（Java の `switch` における `default` のようなもの）。
書き忘れた場合はコンパイルエラーになるのですぐに気づくことができます。

```kotlin
fun getColor(fruit: Fruits) = when (fruit) {
    Fruits.APPLE -> "red"
    else -> throw Exception("Unknown fruit")
}
```


列挙型にプロパティやメソッドを追加する
----

列挙型もクラスの一種なので、プロパティやメソッドを持つことができます（これも Java と同様です）。

```kotlin
enum class Fruits(val label: String, val color: String) {
    APPLE("リンゴ", "赤色"),
    BANANA("バナナ", "黄色"),
    GRAPE("ぶどう", "紫色");  // Kotlin で唯一セミコロンが必要な場所

    override fun toString(): String {
        return "$label は $color です"
    }
}

fun main() {
    val f = Fruits.APPLE
    println(f.label)  //=> "リンゴ"
    println(f.color)  //=> "赤色"
    println(f)  //=> "リンゴ は 赤色 です"
}
```


列挙型を by name でインポートする
----

下記のように特定のパッケージに定義された列挙型があるとします。

{{< code lang="kotlin" title="color.kt" >}}
package com.example

enum class Color {
    RED, YELLOW, GREEN, BLUE;
}
{{< /code >}}

この列挙型の値は、基本的には `Color.RED` のように参照するのですが、インポートするときに `com.example.Color.RED` のように名前まで指定してインポートすることで、`RED` と省略して参照できるようになります。
下記の例では、ワイルドカード `*` を使って、列挙型の名前をすべてインポートしています。

```kotlin
import com.example.Color
import com.example.Color.*

fun showImpression(color: Color) {
    when (color) {
        BLUE, GREEN -> println("寒そうな色ですね")
        RED, YELLOW -> println("暖かそうな色ですね")
    }
}

fun main() {
    val f = RED
    showImpression(f)  //=> 暖かそうな色ですね
}
```

