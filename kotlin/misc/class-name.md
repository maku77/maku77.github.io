---
title: "クラス名を文字列で取得する"
date: "2019-08-08"
---

Kotlin クラス名を文字列として取得したい場合、例えば、`MyClass` クラスの名前を `MyClass` という文字列として取得したい場合は次のように記述します。

```kotlin
val name = MyClass::class.java.simpleName
```

Java の場合は (`MyClass.simpleName`) と記述できたので、それと比べると Kotlin は若干記述が冗長になります。
ただ、クラス名をそのまま文字列リテラルでハードコードすると、クラス名のリファクタリング時に修正し忘れたりするので、このようにクラス名を取得した方がよいケースはよくあります。

例えば、Android アプリのログ (Logcat) には、タグ情報としてクラス名を入れたりしますが（個人的にはアプリ名を入れるべきだと思いますが）、このタグは下記のように初期化することができます。

```kotlin
class Sample {
    companion object {
        private val TAG = Sample::class.java.simpleName
    }

    fun sayHello() {
        Log.i(TAG, "Hello")
    }
}
```

