---
title: "HTML の画像要素 (img) を親要素の左右両端に表示する"
url: "p/usqqvps/"
date: "2018-09-26"
tags: ["CSS"]
description: "HTML のある親要素の左右両端に画像などの子要素を配置する方法を紹介します。"
aliases: /web/layout/images-at-both-ends.html
---

HTML のある親要素の左右両端に画像などの子要素を配置する方法を紹介します。


Flex レイアウトを使って子要素を左右に配置する方法
----

<center>
  <iframe width="400" height="150" src="demo2.html"></iframe>
  <div>（{{< file src="demo2.html" caption="別ページで開く" >}}）</div>
</center>

Flex レイアウトの配置方法として __`justify-content: space-between`__ を指定すると、「子要素を均等に配置し、最初のアイテムは先頭に寄せ、最後のアイテムは末尾に寄せる」という意味になります。

{{< code lang="html" title="HTML" >}}
<div class="box">
  <img src="tree.png">
  <img src="tree.png">
</div>
{{< /code >}}

{{< code lang="css" title="CSS" >}}
.box {
  display: flex;
  justify-content: space-between;
}
{{< /code >}}


position: abosolute 指定で子要素を左右に配置する方法
----

<center>
  <iframe width="400" height="150" src="demo1.html"></iframe>
  <div>（{{< file src="demo1.html" caption="別ページで開く" >}}）</div>
</center>

スタイルに __`position: absolute`__ 指定を行うと、親要素の原点を基準に要素を配置することができます。
この指定に加えて、__`left: 0`__ とすれば左寄せ（デフォルト）、__`right: 0`__ とすれば右寄せで子要素を配置することができます（親要素の左端、右端に配置される）。

{{< code lang="html" title="HTML" >}}
<div class="box">
  <img class="box_left" src="tree.png">
  <img class="box_right" src="tree.png">
</div>
{{< /code >}}

{{< code lang="css" title="CSS" >}}
.box {
  /* 子要素のタイトルを aboslute 配置するために必要 */
  position: relative;
}

.box .box_left {
  position: absolute;
  left: 0;
}

.box .box_right {
  position: absolute;
  right: 0;
}
{{< /code >}}


応用例: ウェブサイトのヘッダ部分で使えそうな例
----

<center>
  <iframe width="550" height="220" src="demo3.html"></iframe>
  <div>（{{< file src="demo3.html" caption="別ページで開く" >}}）</div>
</center>

ここでは、両端に配置した画像の間にテキストを追加しています。
テキストに関しても同じ階層に `position: absolute` 配置し、中央寄せで表示しています。
このような構成にすることで、横幅が狭くなった場合に、画像の上にテキストが重なる形で表示されるようになります。

下記は CSS と HTML の抜粋ですが、ヘッダーの帯部分の高さに対して、上下方向にも中央寄せするようにしているため若干複雑になっています。

{{< code lang="html" title="HTML" >}}
<div class="titleBox">
  <img class="titleBox_imageLeft" src="tree.png">
  <img class="titleBox_imageRight" src="tree.png">
  <h1>これはウェブサイトのタイトルです</h1>
</div>
{{< /code >}}

{{< code lang="css" title="CSS" >}}
.titleBox {
  position: relative; /* 子要素の aboslute 配置用 */
  height: 200px;
  background: #597380;
}

.titleBox > * {
  /* 子要素をすべて上下に中央寄せする */
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
}

.titleBox > .titleBox_imageLeft {
  left: 0;
}

.titleBox > .titleBox_imageRight {
  right: 0;
}

.titleBox > h1 {
  margin: 0;
  padding: 0;
  width: 100%;
  text-align: center;
  color: #eee;
  text-shadow: 2px 2px 3px black;
}
{{< /code >}}

