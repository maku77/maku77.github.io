---
title: "CSS で画像の下に隙間が空くのを防ぐ (vertical-align)"
url: "p/bz3ga84/"
date: "2015-12-07"
tags: ["CSS"]
aliases: /web/layout/remove-margin-of-image.html
---

`img` 要素の下にくっつけて別の要素を配置しようとしても、デフォルトではどうしても隙間が空いてしまいます。

{{< code lang="html" title="HTML 抜粋" >}}
<img src="tree.png"><br>
<img src="tree.png"><br>
<img src="tree.png">
{{< /code >}}

<center>
  <iframe width="98" height="280" src="./demo.html"></iframe>
  <div>図: 画像の下に隙間ができてしまう例（{{< file src="demo.html" caption="別ページで開く" >}}）</div>
</center>

これは、`img` 要素はテキストと同様にベースラインを意識した配置が行われるからです。
下記のように `vertical-align` プロパティを `bottom` に設定しておけば、隙間をなくすことができます。

{{< code lang="css" title="CSS" >}}
img {
  vertical-align: bottom;
}
{{< /code >}}

<center>
  <iframe width="98" height="267" src="./demo2.html"></iframe>
  <div>図: 画像の下の隙間をなくした例（{{< file src="demo2.html" caption="別ページで開く" >}}）</div>
</center>

この問題が発生しない場合は、プロジェクトで使用しているリセット系の CSS に、すでに上記のような定義が含まれている可能性が高いです。

