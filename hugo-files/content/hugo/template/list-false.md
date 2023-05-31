---
title: "Hugo でページリスト（記事一覧）に列挙されないページを作る (_build.list)"
url: "p/4ziyhxe/"
date: "2020-05-17"
lastmod: "2023-05-31"
tags: ["Hugo"]
changes:
  - 2023-05-31: list や render の設定値を真偽値から enum に変更
aliases: /hugo/list/exclude-from-list
---

[Hugo 0.65.0](https://gohugo.io/news/0.65.0-relnotes/) で、ビルド時の振る舞いを制御するためのフロントマター用プロパティ ([_build](https://gohugo.io/content-management/build-options/)) が導入されました。
記事ページのフロントマターで、__`_build.list`__ プロパティを __`never`__ に設定しておくと、`.Pages`、`.RegularPages`、`.Sections` などで返されるページリストに、そのページが含まれなくなります。

```yaml
_build:
  # Whether to add it to any of the page collections.
  #
  # Note that the page can still be found with .Site.GetPage.
  # We extended this property from a boolean to an enum in Hugo 0.68.0.
  # Valid values are:
  #
  # - never: The page will not be included in any page collection.
  # - always (default): The page will be included in all page collections,
  #                     e.g. site.RegularPages, $page.Pages.
  # - local: The page will be included in any local page collection,
  #          e.g. $page.RegularPages, $page.Pages.  One use case for this
  #          would be to create fully navigable, but headless content sections.
  list: always

  # Whether to render it.
  #
  # If always, the page will be treated as a published page,
  # holding its dedicated output files (index.html, etc…) and permalink.
  # We extended this property from a boolean to an enum in Hugo 0.76.0.
  # Valid values are:
  #
  # - never: The page will not be included in any page collection.
  # - always (default): The page will be rendered to disk and get a RelPermalink etc.
  # - link: The page will be not be rendered to disk, but will get a RelPermalink.
  render: always

  # Whether to publish its resources.
  #
  # These will still be published on demand, but enabling this can be useful
  # if the originals (e.g. images) are never used.
  #
  # - true: The Bundle's Resources will be published.
  # - false: Still publish Resources on demand (when a resource's .Permalink or
  #          .RelPermalink is invoked from the templates) but will skip the others.
  publishResources: false
```

例えば、[サイト内検索](/p/p4n5m3i/)のページは特別な経路でのみリンクしたいので、「メニューツリー」や「子ページ一覧」などには表示しないようにしたいとします。
そのような場合は、該当記事の Markdown ファイルで次のようにフロントマターを設定すれば OK です。

{{< code lang="yaml" title="content/search.md" hl_lines="3 4" >}}
---
title: "サイト内検索"
_build:
  list: never
---
{{< /code >}}

ネストされたプロパティは、次のように 1 行で記述することもできます。

{{< code lang="yaml" hl_lines="3" >}}
---
title: "サイト内検索"
_build: { list: never }
---
{{< /code >}}

この仕組みができるまでは、独自の `nomenu` プロパティなどを定義して対応していたのですが、リスト系のページから除外するのがとても簡単になりました。
一方で、[サイトマップ・テンプレート](https://gohugo.io/templates/sitemap-template/) などで `.Data.Pages` によるリスト出力しているところからも除外されてしまうので、そういった副作用には注意してください。

