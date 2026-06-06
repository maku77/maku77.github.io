---
title: "HTML/CSSメモ: メニューの表示位置を固定する"
url: "p/hiiwmt3/"
date: "2013-04-04"
description: "CSS で position: fixed; を指定すると、画面をスクロールしても指定した座標で要素を表示することができます。ここでは、サイドバーメニューにこの指定をして、表示位置を固定してみましょう。"
tags: ["css"]
aliases: /web/menu/position-fixed-menu.html
---

メニュー全体を画面左上に固定
----

{{< image src="img-001.png" >}}

{{< file src="position-fixed-menu-sample1.html" caption="デモページを表示" >}}

まずは、HTML で簡単なメニュー要素を用意します。

{{< code lang="html" title="html" >}}
<nav class="menu">
  <ul>
    <li>Item 1
    <li>Item 2
    <li>Item 3
  </ul>
</nav>
{{< /code >}}

メニュー全体を囲む要素に `position: fixed;` というスタイルを指定することで、画面上の特定の座標に表示位置を固定します。
実際の表示位置は、`top`、`bottom`、`left`、`right` などのプロパティで指定します。

{{< code lang="css" title="css" >}}
.menu {
  position: fixed;
  top: 0;
  left: 0;
  width: 150px;
  background: lightgray;
}
.menu li {
  list-style: none;
}
{{< /code >}}

<div class="note">
<code>position: fixed;</code> の代わりに <code>position: absolute;</code> と指定すると、画面上の左上ではなく、ページ全体の左上を基準とした固定位置で要素を配置することができます（画面スクロールするとその要素も一緒にスクロールします）。
</div>


本文がメニューの後ろに隠れないようにする
----

{{< image src="img-002.png" >}}

{{< file src="position-fixed-menu-sample2.html" caption="デモページを表示" >}}

上記のスタイル指定により、メニュー要素を画面左上に表示することができるようになりました。
しかし、そのままだと、メインコンテンツ（本文）のテキストがメニューの後ろに隠れてしまいます。
メインコンテンツが隠れないようにするには、メインコンテンツの左側にメニューと同じ幅のマージンを入れます。

{{< code lang="html" title="HTML 抜粋" >}}
<main>
  <p>
    これはダミーの本文です。これはダミーの本文です。これはダミーの本文です。
    ...
  </p>
</main>

<nav class="menu">
  <ul>
    <li>Item 1
    <li>Item 2
    <li>Item 3
  </ul>
</nav>
{{< /code >}}

{{< code lang="css" title="CSS" >}}
main {
  display: block;
  margin-left: 150px;
}
.menu {
  position: fixed;
  top: 0;
  left: 0;
  width: 150px;
  background: lightgray;
}
.menu li {
  list-style: none;
}
{{< /code >}}

<div class="note">
IE では、<code>main</code> 要素のデフォルト設定が <code>display: block;</code> になっていないため、明示的に CSS で指定してやる必要があります。これを指定しておかないと、<code>margin-left</code> の設定が効きません。
</div>

可能であれば、CSS ファイルを直接記述するのではなく、SASS などを使用して共通の横幅を変数で指定してしまうのがよいですね。

{{< code lang="scss" title="SASS の場合（メニューの幅を変数で定義）" >}}
$menu_width: 150px;

main {
  display: block;
  margin-left: $menu_width;
}
.menu {
  position: fixed;
  top: 0;
  left: 0;
  width: $menu_width;
  background: lightgray;
}
.menu li {
  list-style: none;
}
{{< /code >}}

