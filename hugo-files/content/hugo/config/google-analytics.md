---
title: "Hugo で Google アナリティクス用のトラッキングコードを埋め込む (googleAnalytics)"
url: "p/zxk6pat/"
date: "2018-06-01"
lastmod: "2024-10-26"
tags: ["Hugo"]
description: "Google Analytics を使用すると、Web サイトのアクセス情報を多角的に分析できるようになります。ここでは、Hugo で作成する Web サイトに、簡単に Google Analytics 用のコードを埋め込めるようにしてみます。"
changes:
  - 2023-05-28: Google Analytics 4 (GA4) の記載を追加
  - 2024-10-26: .Site.IsServer を hugo.IsServer に変更
  - 2026-02-08: .Site.GoogleAnalytics was deprecated in Hugo v0.120.0 and will be removed in Hugo 0.137.0. Use .Site.Config.Services.GoogleAnalytics.ID instead.
aliases: /hugo/settings/google-analytics.html
---

Google Analytics を使用すると、Web サイトのアクセス情報を多角的に分析できるようになります。
ここでは、Hugo で作成する Web サイトに、簡単に Google Analytics 用のコードを埋め込めるようにしてみます。

{{% note title="Google Analytics 4 (GA4) への移行" %}}
Google は従来 Google Analytics (__Universal Analytics__) を提供していましたが、2023 年 7 月以降は、最新バージョン Google Analytics 4 (__GA4__) しか使用できなくなります。
ただし、乗り換えは簡単で、設定するトラッキング ID を GA4 用のものに変更するだけです。
Analytics ID の先頭部分を見ると、どちらのバージョンを使用しているかが分かります。

- （旧）Universal Analytics の ID ... `UA-12345678-1`
- （新）Google Analytcs 4 (GA4) の ID ... `G-12345ABCDE`
{{% /note %}}


トラッキング ID をコンフィグファイルで設定する
----

まずは、[Google Analytics](https://analytics.google.com/) の管理画面から、「プロパティの追加」を実行し、分析したい Web サイトのアドレスを追加しておきます（Google Analytics のアカウントを持っていない場合は、先にアカウントから作成しておく必要があります）。
そのとき発行されるトラッキング ID（__`G-12345ABCDE`__ のような ID）が、その Web サイトへのアクセスを判別するための識別情報となります。
この ID は、Web サイトごとに別のものを使用するので、Hugo の[コンフィグファイルで設定](/p/5m9tdwg/)するのがよいでしょう。

Hugo には、コンフィグファイル用のパラメータとして、あらかじめ __`googleAnalytics`__ というパラメータが用意されています。
下記のような感じで、Google Analytics のサイトで発行したトラッキング ID を設定しましょう。

{{< code lang="toml" title="config.toml" >}}
baseURL = "https://example.com/"
languageCode = "ja-jp"
title = "まく日記"
theme = "maku"

[services.googleAnalytics]
  id = "G-12345ABCDE"
{{< /code >}}

上記のように設定すると、テンプレートファイルの中から、__`.Site.Config.Services.GoogleAnalytics.ID`__ でトラッキング ID を参照できるようになります。


トラッキングコード (JS) を自動で埋め込むようにする
----

Google Analytics を有効にするには、各ページの `head` 要素の先頭に、次のような __トラッキングコード__ を埋め込む必要があります（参考: [gtag.js を使用してアナリティクスのトラッキングを設定する](https://support.google.com/analytics/answer/1008080?hl=ja)）。

```html
<!-- Google tag (gtag.js) -->
<script async src="https://www.googletagmanager.com/gtag/js?id=G-12345ABCDE"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'G-12345ABCDE');
</script>
```

ここでは、Hugo のパーシャルテンプレートを使い、上記の HTML コードを各ページに埋め込むようにしましょう。
`G-12345ABCDE` のようなトラッキング ID を指定する部分は、コンフィグファイルの `googleAnalytics` パラメータで指定した値で置換するようにします。

{{< code lang="go-html-template" title="layouts/partials/analytics.html" >}}
{{ if not hugo.IsServer }}
{{ with .Site.Config.Services.GoogleAnalytics.ID }}
<!-- Google tag (gtag.js) -->
<script async src="https://www.googletagmanager.com/gtag/js?id={{ . }}"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', '{{ . }}');
</script>
{{ end }}
{{ end }}
{{< /code >}}

{{% note %}}
上記テンプレートの 1、2 行目では、ローカルな Hugo サーバでテストしている場合や、`googleAnalytics` が設定されていない場合に、トラッキングコードを出力しないように制御しています。
{{% /note %}}

作成したパーシャルテンプレートは、下記のようにテンプレートファイルからインクルードします（ここでは、[ベーステンプレート (baseof)](/p/bbxj5pa/) からインクルードしています）。

{{< code lang="go-html-template" title="layouts/_default/baseof.html" >}}
<!DOCTYPE html>
<html lang="ja">
<head>
  {{- partial "analytics" . -}}
  <meta charset="UTF-8">
  ...省略...
</head>
<body>
  ...省略...
</body>
</html>
{{< /code >}}

これで、すべてのページに Google Analytics のトラッキングコードが埋め込まれるようになります。


（おまけ）Hugo 組み込みのテンプレート
----

実は、Hugo は組み込みの Internal Template として、Google Analytics のトラッキングコードを埋め込むためのテンプレートを用意しています。
任意のテンプレートファイルの中で、

```go-html-template
{{ template "_internal/google_analytics.html" . }}  <!-- 同期バージョン -->
{{ template "_internal/google_analytics_async.html" . }}  <!-- 非同期バージョン -->
```

のように記述するだけで、簡単にトラッキングコードを埋め込むことができます。
ただし、組み込みのテンプレートは、ローカルサーバー動作時のコード出力抑制には対応していませんし、Google の変更に柔軟に対応することもできないので、できればパーシャルテンプレートは自作してしまった方がよいでしょう。

