---
title: "継承可能なクラスを作成する (open, abstract, override)"
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


参考
----

- [シールクラスで継承可能なクラスを制限する (sealed class)](./sealed-class.html)

