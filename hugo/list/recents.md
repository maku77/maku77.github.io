---
title: "最近更新された記事のリストを表示する"
date: "2018-05-29"
---

新しく作成した記事を列挙する
----

下記のテンプレートコードは、日付（フロントマターの `date` フィールドの値）の新しい順に、 5 件分のリンクを表示します。

#### 作成日 (date) 順に 5 件のリンクを表示する

~~~
<h2>最新の記事</h2>
<ul>
  {{ "{{" }} range first 5 .Site.RegularPages.ByDate.Reverse }}
    <li>
      <b><a href="{{ "{{" }}.RelPermalink}}">{{ "{{" }}.Title}}</a></b>
      <time>{{ "{{" }}.Date.Format "2006-01-02"}}</time>
    </li>
  {{ "{{" }} end }}
</ul>
~~~

`.Site.RegularPages.ByDate.Reverse` という部分で、サイト内の全ページを日付の逆順に列挙することを示しています（インデックス系のページも列挙したい場合は `.RegularPages` の部分を `.Pages` に変更してください）。
`first 5` は、その中の最初の 5 件を取り出すという命令です。

`.Date.Format` による日時フォーマットの指定方法は、[こちらを参考](../go/time.html)にしてください。

- 参考: [Lists of Content in Hugo｜Hugo](https://gohugo.io/templates/lists/)


更新日 (lastmod) の新しい順に列挙する
----

上記の例では、記事の作成日（`date` フィールド）を基準にして最新記事を列挙しましたが、更新日時（`lastmod` フィールド）を基準に最新記事を列挙することもできます。

`ByDate` というところを `ByLastmod` に入れ替えるだけで OK です。

#### 最終更新日 (lastmod) 順に 5 件のリンクを表示する

~~~
<h2>最近更新された記事</h2>
<ul>
  {{ "{{" }} range first 5 .Site.RegularPages.ByLastmod.Reverse }}
    <li>
      <b><a href="{{ "{{" }}.RelPermalink}}">{{ "{{" }}.Title}}</a></b>
      <time>{{ "{{" }}.Date.Format "2006-01-02"}}</time>
    </li>
  {{ "{{" }} end }}
</ul>
~~~

記事のフロントマターに `lastmod` フィールドが定義されていない場合は、`date` フィールドが使用されるようになっています。

このようなフォールバックの仕組みが用意されているので、最新記事を列挙するときは、`.ByDate` よりも `.ByLastmod` を使うようにしておくのがよさそうです。
ただし、typo の修正などで `lastmod` を更新してしまうと、それだけで記事が一番上に出てきてしまうので、ある程度内容のある更新をしたときのみ `lastmod` の日付を更新するなどの工夫が必要かもしれません。


### Git と連携して lastmod の日付を自動で設定する

Hugo の設定ファイルで、下記のように設定しておくと、`lastmod` フィールドが設定されていない記事に対して、Git のコミット日時を自動的に設定してくれるようになります。

#### config.toml

~~~
enableGitInfo = true
~~~

Git を使用しているのであれば、この設定を入れておくとよいでしょう。

- 参考: [Git Info Variables｜Hugo](https://gohugo.io/variables/git/#lastmod)

