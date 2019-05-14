---
title: "コンパニオンオブジェクトとクラス内オブジェクト宣言の違い"
date: "2019-05-14"
---

コンパニオンオブジェクトは、クラスに付随するシングルトンオブジェクトとして参照できます。
また、クラス内でオブジェクト宣言を行うと、これまたシングルトンオブジェクトとして参照できます。
これらはいったい何が違うのでしょうか？

```kotlin
class MyClass {
    // 名前付きコンパニオンオブジェクト
    companion object Foo {
        fun hello() = println("Foo.hello")
    }

    // オブジェクト宣言
    object Bar {
        fun hello() = println("Bar.hello")
    }
}

fun main() {
    MyClass.Foo.hello()  //=> Foo.hello
    MyClass.Bar.hello()  //=> Bar.hello
}
```

一見するとまったく違いがありませんが、シングルトンオブジェクトが生成されるタイミングに微妙な違いがあります。

* <b>コンパニオンオブジェクト</b>: 外側のクラスが参照されたときに生成される（Java の static initializer と同様）
* <b>クラス内オブジェクト宣言</b>: 自分自身が参照されたときに生成される

次のように、それぞれのシングルトン定義に初期化ブロック (`init`) を作ってやると、初期化の順序を確認することができます。

```kotlin
class MyClass {
    companion object Foo {
        init { println("Foo.init") }
        fun hello() = println("Foo.hello()")
    }

    object Bar {
        init { println("Bar.init") }
        fun hello() = println("Bar.hello()")
    }
}

fun main() {
    println("1")
    val obj = MyClass()
    println("2")
    MyClass.Foo.hello()
    println("3")
    MyClass.Bar.hello()
}
```

#### 実行結果

```
1
Foo.init
2
Foo.hello()
3
Bar.init
Bar.hello()
```

コンパニオンオブジェクトである `Foo` インスタンスは、外側のクラスの `MyClass` がインスタンス化されるときに同時に生成されています（だからコンパニオンって言うんですね）。
一方で、オブジェクト宣言された `Bar` オブジェクトは、`Bar.hello()` 関数が呼び出された時点で初めて生成されます。

このような性質があるため、クラス内で JNI の API を呼び出す場合などは、コンパニオンオブジェクトの初期化ブロック (`init`) が、ライブラリのロードタイミングとして適切です。

```kotlin
class MyClass {
    companion object {
        init {
            System.loadLibrary("mylib")
        }
    }
    // JNI の API を呼び出すコード
}
```

これは、下記のような Java の static initializer の使い方と同様です。

```java
class MyJavaClass {
    static {
        System.loadLibrary("mylib");
    }
    // JNI の API を呼び出すコード
}
```

あと、コンパニオンオブジェクトはクラス内で 1 つまでしか定義できないという制約があります。
クラス内に複数のシングルトンオブジェクトを定義したいときは、オブジェクト宣言を使用する必要があります。

