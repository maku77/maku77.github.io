---
title: "Rust で自作したプログラムをシステムにインストールする (cargo install)"
url: "p/owbo2dp/"
date: "2023-01-02"
tags: ["Rust"]
---

何をするか？
----

Rust で自作したコマンドラインツール (CLI) は、プロジェクトのワーキングディレクトリ以下で `cargo run` で実行できますが、毎回ディレクトリを移動するのは面倒です。
[cargo install コマンド](https://doc.rust-lang.org/cargo/commands/cargo-install.html) で、パスの通ったディレクトリに実行ファイルをインストールすれば、どのディレクトリからでもコマンドを実行できるようになります。


自作コマンドをインストールする (cargo install)
----

Rust のプロジェクトがなければ、次のように適当に作ってください。
ここでは、`hello-rust` という名前のプログラムを作成することにします。

```console
$ cargo new ~/hello-rust
$ cd ~/hello-rust
```

ローカルで開発している Rust プログラムをシステムにインストールするには、次のように __`cargo install`__ コマンドを実行します。
このとき、__`--path`__ オプションで `Cargo.toml` ファイルがあるディレクトリを指定する必要があります。
自動的にリリースモードでビルド (`cargo build -r`) してからインストールしてくれるので、あらかじめビルドしておく必要はありません。

```console
$ cargo install --path .
  Installing hello-rust v0.1.0 (/Users/maku/hello-rust)
   Compiling hello-rust v0.1.0 (/Users/maku/hello-rust)
    Finished release [optimized] target(s) in 0.35s
  Installing /Users/maku/.cargo/bin/hello-rust
   Installed package `hello-rust v0.1.0 (/Users/maku/hello-rust)` (executable `hello-rust`)
```

生成された実行ファイル (`hello-rust`) は、__`~/.cargo/bin`__ ディレクトリにインストールされます（インストール先は `--root` オプションで変更できます）。
このディレクトリにパスが通っていれば、次のようにコマンド実行できるようになっているはずです。

```console
$ hello-rust
Hello, world!
```


自作コマンドをアンインストールする (cargo unintall)
----

`cargo install` でインストールした自作コマンドをアンインストールするには、`Cargo.toml` ファイルのあるディレクトリで、__`cargo uninstall`__ コマンドを実行します。

```console
$ cd ~/hello-rust
$ cargo uninstall
    Removing /Users/maku/.cargo/bin/hello-rust
```

インストールされたプログラムは、`~/.cargo/bin` ディレクトリ以下に 1 つのバイナリファイルとして存在しているので、単純にそのファイルを削除するだけでも OK です。

```console
$ rm ~/.cargo/bin/hello-rust
```

