---
title: "文字列リテラルの中で変数や式を展開する（文字列テンプレート）"
date: "2019-04-24"
---

文字列リテラルの中で `"$変数名"` や `"${式}"` のように記述すると、変数の値や式の評価結果を文字列内に展開することができます。

```kotlin
val name = "Maku"
println("Hello, $name!")  //=> Hello, Maku!
```

この仕組みを**文字列テンプレート (string template)** といいます。
`"${式}"` の記述方法を利用すると、文字列テンプレートの中で関数呼び出しなどを行うことができます。

```kotlin
println("You are ${p.age} years old.")  // getAge() の呼び出し
```

ただ、あまり複雑な式を書こうとすると読みにくくなってしまうので、ほどほどに。

```kotlin
fun main(args: Array<String>) {
    println("Hi, ${if (args.isEmpty()) "anonymous" else args[0]}")
}
```

