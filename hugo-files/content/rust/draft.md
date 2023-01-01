---
title: "(DRAFT) Rust 雑多メモ"
url: "p/jkv7gpz/"
date: "2022-12-30"
tags: ["Rust"]
draft: true
---

文字列 → 数値
----

- 参考: [str#parse メソッド](https://doc.rust-lang.org/std/primitive.str.html#method.parse)

```rust
let four: u32 = "4".parse().unwrap();
assert_eq!(4, four);
```


Result 型
----

- `is_err()` ... エラーの場合（値が `Err` variant の場合）に `true` を返します。

`Result` が `Ok` 値を持つかをチェックしつつ、その値を取り出す `if let` 構文。

```rust
if let Ok(v) = result_value {
  println!("value = {}", v);
}
```

`Result#unwrap()` で `Ok` 値を取り出すことができるが、保持している値が `Err` の場合は panic になるのでプロダクトコードでは使わない方がよい。


Option 型
----

`Option` が `Some` 値を持つかをチェックしつつ、その値を取り出す `if let` 構文。

```rust
if let Some(v) = option_value {
  println!("value = {}", v);
}
```

`Option#unwrap()` で `Some` 値を取り出すことができるが、保持している値が `None` の場合は panic になるのでプロダクトコードでは使わない方がよい。


cargo doc --open
----

全ての依存クレートが提供するドキュメントをローカルでビルドして、Web ブラウザーで開く。'


match による分岐処理
----

```rust
let s = "123";
let n: u32 = match s.parse() {
    Ok(num) => num,
    Err(_) => 0,
};
```

文字列 `s` が数値としてパースできれば、変数 `n` にその数値が格納されます。
パースに失敗した場合は、`0` が格納されます。

