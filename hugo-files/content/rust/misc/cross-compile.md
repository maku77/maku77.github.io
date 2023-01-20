---
title: "クロスコンパイルして他の OS 用の実行ファイルを生成する"
url: "p/uyqo7ze/"
date: "2023-01-16"
tags: ["Rust"]
draft: true
---

Rust は各 OS、CPU アーキテクチャ用のクロスコンパイルに対応しており、例えば、Windows 上で Linux 用の実行ファイルを生成することができます。
クロスコンパイルを行うには、次の手順を踏みます。

1. `rustup target add` でビルド対象のターゲットを追加する
2. `cargo build` でビルドするときに `--target` でターゲットを指定する


ターゲットの追加
----

デフォルトでは、`cargo build` (`rustc`) によるビルドは、現在使用中の OS（および CPU アーキテクチャ）でだけビルドできるようになっています。
別の OS 用の実行ファイルを生成するには、それぞれの __ターゲット (target)__ と呼ばれるコンポーネントをインストールする必要があります。

現在インストールされているターゲットを確認するには次のようにします。

{{< code lang="console" title="インストール済みターゲットの確認" >}}
$ rustup target list --installed
x86_64-pc-windows-msvc
{{< /code >}}

インストール可能なターゲットの一覧は次のように確認できます。
`アーキテクチャ名-OS名` のような名前になっているようです。

{{< code lang="console" title="インストール可能なターゲットの一覧" >}}
$ rustup target list
aarch64-apple-darwin
aarch64-apple-ios
aarch64-apple-ios-sim
aarch64-fuchsia
aarch64-linux-android
...省略...
{{< /code >}}

たくさん表示されすぎて、どれを入れればよいか分からないときは、[Platform Support](https://doc.rust-lang.org/nightly/rustc/platform-support.html) のページの __Tier 1__ に表示されているものから選択します（ちゃんとテストされたものが Tier 1 ターゲットとして列挙されています）。
例えば、64 ビットアーキテクチャのターゲットとしては次のようなものがあります（2023-01 時点）。

| ターゲット名 | 説明 |
| ---- | ---- |
| __`x86_64-apple-darwin`__ | 64-bit macOS (10.7+, Lion+) |
| __`x86_64-pc-windows-gnu`__ | 64-bit MinGW (Windows 7+) |
| __`x86_64-pc-windows-msvc`__ | 64-bit MSVC (Windows 7+) |
| __`x86_64-unknown-linux-gnu`__ | 64-bit Linux (kernel 3.2+, glibc 2.17+) |
| __`aarch64-unknown-linux-gnu`__ | ARM64 Linux (kernel 4.1, glibc 2.17+) |

ここでは、Windows 上で Linux 用のビルドをすることを想定して、`*-linux-gnu` というターゲットをインストールします。

{{< code lang="console" title="Linux 用のターゲットをインストール" >}}
$ rustup target add x86_64-unknown-linux-gnu
...数秒待つ...

$ rustup target list --installed
x86_64-unknown-linux-gnu
x86_64-pc-windows-msvc
{{< /code >}}


ターゲットを指定してビルド
----

ターゲット（および `cross`）のインストールができたら、あとは `cargo build` するときに、__`--target`__ オプションでそのターゲットを指定するだけです。

{{< code lang="console" title="Linux 64 ビット用にクロスコンパイル" >}}
$ cargo build --release --target x86_64-unknown-linux-gnu
{{< /code >}}

```
   Compiling myapp v0.1.0 (D:\myapp)
error: linker `cc` not found
  |
  = note: program not found

error: could not compile `myapp` due to previous error
```

(´;ω;｀)

↓


cross のインストール
----

（記述中）

Rust は別の OS 用にクロスコンパイルすることはできるのですが、リンカーなどのツールチェインが存在しないと、ビルドの最後でこけたりします（例: error: linker `cc` not found）。
[cross](https://github.com/cross-rs/cross) というツール使用すると、クロスコンパイルの環境を簡単に整えてくれます。

{{< code lang="console" title="ビルド用のツールチェインを導入" >}}
$ cargo install cross
{{< /code >}}

