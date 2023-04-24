---
title: "Hugo でドラフト指定したセクションが公開されてしまう場合"
url: "p/ynv4ago/"
date: "2019-12-26"
tags: ["Hugo"]
aliases: /hugo/misc/exclude-draft-section.html
---

Hugo のトラブルシューティングです（v0.62.0 で確認）。

ドラフトセクションが公開されてしまう問題
----

Hugo では、記事ページのフロントマターに、

```yaml
draft: true
```

という感じでドラフト指定を行なっておくと、`hugo` コマンドでページ生成するときに __`-D`__ オプションを付けない限り、そのページは出力されなくなります。
しかし、セクションページに関しては、上記のように指定していても、必ず出力されてしまいます（v0.57 くらいからこんな振る舞いになってしまったような・・・）。

例えば、下記のようにトップレベルのセクションを一覧表示しようとすると、ドラフトのセクションまで出力されてしまいます。

```go-html-template
<ul>
  {{ range .Site.Sections }}
    <li><a href="{{ .RelPermalink }}">{{ .Title }}</a>
  {{ end }}
</ul>
```

このあたりの振る舞いは下記で議論されていて、

- [Improve _index.md vs draft · Issue #6312 · gohugoio/hugo · GitHub](https://github.com/gohugoio/hugo/issues/6312)

作者の bep 氏 (Bjørn Erik Pedersen) によると、下記の回答がすべてを表しているようです。

> The original purpose of draft did not include section pages -- as that was not a thing back then.
> （当初はドラフト機能はセクションページを対象にすることを考えていなかった）

セクションをドラフトとしてマークできるようにした場合、その下にぶらさがるページはどう振舞うべきなんだ（一時的にそのセクション自体が存在しないものとしてセクション外に出すの？）とか、仕様が複雑になってしまうのであまり振る舞いを変えたくないという事情があるみたいです。

とは言っても、セクション全体をドラフトとして扱うという機能は需要があるはずです。
bep 氏もドラフトセクションの機能は検討すると言っているようなので、将来のバージョンに期待したいと思います。


ドラフトセクションを表示しない対策
----

ドラフト設定したセクションの出力を抑制する機能は今のところありませんが（2019年時点 v0.62.0）、少なくともセクションを列挙する際に自力でドラフトセクションを無視するようにすることは可能です。

次のサンプルでは、`where` 関数を使って、`.Site.Sections` に含まれるセクションの一覧から、`.Draft` プロパティが `false` であるものだけを抽出しています。
結果として、ドラフトではないセクションだけを列挙できます。

```go-html-template
{{ range where .Site.Sections ".Draft" false }}
  <li><a href="{{ .RelPermalink }}">{{ .Title }}</a>
{{ end }}
```

次のように、各ページ（セクション）の `.Draft` 変数の値によって分岐させる方法もありますが、上記のように `where` 関数を使って最初にフィルタリングしてしまった方がネストが深くならなくてシンプルです。

```go-html-template
{{ range .Site.Sections }}
  {{ if not .Draft }}
    <li><a href="{{ .RelPermalink }}">{{ .Title }}</a>
  {{ end }}
{{ end }}
```

もちろん、上記のようにリンクを出力しないように制御しても、ドラフトセクションのページ自体（HTML ファイル）は生成されてしまうことに注意してください。
生成されているドラフトセクションを見落とさないようにするためには、Hugo をサーバーモードで起動したときに、ドラフトセクションのリンクも含めて出力するようにしておくとよいでしょう。
Hugo がサーバーモードで動作しているかどうかは、`.Site.IsServer` で確認できます。

```go-html-template
{{/* サーバーモードではドラフトセクションも含めて表示 */}}
{{ $sections := .Site.Sections }}
{{ $filtered := cond .Site.IsServer $sections (where $sections ".Draft" false) }}
{{ range $filtered }}
  <li><a href="{{ .RelPermalink }}">{{ .Title }}</a>
{{ end }}
```

ここで使用している `cond` 関数は、C 言語の三項演算子のようなもので、最初のパラメータで指定した `.Site.IsServer` の値が `true` であれば 2 番目のパラメータの値を、`false` の場合は 3 番目のパラメータの値を返します。
よって、サーバーモード時は `$filtered` にドラフトを含むセクション一覧が格納され、リリース時はドラフトを除いたセクション一覧が格納されます。

