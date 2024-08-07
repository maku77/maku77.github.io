---
title: "列挙型 (enum) の値をインデックスで取得する"
date: "2020-03-11"
---

次の例では、列挙型クラス `Fruits` に static な関数 `fromOrdinal()` を定義し、インデックス番号 (0, 1, 2) から `Fruits` インスタンスを取得できるようにしています。
範囲外のインデックスを指定した場合は、`NoSuchElementException` 例外が発生します。

#### Fruit.kt

```kotlin
enum class Fruits {
    APPLE, BANANA, GRAPE;

    companion object {
        fun fromOrdinal(ordinal: Int) : Fruits {
            return values().first { it.ordinal == ordinal }
        }
    }
}
```

#### 使用例

```kotlin
val f1 = Fruits.fromOrdinal(0)  //=> Fruits.APPLE
val f2 = Fruits.fromOrdinal(1)  //=> Fruits.BANANA
val f3 = Fruits.fromOrdinal(2)  //=> Fruits.GRAPE
val f4 = Fruits.fromOrdinal(3)  //=> NoSuchElementException
```

