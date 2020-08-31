---
title: "数値をある範囲内［min, max］に丸める (MathUtils.clamp)"
date: "2020-08-31"
---

ある数字 x を、min ～ max の範囲に収まるように調整するには、__`MathUtils.clamp()`__ を使用できます。

#### 例: x の値を [-100, 100] の範囲に収める

```kotlin
// import androidx.core.math.MathUtils

var x = -150
x = MathUtils.clamp(x, -100, 100)  //=> -100
```

`clamp()` のような関数は Java のコアライブラリとして提供されていてもよさそうなのですが、現状は存在しないので Android がライブラリとして提供してくれています。
とはいっても下記のような簡単な実装ですが。

```java
// MathUtils class
public static int clamp(int value, int min, int max) {
    if (value < min) {
        return min;
    } else if (value > max) {
        return max;
    }
    return value;
}
```

ちなみに上記の代わりに、次のように書けば同じことができると思うかもしれませんが、

```kotlin
x = max(min(x, 100), -100)
```

このようにすると `else-if` ではなく、すべて `if` 分岐になってしまうので、論理的には効率の悪いコードになってしまいます。

