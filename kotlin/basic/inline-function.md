---
title: "インライン関数の特徴を理解する (inline fun)"
date: "2020-10-26"
tags: ["Kotlin"]
---

Kotlin の関数定義には `fun` キーワードを使用しますが、このとき、__`inline fun`__ と記述すると、インライン関数として定義することができます。
`inline` 修飾子が付けられた関数を呼び出すと、呼び出した位置に関数の内容がインライン展開される形でコンパイルされるため、結果的に次のような効果が得られます。

- 型パラメータを `reified` 指定できる
- 非局所リターン (non-local return) を行えるようになる
- 実行時の高速化を期待できる


型パラメータを reified 指定できる
----

Java (JVM) では、型パラメータを持つ関数（ジェネリクス関数）を定義しても、その型情報を実行時には参照することができません。
なぜなら、型パラメータはコンパイル時の型チェックにのみ使われるものであり、後方互換性のために実行コードからは削除されてしまうからです（イレイジャと呼びます）。
Kotlin では、関数を `inline` 定義し、さらに型パラメータを __`reified`__ 指定 (具体化型パラメータ）することにより、実行時に型情報を参照することができます。

例えば次のようなコードをコンパイル、実行することができます。

```kotlin
// 型パラメータで渡された型の名前を出力する
inline fun <reified T> printType() {
    println(T::class.simpleName)
}

printType<String>() // => "String"
```

これは、関数の内容が次のように呼び出し位置にインライン展開され、型情報がそこに埋め込まれることにより実現されています。

```kotlin
println(String::class.simpleName)
```

つまり、型パラメータの `reified` 指定は、`inline` 関数があってこその機能といえます（実際、`inline` 関数以外で型パラメータを `reified` 指定するとコンパイルエラーになります）。

Kotlin の標準ライブラリ (`kotlin.collections`) が提供する Collection の拡張関数 __`filterIsInstance<T>`__ も同じように `inline` 定義されており、型パラメータで指定した型の要素を抽出することができます。

```kotlin
val list = listOf(1, null, "AAA", 2, "BBB")
println(list.filterIsInstance<String>()) // => "AAA", "BBB"
```

`reified` については、下記の記事でもう少し詳しく説明しています。

- 参考: [ジェネリクス: reified でジェネリクスの型情報を維持する](../generics/reified.html)


非局所リターン (non-local return) を行えるようになる
----

__非局所リターン__ というのは、あるブロックの内側から、外側のブロックを抜ける `return` を行うことです。

次のような、`inline` 指定されていないリピート関数 (`noinlineRepeat`) を考えてみます。

```kotlin
/** [times] 回だけ [actions] を繰り返します */
fun noinlineRepeat(times: Int, action: (Int) -> Unit) {
    for (index in 0 until times) {
        action(index)
    }
}

fun isFavorite(num: Int) = num == 3

fun main() {
    noinlineRepeat(10) {
        println(it)
        if (isFavorite(it)) return  // ERROR
    }
}
```

`noinlineRepeat` のブロックの中で `return` しようとしていますが、Kotlin では、このようなラムダ式の中での単純な `return` は禁止されているためコンパイルエラーになります（ラムダ式は最後に評価した値が結果となる）。
このリピート関数を `inline` 関数として定義すると、非局所リターンが可能になり、ラムダ式の外側（この場合は `main` 関数）を抜けることができるようになります。
下記の例では、Kotlin 標準の `repeat` 関数ほぼそのままのコードを使っています。

```
inline fun repeat(times: Int, action: (Int) -> Unit) {
    for (index in 0 until times) {
        action(index)
    }
}

fun isFavorite(num: Int) = num == 3

fun main() {
    repeat(10) {
        println(it)
        if (isFavorite(it)) return  // main 関数を抜ける
    }
}
```

なぜこのようなことが可能かというと、関数の中身と、ラムダ式の中身がインライン展開され、次のようなコードとして扱われるからです。

```kotlin
fun main() {
    for (index in 0 until 10) {
        println(index)
        if (isFavorite(index)) return
    }
}
```

組み込みの `for` ループはもともと非局所リターンが可能な制御構文なので、上記のように展開されたコードからも非局所リターンが可能ということです。
つまり、インライン関数として定義された `repeat` 関数は、`if` や `for` のような制御構文と同様の性質を持つことになります。

ちなみに、関数パラメータのインライン展開を抑制する __noinline__ 修飾子、非局所リターンを禁止する __crossinline__ 修飾子なども用意されています。
例えば次のようなインライン関数があるとします。

```kotlin
inline fun processUser(
    handleName: (String) -> Unit,
    handleAge: (Int) -> Unit,
) {
    handleName("Maku")
    handleAge(13)
}
```

次のように `handleName` の処理の中で `return` してしまうと、非局所リターンになってしまうので、後続の `handleAge` が呼び出されなくなってしまいます。

```kotlin
fun main() {
    processUser(
        handleName = { name ->
            println(name)
            return  // main から抜けてしまう
        },
        handleAge = { age ->
            println(age)
        }
    )
}
```

必ず後続の処理まで実行したいのであれば、次のように `noinline` 指定してインライン展開を抑制するか、`crossinline` 指定して非局所リターンを禁止します。

```kotlin
inline fun processUser(
    noinline handleName: (String) -> Unit,
    noinline handleAge: (Int) -> Unit,
) {
    handleName("Maku")
    handleAge(13)
}
```


実行時の高速化を期待できる
----

関数を `inline` 定義すると、その関数の内容および、関数型パラメータで渡した関数リテラルがそのまま展開されるため、呼び出し時のコストが減って実行速度が向上します。
特に、次のようなリピート関数では、関数型パラメータの内容が展開されることでパフォーマンスが向上します。

```kotlin
inline fun repeat(times: Int, action: (Int) -> Unit) {
    for (index in 0 until times) {
        action(index)
    }
}
```

逆に `inline` 定義されていないバージョンのリピート関数を呼び出すと、ラムダ式用の一時オブジェクトが生成されるためパフォーマンスが低下します。
関数の `inline` 化によるパフォーマンス向上は、主にその関数が関数型パラメータを持つときにこそ見込めるものであり、単純なスカラ型のパラメータしか持たない関数をインライン化してもパフォーマンス向上はほとんど見込めません。
そのため、次のように関数型パラメータを持たない関数をインライン化しようとすると、

```kotlin
inline fun printNumber(num: Int) {
    println(num)
}
```

Android Studio などの IDE は、次のような警告を出します。

```
Expected performance impact from inlining is insignificant.
Inlining works best for functions with parameters of functional types.
```

関数型パラメータを持たない関数をインライン化しても、パフォーマンスはほとんど向上しないから意味ないよ、という警告ですね。
でも、別に弊害がないのであれば、全部インライン化しておけばいいんじゃない？と思うかもしれません。
もちろんインライン化による弊害はあります。


インライン化による弊害や制約
----

### コードの肥大化に注意

インライン化された関数の中からインライン化された関数を呼び出し、さらにその中からインライン化関数を呼び出し、、、とやっていると、すべてのコードが展開されることになるので、コンパイル後のファイルサイズがすぐに肥大化してしまいます。
簡単に言うと、関数という仕組みを一切使わずにスパゲッティコードを記述しているような状態になります。
関数のインライン化は、その特徴を理解して、効果的かつ、必要最小限に行う必要があります。

### 再帰呼び出しできない

インライン関数は再帰呼び出しできないという制約があります。
無限展開になってしまうので、これはもっともな制約です。
`inline` の付いた関数から自分自身を呼び出そうとすると、`cannot be recursive` といったコンパイルエラーになります。

### 可視性を上げる参照はできない

public なインライン関数の中では private なプロパティや関数を参照できないという制約があります。
クラスのカプセル化の利点が活かせないため、インライン関数は、クラス内の関数定義ではあまり使われないかもしれません。
一方、拡張関数の場合は、もともと public なプロパティにしかアクセスできないという性質があるため、インライン化の対象にしやすいと言えます。


具体的な使い道は？
----

例えば、コレクションの要素を繰り返し処理するような拡張関数を作るときなどが `inline` の使いどころです。
`kotlin.collections` パッケージが提供する拡張関数は、`inline` 関数のよいサンプルになっています。
次の `first` 関数は、ラムダ式で示した条件を満たす最初の要素を検索します。

```kotlin
public inline fun <T> Iterable<T>.first(predicate: (T) -> Boolean): T {
    for (element in this) if (predicate(element)) return element
    throw NoSuchElementException("Collection contains no element matching the predicate.")
}
```

スコープ関数（`let`、`apply`、`also` など）もインライン関数として定義されており、ラムダ式の呼び出しコストがかからないようになっています。
つまり、これらのスコープ関数は、Kotlin の組み込み構文（`if` のブロックなど）と同じ感覚で使えるということです。

```kotlin
public inline fun <T, R> T.let(block: (T) -> R): R {
    return block(this)
}

public inline fun <T> T.apply(block: T.() -> Unit): T {
    block()
    return this
}

public inline fun <T> T.also(block: (T) -> Unit): T {
    block(this)
    return this
}
```

