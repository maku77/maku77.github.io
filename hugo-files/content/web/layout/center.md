---
title: "HTML 要素を中央寄せ／右寄せで表示する"
url: "p/7pi429n/"
date: "2013-05-01"
tags: ["CSS"]
aliases: /web/layout/center.html
---

<style>
  .local-sample-es325sf {
    padding: 10px;
    border: thin solid gray;
  }
</style>

水平方向の中央寄せ
----

幅 (`width`) の指定されたブロック要素に対して、__`margin-left`__、__`margin-right`__ をともに __`auto`__ に設定**すると、中央寄せで表示することができます。

<div class="local-sample-es325sf">
  <div style="width:100px; height:50px; margin-left:auto; margin-right:auto; background:blue; color:white;">
    あいうえお
  </div>
</div>

{{< code lang="css" title="CSS" hl_lines="2-4" >}}
div.center {
  display: block;  /* div 要素はもともとブロック要素なので省略可 */
  margin-left: auto;
  margin-right: auto;
  width: 100px
  height: 50px;
  background: blue;
  color: white;
}
{{< /code >}}

{{< code lang="html" title="HTML" >}}
<div class="center">あいうえお</div>
{{< /code >}}

`img` 要素などのインライン要素は、`display: block` でブロック要素として扱うことで、同様の方法で中央寄せできます。

<div class="local-sample-es325sf">
  <img style="display:block; margin:auto; width:100px;" src="https://maku77.github.io/assets/img/logo.jpg">
</div>

{{< code lang="html" title="HTML" >}}
<img style="display: block; margin: auto;" src="sample.png">
{{< /code >}}


右寄せ
----

ブロック要素を右寄せで表示するには、__`margin-left` にだけ `auto`__ を指定します。

<div class="local-sample-es325sf">
  <div style="width:100px; height:50px; margin-left:auto; background:blue; color:white;">
    あいうえお
  </div>
</div>

```css
.right {
  display: block;
  margin-left: auto;
  /* ...省略... */
}
```


縦方向の中央寄せ
----

ある要素の __`display`__ と __`vertical-align`__ を下記のように設定しておくと、その子要素が縦方向に中央寄せされます。
外側のブロック要素の高さを `height` プロパティで指定する必要があることに注意してください。

<center>
<div class="local-sample-es325sf"
    style="display:table-cell; vertical-align:middle; height:100px;">
  <div style="text-align:center; background:pink;">
    上下の中央寄せ
  </div>
</div>
</center>

{{< code lang="css" title="CSS" >}}
div.center {
  display: table-cell;     /* これと */
  vertical-align: middle;  /* これで子要素が中央寄せされる */
  height: 100px;
}

div.center_item {
  text-align: center;
  background: pink;
}
{{< /code >}}

{{< code lang="html" title="HTML" >}}
<div class="center">
  <div class="center_item">上下の中央寄せ</div>
</div>
{{< /code >}}

