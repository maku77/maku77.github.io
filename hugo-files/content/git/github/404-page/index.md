---
title: "GitHub Pages で独自の 404 ページを用意する"
url: "p/bf2nban/"
date: "2018-09-12"
tags: ["GitHub"]
description: "GitHub Pages で存在しないページにアクセスすると、デフォルトでは GitHub が用意している 404 ページが表示されます。ここでは、独自の 404 ページを用意する方法を説明します。"
aliases: /git/github/404-page.html
---

GitHub Pages で存在しないページにアクセスすると、デフォルトでは GitHub が用意している 404 ページが表示されます。
Web サイトのルートパスに `404.html` ファイルを配置すると、独自の 404 ページを表示することができます。
ここでは、Jekyll のページ生成の仕組みを使って、独自の 404 ページを作成する方法を説明します。

{{< image w="600" src="img-001.png" title="GitHub Pages で独自の 404 ページを表示した例" >}}


コンテンツファイル (`404.md`) の作成
----

まず、プロジェクトのルートディレクトリに __`404.md`__ を作成し、`404.html` というページが出力されるようにします。

{{< code lang="yaml" title="404.md" >}}
---
title: "404"
layout: "404"
permalink: "404.html"
---
{{< /code >}}

表示内容に関しては、404 ページ専用のレイアウト (__`_layouts/404.html`__) の方で定義することにします。
よって、上記の Markdown ファイルでは、フロントマターで出力設定だけを記述しておきます。


レイアウトファイル (`_layouts/404.html`) の作成
----

404 ページの出力内容は特有のレイアウトになることが多いので、専用のレイアウトファイルで作成するのがオススメです。
ここでは、__`_layouts/404.html`__ というパスにファイルを作成します（`404.md` のフロントマターで指定したレイアウト名に合わせてください）。

{{< code lang="html" title="_layouts/404.html の記述例" >}}
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta property="og:title" content="{{ page.title }} | {{ site.name }}" />
  <title>{{ page.title }} | {{ site.name }}</title>
  <link rel="icon" sizes="16x16 32x32 48x48 64x64" href="/assets/img/favicon/favicon.ico" />
  <link rel="apple-touch-icon-precomposed" href="/assets/img/favicon/favicon-152.png" />
  <style>
  body {
    background: #eee;
    margin: 1em auto;
    text-align: center;
  }
  .content {
    width: 500px;
    margin: 0 auto;
  }
  h1 {
    font-size: 3rem;
    margin: 1em 0;
  }
  a {
    color: #36f;
    font-weight: bolder;
    text-decoration: none;
  }
  a:hover {
    color: white;
    background: #36f;
  }
  img {
    width: 100px;
    vertical-align: bottom;
  }
  </style>
  {% if jekyll.environment != "development" %}
    {% include analytics.html %}
  {% endif %}
</head>
<body>
  <h1>{{ page.title }}</h1>
  <div class="content">
    <p>ページが見つかりませんでした。</p>
    <p><a href="{{ site.url }}">{{ site.name }}のトップページ</a>へ移動するか、<br>
       下記から検索してみてください。</p>
    <p>{% include search-bar.html %}</p>
    <p><a href="{{ site.url }}"><img src="{{ site.logo }}"></a></p>
  </div>
</body>
</html>
{{< /code >}}

上記の例では、Google Analytics 用のコード (`_includes/analytics.html`) も挿入するようにしています。
こうしておけば、404 ページへのアクセスも解析できるようになるので便利です。
Google Analytics を使用していないのであれば外してください。
また、404 ページからトップページへ飛ばすだけでは不親切なので、Google カスタム検索のためのフォーム (`_includes/search-bar.html`) も配置するようにしています。
このあたりは適宜調整してください。

上記のレイアウトファイルでは、`site` 変数を使ってサイト名 (`site.name`) やロゴファイル名 (`site.logo`) を取得しているので、`_config.yml` の中で下記のような感じで設定しておいてください。

{{< code lang="yaml" title="_config.yml" >}}
name: 天才まくまくノート
url: https://maku77.github.io
logo: /assets/img/logo.jpg
{{< /code >}}

あとは、作成した `404.md` と `_layouts/404.html` を GitHub 上のリポジトリにプッシュすれば完成です。


参考サイト
----

- [Creating a custom 404 page for your GitHub Pages site - User Documentation](https://help.github.com/articles/creating-a-custom-404-page-for-your-github-pages-site/)

