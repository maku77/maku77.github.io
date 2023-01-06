---
title: "Rust での文字列処理いろいろ"
url: "p/95o6n4k/"
date: "2023-01-02"
tags: ["Rust"]
draft: true
---

文字列 → バイト値（配列） (as_byte)
----

```rust
let s = "ABC";
let bytes = s.as_bytes();  // &[u8] 型
println!("{:?}", bytes);  //=> [65, 66, 67]
```

文字リテラル → バイト値
----

```rust
let byte = b'A'; // u8 型 (65)
```


文字列リテラル → String
----

```rust
// `to_owned()` creates an owned `String` from a string slice.
let pasted  = WebEvent::Paste("my text".to_owned());
```
