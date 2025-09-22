---
title: "Kotlinメモ: スマートキャストでキャストを自動化する"
url: "p/rk5dgjh/"
date: "2019-04-24"
tags: ["kotlin"]
aliases: [/kotlin/basic/smart-cast.html]
---

[インタフェースを定義する](/p/ep23xid/) でも説明されているように、あるオブジェクトが特定のインタフェースを持っているかを調べるには、`obj is インタフェース名` というチェックを行います。

```kotlin
interface Command {
    fun execute()
}

fun executeIfPossible(obj: Any) {
    if (obj is Command) {
        obj.execute()
    }
}
```

Java では、あるオブジェクトが特定のインタフェースを持っているかを調べた後に、さらにキャストを行わなければ、そのインタフェースのメソッドを呼び出すことはできませんでした。
Kotlin では、上記のように、`is` による判定が true だったときは、その後ろのブロック内では明示的なキャストを省略することができます（型のチェックとキャストが同時に行われる）。
これを**スマートキャスト (smart cast)** と呼びます。

実際には、下記のような `as` を使ったキャストが内部的に自動的に行われていることになります。

{{< code lang="kotlin" title="冗長なキャスト" >}}
if (obj is Command) {
    val cmd = obj as Command  // これは必要ない
    cmd.execute()
}
{{< /code >}}

スマートキャストは、`||` や `&&` を使った際にも適用されます。
下記の 2 つの例はいずれも想定通り動作します。

```kotlin
if (x is String && x.length > 0) {
    // ...
}
```

```kotlin
if (x !is String || x.isEmpty()) {
    return
}
```

スマートキャストは、`when` 式の中でも使用することができます。

```kotlin
fun doTrick(animal: Animal) {
    when (animal) {
        is Bird -> animal.fly()  // Bird のメソッドを呼び出し
        is Dog -> animal.bark()  // Dog のメソッドを呼び出し
        else -> throw IllegalArgumentException("Unknown animal")
    }
}
```

特定の型にしか適用できない演算子を使用する場合にも、スマートキャストを適用できます。
下記の例では、リスト要素の中の `Int` 要素を見つけて、`+` 演算子で足し合わせています。
`Any` 型（Java でいう `Object`）のままでは `+` 演算子は適用できませんが、`Int` へのスマートキャストにより足し合わせることができるようになっています。

```kotlin
fun addAllIntegers(maybeIntegers: List<Any>) : Int {
    var sum = 0
    for (e in maybeIntegers) {
        if (e is Int) {
            sum += e
        }
    }
    return sum
}

fun main() {
    val list = listOf(100, "AAA", 200, "BBB")
    println(addAllIntegers(list))  //=> 300
}
```

