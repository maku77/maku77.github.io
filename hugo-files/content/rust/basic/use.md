---
title: "Rust の use の使い方"
url: "p/9dpz9hr/"
date: "2022-12-29"
tags: ["Rust"]
draft: true
---

{{% private %}}
- 参考: [肥大化していくプロジェクトをパッケージ、クレート、モジュールを利用して管理する - The Rust Programming Language 日本語版](https://doc.rust-jp.rs/book-ja/ch07-00-managing-growing-projects-with-packages-crates-and-modules.html)
{{% /private %}}

Rust の __`use`__ ディレクティブでは、ライブラリモジュールを使用することを宣言します。
より言語的な表現をすると、「ライブラリをスコープに入れる」という操作を行います。

```rust
use std::io;
use std::io::Write;
```

上記の宣言は、次のように 1 行にまとめて書くことができます。

```rust
use std::io::{self, Write};
```

