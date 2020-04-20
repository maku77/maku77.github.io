---
title: "for ループと while ループ（そして forEach）"
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

つまり、こうです。

- `i in 1..n` ... **1～n** の範囲でループ処理
- `i in 1 until n` ... **1～n-1** の範囲でループ処理


### 逆向きのループ、ステップごとの増加量（減少量）を指定 (downTo, step)

`until` の代わりに **`downTo`** を使用すると、数値を減少させるループ処理を行うことができます。
さらに、 **`step`** を指定することで、1 ループごとの増加量（減少量）を設定することができます。

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

配列やコレクション要素のループ処理
----

### for ループで要素を列挙する

`for` は配列やコレクションの要素をループ処理するときにも使用できます。
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

ちなみに、上記のように、ループ中に 2 つの変数 (`key`、`value`) に代入しながら処理できるのは、Kotlin の [分解宣言 (destructuring declarations)](../basic/dest-decl.html) の仕組みのおかげです。

### forEach 関数で要素を列挙する

ここまでは、主に `for` を使ったループ処理について説明してきましたが、配列やコレクションのループ処理は **`forEach`** を使うことで簡潔に記述することができます。

```kotlin
val arr = arrayOf("AAA", "BBB", "CCC")
arr.forEach {
    println(it)
}

val map = mapOf("foo" to 100, "bar" to 200)
map.forEach { key, value ->
    println("$key => $value")
}
```


for ループか forEach か
----

Kotlin にはループ構文として `for` ループが用意されていますが、Iterable なオブジェクトでは **`forEach`** という関数を使用することができます。
たとえば、数値のループは次のどちらの方法でも記述できます。

```kotlin
for (i in 0..5) { print(i) }  //=> 012345
(0..5).forEach { print(it) }  //=> 012345
```

大きな違いは、`for` ループが構文として後ろのブロックを繰り返し実行しているのに対し、`forEach` 関数は渡したラムダ式を繰り返し呼び出しているということです。
この違いは、次のように `{ }` の内部で `break` したいときに顕著になります。

```kotlin
for (i in 0..10) {
    print(i)
    if (i == 3) break
}  //=> 0123

(0..10).forEach {
    print(it)
    if (it == 3) return@forEach
}  //=> 012345678910
```

`for` ループの場合は、直感的に `break` でループ処理を中断できますが、`forEach` 関数の方は、そもそもループ構文ではないので `break` が使えません。
苦肉の策で、上記のように `return@forEach` としてもループ処理は止まりません。
なので、このように**ループ内でループ制御を行いたい場合は、`for` ループ構文の方を使うべき**です。

コレクション（リストなど）のループ処理も、`for` と `forEach` どちらでも記述できます。

```kotlin
var list = listOf("AAA", "BBB", "CCC")

for (elem in list) {
    println(elem)
}

list.forEach {
    println(it)
}
```

あまり違いがないように見えますが、次のような理由により、**コレクションのループは `forEach` を使った方がよさそう**です。

* 新しい変数を定義しなくてよい（上記の例では `elem`）
* タイプ数が少ない
* 速度的に有利っぽい（Sequence を扱う場合も同様に高速）
* Iterable なオブジェクトを受け取ってチェイン記述できる

最後のチェインというのは、例えば次のようにリストの要素をフィルタして取り出したい場合に、ドット (`.`) でつなげて簡潔に書けるということです。

```kotlin
var nums = 1..10
nums.filter { it % 2 == 0 }.forEach { println(it) }
```

こういったことをやりたいときに `for` ループを使おうとすると、構文上、分断された形の記述になってしまいます。

```kotlin
var nums = 1..10
var filtered = nums.filter { it % 2 == 0 }
for (x in filtered) {
    println(x)
}
```

