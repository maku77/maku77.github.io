---
title: "このページを編集 (Edit this page) のリンクを作成する"
date: "2020-04-01"
description: "Hugo のサイトコンテンツを GitHub で管理しているときは、GitHub の編集ページにリンクを張っておくと便利かもしれません。"
---

GitHub の編集ページ
----

GitHub には簡易的なエディタ機能があり、ウェブサイト上で Markdown ファイルを直接編集することができます。
このページ使用すれば、Git コマンドに不慣れな人でも、手軽に Markdown ファイルを修正することができます。

例えば、`https://github.com/yourname/website/` といった GitHub リポジトリで Hugo のサイトコンテンツを管理している場合、トップページの Markdown ファイルを編集するための URL は次のようになります。

```
https://github.com/yourname/website/blob/master/content/_index.md
```

ここでは、Hugo で生成する各ページに、上記のような編集ページへのリンクを出力することを考えてみます。


編集ページへのリンクを出力する
----

まず、GitHub リポジトリのベース URL は、Hugo の設定ファイルに、独自パラメータ **`editBaseURL`** として定義することにしましょう。

```toml
baseURL = "https://example.com"
languageCode = "ja-jp"
title = "私のウェブサイト"

[params]
  editBaseURL = "https://github.com/yourname/website/edit/master/content"
```

次に、編集ページへのリンクを出力するためのパーシャルテンプレートを作成します。

#### layouts/partials/edit-this-page.html

```
{{ "{{" }} if and .IsPage $.Site.Params.editBaseURL }}
  {{ "{{" }}- $filepath := replace .File.Path "\\" "/" -}}
  {{ "{{" }}- $url := path.Join $.Site.Params.editBaseURL $filepath -}}
  <a href="{{ "{{" }} $url }}">Edit this page</a>
{{ "{{" }}- end }}
```

このテンプレートでは次のようなことを行っています。

1. 現在のページが通常の記事ページであり、設定ファイルに `editBaseURL` が定義されている場合のみ処理
2. `.File.Path` で、Markdown ファイルの `content` ディレクトリからの相対パスを取得（さらに、Windows を考慮してバックスラッシュをスラッシュに変換しておく）
3. `editBaseURL` の値と Markdown ファイルのパスを組み合わせて、編集ページの URL を構築
4. HTML のアンカータグ (`a`) を出力

あとは、このパーシャルテンプレートを、任意のレイアウトファイルから呼び出せば OK です。
ここでは、シングルページテンプレートから呼び出してみます。

#### layouts/_default/single.html（抜粋）

```
{{ "{{" }} partial "edit-this-page" . }}
```

