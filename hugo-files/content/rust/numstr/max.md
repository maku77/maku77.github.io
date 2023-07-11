---
title: "Rust で複数の数値の中から最小値・最大値を見つける (min, max)"
url: "p/e3o2ra3/"
date: "2023-07-09"
tags: ["Rust"]
---

2 つの数値から大きい方を取り出す
----

Rust の標準ライブラリが提供しているジェネリクス関数 [std::cmp::max](https://doc.rust-lang.org/std/cmp/fn.max.html) を使うと、2 つの数値のうち大きい方の値を取り出すことができます。

```rust
let larger = std::cmp::max(2, 5);  //=> 5

// 呼び出し時のコードを短くしたいときは次のように use 宣言します
// use std::cmp::max;
// let larger = max(2, 5);
```

また、数値のプリミティブ型 (`i32` や `f64`) は [core::cmp::Ord](https://doc.rust-lang.org/core/cmp/trait.Ord.html) トレイトを実装しているため、`max` メソッドでも上記と同様の処理を行えます。

```rust
let larger = 2.max(5);  //=> 5
```

前者の関数型と比較して、後者のメソッド型は直感的に理解しにくいかもしれませんが、場面によっては便利です。
例えば、`num.max(0)` という表現は、`num` が負の値だったときに、0 になるよう修正する という処理になります。

- 参考: [数値を特定の範囲に収まるように修正する (`clamp`, `min`, `max`)](/p/23fd7nv/)


複数の数値の中から最大値を取り出す
----

数値型のベクターや配列から、最大の値を持つ値を取り出すには次のようにします。

{{< code lang="rust" title="Vec<i32> から最大値を取り出す" >}}
let nums: Vec<i32> = vec![3, 1, 5, 2, 4];
// let nums: [i32; 5] = [3, 1, 5, 2, 4];

if let Some(max) = nums.iter().max() {
    println!("Max value: {}", max);
} else {
    println!("nums is empty");
}
{{< /code >}}

{{< code title="実行結果" >}}
Max value: 5
{{< /code >}}

この場合、`max` メソッドの戻り値は [Option 型](/p/9m6m5m3/) になっており、ベクターや配列が空の場合は `None` を返すことに注意してください。

