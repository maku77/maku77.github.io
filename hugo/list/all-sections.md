---
title: "全セクションの一覧を表示する (.Site.Sections)"
created: 2017-12-22
---

`.Site.Sections` を参照すると、サイト内のすべてのセクションページを示す `Page` 配列を取得することができます。

#### layouts/index.html

~~~
<h3>全セクションのリスト</h3>
<ul>
  {{ "{{" }} range .Site.Sections }}
    <li><a href="{{ "{{" }} .RelPermalink }}">{{ "{{" }} .Title }}</a>
  {{ "{{" }} end }}
</ul>
~~~

ここで参照できるセクションページは、第一階層に存在するセクションの `index.html` のみであることに注意してください。
下のようなコンテンツ構造だとすると、`.Site.Sections` に含まれるのは、★のついたセクションページのみです。

~~~
content/
  +-- dir1/_index.md  ★
  |    +-- dir1-1/_index.md
  |    +-- dir1-2/_index.md
  +-- dir2/_index.md  ★
       +-- dir2-1/_index.md
       +-- dir2-2/_index.md
~~~

