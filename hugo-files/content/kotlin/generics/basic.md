---
title: "Kotlinメモ: ジェネリクスの基本"
url: "p/yepnwyf/"
date: "2019-12-20"
tags: ["kotlin"]
aliases: ["/kotlin/generics/basic.html"]
---

ジェネリクスとは？
----

ジェネリクス (generics) は、ある型に **型パラメータ (type parameter)** を付加する機能です。
型パラメータを持つクラスや関数を、Generic クラス、Generic 関数のように呼びます。
下記は型パラメータを 1 つ持つ Generic クラスの定義例です。

```kotlin
class Holder<T>(val value: T)
```

型パラメータはこのように `<` と `>` で囲む形で定義され、クラス本体部分の任意の箇所で使用することができます。
型パラメータは、Generic クラスを使用するときに指定した具体的な型に置き換えられます。
このとき指定する型のことを **型引数 (type argument)** と呼びます。
次の例では、型引数として `Int` を指定しています。

```kotlin
val h = Holder<Int>(100)  // Holder(100) と省略可
println(h.value)
```

この場合、`Holder` という Generic クラスから、`Holder<Int>` という型が生成されていることになります。
つまり、理論上は **1 つの Generic クラスは、無限の型を生み出すことができます**。
Kotlin では、「クラス:型 = 1:1」ではなく「クラス:型 = 1:多」の関係にあるということです。
クラスと型は異なる概念です。

Kotlin には型推論 (type inference) の仕組みが備わっているので、多くの場合、型引数の指定を省略することができます。
下記のようなコードは、

```kotlin
val users: List<String> = listOf<String>("User1", "User2")
```

次のように省略できます。

```kotlin
val users = listOf("User1", "User2")
```

Generic クラスや Generic 関数を定義する際、型パラメータは基本的に大文字 1 文字で表現され、慣例として下記のような文字が使用されます。

- **`E`** ... コレクションの要素 (element) の型を表す型パラメータ
- **`K, V`** ... マップ系コレクションのキー (key) と値 (value) の型を表す型パラメータ
- **`T, S`** ... その他の汎用的な型パラメータ

Java では、言語の進化の都合上、Generic クラスのもとになっている原型 (raw type) をそのまま使用することが許されていましたが（例: `List<E>` ではなく `List` を使うことができた）、Kotlin では、Generic クラスを使用する場合は、必ず型引数を指定して使用する必要があります。
特に、空のリストを生成するようなケースでは、初期値から要素の型を推論することができないので、次のいずれかの形で具体的な型引数を指定する必要があります。

```kotlin
val list = mutableListOf<String>()
val list: MutableList<String> = mutableListOf()
```

Generic 関数を定義する
----

型パラメータを持つ関数を定義するときは、**関数名の「前」に「型パラメータ」** を記述します。

```kotlin
fun <T> createPair(a: T, b: T): Pair<T, T> {
    return a to b
}
```

Generic 関数を呼び出すときは、**関数名の「後ろ」に「型引数」** を記述します。
多くの場合は Kotlin コンパイラが型を推論できるので、型引数は省略できます。

```kotlin
val p1 = createPair<Int>(100, 200)
val p1 = createPair(100, 200)  // 省略した場合

val p2 = createPair<String>("Hello", "World")
val p2 = createPair("Hello", "World")  // 省略した場合
```

[拡張関数や拡張プロパティ](/p/du53m3v/)を定義するときも同様です。
次の例では、Generic な `List` クラスを拡張して `lastIndex` プロパティを追加しています（Kotlin の標準ライブラリで提供されています）。

```kotlin
public val <T> List<T>.lastIndex: Int
    get() = this.size - 1
```

この拡張プロパティは、次のように `List` に元々備わっているプロパティのように使用することができます。

```kotlin
val list = listOf("A", "B", "C")
println(list.lastIndex)  //=> 2
```

**Kotlin のコレクション系クラスは Generics を用いた定義が前提**になっているため、`List` の拡張関数を定義する場合は、上記のように必ず型パラメータ（上記の場合は `<T>`）を指定した定義を行うことになります。


Generic クラスを定義する
----

型パラメータを持つクラス（あるいはインタフェース）を定義するときは、**クラス名の後ろ**に型パラメータを記述します。

```kotlin
interface MyList<T> {
    operator fun get(index: Int): T
}
```

あとは、そのクラス本体の実装のどこからでもその型を使用できるようになります。
上記の例では、戻り値の型として型パラメータを使用しています。

### Generic クラスを継承する場合

Generic クラスを親クラスとして継承する場合は、次のいずれかの方法を使って、親クラスの型引数を指定します。

1. 親クラスに対して具体的な型引数を指定してしまう方法
2. 子クラスに新しく定義した型パラメータを親クラスへ伝搬させる方法

1 つ目の方法は、親クラスの型引数に具体的な型を指定してしまう方法で、このように定義した子クラスはもはや Generic クラスではありません。

```kotlin
class MyIntList : MyList<Int> {
    override fun get(index: Int): Int = ...
}
```

2 つ目の方法は、子クラスの方にも型パラメータを持たせ、その型を親クラスの型引数として使ってしまう方法です。
この場合、子クラスも Generic クラスになります。

```kotlin
class MyStack<T> : MyList<T> {
    override fun get(index: Int): T = ...
}
```

型パラメータ制約 (type parameter constraints)
----

例えば、任意のリストを受け取って、その要素の値を合計する関数を作りたいとします。
関数内で要素同士の足し算を行うため、`List<Int>` や `List<Double>` は受け取れるけど、`List<String>` は受け取ることができません。
このような場合は、**型パラメータに制約 (constraint) を付け**て、`Number` のサブクラスを型引数として与えられたリストしか受け付けないようにすることができます。
型パラメータに制約を付けるには、コロン (`:`) を使って、**`T : Number`** のように記述します（Java では `T extends Number` のように記述していました）。

```kotlin
fun <T : Number> sum(list: List<T>): T {
    var result: Double = 0.0
    for (x in list) {
        result += x.toDouble()  // Number#toDouble() を呼び出せる
    }
    return result as T
}
```

結果として、この Generic 関数は、`Number` のサブクラス型（`Int` や `Double`）の要素を持つ `List` しか受け取ることができないため、関数の中で `Number` クラスのメソッドを自由に呼び出して実装を行うことができます。
このような制約 (constraint) を、**上限 (upper bound)** と呼びます。
何も制約を付加しない場合（`<T>` とだけ記述した場合）のデフォルトの上限は `Any?` となり、どんな型の要素を含んだ `List` でも受け取れることになります（`Any?` なので `null` を含んでいてもよい）。
どんな型の要素でもいいけど、`null` だけは受け付けないよ、というのであれば、明示的に `<T : Any>` と宣言します。

型パラメータを、上限 (upper bound) として指定するクラスの型引数として渡すこともできます。
次の例では、型パラメータ `T` の上限として `Comparable<T>` を指定しています。
これは、この関数に渡すオブジェクト `a`、`b` は、少なくとも `Comparable<T>` を実装していなければいけないということです。
結果として、この関数の中で `a` と `b` は、大なり (`>`) や小なり (`<`) を使って比較できるようになります。

```kotlin
fun <T : Comparable<T>> max(a: T, b: T) : T {
    return if (a >= b) a else b
}
```

```kotlin
println(max(10, 20))    //=> 20
println(max("A", "B"))  //=> "B"
println(max(10, "A"))   // Error!
println(max(listOf(), listOf()))  // Error!
```

上記では、関数の型パラメータに制約を与える方法を示しましたが、クラスの型パラメータに関しても同様です。

```kotlin
class TreeNode<T : Vehicle>
```

この `TreeNode` クラスを使用するときは、型引数として、`Vehicle` のサブクラスや、`Vehicle` インタフェースを実装したクラスを指定する必要があります。
`TreeNode` クラスの実装の中では、`Vehicle` インタフェースが持つメソッドを自由に呼び出すことができます。

上限として複数の制約を持たせたい場合は、**`where`** という特殊なキーワードを使用する必要があります。

```kotlin
class TreeNode<T> where T : Vehicle, T : HasWheels
```

このように定義された `TreeNode` を使用する場合、型引数に指定する型として、`Vehicle` インタフェースと `HasWheels` インタフェースの両方を実装した型を指定する必要があります。

