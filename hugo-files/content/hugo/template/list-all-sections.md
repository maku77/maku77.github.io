---
title: "Hugo でサイト内の全セクションの一覧を表示する (.Site.Sections)"
url: "p/vczuozw/"
date: "2017-12-22"
tags: ["Hugo"]
aliases: /hugo/list/all-sections.html
---

__`.Site.Sections`__ を参照すると、サイト内のすべてのセクションページを示す __`Page`__ 配列を取得することができます。

{{< code lang="go-html-template" title="layouts/index.html" >}}
<h3>全セクションのリスト</h3>
<ul>
  {{ range .Site.Sections }}
    <li><a href="{{ .RelPermalink }}">{{ .Title }}</a>
  {{ end }}
</ul>
{{< /code >}}

ここで参照できるセクションページは、第一階層に存在するセクションのみであることに注意してください。
下のようなコンテンツ構造だとすると、`.Site.Sections` に含まれるのは、★のついたセクションページのみです。

```
content/
  +-- dir1/_index.md  ★
  |    +-- dir1-1/_index.md
  |    +-- dir1-2/_index.md
  +-- dir2/_index.md  ★
       +-- dir2-1/_index.md
       +-- dir2-2/_index.md
```

