---
title: "Hugo でドラフトページを作成する"
date: "2017-08-24"
---

ドラフト記事
----

Hugo のドラフト機能は、未完成の記事ファイルを一時的にほかの記事と同じ場所に保存しておくための機能です。
ページの Front matter 部分に、`draft: true` という記述があると、そのページはドラフトとして扱われます。

#### YAML 形式の場合

~~~ markdown
---
draft: true
---

本文
~~~

#### TOML 形式の場合

~~~ markdown
+++
draft = true
+++

本文
~~~

ドラフトとして作成された記事は、デフォルトでは HTML ファイルとして出力されません。
ドラフト記事も出力するようにするには、`hugo` コマンドに `-D (--buildDrafts)` オプションを指定します。

#### ドラフト記事を出力

~~~
$ hugo -D        （サイトを生成する場合）
$ hugo server -D （サーバーを立ち上げる場合）
~~~


public ディレクトリに出力されたドラフト記事に注意
----

`hugo` コマンドは、デフォルトで `public` ディレクトリに HTML ファイルなどを出力しますが、このとき、`public` ディレクトリ内に既に存在するドラフト記事を削除することはありません。

~~~
$ hugo -D  （ここでドラフト記事も含めて生成される）
$ hugo      (次にドラフトモード OFF で生成しても、既存のドラフト記事が削除されない）
~~~

Web サイトを公開するときは、ドラフト記事が間違ってアップロードされないように気を付けてください。
記事のアップロード前は、`public` ディレクトリを削除してから `hugo` コマンドで再生成すると安全です。

#### Linux / Mac の場合

~~~
$ rm -Rf public && hugo
~~~

#### Windows の場合

~~~
C:\> rmdir /s /q public & hugo
~~~


ドラフト記事の一覧を表示する
----

### コマンドラインから

コマンドラインで `hugo list draft` と実行すると、contents ディレクトリの中の記事のうち、ドラフトとしてマークされている（フロントマターに `draft: true` と記述されている）ファイルの一覧を確認することができます。

~~~
$ hugo list draft
draft.md
diaries/temp.md
books/work-shift.md
~~~

- 参考: [hugo list drafts｜Hugo](https://gohugo.io/commands/hugo_list_drafts/)

### テンプレートから

テンプレートファイル内で、ドラフト記事のリンクを列挙するには以下のようにします。

#### layouts/index.html の抜粋

~~~
<h2>ドラフト記事の一覧</h2>
<ul>
{{ "{{" }} range (where .Site.Pages ".Draft" true) }}
  <li><a href="{{ "{{" }} .RelPermalink }}">{{ "{{" }} .Title }}</a>
{{ "{{" }} end }}
</ul>
~~~

<div class="note">
<code>where .Site.Page ".Draft" true</code> という部分は、「サイト内のすべてのページ (<code>.Site.Pages</code>) の中から、ページ変数の .Draft が true である (<code>".Draft" true</code>) ものを列挙する」という命令です。
</div>


テンプレートの中でドラフト記事かどうかを判別する
----

[テンプレートファイル](../layout/template-types.html)の中で、今レンダリングしている記事がドラフトである（フロントマターに `draft: true` と記述されている） (`draft: true`) かどうかを調べるには、[Page 変数](https://gohugo.io/variables/page/)の `.Draft` を参照します。

#### layouts/_default/single.html の抜粋

~~~ html
<h1>{{ "{{" }} .Title }}</h1>
{{ "{{" }} if .Draft }}
  <b>これはドラフト記事です。</b>
{{ "{{" }} end }}
~~~

