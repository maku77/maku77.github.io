---
title: "Hugo のベーステンプレートを作成して、各種テンプレートの基本構成を統一する (baseof.html)"
url: "p/bbxj5pa/"
date: "2018-01-10"
tags: ["Hugo"]
description: "Hugo のベーステンプレート機能を利用すると、各種テンプレートの親テンプレートのようなものを作成することができます。ベーステンプレートを作成することで、すべてのページの HTML 構成を統一することができ、個々のテンプレートの見通しもよくなります。"
aliases: /hugo/template/base-template.html
---

Hugo のベーステンプレート機能を利用すると、各種テンプレートの親テンプレートのようなものを作成することができます。
ベーステンプレートを作成することで、すべてのページの HTML 構成を統一することができ、個々のテンプレートの見通しもよくなります。

Hugo では、リストテンプレートやシングルページテンプレートなど、用途によって別々のテンプレートファイルを用意することになっています。
でも、HTML 全体の大まかな構成は共通した部分が多いはずです。
例えば、どのページでも HTML は次のような構成になっているはずです。

```html
<!DOCTYPE html>
<html lang="ja">
  <html>
    <meta charset="UTF-8">
    ...
  </head>
  <body>
    ...
  </body>
</html>
```

[Hugo のベーステンプレート機能](https://gohugo.io/templates/base/)を使用すると、上記のような全体の構成をベーステンプレート (__`baseof.html`__) として作成し、その中の部分的なブロックだけを通常のテンプレート（リストテンプレートやシングルページテンプレート）で定義した内容で置き換えるということができます。

{{< image w="740" src="img-001.svg" title="ベーステンプレートの仕組み" >}}

Hugo のベーステンプレート機能は、[Go のテンプレートライブラリ](https://golang.org/pkg/text/template/)の block template という仕組みを使用しており、__`block`__ で定義した部分を、__`define`__ で定義した内容で置き換えるという動作をします。
上の図を見ると分かりやすいと思いますが、ベーステンプレート (`baseof.html`) を用意すると、基本的にすべてのページがベーステンプレートを利用してレンダリングされるようになり、その中の部分的なブロック（上記では `main` という名前のブロック）が子テンプレートで定義した内容で置き換えられます。
このとき、どの子テンプレートが使用されるかは、ベーステンプレートの仕組みを使用しない場合と同様で、表示するページの種類によって決まります。
例えば、ホームページやセクションページなどのレンダリングには `list.html` が使用され、個々の記事ページのレンダリングには `single.html` が使用されます。


具体的なベーステンプレートの利用例
----

下記はベーステンプレートの具体的な記述例です。
`main` 要素以下のメインコンテンツを block template の機能で置き換えるようにしています。

{{< code title="layouts/_default/baseof.html（ベーステンプレート）" >}}
<!DOCTYPE html>
<html lang="{{ .Site.LanguageCode }}">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1.0">
  <link rel="stylesheet" href="{{ "assets/css/main.css" | relURL }}">
  <title>{{ if not .IsHome }}{{ .Title }}｜{{ end }}{{ .Site.Title }}</title>
  {{ partial "head/favicon" . }}
</head>
<body>
  <div id="root">
    <div id="pageTitle"><a href="{{ "/" | relURL }}">{{ .Site.Title }}</a></div>
    {{ partial "menu" . }}
    <main id="main">
      {{ block "main" . }}main ブロックが見つかりません。{{ end }}
    </main>
  </div>
</body>
</html>
{{< /code >}}

{{< code lang="html" title="layouts/_default/list.html（リストテンプレート）" >}}
{{ define "main" }}
  <h1>{{ .Title }}</h1>
  {{ partial "breadcrumb" . }}
  {{ .Content }}

  <h2>ページリスト</h2>
  <ul>
    {{- range .Data.Pages }}
      <li><a href="{{ .RelPermalink }}">{{ .Title }}</a>
    {{- end }}
  </ul>
{{ end }}
{{< /code >}}

{{< code lang="html" title="layouts/_default/single.html（シングルページテンプレート）" >}}
{{ define "main" }}
  <h1>{{ .Title }}</h1>
  {{ partial "breadcrumb" . }}
  {{ partial "tags-in-page" . }}
  {{ .Content }}
{{ end }}
{{< /code >}}


子テンプレートにはテキスト出力するコードを入れない
----

ベーステンプレート（親テンプレート）を使用したレンダリングを行いたい場合は、子テンプレート側では何も出力しないようにする必要があるようです。
例えば、下記のシングルページテンプレートでは、部分テンプレート `main` を定義していますが、その直後に「こんにちは！」と出力しています。

{{< code title="layouts/_default/single.html" >}}
{{ define "main" }}
  メインコンテンツ１
{{ end }}

こんにちは！
{{< /code >}}

このように記述してしまうと、シングルページレイアウトには、ベーステンプレート (`baseof.html`) によるレイアウトが適用されなくなり、「こんにちは！」としか表示されなくなってしまうようです。

逆に、ベーステンプレートを適用したくないテンプレートファイルでは、そのテンプレートファイルだけで出力を完結してしまえばよいということですね。

