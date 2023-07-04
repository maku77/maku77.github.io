---
title: "Rust の Result 型エイリアスでコードを簡潔にする"
url: "p/ez9gpw5/"
date: "2023-01-08"
tags: ["Rust"]
---

Rust の `std::io` モジュールの関数は、戻り値として __`Result<T, std::io::Error>`__ 型のオブジェクトを返すのですが、`Result::Err` バリアント部分の型は `std::io::Error` でいつも同じなので、簡潔に記述できるように、次のような [`std::io::Result`](https://doc.rust-lang.org/std/io/type.Result.html) というエイリアス型が定義されています。

{{< code lang="rust" title="std::io::Result" >}}
pub type Result<T> = Result<T, Error>;  // 後ろの Error は std::io::Error
{{< /code >}}

ここで定義している `Result` 型は、基礎となる `std::result::Result` とは別物なので注意してください。
`std::io::Result` という型を、`std::result::Result` と `std::io::Error` を組み合わせて定義しています。

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

上記の `get_string()` 関数の戻り値の型 `io::Result<String>` は、実際には `std::result::Result<String, std::io::Error>` と同等です。

関数の処理が成功したときに値を返す必要がない（`Ok` バリアントのデータが必要ない）場合は、もっとシンプルに __`io::Result<()>`__ という型になります。

```rust
fn foo() -> io::Result<()> {
    // ...
    Ok(())
}
```

__`Result<()>`__ といった表現が出てきた場合、どこかにこういったエイリアス型が定義されているはずです。

