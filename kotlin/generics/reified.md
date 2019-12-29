---
title: "ジェネリクス: reified でジェネリクスの型情報を維持する"
date: "2019-12-27"
---


具体化型パラメータ (reified type parameters)
----

Java の頃から変わっていませんが、ジェネリクスの型引数で指定した型情報は、**コンパイル時にイレイジャ (erasure) によって削除**されます。
つまり、実行時にその型情報を参照することはできません。
これは、JVM の仕組みによるもので、erased at runtime と呼ばれたりします。
Kotlin では、**`reified type parameter`** という機能を使うことで、型引数で指定した型情報を残すことができます。

例えば、次のように、いろいろな型の要素を含んだリストから、指定した型の要素だけを取り出すための Generic 関数 `filterByType()` を作りたいとします。

```kotlin
val list = listOf(1, "A", 2, "B")

val intList = filterByType<Int>(list)
println(intList)  //=> [1, 2]

val strList = filterByType<String>(list)
println(strList)  //=> [A, B]
```

この `filterByType()` 関数は、次のような感じで実装できそうな気がします。

#### 間違ったコード

```kotlin
fun <T> filterByType(list: List<*>) : List<T> {
    val result = mutableListOf<T>()
    list.forEach {
        // ERROR: cannot check for instance of erased type: T
        if (it is T) result.add(it)
    }
    return result
}
```

しかし、ジェネリクスの型情報はイレイジャによってコンパイル時に失われるため、`if (it is T)` という実行時の型判定を行うことができません。
`T` という型情報は、コンパイル時の整合性の確認には活用されますが、実行時には役に立たないということです。

そこで、`reified` の出番です。
Kotlin では、上記の関数を **`inline` で定義し、さらに型パラメータに `reified` キーワードを付ける** ことにより、実行時まで型情報が残され、うまく動作するようになります。

#### 正しいコード

```kotlin
inline fun <reified T> filterByType(list: List<*>) : List<T> {
    val result = mutableListOf<T>()
    list.forEach {
        if (it is T) result.add(it)
    }
    return result
}
```

仕組みとしては、型情報を伴ったコードがその場にインライン展開されるため、型の判定も正しく行えるということのようです。


reified の使用例
----

### Kotlin の filterIsInstance

Kotlin の標準ライブラリでも、下記のような拡張関数が定義されていたりします。

```kotlin
fun <reified R> Array<*>.filterIsInstance(): List<R>
fun <reified R> Iterable<*>.filterIsInstance(): List<R>
```

次のようにすると、リストに含まれる `Int` 要素を抽出することができます。

```kotlin
val list = listOf(1, "A", 2, "B")
val intList = list.filterIsInstance<Int>()
println(intList)  //=> [1, 2]
```

- 参考: [filterIsInstance - Kotlin Programming Language](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/filter-is-instance.html)

### Android の startActivity

reified type parameter として渡した型情報から、直接コンストラクタを呼び出すことはできませんが、その型の `java.lang.Class` オブジェクトは `T::class.java` という形で取得することができます。

下記の `Context` クラスの拡張メソッドは、`Class` オブジェクトを指定する代わりに、型引数で指定したクラス名を使って `Intent` オブジェクトを生成しています。

```kotlin
inline fun <reified T : Activity> Context.startActivity() {
    startActivity(Intent(this, T::class.java))
}
```

次のように、簡潔な形で `Activity` を起動することができます。

```kotlin
startActivity<MyActivity>()
```

