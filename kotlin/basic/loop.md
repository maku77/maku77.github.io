---
title: "for ループと while ループ"
date: "2019-04-24"
---

while ループ
----

Kotlin の `while` ループ、および `do-while` ループは次のような形で使用します。

```kotlin
var i = 0
while (i < 10) {
    print(i++)
}
```

```kotlin
var i = 0
do {
    print(i++)
} while (i < 10)
```

`while` ループは、括弧の中の条件式が真 (true) である限りブロック内のコードを繰り返し実行します。
`do-while` ループも同様ですが、少なくとも一回はブロック内のコードを実行します。

Java と同様の構文なので、迷うことはないと思います。


for ループ
----

Java にはいわゆる C/C++ 形式のループ (`for (int i=0; i<n; ++i)`) と、for-each 形式のループ (`for (int i : arr)`) がありますが、Kotlin には **`for (xxx in yyy)`** の形の for ループしか存在しません。

### 数値範囲のループ（.. と until）

Kotlin では、**`..` 演算子** を使用すると、数値の連番を示すことができます。
これを利用して、ある数字からある数字までのループ処理を下記のように記述することができます。

#### 例: 1～5 のループ

```kotlin
for (i in 1..5) {
    println("Count: $i")  //=> 1 2 3 4 5
}
```

`i in 1..n` というループは、`n` で指定した数字まで処理されることに注意してください。

`..` 演算子を使用する代わりに、**`until`** を使用して同様のループ処理を行うこともできます。
この場合、`until` で指定した数値の 1 つ前の数値まで処理されます。

#### 例: 1～4 のループ

```kotlin
for (i in 1 until 5) {
    println("Count: $i")  //=> 1 2 3 4
}
```

- `i in 1..n` ... **1～n** の範囲でループ処理
- `i in 1 until n` ... **1～n-1** の範囲でループ処理


### 逆向きのループ、ステップごとの増加量（減少量）を指定 (downTo, step)

`until` の代わりに **`downTo`** を使用すると、数値を減少させるループ処理を行うことができます。
さらに、`step` を指定することで、1 ループごとの増加量（減少量）を設定することができます。

#### 例: 10 から 0 まで -2 しながらループ処理

```kotlin
for (i in 10 downTo 0 step 2) {
    println("Count: $i")  //=> 10 8 6 4 2 0
}
```

### 文字 (Ascii code) でループ

数字と同様に、`'A'..'E'` とすれば、A から E までの文字範囲を示すことができます。

```kotlin
for (ch in 'A'..'E') {
    println("Char: $ch")  //=> A B C D E
}
```

コンテナ要素のループ処理
----

`for` は配列やコンテナの要素をループ処理するときにも使用できます。
下記の例ではリストの要素を順番にループ処理しています。

```kotlin
val arr = arrayOf("AAA", "BBB", "CCC")  // listOf(...) でも同様
for (elem in arr) {
    println(elem)
}
```

リストの要素をインデックス付き (0, 1, 2, ...) でループ処理するには、**`withIndex()`** と組み合わせて下記のようにします。

```kotlin
val arr = arrayOf("AAA", "BBB", "CCC")
for ((index, elem) in arr.withIndex()) {
    println("arr[$index] = $elem")
}
```

#### 実行結果
```
arr[0] = AAA
arr[1] = BBB
arr[2] = CCC
```

マップのキーと値も `for` でループ処理できます。

```kotlin
val map = mapOf("foo" to 100, "bar" to 200)
for ((key, value) in map) {
    println("$key => $value")
}
```

