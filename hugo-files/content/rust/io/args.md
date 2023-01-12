---
title: "Rust でコマンドライン引数を扱う (1) std::env::args"
url: "p/wu6gqz9/"
date: "2023-01-04"
tags: ["Rust"]
---

Rust プログラムに渡されたコマンドライン引数を扱う方法として、[std::env::args 関数](https://doc.rust-lang.org/stable/std/env/fn.args.html) を使う方法を説明します。
この関数は標準で呼び出すことができるのでお手軽ですが、リッチなコマンドライン引数を提供したいときは、[clap クレートを使う方法](/p/bdp2doy/) がおすすめです。

std::env::args の基本
----

`std::env::args` 関数は、イテレート可能な [std::env::Args](https://doc.rust-lang.org/stable/std/env/struct.Args.html) オブジェクトを返します。
1 番目の要素には実行したファイルの名前（相対パス）が含まれており、2 番目以降にコマンドライン引数が格納されています。

{{< code lang="rust" title="src/main.rs" >}}
use std::env;

fn main() {
    for arg in env::args() {
        println!("{arg}");  // arg は単純な String 型
    }
}
{{< /code >}}

`cargo run` でコマンドを実行する場合、プログラムに渡すコマンドライン引数は、次のように __`--`__ の後ろに指定します。

{{< code lang="console" title="実行例" >}}
$ cargo -q run -- --aaa 100 200
target/debug/sample
--aaa
100
200
{{< /code >}}

`--` というセパレーターを入れないと、`--aaa` オプションが、`cargo` 側のオプションとして渡されてしまうので注意してください。


ベクター型で処理する (collect)
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


次のステップ
----

- [Rust でコマンドライン引数を扱う (2) clap クレート](/p/bdp2doy/)

