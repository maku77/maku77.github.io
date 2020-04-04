---
title: "コンフィグファイルに設定した情報を参照する"
date: "2018-06-01"
---

コンフィグファイルの基本
----

Hugo サイトの全般的な設定は、ルートディレクトリに置いた下記のいずれかのコンフィグファイルで行います（最初に見つかったものが採用されます）。

1. `config.toml` （TOMLフォーマットで記述）
2. `config.yaml` （YAMLフォーマットで記述）
3. `config.json` （JSONフォーマットで記述）

### config.toml の例

~~~ toml
baseURL = "https://example.com/"
languageCode = "ja-jp"
title = "まく日記"
themesDir = "../hugo_themes"
theme = "maku"
~~~

設定可能なパラメータや、それぞれのデフォルト値は下記に一覧があります。

- [Configure Hugo｜Hugo](http://gohugo.io/getting-started/configuration/)


設定値を参照する
----

### Hugo が定義しているパラメータを参照する

コンフィグファイルで設定したパラメータは、テンプレートファイル内から、`.Site` 変数を使って参照することができます。
例えば、`config.toml` ファイルの中で設定した `title` や `baseURL` パラメータの値は次のようにして参照することができます。

~~~ html
このサイトのタイトルは <b>{{ "{{" }} $.Site.Title }}</b> です。
このサイトのベースアドレスは <b>{{ "{{" }} $.Site.BaseURL }}</b> です。
~~~

パラメータ名の先頭は、大文字になっていることに注意してください（`.Site.title` ではなく `.Site.Title` が正しい）。
それぞれの設定値が、どのようなプロパティ名で参照できるようになっているかは、下記の Site 変数の一覧を見るとわかります。

- [Site Variables｜Hugo](https://gohugo.io/variables/site/)


パラメータが設定されているかどうかで処理を分岐させたい場合は、例えば次のように記述しておけばよいでしょう。
`googleAnalytics` というパラメータで、Google Analytics 用のトラッキング ID が設定されているかどうかをチェックしています。

~~~ html
{{ "{{" }} with $.Site.GoogleAnalytics }}
  Google Analytics のトラッキング ID は <b>{{ "{{" }} . }}</b> です。
{{ "{{" }} else }}
  Google Analytics のトラッキング ID が設定されていません。
{{ "{{" }} end }}
~~~


### 独自定義のパラメータを参照する

コンフィグファイルで独自定義のパラメータを設定するには、`params` セクションの下にパラメータを追加します。

~~~ toml
baseURL = "https://example.com/"
languageCode = "ja-jp"
title = "まく日記"

[params]
  GitHubUser = "maku77"
  Twitter = "hogehoge"
  Subtitle = "天才まくまくのゆるふわ日記といろんなメモ"
  Description = "ここには、このサイトの詳細情報を記述します。"
  SidebarRecentLimit = 5
  ListOfFoo = ["foo1", "foo2"]
~~~

独自定義のパラメータは、テンプレートファイル内から `$.Site.Params.名前` という形で参照することができます。
下記の例では、独自パラメータの `Description` の値を参照しています。


#### 独自パラメータを参照する (layouts/partials/head.html)

~~~ html
<meta name="description" content="{{ "{{" }}if .IsHome}}{{ "{{" }} $.Site.Params.description }}{{ "{{" }}else}}{{ "{{" }}.Description}}{{ "{{" }}end}}" />
~~~

ちなみに、このサンプルでは、ホームページではサイトのコンフィグ設定 (`$.Site.Params.Description`) を参照し、それ以外のページでは、各ページのフロントマターに設定した `description` パラメータの値を参照するようにしています。

