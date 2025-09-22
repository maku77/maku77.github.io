---
title: "Kotlinメモ: lateinit による変数の初期化"
url: "p/ymzvh9q/"
date: "2019-09-25"
tags: ["kotlin"]
aliases: [/kotlin/basic/lateinit.html]
---

lateinit 変数とは
----

クラスのプロパティは、できるだけ `val`（再代入不可）変数として定義すると保守性の高いコードを作成することができます。
`val` 変数は生成時に参照先のオブジェクトを確定しておく必要があるため、変数の宣言時に代入するか、`init` ブロックでの代入が必要です。

```kotlin
class Potter {
    private val magics = mutableListOf<String>()
    //...
}
```

しかし、Android などのフレームワーク上で実装を行っている場合、どうしてもフレームワークによる初期化関数の呼び出しの段階まで変数の初期化ができないことがあります。
このようなケースで `val` 変数の代わりに使用できるのが **`lateinit`** 変数です。

下記は Android の `Activity` 実装の抜粋です。
ここでは、`surfaceView` 変数を `lateinit` で宣言することにより、初期化タイミングを `onCreate()` 呼び出しまで遅延させています。
`lateinit` 変数は `var` で宣言しないといけないことに注意してください。

```kotlin
class MainActivity : Activity() {
    private lateinit var surfaceView: SurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        surfaceView = SurfaceView(this)
    }

    //...
}
```

変数を `lateinit` 宣言することにより、**non-null な初期化済みの変数として参照することができる**ようになります。
ただし、参照する前に必ず初期化（代入）されていることが条件となります。
初期化前に `lateinit` 変数を参照すると、次のような **`UninitializedPropertyAccessException`** が発生します。

```
Exception in thread "main" kotlin.UninitializedPropertyAccessException:
    lateinit property book has not been initialized
```


lateinit 変数の使用例
----

### DI による依存注入

`lateinit` による変数初期化は、Dagger などの DI 系ツールで依存関係を注入するときにもよく使用されます。

```kotlin
class MyClass {
    @Inject
    lateinit var logger: Logger

    //...
}
```

上記の `logger` 変数は、Dagger により `MyClass` オブジェクトが生成されるときに自動的に初期化されます。

### テストクラスの SetUp

下記は Kotlin のサイトに紹介されている例ですが、テストクラスのセットアップメソッドが呼ばれた段階で `subject` 変数の初期化を行っています。

```kotlin
public class MyTest {
    lateinit var subject: TestSubject

    @SetUp fun setup() {
        subject = TestSubject()
    }

    @Test fun test() {
        subject.method()  // dereference directly
    }
}
```

セットアップメソッドが最初に実行されることが保障されているので、このように `lateinit` 宣言することができます。


参考情報
----

- [lateinit 変数が初期化されているかどうかを調べる (isInitialized)](/p/qpc53iy/)

