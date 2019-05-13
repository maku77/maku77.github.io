---
title: "by を使ったメソッド呼び出しの委譲"
date: "2019-05-13"
---

by による委譲とは
----

Decorator パターンを実装したり、コレクションクラスを委譲によって拡張したいケースでは、ベースとなるインタフェースで定義されているすべてのメソッドを実装しなければいけません。
あるクラスの機能を継承なしで拡張できる一方で、拡張しなくてもよいメソッドも含めて委譲処理を実装しなければいけないのは骨の折れる作業です。
単純なボイラープレート実装があふれたコードは美しくありません。

このようなケースで利用できるのが、**`by` によるメソッド呼び出しの委譲 (class delegation)**です。

#### IBar インタフェースの呼び出しを bar オブジェクトへ委譲

```kotlin
class Foo(val bar: IBar) : IBar by bar
```

実装するインタフェース名に続けて `by someObj` と記述すると、そのインタフェースの実装を指定したオブジェクト `someObj` に一括で委譲することができます。
そして、必要なメソッドだけをオーバーライドすることができます。


by による委譲処理の例
----

例えば、下記のような単純なログ出力クラスがあるとします（Decorator パターンなのでインタフェースが必要です）。

```kotlin
interface Logger {
    fun info(text: String)
    fun warn(text: String)
    fun error(text: String)
}

class SimpleLogger : Logger {
    override fun info(text: String) = println(text)
    override fun warn(text: String) = println(text)
    override fun error(text: String) = println(text)
}
```

この `SimpleLogger` クラスを Decorator パターンで拡張し、**`error` メソッドの呼び出し時にだけ `ERROR: ` というプレフィックスを付けて出力する**ようにします。
単純に実装すると下記のようになると思います。

```kotlin
class EmphasizedLogger(val logger: Logger) : Logger {
    override fun info(text: String) = logger.info(text)
    override fun warn(text: String) = logger.warn(text)
    override fun error(text: String) = logger.error("ERROR: $text")
}

fun main() {
    val logger = EmphasizedLogger(SimpleLogger())
    logger.error("Hello")  //=> ERROR: Hello
}
```

もちろん、これはこれで間違いではありませんが、`info` メソッドと `warn` メソッドは単純に処理を委譲しているだけで、あまりクールではありません。
このようなとき `by` を使用すると、あるインタフェースに関する処理を、指定したオブジェクトにまとめて委譲してしまうことができます。

下記の例では、`Logger` インタフェースに関する処理を、コンストラクタで渡された `logger` オブジェクトへ委譲しています。
ただし、`error` メソッドだけは、自分のクラスでオーバーライドして拡張しています。

```kotlin
class EmphasizedLogger(val logger: Logger) : Logger by logger {
    override fun error(text: String) = logger.error("ERROR: $text")
}
```

このように、`by` による委譲を行うことで、自分が拡張したい部分だけをコーディングすることができるようになります。


コレクションクラスを Decorator パターンで拡張する
----

`by` によるメソッド呼び出しの委譲は、コレクションなどのメソッドの多いクラスを Decorator パターンで拡張するときに威力を発揮します。
下記の例では、`LinkedList` を拡張し、同一の値を保持しないキューである `UniqueQueue` クラスを実装しています。

```kotlin
import java.util.*

class UniqueQueue<T>(private val innerQueue: Queue<T> = LinkedList<T>())
        : Queue<T> by innerQueue {
    override fun add(element: T) =
        if (element in innerQueue) false else innerQueue.add(element)
}

fun main() {
    val queue = UniqueQueue<Int>()
    queue.add(1)
    queue.add(2)
    queue.add(3)
    queue.add(1)
    queue.add(2)
    println(queue.toList())  //=> [1, 2, 3]
}
```
