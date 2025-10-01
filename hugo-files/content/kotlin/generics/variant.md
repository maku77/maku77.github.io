---
title: "Kotlinメモ: ジェネリクスの共変 (covariant) と不変 (invariant) について理解する"
url: "p/irsz3gs/"
date: "2019-12-20"
tags: ["kotlin"]
aliases: ["/kotlin/generics/variant.html"]
---

共変である (covariant) とはどういうことか？
----

Kotlin の `Int` は `Any` として使用することができます（`Any` は Java で言うところの `Object` です）。
それでは、

1. `List<Int>` は `List<Any>` として扱うことができるでしょうか？
2. `MutableList<Int>` は `MutableList<Any>` として扱うことができるでしょうか？

その答えは、使用している Generic クラスが、その型パラメータに関して **共変である (covariant)** か **不変であるか (invariant)** によって決まります。

1. `List<Int>` は `List<Any>` として**使える**（`List` は共変である (= covariant)）
2. `MutableList<Int>` は `MutableList<Any>` としては**使えない**（`MutableList` は共変ではない (= invariant)）

「Generic クラスが共変である」とは、型引数に指定した型の親子関係が、Generic クラスによって生成された型の親子関係と等しくなるということを意味します。
例えば、`List<E>` はその型パラメータ `E` に関して共変であり、`List<Int>` を `List<Any>` として扱うことが可能です（`List<Int>` は `List<Any>` のサブタイプとみなされます）。

{{< code lang="kotlin" title="List<Int> は List<Any> として扱える" >}}
fun showElems(list: List<Any>) {
    for (e in list) {
        println(e)
    }
}

fun main() {
    val a = listOf(0, 1, 2)  // List<Int>
    showElems(a)  // OK!
}
{{< /code >}}

一方で、`MutableList<E>` は型パラメータ `E` に関して共変ではなく、`MutableList<Int>` は `MutableList<Any>` として扱うことができません。
まったく互換性のない型として扱われます。

なぜこのような差が出てくるのでしょうか？
次のようなコードを考えると分かりやすいと思います。

{{< code lang="kotlin" title="MutableList<Int> は MutableList<Any> としては扱えない" >}}
fun addElems(list: MutableList<Any>) {
    list.add("Hello")
    list.add("World")
}

fun main() {
    val a = mutableListOf(0, 1, 2)  // MutableList<Int>
    addElems(a)  // Compile Error!
}
{{< /code >}}

仮に上記のようなコードが合法とされてしまうと、`MutableList<Int>` 型のオブジェクトに、`Int` 以外の任意の要素を追加できることになってしまいます。
だから、`MutableList<E>` は型パラメータ `E` に関して共変ではない、と定義されているのです。
そのため、上記のようなコードはコンパイルエラーになります。

`List<Int>` の要素がより汎用的な `Any` として**使われるだけ**であれば問題はなく、`MutableList<Int>` がより汎用的な型である `Any` を**受け入れてしまう**のは問題があるということです。

ややこしいですが、共変 (covariant) と不変 (invariant) は Kotlin のジェネリクスを扱う上で非常に大切な概念なので、もう一度まとめておきます。

* `A` が `B` のサブタイプであるとき、`List<A>` は `List<B>` のサブタイプとなる。このような Generic クラスを **共変である (covariant)** という。
* `A` が `B` のサブタイプであったとしても、`MutableList<A>` は `MutableList<B>` のサブタイプにはならない。このような Generic クラスを **不変である (invariant)** という（＝共変ではない）。


共変な Generic クラス (covariant class) を定義する
----

ある型パラメータに関して共変な Generic クラスを定義するには、型パラメータに **`out`** キーワードを付けて定義します。
逆に何も修飾子を付けないで型パラメータを定義した場合は、その Generic クラスはデフォルトで不変 (invariant) になります。

```kotlin
class Holder<out T>(val elem: T) {
    fun get(): T = elem
}
```

型パラメータに `out` を付けるということは、その Generic クラスの実装において、型パラメータを出力用としてしか使用しないという宣言でもあります。
出力用というのは、戻り値の位置のみでその型パラメータを使用するということです。
そのため、このようなクラスを producer と呼ぶことがあり、クラス名に `Producer` が使われたりします。
個人的にはそういった命名はサンプルコードとしては分かりにくいと思うので、ここでは `Holder` というクラス名にしています。

専門用語では、getter などの出力位置で使うことを **out position** で使用する、setter などの入力位置で使うことを **in position** で使用すると言います。
型パラメータに `out` を付けることができるのは、その型パラメータを out position でしか使用していない場合のみに限られます（正確には in position で使用しない場合）。

例えば、上記のサンプルコードでは、型パラメータ `T` を getter の戻り値 (out position) の型としてのみ使用しています（コンストラクタのパラメータでも使っていますが、コンストラクタの `val` パラメータは本質的には getter を定義するものであり、out position とみなされます）。
型パラメータ `T` は in position では一切使用していないので、`out` 修飾子を付加することができ、共変 (covariant) な Generic クラスとすることができます。

もし、次のように `out` を付けた型パラメータを in position で使ってしまうと、

```kotlin
class Holder<out T>(val elem: T) {
    fun dump(t: T) { ... }  // ERROR
}
```

```
Type parameter T is declared as 'out' but occurs in 'in' position in type T
```

といった感じのコンパイルエラーになります。
なぜなら、このような使い方を許してしまうと、先に示した `MutableList` の例のように、型の安全性が保てなくなってしまうケースが発生するからです。

さて、ここで、次のような継承関係のある `Animal` クラスと `Bird` クラスがあるとします。
`Bird` は `Animal` のサブタイプです。

```kotlin
open class Animal {
    fun eat() { println("EAT") }
}

class Bird : Animal() {
    fun fly() { println("FLY") }
}
```

そして、`Holder<Animal>` をパラメータに取る `doEat` 関数を次のように定義したとします。

```kotlin
fun doEat(holder: Holder<Animal>) {
    val animal = holder.get()
    animal.eat()
}
```

`Holder` クラスは共変なジェネリッククラスとして定義（型パラメータ `T` に `out` を付けて定義）されているので、`Holder<Bird>` オブジェクトは `Holder<Animal>` オブジェクトの代わりに使用することができます。
よって、次のように `doEat` 関数に、`Holder<Bird>` オブジェクトを渡すことができます。

```kotlin
val holder: Holder<Bird> = Holder(Bird())
doEat(holder)
```

これが、`class Holder<out T>` のように、型パラメータに `out` を付けて定義した効果です。
もし、`out` を付けないで `class Holder<T>` と不変 (invariant) なクラスとして定義していると、`doEat(holder)` の呼び出しは、次のようなエラーになってしまいます。

```
Type mismatch: inferred type is Holder<Bird> but Holder<Animal> was expected
```

`Holder` が共変になっていないので、`Holder<Bird>` と `Holder<Animal>` の間には何の互換性もないからです。
ただ、これには少しだけ抜け道があって、ジェネリッククラスを使用する側（ここでは `doEat` 関数を定義する場所）で、次のように `out` を付けて、「共変 (covariant) な型パラメータとして使用しますよ」と宣言してしまう方法があります。

```kotlin
fun doEat(holder: Holder<out Animal>) {
    val animal = holder.get()
    animal.eat()
}
```

これは、`Holder` クラス自体は共変として定義してないけれど、少なくともこの関数の実装では共変なクラスとして使いますよ（`Holder<Animal>` の代わりに `Holder<Bird>` を渡してもいいですよ）ということを示しています。
このように、ジェネリッククラスを利用する場所で型引数に `out` 修飾子を付けてしまう方法を、**use-site variance（利用箇所分散）** と呼びます。
詳しくは後述します。


### Kotlin の List と MutableList の定義

では、Kotlin の `List` インタフェースと `MutableList` インタフェースの定義を見てみましょう。

```kotlin
interface List<out E> : Collection<E> { ... }
interface MutableList<E> : List<E>, MutableCollection<E> { ... }
```

`List` インタフェースの型パラメータ `E` には `out` が付いており、`MutableList` インタフェースの方には付いていません。
よって、`List` は型パラメータ `E` に関して共変 (covariant) なジェネリッククラスであり、`MutableList` は不変 (invariant) なジェネリッククラスです。

型パラメータの `out` 修飾子は、多くの場合、ファクトリ系のクラスや、immutable なデータホルダー系クラスで使われることになります。
しかし、`clear()` のように、まったくパラメータを取らずにオブジェクトの内容を変更するメソッドもあり得るため、immutable なクラスのみが共変 (covariant) なクラスになれるというわけではなりません。


反変な Generic クラス (contravariant class) を定義する
----

共変 (covariant) に近い概念に、その反対の性質を持つ **反変 (contravariant)** があります。
共変 (covariant) なジェネリッククラスから生成された型は、その型引数で指定した型の親子関係と同じ親子関係を持ちますが、反変 (contravariant) なジェネリッククラスから生成された型は、この親子関係が逆転します。
文章で書いても分かりにくいので、コードで見てみましょう。

まず、共変 (covarinat) のおさらいから。
共変な型パラメータは、`out` で修飾します。

```kotlin
interface List<out E>
```

`List<Int>` は `List<Any>` のサブタイプになります。

次に、反変 (contravariant) の例です。
反変なインタフェースの代表例としてあげられるのが `Comparator` インタフェースです。
反変な型パラメータは、**`in`** で修飾します。

```kotlin
interface Comparator<in T> {
    fun compare(a: T, b: T): Int
}
```

`Comparator<Int>` は `Comparator<Any>` の**スーパータイプ**になります。
言い換えると、`Comparator<Any>` が `Comparator<Int>` のサブタイプになります。
このあたりの親子関係が、共変 (covariant) のときと逆転しているため、反変 (contravariant) と呼びます。

なぜこのように親子関係が逆転するのでしょうか？
`Comparator<Any>` と `Comparator<Int>` の関係を考えてみましょう。
`Comparator<Int>` をパラメータとして受け取る関数には、`Comparator<Any>` オブジェクトを渡すことができます。

```kotlin
val anyComp = Comparator<Any> { a, b ->
    a.hashCode() - b.hashCode()
}

val intList = mutableListOf(3, 1, 5)
intList.sortWith(anyComp)
```

なぜなら、`Comparator<Any>` の実装の中では `Any` インタフェースしか参照しないため、その実装を `Int` オブジェクト同士の比較に使用しても何ら問題はないからです。

`in` 修飾子を付けられる型パラメータは、in position でしか使われないものに限定されます（正確には、public メソッドの戻り値のような、out position では一切使われないもの）。
そのジェネリッククラスの中では、型パラメータを受け入れる（あるいは消費する）用途にしか使用しないため、そのジェネリッククラスのことを consumer と呼んだりします。
なので、サンプルコードでは反変 (contravariant) なジェネリッククラスの名前が `Consumer` になっていたりします。

Kotlin の `Continuation` インタフェースも、反変なジェネリックインタフェースのひとつです。

```kotlin
interface Continuation<in T> {
   val context: CoroutineContext
   fun resume(value: T)
   fun resumeWithException(exception: Throwable)
}
```


declaration-site variance（宣言箇所分散）と use-site variance（利用箇所分散）
----

前述の `Holder` クラスの説明の場所でも少し出てきましたが、ジェネリッククラスの型パラメータに、`out` や `in` などの分散修飾子 (variance modifier) を付ける場合、Kotlin ではその付加タイミングが 2 パターンあります。

* **declaration-site variance（宣言箇所分散）** ... クラスやインタフェースの宣言時に型パラメータを `out` や `in` で修飾する
* **use-site variance（使用箇所分散）** ... すでに定義されているジェネリッククラスを使うときに型引数に `out` や `in` を付ける

### declaration-site variance（宣言箇所分散）

次の `List` インタフェースのように、インタフェースを宣言する時点で型パラメータに `out` や `in` 修飾子を付けてしまう方法です。

```kotlin
interface List<out E> : Collection<E> { ... }
```

この `List` インタフェースを使う場所では、デフォルトで共変 (covariant) として扱われます。
例えば、`List<Number>` を受け取る関数には、`List<Int>` や `List<Double>` を渡すことができます。

```kotlin
fun showNumbers(nums: List<Number>) {
    println(nums)
}

fun main() {
    showNumbers(listOf(1, 2, 3))  // List<Int> を渡せる
    showNumbers(listOf(0.1, 0.2, 0.3))  // List<Double> も渡せる
}
```

もっと簡単に書くと、`List` インタフェースでは、次のような代入が可能ということです。

```kotlin
val nums: List<Number> = listOf<Int>(1, 2, 3)  // OK
```

逆に、`MutableList` インタフェースは共変 (convariant) として定義されていない（＝不変 (invariant)) ので、次のような代入ができません。

```kotlin
val nums: MutableList<Number> = mutableListOf<Int>(1, 2, 3)  // ERROR
```


### use-site variance（使用箇所分散）

ジェネリッククラスを使用する際に、その型引数に `in` や `out` キーワードを付ける方法です。

例えば、Kotlin の `MutableList` は不変 (invariant) なジェネリッククラスなので、次のように、`MutableList<Int>` を `MutableList<Number>` として扱うことはできません。

```kotlin
val list: MutableList<Number> = mutableListOf<Int>(1, 2, 3)  // ERROR
```

このような不変 (invariant) クラスであっても、使用する場所で `out` キーワードを付ければ、その部分だけでは共変であるとみなして使用することができます。
その副作用として、in position で要素を扱うことができなくなるので、`add()` メソッドなどを呼び出せなくなります。

```kotlin
val list: MutableList<out Number> = mutableListOf<Int>(1, 2, 3)
list.add(1.5)  // ERROR
```

このように型引数を使用することを、専門用語で **型投影（タイププロジェクション）** と呼んだりします。
このケースでは、`out` キーワードを付けているので、out-projected されているといいます。

より実践的な例として、次のような、配列 (`Array`) の中身をコピーする関数を考えてみます。

```kotlin
fun <T> copyArray(src: Array<T>, dst: Array<T>) {
    assert(src.size == dst.size)
    for (i in src.indices) {
        dst[i] = src[i]
    }
}
```

Kotlin の `Array` クラスは、次のように型パラメータに variance modifier（`out` や `in`）が付いていないので、`MutableList` と同様に、不変 (invariant) なジェネリッククラスです。

```kotlin
class Array<T>
```

よって、`Array<Int>` と `Array<Number>` には互換性がなく、次のようなコードはエラーになります。

```kotlin
val arr1 = arrayOf<Int>(1, 2, 3)
val arr2 = arrayOfNulls<Number>(3)
copyArray(arr1, arr2)  // ERROR
```

そこで、使用箇所分散 (use-site variance) の仕組みを使って、`dst` パラメータの型パラメータに `in` キーワードを付けて、反変 (contravariant) にします。
`in` キーワードが付けられることにより、この `dst` オブジェクトは、`T` 型の要素を取り込むだけ（consume するだけ）ですよと宣言していることになります。


```kotlin
fun <T> copyArray2(src: Array<T>, dst: Array<in T>) {
    assert(src.size == dst.size)
    for (i in src.indices) {
        dst[i] = src[i]
    }
}
```

すると、この関数の `src` パラメータに `Array<Int>` を渡した場合は、`dst` パラメータに `Array<Number>` などをサブタイプとみなして渡せるようになります。

```kotlin
val arr1 = arrayOf<Int>(1, 2, 3)
val arr2 = arrayOfNulls<Number>(3)
copyArray2(arr1, arr2)  // OK
```

`Array` クラスは本来は不変 (invariant) なクラスとして定義されているものですが、この関数だけは反変 (contravariant) として使えるようになるということです。

ちなみに、Java には、この use-site variance しか存在せず、`<? extends Hoge>` や `<? super Hoge>` といった上限境界、下限境界を指定する方法が使われていました。


in position と out position
----

型パラメータを共変 (covariant) にするには `out` 修飾子を付ける必要があり、反変 (contravariant) にするには `in` 修飾子を付ける必要があります。
これらの修飾子を付けるときの制約は次のようになっています。

- `out` ... in position で使用する型パラメータには付けられない
- `in` ... out position で使用する型パラメータには付けられない

ここでは、どの位置で型パラメータを参照することが、in position と out position のどちらで使用しているとみなされるのかをまとめておきます。

- in position
    - public なメソッドのパラメータ
    - public なプロパティの setter（コンストラクタパラメータで、`var` が付いているもの。内部的に setter が定義されるため in position とみなされる）
- out position
    - public なメソッドの戻り値
    - public なプロパティの getter（コンストラクタパラメータで、`val` あるいは `var` が付いているもの。内部的に getter が定義されるため out position とみなされる）
- in position でも out position でもない（共変にも反変にもできる）
    - private なメソッドやプロパティのパラメータおよび戻り値（private なメソッドでは、型の誤用の心配がないため）
    - コンストラクタパラメータで、`val` も `var` も付いていないもの（初期化時にしか呼ばれないものであり、型パラメータの誤用の危険性が低いため、in position でも out position でもないとみなされる）


Kotlin の配列は不変、Java の配列は共変
----

Kotlin の `Array` クラスは不変 (invariant) なジェネリッククラスとして定義されています。
なので、基本的に、`Array` クラスから生成される異なる型の間に、親子関係は生まれません（前述の型投影 (use-site variance) の仕組みを使う場合は例外です）。

```kotlin
val arr: Array<Any> = arrayOf<Int>(1, 2, 3)  // ERROR
```

一方、Java の配列は共変 (covariant) なものと定義されていました。
なので、次のような代入が可能でした。

```java
Integer[] nums = {1, 2, 3};
Object[] objs = nums;
objs[0] = "ABC";  // Runtime ERROR
```

この仕様の問題点として、上記のように、`Integer[]` 配列に `String` オブジェクトを格納するコードがコンパイルできてしまう点が挙げられていました。
このような問題に対するアプローチとして、Kotlin では `Array<E>` クラスを（よりプリミティブな要素を保持する `IntArray` や `CharArray` も同様に）不変 (invariant) とすることに決めました。
`Array<Int>` と `Array<Any>` の間には互換性がないため、上記の Java の例のように、想定外の型の要素が格納されてしまう心配がありません。


`List<Any?>` と `List<*>` (star projection) の違い
----

ジェネリクス型のオブジェクトを受け取る関数を定義するときに、要素の型情報を特に意識しないケースでは、型パラメータの代わりに、より簡潔な **スタープロジェクション (star projection)** の構文を使用することができます。

```kotlin
fun dump(list: List<*>) {
    for ((index, elem) in list.withIndex()) {
        println("$index: $elem")
    }
}

fun main() {
    val list = listOf("AAA", "BBB", "CCC")
    dump(list)
}
```

上記の `dump` 関数を次のように型パラメータを使って定義しても同様に動作しますが、関数の定義の中で型情報を使用しないため、スタープロジェクションの構文を使用した方がシンプルに記述できます。

```
fun <T> dump(list: List<T>)
```

型パラメータの位置に `<*>` を指定するのと、`<Any?>` を指定するのは同じような感じがしますが、次のように明確な違いがあります。

* `MutableList<*>` ... 特定の型の要素が入ったリスト
* `MutableList<Any?>` ... 何でも入れられるリスト

`MutableList<*>` で参照しているリストの実態は `MutableList<Int>` だったりする一方、`MutableList<Any?>` で参照するリストは必ず `MutableList<Any?>` です。

`MutableList<*>` を受け取る関数は、具体的な型は気にしないけれど、呼び出し元で具体的な型引数を指定して作成したリストが渡されることを想定しています。
つまり、次のようなコードは型の安全性がないため、コンパイルエラーになります。

```kotlin
fun addSomething(list: MutableList<*>) {
    list.add("Hello")  // ERROR: 勝手にString型のリストだと想定しちゃだめ
}

fun main() {
    var intList = mutableListOf(1, 2, 3)
    addSomething(intList)
}
```

一方で、`MutableList<Any?>` は何でも格納できるリストであることを示します。

```kotlin
fun addSomething(list: MutableList<Any?>) {
    list.add("Hello")  // OK: このリストには何でも入れられる
}

fun main() {
    var list = mutableListOf<Any?>(1, "A", null)
    addSomething(list)
}
```

`MutableList` は不変 (invariant) なクラスであるため、`MutableList<Any?>` を受け取る関数には、`MutableList<Any?>` オブジェクトしか渡せないことに注意してください。

スタープロジェクションは、その要素をリードオンリーにする性質を持っています。

```kotlin
val list1 = mutableListOf(1, 2, 3)
list1.add(100)  // OK

val list2: MutableList<*> = list1
list2.add(100)  // ERROR
```

ちなみに、Java ではワイルドカード文字として、`*` ではなく `?` を使用していますが、Kotlin と同様にコレクションクラスをリードオンリーにする作用があります。

```java
List<String> strList = new ArrayList<>();
strList.add("Hello");  // OK

List<?> roList = strList;
roList.add("World");  // ERROR
```


（コラム）分散という呼び方について
----

covariant（共変）や invariant（不変）、contravariant（反変）などの性質は、まとめて variance/variant と呼ばれます。
これの日本語訳はよく「分散」と記述されていますが、分散ってデータの散らばり具合を意味する用語なので、この訳はとても気持ちが悪いです。

型の互換性を示すものなのだから、もっとわかりやすく、互換性とか変換性のように呼ぶべきだと思いますが、ちょっと抽象的すぎなのであまりいい用語がなかったのかもしれません。
でも少なくとも、分散とか変な呼び方をして、理解しにくくしてしまうのは悪い傾向かと思います。
誰かもっといい名前を付けて欲しいです！

あと、型パラメータに制約を付けるときの upper bound（上限境界）とか、lower bound（下限境界）とかいう用語も、ネーミングがよくないですね。
わざと分かりにくくしているのではないかという噂も。。。

