---
title: "Rust の format!、println!、eprintln! マクロによる文字列フォーマット"
url: "p/eqqdxii/"
date: "2023-07-09"
tags: ["Rust"]
draft: true
---

{{% private %}}
- https://doc.rust-lang.org/std/macro.format.html
- https://doc.rust-lang.org/std/macro.print.html
- https://doc.rust-lang.org/std/macro.println.html
- https://doc.rust-lang.org/std/macro.eprint.html
- https://doc.rust-lang.org/std/macro.eprintln.html
{{% /private %}}

Rust でフォーマット文字列を使って `String` インスタンスを生成したり、標準出力へ出力したりするには、次のようなマクロを使用します。

- __`format!`__ ... `String` インスタンスを生成します。
- __`print!`__ / __`println!`__ ... 標準出力 (`io::stdio`) へ出力します。`println` の方は改行が付加されます。
- __`eprint!`__ / __`eprintln!`__ ... 標準エラー出力 (`io::stderr`) へ出力します。`eprintln` の方は改行が付加されます。


print / println マクロ
----

```rust
// 16 進表記
let num = 0xff0000;
println!("{:0x}", num);   //=> "ff0000"
println!("{:0X}", num);   //=> "ff0000"
println!("{:08x}", num);  //=> "00ff0000"
println!("{:08X}", num);  //=> "00FF0000"
```

```rust
let text_length: Option<usize> = text.as_ref().map(|s| s.len());
println!("still can print text: {text:?}");
```

- 変数名を先に指定して、`{err:?}` という書き方も可能。`{num:08x}` とも書ける。


標準出力への出力 (eprint, eprintln)
----

`println!` マクロの出力先を「標準エラー出力 (`io::stderr`)」に変えたものが、__`eprintln!`__ マクロです。
エラーメッセージを `eprintln!` で出力するようにしておけば、仮にユーザーがコマンド実行時に出力先をリダイレクト (`> filename`) した場合でも、エラーメッセージだけは画面上に表示することができます。

{{< code lang="rust" hl_lines="6" >}}
use std::{env, process};

fn main() {
    let args: Vec<String> = env::args().collect();
    if args.len() < 2 {
        eprintln!("Error: At least one argument is required");
        process::exit(1);
    }
    println!("{:?}", args);
}
{{< /code >}}

