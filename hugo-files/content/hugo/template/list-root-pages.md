---
title: "Hugo でセクションを持たない記事ページ（ルートの記事ページ）の一覧を表示する (.Site.Home.RegularPages)"
url: "p/4sxmnfi/"
date: "2017-12-30"
lastmod: "2020-01-14"
tags: ["Hugo"]
aliases: /hugo/list/root-pages.html
---

Home ページ直下の記事ページ ＝ セクションを持たないページ
----

ホームページを示す `Page` オブジェクトの __`RegularPages`__ プロパティを参照すると、__`content` ディレクトリ直下に置かれた通常ページの一覧__ を取得することができます。
つまり、どのセクションにも所属していない記事ページの一覧です。

```go-html-template
<h3>セクションを持たない記事ページ一覧（タイトル順）</h3>
<ol>
  {{ range .Site.Home.RegularPages.ByTitle }}
    <li><a href="{{ .RelPermalink }}">{{ .Title }}</a>
  {{ end }}
</ol>
```

さらに __`first`__ 関数を組み合わせて使用すれば、最初の 5 件のみに絞り込んで表示することができます。

```go-html-template
<h2>セクションを持たない記事ページ一覧（最初の５件）</h2>
<ol>
  {{ range first 5 .Site.Home.RegularPages.ByTitle }}
    <li><a href="{{ .RelPermalink }}">{{ .Title }}</a>
  {{ end }}
</ol>
```


（コラム）Hugo v0.58.0 より前のバージョンでのやり方
----

Hugo v0.58.0 より前のバージョンでは、ホームページの `Page` オブジェクトの `.RegularPages` が、サイト全体の記事ページを返してしまっていたので、別の方法でセクションに所属していない記事ページを取得する必要がありました。
例えば、`$.Site.RegularPages` で取得したサイト内の記事ページの一覧（`Page` 配列）を、`where` 関数を使ってフィルタすることで、目的の記事ページだけに絞り込んでいました。

```go-html-template
<h3>セクションを持たない記事ページ一覧（タイトル順）</h3>
<ol>
  {{ range (where $.Site.RegularPages "Section" "").ByTitle }}
    <li><a href="{{ .RelPermalink }}">{{ .Title }}</a>
  {{ end }}
</ol>
```

