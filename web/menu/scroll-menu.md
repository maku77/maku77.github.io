---
title: "メニュー要素内でスクロールできるようにする"
date: "2017-10-18"
description: "サイドバー内のメニュー要素を位置を固定して表示する場合は、メニュー項目が増えて画面内に収まらなくなったときのために、メニュー内でスクロールできるようにしておく必要があります。"
---

サイドバー単独でスクロールできるようにする
----

![scroll-menu1.png](scroll-menu1.png){: .center }

<a target="_blank" href="scroll-menu-sample1.html">デモページを開く</a>

サイドバーにメニューを表示しているときに、例えばその高さ (`height`) を `100vh` に設定すると、画面の高さと同じ高さでメニューが表示されます。
サイドバーの高さを固定すると、画面外に溢れるメニュー項目は見えなくなってしまいます。
このようなケースでは、`overflow-y: scroll;` を設定すると、スクロールバーを表示して、メニューの領域を単独でスクロールすることができるようになります。

#### HTML 抜粋

~~~ html
<main>
  <p>
    これはダミーの本文です。これはダミーの本文です。これはダミーの本文です。
    これはダミーの本文です。これはダミーの本文です。これはダミーの本文です。
    ...
  </p>
</main>

<nav class="menu">
  <ul>
    <li>Item 1 <li>Item 2 <li>Item 3 <li>Item 4 <li>Item 5
    <li>Item 6 <li>Item 7 <li>Item 8 <li>Item 9 <li>Item 10
    ...
  </ul>
</nav>
~~~

#### CSS 抜粋

~~~ css
main {
  display: block;  /* for IE */
  margin-left: 150px;
}

.menu {
  position: fixed;
  top: 0px;
  left: 0px;
  width: 150px;
  height: 100vh;
  background: lightgray;

  /* 縦方向のスクロールバーを表示 */
  overflow-y: scroll;

  /* IE などのスクロールバーの色設定 */
  scrollbar-face-color: #999;
  scrollbar-track-color: #eee;

  /* スマホ用の慣性スクロール */
  -webkit-overflow-scrolling: touch;
}
~~~


細くてクールなスクロールバーを表示する
----

![scroll-menu2.png](scroll-menu2.png){: .center }

<a target="_blank" href="scroll-menu-sample2.html">デモページを開く</a>

Chrome ブラウザなどでは、下記のようなスタイル指定を行うことにより、細くてかっこいいスクロールバーを表示することができます。

#### CSS 抜粋

~~~ css
/* Chrome などでは細めのきれいなスクロールバーを表示 */
.menu::-webkit-scrollbar {
  width: 5px;
}
.menu::-webkit-scrollbar-thumb {
  background: rgba(0, 0, 0, 0.3);
}
.menu::-webkit-scrollbar-track {
  background: rgba(0, 0, 0, 0.1);
}
~~~

