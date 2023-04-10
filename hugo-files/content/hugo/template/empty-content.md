---
title: "Hugo で Markdown ファイルに本文が記述されていない場合だけ特別な出力をする"
url: "p/7m6n4j2/"
date: "2018-11-02"
lastmod: "2020-01-14"
tags: ["Hugo"]
aliases: /hugo/template/empty-content.html
---

例えば、あるページの Markdown ファイルが下記のようにフロントマターだけの記述になっていると、

```yaml
---
title: "ページタイトル"
date: "2018-11-02"
---
```

テンプレートから `Page` 変数の __`{{ .Content }}` を参照したときの値は空っぽ__ になります。
このような場合だけ別の出力をしたい場合は、下記のように場合分け処理を記述します。

{{< code lang="go-html-template" title="layouts/_default/single.html（抜粋）" >}}
{{ with .Content }}
  {{ . }}
{{ else }}
  <b>本文が記述されていません。</b>
{{ end }}
{{< /code >}}

このような分岐出力は、セクションページ（`_index.md`）などで活用できると思います。
例えば、次のようにセクションテンプレートを記述しておけば、セクションページに本文が記述されている場合はその内容を表示し、本文が記述されていない場合はそのセクション直下のページリストを表示できます。

{{< code lang="go-html-template" title="layouts/_default/section.html（抜粋）" >}}
{{- with .Content }}
  {{ . }}
{{- else }}
  <ul class="pageList">
    {{/* カレントセクション直下のセクション */}}
    {{- range .Sections }}
      <li><a href="{{ .RelPermalink }}">{{ .Title }}</a> /</li>
    {{- end -}}

    {{/* カレントセクション直下の記事ページ */}}
    {{- range .RegularPages }}
      <li><a href="{{ .RelPermalink }}">{{ .Title }}</a></li>
    {{- end }}
  </ul>
{{- end }}
{{< /code >}}

コンテンツファイルが下記のような構成で配置されているとすると、

```
content/
  +-- mysection/
        +-- _index.md
        +-- page1.md
        +-- page2.md
        +-- page3.md
```

セクションページの `mysection/_index.md` の本文に何も書かなかった場合は、自動的にサブページ (`page1`, `page2`, `page3`) へのリンクが表示されます。
本文を記述した場合は、その本文だけが表示されるので、自力で子ページへのリンクを記述するようにします。

