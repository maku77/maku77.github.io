---
title: "Hugo で JSON-LD 形式のパンくずリストを出力する（SEO 対策）"
url: "p/5fzgwdt/"
date: "2020-05-16"
tags: ["Hugo"]
description: "JSON-LD 形式の構造化データを HTML 内に含めておくと、Google の検索結果にページの階層構造が表示されるようになります。"
aliases: /hugo/template/breadcrumbs-json.html
---

[JSON-LD](https://json-ld.org/) 形式の構造化データを HTML 内に含めておくと、Google の検索結果にページの階層構造が表示されるようになります。
この構造化データは、あくまで __Google などの検索エンジンに記事の階層を伝えるためのもの__ であり、記事内に表示するためのパンくずリストではないことに注意してください。
画面表示用のパンくずリストを出力したいときは、下記の記事を参考にしてください。

- 参考: [Hugo でパンくずリストを表示する](/p/vemn3c4/)


パンくずリストのフォーマット
----

JSON-LD 形式でパンくずリストを作成するときに、どのような内容で出力すればよいかは、 [Google のパンくずリストの説明ページ](https://developers.google.com/search/docs/data-types/breadcrumb) に分かりやすく書かれています。

例えば、ある「記事ページ」が次のような階層で配置されているとします。

```
ホーム ＞ カテゴリA ＞ カテゴリB ＞ 記事ページ
```

これを JSON-LD で表現するには、下記のようなコードを `head` 要素や `body` 要素の中に記述します。
特に理由がなければ、少しでもパフォーマンスを考慮して `body` 要素の最後に記述するのがよいでしょう。

```html
<script type="application/ld+json">
{
  "@context": "https://schema.org",
  "@type": "BreadcrumbList",
  "itemListElement": [{
    "@type": "ListItem",
    "position": 1,
    "name": "サイト名",
    "item": "https://example.com/"
  },{
    "@type": "ListItem",
    "position": 2,
    "name": "カテゴリA",
    "item": "https://example.com/category-a"
  },{
    "@type": "ListItem",
    "position": 3,
    "name": "カテゴリB",
    "item": "https://example.com/category-b"
  },{
    "@type": "ListItem",
    "position": 4,
    "name": "記事ページのタイトル",
    "item": "https://example.com/article-page"
  }]
}
</script>
```

重要なのは __`itemListElement`__ プロパティで、この配列要素として各階層の内容を記述します。
とても単純なので説明するまでもないと思いますが、 __`name`__ プロパティで各階層の名称、 __`item`__ プロパティでその階層を示す URL を指定します。
__`position`__ プロパティは 1、2、3 と順番に振っていけば OK です。

パンくずリストは記事の位置を論理的に階層化しますが、`item` プロパティで指定する URL は階層化されていなくてもよいところが柔軟性があってよいですね。
1 番目の要素にホームページのエントリ、最後の要素に記事ページ自体のエントリを配置するのが無駄なように感じるかもしれませんが、Google 検索の結果ではこのあたりを考慮して表示を最適化してくれるので、心配せずに全て含めておけば大丈夫です。


テンプレートで JSON-LD を出力する
----

ここでは、Hugo のパーシャルテンプレートで、JSON-LD 形式のパンくずリストを出力できるようにします。
まず、ユーティリティ関数として、現在のページの階層構造を `.Page` の配列で取得するパーシャルテンプレートを用意します。

- 参考: [ページの階層構造を取得する関数を作成する (get-hierarchy)](/p/v9t62ux/)

{{< code lang="go-html-template" title="layouts/partials/function/get-hierarchy.html" >}}
{{ $hierarchy := slice . }}

{{ if .Parent }}
  {{ $parentHierarchy := partial "function/get-hierarchy" .Parent }}
  {{ if $parentHierarchy }}
    {{ $hierarchy = $parentHierarchy | append $hierarchy }}
  {{ end }}
{{ end }}

{{ return $hierarchy }}
{{< /code >}}

次のパーシャルテンプレートでは、上記の `get-hierarchy` ユーティリティが返す階層情報を使って、JSON-LD 形式のパンくずリストを出力します。

{{< code lang="go-html-template" title="layouts/partials/breadcrumb-json.html" >}}
{{ $hierarchy := partial "function/get-hierarchy" . }}
<script type="application/ld+json">
{
  "@context": "https://schema.org",
  "@type": "BreadcrumbList",
  "itemListElement": [
    {{- range $index, $page := $hierarchy }}
    {{- if gt $index 0 }},{{ end }}
    {
      "@type": "ListItem",
      "position": {{ add $index 1 }},
      "name": {{ $page.LinkTitle }},
      "item": {{ $page.Permalink }}
    }
    {{- end }}
  ]
}
</script>
{{< /code >}}

あとは、任意のレイアウト用テンプレートから、次のように呼び出せば、そこにパンくずリストが出力されます。

{{< code lang="go-html-template" title="layouts/_default/baseof.html（抜粋）" >}}
<body>
  ... 省略 ...
  {{ partial "breadcrumb-json" . }}
</body>
{{< /code >}}


JSON-LD の内容を検証する
----

出力された JSON-LD 形式のパンくずリストは、下記のサイトで検証することができます。

- [Google 構造化データ テストツール](https://search.google.com/structured-data/testing-tool/u/0/)

`BreadcrumbList` の項目でエラーが出ていないことを確認しましょう。

