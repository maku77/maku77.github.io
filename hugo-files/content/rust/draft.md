---
title: "(DRAFT) Rust 雑多メモ"
url: "p/jkv7gpz/"
date: "2022-12-30"
tags: ["Rust"]
draft: true
---

トレイト
----

- `Copy` トレイトを実装すると、その変数を別の変数へ代入しても、所有権が移動 (move) しなくなる。具体的には、ヒープ上のデータがコピーされ、それぞれの変数が異なるヒープ上のデータの所有者 (owner) となります。


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


cargo
----

### cargo --list

cargo のサブコマンドの一覧を表示する。

### cargo doc --open

全ての依存クレートが提供するドキュメントをローカルでビルドして、Web ブラウザーで開く。'

### cargo new --bin xxx

バイナリトレイト用のプロジェクトを作成する。


その他
----

- variance
  - covariant .. 何らか型のサブタイプとして代用できるとき
  - invariant
  - contravariant

