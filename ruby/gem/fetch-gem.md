---
title: 勉強用に Gem パッケージをダウンロードする
date: "2015-11-22"
---

単純に、ある Gem のソースコードを確認してみたい場合などは、`gem fetch` コマンドを使用すると、Gem ファイルそのものをカレントディレクトリにダウンロードできます。
下記の例では、`rvm` パッケージの Gem ファイルをダウンロードしています。

```
$ gem fetch rvm
Fetching: rvm-1.11.3.9.gem (100%)
Downloaded rvm-1.11.3.9
```

ダウンロードされた Gem ファイルを展開するには、`gem unpack` コマンドを使用します。

```
$ gem unpack rvm
Unpacked gem: '/Users/maku/sand-box/rvm-1.11.3.9'
```

これで、Gem のソースコードを参照することができます。
Gem のソースコードは、通常 `lib` ディレクトリ以下に格納されています。

