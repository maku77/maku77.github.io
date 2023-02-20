---
title: "Hugo でページの階層構造を取得する関数を作成する (get-hierarchy)"
url: "p/v9t62ux/"
date: "2020-05-16"
tags: ["Hugo"]
aliases: /hugo/layout/get-hierarchy.html
---

何を作るか？
----

Hugo の記事ページは、次のような感じで、セクション機能を使って階層化することができます。

```
ホーム ＞ セクション1 ＞ セクション2 ＞ ページタイトル
```

ここでは、上記のような階層構造をスライス（配列）で取得する [関数を作成](/p/4anjern/) してみます。
ページの階層構造を取得できると、パンくずリストなどを簡単に出力できるようになります。


階層構造を取得する関数
----

関数はパーシャルテンプレートの形で作るので、__`layouts/partials`__ の下に `.html` ファイルを作成します。
ここでは、関数であることを明確にするために、さらに __`function`__ という名前のディレクトリを作ってその下に格納することにします。
関数名（テンプレート名）は __`get-hierarchy`__ とします。

{{< code lang="go-html-template" title="layouts/partials/function/get-hierarchy.html" >}}
{{ $hierarchy := slice . }}

{{ if .Parent }}
  {{ $parentHierarchy := partial "function/get-hierarchy" .Parent }}
  {{ if $parentHierarchy }}
    {{ $hierarchy = $parentHierarchy | append $hierarchy }}
  {{ end }}
{{ end }}

{{ return $hierarchy }}
{{< /code >}}

この関数は、任意のテンプレートファイルから次のように呼び出します。

```go-html-template
{{ $hierarchy := partial "function/get-hierarchy" . }}
```

`$hierarchy` 変数には、__ホームページからカレントページまでの `Page` 変数の配列データ__ が格納されます。
例えば、次のようにすれば、ページ階層を番号付きでリンク表示できます。

```go-html-template
{{ $hierarchy := partial "function/get-hierarchy" . }}
{{- range $index, $page := $hierarchy }}
  {{ $index }}: <a href="{{ $page.RelPermalink}}">{{ $page.Title }}</a><br>
{{- end }}
```

{{< code title="表示結果のイメージ" >}}
0: ホーム
1: セクション1
2: セクション2
3: ページタイトル
{{< /code >}}

この `get-hierarchy` 関数を使用すれば、パンくずリストのようなものも簡単に表示できるはずです。

- 参考: [パンくずリストを表示する](/p/vemn3c4/) ← こちらの記事では関数を使わずに出力してますが

