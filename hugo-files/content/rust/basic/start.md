---
title: "Rust プログラミングを始める（rustc と cargo コマンド）"
url: "p/96o6xfv/"
date: "2022-12-11"
tags: ["Rust"]
---

Rust 関連コマンドのインストール
----

### Rust のインストール

Rust コンパイラ (__`rustc`__) や Rust 用のパッケージマネージャー (__`cargo`__) は、下記の公式サイトの手順で簡単にインストールできます。
推奨されている方法でインストールすると、Rust 関連のコマンドをアップデートするための __`rustup`__ コマンドもインストールされます。

- 公式サイト: [Rust をインストール - Rustプログラミング言語](https://www.rust-lang.org/ja/tools/install/)

{{< code lang="console" title="Rust のインストール方法の例（Linux/macOS の場合）" >}}
$ curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh
{{< /code >}}

標準構成でインストールすると、Rust 関連の各コマンドが __`~/.cargo/bin/`__ ディレクトリにインストールされます。

{{< code lang="console" title="Rust 関連コマンドの一覧" >}}
$ ls ~/.cargo/bin
cargo*         clippy-driver* rust-lldb*     rustup*
cargo-clippy*  rls*           rustc*
cargo-fmt*     rust-gdb*      rustdoc*
cargo-miri*    rust-gdbgui*   rustfmt*
{{< /code >}}

### Rust のバージョンアップ

一度 `rustup` コマンドのインストールが済んでしまえば、上記の Rust 関連コマンド（Rust ツールチェイン）は次のようにまとめてアップデートできます。

{{< code lang="console" title="Rust ツールチェインの更新" >}}
$ rustup update
{{< /code >}}

### Rust のアンインストール

Rust 関連コマンドを削除したいときも、`rustup` コマンドを使用します。
次のようにすると、`rustup` コマンドを含む、すべての Rust 関連コマンドがアンインストールされます（`$HOME/.cargo/bin` ディレクトリも削除されます）。

{{< code lang="console" title="Rust ツールチェインのアンインストール" >}}
$ rustup self uninstall
{{< /code >}}


rustc コマンドで Rust コードをビルドする
----

次の `main.rs` ファイルは、Rust 言語の Hello World プログラムです。

{{< code lang="rust" title="main.rs" >}}
fn main() {
    println!("Hello, world!");
}
{{< /code >}}

この `main.rs` ファイルをコンパイルするには、__`rustc`__ コマンドを使用します。
デフォルトでは、`.rs` ファイルのベース名と同じ名前の実行ファイルが生成されます。

{{< code lang="console" title="Rust コード (.rs) のコンパイル" >}}
$ rustc main.rs
$ ./main
Hello, world!
{{< /code >}}

__`-o`__ オプションで、生成する実行ファイル名を指定することもできます。

```console
$ rustc -o hello main.rc
$ ./hello
Hello, world!
```

普段の開発では `rustc` コマンドを直接使うことは少なく、より包括的なパッケージングツールである `cargo` コマンドを使うのが一般的です。


cargo コマンドで Rust プロジェクトを作成する
----

Rust のデフォルトのビルドシステムである __`cargo`__ コマンドを使用すると、依存ライブラリの管理や、プロジェクトのパッケージング処理を行うことができます。
Rust プロジェクトの開発は、通常、この `cargo` コマンドを使って進めることになります。

### プロジェクトの作成 (cargo new/init)

`cargo` で新しいプロジェクト (cargo package) を作成するには、__`cargo new`__ コマンドを使用します（既存のディレクトリを使う場合は、__`cargo init`__ コマンドを使用します）。

{{< code lang="console" title="Rust プロジェクトの新規作成" >}}
$ cargo new hello
     Created binary (application) `hello` package
{{< /code >}}

このように実行すると、新規プロジェクト用の `hello` ディレクトリが生成されます。
この中には、Rust プロジェクトの設定ファイルである __`Cargo.toml`__ や、プログラムのエントリポイントとなる `src/main.rs` ファイルが含まれています。
また、Git リポジトリとしての初期化も済んでおり、すぐに `git commit` していけるようになっています。

```
hello/
├── .git/
├── .gitignore
├── Cargo.toml
└── src/
    └── main.rs
```

### ビルドと実行 (cargo run/build)

プロジェクトのディレクトリ内で、__`cargo run`__ コマンドを実行することで、プログラムを実行することができます。

{{< code lang="console" title="プロジェクトのビルドと実行" >}}
$ cargo run
    Finished dev [unoptimized + debuginfo] target(s) in 0.00s
     Running `target/debug/hello`
Hello, world!
{{< /code >}}

`cargo` のログ出力を抑制するには、__`-q (--quite)`__ オプションを追加します。

```console
$ cargo run -q
Hello, world!
```

ログ出力から分かるように、内部的にビルドされてできたプログラムは、__`target`__ ディレクトリ以下に配置されます。
デフォルトではデバッグモードでビルドされるので、`target/debug/<実行ファイル名>` というパスで実行ファイルが生成されます。
__`-r (--release)`__ オプションを付けてリリースモードでビルドすると、`target/release/<実行ファイル名>` というパスに生成されます。

ビルド関連の `cargo` サブコマンドには、次のようなものがあります。

| コマンド | 説明 |
| ---- | ---- |
| `cargo clean` | target ディレクトリの削除 |
| `cargo build` | ビルド（debug モード） |
| `cargo build -r` | ビルド（release モード） |
| `cargo run` | ビルド＆実行（debug モード） |
| `cargo run -r` | ビルド＆実行（release モード） |

これで、Rust でプログラミングを始める準備が整いました！

٩(๑❛ᴗ❛๑)۶ わーぃ

