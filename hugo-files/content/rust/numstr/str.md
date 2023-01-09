---
title: "Rust での文字列処理いろいろ"
url: "p/95o6n4k/"
date: "2023-01-02"
tags: ["Rust"]
draft: true
---

String インスタンスの生成
----

`String` 型はヒープ上に文字列データを保持するための型です。
一方で、文字列リテラルは、バイナリにハードコードされます。
次のコードは、文字列リテラル `"Hello"` と同じ内容の `String` インスタンスをヒープ上に確保しています。

{{< code lang="rust" title="文字列リテラル (&str) → String" >}}
let s = String::from("Hello");
{{< /code >}}

可変な文字列 (`String`) を作成するには、`let mut` で変数を定義します。

```rust
let mut s = String::new();
s.push_str("AAA");
s.push_str("BBB");
assert_eq!(s, "AAABBB");
```


文字列 → バイト値（配列） (as_byte)
----

```rust
let s = "ABC";
let bytes = s.as_bytes();  // &[u8] 型
println!("{:?}", bytes);  //=> [65, 66, 67]
```

文字リテラル → バイト値 (u8)
----

```rust
// 文字のバイト値を取得する
let byte = b'A'; // u8 型 (65)
```

文字 (char)  → String
----

```rust
c.to_string()
```

文字列リテラル → String
----

```rust
// `to_owned()` creates an owned `String` from a string slice.
let pasted  = WebEvent::Paste("my text".to_owned());
```

`to_owned` は過去の経緯から残っているが、今では `to_string` と同じ効果らしい。


文字列 → 数値
----

- 参考: [str#parse メソッド](https://doc.rust-lang.org/std/primitive.str.html#method.parse)

```rust
let s = "123";
let n = match s.parse() {
    Ok(num) => num,
    Err(_) => 0,
};
```

文字列 `s` が数値としてパースできれば、変数 `n` にその数値が格納されます。
パースに失敗した場合は、`0` が格納されます。
上記の `match` による分岐処理は、`unwrap_or` を使えば次のように簡潔に記述できます。

```rust
let s = "123";
let n = s.parse().unwrap_or(0);
```

`n` の型は、後ろで `0` という整数リテラルが使われているので、デフォルトの整数型である `i32` 型と推測されますが、次のように型注釈を入れれば別の数値型として扱うことができます。

```rust
let u8_val: u8 = "255".parse().unwrap_or(0);
let f64_val: f64 = "1.23".parse().unwrap_or(0.);
```

