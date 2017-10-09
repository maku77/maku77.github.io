---
title: "Viewport、メディアクエリ設定によるレスポンシブ Web デザインの基本"
created: 2015-09-22
description: "Web サイトのレイアウトは、様々な端末、画面サイズで表示されることを想定して作成しなければいけません。スマホやタブレット、TV のように物理的に画面サイズが異なることもありますし、PC 上の Web ブラウザのように、ウィンドウサイズがユーザによって自由に変更されることもあります。スマホやタブレットに備えられている viewport の機能や、CSS3 の Media Queries の仕組みを利用すると、こういった様々な条件での表示に、柔軟に対応することができます。"
---

Viewport の設定
----

Viewport はスマホなどの物理的に画面が小さいモバイル端末に備えられている機能で、Web ページを部分的に表示するための仮想画面を提供する機能です。
Viewport 設定は、`head` セクション内に配置する `meta` 要素で行います。

```html
<meta name="viewport" content="width=device-width, initial-scale=1">
```

`width` を `device-width` と設定しておくことにより、Viewport の幅がデバイスに設定された仮想的な横幅に自動的に設定されます。
スマホの場合は、`device-width` は、320 〜 360px 程度に設定されていることが多いようです（[参考サイト](http://screensiz.es/phone)）。
`device-width` のデフォルトは 960px になっているため、スマホ用にコンパクトなレイアウトに切り替えたい場合は、`device-width` を明示的に指定することで、横幅を擬似的に狭めてやる必要があります。
この横幅は、後に説明する Media Queries における width 判定に効いてきます。

`initial-scale` は初期の表示倍率で、0 〜 10 の間で設定します。


Media クエリの設定
----

CSS3 の Media クエリの仕組みを使用すると、画面の表示サイズによって、CSS の切り替えを行うことができます。


```css
/* 画面幅が 480px 以下になったときのデザイン */
@media screen and (max-width: 480px) {
  body {
    background-color: blue;
  }
}

/* 画面幅が 960px 以上になったときのデザイン */
@media screen and (min-width: 960px) {
  body {
    background-color: yellow;
  }
}
```

[デモページを表示](media-query-demo.html)

例えば、上記の例では、画面の幅が 480px 以下になったとき、960px 以上になったときに背景色を変更しています。
この仕組みを使って、画面サイズが変更されたときにレイアウトを変更することで、レスポンシブ Web デザインを実現できます。

メディアタイプは、`@import` ディレクティブでも指定することができます。

```css
@import url("tv_and_print.css") tv, print;
```

外部の CSS ファイルを読み込む場合にも、表示方法によって適用する CSS を切り替えることができます。

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
<link rel="stylesheet" type="text/css" href="color.css" media="screen and (color)" />
<link rel="stylesheet" type="text/css" href="mono.css" media="screen and (monochrome)" />
```

[参考: Media Queries - W3C](http://www.w3.org/TR/css3-mediaqueries/)


