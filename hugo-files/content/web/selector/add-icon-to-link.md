---
title: "HTML/CSSメモ: 外部リンクに自動的にアイコンを付けて表示する (External link icons)"
url: "p/9m6f3ex/"
date: "2017-10-11"
lastmod: "2020-04-23"
description: "a 要素によってリンクを表示するときは、サイト内のリンクと外部リンクを区別できるように表示すると親切です。"
tags: ["html", "css"]
aliases: /web/selector/add-icon-to-link.html
---

考え方
----

#### 表示サンプル

* [サイト内のリンクの例](./)
* [外部サイトへのリンクの例](http://example.com/)


CSS の属性セレクタ (attribute selectors) を使用すると、`a` 要素の `href` 属性が、`http` で始まっているかどうかを調べることができます。
この仕組みを利用して、`a` 要素が外部リンクを示すものだけに、特殊な表示効果を与えることができます。
上記の表示サンプルでは、外部サイトへのリンクにだけアイコンを付加しています。

<div class="note">
ここでは、「外部リンク」を URL が <code>http</code> で始まっているものと定義しているため、サイト内リンクは相対パス（例: <code>/sample.html</code>）で記述するようにルール化しておく必要があります。
</div>

`a` 要素のうち、リンク先のアドレスが `http` で始まっているものは、下記のような CSS の属性セレクタで選択することができます。

```css
a[href^="http"]
```

ただし、これだけだと、画像リンク (`a` 要素の中に `img` 要素があるもの）まで選択対象にしてしまうので、子要素に `img` 要素を含むものを対象外にする必要があります。
そのためには、下記のように `:not`、`:has` などを組み合わせて指定します。

```css
a[href^="http"]:not(:has(img))
```

このように選択した要素に対して独自のスタイルを設定することで、リンクの表示をカスタマイズすることができます。


実装
----

実は、上記のような複雑な構成のセレクタは CSS4 で実装予定のものであり、現状の Web ブラウザは対応していません。
ただし、jQuery を使えばこのような複雑なセレクタも使用することができます。
下記の例では、jQuery ですべての外部リンク要素を選択し、別タブで開くように `target="_blank"` 属性を追加しています。
さらに、子要素として `img` を持たない場合は、`externalLinkIcon` というクラスを追加しています。

{{< code lang="javascript" title="add-exlink-icon.js（jQuery を使う場合）" >}}
$(function() {
  // 外部リンクを必ず新しいタブで開く
  // （rel=noopener を指定することにより、必ず別プロセスで開く）
  $('a[href^=http]').attr('target', '_blank').attr('rel', 'noopener');

  // 画像を含まない外部リンクにクラス属性を付加する
  $('a[href^=http]:not(:has(img))').addClass("externalLinkIcon");
});
{{< /code >}}

もし、純粋な JavaScript でがんばるのであれば、次のようにすれば同じような振る舞いになるはずです。
一気に面倒になりますね。。。

```javascript
document.addEventListener('DOMContentLoaded', () => {
  const links = document.querySelectorAll('a[href^=http]');
  for (const link of links) {
    // 外部リンクを必ず新しいタブで開く
    // （rel=noopener を指定することにより、必ず別プロセスで開く）
    link.setAttribute('target', '_blank');
    link.setAttribute('rel', 'noopener');

    // 子に img 要素を持つ場合はアイコンを表示しない
    if (link.querySelector('img')) continue;

    // クラスを追加して外部リンクアイコンを表示
    link.classList.add('externalLinkIcon');
  }
});
```

スタイルシートの方では、`externalLinkIcon` クラスを持つ要素に対して、外部リンクアイコンなどを付加するように設定します。

{{< code lang="css" title="sample.css" >}}
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
{{< /code >}}

疑似要素（`::before` や `::after`）を使わずに、下記のように記述する方法もありますね。

```css
/* 末尾にアイコンを付加 */
.externalLinkIcon {
  background: transparent url("/img/icon-external-link.png") center right no-repeat;
  display: inline-block;
  padding-right: 20px;
}
```

