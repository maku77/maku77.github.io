---
title: "Hugo でサイトのヘッダーとフッターをパーシャルファイルに分離する"
url: "p/wvi3n7q/"
date: "2017-12-27"
tags: ["Hugo"]
description: "サイト内で共通のヘッダー部分とフッター部分を Hugo のパーシャルテンプレートファイルとして作成しておくと、全てのテンプレートから参照できるため、テンプレートの記述をシンプルにすることができます。"
aliases: /hugo/template/partial-header.html
---

パーシャルテンプレートでページ構成を分割する
----

サイト内で共通のヘッダー部分とフッター部分を Hugo の __パーシャルテンプレートファイル__ として作成しておくと、全てのテンプレートから参照できるため、テンプレートの記述をシンプルにすることができます。
Hugo のテンプレート構成では、下記のような感じでテンプレート前半部分と後半部分をまるごとパーシャルファイルで構成してしまうのが一般的のようです。

{{< image src="img-001.png" title="ヘッダーとフッターをパーシャルテンプレート化" >}}

上記ではリストテンプレート (`list.html`) の例を示していますが、ホームページテンプレート (`layouts/index.html`) や、セクションテンプレート (`layouts/_default/section.html`)、シングルページテンプレート (`layouts/_default/single.html`) などを記述する際も同様に構成します。
つまり、パーシャルファイルとして作成する `header.html` や `footer.html` は、どのテンプレートファイルからでも使用できるように、汎用的な記述をしておく必要があります。

{{< code lang="html" title="layouts/partials/header.html" >}}
<!DOCTYPE html>
<html lang="{{ .Site.LanguageCode }}">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1.0">
  <link rel="stylesheet" href="/assets/css/main.css">
  <title>{{ if not .IsHome }}{{ .Title }} | {{ end }}{{ .Site.Title }}</title>
  {{ partial "header-favicon" . }}
</head>
<body>
{{< /code >}}

{{< code lang="html" title="layouts/partials/footer.html" >}}
</body>
</html>
{{< /code >}}

上記のようなパーシャルテンプレートは、各レイアウト用のテンプレートから次のように使用します。

{{< code lang="html" title="layouts/_default/list.html" >}}
{{ partial "header" . }}

<main id="main">
  <h1>{{ .Title }}</h1>
  {{ .Content }}
</main>

{{ partial "footer" . }}
{{< /code >}}

ここでは、リストテンプレート内で使用する方法を示していますが、ホームテンプレートや、シングルページテンプレートなどでも同様に使用できます。


ベーステンプレートの機能を使うのがオススメ
----

上記では、HTML の前半部分や後半部分をパーシャルテンプレートとして分離する方法を示しましたが、Hugo の仕組みに慣れてきたら、__ベーステンプレートの機能__ を使って HTML を構成する方法がお勧めです。

- 参考: [ベーステンプレートを作成して、各種テンプレートの基本構成を統一する](/p/bbxj5pa/)

