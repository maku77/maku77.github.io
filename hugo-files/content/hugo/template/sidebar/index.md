---
title: "Hugo でサイドバー用のページツリーを表示する（現在表示しているページを考慮した階層表示）"
url: "p/7o7ymst/"
date: "2018-01-23"
lastmod: "2023-02-21"
tags: ["Hugo"]
description: "サイドバーメニューに、サイトの階層構造に応じたリンクを表示しておくと、サイト内の様々なページに簡単に移動できるようになります。"
aliases: /hugo/list/sidebar-menu.html
changes:
  - Hugo のバージョンアップで仕様が変わったので全体的に修正。
---

サイドバーメニューに、サイトの階層構造に応じたリンクを表示しておくと、サイト内の様々なページに簡単に移動できるようになります。

サイドバーでのページツリー表示のイメージ
----

サイト全体のページ一覧をツリー構造で表示する方法は、下記のページで紹介しています。

* [サイト内の全ページの一覧をセクションの階層構造に従って表示する](/p/xuwd7tn/)

しかし、上記のページで説明している方法でツリー表示すると、すべてのページが展開された状態で表示されてしまうため、サイドバーに表示するツリーとしてはちょっと情報量が多すぎます。
ここでは、もう少しコンパクトに表示されるように、現在のページの上位ノードだけを展開したツリーを表示するようにしてみます。

イメージとしては次のような感じで、表示中のページ（ここでは page1）の上位のセクションだけを展開したツリーを表示することを考えます。

```
- sec1/
- sec2/
    - sec2-1/
    - sec2-2/
        - page1 （表示中のページ）
        - page2
        - sec2-2-1/
        - sec2-2-2/
    - sec2-3/
- sec3/
- sec4/
```

このようなツリーをサイドバーなどに表示しておくことで、ユーザが今、サイト全体でどの位置の記事を読んでいるかを簡単に把握できるようになります（パンくずリストなども同様の効果がありますが、ツリー表示の方が、より全体を把握しやすいといえます）。


展開すべきノードを知る
----

自分自身のページが所属するセクションだけを展開したツリーを表示するには、テンプレートコード内で、__セクションの親子関係を（先祖まで含めて）把握する必要があります__。
そのために、`Page` オブジェクトの以下のようなメソッドを利用することができます（参考: [Hugo - Section Variables and Methods](https://gohugo.io/variables/page/#section-variables-and-methods)）。
Hugo 本家のマニュアルページでは、`.InSection` や `.IsAncestor`、`.IsDescendant` メソッドは、__Section 変数__ の Methods として記述されていますが、通常ページを含む `Page` オブジェクトのメソッドとして参照することができます。

__`$p1.InSection $p2`__
: `$p1` と `$p2` が同一のセクションに所属していれば `true`（`$p1 = $p2` の場合も `true`）。
  それ以外は `false`。<br>
  (Whether the given page is in the current section.)

__`$p1.IsAncestor $p2`__
: `$p1` が `$p2` の先祖ページかどうかを調べる。
  `$p1` セクションが `$p2` のカレントセクションよりも上位のセクションであれば `true`。
  `$p1` が通常ページの場合は常に `false`。
  `$p1 = $p2` の場合も `false`。<br>
  (Whether the current page is an ancestor of the given page.)

__`$p1.IsDescendant $p2`__
: `$p1` が `$p2` の子孫ページかどうかを調べる。`$p2.IsAncestor $1` とするのと同じ。<br>
  (Whether the current page is a descendant of the given page.)

{{% note %}}
- Hugo 0.100 で `.IsDescendant` と `.IsAncestor` の振る舞いが変更されました。
  - [IsDescendant/IsAncestor returns true for self · Issue #9925 · gohugoio/hugo](https://github.com/gohugoio/hugo/issues/9925)
  - [Notes release notes 0.100 · Issue #9934 · gohugoio/hugo](https://github.com/gohugoio/hugo/issues/9934)
{{% /note %}}

なかなか分かりにくいですね。このような場合は実際にテストしてみるのが一番です。
これらのメソッドがどういう振る舞いをするのかを明確にするため、次のようなディレクトリ構成（セクション構成）のダミーサイトでテストしてみます。

```
/_index.md
/page.md
    /sec1/_index.md
    /sec1/page.md
        /sec1/sec1-1/_index.md
        /sec1/sec1-1/page1.md
        /sec1/sec1-1/page2.md
            /sec1/sec1-1/sec1-1-1/_index.md
            /sec1/sec1-1/sec1-1-1/page.md
        /sec1/sec1-2/_index.md
        /sec1/sec1-2/page1.md
        /sec1/sec1-2/page2.md
```

次の表は、各ページの Page オブジェクト (`$p1`) の `.InSection`、`.IsAncestor`、`.IsDescendant` メソッドに、別ページの Page オブジェクト (`$p2`) を渡したときにどう判定されるかの一覧です（Hugo 0.110.0 で確認）。

<iframe style="width: 100%; height: 50vh;" src="demo.html"></iframe>
<div style="text-align: right; margin-top: 0;">
  {{< file src="demo.html" caption="→ 別ウィンドウで開く" >}}
</div>

`$p1` がセクションの場合と通常ページの場合で振る舞いが変わったりするので若干ややこしいですが、__これらのメソッドは、セクションページのみで使用する__ ようにすれば比較的わかりやすいコードを記述できると思います。
特に、サイドバーなどに表示するページツリーを生成するときは、__`.IsAncestor` が true になるセクションだけを、さらに深く辿っていく__ ようにすれば、カレントページの上位セクションのみを開いたページツリーを生成することができます。

- このテストに使用したコードはこちら: [https://github.com/maku77/p-7o7ymst/](https://github.com/maku77/p-7o7ymst/)


サイドバー用のページツリーを作成する
----

下記のパーシャルテンプレートは、現在表示中のページよりも上位のセクションを展開して、ページツリーを表示します。

{{< code lang="go-html-template" title="/layouts/partials/nav-tree.html" >}}
<h3>メニュー</h3>
{{- template "nav-tree-internal" (dict "section" .Site.Home "current" .) }}

{{- define "nav-tree-internal" }}
  {{- $section := .section }}{{/* 今回処理するセクション */}}
  {{- $current := .current }}{{/* 現在表示中のページ */}}

  <ul>
    {{- /* セクション直下のセクションページをループ表示 */}}
    {{- range $section.Sections }}
      <li><a href="{{ .RelPermalink }}">{{ .LinkTitle }}{{ if eq . $current }}★{{ end }}</a>
      {{- if or (.IsAncestor $current) (eq . $current) }}
        {{- /* 開いているページよりも上位のセクション（あるいは自分自身）であればさらに辿る */}}
        {{- template "nav-tree-internal" (dict "section" . "current" $current) }}
      {{- end }}
    {{- end }}

    {{- /* セクション直下の通常ページをループ表示 */}}
    {{- range $section.RegularPages }}
      <li><a href="{{ .RelPermalink }}">{{ .LinkTitle }}{{ if eq . $current }}★{{ end }}</a>
    {{- end }}
  </ul>
{{- end }}
{{< /code >}}

例えば、サイドバー用のパーシャルテンプレートなどから次のような感じで使用します。

{{< code lang="go-html-template" title="/layouts/partials/side-bar.html" >}}
{{ partial "nav-tree" . }}
{{< /code >}}

すると、次のような感じのツリーメニューが表示されます。
現在表示中のページには ★ マークが表示され、それよりも上位のセクションだけが展開されて表示されます。

```
メニュー

- sec1/
    - sec1-1/
        - sec1-1-1/
        - page1.html★
        - page2.html
    - sec1-2/
    - page.html
- page.html
```

ちなみに、ここでは最上位のホームページへのリンクは表示しないようにしています（無駄に階層が 1 つ深くなってしまうため）。
トップページのリンクを表示したいときは、下記のように個別に表示すればよいでしょう。

```go-html-template
<a href="{{ "/" | relURL }}">Home</a>
```

