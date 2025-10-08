---
title: "Kotlinメモ: ある値がコレクションに含まれているか調べる (in)"
url: "/p/4ycigqq/"
date: "2019-04-24"
tags: ["kotlin"]
aliases: ["/kotlin/collection/in.html"]
---

Kotlin のコレクションに、特定の要素が含まれているかを調べるには **`in`** を使用します。
下記の例では、`Set<String>` コレクションの中に特定の文字列 `"banana"` が要素として含まれているかを調べています。

{{< code lang="kotlin" title="Set にある値が含まれているか" >}}
val set = setOf("apple", "banana", "orange")
if ("banana" in set) {
    println("バナナ発見！")
}
{{< /code >}}

上記は `Set` の例ですが、`List` でも `Map` でも同様に調べることができます。

{{< code lang="kotlin" title="List にある値が含まれているか" >}}
val list = listOf("apple", "banana", "orange")
if ("banana" in list) {
    println("バナナ発見！")
}
{{< /code >}}

{{< code lang="kotlin" title="Map にある値が含まれているか" >}}
val map = mapOf("apple" to 1, "banana" to 2, "orange" to 3)
if ("banana" in map) {
    println("バナナ発見！")
}
{{< /code >}}

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

