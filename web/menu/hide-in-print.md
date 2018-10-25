---
title: "印刷時に特定の要素（メニューなど）を非表示にする"
date: "2018-10-25"
---

`@media print` というメディアクエリを使用すると、印刷時のみ適用するスタイルを設定することができます。
その中で `display: none;` と指定すれば、特定の要素を印刷時のみ非表示にすることができます。
本サイトも、印刷時にサイドバーメニューや検索バーを非表示にしています（印刷プレビューで確認できます）。

下記の例では、ナビゲーションメニューを印刷時に非表示にしています。

~~~ css
@media print {
  #menu {
    display: none;
  }
}
~~~

~~~ html
<nav id="menu" aria-label="メニュー">
  ...
</nav>
~~~

次のようなクラスを作成しておいて、HTML 内の任意の要素に対して付加するという方法もあります。

~~~ css
@media print {
  .hideInPrint{
    display: none;
  }
}
~~~

~~~ html
<div class="foo hideInPrint"> ... </div>
<div class="bar hideInPrint"> ... </div>
~~~

