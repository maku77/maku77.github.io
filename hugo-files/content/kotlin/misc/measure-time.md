---
title: "Kotlinメモ: ある処理にかかった時間を計測する (measureTimeMillis/Micros)"
url: "p/zmr4tpu/"
date: "2019-09-02"
tags: ["kotlin"]
aliases: ["/kotlin/misc/measure-time.html"]
---

下記の Kotlin の組み込み関数を使用すると、ブロックで渡した一連の処理にかかった時間を計測することができます。

- [`measureTimeMillis()`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.system/measure-time-millis.html) - ブロックで渡した処理にかかる時間を「ミリ秒」単位で計測
- [`measureTimeMicros()`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.system/measure-time-micros.html) - ブロックで渡した処理にかかる時間を「マイクロ秒」単位で計測

```kotlin
import kotlin.system.measureTimeMillis

val time = measureTimeMillis {
    val one = doSomething1()
    val two = doSomething2()
    println("The answer is ${one + two}")
}

println("Completed in $time ms")
```

計測範囲が分かりやすくてよいですね！

