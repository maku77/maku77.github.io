---
title: "HTML/CSSメモ: メニュー要素内でスクロールできるようにする"
url: "p/x6irhig/"
date: "2017-10-18"
description: "サイドバー内のメニュー要素を位置を固定して表示する場合は、メニュー項目が増えて画面内に収まらなくなったときのために、メニュー内でスクロールできるようにしておく必要があります。"
tags: ["css"]
aliases: /web/menu/scroll-menu.html
---

サイドバー単独でスクロールできるようにする
----

{{< image src="img-001.png" >}}

{{< file src="scroll-menu-sample1.html" caption="デモページを開く" >}}

サイドバーにメニューを表示しているときに、例えばその高さ (`height`) を `100vh` に設定すると、画面の高さと同じ高さでメニューが表示されます。
サイドバーの高さを固定すると、画面外に溢れるメニュー項目は見えなくなってしまいます。
このようなケースでは、`overflow-y: scroll;` を設定すると、スクロールバーを表示して、メニューの領域を単独でスクロールすることができるようになります。

{{< code lang="html" title="HTML 抜粋" >}}
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
{{< /code >}}

{{< code lang="css" title="CSS 抜粋" >}}
main {
  display: block;
  margin-left: 150px;
}

.menu {
  position: fixed;
  top: 0;
  left: 0;
  width: 150px;
  height: 100vh;
  background: lightgray;

  /* 縦方向のスクロールバーを表示 */
  overflow-y: scroll;

  /* スクロールバーの色設定 */
  scrollbar-color: #999 #eee;
}
{{< /code >}}


細くてクールなスクロールバーを表示する
----

{{< image src="img-002.png" >}}

{{< file src="scroll-menu-sample2.html" caption="デモページを開く" >}}

Chrome ブラウザなどでは、下記のようなスタイル指定を行うことにより、細くてかっこいいスクロールバーを表示することができます。

{{< code lang="css" title="CSS 抜粋" >}}
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
{{< /code >}}

