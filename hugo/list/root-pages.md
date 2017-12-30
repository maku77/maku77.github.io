---
title: "セクションを持たない記事ページ（ルートの記事ページ）の一覧を表示する"
created: 2017-12-30
---

`$.Site.RegularPages` を参照すると、サイト内の記事ページの一覧（Page 配列）を取得することができます。
この結果を `where` 関数を使ってフィルタすることで、目的の記事ページの一覧だけを取得できます。
下記の例では、セクションを持たない記事ページ、つまり、コンテンツディレクトリ (`content/`) の直下に置かれた記事ページのみの一覧を出力しています。

~~~ html
<h3>セクションを持たない記事ページ一覧（タイトル順）</h3>
<ol>
  {{ "{{" }} range (where $.Site.RegularPages "Section" "").ByTitle }}
    <li><a href="{{ "{{" }} .RelPermalink }}">{{ "{{" }} .Title }}</a>
  {{ "{{" }} end }}
</ol>
~~~

さらに `first` 関数を組み合わせて使用すれば、最初の５件のみに絞り込んで表示することができます。

~~~ html
<h2>セクションを持たない記事ページ一覧（最初の５件）</h2>
<ol>
  {{ "{{" }} range first 5 (where $.Site.RegularPages "Section" "").ByTitle }}
    <li><a href="{{ "{{" }} .RelPermalink }}">{{ "{{" }} .Title }}</a>
  {{ "{{" }} end }}
</ol>
~~~

