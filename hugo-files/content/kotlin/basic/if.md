---
title: "Kotlinメモ: if による分岐処理と if 式"
url: "p/4t7x4k4/"
date: "2019-01-22"
tags: ["kotlin"]
aliases: [/kotlin/basic/if.html]
---

Kotlin の `if-else` による分岐処理は、Java と同様の構文で記述することができます。

```kotlin
val n = 100
if (n % 15 == 0) {
    println("FizzBuzz")
} else if (n % 3 == 0) {
    println("Fizz")
} else if (n % 5 == 0) {
    println("Buzz")
} else {
    println(n)
}
```

Java と異なるのは、Kotlin では `if` は文 (statement) ではなく式 (expression) であるという点です。
`if` 式を評価した結果は、そのまま変数に代入したり、関数の戻り値として使用することができます。
Java や C/C++ の三項演算子と同じ感覚で、下記のように使用することができます。

### if 式の評価結果を変数に格納する

```kotlin
val isOk = true
val message = if (isOk) "OK" else "ERROR"
println(message)  //=> "OK"
```

### if 式の評価結果を戻り値として使用する

```kotlin
fun getMessage(isOk: Boolean) = if (isOk) "OK" else "ERROR"
```

