---
title: "CSS の box-sizing プロパティで width の計算方法を制御する"
url: "p/fprd6w4/"
date: "2017-12-29"
lastmod: "2023-09-27"
tags: ["CSS"]
description: "CSS の width プロパティがどこまでの幅を示すかは、box-sizing プロパティの設定によって変化します。段組によるレイアウトを正しく行うには、box-sizing の考え方を理解しておく必要があります。"
aliases: /web/layout/box-sizing.html
changes:
  - 2023-09-27: 文章を校正。
---

CSS の `width` プロパティが要素のどの部分の幅を示すかは、__`box-sizing`__ プロパティの設定によって変化します。
段組などのレイアウトを正しく行うには、`box-sizing` の考え方を理解しておく必要があります。

content-box 指定と border-box 指定
----

下記の図は、HTML 要素の `width` の値が、具体的にどの部分の幅であるかを示しています。
ここでは簡略化のために `width` の例を示していますが、`height` も同様です。

{{< image w="900" src="img-001.svg" >}}

`box-sizing` プロパティのデフォルト値は __`content-box`__ になっており、簡単に言うと、テキストの描画部分の領域のことを表しています。
デフォルトの状態で、`width: 300px;` というスタイル指定を行った場合、その `300px` にはパディング (padding) 部分や、ボーダー (border) 部分のサイズは含まれません。
つまり、`width: 300px;` と指定された要素が実際に描画されるときには、それよりも大きなサイズで描画されることになります。
ボーダー領域まで含めたサイズを、`width` プロパティや `height` プロパティで指定したいときは、`box-sizing` プロパティの値を __`border-box`__ に指定します。

{{< code lang="css" title="CSS" >}}
.sample {
  /* width と height にボーダー領域まで含める */
  box-sizing: border-box;
}
{{< /code >}}


実際に試してみる
----

下記のデモは、`box-sizing` プロパティの値を `content-box`（デフォルト）と `border-box` に設定したときの表示の違いを示しています。
分かりやすくするために、ボーダー領域は青色で表示しています。
パディング領域だけに色をつけることはできないため、テキストの周りの微妙な隙間がパディング領域だと思ってください。

<center>
  <iframe width="363" height="273" src="./demo.html"></iframe>
  <div>図: box-sizing のデモ（{{< file src="demo.html" caption="別ページで開く" >}}）</div>
</center>

どちらも、`width` は `300px` としていますが、`box-sizing: border-box;` と指定した場合は、ボーダー領域まで `width` に含めて計算するようになるため、実際の表示も若干小さくなります。

{{< code lang="html" title="HTML" >}}
<div class="content-box">
  box-sizing: <b>content-box</b>;
</div>

<div class="border-box">
  box-sizing: <b>border-box</b>;
</div>
{{< /code >}}

{{< code lang="css" title="CSS" >}}
div {
  width: 300px;
  height: 100px;
  margin: 10px;
  padding: 10px;
  border: solid 10px #59f;
  background: #ddd;
}

div.content-box {
  box-sizing: content-box;
}

div.border-box {
  box-sizing: border-box;
}
{{< /code >}}

{{% note %}}
ちなみに `margin` に関しては、`width` の計算に含まれることはありません。
`margin` はあくまで __要素の外側__ のマージンだと考えましょう。
{{% /note %}}


段組レイアウトでは box-sizing: border-box が便利
----

２つ以上のボックスを横に並べて段組レイアウトを行う場合、デフォルトの `box-sizing` アルゴリズム (`content-box`) を使用していると、`width` 値の指定が非常に複雑になります（パディングやボーダーのサイズを考慮して `width` 値を調整しなければならないため）。
このような場合、横に並べるボックス要素に、`box-sizing: border-box;` の指定を行っておくと、直感的な `width` 指定を行うことができます。

<center>
  <iframe width="380" height="120" src="demo2.html"></iframe>
  <div>図: box-sizing のデモ 2（{{< file src="demo2.html" caption="別ページで開く" >}}）</div>
</center>

{{< code lang="css" title="CSS" >}}
<div>
  <div class="pane pane-left">Left (150px)</div>
  <div class="pane pane-right">Right</div>
</div>
{{< /code >}}

{{< code lang="css" title="CSS" >}}
.pane {
  box-sizing: border-box;
  height: 100vh;
  padding: 10px;
}

.pane-left {
  float: left;
  width: 150px;
  background: #f9c;
}

.pane-right {
  margin-left: 150px;
  background: #9fa;
}
{{< /code >}}

