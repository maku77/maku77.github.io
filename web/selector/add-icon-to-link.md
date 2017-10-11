---
title: "外部リンクに自動的にアイコンを付けて表示する"
created: 2017-10-11
description: "A 要素によってリンクを表示するときは、サイト内のリンクと外部リンクを区別できるように表示すると親切です。"
---

#### 表示サンプル

* [サイト内のリンクの例](./)
* [外部サイトへのリンクの例](http://example.com/)


CSS の属性セレクタ (attribute selectors) を使用すると、`a` 要素の `href` 属性が、`http` で始まっているかどうかを調べることができます。
この仕組みを利用して、`a` 要素が外部リンクを示すものだけに、特殊な表示効果を与えることができます。
上記の表示サンプルでは、外部サイトへのリンクにだけアイコンを付加しています。

<div class="note">
ここでは、「外部リンク」を URL が <code>http</code> で始まっているものと定義しているため、サイト内リンクは相対パス（例: <code>/sample.html</code>）で記述するようにルール化しておく必要があります。
</div>

リンクの URL が `http` で始まっている `a` 要素は、下記のような CSS の属性セレクタで選択することができます。

~~~ css
a[href^="http"]
~~~

ただし、これだけだと、画像リンク (`a` 要素の中に `img` 要素があるもの）まで選択対象にしてしまうので、子要素に `img` 要素を含むものを対象外にする必要があります。
そのためには、下記のように `:not`、`:has` などを組み合わせて指定します。

~~~ css
a[href^="http"]:not(:has(img))
~~~

このような前の要素に遡って適用するようなセレクタは、CSS ファイルではなく、JavaScript や jQuery で要素選択する必要があるようです。
下記の例では、jQuery ですべての外部リンク要素を選択し、`externalLinkIcon` というクラスを追加しています。

#### sample.js

~~~ javascript
$(function() {
  $('a[href^="http"]:not(:has(img))').addClass("externalLinkIcon");
});
~~~

そして、スタイルシートの方では、`externalLinkIcon` クラスを持つ要素に対して、外部リンクアイコンなどを付加するように設定します。

#### sample.css

~~~ css
/* 先頭にアイコンを付加する場合 */
.externalLinkIcon::before {
  content: url("/assets/img/icon-external-link.png");
  padding-right: 3px;
}

/* 末尾にアイコンを付加する場合 */
.externalLinkIcon::after {
  content: url("/assets/img/icon-external-link.png");
  padding-left: 3px;
  padding-right: 5px;
}
~~~

疑似要素（`::before` や `::after`）を使わずに、下記のように記述する方法もありますね。

~~~ css
/* 末尾にアイコンを付加 */
.externalLinkIcon {
  background: transparent url("/img/icon-external-link.png") center right no-repeat;
  display: inline-block;
  padding-right: 20px;
}
~~~

