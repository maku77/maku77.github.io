---
title: "画面サイズによって全体のレイアウトを変更する"
date: "2016-07-15"
description: "CSS3 のメディアクエリをうまく使うと、Web ブラウザのウィンドウサイズによって、動的にレイアウトを変更することができます。"
---

全体のレイアウト構成
----

ここでは、画面の幅によって、下記のように変化するレイアウトを考えてみます。　

* 1000px 超: 固定幅の 2 段組レイアウト
* 1000px 以下: 可変幅の 2 段組レイアウト（リキッドレイアウト）
* 700px以下: シングルカラム

今このページを PC のブラウザで見ているのであれば、下記のデモページを表示して、画面幅を変更してみてください。
画面幅に応じてレイアウトが自動的に変更されるはずです。

#### デモ

<iframe class="xHtmlDemo" style="height: 450px" src="change-layout-demo.html"></iframe>
<a target="_blank" href="change-layout-demo.html">デモページを表示</a>

HTML 要素は下記のようにヘッダ、コンテンツ、サイドバー、フッタと、4 つの部分に分かれています。

```html
<div id="page">
  <header id="header">Header</header>
  <article id="content">
    <div>Content</div>
    <div>Content</div>
    <div>Content</div>
  </article>
  <aside id="sidebar">
    <div>Sidebar</div>
    <div>Sidebar</div>
  </aside>
  <footer id="footer">Footer</footer>
</div>
```


固定幅の 2 段組レイアウト（画面幅が広いとき）
----

表示幅が大きいときは、サイズ固定でページを表示します。
分かりやすいように、このレイアウトのときは、周りに青色で枠を表示しています。

```css
#page {
  width: 980px;
  margin: 0 auto;
  border: blue 5px solid;
}
#header {
  background-color: lightblue;
}
#content {
  width: 680px;
  float: left;
  background-color: lightpink;
}
#sidebar {
  width: 300px;
  float: right;
  background-color: lightgreen;
}
#footer {
  clear: both;
  background-color: lightblue;
}
```


可変幅の 2 段組レイアウト（画面幅が中サイズのとき）
----

幅が 1000px 以下になると、コンテンツ部分と、サイドバー部分を動的にサイズ変更して画面内に収まるように調整します。
このレイアウトのときは、周りに黄色で枠を表示します。

```css
@media screen and (max-width: 1000px) {
  #page {
    width: 98%;
    border-color: yellow;
  }
  #content {
    width: 70%;
  }
  #sidebar {
    width: 30%;
  }
}
```


シングルカラムレイアウト（画面幅が狭いとき）
----

幅が 700px 以下（モバイル端末含む）になると、サイドバーは表示しきれないので、画面の下の方に移動させます。コンテンツとサイドバーの幅を 100% で表示することによって、シングルカラム表示にしています。
このレイアウトのときは、周りに赤色で枠を表示します。

```css
@media screen and (max-width: 700px) {
  #page {
    border-color: red;
  }
  #content {
    width: 100%;
  }
  #sidebar {
    width: 100%;
  }
}
```

