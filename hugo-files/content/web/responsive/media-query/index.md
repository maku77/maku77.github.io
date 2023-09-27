---
title: "Viewport とメディアクエリによるレスポンシブ Web デザインの基本"
date: "2015-09-22"
lastmod: 2023-09-27"
url: "p/ta2kimu/"
tags: ["CSS"]
description: "Web サイトのレイアウトは、様々な端末、画面サイズで表示されることを想定して作成しなければいけません。スマホやタブレット、テレビのように物理的に画面サイズが異なることもありますし、PC 上の Web ブラウザのように、ウィンドウサイズがユーザによって自由に変更されることもあります。スマホやタブレットに備えられている Viewport の機能や、CSS3 のメディアクエリの仕組みを利用すると、こういった様々な条件での表示に、柔軟に対応することができます。"
changes:
  - 2023-09-27: Viewport の説明を更新
aliases: /web/responsive/media-query.html
---

Web サイトのレイアウトは、様々な端末、画面サイズで表示されることを想定して作成しなければいけません。
スマホやタブレット、テレビのように物理的に画面サイズが異なることもありますし、PC 上の Web ブラウザのように、ウィンドウサイズがユーザによって自由に変更されることもあります。
スマホやタブレットに備えられている __Viewport__ の機能や、CSS3 の __メディアクエリ__ の仕組みを利用すると、こういった様々な条件での表示に、柔軟に対応することができます。


Viewport の設定
----

Viewport はスマホなどの物理的に画面が小さいモバイル端末に備えられている機能で、Web ページを部分的に表示するための仮想画面を提供する機能です。
Viewport 設定は、`head` セクション内に配置する `meta` 要素で行います。

{{< code lang="html" title="基本的な viewport 設定" >}}
<head>
  <!-- 省略 -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
{{< /code >}}

`content` 属性内で __`width=device-width`__ と設定しておくことにより、デバイスごとに設定された仮想的な横幅 (`device-width`) が、Viewport の幅として設定されます。
つまり、デバイスの画面サイズに合わせてコンテンツを表示します。
デバイスの幅に合わせることで、ユーザーがズーム操作をせずにコンテンツを見ることができるようになります。
スマホの場合は、`device-width` は、320 〜 360px 程度に設定されていることが多いようです（[参考サイト](http://screensiz.es/phone)）。
この横幅は、後述するメディアクエリの width 判定にも影響します。

`initial-scale` はページが最初に読み込まれたときのズームレベルで、0 〜 10 の間で指定します。
__`initial-scale=1`__ は、通常のズームレベルでページを表示することを意味します。
これにより、ユーザーがページを開いたときにズームアウトまたはズームインする必要がなくなります。


メディアクエリの設定
----

CSS3 のメディアクエリの仕組みを使用すると、画面の表示サイズに応じて CSS を切り替えることができます。

{{< code lang="css" title="CSS" >}}
/* デフォルトのスタイル（300px 未満） */
body { background: pink; }

/* 画面幅が 300px 以上の場合のスタイル */
@media screen and (min-width: 300px) {
  body { background: lightblue; }
}

/* 画面幅が 600px 以上の場合のスタイル */
@media screen and (min-width: 600px) {
  body { background: lightgreen; }
}
{{< /code >}}

<center>
  <iframe width="100%" height="130" src="./demo.html" style="resize: both; overflow: auto;"></iframe>
  <div>（<a target="_blank" href="./demo.html">別ページで開く</a>）</div>
</center>

上記の例では、画面の幅が 300px 以上になったとき、600px 以上になったときに背景色を変更しています。

- 1px ～ 299px ... 赤 (pink)
- 300px ～ 599px ... 青 (lightblue)
- 600px ～ ... 緑 (lightgreen)

この仕組みを使って、画面サイズが変更されたときにレイアウトを変更することで、レスポンシブ Web デザインを実現できます。

CSS 内の __`@import`__ ディレクティブでもメディアタイプを指定することができます。

{{< code lang="css" title="TV 上での表示あるいは印刷時の CSS を指定" >}}
@import url("tv_and_print.css") tv, print;
{{< /code >}}

__`link`__ 要素で外部の CSS ファイルを読み込む場合も、メディアクエリで CSS を切り替えることができます。

```html
/* 画面表示用 or 印刷用 */
<link rel="stylesheet" media="screen" href="screen.css">
<link rel="stylesheet" media="print" href="print.css">

/* 横幅別 */
<link rel="stylesheet" media="(max-width: 960px)" href="max960px.css">
<link rel="stylesheet" media="(max-width: 320px)" href="max320px.css">
<link rel="stylesheet" media="screen and (max-width: 480px)" href="mobile.css" />

/* 縦表示 or 横表示 */
<link rel="stylesheet" media="(orientation: portrait)" href="portrait.css">
<link rel="stylesheet" media="(orientation: landscape)" href="landscape.css">

/* カラー or 白黒 */
<link rel="stylesheet" media="screen and (color)" href="color.css" />
<link rel="stylesheet" media="screen and (monochrome)" href="mono.css" />
```

- 参考リンク: [Media Queries - W3C](http://www.w3.org/TR/css3-mediaqueries/)

