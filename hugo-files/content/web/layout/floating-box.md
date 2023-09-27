---
title: "CSS のフローティングボックス (float) を利用したレイアウトを理解する"
url: "p/prbp2g2/"
date: "2015-10-25"
lastmod: "2023-09-27"
tags: ["CSS"]
changes:
  - 2023-09-27: 文章を校正。
aliases: /web/floating-box.html
---

<style>
  .local-sample {
    padding: 10px;
    border: thin solid gray;
  }
  .local-box {
    font-family: monospace;
    color: red;
    background: #fcc;
    border: solid 2px red;
    font-weight: bolder;
    width: 120px;
    height: 80px;
    padding: 0.2em;
    margin-right: 0.5em;
  }
</style>

float プロパティにより後続要素を回りこませる
----

CSS の __`float`__ プロパティには次のような値を指定することができます。

```css
float: none;   /* デフォルト */
float: left;   /* フローティングボックスを構成して左端に配置 */
float: right;  /* フローティングボックスを構成して右端に配置 */
```

`float` プロパティに `left` を設定すると、その要素は左端に表示され、後続の要素はその反対側（この場合は右側）に回り込んで表示されるようになります。

### float を指定しない場合

<div class="local-sample">
  <div class="local-box">float: none;</div>
  <div>デフォルトでは、ブロック要素の後続の要素は別の段落に表示さます。</div>
</div>

### float: left を指定した場合

<div class="local-sample">
  <div class="local-box" style="float: left;">float: left;</div>
  <div>
    <code>float</code> プロパティに<br>
    <code>left</code> や <code>right</code><br>
    を指定すると、後続の要素は、<br>
    回り込んで表示されるようになります。
  </div>
  <div style="clear:both"></div>
</div>


フローティングボックスの形成
----

`float` プロパティに `left` や `right` を設定した場合、その要素は __フローティングボックス__ を形成します。
後続の要素は、フローティングボックスが存在しないとして配置されるので、横幅いっぱいのサイズが確保されます。
下記の例は、後続要素に青色の背景色を付けたサンプルです。

<div class="local-sample">
  <div class="local-box" style="float: left;">float: left;</div>
  <div style="background: #cef">
    フローティングボックスは<br>
    後続要素から見ると<br>
    あたかもそこには存在しない<br>
    かのように配置されます。
  </div>
  <div style="clear:both"></div>
</div>

後続要素の横幅は、フローティングボックスに重なる形で画面いっぱいのサイズになっていることが分かります。
この場合でも、実際に表示されるテキストは重ならないように考慮して描画されることになっています。


フローティングボックスの回りこみの解除 (clear)
----

フローティングボックスの後続の要素は、フローティングボックスに対して回りこむように配置されていきます。
その回りこみを解除して、新しい段落に表示したい場合は、__`clear`__ プロパティを指定します。

```css
clear: none;   /* 回りこみを解除しない（デフォルト） */
clear: left;   /* 左側のフローティングボックスへの回りこみを解除 */
clear: right;  /* 右側のフローティングボックスへの回りこみを解除 */
clear: both;   /* 全てのフローティングボックスへの回りこみを解除 */
```

### clear を指定しない場合

<div class="local-sample">
  <div class="local-box" style="float: left;">float: left;</div>
  <div>後続の 1 つ目の要素</div>
  <div>後続の 2 つ目の要素</div>
  <div style="clear:both"></div>
</div>

### clear: left; を指定した場合

<div class="local-sample">
  <div class="local-box" style="float: left;">float: left;</div>
  <div>後続の 1 つ目の要素</div>
  <div style="clear:left;">後続の 2 つ目の要素 (<code>clear: left;</code>)</div>
</div>

CSS の `float` プロパティは、この `clear` 指定を後続の要素に対して指定する必要があるため、レイアウトが難しくなってしまうという欠点があります。
段組を構成したい場合は、CSS3 のグリッドレイアウトやマルチカラムの仕組みを使うことで、よりシンプルにレイアウトを作成することができます。


連続するフローティングボックス
----

### float:left を連続して配置

`float: left;` を設定した要素を連続して配置すると、それら全てのフローティングボックスを回りこむように後続の要素が配置されます。

<div class="local-sample">
  <div class="local-box" style="float: left;">float: left;</div>
  <div class="local-box" style="float: left;">float: left;</div>
  <div>
    連続するフローティングボックス<br>
    が配置されていると、<br>
    後続の要素は、<br>
    それらすべてを回りこむように配置されます。
  </div>
  <div style="clear:both"></div>
</div>

### float:left と float:right を配置

`float` プロパティを `left` に設定した要素と、`right` に設定した要素を連続して配置すると、それぞれ左側と右側にフローティングボックスが配置されます。

<div class="local-sample">
  <div class="local-box" style="float: left;">float: left;</div>
  <div class="local-box" style="float: right;">float: right;</div>
  <div>左右にそれぞれフローティングボックスを配置することもできます。</div>
  <div style="clear:both"></div>
</div>

この構成は、昔はサイトヘッダーやサイドバーの表示のためによく使われましたが、現在では代わりにフレックスボックスレイアウトが使われることが多くなっています。

