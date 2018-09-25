---
title: "要素を中央寄せ／右寄せで表示する"
date: "2013-05-01"
---

水平方向の中央寄せ
----

幅 (`width`) の指定されたブロック要素に対して、**`margin-left`、`margin-right` をともに `auto` に設定**すると、中央寄せで表示することができます。

~~~ css
div.center {
  /* display: block;  div 要素はもともとブロック要素なので必要なし */
  width: 100px
  height: 50px;
  margin-left: auto;
  margin-right: auto;
  background: blue;
}
~~~

#### 使用例

~~~ html
<div class="center">あいうえお</div>
~~~

### 応用: img 要素の中央寄せ

`img` 要素はブロック要素ではないので、マージンを設定することができず、通常は中央寄せで表示することができません。
強引に中央寄せするには、`display: block` でブロック要素として扱い、マージンを設定します。

~~~ css
img.center {
  display: block;
  margin: auto;
}
~~~

#### 使用例

~~~ html
<img class="center" src="sample.png">
~~~


右寄せ
----

ブロック要素を右寄せで表示するには、**`margin-left` にだけ `auto` を指定**します。

~~~ css
img {
  display: block;  /* img は inline 要素なので block 要素に置き換える */
  margin-left: auto;
}
~~~


縦方向の中央寄せ
----

### ある要素内での縦方向の中央寄せ

ある要素の **`display`** と **`vertical-align`** を下記のように設定しておくと、その子要素が縦方向に中央寄せされます。

~~~ css
div.center {
  display: table-cell;     /* これと */
  vertical-align: middle;  /* これで子要素が中央寄せされる */

  border: 1px solid black;
  width: 100px;
  height: 100px;
}
div.center_item {
  text-align: center;
  background: pink;
}
~~~

#### 使用例

~~~ html
<div class="center">
  <div class="center_item">ABC</div>
</div>
~~~

### 画面全体での縦方向の中央寄せ

`body` 要素全体の上半分にダミーのブロック要素を置いて、そこからの相対位置で要素を配置することで、画面全体の縦方向の中央寄せを行うことができます。

~~~ css
html, body {
  height: 100%;
  margin: 0px;
  padding: 0px;
}
div.upperHalf {
  height: 50%;
}
div.center {
  position: fixed;
  height: 100px;
  margin-top: -50px;
  width: 100%;
  background: blue;
}
~~~

#### 使用例

~~~ html
<div class="upperHalf"></div>
<div class="center"></div>
~~~

