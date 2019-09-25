---
title: "safe call (?.) や elvis operator (?:)、let で null をうまく扱う"
date: "2019-09-24"
---

null を扱うさまざまな演算子・関数
----

Kotlin には、`null` をうまく扱うための便利な演算子や関数が用意されています。
代表的なものに下記があります。

- **`オブジェクト?.メソッド` (safe call)** -- オブジェクトが `null` でないならメソッドを呼び出す。`null` なら `null` を返す。
- **`式1 ?: 式2` (Elvis operator)** -- 式1が `null` でないならその値、`null` なら式2を評価する
- **`オブジェクト!!` (not-null assertion / unsafe dereference)** -- nullable なオブジェクトを not-null なオブジェクトとして参照する。実際には `null` だった場合、`NullPoiterException` が発生する。


使用例
----

### オブジェクトが null じゃない場合のみメソッドを呼び出す

```kotlin
logger?.log("Hello")
```

`logger` 変数が `null` でない場合のみ `log()` メソッドを実行します。


### オブジェクトが null だった場合のデフォルト値

```kotlin
val title = book?.title ?: "Unknown title"
```

`book` 変数が `null` でない場合は `book.title` の値を、`null` である場合は "Unknown title" を `title` に代入します。


### オブジェクトが null でないときのみ処理を行う

```kotlin
// var book: Book? = null

book?.let {
    // let の中では it で non-null な book を参照できる
    println(it.title)
    println(it.author)
}
```

safe call (`?.`) と `let` 関数を組み合わせた **safe call let** と呼ばれているイディオムです。
上記の例では、変数 `book` が null でない場合に `book` のプロパティを参照する処理を実行します。
`book` が `null` でない場合にメソッドを 1 つだけ呼び出したい場合は、単純に safe call で `book?.foo()` とすればよいのですが、上記のように連続してオブジェクトを参照したい場合や、そのオブジェクト自体をパラメータとして別の関数に渡したい場合などに利用します。
別の関数のパラメータとして渡したい場合は次のように簡潔に記述することができます。

```kotlin
book?.let(display::showBookCover)
```

これは、下記のように記述するのと同じです。

```kotlin
book?.let { display.showBookCover(it) }
```


（コラム）let はラムダ式の評価結果を返すことに注意
----

`let` の戻り値の振る舞いをきちんと理解していないと、思わぬ不具合の元になります。
次の safe call let イディオムを見てください。

```kotlin
book?.let(logger::printTitle) ?: logger.showError();
```

あるいは、下記でも同様。

```kotlin
book?.let { logger.printTitle(it) } ?: logger.showError();
```

このコードは、`book` が null でない場合に `printTitle(book)` を実行し、null である場合に `showError()` を実行するコードのように見えます。
しかし、`let()` は実行したラムダ式の評価結果を返すため、`printTitle(book)` が null を返した場合にも `showError()` が実行されてしまいます。

この振る舞いは意図したものと異なるかもしれません。
正しくは、下記のように書くべきかもしれません。

```kotlin
if (book != null) {
    logger.printTitle(book)
} else {
    logger.showError()
}
```

