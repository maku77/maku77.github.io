---
title: "ある値がコレクションに含まれているか調べる (in)"
date: "2019-04-24"
---

あるコレクションに、特定の要素が含まれているかを調べるには **`in`** を使用します。
下記の例では、`Set<String>` コレクションの中に特定の文字列 `"banana"` が要素として含まれているかを調べています。

#### Set にある値が含まれているか

```kotlin
val set = setOf("apple", "banana", "orange")
if ("banana" in set) {
    println("バナナ発見！")
}
```

上記は `Set` の例ですが、`List` でも `Map` でも同様に調べることができます。

#### List にある値が含まれているか

```kotlin
val list = listOf("apple", "banana", "orange")
if ("banana" in list) {
    println("バナナ発見！")
}
```

#### Map にある値が含まれているか

```kotlin
val map = mapOf("apple" to 1, "banana" to 2, "orange" to 3)
if ("banana" in map) {
    println("バナナ発見！")
}
```

ちょっと変わった例として、`..` を使って定義した範囲の中に、ある値が含まれているかを調べるときにも `in` を使うことができます。

```kotlin
val num = 5
if (num in 1..10) {
    println("1～10 の中にあるよ")
}

val ch = 'H'
if (ch in 'A'..'Z') {
    println("A～Z の中にあるよ")
}
```

`in` の否定形の **`!in`** を使うと、配列やコレクションに、**指定した値が含まれていない** ことを調べることができます。

```kotlin
if ("lemon" !in fruits) {
    // レモンが含まれていない場合
}
```

