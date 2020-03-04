---
title: "可変長引数をとる関数を定義する (vararg)"
date: "2020-01-16"
---

Kotlin の可変長引数は vararg キーワード
----

Kotlin で可変長引数 (variable-length arguments) を表現するには、**`vararg`** キーワードを使用します。
例えば、`List` を生成するファクトリ関数である `listOf` は次のように定義されています。

```kotlin
fun <T> listOf(vararg elements: T): List<T> {
    // ...
}
```

パラメータに `vararg` が付けられているため、任意の数の引数を次のように渡すことができます。

```kotlin
val list = listOf(1, 2, 3, 4 ,5)
```

ちなみに、Java で可変長引数を定義するときは、3 つのドット (`...`) を使っていました。
Java でも Kotlin でも、関数の実装側では配列と同様に扱うことができます。

```java
// これは Java の例
public void show(Integer... args) {
    for (Integer x: args) { /* ... */ }
}
```


vararg を使った関数を実装する
----

下記は、任意の数の `String` 引数を受け取ることのできる関数の定義例です。
可変長引数として定義したパラメータは、**配列型のオブジェクトとして参照することができる**ので、`forEach` 関数などを使ってループ処理することができます（ここでは `strings` パラメータは `Array<String>` として扱えます）。

```kotlin
fun show(vararg strings: String) {
    strings.forEach {  // 配列のループで処理できる
        println(it)
    }
    if (!strings.isEmpty()) {
        print(strings[0])  // もちろんインデックスアクセスも可能
    }
}
```

呼び出すときは、**0 個以上**の任意の数の引数を渡すことができます。

```kotlin
show()
show("A")
show("A", "B", "C")
```


配列をアンパックして vararg パラメータに渡す（スプレッド演算子）
----

Java の可変長パラメータには配列をそのまま渡すことができましたが、Kotlin の場合は配列をアンパック（展開）してから渡す必要があります。
配列をアンパックするには、**スプレッド演算子 (spread operator)** (`*`) を使用します。

```kotlin
val arr: Array<String> = arrayOf("A", "B", "C")
val list = listOf(*arr)
println(list)  //=> [A, B, C]
```

配列をアンパックせずに、次のようにそのまま `listOf()` 関数に渡してもコンパイルエラーにはなりませんが、この場合は 1 つの配列を要素に持つ `List` オブジェクトが生成されてしまいます。

```kotlin
val list = listOf(arr)
println(list)  //=> [[Ljava.lang.String;@1be6f5c3]
```

可変長引数とスプレッド演算子の組み合わせは強力で、次のように、任意の位置で配列をアンパックすることができます。

```kotlin
val arr: Array<String> = arrayOf("A", "B", "C")
val list = listOf("XXX", *arr, "YYY")
println(list)  //=> [XXX, A, B, C, YYY]
```

