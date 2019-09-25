---
title: "lateinit 変数が初期化されているかどうかを調べる (isInitialized)"
date: "2019-09-25"
---

isInitialized の概要
----

Kotlin 1.2 以降では、[lateinit 変数](./lateinit.html)が初期化済みかどうかを **`isInitialized`** を使って調べることができます。
あるクラスのプロパティが初期化済みかどうかを調べるには次のようにします。

```kotlin
if (::プロパティ.isInitialized) {
    // プロパティは初期化済み (代入済み）
}
```

直接プロパティにアクセスすると `UninitializedPropertyAccessException` が発生する可能性があるため、上記のようにプロパティ参照の仕組みを利用して参照するところがポイントです。


isInitialized の使用例
----

下記の例では、`useSubject()` メソッドを呼び出したときに、`lateinit` 変数である `subject` が初期化されていないときのみ初期化を行うように実装しています。
ここでは、`subject` の初期化に `useSubject()` で渡されたパラメータを使用するため、このタイミングで初期化するしか方法がないと想定しています。

```kotlin
class MyClass {
    private lateinit var subject: Subject

    fun useSubject(context: Context) {
        initSubject(context)
        // ここで、subject を使用した処理
    }

    private initSubject(context: Context) {
        if (::subject.isInitialized) {
            return
        }
        // ここで、subject の初期化（context を使用する）
    }

    //...
}
```

