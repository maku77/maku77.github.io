---
title: "Hugo のショートコードの中から設定ファイルのパラメータを参照する ($.Site.Params)"
url: "p/53patex/"
date: "2017-10-02"
tags: ["Hugo"]
description: "Hugo のショートコードの中で $.Site.Params を参照すると、サイト設定ファイル (config.toml) で設定した params 情報にアクセスすることができます。"
aliases: /hugo/shortcode/site-params.html
---

Hugo のショートコードの中で __`$.Site.Params`__ を参照すると、設定ファイル (`config.toml`) の __`params`__ セクションに記述した情報を取得することができます。
例えば、設定ファイルに下記のように記述されていたとします。

{{< code lang="toml" title="config.toml" >}}
baseURL = "http://example.org/"
languageCode = "ja-jp"
title = "わたしのブログ"
theme = "my-theme"

[params]
  subtitle = "Hugo を使って日記を書いています"
  authors = [
    "Maku",
    "Ponyo"
  ]
{{< /code >}}

上記の `params` セクションに記述した設定値は、ショートコードの中から __`$.Site.Params.<パラメータ名>`__ で参照することができます。
下記の `site-info` ショートコードは、サイトのタイトルとサブタイトル、筆者情報を表示するショートコードの実装例です。


{{< code lang="go-html-template" title="layouts/shortcodes/site-info.html" >}}
<div class="title">{{ $.Site.Title }}</div>
<div class="subtitle">{{ $.Site.Params.subtitle }}</div>
<ul>
  {{ range $.Site.Params.authors }}
    <li>{{- . -}}</li>
  {{ end }}
</ul>
{{< /code >}}

{{% note %}}
上記のコードの中で、__`{{- . -}}`__ と書いてある部分は、ループ処理で取り出した著者の名前を出力することを示しています。
`{{ . }}` と記述する代わりに、前後にハイフンを入れておくことで、出力時に前後の余計なスペースを取り除いてくれます。
{{% /note %}}

コンテンツファイル (`.md`) の中からは、下記のようにしてショートコードを呼び出すことができます。

```yaml
---
title: "ページタイトル"
date: "2017-10-02"
---

{{</* site-info */>}}
```

ショートコードの部分は、下記のように展開されます。

```html
<div class="title">わたしのブログ</div>
<div class="subtitle">Hugo を使って日記を書いています</div>
<ul>
    <li>Maku
    <li>Ponyo
</ul>
```

