---
title: "Rust の Result オブジェクトを消費せずに参照する (as_ref, as_mut)"
url: "p/z3gts64/"
date: "2023-01-08"
tags: ["Rust"]
---

通常、`Result` オブジェクトを `match` や `unwrap`、`ok` メソッドでハンドルすると、その `Result` は消費されます（所有権が移動します）。
この振る舞いを防ぐには、[as_ref](https://doc.rust-lang.org/std/result/enum.Result.html#method.as_ref) や [as_mut](https://doc.rust-lang.org/std/result/enum.Result.html#method.as_mut) メソッドを使います。

{{< code lang="rust" title="std::result::Result のメソッド" >}}
pub const fn as_ref(&self) -> Result<&T, &E>
pub fn as_mut(&mut self)   -> Result<&mut T, &mut E>
{{< /code >}}

`as_ref` メソッドで取得した `Result` オブジェクト経由で `Ok`/`Err` を参照すれば、それらのオブジェクトが消費されずに済みます。

```rust
let ok_opt = result.as_ref().ok();  // 借用 (borrow)
// ...
// ここでまだ result の Ok/Err は有効
```

