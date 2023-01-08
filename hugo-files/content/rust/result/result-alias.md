---
title: "Rust の Result のエイリアス型でコードを簡潔にする"
url: "p/ez9gpw5/"
date: "2023-01-08"
tags: ["Rust"]
---

`std::io` モジュールの関数は、戻り値として __`Result<T, std::io::Error>`__ 型のオブジェクトを返すのですが、後ろの `std::io::Error` はいつも同じなので、これを省略して記述できるように、次のような [`std::io::Result`](https://doc.rust-lang.org/std/io/type.Result.html) というエイリアス型が定義されています。

{{< code lang="rust" title="std::io::Result" >}}
pub type Result<T> = Result<T, Error>;  // 後ろの Error は std::io::Error
{{< /code >}}

`std::result::Result` とは別物なので注意してください。
`std::io::Result` を使うと、例えば、`String` を成功値 (`Ok` バリアントのフィールド) とする `Result` は、__`io::Result<String>`__ と簡潔に記述することができます。
下記が具体的な使用例です。

```rust
use std::io;

/// 標準入力から 1 行読み込みます
fn get_string() -> io::Result<String> {
    let mut buffer = String::new();
    io::stdin().read_line(&mut buffer)?;
    Ok(buffer)
}
```

この `io::Result<String>` は、実際には `std::result::Result<String, std::io::Error>` と同等です。

関数の処理が成功したときに値を返す必要がない（`Ok` バリアントのデータが必要ない）場合は、もっとシンプルに __`io::Result<()>`__ という型になります。

```rust
fn foo() -> io::Result<()> {
    // ...
    Ok(())
}
```

__`Result<()>`__ といった表現が出てきた場合、どこかにこういったエイリアス型が定義されているはずです。

