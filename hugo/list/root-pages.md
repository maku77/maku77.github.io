---
title: "セクションを持たない記事ページ（ルートの記事ページ）の一覧を表示する (.Site.Home.RegularPages)"
date: "2017-12-30"
lastmod: "2020-01-14"
---

Home ページ直下の記事ページ ＝ セクションを持たないページ
----

（Hugo v0.58.0 以降）

ホームページを示す `Page` オブジェクトの `RegularPages` を参照すると、`content` ディレクトリ直下に置かれた通常ページの一覧を取得することができます。
つまり、どのセクションにも所属していない記事ページの一覧です。

~~~ html
<h3>セクションを持たない記事ページ一覧（タイトル順）</h3>
<ol>
  {{ "{{" }} range .Site.Home.RegularPages.ByTitle }}
    <li><a href="{{ "{{" }} .RelPermalink }}">{{ "{{" }} .Title }}</a>
  {{ "{{" }} end }}
</ol>
~~~

さらに `first` 関数を組み合わせて使用すれば、最初の５件のみに絞り込んで表示することができます。

~~~ html
<h2>セクションを持たない記事ページ一覧（最初の５件）</h2>
<ol>
  {{ "{{" }} range first 5 .Site.Home.RegularPages.ByTitle }}
    <li><a href="{{ "{{" }} .RelPermalink }}">{{ "{{" }} .Title }}</a>
  {{ "{{" }} end }}
</ol>
~~~


（コラム）Hugo v0.58.0 より前のバージョンでのやり方
----

Hugo v0.58.0 より前のバージョンでは、ホームページの `Page` オブジェクトの `.RegularPages` が、サイト全体の記事ページを返してしまっていたので、別の方法でセクションに所属していない記事ページを取得する必要がありました。

例えば、`$.Site.RegularPages` を参照すると、サイト内の記事ページの一覧（Page 配列）を取得することができるので、この結果を `where` 関数を使ってフィルタすることで、目的の記事ページの一覧だけを取得できます。

~~~ html
<h3>セクションを持たない記事ページ一覧（タイトル順）</h3>
<ol>
  {{ "{{" }} range (where $.Site.RegularPages "Section" "").ByTitle }}
    <li><a href="{{ "{{" }} .RelPermalink }}">{{ "{{" }} .Title }}</a>
  {{ "{{" }} end }}
</ol>
~~~

