---
title: "Kotlinメモ: 文字列と数値を変換する (toIntOrNull, toInt)"
url: "p/cdxns9p/"
date: "2019-04-24"
tags: ["kotlin"]
aliases: ["/kotlin/numstr/convert.html"]
---

文字列 (String) → 数値 (Int)
----

### `toIntOrNull()`

Kotlin は `String` クラスに **`toIntOrNull()`** 拡張関数を定義しており、これを使うと任意の文字列を数値型 (Int) に変換することができます。
その名の通り、変換できない文字列の場合は `null` を返します。

```kotlin
val num1 = "100".toIntOrNull()  //=> 100
val num2 = "xyz".toIntOrNull()  //=> null
```

`String#toIntOrNull()` のパラメータには基数 (radix) を指定することができるため、下記のように2進数表記や16進数表記の文字列をパースすることもできます。

```kotlin
val num1 = "10000000".toIntOrNull(2)  //=> 128
val num2 = "FFFF".toIntOrNull(16)     //=> 65535
val num3 = "0xFFFF".toIntOrNull(16)   //=> null
```

上記の結果からもわかるように、16進数表記の文字列にプレフィックスとして `0x` が付いていると、`toIntOrNull()` は `null` を返すということに注意してください。

### `toInt()`

似たようなメソッドに **`String#toInt()`** がありますが、こちらはパースに失敗したときに `null` を返すのではなく、`NumberFormatException` を投げます。
必要に応じて使い分けましょう。

```kotlin
try {
    val num = "xyz".toInt()
    println(num)
} catch (ex : NumberFormatException) {
    System.err.println(ex)
}
```


数値 (Int) → 文字列 (String)
----

**`Int.toString(radix)`** を使用すると、数値 (Int) を任意の基数の文字列表現に変換することができます。

```kotlin
val num = 255
println(num.toString(2))   //=> "11111111"
println(num.toString(8))   //=> "377"
println(num.toString(16))  //=> "ff"
```

Java の `Integer` クラスにある `toBinaryString`、`toOctalString`、`toHexString` と同様に使用することができますが、負の値を指定したときには結果が変わってくることに注意してください。

```kotlin
val num = -255
println(num.toString(2))   //=> "-11111111"
println(num.toString(8))   //=> "-377"
println(num.toString(16))  //=> "-ff"
println(Integer.toBinaryString(num))  //=> "11111111111111111111111100000001"
println(Integer.toOctalString(num))   //=> "37777777401"
println(Integer.toHexString(num))     //=> "ffffff01"
```

