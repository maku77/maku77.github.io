---
title: "Hugo でパンくずリストを表示する"
url: "p/vemn3c4/"
date: "2018-01-02"
tags: ["Hugo"]
description: "パンくずリストは、親ページに素早くジャンプするためのリンクです。ここでは、Hugo のテンプレート機能を使って、パンくずリストを出力する方法を説明します。"
aliases: /hugo/template/breadcrumbs.html
---

パンくずリストは、親ページに素早くジャンプするための次のようなリンクです。

```
ホーム > セクション1 > セクション2 > ページタイトル
```

ここでは、Hugo のテンプレート機能を使って、パンくずリストを出力する方法を説明します。

パンくずリストを表示するためのテンプレート
----

下記は、パンくずリストを出力するための `breadcrumb` テンプレートの定義です（[Hugo 本家で紹介されているコード](https://gohugo.io/content-management/sections/#example-breadcrumb-navigation)を参考にしています）。

```go-html-template
{{- define "breadcrumb" }}
  {{- if .node.Parent }}
    {{- template "breadcrumb" (dict "node" .node.Parent "start" .start) }}
  {{- else if not .node.IsHome }}
    {{- template "breadcrumb" (dict "node" .node.Site.Home "start" .start) }}
  {{- end }}

  {{- if eq .node .start }}
    <li>{{ .node.LinkTitle }}
  {{- else }}
    <li><a href="{{ .node.Permalink }}">{{ .node.LinkTitle }}</a>
  {{- end }}
{{- end }}
```

実際にパンくずリストを出力したい部分で下記のように呼び出します。

```go-html-template
<ol class="breadcrumb">
  {{- template "breadcrumb" (dict "node" . "start" .) }}
</ol>
```

さらに、下記のようなスタイルを適用すれば、リンクがいい感じで横方向に並んでくれます。

```css
.breadcrumb {
  padding: 0;
}
.breadcrumb li {
  display: inline;
  list-style: none;
}
.breadcrumb li:not(:last-child)::after {
  content: '>';
  padding: 0 0.5em;
}
```

{{< code title="表示イメージ" >}}
ホーム > セクション1 > セクション2 > ページタイトル
{{< /code >}}

{{% note %}}
ここでは、リンクタイトルとして、__`.LinkTitle`__ で取得したページタイトルを表示しています。
記事のフロントマターに、__`linkTitle`__ プロパティが設定されている場合、`title` の設定よりも優先して表示されます。
`linkTitle` に短めのタイトルを設定しおくことで、パンくずリストをシンプルに表示できます。
{{% /note %}}


解説
----

パンくずリストを作成する基本的な考え方は、自分自身のページから親ページを辿るという方法です。
Hugo では、`.Parent` で親ページ（親セクション）を参照することができるので、下記のように再帰的に `.Parent` を辿っていくことで、最上位のホームページまで辿ることができます（ホームページまで到達すると `.Parent` の値が `nil` になるのでそこで再帰呼び出しが止まります）。

```go-html-template
{{- define "breadcrumb" }}
  {{- if .Parent }}
    {{- template "breadcrumb" .Parent }}
  {{- end }}
  <li>{{ .Title }}
{{- end }}

<ol>
  {{- template "breadcrumb" . }}
</ol>
```

例えば、ホームページから見て 2 階層下のセクション内のページで、上記のテンプレートを適用すると、次のような HTML が出力されます。

```html
<ol>
  <li>ホームページ
  <li>セクション
  <li>サブセクション
  <li>サブセクション内の記事タイトル
</ol>
```

通常の記事ページやセクションページでパンくずリストを表示するときは、このアルゴリズムで十分なのですが、これだけだと、タクソノミーリスト（あるタグを持つ記事一覧）のページを表示するケースで、最上位にホームページが表示されません。
なぜなら、タクソノミーリストのページで `.Parent` を参照してもホームページを取得することはできず、`nil` になってしまうからです。
パンくずリストの最上位に必ずホームページを表示するには、下記のように `else if` を追加する必要があります。

```go-html-template
{{- define "breadcrumb" }}
  {{- if .Parent }}
    {{- template "breadcrumb" .Parent }}
  {{- else if not .IsHome }}
    {{- template "breadcrumb" .Site.Home }}
  {{- end }}
  <li>{{ .Title }}
{{- end }}

<ol>
  {{- template "breadcrumb" . }}
</ol>
```

親ページが見つからなかったときに、自分自身がホームページではないとすると、自分自身はタクソノミーリストのページだと考えられます。
なので、そのようなケースでは、強制的に `.Site.Home` をパラメータに渡して、ホームページを出力するようにしています。

上記のテンプレートを、タクソノミーリストのページに適用すると、例えば下記のような感じで表示されます（正しく先頭にホームページが表示されます）。

```html
<ol>
  <li>ホームページ
  <li>タグ１
</ol>
```

あとは、リンク出力やスタイル定義などを加えてあげれば、立派なパンくずリストが完成します。

