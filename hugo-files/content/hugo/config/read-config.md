---
title: "Hugo でコンフィグファイルに設定した情報を参照する"
url: "p/5m9tdwg/"
date: "2018-06-01"
tags: ["Hugo"]
aliases: /hugo/settings/read-config.html
---

コンフィグファイルの基本
----

Hugo サイトの全般的な設定は、ルートディレクトリに置いた下記のいずれかのコンフィグファイルで行います（最初に見つかったものが採用されます）。

1. `config.toml` （TOMLフォーマットで記述）
2. `config.yaml` （YAMLフォーマットで記述）
3. `config.json` （JSONフォーマットで記述）

{{< code lang="toml" title="config.toml の記述例" >}}
baseURL = "https://example.com/"
languageCode = "ja-jp"
title = "まく日記"
themesDir = "../hugo_themes"
theme = "maku"
{{< /code >}}

設定可能なパラメータや、それぞれのデフォルト値は下記に一覧があります。

- [Configure Hugo｜Hugo](http://gohugo.io/getting-started/configuration/)


設定値を参照する
----

### Hugo が定義しているパラメータを参照する

コンフィグファイルで設定したパラメータは、テンプレートファイル内から、__`.Site`__ 変数を使って参照することができます。
例えば、`config.toml` ファイルの中で設定した `title` や `baseURL` パラメータの値は次のようにして参照することができます。

```go-html-template
このサイトのタイトルは <b>{{ $.Site.Title }}</b> です。
このサイトのベースアドレスは <b>{{ $.Site.BaseURL }}</b> です。
```

パラメータ名の先頭は、大文字になっていることに注意してください（`.Site.title` ではなく `.Site.Title` が正しい）。
それぞれの設定値が、どのようなプロパティ名で参照できるようになっているかは、下記の Site 変数の一覧を見るとわかります。

- [Site Variables｜Hugo](https://gohugo.io/variables/site/)

パラメータが設定されているかどうかで処理を分岐させたい場合は、例えば次のように記述しておけばよいでしょう。
ここでは、`googleAnalytics` というパラメータで、Google Analytics 用のトラッキング ID が設定されているかどうかをチェックしています。

```go-html-template
{{ with $.Site.GoogleAnalytics }}
  Google Analytics のトラッキング ID は <b>{{ . }}</b> です。
{{ else }}
  Google Analytics のトラッキング ID が設定されていません。
{{ end }}
```

- 参考: [Google アナリティクス用のトラッキングコードを埋め込む](/p/zxk6pat/)

### 独自定義のパラメータを参照する

コンフィグファイルで独自定義のパラメータを設定するには、__`params`__ セクションの下にパラメータを追加します。

{{< code lang="toml" title="config.toml" >}}
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
{{< /code >}}

独自定義のパラメータは、テンプレートファイル内から __`$.Site.Params.名前`__ という形で参照することができます。
下記の例では、独自パラメータの `Description` の値を参照しています。

{{< code lang="go-html-template" title="独自パラメータを参照する (layouts/partials/head.html)" >}}
<meta name="description" content="{{ if .IsHome }}{{ $.Site.Params.description }}{{ else }}{{ .Description }}{{ end }}" />
{{< /code >}}

ちなみに、このサンプルでは、ホームページではサイトのコンフィグ設定 (__`$.Site.Params.Description`__) を参照し、それ以外のページでは、各ページのフロントマターに設定した __`description`__ パラメータの値を参照するようにしています。

