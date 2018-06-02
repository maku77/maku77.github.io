---
title: "記事ページに付けられたターム一覧（タグ一覧）を表示する"
date: "2018-01-01"
description: "シングルページテンプレートにおいて、その記事ページに付けられたタグの一覧などを表示するようにすれば、関連する記事（同じタグの付けられた記事）を探しやすくなります。"
---

タクソノミーターム（タグやカテゴリ）は、各ページの front matter 部分で定義するので、ページ変数 (`.Params`) 経由でその情報を取得することができます。
次のパーシャルテンプレートは、記事ページに付けられたタグの情報を取得し、リスト形式で出力します。

#### layouts/partials/tags-in-page.html

~~~
<ul class="tags">
  {{ "{{" }}- range .Params.tags -}}
    <li><a href="{{ "{{" }} "/tags/" | relLangURL }}{{ "{{" }} . | urlize }}">{{ "{{" }} . }}</a>
  {{ "{{" }}- end -}}
</ul>
~~~

タグが設定されていない場合に、先頭の ul 要素ごと出力しないようにするには、ちょっとだけ変えて下記のようにします。

~~~
{{ "{{" }} with .Params.tags }}
  <ul class="tags">
    {{ "{{" }}- range . -}}
      <li><a href="{{ "{{" }} "/tags/" | relLangURL }}{{ "{{" }} . | urlize }}">{{ "{{" }} . }}</a>
    {{ "{{" }}- end -}}
  </ul>
{{ "{{" }} end }}
~~~

上記のパーシャルテンプレートを使用するときは、シングルページテンプレート内で次のように記述します。

#### layouts/_default/single.html

~~~
{{ "{{" }} partial "tags-in-page" . }}
~~~

デフォルトでは、`li` 要素は単純な箇条書きのリスト形式で表示されてしまいます。
次のようなスタイルを定義しておくと、それっぽく表示されてよい感じになります。

#### CSS

~~~ css
.tags {
  padding: 0;
}
.tags li {
  display: inline-block;  /* 横に並べる */
  list-style-type: none;
  margin-right: 0.5em;
}
.tags li a {
  display: block;  /* 選択範囲を広げる */
  text-decoration: none;
  padding: 0.5em 1em;
  background: lightgray;
  color: #333;
}
.tags li a:hover {
  background: #555;
  color: white;
}
~~~

実際に表示すると次のような感じになります。

<iframe class="xHtmlDemo" src="terms-in-page-demo.html"></iframe>
<a target="_blank" href="terms-in-page-demo.html">デモページを開く</a>

