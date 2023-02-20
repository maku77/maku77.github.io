---
title: "Hugo でドラフトページを作成する"
url: "p/m2oatdw/"
date: "2017-08-24"
tags: ["Hugo"]
aliases: /hugo/basic/draft.html
---

ドラフト記事
----

Hugo のドラフト機能は、未完成の記事ファイルを一時的にほかの記事と同じ場所に保存しておくための機能です。
ページの Front matter 部分に、`draft: true` という記述があると、そのページはドラフトとして扱われます。

{{< code lang="yaml" title="YAML 形式の場合" >}}
---
draft: true
---

本文
{{< /code >}}

{{< code lang="md" title="TOML 形式の場合" >}}
+++
draft = true
+++

本文
{{< /code >}}

ドラフトとして作成された記事は、デフォルトでは HTML ファイルとして出力されません。
ドラフト記事も出力するようにするには、`hugo` コマンドに __`-D`__ (__`--buildDrafts`__) オプションを指定します。

{{< code lang="console" title="ドラフト記事を出力" >}}
$ hugo -D         # サイトを生成する場合
$ hugo server -D  # サーバーを立ち上げる場合
{{< /code >}}


public ディレクトリに出力されたドラフト記事に注意
----

`hugo` コマンドは、デフォルトで `public` ディレクトリに HTML ファイルなどを出力しますが、このとき、`public` ディレクトリ内に既に存在するドラフト記事を削除することはありません。

```console
$ hugo -D  # ここでドラフト記事も含めて生成される
$ hugo     # 次にドラフトモード OFF で生成しても、既存のドラフト記事が削除されない
```

Web サイトを公開するときは、ドラフト記事が間違ってアップロードされないように気を付けてください。
記事のアップロード前は、`public` ディレクトリを削除してから `hugo` コマンドで再生成すると安全です。

{{< code lang="console" title="Linux / macOS の場合" >}}
$ rm -Rf public && hugo
{{< /code >}}

{{< code title="Windows の場合" >}}
C:\> rmdir /s /q public & hugo
{{< /code >}}


ドラフト記事の一覧を表示する
----

### コマンドラインから

コマンドラインで __`hugo list drafts`__ と実行すると、`contents` ディレクトリ内の記事のうち、ドラフトとしてマークされている（フロントマターに `draft: true` と記述されている）ファイルの一覧を確認することができます。

```console
$ hugo list drafts
draft.md
diaries/temp.md
books/work-shift.md
```

- 参考: [hugo list drafts｜Hugo](https://gohugo.io/commands/hugo_list_drafts/)

### テンプレートから

テンプレートファイル内で、ドラフト記事のリンクを列挙するには以下のようにします。

{{< code lang="go-html-template" title="layouts/index.html（抜粋）" >}}
<h2>ドラフト記事の一覧</h2>
<ul>
  {{ range (where .Site.Pages ".Draft" true) }}
    <li><a href="{{ .RelPermalink }}">{{ .Title }}</a></li>
  {{ end }}
</ul>
{{< /code >}}

{{% note %}}
`where .Site.Page ".Draft" true` という部分で、ドラフト記事を抽出するための検索条件を指定しています。
サイト内のすべてのページ (`.Site.Pages`) から、ページ変数の `.Draft` の値が `true` であるもの (`".Draft" true`) を列挙する、という指定になっています。
{{% /note %}}


テンプレートの中でドラフト記事かどうかを判別する
----

[テンプレートファイル](/p/zg4n7q9/) の中で、今レンダリングしている記事がドラフトである（フロントマターに `draft: true` と記述されている）かどうかを調べるには、[Page 変数](https://gohugo.io/variables/page/) の __`.Draft`__ を参照します。

{{< code lang="go-html-template" title="layouts/_default/single.html（抜粋）" >}}
<h1>{{ .Title }}</h1>
{{ if .Draft }}
  <b>注: この記事はまだドラフトです。</b>
{{ end }}
{{< /code >}}

