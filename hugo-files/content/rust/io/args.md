---
title: "Rust でコマンドライン引数を扱う (std::env::args)"
url: "p/wu6gqz9/"
date: "2023-01-04"
tags: ["Rust"]
---

std::env::args の基本
----

Rust プログラムに渡されたコマンドライン引数を参照するには、[std::env::args メソッド](https://doc.rust-lang.org/stable/std/env/fn.args.html) を使用します。
この関数は、イテレート可能な [std::env::Args](https://doc.rust-lang.org/stable/std/env/struct.Args.html) オブジェクトを返します。
1 番目の要素は実行したファイルの名前（相対パス）で、2 番目以降にコマンドライン引数が格納されています。

{{< code lang="rust" title="src/main.rs" >}}
use std::env;

fn main() {
    for arg in env::args() {
        println!("{arg}");  // arg は単純な String 型
    }
}
{{< /code >}}

`cargo run` でコマンドを実行した場合も、コマンドライン引数を参照できます。

{{< code lang="console" title="実行例" >}}
$ cargo -q run 100 200 300
target/debug/sample
100
200
300
{{< /code >}}


Vec<String> 型で取得する (collect)
----

`std::env::args` 関数の戻り値 `Args` は、次のように __`Vec<String>`__ 型に変換してしまうと扱いやすいです。

```rust
use std::env;

fn main() {
    let args: Vec<String> = env::args().collect();

    // すべての内容をダンプ
    println!("{:?}", args);

    // 各パートを取り出す
    println!("The number of arguments is {}", args.len());
    println!("My path is {}", args[0]);
    println!("The remaining arguments are {:?}", &args[1..]);

    // 必須の引数を表現したい場合
    if args.len() < 2 {
        eprintln!("The dir_name argument is required");
        std::process::exit(1);
    }
    let dir_name = args.get(1).unwrap();
    println!("dir_name = {}", dir_name);
}
```


clap クレートを使う方法
----

`-o` や `--output` といった形のコマンドライン引数を扱いたい場合は、clap クレートを使うと簡単に実装できます。

- 参考: [Argument Parsing - Rust Cookbook](https://rust-lang-nursery.github.io/rust-cookbook/cli/arguments.html)

