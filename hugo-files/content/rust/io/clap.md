---
title: "Rust でコマンドライン引数を扱う (2) clap クレート"
url: "p/bdp2doy/"
date: "2023-01-12"
tags: ["Rust"]
---

{{% private %}}
- [Argument Parsing - Rust Cookbook](https://rust-lang-nursery.github.io/rust-cookbook/cli/arguments.html)
  - [derive マクロの説明](https://docs.rs/clap/latest/clap/_derive/)
  - [Command line argument parsing in Rust using Clap - LogRocket Blog](https://blog.logrocket.com/command-line-argument-parsing-rust-using-clap/)
{{% /private %}}

clap とは？
----

[clap クレート](https://docs.rs/clap/) は、コマンドライン引数のパーサーライブラリで、次のような機能を備えています。

- 一般的なオプション形式（`-a` や `--name val`）や、サブコマンド形式の引数のパース
- ヘルプ出力 (usage) の自動生成
- シェル用の入力補完スクリプト生成（clap_complete クレート）

`derive` マクロにより多くのボイラプレートコードを自動生成してくれるので、コードをシンプルに保ちつつ、高度なコマンドライン引数処理を行うことができます。


clap を使う準備
----

`clap` クレートを使うために、最初に `Cargo.toml` に依存関係を追加しておきます。

{{< code lang="console" title="clap クレートを使う準備" >}}
$ cargo add clap --features derive
{{< /code >}}

`derive` マクロを有効にするために、`--features derive` オプションが必要です。
次のように依存関係が追加されていれば OK です。

{{< code lang="toml" title="Cargo.toml" >}}
[dependencies]
clap = "4.0.32"
{{< /code >}}


clap の基本
----

任意の構造体定義に、`clap` が提供する __`#[derive(Parser)]`__ マクロを付加することで、コマンドライン引数用のバッファとして使えるようになります。

{{< code lang="rust" title="src/main.rs" >}}
use clap::Parser;

/// ヘルプ出力の最初に表示されるテキスト
#[derive(Parser)]
struct Args {
    /// 1 番目のパラメーターの説明として表示されるテキスト
    first: String,
    /// 2 番目のパラメーターの説明として表示されるテキスト
    second: u32,
}

fn main() {
    let args = Args::parse();
    println!("{:?}, {:?}", &args.first, &args.second);
}
{{< /code >}}

上記のように定義した `Args` 構造体は、自動的に __`parse`__ メソッドを備えるようになり、これを `main` 関数で呼び出すことで、コマンドライン引数をパースしてフィールドに格納してくれます。

下記は、正しくコマンドライン引数を指定した場合の実行結果です。

{{< code lang="console" title="実行例" >}}
$ ./myapp aaa 100
"aaa", 100
{{< /code >}}

{{% note title="cargo コマンドで起動する場合" %}}
上記の実行例は、`myapp` という名前の実行ファイルを生成したと想定しています。
`cargo` コマンドで直接実行する場合は、次のような感じでコマンドライン引数を指定します。

```console
$ cargo -q run -- aaa 100
```

`-q` オプションは `Cargo` の冗長なメッセージを抑制するもので、`--` はそれ以降のコマンドライン引数をプログラム側に渡すことを示しています。
{{% /note %}}

構造体の `first` フィールドに `"aaa"` という文字列が格納され、`second` フィールドに `100` という数値が格納されています。
`second` フィールドは、`u32` 型として定義されているので、ユーザーが数値以外を入力すると、`clap` はヘルプメッセージを出力してプログラムを終了 (`Error#exit`) します。
メッセージの内容は `clap` が自動生成してくれます。

{{< code lang="console" title="不正なコマンドライン引数を指定した場合" >}}
$ ./myapp aaa bbb
error: Invalid value 'bbb' for '<SECOND>': invalid digit found in string

For more information try '--help'
{{< /code >}}

これだけでも十分に便利ですが、`clap` はさらに __`-h`__ や __`--help`__ オプションを自動で実装してくれます。
表示されるヘルプメッセージは、構造体のドキュメンテーションコメント (__`///`__) の記述から自動生成されます。

{{< code lang="console" title="実行例" >}}
$ ./myapp --help
ヘルプ出力の最初に表示されるテキスト

Usage: myapp <FIRST> <SECOND>

Arguments:
  <FIRST>   1 番目のパラメーターの説明として表示されるテキスト
  <SECOND>  2 番目のパラメーターの説明として表示されるテキスト

Options:
  -h, --help  Print help information
{{< /code >}}


Linux スタイルのコマンドライン引数
----

`-f` や `--foo` のような Linux スタイルのコマンドライン引数を定義するには、構造体のフィールドに __`#[arg(short, long)]`__ 属性を付加します。

```rust
/// Simple program to greet a person
#[derive(Parser)]
struct Args {
    /// Name of the person to greet
    #[arg(short, long)]
    name: String,

    /// Number of times to greet
    #[arg(short, long)]
    count: u8,

    /// Run in debug mode
    #[arg(short, long)]
    debug: bool,
}
```

デフォルトでは、構造体のフィールド名 (`name`) から、`-n` や `--name` といったオプションが自動生成されます。

```console
$ ./myapp --help
Simple program to greet a person

Usage: myapp --name <NAME> --count <COUNT>

Options:
  -n, --name <NAME>    Name of the person to greet
  -c, --count <COUNT>  Number of times to greet
  -d, --debug          Run in debug mode
  -h, --help           Print help information
```

他にも次のような属性指定が可能です。

- __`#[arg(short)]`__ ... 短い形式のオプション (`-n`) のみ生成します。
- __`#[arg(long)]`__ ... 長い形式のオプション (`--name`) のみ生成します。
- __`#[arg(short = 'x')]`__ ... 短い形式のオプション名を `-x` に変更します。
- __`#[arg(long = "xyz")]`__ ... 短い形式のオプション名を `--xyz` に変更します。


省略可能なパラメーターとデフォルト値
----

構造体のフィールドの型を [Option 列挙型](/p/9m6m5m3/) として定義すると、そのコマンドライン引数は省略可能になります。

{{< code lang="rust" hl_lines="5 9 13" >}}
#[derive(Parser)]
struct Args {
    /// Name of the person to greet
    #[arg(short, long)]
    name: Option<String>,

    ///  Number of times to greet
    #[arg(short, long)]
    count: Option<u8>,

    /// Run in debug mode
    #[arg(short, long)]
    debug: Option<bool>,
}
{{< /code >}}

ユーザーがコマンドライン引数を指定しなかった場合、対応するフィールドの値は `Option::None` となります。

```rust
fn main() {
    let args = Args::parse();
    match &args.name {
        Some(name) => println!("Hello, {}", name),
        None => println!("Who are you?"),
    }
}
```

`bool` 型のフィールドに関しては、`Option` 型にしなくても省略可能なフラグとして機能します。
ユーザーがこのフラグを指定しなかった場合は、デフォルト値として `false` が格納されます。

{{< code lang="rust" hl_lines="5" >}}
#[derive(Parser)]
struct Args {
    /// Run in debug mode
    #[arg(short, long)]
    debug: bool,
}
{{< /code >}}

次のように __`default_value`__ でデフォルト値を設定しておくこともできます。
この場合も、フィールドを `Option` 型にする必要はありません。

{{< code lang="rust" hl_lines="4" >}}
#[derive(Parser)]
struct Args {
    /// Name of the person to greet
    #[arg(short, long, default_value = "John Doe")]
    name: String,
}
{{< /code >}}

Linux 形式ではないコマンドライン引数 (positional arguments) も通常は必須のコマンドライン引数として扱われますが、`Optional` 型にするか、デフォルト値を指定することで、省略可能にすることができます。

{{< code lang="rust" hl_lines="4 7" >}}
#[derive(Debug, Parser)]
struct Args {
    /// Input file name
    input: Option<String>,

    /// Output file name
    #[arg(default_value = "output.txt")]
    output: String,
}
{{< /code >}}


特定の値だけ指定できるようにする (ValueEnum)
----

決められた値のいずれかのみを指定できるオプションを定義するには、__`ValueEnum`__ 使って次のようにします。
ここでは、`--format` オプションで `json`、`yaml`、`toml` のいずれかを指定できるように実装しています。
ユーザーが `--format` オプションを指定しなかった場合は、デフォルトで `json` が使われます。

```rust
use clap::{Parser, ValueEnum};

#[derive(Parser)]
struct Args {
    /// Output format
    #[arg(short, long)]
    #[clap(value_enum, default_value_t=Format::Json)]
    format: Format,
}

#[derive(ValueEnum, Clone, Debug)]
enum Format {
    Json,
    Yaml,
    Toml,
}

fn main() {
    let args = Args::parse();
    println!("format = {:?}", args.format);
}
```

ヘルプもそれっぽい内容で表示してくれます。

```console
$ ./myapp --help
Usage: myapp [OPTIONS]

Options:
  -f, --format <FORMAT>  Output format [default: json] [possible values: json, yaml, toml]
  -h, --help             Print help information
```


ヘルプに表示する詳細説明
----

アプリケーションや各パラメーターの詳細説明は次のように記述します。

{{< code lang="rust" hl_lines="2-5 9-12" >}}
/// My app
///
/// Long long long description.
/// Long long long description.
/// Long long long description.
#[derive(Parser)]
struct Args {
    /// Your name
    ///
    /// Long long long description.
    /// Long long long description.
    /// Long long long description.
    name: String,
}
{{< /code >}}

この詳細説明を表示するには、__`--help`__ オプションを指定してプログラムを起動します（`-h` では表示されません）。

```console
$ ./myapp --help
My app

Long long long description. Long long long description. Long long long description.

Usage: myapp <NAME>

Arguments:
  <NAME>
          Your name

          Long long long description. Long long long description. Long long long description.

Options:
  -h, --help
          Print help information (use `-h` for a summary)
```

