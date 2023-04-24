---
title: "Hugo で「このページを編集 (Edit this page)」のリンクを表示する"
url: "p/vit4ckt/"
date: "2020-04-01"
tags: ["Hugo"]
description: "Hugo のサイトコンテンツを GitHub で管理しているときは、GitHub の編集ページにリンクを張っておくと便利かもしれません。"
aliases: /hugo/advanced/edit-this-page.html
---

GitHub の編集ページ
----

GitHub には簡易的なエディタ機能があり、ウェブサイト上で Markdown ファイルを直接編集できるようになっています。
Hugo サイトのコンテンツを GitHub で管理しているのであれば、GitHub の編集ページにリンクを張っておくと便利かもしれません。
この編集ページを使用すれば、Git コマンドに不慣れな人でも手軽に Markdown ファイルを修正することができます。

例えば、`https://github.com/yourname/website/` といった GitHub リポジトリで Hugo のサイトコンテンツを管理している場合、トップページの Markdown ファイルを編集するための URL は次のようになります。

```
https://github.com/yourname/website/edit/master/content/_index.md
```

ここでは、Hugo で生成する各ページに、上記のような編集ページへのリンクを出力することを考えてみます。


編集ページへのリンクを出力する
----

まず、GitHub リポジトリのベース URL は、Hugo の設定ファイルに、独自パラメータ __`editBaseURL`__ として定義することにしましょう。

{{< code lang="toml" title="hugo.toml" >}}
baseURL = "https://example.com"
languageCode = "ja-jp"
title = "私のウェブサイト"

[params]
  editBaseURL = "https://github.com/yourname/website/edit/master/content"
{{< /code >}}

次に、編集ページへのリンクを出力するためのパーシャルテンプレートを作成します。

{{< code lang="go-html-template" title="layouts/partials/edit-this-page.html" >}}
{{ if $.Site.Params.editBaseURL }}
  {{- $filepath := replace .File.Path "\\" "/" -}}
  {{- $url := printf "%s/%s" $.Site.Params.editBaseURL $filepath -}}
  <a href="{{ $url }}">Edit this page</a>
{{- end }}
{{< /code >}}

このテンプレートでは次のようなことを行っています。

1. 設定ファイルに `editBaseURL` が定義されている場合のみ処理を継続
2. `.File.Path` で、Markdown ファイルの `content` ディレクトリからの相対パスを取得（さらに、Windows を考慮してバックスラッシュをスラッシュに変換しておく）
3. `editBaseURL` の値と Markdown ファイルのパスを組み合わせて、編集ページの URL を構築（URL の結合に `path.Join` が使えるかと思ったけど、`https://` のスラッシュが一個削除されてしまってうまくいかないので、ここでは `printf` で結合してます）
4. HTML のアンカータグ (`a`) を出力

あとは、このパーシャルテンプレートを、任意のレイアウトファイルから呼び出せば OK です。
ここでは、シングルページテンプレートから呼び出してみます。

{{< code lang="go-html-template" title="layouts/_default/single.html（抜粋）" >}}
{{ partial "edit-this-page" . }}
{{< /code >}}

