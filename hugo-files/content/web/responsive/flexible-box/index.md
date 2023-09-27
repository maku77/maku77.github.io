---
title: "CSS のフレキシブルボックスによるレスポンシブレイアウト"
url: "p/tq3zi5j/"
date: "2016-01-01"
tags: ["CSS"]
aliases: /web/flexible-box.html
---

CSS のフレキシブルボックスレイアウト機能を利用すると、下記の様な段組レイアウトを簡単に作成することができます。

{{< image w="500" src="img-001.png" title="幅が広いときはサイドバーを表示" >}}

{{< code lang="html" title="HTML" >}}
<div id="container">
  <div id="sidebar1">Sidebar 1</div>
  <div id="main">Main<br>Main<br>Main</div>
  <div id="sidebar2">Sidebar 2</div>
</div>
{{< /code >}}

{{< code lang="css" title="CSS" >}}
#container {
  /* フレキシブルボックスレイアウトによる段組 */
  display: flex;
}

#sidebar1, #sidebar2 {
  width: 100px;
  background: pink;
}

#main {
  /* 画面幅が広い時はこの要素の横幅を拡張する */
  flex-grow: 1;
  background: cyan;
}
{{< /code >}}

- 参考: [（旧）フレキシブルボックスの旧式の定義方法](/p/uu32hpo/)

さらに、メディアクエリを利用して、画面幅が一定サイズ以下になったときに、このフレキシブルボックスレイアウトを解除することができます。
例えば、スマートフォンなどで表示する場合に、サイドバーを縦に並べたり、省略してもよいサイドバーを非表示にしたりできます。
下記の例では、画面幅が `400px` 以下になった場合にフレキシブルボックスレイアウトを解除し、`sidebar1` を横幅いっぱいで表示し、`sidebar2` を非表示にしています。

{{< image w="400" src="img-002.png" title="幅が狭いときは縦に並べる" >}}

{{< code lang="css" >}}
/*
 * レスポンシブデザイン対応。
 * 幅が狭い場合は sidebar1 を横幅いっぱいに表示する。
 * sidebar2 は非表示にする。
 */
@media screen and (max-width: 400px) {
  #container {
    display: block;
  }
  #sidebar1 {
    width: auto;
  }
  #sidebar2 {
    display: none;
  }
}
{{< /code >}}

- [JSFiddle で試す](https://jsfiddle.net/maku77/h5vzwzxt/)

上記の例では、サイドバーを表示するレイアウトをデフォルトのスタイルとして定義しましたが、モバイルファーストの考えを採用するのであれば、幅が狭い場合のレイアウト（縦に並べるレイアウト）をデフォルトのスタイルとして定義してください。

- 参考: [モバイルファーストな CSS を作成するコツ（`max-width` ではなく `min-width` を使うべし）](/p/7vwoyht/)

