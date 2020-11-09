---
title: "インラインクラスでプリミティブ型の型安全性を確保する (inline class)"
date: "2020-11-04"
tags: ["Kotlin"]
---

インラインクラスの基本
----

Kotlin のインラインクラスを使用すると、パフォーマンスに悪影響のないプリミティブ型ラッパークラスを作成することができます。
プライマリコンストラクタで 1 つの値（プロパティ）のみを受け取るクラスは、次のように __`inline`__ キーワードを付けてインラインクラスにすることができます。

```kotlin
inline class Name(private val name: String)
```

このインラインクラスは、通常のクラスと同様に次のように使用します。

```kotlin
val name: Name = Name("Maku")
```

違いはコンパイル後のバイトコードに現れます。
上記のコードはコンパイル時にインライン展開され、次のようなコードを記述したのと同様に扱われます。

```kotlin
// コンパイル後のコード
val name: String = "Maku"
```

つまり、コンパイル時は具体的な型（`Name` 型）で型チェックを行いつつも、実行時にはプリミティブ型になっているのでパフォーマンスが悪化しない、といったことが実現できます。

現状、インラインクラスは、init ブロックを持てない、[バッキングフィールド](https://kotlinlang.org/docs/reference/properties.html#backing-fields)を持てない、といった制約がありますが、単純な getter プロパティや関数を持つことはできます。

```kotlin
inline class Name(private val name: String) {
    val length: Int
        get() = name.length

    fun greet() {
        println("Hello, $name")
    }
}

val name: Name = Name("Maku")  //=> val name: String = "Maku"
name.greet()                   //=> name.greet() ???
```

上記の `name` 変数はコンパイル時に `String` オブジェクトに置き換えられるので、`name.greet()` の部分でエラーになりそうですが、コンパイラがうまいこと静的関数を生成して問題なく動作するようにしてくれます。
上記のコードは次のようなコードにコンパイルされます。

```kotlin
val name: String = "Maku"
Name.`greet-impl`(name)
```


インラインクラスの用途
----

### 同一のプリミティブ型を区別したいとき

同じ型のパラメータを複数取る関数を作ると、引数の指定方法を間違える不具合が入りやすくなります。
例えば、次の `searchBooks` 関数は 2 つの `Int` 値を受け取ります。

```kotlin
fun searchBooks(authorId: Int, genreId: Int): Array<Book> {
    // ...
}
```

2 つのパラメーターは両方とも `Int` 型なので、次のように順序を間違えて呼び出してしまってもコンパイルエラーになってくれません。
実行時にもすぐには分からず、潜在的な不具合を埋め込んでしまう可能性があります。

```kotlin
val authorId: Int = getAuthorId()
val genreId: Int = getGenreId()
val books = searchBooks(genreId, authorId)  // 間違い！
```

次のように名前付き引数の構文を使えば、コード上は分かりやすくなりますが、相変わらず型情報は同じ `Int` なので、型安全性が高いとは言えません。

```kotlin
val books = searchBooks(authorId=authorId, genreId=genreId)
```

そこで、次のようにインラインクラスとして `AuthorId` 型と `GenreId` 型を定義します。

```kotlin
inline class AuthorId(private val authorId: Int)
inline class GenreId(private val genreId: Int)

fun searchBooks(authorId: AuthorId, genreId: GenreId): Array<Book> {
    // ...
}
```

こうすれば、それぞれの ID が異なる型として定義されるので、引数の指定ミスはコンパイル時に確実に発見できるようになります。

```kotlin
val authorId: AuthorId = ...
val genreId: GenreId = ...

val books1 = searchBooks(authorId, genreId)  // OK
val books2 = searchBooks(genreId, authorId)  // Error!
```

しかも、上記のインラインクラスは実際には `Int` 型として扱われるため、単純なラッパークラスとは違って Unboxing（`AuthorId` から `Int` への変換）のオーバーヘッドがかかりません。
つまり、速度を犠牲にせずに、型安全性を確保できます。


### 単位の違いを表現したいとき

長さ（m/cm/mm）、時間（時/分/秒）、ファイルサイズ（mb/kb/byte）などの単位をともなう情報は、多くの場合は `Int` や `Long` などのプリミティブ型で表現されます。
このような場合、単位を表現するラッパークラスを作成すると、単位の間違いによる不具合を防ぐことができます。

まずは、すべてプリミティブの `Long` 型だけで済ませた例から見てみます。
次のコードは、時間を表す `time` が、「秒」と「ミリ秒」の 2 つの意味で使われてしまっています（不具合です）。

```kotlin
import java.util.concurrent.TimeUnit

// ラピュタ崩壊までの残り時間を取得
fun getRemainingTime(): Long {
    return 100  // 100秒のつもり
}

// ラピュタ崩壊までのカウントダウンを開始する
fun startTimer(time: Long) {
    TimeUnit.MILLISECONDS.sleep(time)  // time を「ミリ秒」として扱う
    println("バルス！")
}

fun main() {
    val time: Long = getRemainingTime()
    startTimer(time)
}
```

`getRemainingTime()` が返す値は「100秒」を表現しているつもりですが、この値をそのまま `startTimer()` に渡すと「100ミリ秒」として扱われてしまいます。
ラピュタ崩壊までいくらかの猶予（100秒）があると思っていたら、一瞬（100ミリ秒）でバルスされてしまうことになります。

このようなミスは、関数名やパラメーター名の工夫である程度防ぐことができます。

```kotlin
fun getRemainingTimeSeconds(): Long {
    // ...
}

fun startTimer(timeMillis: Long) {
    // ...
}
```

しかし、すべての関数やパラメーターにこのような単位名を付けようとすると、コードが全体的に冗長になり、可読性が下がってしまいます。
それに、ID の例でも示したように、名前で単位を示すのは型安全性という面では不安が残ります（万が一、誤用してもコンパイルエラーになりません）。

そこで、インラインクラスを使ったラッパークラスの出番です。
次の例では、秒を表現する型 `Seconds` と、ミリ秒を表現する型 `Millis` を定義しています。

```kotlin
// 秒を表現する型
inline class Seconds(val seconds: Long) {
    fun toMillis() = Millis(seconds * 1000)
}

// ミリ秒を表現する型
inline class Millis(val millis: Long) {
    // ...
}

fun getRemainingTime(): Seconds {
    return Seconds(100)
}

fun startTimer(time: Millis) {
    TimeUnit.MILLISECONDS.sleep(time.millis)
    println("バルス！")
}

fun main() {
    val time: Seconds = getRemainingTime()
    startTimer(time.toMillis())
    // startTimer(time)  // これは型のミスマッチによるコンパイルエラー
}
```

これで、時間情報を受け渡す各ポイントで型情報のチェックが入り、異なる単位の時刻情報が受け渡しされてしまうのを防ぐことができます。

もし、`Seconds(100)` のような記述が煩わしいと思うのであれば、次のように `Long` の拡張プロパティを定義してしまう方法もあります。
こうすれば、独自の数値型がもともと言語に備わっているかのようにコーディングすることができます。

```kotlin
// Long に拡張プロパティを追加
val Long.sec get() = Seconds(this)
val Long.ms get() = Millis(this)

// 使用例
val timeMillis: Millis = 50.ms
```

`50` と記述する代わりに、`50.ms` と記述するだけで `Millis` 型のインスタンスを扱えます。


インラインクラスと typealias の違い
----

Kotlin の `typealias` を使うと、既存の型のエイリアスを作成することができます。

```kotlin
typealias Seconds = Long
typealias Millis = Long
```

この仕組みを使うと、インラインクラスを使った場合と同じようなコードを記述できますが、意味はまったく異なります。
インラインクラスが新しい型を定義しているのに対し、`typealias` はあくまで別名を付けているだけです。
つまり、上記のように定義した別名 `Seconds` と `Millis` は、`Long` と等価（同じ型）の扱いになります。

```kotlin
typealias Seconds = Long
typealias Millis = Long

fun getRemainingTime(): Seconds = 100
fun startTimer(time: Millis) {}

fun main() {
    // Seconds を Long に入れてもエラーにならない！
    val seconds: Long = getRemainingTime()

    // Long を Millis として扱ってもエラーにならない！
    startTimer(seconds)
}
```

このような場面で型安全性を確保するには、`typealias` で別名を付けるのではなく、インラインクラス（によるラッパークラス）で新しい型を定義する必要があります。


コンパイル時の警告について
----

### Experimental feature 警告

インラインクラスは現状 Experimental feature として提供されているので、使用しようとすると、

```
The feature "inline classes" is experimental
```

といった警告がでることがあります。
インラインクラスが正式リリースされれば、この警告は消えるのでそのままにしておいてもよいのですが、気になる場合は次のように警告を抑制することができます。

#### 方法1: ファイルの先頭にアノテーションを記述する方法

```kotlin
@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")
```

#### 方法2: 対象コードの直前にアノテーションを記述する方法

```kotlin
@Suppress("EXPERIMENTAL_FEATURE_WARNING")
inline class CategoryId(val id: Int)
```

#### 方法3: build.gradle (.kts) にコンパイラオプションを指定する方法

```groovy
# build.gradle
compileKotlin {
    kotlinOptions.freeCompilerArgs += ["-Xinline-classes"]
}

# build.gradle.kts
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.freeCompilerArgs += "-Xinline-classes"
}
```

いずれも、Android Studio を使用している場合は、`inline` にカーソルを合わせて `Alt + Enter` で自動入力できます。

### 1 ファイルでたくさん inline class 定義するとき

次のように 1 ファイル内でたくさんインラインクラスを定義している場合、detekt などの静的解析で警告が発生することがあります。

#### types.kt

```kotlin
package com.example.myapp

inline class GenreId(val id: Int)
inline class EventId(val id: Int)
```

```
class GenreId should be declared in a file named GenreId.kt
```

例えば、[detekt であれば、@Suppress アノテーションを使ってこの警告を抑制](https://arturbosch.github.io/detekt/suppressing-rules.html) することができます。

```kotlin
@file:Suppress(
    "MatchingDeclarationName",  // 「ファイル名＝公開クラス名」のチェックを抑制
    "EXPERIMENTAL_FEATURE_WARNING"  // inline class の experimental 警告を抑制
)
package com.example.myapp

// ...
```

