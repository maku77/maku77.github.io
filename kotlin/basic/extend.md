---
title: "継承可能なクラスを作成する"
date: "2019-05-10"
---

open と override
----

Kotlin のクラス定義を行うと、デフォルトでは継承できないようになっています。
**継承可能なクラスとして定義するには、`open` モディファイアを付ける**必要があります。
さらに、その中で**オーバーライド可能なメソッドにも `open` モディファイアを付ける**必要があります。

下記の `Animal` クラスは継承可能であり、`cry` メソッドがオーバーライドできるようになっています。

```kotlin
open class Animal(val name: String) {
    fun greet() {
        print("私は${name}です。")
        cry()
    }
    open fun cry() {
        println("ウー！")
    }
}
```

下記の `Cat`、`Dog` クラスは `Animal` クラスを継承し、`cry` メソッドをオーバーライドしています。
**オーバーライドしていることを示すために、メソッドの定義時に `override` キーワードを付ける**必要があります。

```kotlin
open class Cat : Animal("猫") {
    override fun cry() = println("ニャー！")
}

open class Dog : Animal("犬") {
    override fun cry() = println("ワンワン！")
}

fun main() {
    Animal("動物").greet()  // 私は動物です。ウー！
    Cat().greet()  // 私は猫です。ニャー！
    Dog().greet()  // 私は犬です。ワンワン！
}
```


それ以上オーバーライドを許可しない (final)
----

`override` の付いたメソッドはデフォルトで `open` な状態になっており、さらにその子クラスでオーバーライドすることが可能です。
**さらにオーバーライドされることを防ぐには、明示的に `final` キーワードを付けて `override` する**必要があります。

```kotlin
open class Cat : Animal("猫") {
    final override fun cry() = println("ニャー！")
}
```


親クラスのコンストラクタを呼び出す (super)
----

サブクラスのコンストラクタ呼び出しからは、間接的、あるいは直接的に親クラスのコンストラクタを呼び出す必要があります。
親クラスがパラメータなしの（自動生成された）コンストラクタしか持たない場合でも、その**パラメータなしのコンストラクタを使用するということを示すために、後ろに `()` を付けて呼び出しておく必要があります**。

```kotlin
open class Parent

class Child : Parent() {
    // ...
}
```

サブクラスがセカンダリ・コンストラクタしか持たない場合は、**`super`** キーワードを使って、親クラスのいずれかのコンストラクタを呼び出すようにすれば OK です。

```kotlin
open class Parent(val name: String) {
    constructor(dummy: Int) : this("ほげ")
}
class Child : Parent {
    constructor(name: String) : super(100)
}
```


抽象クラスを作成する (abstract class)
----

Kotlin でも Java と同様に、**`abstract` キーワードを使って抽象クラスを定義することができます**。
抽象クラスは 1 つ以上の抽象メソッド（実装のないメソッド）を定義することができ、このメソッドにも `abstract` キーワードを付けます。
`abstract` キーワードの付いたメソッドはオーバーライドすることを前提としているため、`open` を付ける必要はありません。

```kotlin
abstract class Parent {
    // オーバーライドして実装しなければいけない抽象メソッド（実装がない）
    abstract fun abstractMethod()

    // オーバーライド可能なメソッド（実装がある）
    open fun openMethod() = println("openMethod of Parent")

    // オーバーライドできない通常のメソッド
    fun normalMethod() = println("normalMethod of Parent")
}

class Child : Parent() {
    override fun abstractMethod() = println("abstractMethod in Child")
    override fun openMethod() = println("openMethod in Child")
}

fun main() {
    val obj = Child()
    obj.abstractMethod()  //=> abstractMethod in Child
    obj.openMethod()  //=> openMethod in Child
}
```

上記のサンプルを見ると分かるように、`abstract` メソッドと `open` メソッドの使い方にほとんど差はありません。
違いは、`abstract` メソッドがデフォルトで実装を持っていないということです。


シールクラスで継承可能なクラスを制限する (sealed class)
----

### シールクラスとは

クラスを定義するときに、**`sealed`** キーワードを使ってマークするとそのクラスは**シールクラス (sealed class)** となり、そのクラスのサブクラスとなれるクラスを制限することができます。
例えば、`Screen` クラスのサブクラスが、`Screen.Main`、`Screen.ProductList` しか存在しないことを明示することができます。

Kotlin はシールクラスを列挙型クラス (enum class) の強化版のようなものだと位置付けています。
enum 型は各インスタンスがシングルトンとしてのみ存在可能ですが、シールクラスのサブクラスは、シングルトンにすることもできますし、個別のインスタンスとして生成することもできます。

シールクラスは抽象クラスと同様の性質を持ちます。
デフォルトで継承可能 (`open`) なクラスとして定義され、`abstract` なメソッド、プロパティを含むことができます。
インスタンスを生成するときは、必ずサブクラスのインスタンスとして生成しなければいけません。

### シールクラスの利用例

シールクラスは利用例を見たほうが理解しやすいと思います。
例えば、列挙型クラスのようなものを作りたいのだけれど、場合によってはパラメータを渡して亜種となるオブジェクトを作らなければいけない、といったケースで利用できます。

ここでは、アプリケーションの「画面状態」を示す `Screen` クラスを考えてみます。
画面状態には下記の種類があります。

1. `Screen.Main` -- メイン画面
2. `Screen.ProductList` -- 全商品のリスト画面
3. `Screen.ProductDetail` -- ある商品の詳細画面（**商品 ID プロパティを含む**）

これらが 3 つの画面状態だけを示せればよいのであれば、従来の列挙クラスで 3 つの要素を定義してやれば解決です。
問題は、3 つ目の `Screen.ProductDetail` が、表示対象となる商品の ID をプロパティとして保持するという点です。
つまり、`Screen.ProductDetail` という 1 つの画面状態だけを示せばよいのではなく、`Screen.ProductDetail(1)` とか、`Screen.ProductDetail(2)` といった異なる画面状態を表現したいということです。

このように、列挙型の個々のオブジェクトに異なるプロパティを持たせたいという場面に出くわしたら、シールクラスの出番です。

```kotlin
sealed class Screen {
    object Main : Screen()
    object ProductList: Screen()
    data class ProductDetail(val productId: Int) : Screen()
}
```

<div class="note">
ここでは、シールクラスのサブクラスをネストして定義していますが、ネストさせる必要は必ずしもありません。
ただ、enum クラスの拡張としてシールクラスを利用するのであれば、このようにネストして定義しておいた方がわかりやすいでしょう。
</div>

上記のようにシールクラスを定義すると、`Screen.Main` や `Screen.ProductList` などのシングルトンは従来の列挙型要素のように扱うことができ、さらに、データクラスとして定義した `Screen.ProductDetail` には自身のプロパティとして `productId` を持たせることができます。

下記は、この `Screen` シールクラスの使用例です。
`transition` 関数に `Screen` オブジェクトを渡すと、その画面へ遷移するというユースケースを想定しています。

```kotlin
// 画面遷移の実装（だと過程）
fun gotoMain() = println("gotoMain")
fun gotoProductList() = println("gotoProductList")
fun gotoProductDetail(id: Int) = println("gotoProductDetail($id)")

// 渡されたスクリーンタイプによって画面遷移する
fun transition(screen: Screen) {
    when (screen) {
        is Screen.Main -> gotoMain()
        is Screen.ProductList -> gotoProductList()
        is Screen.ProductDetail -> gotoProductDetail(screen.productId)
    }
}

fun main() {
    val list = listOf(
        Screen.Main,
        Screen.ProductList,
        Screen.ProductDetail(1),
        Screen.ProductDetail(2)
    )
    list.forEach { transition(it) }
}
```

#### 実行結果

```
gotoMain
gotoProductList
gotoProductDetail(1)
gotoProductDetail(2)
```

上記の `when` 式の記述を見ると分かるように、`else` 節の記述が省略されています。
シールクラスを使用すると、コンパイラがすべてのサブクラス分岐をカバーできていることを把握できるようになるので、`else` 節によるフォールバック記述が必要なくなります。
この仕組みにより、enum 型クラスと同様、`when` 式での記述漏れを防ぐことができます。

