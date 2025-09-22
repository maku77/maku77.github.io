---
title: "Kotlinメモ: lateinit 変数が初期化されているかどうかを調べる (isInitialized)"
url: "p/qpc53iy/"
date: "2019-09-25"
tags: ["kotlin"]
aliases: [/kotlin/basic/lateinit-isinitialized.html]
---

isInitialized の概要
----

Kotlin 1.2 以降では、[`lateinit` 変数](/p/ymzvh9q/)が初期化済みかどうかを **`isInitialized`** を使って調べることができます。
あるクラスのプロパティが初期化済みかどうかを調べるには次のようにします。

```kotlin
if (::プロパティ.isInitialized) {
    // プロパティは初期化済み (代入済み）
}
```

直接プロパティにアクセスすると `UninitializedPropertyAccessException` が発生する可能性があるため、上記のようにプロパティ参照の仕組みを利用して参照するところがポイントです。


isInitialized の使用例
----

下記の `initSubject()` メソッドでは、`lateinit` 変数である `subject` が初期化されているかどうかを `isInitialized` で調べ、初期化されていない場合にのみ初期化を行うようにしています。
`foo()` や `bar()` メソッドの中で最初に `initSubject()` を呼び出すことで、`subject` が確実に初期化されてから参照されるようにしています。

```kotlin
class MyClass {
    private lateinit var subject: Subject

    private fun initSubject(context: Context) {
        if (::subject.isInitialized) {
            return
        }
        // ここで context を使って subject を初期化する
    }

    fun foo(context: Context) {
        initSubject(context)
        // ... subject を使う処理 ...
    }

    fun bar(context: Context) {
        initSubject(context)
        // ... subject を使う処理 ...
    }

    //...
}
```

`subject` の初期化には `foo()` や `bar()` の呼び出し時に渡される `Context` オブジェクトを必要としているため、このタイミングで初期化するしか方法がないと想定しています。

