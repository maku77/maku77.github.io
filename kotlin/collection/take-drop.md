---
title: "配列やリストの先頭・末尾の n 要素を取り出す・削除する (take, drop)"
date: "2019-08-05"
---

先頭・末尾からいくつかの要素を取り出す (take)
----

`take` 系のメソッドを使用すると、配列やリストの先頭・末尾から指定した数の要素を取り出すことができます。
どのメソッドも新しいリストを返すため、元のリストは変化しません。

- [take(n)](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/take.html): 先頭から n 個の要素を取り出す
- [takeLast(n)](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/take-last.html): 末尾から n 個の要素を取り出す

```kotlin
val list = listOf(1, 2, 3, 4, 5)  // intArrayOf でも OK
println(list.take(3))      //=> [1, 2, 3]
println(list.takeLast(3))  //=> [3, 4, 5]
```

`takeWhile` 系のメソッドは、条件式を指定することができ、各要素の値が条件を満たす限り取り出します。

- [takeWhile(predicate: (T) -> Boolean)](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/take-while.html): 先頭から条件に一致する限り要素を取り出す
- [takeLastWhile(predicate: (T) -> Boolean)](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/take-last-while.html): 末尾から条件に一致する限り要素を取り出す

```kotlin
val chars = ('a'..'z').toList()
println(chars.takeWhile { it < 'f' })  //=> [a, b, c, d, e]
println(chars.takeLastWhile { it > 'w' })  //=> [x, y, z]
```


先頭・末尾のいくつかの要素を削除する (drop)
----

`drop` 系のメソッドを使用すると、配列やリストの先頭・末尾から指定した数の要素を削除します。
どのメソッドも新しいリストを返すため、元のリストは変化しません。

- [drop(n)](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/drop.html): 先頭から n 個の要素を削除する
- [dropLast(n)](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/drop-last.html): 末尾から n 個の要素を削除する

```kotlin
val list = listOf(1, 2, 3, 4, 5)  // intArrayOf でも OK
println(list.drop(3))      //=> [4, 5]
println(list.dropLast(3))  //=> [1, 2]
```

`dropWhile` 系のメソッドは、条件式を指定することができ、各要素の値が条件を満たす限り削除します。

- [dropWhile(predicate: (T) -> Boolean)](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/drop-while.html): 先頭から条件に一致する限り要素を削除する
- [dropLastWhile(predicate: (T) -> Boolean)](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/drop-last-while.html): 末尾から条件に一致する限り要素を削除する

```kotlin
val chars = ('a'..'z').toList()
println(chars.dropWhile { it < 'x' })     //=> [x, y, z]
println(chars.dropLastWhile { it > 'c' }) //=> [a, b, c]
```

