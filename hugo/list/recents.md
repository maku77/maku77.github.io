---
title: "最近更新された記事（新着記事）のリストを表示する"
date: "2018-05-29"
---

新しく作成した記事を列挙する
----

下記のテンプレートコードは、日付（フロントマターの `date` フィールドの値）の新しい順に、 5 件分のリンクを表示します。

#### 作成日 (date) 順に 5 件のリンクを表示する

~~~
<h2>新着記事</h2>
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

`.Date.Format` による日時フォーマットの指定方法は、[こちらを参考](/p/sy58beh)にしてください。

- 参考: [Lists of Content in Hugo｜Hugo](https://gohugo.io/templates/lists/)


更新日 (lastmod) の新しい順に列挙する
----

上記の例では、記事の作成日（`date` フィールド）を基準にして最新記事を列挙しましたが、更新日時（`lastmod` フィールド）を基準に最新記事を列挙することもできます。

次のようにコードを置き換えるだけで OK です。

* `.ByDate` → `.ByLastmod`
* `.Date` → `.Lastmod`

#### 最終更新日 (lastmod) 順に 5 件のリンクを表示する

~~~
<h2>最近更新された記事</h2>
<ul>
  {{ "{{" }} range first 5 .Site.RegularPages.ByLastmod.Reverse }}
    <li>
      <b><a href="{{ "{{" }}.RelPermalink}}">{{ "{{" }}.Title}}</a></b>
      <time>{{ "{{" }}.Lastmod.Format "2006-01-02"}}</time>
    </li>
  {{ "{{" }} end }}
</ul>
~~~

記事のフロントマターに `lastmod` フィールドが定義されていない場合は、`date` フィールドが使用されるようになっています。

このようなフォールバックの仕組みが用意されているので、最新記事を列挙するときは、`.ByDate` よりも `.ByLastmod` を使うようにしておくのがよさそうです。
ただし、typo の修正などで `lastmod` を更新してしまうと、それだけで記事が一番上に出てきてしまうので、ある程度内容のある更新をしたときのみ `lastmod` の日付を更新するなどの工夫が必要かもしれません。


### Git と連携して lastmod の日付を自動で設定する

Hugo の設定ファイルで、下記のように設定しておくと、`lastmod` フィールドが設定されていない記事に対して、Git のコミット日時を自動的に設定してくれるようになります（Page 変数の `.Lastmod` を参照して日時を表示してください）。

#### config.toml

~~~
enableGitInfo = true
~~~

Git を使用しているのであれば、この設定を入れておくとよいでしょう。

- 参考: [Git Info Variables｜Hugo](https://gohugo.io/variables/git/#lastmod)


新着記事のリストにドラフトページを含めない
----

`where` 関数を組み合わせて使用すると、新着記事の一覧から、ドラフトページ（ページのフロントマターに `draft: true` と書かれたもの）を除くことができます。

~~~
<h2>新着記事</h2>
<ul>
  {{ "{{" }} range first 5 (where .Site.RegularPages.ByLastmod.Reverse ".Draft" "!=" true }}
    <li>
      <b><a href="{{ "{{" }}.RelPermalink}}">{{ "{{" }}.Title}}</a></b>
      <time>{{ "{{" }}.Lastmod.Format "2006-01-02"}}</time>
    </li>
  {{ "{{" }} end }}
</ul>
~~~

<div class="note">
ドラフトページはデフォルトでは出力されないので、通常はこのようなフィルタリングは必要ありません。ドラフトページを出力するには、<code>hugo</code> コマンドを実行するときに、<code>-D</code> オプションを指定します。<code>hugo server</code> コマンドで Hugo サーバーのホスティングを行うときも同様です。
</div>

- 参考: [Hugo でドラフトページを作成する](../basic/draft.html)

独自のページ変数を用意して、新着記事への表示／非表示を制御するというのもありですね。
例えば、ページのフロントマターに `working: true` と設定されていたら新着記事に列挙しないようにするには次のようにします。

~~~
<h2>新着記事</h2>
<ul>
  {{ "{{" }} range first 5 (where .Site.RegularPages.ByLastmod.Reverse ".Params.working" "!=" true }}
    <li>
      <b><a href="{{ "{{" }}.RelPermalink}}">{{ "{{" }}.Title}}</a></b>
      <time>{{ "{{" }}.Lastmod.Format "2006-01-02"}}</time>
    </li>
  {{ "{{" }} end }}
</ul>
~~~

`where` 関数の条件式の `".Draft" "!=" true` というところが `".Params.working" "!=" true` に変わっただけです。

