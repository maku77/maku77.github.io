---
title: "Hugo で記事ページに付けられたターム一覧（タグ一覧）を表示する"
url: "p/topd7uy/"
date: "2018-01-01"
tags: ["Hugo"]
description: "シングルページテンプレートにおいて、その記事ページに付けられたタグの一覧を表示するようにしておくと、関連する記事（同じタグの付けられた記事）を探しやすくなります。"
aliases: /hugo/taxonomy/terms-in-page.html
---

シングルページテンプレートにおいて、その記事ページに付けられたタグの一覧を表示するようにしておくと、関連する記事（同じタグの付けられた記事）を探しやすくなります。

タクソノミーターム（タグやカテゴリ）は、各ページの front matter 部分で定義するので、ページ変数 (__`.Params`__) 経由でその情報を取得することができます。
次のパーシャルテンプレートは、記事ページに付けられたタグの情報を取得し、リスト形式で出力します。

{{< code lang="go-html-template" title="layouts/partials/tags-in-page.html" >}}
<ul class="tags">
  {{- range .Params.tags -}}
    <li><a href="{{ "/tags/" | relLangURL }}{{ . | urlize }}">{{ . }}</a>
  {{- end -}}
</ul>
{{< /code >}}

タグが設定されていない場合に、先頭の ul 要素ごと出力しないようにするには、ちょっとだけ変えて下記のようにします。

```go-html-template
{{ with .Params.tags }}
  <ul class="tags">
    {{- range . -}}
      <li><a href="{{ "/tags/" | relLangURL }}{{ . | urlize }}">{{ . }}</a>
    {{- end -}}
  </ul>
{{ end }}
```

上記のパーシャルテンプレートを使用するときは、シングルページテンプレート内で次のように記述します。

{{< code lang="go-html-template" title="layouts/_default/single.html" >}}
{{ partial "tags-in-page" . }}
{{< /code >}}

デフォルトでは、`li` 要素は単純な箇条書きのリスト形式で表示されてしまいます。
次のようなスタイルを定義しておくと、それっぽく横並びで表示されてよい感じになります。

{{< code lang="css" title="CSS" >}}
.tags {
  padding: 0;
}
.tags li {
  display: inline-block;  /* 横に並べる */
  list-style-type: none;
  margin-right: 0.5em;
}
.tags li a {
  display: block;  /* 選択範囲を広げる */
  text-decoration: none;
  padding: 0.5em 1em;
  background: lightgray;
  color: #333;
}
.tags li a:hover {
  background: #555;
  color: white;
}
{{< /code >}}

実際に表示すると次のような感じになります。

<iframe class="xHtmlDemo" src="./demo.html" style="width: 300px; height: 80px;"></iframe>

