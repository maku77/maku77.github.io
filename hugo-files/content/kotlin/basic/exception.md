---
title: "Kotlinメモ: 例外処理を記述する (try, catch, finally)"
url: "p/bvu8qmi/"
date: "2019-04-24"
tags: ["kotlin"]
aliases: [/kotlin/basic/exception.html]
---

例外の基本
----

Kotlin の例外処理には、Java と同様の **`try`**、**`catch`**、**`finally`** キーワードを使用します。

下記は、`NumberFormatException` 例外を捕捉する例です。

```kotlin
try {
    val num = "xyz".toInt()
    println(num)
} catch (e : NumberFormatException) {
    System.out.println(e)
}
```

例外のスローも Java と同様に **`throw`** を使って行います。
クラスのインスタンスを生成するときに `new` が必要なかったように、ここでも `new` は必要ありません。

```kotlin
fun fibonacci(n: Int) {
    if (n < 0) throw IllegalArgumentException("負の値は指定できません")
    // ...
}
```


例外の捕捉は必須ではない
----

Java では、`IOException` などの検査例外 (checked exception) を投げるメソッドを呼び出すメソッドは、必ず `catch` で例外を捕捉するか、自分自身のメソッドで `throws IOException` と宣言しておく必要がありました。
Kotlin では、**検査例外 (checked exception) と非検査例外 (unchecked exception) を区別しない** ので、メソッドに `throws IOException` の宣言が必要ありません。
スローされた例外を捕捉するもしないも呼び出し側の自由です。
捕捉されなかった例外は、呼び出し元のメソッドに伝搬されていきます。

```kotlin
fun throwIoException() {
    throw IOException("読み込みエラーだよ！")
}

fun foo() {
    // IOException をスローするメソッドを呼び出しているけど、
    // この foo メソッドでは throws 宣言をしなくてもよい。
    throwIoException()
}

fun main() {
    try {
        foo()
    } catch (e : IOException) {
        System.err.println(e)
    }
}
```


try を式として扱う
----

Kotlin では、`try` も式です。
`try` のブロックの中で評価された値を、そのまま変数で受け取ったり、関数の戻り値として返したりすることができます。

下記の例では、`try` ブロックの中で実行した `String.toInt()` の結果を、`num` 変数に代入しています。

```kotlin
fun printIfInt(s: String) {
    val num = try {
        s.toInt()
    } catch (e: NumberFormatException){
        return
    }
    println(num)
}
```

Java では、`try` ブロック内で作成したオブジェクトを、ブロックの外で参照したいときは、ブロックの外でその変数を定義しておく必要がありました（とりあえず `null` 値を入れておくとかして）。
Kotlin ではそのような回りくどい書き方をせずに済みます。

上記の例では、例外が発生したときに `return` でそのまま関数から抜けるようにしていますが、三項演算子的に別の値を返すこともできます。
次の例では、文字列をうまくパースできなかった場合は、`num` 変数に `null` を格納するようにしています。

```kotlin
val num = try {
    s.toInt()
} catch (e: NumberFormatException){
    null
}
```

もっとも、この例のような処理は、`String.toIntOrNull` という関数を使うのが正解ですが。

```kotlin
val num = s.toIntOrNull();  // Int に変換できない場合 null にする
```

ちなみに、`if` 式を三項演算子的に使う場合、分岐後の式が 1 つであれば下記のように `{}` を省略できましたが、

```kotlin
println(if (x == 0) "ZERO" else "NOT ZERO")
```

`try` ～ `catch` の場合は、`{}` は省略できないことに注意してください。

{{< code lang="kotlin" title="こんな書き方はできません" >}}
val num = try s.toInt() catch (e: NumberFormatException) null
{{< /code >}}

