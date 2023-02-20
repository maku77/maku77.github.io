---
title: "Hugo でページタイトルに自動でセクションプレフィックスを付ける"
url: "p/dunigdz/"
date: "2019-05-17"
tags: ["Hugo"]
description: "Hugo で多くのコンテンツを作成する場合、記事の種類ごとにセクション（ディレクトリ）に分けて管理することになります。このような場合、各ページのタイトルに対して、セクションのタイトルを自動で付加できれば、ページタイトルの管理が楽になります。"
aliases: /hugo/layout/section-prefix.html
---

Hugo で多くのコンテンツを作成する場合、記事の種類ごとにセクション（ディレクトリ）に分けて管理することになります。
このような場合、各ページのタイトルに対して、セクションのタイトルを自動で付加できれば、ページタイトルの管理が楽になります。

何をするか？
----

例えば、次のようなセクション構造で記事を管理しているとします。

```
contents/
  +-- _index.md (home page)
  +-- section1/
  |    +-- _index.md (section page)
  |    +-- page1.md  (single page)
  |    +-- page2.md  (single page)
  |    +-- page3.md  (single page)
  +-- section2/
       +-- _index.md (section page)
       +-- ...
```

各ディレクトリにある __`_index.md`__ は、セクションページのコンテンツファイルとして使用されます。
このファイルのフロントマターには、下記のようにセクションのタイトルを記述することができます。

{{< code lang="yaml" title="contents/section1/_index.md" >}}
---
title: "セクション1"
date: "2019-05-17"
---

セクションページの本文…
{{< /code >}}

このセクションタイトルは、セクションページ（このケースでは `https://example.com/section1/`）にアクセスしたときに使用されるものですが、同じセクションに所属する記事ページのタイトルのプレフィックスにも自動で付加できると便利です。

例えば、`section1` に所属する記事ページとして下記のようなコンテンツがあるとします。

{{< code lang="yaml" title="contents/section1/page.md" >}}
---
title: "タイトル1"
date: "2019-05-17"
---

ページの本文…
{{< /code >}}

このように記述されているときに、表示するタイトルを `タイトル1` ではなく、__`セクション1: タイトル1`__ のようにしたいということです。
このように、セクションタイトルを付加することで、あるページを単独で表示したときに、どのような階層に配置された記事なのかが分かりやすくなります。


セクションプレフィックスの実装
----

ここでは、シングルページテンプレートで、セクションプレフィックスの仕組みを実現する方法を示します。
下記の例では、構築したページタイトルを `$pageTitle` という変数に格納しています。

{{< code lang="go-html-template" title="layouts/_defaults/single.html（抜粋）" >}}
{{ $pageTitle := cond (eq .CurrentSection.Section "") (.Title) (printf "%s: %s" .CurrentSection.Title .Title) }}

<h1>{{ $pageTitle }}</h1>
{{< /code >}}

コンテンツのルートディレクトリ (`contents`) に配置したページにアクセスしたときは、`.CurrentSection.Section` の値が空 (`""`) になることを利用して、ページのタイトルをそのまま表示するようにしています。

{{% note %}}
上記では、`h1` 要素で表示上のページタイトルを設定していますが、`head` 要素下の `title` 要素にも同様に設定するようにしてください。
Google などの検索エンジンは、`title` 要素に指定したページタイトルを表示します。
{{% /note %}}


（応用）セクションプレフィックス用のプロパティを用意する方法
----

上記の例では、セクションプレフィックスとして、自分が所属するセクションのセクションページに設定された `title` プロパティの値を使用していました。
ここでは、セクションプレフィックス用に、独自のプロパティ __`sectionPrefix`__ を参照するようにしてみます。

このようなケースでは、Hugo の [Front Matter Cascade](https://gohugo.io/content-management/front-matter/#front-matter-cascade) という機能を使用すると便利です。
例えば、セクションページのフロントマターで次のように記述しておくと、

{{< code lang="yaml" title="contents/section1/_index.md" >}}
---
title: "セクションタイトル1"
cascade:
  sectionPrefix: "セクション1"
---
{{< /code >}}

このセクション内の記事では、__`.Params.sectionPrefix`__ 変数で `セクション1` という値を参照できるようになります。
親セクションのフロントマターで __`cascade:`__ 以下に設定したプロパティを、すべての子ページから参照できるということです。

下記はテンプレートの実装例です。

{{< code lang="go-html-template" title="テンプレートの例（抜粋）" >}}
{{ $pageTitle := .Title }}
{{ if ne .Kind "section" }}
  {{ with .Params.sectionPrefix }}
    {{ $pageTitle = printf "%s: %s" . $pageTitle }}
  {{ end }}
{{ end }}

<h1>{{ $pageTitle }}</h1>
{{< /code >}}

現在のページがセクションページでなければ、`sectionPrefix` プロパティの値をタイトルの前に連結する、という処理になっています。

