---
title: "IE 8 以下でも canvas タグを使用できるようにする"
date: "2015-02-21"
---

HTML5 の canvas タグは、各ブラウザの以下のバージョンから対応しています。

* Safari 1.3
* Firefox 1.5
* Opera 9
* IE 9
* Chrome（すべてのバージョン）

IE 8 以下でも canvas を扱えるようにするためのライブラリ **Explorer Canvas** が公開されています。
canvas が備えている機能を完全に再現することはできませんが、基本的な命令は動作させることができるので、古いブラウザをサポートしたい場合には有効です。

Explorer Canvas ライブラリは、下記からダウンロードして使用します。

- [arv/ExplorerCanvas: Canvas for IE8 and older](https://code.google.com/p/explorercanvas/downloads/list)

IE 8 以下だけで、このスクリプトを読み込むようにするために、下記のコメントを HTML ファイルに記述しておきます。

~~~ html
<!--[if let IE 8]>
<script src="excanvas.compiled.js"></script>
<![endif]-->
~~~

