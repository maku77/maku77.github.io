---
title: "Hugo のセクションテンプレート (section.html) の中でセクションのタイトルを表示する"
url: "p/mn4ji9o/"
date: "2017-11-28"
aliases: /hugo/layout/section-name.html
description: "セクションテンプレートの中では、.Title や .Section を利用してセクションページ用のタイトルを生成することができます。それぞれの変数の違いについて説明します。"
alises: /hugo/layout/section-name.html
---

セクションテンプレートの中では、__`.Title`__ や __`.Section`__ を利用してセクションページ用のタイトルを生成することができます。
それぞれの変数の違いについて説明します。

セクションテンプレートとは
----

[セクションテンプレート](https://gohugo.io/templates/section-templates/)（あるいはセクションページテンプレート）は、content ディレクトリ内の各セクションのインデックスファイル (__`_index.md`__) をレンダリングするときに使用されるテンプレートファイルです。

{{< code title="ディレクトリ構成の例" >}}
content/
  +-- mysection1/
  |   +-- _index.md  ★このファイルや、
  |   +-- page1.md
  |   +-- page2.md
  +-- mysection2/
      +-- _index.md  ★このファイルに適用されるレイアウトです
      +-- page1.md
      +-- page2.md
{{< /code >}}

どのようなファイルがセクションテンプレートとして使用されるかは、本家サイトの下記ページに詳しく説明されています。

- [Section Template Lookup Order](https://gohugo.io/templates/section-templates/#section-template-lookup-order)

すべてのセクションで共通のテンプレートを使用したいのであれば、まずは下記のようなパスにテンプレートファイルを用意するのがよいでしょう。

- `/layouts/_default/section.html` （セクション専用のテンプレート）
- `/layouts/_default/list.html` （セクション以外にも様々なリスト系ページで使われるテンプレート）
＜
テーマとしてテンプレートファイルを提供したいときは、テーマディレクトリの下に同じ構成でファイルを作成すれば OK です。

- `/themes/<テーマ名>/layouts/_default/section.html`
- `/themes/<テーマ名>/layouts/_default/list.html`

__`list.html`__ の方は、ホームページ（ルートの `_index.md`）や、タグやカテゴリーのインデックスページなどにも使用されます。
セクションのインデックスページとしてのみ適用したいテンプレートであれば、__`section.html`__ という名前で作成しましょう。


セクションテンプレートから .Title を参照する
----

セクションテンプレート `layouts/_default/section.html`（存在しない場合は `list.html`）の中で __`{{ .Title }}`__ と記述すると、そのセクションの `_index.md` ファイルのフロントマターに定義した `title` の情報が参照されます。
これはすなわち「セクションページのタイトル」として使用できるものです。

{{% note %}}
シングルページテンプレート `layouts/_default/single.html` の場合も、同様に `{{ .Title }}` と記述することで、各ページのフロントマターに記述した `title` 情報を参照することができます。
{{% /note %}}

{{< code title="ディレクトリ構成の例" >}}
content/
  +-- mysection/
      +-- _index.md （セクションページ）
      +-- a.md
      +-- b.md
      +-- c.md
{{< /code >}}

{{< code lang="yaml" title="セクションページの例 (content/mysection/_index.md)" >}}
---
title: "サンプルセクション"
---

これは本文のサンプル。
{{< /code >}}

{{< code lang="go-html-template" title="セクションテンプレートの例 (layouts/_default/section.html)" >}}
<h1>{{ .Title }}</h1>
{{< /code >}}

上記のような構成で、`http://localhost:1313/mysection/` にアクセスすると、下記のような HTML が表示されることになります。

```html
<h1>サンプルセクション</h1>
```

想定通り、`_index.md` のフロントマターに記述した `title` フィールドの値が表示されていることがわかります。
同様に、セクションテンプレートの中から __`{{ .Content }}`__ を参照すると、`_index.md` に記述した本文の内容がそこに展開されます（この場合は、「これは本文のサンプル。」というテキストが展開されます）。


_index.md がない場合に .Title を参照するとどうなるか？
----

実は、上記のディレクトリ構成で示した `content/mysection/_index.md` というファイルを作成しなくても、そのセクションの `index.html` ファイルは暗黙的に生成されます。
そのようなケースでも、テンプレートファイルとしてはセクションテンプレート (`section.html`) が使用されます。
擬似的な空っぽの `_index.md` ファイルが存在し、そのファイルに対してセクションテンプレートが適用されていると考えればよいでしょう。

ただし、この場合は `_index.md` のフロントマター記述も存在しないため、`{{ .Title }}` で参照できるはずの `title` 情報を取得することができません。
その代わりに、Hugo は __セクションのディレクトリ名から自動的にセクションページのタイトルを生成__ し、それを `{{ .Title }}` の値として使用します。
そのとき生成されるタイトルは、ディレクトリ名と同一ではないことに注意してください。
Hugo はセクションのディレクトリ名の __先頭を大文字にし、さらに複数形に変換した単語__ をタイトルとして生成します。
上記のディレクトリ構成の場合は、__セクションのディレクトリ名は `mysection` なので、自動生成されるタイトルは `Mysections`__ となります。

{{< code lang="html" title="http://localhost:1313/mysection/ にアクセスした場合のレンダリング結果" >}}
<h1>Mysections</h1>
{{< /code >}}

セクション名が自動的に複数形になってしまうのを防ぎたい場合は、Hugo の設定ファイル（`config.toml` など）で下記のように設定します。

{{< code lang="toml" title="/config.toml" >}}
# Pluralize titles in lists using inflect
pluralizeListTitles = false
{{< /code >}}


セクションテンプレートから .Section を参照する
----

セクションテンプレートの中で __`{{ .Section }}`__ を参照すると、セクションのディレクトリ名をそのまま取得することができます。
`{{ .Title }}` と異なり、自動的に先頭を大文字にしたり、複数形にしたりすることがないので、ディレクトリ構成をそのまま出力したい場合にはこちらを使用するのがよいでしょう。

{{< code lang="go-html-template" title="セクションテンプレートの例 (layouts/_default/section.html)" >}}
<h1>{{ .Section }}</h1>
{{< /code >}}

{{< code lang="html" title="http://localhost:1313/mysection/ にアクセスした場合のレンダリング結果" >}}
<h1>mysection</h1>
{{< /code >}}

