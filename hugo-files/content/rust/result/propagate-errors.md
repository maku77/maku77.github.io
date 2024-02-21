---
title: "Rust の ? 演算子でエラーを伝搬させる"
url: "/p/6nce7nw/"
date: "2024-02-21"
tags: ["Rust"]
---

エラーの伝搬 (propagating errors) とは
----

Rust でエラーを返す可能性のある関数（__`Result`__ を返す関数）を呼び出すとき、その戻り値を受けて直ちにエラー処理を行うことは少なく、多くの場合は呼び出し元にエラーを返す（__`Error`__ 値を伝搬させる）ことになると思います。

```rust
let book = match get_book(123) {
    Ok(book) => book,
    Err(err) => return Err(err.into()),  // 呼び出し元にエラーを伝搬
};
```

このようなケースで、毎回 `match` を使った条件分岐コードを記述していると、冗長なコードで溢れかえってしまいます。
そこで、Rust はエラーを呼び出し元に伝搬させるための __`?`__ 演算子を用意しています（Rust 1.13 以降）。


? 演算子の使い方
----

```rust
let book = get_book(123)?;
```

このように記述すると、`get_book()` 関数の戻り値が `Result::Ok` だった場合はそれを `unwrap()` した値が `book` 変数に格納され、戻り値が `Result::Err` だった場合はそのエラーをそのまま return してくれます。
つまり、前述の `match` を使ったコードと同じ振る舞いを 1 行で表現することができます。

`?` 演算子は、__`Option`__ を戻り値として返す関数の中でも使用することができます。
その場合、エラー発生時は __`Option::None`__ を返すという振る舞いになります。


main 関数での具体的な使用例
----

`main()` 関数の戻り値の型が、__`Result<(), Box<dyn std::error::Error>>`__ になっているのを見たことがあるかもしれません。
`Box<dyn std::error::Error>` という型は、`Error` トレイトを実装したあらゆる型を保持することができる `Box` 型です。
このような表現にしておくことで、その関数はあらゆる `Error` 型を返す可能性があることを示すことができます。

例えば、下記の `main()` 関数は戻り値として汎用的な `Result` 型を返すように定義しています。
この関数の中では、どんなエラーを返す関数呼び出しでも `?` 演算子を使うことができます。
`main()` 関数の中から、明示的に独自のエラーを返すこともできます。

{{< code lang="rust" title="src/main.rs" hl_lines="4" >}}
use std::{env, fs::File, io::Read};

// どんなエラーも扱える Result 型
type Result = std::result::Result<(), Box<dyn std::error::Error>>;

fn main() -> Result {
    let args: Vec<String> = env::args().collect();
    if args.len() < 2 {
        eprintln!("Usage: {} <file>", args[0]);
        return Err("No file specified".into());
    }

    // ファイルを開いて読み込む
    let filename = &args[1];
    let mut file = File::open(filename)?;  // エラーの可能性
    let mut buf = String::new();
    file.read_to_string(&mut buf)?;  // エラーの可能性

    // ファイルの内容を出力
    println!("{}", &buf);

    Ok(())
}
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ cargo run -q -- hoge.txt
Error: Os { code: 2, kind: NotFound, message: "指定されたファイルが見つかりません。" }
{{< /code >}}

