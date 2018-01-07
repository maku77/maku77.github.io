---
title: "全ページの一覧を表示する (.Site.Pages)"
date: "2017-12-22"
---

`.Site.Pages` を参照すると、サイト内のすべてのページを示す `Page` 配列を取得することができます。
下記はホームページテンプレート内で、サイト内のすべてのページのリンクを表示する例です。

#### layouts/index.html

~~~ html
<h3>全ページのリスト</h3>
<ul>
  {{ "{{" }} range .Site.Pages }}
    <li><a href="{{ "{{" }} .RelPermalink }}">{{ "{{" }} .Title }}</a>
  {{ "{{" }} end }}
</ul>
~~~

`.Site.Pages` はデフォルトで、日時 (.Date) の一番新しいものから昇順に並べられた `Page` 配列を返します。

<div class="note">
<code>.Site.Pages</code> は、現在選択中の言語内でのページ一覧を返します。
全言語を含むページ一覧を取得したいときは、代わりに <code>.Site.AllPages</code> を参照してください。
</div>

