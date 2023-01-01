---
title: "Rust でキーボードからの入力を取得する"
url: "p/eamw7fp/"
date: "2022-12-29"
tags: ["Rust"]
---

[std::io::Stdin](https://doc.rust-lang.org/std/io/struct.Stdin.html) の __`read_line`__ 関数を使うと、ユーザーのキーボード入力を読み取ることができます。

{{< code lang="rust" title="main.rs" hl_lines="9" >}}
use std::io::{self, Write};

fn main() {
    print!("Please input your name: "); // プロンプトを表示して入力を促す
    io::stdout().flush().unwrap(); // 上記出力を強制フラッシュ

    let mut line = String::new(); // 入力用のバッファ
    io::stdin()
        .read_line(&mut line) // キーボードからの入力（標準入力）を 1 行読み込む
        .expect("Failed to read line"); // 戻り値の Result が Err の場合は終了

    println!("Hi, {}!", line.trim_end()); // 末尾の改行コードは trim_end で削除可能
}
{{< /code >}}

{{< code title="実行例" >}}
Please input your name: Maku
Hi, Maku!
{{< /code >}}

