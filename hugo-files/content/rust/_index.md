---
title: "Rust"
url: "/rust/"

categoryName: "まくまく Rust ノート"
categoryUrl: "/rust/"
categoryIcon: _index.svg
---

{{% private %}}
- [10 分で理解する Rust 文法](/p/63m4k3i/)
- [(DRAFT) Rust 未整理メモ](/p/jkv7gpz/)
- コレクション
  - [ベクター型 (Vec) を扱う](/p/jku3biq/)
- 文字列／数値
  - [文字列処理いろいろ](/p/95o6n4k/)
- クレート（ライブラリ）の依存管理
  - [Cargo で外部クレートの依存関係を管理する (`Cargo.toml`)](/p/4yj2hzf/)
  - [Rust の use の使い方](/p/9dpz9hr/)
- ツール／エコシステム
  - [クロスコンパイルして他の OS 用の実行ファイルを生成する](/p/uyqo7ze/)
{{% /private %}}

はじめに
----

- [Rust プログラミングを始める（`rustc` と `cargo` コマンド）](/p/96o6xfv/)
- [Rust で自作したプログラムをシステムにインストールする (`cargo install`)](/p/owbo2dp/)


基本
----

- [列挙型 (enum) の定義と match, if let による照合](/p/ffqyajs/)
- [Option 型の基本 ─ 値の有無を表現する型](/p/9m6m5m3/)
- [Result 型の基本 ─ 成功と失敗を表現する型](/p/us2ahpw/)
  - [各種ライブラリのエラー型と Error トレイト](/p/8amv5eo/)
  - [Result のエイリアス型でコードを簡潔にする](/p/ez9gpw5/)
  - [Result オブジェクトを消費せずに参照する (as_ref, as_mut)](/p/z3gts64/)

{{% private %}}
- エラーを伝搬させる（? 演算子）
{{% /private %}}


入出力 (I/O) 処理、ファイル処理
----

- ユーザー入力
  - [コマンドライン引数を扱う (1) `std::env::args`](/p/wu6gqz9/)
  - [コマンドライン引数を扱う (2) `clap` クレート](/p/bdp2doy/)
  - [キーボードからの入力を取得する (`std::io::Stdin`)](/p/eamw7fp/)
- ファイル／ディレクトリ／パス
  - [ディレクトリ内のファイルを列挙する (`std::fs::read_dir`)](/p/2kv6eub/)
  - [ファイルやディレクトリの名前を変更（移動）する (`std::fs::rename`)](/p/raiqzbr/)
  - [ディレクトリを作成・削除する (`std::fs::create_dir, create_dir_all, remove_dir, remove_dir_all`)](/p/zju5eow/)
  - [ファイルやディレクトリのパス文字列を構築／分割する (`std::io::Path`, `PathBuf`)](/p/36hr2bj/)
  - [ファイルやディレクトリの存在を調べる (`std::io::Path`, `PathBuf`)](/p/fbkt3ah/)
- [JSON フォーマットを扱う (serde)](/p/xdyk5o8/)
- [Excel ファイルを読み込む (calamine)](/p/4ye2eah/)


その他
----

- [正規表現を扱う (regex)](/p/r7sdwgy/)
- [型の名前を取得する (`std::any::type_name`)](/p/m9vdtaq/)


おすすめ書籍
----

- {{< amazon-inline id="B0979PWD4Z" title="Programming Rust" >}}
  - Rust 文法を正しく理解するのに最適な本です。
- {{< amazon-inline id="B0957SWKBS" title="Rust for Rustaceans" >}}
  - Rustacean（Rust プログラマー）としてよりよいコードを記述するための本です。

