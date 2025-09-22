---
title: "Kotlinメモ: ラムダ式の基本 (lambda expression)"
url: "p/rdoseay/"
date: "2019-05-16"
tags: ["kotlin"]
aliases: [/kotlin/basic/lambda.html]
---

ラムダ式とは
----

ラムダ式は、パラメータを受け取ることのできる名前のない小さなコードブロックです。
Kotlin では、ラムダ式は次のように中括弧 `{}` で囲んで定義します。

```kotlin
{ a: Int, b: Int -> a + b }
```

このラムダ式は、2 つの `Int` 型パラメータを受け取り、足し合わせた値を返します。
ラムダ式を変数に格納すると、**通常の関数のように呼び出すことができる**ようになります。

```kotlin
val sum = { x: Int, y: Int -> x + y }
println(sum(1, 2))  //=> 3
```

ラムダ式の典型的な使い方は、処理の一部をパラメータとして渡す使い方です。
次の例では、ボタンが押されたときのイベントハンドラとして、パラメータなしのラムダ式をセットしています。

```kotlin
button.setOnClickListener {
    println("ボタンが押されたよ")
}
```

このようなコールバック処理は、インタフェースを実装したオブジェクトを渡すことでも実現できますが、ラムダ式を使うと関数の本体部分だけを渡すことができるので、とてもシンプルな記述ができます。


ラムダ式を受け取る関数を定義する
----

下記の `myRepeat` 関数は、第1引数 `times` で指定された回数だけ、第2引数 `action` で指定されたラムダ式を繰り返し実行します。

```kotlin
fun myRepeat(times: Int, action: (Int) -> Unit) {
    for (index in 0 until times) {
        action(index)
    }
}
```

パラメータ `action` の型は **`(Int) -> Unit`** となっていますが、これは、`Int` 型のパラメータを取り、戻り値がない (`Unit`) ラムダ式を受け取ることを表しています。
`myRepeat` 関数は次のように使用します。


```kotlin
fun main() {
    myRepeat(3, { i -> println("Hello $i")} )
}
```

{{< code title="実行結果" >}}
Hello 0
Hello 1
Hello 2
{{< /code >}}

上記のように、関数の最後のパラメータがラムダ式である場合、その関数を呼び出すときに、ラムダ式を括弧の外に出して次のように記述することができます。

```kotlin
myRepeat(3) { i -> println("Hello $i") }
```

このような記述方法が許されているので、ラムダ式の中が複数行にわたるコードになった場合にも、次のようにシンプルに記述することができます。


```kotlin
myRepeat(3) { i ->
    println("${i + 1} 回目のループ処理です")
    println("Hello!")
}
```

ラムダ式のパラメータが 1 つだけのとき、`i ->` の部分を省略し、代わりに **`it`** というパラメータ名で参照することができます。

```kotlin
myRepeat(3) { println("Hello $it") }
```

デフォルトのパラメータ名である `it` を使用すると簡潔な記述が可能ですが、明示的に名前を付けたほうが可読性が上がるケースもあります。
例えば、ラムダ式がネストされている場合などはパラメータ名を指定すべきでしょう。

ちなみに、Kotlin の組み込み関数 `repeat` を使うと、上記の `myRepeat` と同様のことを行えます。


ラムダ式のいろいろな使い方
----

### forEach（繰り返し処理）

リストや配列の要素を繰り返し処理する [`forEach` 関数](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/for-each.html) は、ラムダ関数の代表的な使用例です。

```kotlin
val list = listOf("AAA", "BBB", "CCC")
list.forEach {
    println(it)
}
```

このようにパラメータがラムダ式だけであれば、関数名の直後の括弧 `()` を省略することができます（`forEach()` と書かなくてよい）。

ちなみに、ループ処理中にインデックス番号が欲しいときは、`forEach` の代わりに **`forEachIndexed`** を使用します。

```kotlin
list.forEachIndexed { index, value ->
    println("$index -> $value")
}
```

次のように `for ~ in` を使用した書き方もできますが、ラムダ式を使ったほうがスマートですね。

```kotlin
for ((index, value) in list.withIndex()) {
    println("$index -> $value")
}
```

### map, mapIndexed（変換処理をラムダ式で指定する）

リストや配列に適用できる [`map` 関数](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/map.html) を使うと、各要素の値を、ラムダ式によって加工することができます。
下記の例では、数値リストの各要素を 2 倍にしています。

```kotlin
val list = listOf(1, 2, 3)
val list2 = list.map { it * 2 }
println(list2)  //=> [2, 4, 6]
```

`map` 関数は下記のように定義されていて、受け取るラムダ式の型は `(T) -> R` と定義されています。

```kotlin
public inline fun <T, R> Iterable<T>.map(transform: (T) -> R): List<R> {
    return mapTo(ArrayList<R>(collectionSizeOrDefault(10)), transform)
}
```

`T` は元のリスト要素の型で、`R` が変換後のリスト要素の型です。
実際には、ほとんどのケースで型の指定を省略することができるため、呼び出しコードはとてもシンプルになります。


### maxBy, sortBy（比較アルゴリズムをラムダ式で指定する）

リストや配列に適用できる [`maxBy` 関数](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/max-by.html) を使うと、その中の最大の要素を検索するときに、どのフィールドの値を参照するかをラムダ式で指定することができます。
下記の例では、`Book` のリストのから、`price` フィールドが最大であるものを検索しています。

```kotlin
data class Book(val title: String, val price: Int)
val list = listOf(
    Book("Title1", 500),
    Book("Title2", 1000),
    Book("Title3", 300))

val mostExpensiveBook = list.maxBy { it.price }
println(mostExpensiveBook)  //=> Book(title=Title2, price=1000)
```

上記の例では、検索に使用するフィールドを `list.maxBy { it.price }` というようにラムダ式で指定していますが、このように単純に、あるフィールドを参照すればよい場合、**`Book::price` というメンバ参照で指定することもできます**。
この方法であれば、ラムダ式の記述は必要なくなります。

```kotlin
val mostExpensiveBook = list.maxBy(Book::price)
```

`Book::price` は、`{ b: Book -> b.price }` というラムダ式の省略記法です。
[メンバ参照に関しての説明はこちら](/p/r2gqqnt/) を参照してください。

`maxBy` 関数の実装は次のようになっています。

```kotlin
/**
 * Returns the first element yielding the largest value of
 * the given function or `null` if there are no elements.
 */
public inline fun <T, R : Comparable<R>> Iterable<T>.maxBy(selector: (T) -> R): T? {
    val iterator = iterator()
    if (!iterator.hasNext()) return null
    var maxElem = iterator.next()
    var maxValue = selector(maxElem)
    while (iterator.hasNext()) {
        val e = iterator.next()
        val v = selector(e)
        if (maxValue < v) {
            maxElem = e
            maxValue = v
        }
    }
    return maxElem
}
```

### withLock（テンプレートメソッドのような処理をラムダ式で実現する）

テンプレート的な処理の一部をラムダ式で指定すると、コードを簡潔にすることができます。
例えば、`ReentrantLock` による排他処理は、`lock()` で開始、`unlock()` で終了、という流れになりますが、このように順序の決まった定型処理をカプセル化することができます。

```kotlin
val lock = ReentrantLock()
lock.withLock {
    // スレッドセーフにしたいコード
}
```

`withLock` 関数は次のように実装されています。

```kotlin
public inline fun <T> Lock.withLock(action: () -> T): T {
    lock()
    try {
        return action()
    } finally {
        unlock()
    }
}
```


ラムダ式のパラメータの型を省略できないケース
----

ラムダ式のパラメータの型は通常コンパイラによって推測されるので省略できますが、ラムダ式をいったん変数に格納してから使用する場合は、パラメータの型を明示する必要があります。

```kotlin
val getBookPrice = { b: Book -> b.price }
val mostExpensiveBook = list.maxBy(getBookPrice)
```


ラムダ式からのローカル変数アクセス
----

Kotlin のラムダ式からは、同じスコープで定義されたローカル変数や関数のパラメータにアクセスすることができます。
Java のラムダ式やインナーメソッドからも、同様にローカル変数にアクセスすることはできますが、`final` 定義された変数にしかアクセスできないという制約がありました。
Kotlin のラムダ式にはそのような制約はないため、ラムダ式の中から、外側の変数の値を変更することができます。

```kotlin
fun totalSales(shops: List<Shop>) : Int {
    var total = 0
    shops.forEach {
        total += it.sales
    }
    return total
}
```

{{% note %}}
専門用語では、Java のラムダは「final 変数しか捕捉 (capture) できない」と表現します。
逆に、Kotlin のラムダは「mutable な変数も捕捉できる」と表現します。
{{% /note %}}

ラムダ式で外部変数を変更する場合は、上記のように、**同期で実行される必要があります**。
非同期に実行されるラムダ式の中で値を変えようとしても、その次の処理が先に実行されてしまうので意味のないコードになってしまいます。

```kotlin
fun getClickCount(button: Button) {
    var count = 0
    button.setOnClickListener { ++count }
    return count  // ここが先に実行されるので常に 0 になってしまう
}
```

ちなみに、前述の `totalSales` のような処理は、**`sumBy`** や **`fold`** を使えば、次のように簡単に記述することができます。

```kotlin
val total = list.sumBy(Shop::sales)
val total = list.fold(0) { total, elem -> total + elem.sales }
```

`sumBy` は単純にフィールドの値を足し合わせ、`fold` は一要素ずつ計算結果を畳み込んでいきます（一つ前の計算結果を用いて次の計算を行う）。

