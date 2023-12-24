---
title: "Rust"
url: "/rust/"

categoryName: "まくまく Rust ノート"
categoryUrl: "/rust/"
categoryIcon: _index.svg
---

はじめに（Rust の実行環境）
----

- [Rust プログラミングを始める（`rustc` と `cargo` コマンド）](/p/96o6xfv/)
- [Rust で自作したプログラムをシステムにインストールする (`cargo install`)](/p/owbo2dp/)

{{% private %}}
- cargo
  - [Cargo で外部クレートの依存関係を管理する (`Cargo.toml`)](/p/4yj2hzf/)
- [10 分で理解する Rust 文法](/p/63m4k3i/)
- [(DRAFT) Rust 未整理メモ](/p/jkv7gpz/)
- ツール／エコシステム
  - [クロスコンパイルして他の OS 用の実行ファイルを生成する](/p/uyqo7ze/)
{{% /private %}}


Rust の文法
----

- [制御構文 (if, match, while, loop, for)](/p/22cnw7f/)
- [配列 (array) とタプル (tuple)](/p/7r3cmv6/)
- [構造体 (struct) とタプル構造体 (tuple struct)](/p/h8kw8ju/)
- [所有権 (ownership) と借用 (borrow)](/p/4nx8hqy/)
- [列挙型 (enum) の定義と match, if let による照合](/p/ffqyajs/)
- [パニック (panic) の扱いについて](/p/nfxwcc2/)
- [ジェネリクス構造体・関数を定義する（任意の型を扱えるようにする）](/p/be8u7sg/)
- [オブジェクトのライフタイム（生存期間）を理解する (`'static`, `'a`)](/p/zfhtasm/)

{{% private %}}
- エラーを伝搬させる（? 演算子）
{{% /private %}}


標準ライブラリが提供する型
----

- [Option 型の基本 ─ 値の有無を表現する型](/p/9m6m5m3/)
- [Result 型の基本 ─ 成功と失敗を表現する型](/p/us2ahpw/)
  - [各種ライブラリのエラー型と Error トレイト](/p/8amv5eo/)
  - [Result 型エイリアスでコードを簡潔にする](/p/ez9gpw5/)
  - [Result オブジェクトを消費せずに参照する (as_ref, as_mut)](/p/z3gts64/)
- コレクション
  - [ベクター型 (Vec) を扱う](/p/jku3biq/)
  - [ハッシュマップ型 (HashMap) を扱う](/p/eefwaa3/)

{{% private %}}
- セット型 (HashSet) を扱う
  - https://doc.rust-jp.rs/rust-by-example-ja/std/hash/hashset.html
{{% /private %}}


プロジェクト構成（パッケージ／クレート／モジュール）
----

- [モジュールを定義する (`mod`)](/p/gxj4n7q/)

{{% private %}}
- クレート（ライブラリ）の依存管理
  - [Rust の use の使い方](/p/9dpz9hr/)
{{% /private %}}


文字列と数値 <!-- numstr -->
----

- 数値
  - [複数の数値の中から最小値・最大値を見つける (`min`, `max`)](/p/e3o2ra3/)
  - [数値を特定の範囲に収まるように修正する (`clamp`, `min`, `max`)](/p/23fd7nv/)

{{% private %}}
- [文字列型 (String) を扱う](/p/vakbzyc/)
- [Rust での文字列処理いろいろ](/p/95o6n4k/)
{{% /private %}}


入出力 (I/O)、ファイル
----

- ユーザー入力
  - [コマンドライン引数を扱う (1) `std::env::args`](/p/wu6gqz9/)
  - [コマンドライン引数を扱う (2) `clap` クレート](/p/bdp2doy/)
  - [キーボードからの入力を取得する (`std::io::Stdin`)](/p/eamw7fp/)
- ファイル／ディレクトリ／パス情報
  - [ディレクトリ内のファイルを列挙する (`std::fs::read_dir`)](/p/2kv6eub/)
  - [ファイルやディレクトリの名前を変更（移動）する (`std::fs::rename`)](/p/raiqzbr/)
  - [ディレクトリを作成・削除する (`std::fs::create_dir, create_dir_all, remove_dir, remove_dir_all`)](/p/zju5eow/)
  - [ファイルやディレクトリのパス文字列を構築／分割する (`std::io::Path`, `PathBuf`)](/p/36hr2bj/)
  - [ファイルやディレクトリの存在を調べる (`std::io::Path`, `PathBuf`)](/p/fbkt3ah/)
- ファイルの読み書き
  - [JSON フォーマットを扱う (`serde`)](/p/xdyk5o8/)
  - [Excel ファイルを読み込む (`calamine`)](/p/4ye2eah/)
- [MongoDB を扱う (`mongodb`)](/p/nzi6xbm/)


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

