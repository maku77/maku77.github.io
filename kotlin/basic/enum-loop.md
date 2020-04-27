---
title: "列挙型 (enum) の値をループ処理する (values)"
date: "2020-04-27"
---

列挙型 (enum) クラスに自動的に定義される __`values()`__ メソッドを使用すると、全ての値を含む配列 (__`Array<列挙型>`__) を取得することができます。
この配列を for-in ループで処理すれば、列挙型の値を 1 つずつ取り出せます。

```kotlin
enum class Fruits {
    APPLE, BANANA, GRAPE
}

fun main() {
    for (x: Fruits in Fruits.values()) {
        println(x)
    }
}
```

次のように `forEach` でループ処理することもできますね。

```kotlin
Fruits.values().forEach { println(it) }
Fruits.values().forEach(::println)
```

