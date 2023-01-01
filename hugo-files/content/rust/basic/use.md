---
title: "Rust の use の使い方"
url: "p/9dpz9hr/"
date: "2022-12-29"
tags: ["Rust"]
draft: true
---

- 参考: [肥大化していくプロジェクトをパッケージ、クレート、モジュールを利用して管理する - The Rust Programming Language 日本語版](https://doc.rust-jp.rs/book-ja/ch07-00-managing-growing-projects-with-packages-crates-and-modules.html)

__`use`__ でライブラリをスコープに入れることができます。

```rust
use std::io;
use std::io::Write;
```

は、次のように 1 行にまとめて書くことができます。

```rust
use std::io::{self, Write};
```

