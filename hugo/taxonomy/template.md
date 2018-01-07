---
title: "タクソノミー関連のテンプレートを定義する"
date: "2017-12-31"
description: "あるタクソノミーに所属するタームの一覧（例えばタグの一覧）などを表示するページは Hugo によって自動的に生成されます。ただし、そのためのレイアウト（テンプレート）ファイルはあらかじめ作成しておく必要があります。"
---

タクソノミー関連の２種類のテンプレート
----

タクソノミー関連のテンプレートには、大きく分けて下記の２種類があります。

タクソノミー・ターム・テンプレート (Taxonomy Terms Template)
: あるタクソノミーに含まれているタームの一覧を表示するためのレイアウトです。`https://example.com/tags/` といった URL にアクセスしたときに使用されます。

タクソノミー・リスト・テンプレート (Taxonomy List Template)
: あるタームが付加されているページの一覧を表示するためのレイアウトです。`https://example.com/tags/mytag/` といった URL にアクセスしたときに使用されます。

以下、それぞれのテンプレートをどのように作成するかを説明していきます。


タクソノミー・ターム・テンプレートを作成する
----

タクソノミー・ターム・テンプレートは、あるタクソノミーに所属するタームの一覧を表示するときに使用されるテンプレートです。
例えば、`tags` というタクソノミーが定義されている時、このテンプレートによって `https://example.com/tags/` という URL でアクセスできるタグ一覧ページが生成されます。

下記のテンプレートファイルのうち、最初に見つかったファイルがタクソノミー・ターム・テンプレートとして使用されます。

1. /layouts/taxonomy/**＜単数系のタクソノミー名＞**.terms.html
2. /layouts/_default/terms.html
3. /themes/**＜テーマ名＞**/layouts/taxonomy/**＜単数系のタクソノミー名＞**.terms.html
4. /themes/**＜テーマ名＞**/layouts/_default/terms.html

全てのタクソノミーで共通のレイアウトを使用するのであれば、まずは **`layouts/_default/terms.html`** を作成しておけばよいでしょう。

下記のサンプルテンプレートは、対象のタクソノミーに所属するタームの一覧を表示します。

#### layouts/_default/terms.html

~~~ html
<h1>{{ "{{" }} .Title }}</h1>
<ul>
  {{ "{{" }}- $plural := .Data.Plural }}
  {{ "{{" }}- range $index, $term := .Data.Terms.Alphabetical }}
    <li><a href="{{ "{{" }} $.Site.LanguagePrefix }}/{{ "{{" }} $plural }}/{{ "{{" }} $term.Name | urlize }}">{{ "{{" }} $term.Name }}</a> ({{ "{{" }} $term.Count }})
  {{ "{{" }}- end }}
</ul>
~~~

ここでは、簡略化のために `<head>` 要素などの記述を省略していますが、本番環境では、正しく全体の HTML コードを出力するようにしてください。

上記のようなタクソノミー・ターム・テンプレートを配置した後で、`http://example.com/tags` にアクセスすると、以下のような HTML がレンダリングされます（日本語のタグ名を含む URL は正しくエスケープされます）。

~~~ html
<h1>Tags</h1>
<ul>
  <li><a href="/tags/%E3%82%BF%E3%82%B01">タグ1</a> (10)
  <li><a href="/tags/%E3%82%BF%E3%82%B02">タグ2</a> (7)
  <li><a href="/tags/%E3%82%BF%E3%82%B02">タグ3</a> (12)
</ul>
~~~

ここでは、tags タクソノミーに、「タグ1」「タグ2」「タグ3」というタームが存在していると仮定しています。
各リンクの後ろに表示された数字は、そのタグが付加されているページの数が表示されています。

<div class="note">
<code>.Data.Terms.Alphabetical</code> という部分を <code>.Data.Terms.ByCount</code> に置き換えれば、ページ数の多い順にタグの一覧を表示することができます。
</div>

上記のリンクをクリックすると、そのターム（ここではタグ）が設定された記事の一覧ページにジャンプします。
そこで使われるテンプレートが、次に説明するタクソノミー・リスト・テンプレートです。


タクソノミー・リスト・テンプレートを作成する
----

タクソノミー・リスト・テンプレートは、あるタクソノミーの、あるタームが付加されているページの一覧を表示するときに使用されるテンプレートです。
例えば、tags タクソノミーに、mytag というタームが設定されているページの一覧は、`https://example.com/tags/mytag/` という URL で表示できるのですが、そのページを生成するときに使用されます。

下記のテンプレートファイルのうち、最初に見つかったファイルがタクソノミー・リスト・テンプレートとして使用されます。

1. /layouts/taxonomy/**＜単数系のタクソノミー名＞**.html
2. /layouts/_default/taxonomy.html
3. /layouts/_default/list.html
4. /themes/**＜テーマ名＞**/layouts/taxonomy/**＜単数系のタクソノミー名＞**.html
5. /themes/**＜テーマ名＞**/layouts/_default/taxonomy.html
6. /themes/**＜テーマ名＞**/layouts/_default/list.html

汎用リストテンプレートである `layouts/_default/list.html` は、タクソノミー・リスト・テンプレートとしても使用できるのですが、汎用リストテンプレートはホームページやセクションページのレンダリングにも使用されるものです。
セクションの記事一覧ページと、ターム別の記事一覧ページのレイアウトを異なるものにしたい場合は、`layouts/_default/taxonomy.html` などの専用ファイルとして作成してください。

ここでは、汎用リストテンプレートとして、`layouts/_default/list.html` を作成しましょう。

#### layouts/_default/list.html

~~~ html
<h1>{{ "{{" }} .Title }}</h1>

{{ "{{" }}- .Content }}

<ul>
  {{ "{{" }}- range .Data.Pages }}
    <li><a href="{{ "{{" }} .RelPermalink}}">{{ "{{" }} .Title }}</a>
  {{ "{{" }}- end }}
</ul>
~~~

このテンプレートにより、例えば下記のような記事ページ一覧用の HTML ファイルが生成されます。

~~~ html
<h1>Mytag</h1>

<ul>
  <li><a href="/post/page1/">記事ページ１</a>
  <li><a href="/post/page2/">記事ページ２</a>
  <li><a href="/post/page3/">記事ページ３</a>
</ul>
~~~

このテンプレートも、説明用に簡略化したものです。
本番環境では `head` 要素などをちゃんと出力してくださいね。

