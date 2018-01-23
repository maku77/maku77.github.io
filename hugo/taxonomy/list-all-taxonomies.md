---
title: "サイト全体のターム一覧（タグ一覧）を表示する"
date: "2017-12-31"
description: "Web サイトのサイドバーなどに、タクソノミータームの一覧（つまりタグの一覧、タグクラウドなど）を表示しておくと、同じ系統の記事に素早くアクセスできて便利です。"
---

ここでは、タクソノミーとして Hugo デフォルトの tags と categories が定義されていることを前提とします。


特定のタクソノミーのターム一覧を表示（タグの一覧を表示する）
----

ここでは、tags タクソノミーに含まれているターム一覧を表示する例を示します（つまりタグの一覧です）。
多くのサイトでは、使用するタクソノミーの数は限られている（デフォルトの tags や categories で間に合うことが多い）ので、この使い方が一番多いかもしれません。

~~~
<h3>タグ一覧</h3>
<ul>
  {{ "{{" }}- range $termName, $entries := .Site.Taxonomies.tags }}
    <li><a href="{{ "{{" }} "/tags/" | relLangURL }}{{ "{{" }} $termName | urlize }}">{{ "{{" }} $termName }}</a> ({{ "{{" }} $entries.Count }})
  {{ "{{" }}- end }}
</ul>
~~~

#### 出力結果

~~~ html
<h3>タグ一覧</h3>
<ul>
  <li><a href="/tags/%E3%82%BF%E3%82%B01">タグ1</a> (5)
  <li><a href="/tags/%E3%82%BF%E3%82%B02">タグ2</a> (10)
  <li><a href="/tags/%E3%82%BF%E3%82%B03">タグ3</a> (15)
</ul>
~~~

各リンクの後ろには、そのタグを含むページの数を表示しています。

ページ数の多い順に列挙するには次のようにします。

~~~
<h3>タグ一覧</h3>
<ul>
  {{ "{{" }}- range .Site.Taxonomies.tags.ByCount }}
    <li><a href="{{ "{{" }} "/tags/" | relLangURL }}{{ "{{" }} .Term | urlize }}">{{ "{{" }} .Term }}</a> ({{ "{{" }} .Count }})
  {{ "{{" }}- end }}
</ul>
~~~

#### 出力結果

~~~ html
<h3>タグ一覧</h3>
<ul>
  <li><a href="/tags/%E3%82%BF%E3%82%B03">タグ3</a> (15)
  <li><a href="/tags/%E3%82%BF%E3%82%B02">タグ2</a> (10)
  <li><a href="/tags/%E3%82%BF%E3%82%B01">タグ1</a> (5)
</ul>
~~~


ターム一覧と、そのタームに所属するページを列挙する
----

タグ別にページの一覧まで表示してしまいたい場合は次のようにします。

~~~
<h3>タグ別ページ一覧</h3>
<ul>
  {{ "{{" }}- range $termName, $entries := .Site.Taxonomies.tags }}
    <li>{{ "{{" }} $termName }}
      <ul>
        {{ "{{" }}- range $entries.Pages }}
          <li><a href="{{ "{{" }} .RelPermalink }}">{{ "{{" }} .LinkTitle }}</a>
        {{ "{{" }}- end }}
      </ul>
    </li>
  {{ "{{" }}- end }}
</ul>
~~~

#### 出力結果

~~~ html
<h3>タグ別ページ一覧</h3>
<ul>
  <li>タグ1
    <ul>
      <li><a href="/page1/">記事ページ1</a>
      <li><a href="/page2/">記事ページ2</a>
    </ul>
  </li>
  <li>タグ2
    <ul>
      <li><a href="/page3/">記事ページ3</a>
      <li><a href="/page4/">記事ページ4</a>
    </ul>
  </li>
  <li>タグ3
    <ul>
      <li><a href="/page2/">記事ページ2</a>
      <li><a href="/page4/">記事ページ4</a>
    </ul>
  </li>
</ul>
~~~


全タクソノミーのターム一覧を表示
----

タクソノミーが tags や categories 以外にもたくさん定義されている場合、全てのタクソノミーをループ処理で出力してしまうのがよいかもしれません。
下記の例では、１つ目の range ループですべてのタクソノミーをループ処理し、２つ目の range ループで各タクソノミー内のタームをループ処理しています。

~~~
<h3>全タクソノミーのターム一覧を表示</h3>
<ul>
  {{ "{{" }}- range $taxonomyName, $taxonomy := .Site.Taxonomies }}
    {{ "{{" }}- $taxonomyUrl := print ("/" | relLangURL) ($taxonomyName | urlize) }}
    <li><a href="{{ "{{" }} $taxonomyUrl }}">{{ "{{" }} $taxonomyName }}</a>
      <ul>
        {{ "{{" }}- range $termName, $entries := $taxonomy }}
          <li><a href="{{ "{{" }} print $taxonomyUrl "/" ($termName | urlize) }}">{{ "{{" }} $termName }}</a> ({{ "{{" }} $entries.Count }})
        {{ "{{" }}- end }}
      </ul>
    </li>
  {{ "{{" }}- end }}
</ul>
~~~

#### 出力結果

~~~ html
<h3>全タクソノミーのターム一覧を表示</h3>
<ul>
  <li><a href="/categories">categories</a>
    <ul>
      <li><a href="/categories/%E3%82%AB%E3%83%86%E3%82%B4%E3%83%AA1">カテゴリ1</a> (7)
      <li><a href="/categories/%E3%82%AB%E3%83%86%E3%82%B4%E3%83%AA2">カテゴリ2</a> (3)
      <li><a href="/categories/%E3%82%AB%E3%83%86%E3%82%B4%E3%83%AA3">カテゴリ3</a> (9)
    </ul>
  </li>
  <li><a href="/tags">tags</a>
    <ul>
      <li><a href="/tags/%E3%82%BF%E3%82%B01">タグ1</a> (5)
      <li><a href="/tags/%E3%82%BF%E3%82%B02">タグ2</a> (10)
      <li><a href="/tags/%E3%82%BF%E3%82%B03">タグ3</a> (15)
    </ul>
  </li>
</ul>
~~~

タクソノミーごとにタームの一覧情報だけ出力すればよいのであれば、`$.Site.GetPage` を組み合わせて使用することで、もう少しシンプルに記述することができます。

~~~
{{ "{{" }}- range $taxonomyName, $taxonomy := .Site.Taxonomies }}
  <h2>{{ "{{" }} $taxonomyName }}</h2>
  <ul>
    {{ "{{" }}- range ($.Site.GetPage "taxonomyTerm" $taxonomyName).Pages }}
      <li><a href="{{ "{{" }} .Permalink }}">{{ "{{" }} .Title }}</a></li>
    {{ "{{" }}- end }}
  </ul>
{{ "{{" }}- end }}
~~~

さらに、タームが付加されている各ページへのリンクをすべて表示したいのであれば、次のような感じで記述すればよいでしょう。

~~~
<h1>全ページのタクソノミー別リンク</h1>

{{ "{{" }}- range $taxonomyName, $taxonomy := .Site.Taxonomies }}
  <h2>{{ "{{" }} $taxonomyName }}</h2>
  {{ "{{" }}- range $termName, $entries := $taxonomy }}
    <h3>{{ "{{" }}- $termName }}</h3>
    <ul>
      {{ "{{" }}- range $entries.Pages }}
        <li><a href="{{ "{{" }} .RelPermalink}}">{{ "{{" }} .LinkTitle }}</a>
      {{ "{{" }}- end }}
    </ul>
  {{ "{{" }}- end }}
{{ "{{" }}- end }}
~~~

出力結果は長くなってしまうので省略します。
大きなサイトではあまりにも巨大なリンク集になってしまうので使いものにならないかもしれませんが、小規模なサイトでは、タクソノミー別のサイトマップを作成したいときに使えるかもしれません。

