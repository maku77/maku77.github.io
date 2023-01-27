---
title: "Rust で文字列型 (String) を扱う"
url: "p/vakbzyc/"
date: "2023-01-25"
tags: ["Rust"]
draft: true
---

- 文字列スライスの型は __`&str`__ で、`u8` バイトシーケンスへの参照を表す。
  - 文字列リテラルは __`&'static str`__ で表現される。


文字列スライス (`&str`) を受け取り、文字列スライスを返す例。

```rust
fn country(id: &str) -> &str {
    match id {
        "ja" => "Japan",
        "us" => "United States",
        _ => "Unknown",
    }
}

println!("{}", country("ja"));  //=> Japan
```


文字列をスペースで分割する (split_whitespace)
----

```rust
let text = "  AAA  BBB CCC ";

for word in text.split_whitespace() {
    println!("[{}]", word);
}
```

{{< code title="実行結果" >}}
[AAA]
[BBB]
[CCC]
{{< /code >}}

単語の前後の連続するスペースは削除してくれます。

