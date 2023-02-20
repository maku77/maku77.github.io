---
title: "Hugo でサイト内の全ページの一覧を表示する (.Site.Pages)"
url: "p/sgrjpfu/"
date: "2017-12-22"
tags: ["Hugo"]
aliases: /hugo/list/all-pages.html
---

__`.Site.Pages`__ を参照すると、サイト内のすべてのページを示す __`Page`__ 配列を取得することができます。
下記はホームページテンプレート内で、サイト内のすべてのページのリンクを表示する例です。

{{< code lang="go-html-template" title="layouts/index.html" >}}
<h3>全ページのリスト</h3>
<ul>
  {{ range .Site.Pages }}
    <li><a href="{{ .RelPermalink }}">{{ .Title }}</a>
  {{ end }}
</ul>
{{< /code >}}

`.Site.Pages` はデフォルトで、日時 (`.Date`) の一番新しいものから昇順に並べられた `Page` 配列を返します。

{{% note %}}
`.Site.Pages` は、現在選択中の言語内でのページ一覧を返します。
全言語を含むページ一覧を取得したいときは、代わりに __`.Site.AllPages`__ を参照してください。
{{% /note %}}

