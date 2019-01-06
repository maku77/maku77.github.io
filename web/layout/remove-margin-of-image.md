---
title: "画像の下に隙間が空くのを防ぐ"
date: "2015-12-07"
---

img 要素の下にくっつけて別の要素を配置しようとしても、デフォルトではどうしても隙間が空いてしまいます。


#### HTML 抜粋

~~~ html
<img src="tree.png"><br>
<img src="tree.png"><br>
<img src="tree.png">
~~~


#### 表示デモ

<iframe class="xHtmlDemo" src="remove-margin-of-image-demo1.html"></iframe>

これは、img 要素はテキストと同様にベースラインを意識した配置が行われるからです。
下記のように `vertical-align` プロパティを `bottom` に設定しておけば、隙間をなくすことができます。

#### CSS

~~~ css
img {
  vertical-align: bottom;
}
~~~

#### 表示デモ２

<iframe class="xHtmlDemo" src="remove-margin-of-image-demo2.html"></iframe>

この問題が発生しない場合は、プロジェクトで使用しているリセット系の CSS に、すでに上記のような定義が含まれている可能性が高いです。

