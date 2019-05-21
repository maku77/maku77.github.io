---
title: "各ページに目次を表示する (.TableOfContents)"
date: "2019-05-21"
description: "Hugo 組み込みのページ変数 .TableOfContents を使用すると、各ページに簡単に目次を表示することができます。"
---

Hugo の目次機能
----

各ページの Markdown ファイル (`.md`) では、`##` や `----` を使ったセクション（`h2` 要素として出力される）を記述していると思います。
Hugo には、これらの**セクション名から自動的に目次 (Table of Contents) を出力する機能**が付いています。
下記は、自動的に出力した目次の例です。

![table-of-contents-001.png](./table-of-contents-001.png){: .center }


テンプレートの記述方法
----

目次を自動的に出力するには、テンプレートファイル内で **`.TableOfContents`** というページ変数を参照します。

#### layouts/_default/single.html（抜粋）

```
{{ "{{" }}- with .TableOfContents }}
  <aside class="xToc">
    <div class="xToc_title">目次</div>
    {{ "{{" }} . }}
  </aside>
{{ "{{" }}- end }}
```

ここでは、目次情報がないときに何も表示しないように、`with` で分岐処理を行っています。

Hugo が自動的に生成する `.TableOfContents` の値は、`ul` 要素と `li` 要素によって構成されています。
出力された HTML の内容を見て、スタイルシート (CSS) の調整をするとよいでしょう。
下記は CSS の記述例です。

```css
.xToc {
  font-size: smaller;
  border-radius: 0.5em;
  border: solid 1px lightgray;
  padding: 0.5em 1em;
  line-height: 2;
}
.xToc_title {
  font-weight: bolder;
}
.xToc ul {
  list-style-type: none;
  padding-left: 0;
}
.xToc li {
  padding-top: 0 !important;
}
.xToc li > ul {
  padding-left: 1em;
  width: 100%;
}
.xToc a {
  font-weight: normal;
  display: block;
  border-bottom: dashed 1px lightgray;
}
```


参考
----

- [Table of Contents ｜ Hugo](https://gohugo.io/content-management/toc/)

