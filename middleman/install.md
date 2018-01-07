---
title: Middleman とは／Middleman のインストール
date: "2015-11-03"
---

Middleman とは
====
Middleman は静的なサイトを作成するための統合ツールです。
例えば、下記のような Web サイト作成に必要な機能をひととおり備えています。

* Web サイトの雛形の生成
* Sassy CSS (SCSS) による CSS の生成
* CSS の結合 (minify)
* ERB 言語で Ruby のループ構文などを使用した HTML ファイルの生成
* Markdown や Stylus を使用した記事から HTML ファイルの生成
* JavaScript のインポート機能（Asset Pipeline による require）
* JavaScript の結合 (minify)
* 簡易 Web サーバ機能 (Middleman Server) による開発中の自動コンパイル、Live Reload
* gzip 圧縮によるパフォーマンスの最適化
* デプロイ作業の自動化


Middleman のインストール
====
Middleman は、Ruby の gem コマンド (RubyGems) で簡単にインストールすることができます。

```
$ sudo gem install middleman
```

