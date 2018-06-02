---
title: "Google アナリティクス用のトラッキングコードを埋め込む"
date: "2018-06-01"
description: "Google Analytics を使用すると、Web サイトのアクセス情報を多角的に分析できるようになります。ここでは、Hugo で作成する Web サイトに、簡単に Google Analytics 用のコードを埋め込めるようにしてみます。"
---

トラッキング ID をコンフィグファイルで設定する
----
まずは、[Google Analytics](https://analytics.google.com/) の管理画面から、「プロパティの追加」を実行し、分析したい Web サイトのアドレスを追加しておきます（Google Analytics のアカウントを持っていない場合は、先にアカウントから作成しておく必要があります）。
そのとき発行されるトラッキング ID（**UA-12345678-1** のような ID）が、その Web サイトへのアクセスを判別するための識別情報となります。
この ID は、Web サイトごとに別のものを使用するので、Hugo の[コンフィグファイルで設定](./read-config.html)するのがよいでしょう。

Hugo には、コンフィグファイル用のパラメータとして、あらかじめ **googleAnalytics** というパラメータが用意されています。
下記のような感じで、Google Analytics のサイトで発行したトラッキング ID を設定しましょう。

#### config.toml

~~~ toml
baseURL = "https://example.com/"
languageCode = "ja-jp"
title = "まく日記"
theme = "maku"

googleAnalytics = "UA-12345678-1"
~~~

上記のように設定すると、テンプレートファイルの中から、`.Site.GoogleAnalytics` でトラッキング ID を参照できるようになります。


トラッキングコード (JS) を自動で埋め込むようにする
----

Google Analytics を有効にするには、各ページの `head` 要素の先頭に、次のような**トラッキングコード**を埋め込む必要があります（参考: [gtag.js を使用してアナリティクスのトラッキングを設定する](https://support.google.com/analytics/answer/1008080?hl=ja)）。

~~~ html
<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-12345678-1"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-12345678-1');
</script>
~~~

ここでは、Hugo のパーシャルテンプレートを使い、上記の HTML コードを各ページに埋め込むようにしましょう。
`UA-12345678-1` のようなトラッキング ID を指定する部分は、コンフィグファイルの `googleAnalytics` パラメータで指定した値で置換するようにします。

#### layouts/partials/analytics.html

~~~ html
{{ "{{" }} if not .Site.IsServer }}
{{ "{{" }} with .Site.GoogleAnalytics }}
<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id={{ "{{" }} . }}"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', '{{ "{{" }} . }}');
</script>
{{ "{{" }} end }}
{{ "{{" }} end }}
~~~

<div class="note">
上記テンプレートの 1、2 行目では、ローカルな Hugo サーバでテストしている場合や、<code>googleAnalytics</code> が設定されていない場合に、トラッキングコードを出力しないように制御しています。
</div>

作成したパーシャルテンプレートは、下記のようにテンプレートファイルからインクルードします（ここでは、[ベーステンプレート (baseof)](../template/base-template.html) からインクルードしています）。

#### layouts/_default/baseof.html

~~~ html
<!DOCTYPE html>
<html lang="ja">
<head>
  {{ "{{" }}- partial "analytics" . -}}
  <meta charset="UTF-8">
  ...省略...
</head>
<body>
  ...省略...
</body>
</html>
~~~

これで、すべてのページに Google Analytics のトラッキングコードが埋め込まれるようになります。


### Hugo 組み込みのテンプレートを利用する

実は、Hugo は組み込みの Internal Template として、Google Analytics のトラッキングコードを埋め込むためのテンプレートを用意しています。
任意のテンプレートファイルの中で、

~~~
{{ "{{" }} template "_internal/google_analytics.html" . }}  <!-- 同期バージョン -->
{{ "{{" }} template "_internal/google_analytics_async.html" . }}  <!-- 非同期バージョン -->
~~~

のように記述すれば、簡単にトラッキングコードを埋め込むことができます。
ただし、組み込みのテンプレートは、ローカルサーバ動作させているときのコード出力抑制に対応していませんし、Google の変更に柔軟に対応することもできないので、できればパーシャルテンプレートは自作してしまった方がよいでしょう。

