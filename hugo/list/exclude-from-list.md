---
title: "ページリスト（記事一覧）に列挙されないページを作る (_build.list)"
url: "/p/9r2bku4"
date: "2020-05-17"
---

[Hugo 0.65.0](https://gohugo.io/news/0.65.0-relnotes/) でフロントマター用の [_build プロパティ](https://gohugo.io/content-management/build-options/) が導入されました。

```yaml
_build:
  # Whether to add it to any of the page collections.
  # Note that the page can still be found with .Site.GetPage.
  list: true

  # Whether to render it.
  render: true

  # Whether to publish its resources. These will still be published on demand,
  # but enabling this can be useful if the originals (e.g. images) are
  # never used.
  publishResources: true
```

各記事ページのフロントマターで、__`list`__ プロパティを __`false`__ に設定しておくと、`.Pages`、`.RegularPages`、`.Sections` などで返されるページリストに含まれなくなります。

例えば、「サイト内検索」のページは特別な経路でのみリンクしたいので、「メニューツリー」や「子ページ一覧」などには表示しないようにしたいとします。
そのような場合は、該当記事の Markdown ファイルに次のように記述すれば OK です。

#### content/search.md

```yaml
---
title: "サイト内検索"
_build:
  list: false
---
```

一行で書きたいなら次のように書くこともできます。

```yaml
---
title: "サイト内検索"
_build: { list: false }
---
```

この仕組みができるまでは、独自の `nomenu` プロパティなどを定義して対応していたのですが、ページ一覧系の出力から除外するのがとても簡単になりました。
一方で、[サイトマップ・テンプレート](https://gohugo.io/templates/sitemap-template/) などで `.Data.Pages` によるリスト出力しているところからも除外されてしまうので、そういった副作用には注意してください。

