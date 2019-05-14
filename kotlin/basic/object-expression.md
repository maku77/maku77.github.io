---
title: "オブジェクト式で無名オブジェクトを生成する (object expression)"
date: "2019-05-14"
---

オブジェクト式の基本
----

[オブジェクト宣言 (object declaration)](./object-declarations.html) は、クラスの定義とインスタンス生成を同時に行う構文ですが、これと似たものに **オブジェクト式 (object expression)** があります。

オブジェクト宣言では、`object MyObj { ... }` のように、クラス定義と同時に名前付きのオブジェクトを作成していましたが、オブジェクト式はその場で名前なしの無名オブジェクト (anonymous objects) を生成します。
オブジェクト式を使って生成したオブジェクトは、変数に格納するか、関数に渡すパラメータとして使用します（そうしないと参照できないため）。

下記の例では、オブジェクト式を使って、`greet()` 関数を持つ無名オブジェクトを生成しています。
生成されたオブジェクトを使用するために、いったん `myObj` という変数に格納しています。

```kotlin
val myObj = object {
    fun greet() = println("Hello")
}

myObj.greet()
```

（これだけ見ると、JavaScript のオブジェクトにとても似ていますね）

オブジェクト式は、何らかのインタフェースを実装する無名オブジェクトを生成するときによく使われます。

```kotlin
interface ButtonListener {
    fun onClicked()
}

val listener = object : ButtonListener {
    override fun onClicked() = println("押されたよ")
}
```

オブジェクト式により生成した無名オブジェクトを、関数のパラメータに直接渡してしまうこともできます。

```kotlin
button.addListener(object : ButtonListener {
    override fun onClicked() = println("押されたよ")
})
```

ただし、このように記述すると、このコードが実行されるたびにオブジェクトが生成されることに注意してください。

ちなみに、オブジェクト宣言 (object declaration) を使った場合は、下記のような記述になりますが、この場合は、イコール演算子の右側に書いたり、そのまま関数のパラメータで渡したりすることはできません（オブジェクト宣言は「式」ではないので、値を持ちません）。

```kotlin
// オブジェクト宣言 (object declaration) の場合
object MyButtonListener : ButtonListener {
    override fun onClicked() = println("押されたよ")
}
```

オブジェクト式から外部変数を参照する
----

オブジェクト式のクラス実装部分からは、その外側のスコープにある変数や関数を参照することができます。
これは Java の無名インナクラスと同様ですが、Kotlin では `final` 修飾されていない変数を参照することができ、さらに、その値を変更することもできます。

```kotlin
fun startCounting(button: Button) {
    var count = 0

    button.addListener(object : ButtonListener {
        override fun onClicked() {
            println("${++count} 回クリックされたよ")
        }
    })
}
```


Java の無名インナークラスに比べた利点
----

Kotlin のオブジェクト式は、Java では無名インナークラスに相当します。

#### Java の無名オブジェクト生成

```java
button.addListener(new ButtonListener() {
    @Override
    public void onClick() {
        System.out.println("Hello");
    }
});
```

Java の無名インナークラスの場合、その構文の都合上、1 つまでしかインタフェースを実装できませんでしたが、**Kotlin のオブジェクト式では、任意の数のインタフェースを実装することができます**（前述しましたが、インタフェースを全く実装しないプレーンなオブジェクトも作成できます）。

```kotlin
val listener = object : IFoo, IBar, IHoge {
    // ...
}
```

