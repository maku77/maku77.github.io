---
title: "Hugo のセクション機能の基本（記事を階層化する）"
url: "p/8ihz7es/"
date: "2017-10-04"
tags: ["Hugo"]
description: "Hugo では、記事ファイルをディレクトリ内に格納するだけで、その名前の「セクション」に所属する記事として扱うことができます。この機能は、記事を物理的な階層構造で管理するために使用することができます。"
aliases: /hugo/layout/section.html
---

Hugo では、記事ファイルをディレクトリ内に格納するだけで、その名前の __セクション__ に所属する記事として扱うことができます。
この機能は、記事を物理的な階層構造で管理するために使用することができます。

セクションの基本
----

Hugo の記事ファイル（`.md` ファイル）は、__`content`__ ディレクトリに格納していきますが、`content` ディレクトリの下にさらにディレクトリを作成して、そこに格納することもできます。
例えば、次のように `author` というサブディレクトリを作成して、その下に `.md` ファイルを配置することができます。

- `/content/author/maku.md`

このようなディレクトリ階層を作成すると、`maku.md` という記事は、`author` という __セクションに所属する記事__ として扱われるようになります。
セクションはディレクトリ階層で表現されるため、1 つの記事は、1 つのセクションにしか所属できません。

セクション名は、記事のテンプレート内から __`{{ .Section }}`__ で参照することができます（Page オブジェクトのプロパティ）。
個々の記事のレイアウトは、__`single.html`__ というテンプレートファイル ([single page template](https://gohugo.io/templates/single-page-templates/)) によって行われるため、まずはこのテンプレートファイルを作成しましょう。

{{< code lang="go-html-template" title="layouts/_default/single.html" >}}
<h1>{{ .Title }}</h1>

セクション名: {{ with .Section }}{{ . }}{{ else }}なし{{ end }}

<main>
{{ .Content }}
</main>
{{< /code >}}

{{% note %}}
ここで使用している `with` という構文は、`if` の拡張構文です。
指定した値が存在する場合、シンプルに `{{ . }}` でその値を参照できます。
上記のテンプレートファイルの例では、`.Section` が存在する場合だけその値を表示し、存在しない場合は「なし」と表示するようにしています。
{{% /note %}}

記事ファイル (`.md`) としては、次の 2 種類を用意してどのように表示されるか比べてみます。

{{< code lang="yaml" title="content/sample.md（どのセクションにも所属しない記事）" >}}
---
title: "サンプル"
date: "2017-10-04"
---

ダミー本文
{{< /code >}}

{{< code lang="yaml" title="content/author/maku.md（author セクションに所属する記事）" >}}
---
title: まく
date: "2017-10-04"
---

ダミー本文
{{< /code >}}

それぞれのページにアクセスすると、下記のように表示されます。

{{< image border="true" src="img-001.png" title="content/sample.md の場合" >}}

{{< image border="true" src="img-002.png" title="content/author/maku.md の場合" >}}

`author` ディレクトリ以下に配置した記事だけ、セクション名を取得できていることがわかります。


セクションの index ページを作成する (_index.md)
----

セクション用の index ページ（__section page__ と呼びます）は、そのセクションのディレクトリ直下に __`_index.md`__ というファイル名で作成します。
例えば、下記は `event` セクションに属するコンテンツの構成を示しています。

{{< code title="event セクションの記事" >}}
/content/event/_index.md  (section page)
/content/event/page1.md   (single page)
/content/event/page2.md   (single page)
/content/event/page3.md   (single page)
{{< /code >}}

上記の index ページには、`http://localhost:1313/event/` というアドレスでアクセスできるようになります。
セクションの index ページがレンダリングされるとき、レイアウトファイルとしては `section.html` ([section page template](https://gohugo.io/templates/section-templates/)) 、あるいは `list.html` ([list page template](https://gohugo.io/templates/lists/)) が使用されます。
個々の記事ファイルをレンダリングするときに使用される `single.html` ([single page template](https://gohugo.io/templates/single-page-templates/)) とは異なるレイアウトファイルが使用されることに注意してください。
これはつまり、index ページがセクション内のコンテンツを __一覧表示するためのリストページ__ として特殊扱いされることを意味しています。
より具体的には、下記のファイルのうち、最初に見つかったレイアウトファイルが `_index.md` のレンダリングに使用されます（[Section Page Templates](https://gohugo.io/templates/section-templates/) より抜粋）。

{{< code title="セクションの index ページ用のレイアウトファイル" >}}
/layouts/section/<SECTION>.html
/layouts/<SECTION>/list.html
/layouts/_default/section.html
/layouts/_default/list.html
/themes/<THEME>/layouts/section/<SECTION>.html
/themes/<THEME>/layouts/<SECTION>/list.html
/themes/<THEME>/layouts/_default/section.html
/themes/<THEME>/layouts/_default/list.html
{{< /code >}}

{{% note title="list.html は section.html よりも汎用的に使われる" %}}
`/layouts/_default/list.html` は、ホームページ（ルートの `_index.html`）や、タクソノミーリスト（あるタグがついたページの一覧）などのレイアウトファイルとしても使用されます。
ホームページ専用のレイアウトファイル、タクソノミーリスト専用のレイアウトファイルを用意したい場合は、`/layouts/index.html`、`/layouts/_default/taxonomy.html` などを作成します。
このあたりの詳細は、Hugo マニュアルの [Homepage Template](https://gohugo.io/templates/homepage/)、[Taxonomy Templates](https://gohugo.io/templates/taxonomy-templates/) を参照してください。
{{% /note %}}

ここでは、`/layouts/_default/list.html` というファイルとしてレイアウトファイルを作成してみます（`list.html` は、ホームページやタクソノミーリストのページにも使用される、リスト系ページ用の汎用レイアウトファイルです）。

{{< code lang="go-html-template" title="/layouts/_default/list.html" >}}
<main>
  <article>
    <header>
      <h1>{{ .Title }}</h1>
    </header>
    {{ .Content }}
  </article>
  <ul>
    {{ range .Data.Pages }}
    <li>
      <a href="{{ .Permalink }}">{{ .Title }} ({{ .Date.Format "2006-01-02" }})</a>
    </li>
    {{ end }}
  </ul>
</main>
{{< /code >}}

上記のテンプレートを作成した後で、`http://localhost:1313/event/` にアクセスすると、下記のように表示されます。

{{< image border="true" src="img-003.png" >}}


セクションの index ページ専用のレイアウトを作成する (section.html)
----

`layouts/_default/list.html` というレイアウトファイルは、汎用的な list page template として使用されるため、各セクションの `_index.md` のレンダリング以外にも、ホームページ（ルートの `_index.md`）のレンダリングや、タグの一覧ページのレンダリングにも使用されます。
各セクションの `_index.md` をレンダリングするための専用のレイアウトファイルを用意したいときは、`list.html` という名前のレイアウトファイルの代わりに、次のような名前のレイアウトファイルを作成します。

{{< code title="セクションの index ページ専用のレイアウトファイル" >}}
/layouts/section/<SECTION>.html
/layouts/_default/section.html
/themes/<THEME>/layouts/section/<SECTION>.html
/themes/<THEME>/layouts/_default/section.html
{{< /code >}}

上記のうち最初に見つかったレイアウトファイルが使用されます。
パスの中にセクション名 (`<SECTION>`) を含むファイルを用意しておくと、そのセクション専用の index ページをレンダリングするためのレイアウトファイルとして使用されます。
例えば、`/layouts/section/event.html` というレイアウトファイルは、`event` セクションの `_index.md` をレンダリングするためのレイアウトファイルとして使用されます。
全セクションの `_index.md` で共通のレイアウトファイルを使用するのであれば、__`layouts/_default/section.html`__ というファイルをひとつだけ用意しておけば OK です。

