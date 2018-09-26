---
title: "画像や div 要素を両端に表示する"
date: "2018-09-26"
description: "ここでは、ある親要素の左右両端に画像などの子要素を配置する方法を示します。"
---

position: abosolute 指定で子要素を左右に配置する方法
----

### デモ

<iframe class="xHtmlDemo" src="images-at-both-ends-demo1.html"></iframe>
<a target="_blank" href="images-at-both-ends-demo1.html">デモページを開く</a>

スタイルに `position: absolute` 指定を行うと、親要素の原点を基準に要素を配置することができます。
この指定に加えて、`left: 0` とすれば左寄せ（デフォルト）、`right: 0` とすれば右寄せで子要素を配置することができます（親要素の左端、右端に配置される）。

#### CSS 抜粋

~~~ css
.box {
  /* 子要素のタイトルを aboslute 配置するため必要 */
  position: relative;
}
.box .box_imageLeft {
  position: absolute;
  left: 0;
}
.box .box_imageRight {
  position: absolute;
  right: 0;
}
~~~

#### HTML 抜粋

~~~ html
<div class="box">
  <img class="box_imageLeft"  src="sample.png">
  <img class="box_imageRight" src="sample.png">
</div>
~~~


justify-content: space-between 指定で子要素を左右に配置する方法
----

### デモ

<iframe class="xHtmlDemo" src="images-at-both-ends-demo2.html"></iframe>
<a target="_blank" href="images-at-both-ends-demo2.html">デモページを開く</a>

Flex レイアウトの配置方法として `justify-content: space-between` を指定すると、「子要素を均等に配置し、最初のアイテムは先頭に寄せ、最後のアイテムは末尾に寄せる」という意味になります。

#### CSS 抜粋

~~~ css
.box {
  display: flex;
  justify-content: space-between;
}
~~~

#### HTML 抜粋

~~~ html
<div class="box">
  <img src="sample.png">
  <img src="sample.png">
</div>
~~~


応用例: ウェブサイトのヘッダ部分で使えそうな例
----

### デモ

<iframe class="xHtmlDemo" src="images-at-both-ends-demo3.html"></iframe>
<a target="_blank" href="images-at-both-ends-demo3.html">デモページを開く</a>

ここでは、両端に配置した画像の間にテキストを追加しています。
テキストに関しても同じ階層に `position: absolute` 配置し、中央寄せで表示しています。
このような構成にすることで、横幅が狭くなった場合に、画像の上にテキストが重なる形で表示されるようになります。

下記は CSS と HTML の抜粋ですが、ヘッダーの帯部分の高さに対して、上下方向にも中央寄せするようにしているため若干複雑になっています。

#### CSS 抜粋

~~~ css
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
~~~

#### HTML 抜粋

~~~ html
<div class="titleBox">
  <img class="titleBox_imageLeft"  src="sample.png">
  <img class="titleBox_imageRight" src="sample.png">
  <h1>これはウェブサイトのタイトルです</h1>
</div>
~~~

