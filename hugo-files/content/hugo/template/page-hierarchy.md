---
title: "Hugo でサイト内の全ページの一覧をセクションの階層構造に従って表示する"
url: "p/xuwd7tn/"
date: "2018-01-09"
lastmod: "2020-01-14"
tags: ["Hugo"]
description: "現在の階層のページ一覧を出力するテンプレートを再帰的に呼び出すようにすると、サイト内の全てのセクションとページを、ツリー構造で表示することができます。すべてのページのリンクが出力されるので、サイトマップの出力に使用したり、ホームページ用のテンプレートに利用するとよいでしょう。"
aliases: /hugo/list/page-hierarchy.html
---

現在の階層のページ一覧を出力するテンプレートを再帰的に呼び出すようにすると、サイト内の全てのセクションとページを、ツリー構造で表示することができます。
すべてのページのリンクが出力されるので、サイトマップの出力に使用したり、ホームページ用のテンプレートに利用するとよいでしょう。

ツリー出力の基本
----

下記は、サイト内のすべてのページをツリー形式で表示するサンプルテンプレートです。
セクション変数の __`.Pages`__ を参照すると、そのセクションに含まれるサブセクションや通常ページを取得することができるので、それを `range` でループ処理しています。
部分テンプレート __`hierarchy`__ を定義し、これをホームページ (`.Site.Home`) から再帰的に呼び出すことで全ページのツリー構造を出力しています。

{{< code lang="go-html-template" title="layouts/index.html（抜粋）" >}}
<h2>全ページのリスト</h2>

{{- define "hierarchy" }}
  <ul>
    {{- range .Pages }}
      <li><a href="{{ .RelPermalink }}">{{ .Title }}</a>
      {{- if .IsSection }}{{ template "hierarchy" . }}{{ end }}
    {{- end }}
  </ul>
{{- end }}

{{ template "hierarchy" .Site.Home }}
{{< /code >}}

{{< code title="出力結果のイメージ（実際には各項目がリンクになります）" >}}
* タイトル A
* セクション 1
    * セクション 1-1
        * タイトル 1-1-A
        * タイトル 1-1-B
    * タイトル 1-A
    * タイトル 1-B
* タイトル B
* タイトル C
* セクション 2
    * セクション 2-1
        * タイトル 2-1-A
        * タイトル 2-1-B
{{< /code >}}

ここでは、`hierarchy` のパラメータとして `.Site.Home` を渡しているため、サイト全体のページツリーが出力されていますが、任意のセクションをルートにしてページツリーを出力することができます。
例えば、セクションテンプレートで次のように呼び出せば、現在のページが所属しているセクション (`.CurrentSection`) 以下のセクションおよびページをツリー表示できます。

```go-html-template
{{ template "hierarchy" .CurrentSection }}
```


セクションページを優先的に表示する
----

ページをリスト化するときに、セクションページを通常ページよりも先に表示したいときは、例えば次のようにします。
ここでは、セクションページと通常ページを `.Pages` でまとめてループ処理するのではなく、__`.Sections`__ でセクションページのみを先にループ処理してから、__`.RegularPages`__ で通常ページをループ処理するようにしています。


{{< code lang="go-html-template" title="layouts/index.html（抜粋）" >}}
{{- define "hierarchy" }}
  <ul>
    {{- /* セクションページをループ処理 */}}
    {{- range .Sections }}
      <li><a href="{{ .RelPermalink }}">📁{{ .Title }}</a>
      {{- template "hierarchy" . }}
    {{- end }}

    {{- /* 通常の記事ページをループ処理 */}}
    {{- range .RegularPages }}
      <li><a href="{{ .RelPermalink }}">{{ .Title }}</a>
    {{- end }}
  </ul>
{{- end }}

{{ template "hierarchy" .Site.Home }}
{{< /code >}}

{{< code title="出力結果のイメージ（セクションページが先に表示される）" >}}
* 📁セクション 1
    * 📁セクション 1-1
        * タイトル 1-1-A
        * タイトル 1-1-B
    * タイトル 1-A
    * タイトル 1-B
* 📁セクション 2
    * 📁セクション 2-1
        * タイトル 2-1-A
        * タイトル 2-1-B
* タイトル A
* タイトル B
* タイトル C
{{< /code >}}

ちなみに、セクション名の前にはフォルダアイコンを表示しています（Unicode の数値文字参照で __`&#128193;`__ と書いても OK）。
フォルダアイコンを表示する別の方法として、[Hugo の絵文字表示機能 (emojify)](/p/88e7tiz/) を使って、`{{ emojify ":open_file_folder:" }}` のように記述する方法もあります。


指定したセクションも含めて表示する
----

ツリー構造が一段深くなってしまうのでオススメはしませんが、指定したセクション（ルートセクション）も含めてツリー表示するには、例えば次のように実装します。

```go-html-template
{{- define "hierarchy" }}
  <ul>
    <li><a href="{{ .RelPermalink }}">📁{{ .Title }}</a>
    {{- range .Sections }}
      {{- template "hierarchy" . }}
    {{- end }}
    {{- range .RegularPages }}
      <ul>
        <li><a href="{{ .RelPermalink }}">{{ .Title }}</a>
      </ul>
    {{- end }}
  </ul>
{{- end }}

{{ template "hierarchy" .Site.Home }}
```

次のように、ホームページをルートとしてツリー表示されます。

{{< code title="出力結果のイメージ" >}}
* 📁ホーム
    * 📁セクション 1
        * 📁セクション 1-1
            * タイトル 1-1-A
            * タイトル 1-1-B
        * タイトル 1-A
        * タイトル 1-B
    * 📁セクション 2
        * 📁セクション 2-1
            * タイトル 2-1-A
            * タイトル 2-1-B
    * タイトル A
    * タイトル B
    * タイトル C
{{< /code >}}


2 階層目までを表示する
----

下記の例では、`hierarchy` ローカルテンプレートに __`level`__ というパラメータを追加し、そのパラメータで指定した階層までのページリストを表示するように変更したものです。
ここでは `level` に 2 を指定して、2 階層目までのページをツリー出力しています。

{{< code lang="go-html-template" title="layouts/index.html（抜粋）" >}}
<h2>2 階層目までのページのリスト</h2>

{{- define "hierarchy" }}
  {{- $section := .section }}
  {{- $level := .level }}
  <ul>
    {{- range $section.Pages }}
      <li><a href="{{ .RelPermalink }}">{{ if .IsSection }}📁{{ end }}{{ .Title }}
      {{- if and .IsSection (gt $level 1) }}
        {{- template "hierarchy" (dict "section" . "level" (sub $level 1)) }}
      {{- end }}
      </li>
    {{- end }}
  </ul>
{{- end }}

{{ template "hierarchy" (dict "section" .Site.Home "level" 2) }}
{{< /code >}}

{{< code title="出力結果のイメージ" >}}
* タイトル A
* 📁セクション 1
    * セクション 1-1
    * タイトル 1-A
    * タイトル 1-B
* タイトル B
* タイトル C
* 📁セクション 2
    * 📁セクション 2-1
{{< /code >}}

`hierarchy` テンプレートを再帰呼び出しするときに、__`sub`__ 関数で `level` パラメータの値を 1 つずつ減らすことで、再帰処理を途中で止めるようにしています。

