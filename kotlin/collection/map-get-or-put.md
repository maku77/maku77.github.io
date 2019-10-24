---
title: "マップの値を始めて取得しようとしたときに初期化する（Map の遅延初期化）(getOrPut)"
date: "2019-10-24"
---

`MutableMap` の [getOrPut メソッド](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/get-or-put.html) を使用すると、指定されたキーに対応する値が見つからないときに、その値をラムダ式によって初期化した上で返すことができます。

```kotlin
val map = mutableMapOf<String, Int>()
println(map.get("foo"))  //=> null （値が存在しない）
println(map.getOrPut("foo", { 0 }))  //=> 0（get と同時に初期値がセットされる）
println(map.get("foo"))  //=> 0 （値がセットされている）
```

`getOrPut()` を利用すると、マップ値の遅延初期化を行えます。
キー＆値の形式の固定値を取得したいのだけど、各値の取得には多少コストがかかるという場合のキャッシュ用途で利用できます（値が変わらない前提）。

```kotlin
class UserDb {
    // ユーザーの年齢のキャッシュ
    private var _userAge = mutableMapOf<String, Int>()

    // ユーザーの年齢を取得する（キャッシュを利用）
    fun getAge(name: String) : Int =
        _userAge.getOrPut(name, { getAgeWithoutCache(name) })

    private fun getAgeWithoutCache(name: String) : Int {
        // 初期値の計算に時間がかかるという想定
        return 10
    }
}

fun main() {
    val userDb = UserDb()
    val age : Int = userDb.getAge("maku")
    println(age)
}
```

