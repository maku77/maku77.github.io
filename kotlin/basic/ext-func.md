---
title: "既存のクラスに関数やプロパティを追加する（拡張関数）"
date: "2019-05-08"
description: "Kotlin の拡張関数 (extension functions) の仕組みを利用すると、既存のクラスにメソッドやプロパティを追加することができます。"
---

クラスに拡張関数を追加する
----

拡張関数を使うと、自分で作成したクラスではなくてもメソッドを追加することができます。
例えば、Java のコアライブラリの `String` クラスや、Android SDK が提供するクラスなどを拡張することができます。
やりすぎると分かりにくくなってしまいますが、効果的に導入すると、簡潔で分かりやすいコードを記述できるようになります。

次の例では、`String` クラスに拡張関数を追加し、ある文字列が `0x` あるいは `0X` で始まっているかを調べる `isHex` メソッドを定義しています。

```kotlin
fun String.isHex(): Boolean = this.startsWith("0x", true)

fun main() {
    println("0x123".isHex())  //=> true
    println("12345".isHex())  //=> false
}
```

**拡張関数の中で `this` を参照すると、レシーバオブジェクトを参照する**ことができます。
つまり、`s.isHex()` と呼び出した場合、`this` はオブジェクト `s` を参照します。

拡張関数の実装は、普通のメソッド実装と同じ感覚で記述することができるので、拡張関数の中からメンバメソッドを呼び出すときは、通常  `this` を省略することができます。
よって、上記の `String.isHex()` 関数は下記のように記述できます。

```kotlin
fun String.isHex(): Boolean = startsWith("0x", true)
```

このように、拡張関数の中から別のメンバメソッドを呼び出すことができるのですが、**呼び出せるのは public なメンバメソッドだけ**であることに注意してください。
private メソッドや、protected メソッドを呼び出すことはできません。
これは、拡張関数を追加することによって、カプセル化されたクラス構造を破壊してしまわないようにするための Kotlin の配慮です。


Int などの基本型に拡張関数を追加する
----

Kotlin の `Int` などの基本型に拡張関数を追加する場合、その値自身は `this` で参照することができます。
下記の例では、`Int` 値を 2 乗した値を返す `square` メソッドを `Int` 型に追加しています。

```kotlin
fun Int.square(): Int = this * this

fun main() {
    println(20.square())  //=> 400
}
```


拡張関数をパッケージ化する
----

あるパッケージ内で定義された拡張関数は、パッケージのトップレベルに定義された関数と同様にインポートできます。
例えば、下記のように `com.example.ext.strings` パッケージで `String` クラスの拡張関数が定義されているとします。

#### StringUtil.kt

```kotlin
package com.example.ext.strings

fun String.isHex(): Boolean = startsWith("0x", true)
```

この `isHex` 関数を使用するには、下記のようにトップレベルの関数をインポートするかのようにインポートします。

#### main.kt

```kotlin
import com.example.ext.strings.isHex

fun main() {
    println("0x123".isHex())
}
```

複数の拡張関数をインポートするときは、ワイルドカードを使ってまとめてインポートすることができます。

```kotlin
import com.example.ext.strings.*
```

インポート時に **`as`** を使って、拡張関数に別名を付けることもできます。
複数のライブラリで定義された拡張関数名が衝突している場合は、この方法で解決できます。

```kotlin
import com.example.ext.strings.isHex as hex

fun main() {
    println("0x123".hex())
}
```


クラスに拡張プロパティを追加する
----

既存のクラスに拡張プロパティ (extension properties) を追加することもできます。
拡張関数とほぼ同様ですが、最初のキーワードが `fun` ではなく、**`val`** になります。

```kotlin
val String.lastChar: Char
    get() = get(length - 1)

fun main() {
    println("ABC".lastChar)  //=> 'C'
}
```

もともとフィールド（メンバ変数）としての実体のないところにプロパティを追加することになるので、必ず上記のように getter メソッドとして定義することになります（既存のフィールドの値などを参照する形で getter を実装するしかない）。

書き込み可能な拡張プロパティを定義するには、下記のように setter を実装します。
対象となるクラスは mutable（変更可能）でなければいけないので、ここでは `String` ではなく `StringBuilder` に拡張プロパティを定義していることに注意してください。
変更可能なプロパティであることを示すため、最初のキーワードは `val` ではなく **`var`** にします。

```kotlin
/** 末尾の文字を取得・設定します */
var StringBuilder.lastChar: Char
    get() = get(length - 1)
    set(value: Char) {
        setCharAt(length - 1, value)
    }

fun main() {
    val sb = StringBuilder("ABC")
    sb.lastChar = 'X'
    println(sb)  //=> "ABX"
}
```


コレクションクラスを拡張関数で拡張する
----

Generics を利用して定義されたコレクションクラスにも、拡張関数を追加することができます。
下記の例では、`Map` インタフェースに `findKey` メソッドを追加して、値からキーを取得できるように拡張しています。

```kotlin
/**
 * マップ要素の値からキーを検索します。
 * 見つからない場合は null を返します。
 */
fun <K, V> Map<K, V>.findKey(value: V) =
    entries.firstOrNull { it.value == value }?.key

fun main() {
    val map = mapOf("AAA" to 100, "BBB" to 200, "CCC" to 300)
    println(map.findKey(100))  //=> "AAA"
    println(map.findKey(777))  //=> null
}
```

特定の型の要素を持つコレクション専用の拡張関数を定義することもできます。
次の例では、`String` 要素を保持するコレクションに対してだけ実行できる `tsv` メソッドを定義しています。

```kotlin
/** すべての要素をタブ文字で結合した文字列を作成します。 */
fun Collection<String>.tsv() = joinToString("\t")

fun main() {
    val list = listOf("A", "B", "C")
    println(list.tsv())  //=> "A   B   C"
}
```

Kotlin の標準ライブラリには、配列やコレクションを便利に使用するための拡張関数がいろいろ定義されています。
例えば、`last()` 拡張関数は、配列やコレクションの最後の要素を返します。

```kotlin
fun <T> Array<out T>.last(): T
fun <T> Iterable<T>.last(): T
fun <T> List<T>.last(): T
```

次のように直感的に使用できます。

```kotlin
val arr = intArrayOf(1, 2, 3)
val list = listOf("A", "B", "C")
println(arr.last())  //=> 3
println(list.last())  //=> "C"
```


拡張関数を Java のコードから参照する
----

Kotlin で定義した拡張関数を Java のコードから利用する場合は、ファイル名に基づくクラス名を使用してアクセスします（トップレベルに定義した関数を Java から呼び出す場合と同様です）。

例えば、下記のように `StringUtil.kt` ファイル内で `String` クラスの拡張関数 `isHex` が定義されているとします。

#### StringUtil.kt

```kotlin
package com.example.ext.strings

fun String.isHex(): Boolean = startsWith("0x", true)
```

この関数を Java のコードから使用する場合は、`StringUtilKt.isHex` という static メソッドとして参照します。
操作の対象となるレシーバオブジェクトは第一引数で渡します。

#### Main.java

```java
import com.example.ext.strings.StringUtilKt;

public class Main {
    public static void main(String... args) {
        System.out.println(StringUtilKt.isHex("0x123"));  //=> true
    }
}
```

このように、Kotlin の拡張関数は内部的には static なメソッドとして扱われているため、拡張関数として追加したメソッドは override できないという制約があります（パラメータが異なる overload を定義することは可能）。

- 参考: [パッケージのトップレベルに関数、プロパティ、定数を定義する](../package/top-level.html)


既存メソッドは拡張関数で上書きできない
----

既存のメソッドと同じシグネチャの拡張関数を定義した場合は、既存のメソッドの方が優先されて呼び出されます。

```kotlin
class Person(val name: String) {
    fun greet() {
        println("I am $name")
    }
}

// Person オブジェクトの動きを強引に変えてみる
fun Person.greet() {
    println("Hehehe")
}

fun main() {
    val p = Person("Maku")
    p.greet()
}
```

#### 実行結果

```
I am Maku
```

この動作も、既存クラスの振る舞いが破壊されないようにするための Kotlin の配慮です。

