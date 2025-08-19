---
title: "Androidメモ: 数値をある範囲内［min, max］に丸める (MathUtils.clamp)"
url: "p/qyh8496/"
date: "2020-08-31"
tags: ["android"]
aliases: ["/android/numstr/clamp.html"]
---

Android アプリの実装で、ある数字を指定した範囲 ［min, max］に収まるように調整したいときは、__`MathUtils.clamp()`__ というユーティリティ関数を使用できます。

{{< code lang="kotlin" title="例: x の値を [-100, 100] の範囲に収める" >}}
// import androidx.core.math.MathUtils

var x = -150
x = MathUtils.clamp(x, -100, 100)  //=> -100
{{< /code >}}

`clamp()` のような関数は Java のコアライブラリとして提供されていてもよさそうなのですが、現状は存在しないので Android がライブラリとして提供してくれています。
とはいっても、中身は下記のような簡単な実装です。

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

このようにすると、`else-if` ではなく全て `if` 分岐になってしまい効率が悪いです。

