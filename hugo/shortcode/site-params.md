---
title: "ショートコードの中から設定ファイルのパラメータを参照する ($.Site.Params)"
date: "2017-10-02"
description: "ショートコードの中で $.Site.Params を参照すると、サイト設定ファイル (config.toml) で設定した params 情報にアクセスすることができます。"
---

例えば、設定ファイル (config.toml) に下記のように記述されていたとします。

#### /config.toml

~~~ toml
baseURL = "http://example.org/"
languageCode = "en-us"
title = "My New Hugo Site"
theme = "my-theme"

[params]
  subtitle = "Hugo is Absurdly Fast!"
  authors = [
    "Maku",
    "Moja"
  ]
~~~

上記の `params` セクションに記述した設定値は、ショートコードの中から `$.Site.Params.<パラメータ名>` で参照することができます。
下記の `site-info` ショートコードは、サイトのタイトルとサブタイトル、筆者情報を表示するショートコードの実装例です。


#### /layouts/shortcodes/site-info.html

~~~ html
<div class="title">{{ "{{" }} $.Site.Title }}</div>
<div class="subtitle">{{ "{{" }} $.Site.Params.subtitle }}</div>
<ul>
  {{ "{{" }} range $.Site.Params.authors }}
    <li>{{ "{{" }}- . -}}
  {{ "{{" }} end }}
</ul>
~~~

<div class="note">
上記のコードの中で、<code>{{ "{{" }}- . -}}</code> と書いてある部分は、ループ処理で取り出した著者の名前を出力することを示しています。<code>{{ "{{" }} . }}</code> と記述する代わりに、前後にハイフンを入れておくことで、出力時に前後の余計なスペースを取り除いてくれます。
</div>

コンテンツファイル (.md) の中からは、下記のようにしてショートコードを利用します。

~~~ md
---
title: "Page1 Title"
date: 2017-10-02
---

{{ "{{" }}< site-info >}}
~~~

ショートコードの部分は、下記のように展開されます。

~~~ html
<div class="title">My New Hugo Site</div>
<div class="subtitle">Hugo is Absurdly Fast!</div>
<ul>
    <li>Maku
    <li>Moja
</ul>
~~~

