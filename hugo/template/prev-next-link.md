---
title: "次のページ、前のページへのリンクを表示する"
date: "2018-04-22"
description: "ページ変数の .NextInSection や .PrevInSection を使用すると、同じセクション内の次のページ、前のページを参照することができます。"
---

同じ階層にある「前のページ」「次のページ」へのリンクを表示する
----

下記のテンプレートコードは、前のページへのリンクと、次のページへのリンクを表示するシンプルな例です。

~~~
{{ "{{" }} with .PrevInSection }}
  <a href="{{ "{{" }} .Permalink }}">{{ "{{" }} .LinkTitle }}</a>
{{ "{{" }} end }}

{{ "{{" }} with .NextInSection }}
  <a href="{{ "{{" }} .Permalink }}">{{ "{{" }} .LinkTitle }}</a>
{{ "{{" }} end }}
~~~

Page 変数の `.PrevInSection` や `.NextInSection` には、同じセクション内にある通常ページ (regular page) の内、前のエントリと次のエントリにあたるページが格納されています。
これらの変数は、通常ページ (regular page) でしか有効でないことに注意してください（つまり、シングルページレイアウトで使用します）。
セクションページなどで参照すると、`nil` が返されます。

<div class="note">
リンクのタイトルをなるべく短く表示するため、<code>.Title</code> ではなく <code>.LinkTitle</code> を参照していることに注意してください。こうしておくと、ページのフロントマターで <code>linkTitle</code> プロパティが設定されている場合に、<code>title</code> プロパティよりも優先的に参照されるようになります。
</div>

体裁を整えるのであれば、前のページへのリンクは左寄せ、次のページへのリンクは右寄せで表示するとよいでしょう。
イメージとしては下記のような感じ。

~~~
≪前のページへ　　　　　　　　　次のページへ≫
~~~

さらに、コードはパーシャルテンプレートとして、使い回しがきくようにしておきましょう。
下記は完成版のパーシャルテンプレートです。

#### layouts/partials/prev-next.html

~~~
<div class="xPrevNextLink">
  {{ "{{" }} with .PrevInSection }}
    <div class="xPrevNextLink_prev">
      <a href="{{ "{{" }} .Permalink }}">{{ "{{" }} .LinkTitle }}</a>
    </div>
  {{ "{{" }} end }}
  {{ "{{" }} with .NextInSection }}
    <div class="xPrevNextLink_next">
      <a href="{{ "{{" }} .Permalink }}">{{ "{{" }} .LinkTitle }}</a>
    </div>
  {{ "{{" }} end }}
</div>
~~~

左寄せ、右寄せ用のスタイルも定義しておきます。
リンクタイトルの前後に表示される ≪ 記号や ≫ 記号も、CSS で表示するように設定します。

#### static/assets/css/main.css（例）

~~~ css
/* 前ページ、次ページへのリンク */
.xPrevNextLink::after {
  /* float による回り込みの解除 */
  display: block;
  content: "";
  clear: both;
}
.xPrevNextLink_prev {
  display: block;
  float: left;
}
.xPrevNextLink_prev::before {
  content: "≪";
}
.xPrevNextLink_next {
  display: block;
  float: right;
  text-align: right;

}
.xPrevNextLink_next::after {
  content: "≫";
}
~~~

パーシャルテンプレートを使用するには、任意のテンプレートファイル内で次のように参照します。

#### layouts/_default/single.html（例）

~~~
...
<body>
  <main>
    ...
    {{ "{{" }} partial "prev-next" . }}
    ...
  </main>
</body>
...
~~~


セクションページも含めて「前のページ」「次のページ」へのリンクを表示する
----

上記の `.PrevInSection` や `.NextInSection` は、**通常ページ (regular page) のみを対象**として、「前のページ」や「次のページ」へのリンクを実現するための機能でした。
例えば、content ディレクトリ内に次のような構成で記事を作成したとすると、

* section-0/_index.md
    * section-1/_index.md
    * section-2/_index.md
    * section-3/_index.md
    * <b>page-1.md</b>
    * <b>page-2.md</b>
    * <b>page-3.md</b>

<b>page-1</b>、<b>page-2</b>、<b>page-3</b> の 3 つの通常ページを前後に移動するためのリンクとして働きます。
セクションページは対象にならないため、<b>page-1</b> から <b>section-3</b> へのリンク（およびその逆のリンク）は張られません。

<b>section-1</b>、<b>section-2</b>、<b>section-3</b> などのセクションページも含めて、前後に移動できるようにするには、`.PrevInSection` や `.NextInSection` を使用せず、自力で前後のページへの参照を求める必要があります。
下記のサンプルコードは、セクションページを含めた「前のページ」「次のページ」へのリンクを出力する例です。

#### layouts/partials/prev-next-siblings.html

~~~
{{ "{{" }} if .Parent }}
  {{ "{{" }}/* まずは同じ階層のセクションとページから prev と next を決める */}}
  {{ "{{" }} $p := .Parent }}
  {{ "{{" }} $sections := $p.Sections }}
  {{ "{{" }} $pages := (cond $p.IsHome (where $.Site.RegularPages "Section" "") $p.Pages) }}
  {{ "{{" }} range (union $sections $pages) }}
    {{ "{{" }} if eq . $ }}
      {{ "{{" }} $.Scratch.Set "found" true }}
    {{ "{{" }} else }}
      {{ "{{" }} if $.Scratch.Get "found" }}
        {{ "{{" }} if not ($.Scratch.Get "next") }}
          {{ "{{" }}/* Set "next" entry as just after the current page. */}}
          {{ "{{" }} $.Scratch.Set "next" . }}
        {{ "{{" }} end }}
      {{ "{{" }} else }}
        {{ "{{" }}/* Update "prev" entry until the current page is found. */}}
        {{ "{{" }} $.Scratch.Set "prev" . }}
      {{ "{{" }} end }}
    {{ "{{" }} end }}
  {{ "{{" }} end }}

  {{ "{{" }}/* 見つかった prev と next のリンクを表示 */}}
  <div class="xPrevNextLink">
    {{ "{{" }}- with ($.Scratch.Get "prev") -}}
      <div class="xPrevNextLink_prev">
        <a href="{{ "{{" }} .Permalink }}">{{ "{{" }} .Title }}</a>
      </div>
    {{ "{{" }}- end -}}
    {{ "{{" }}- with ($.Scratch.Get "next") -}}
      <div class="xPrevNextLink_next">
        <a href="{{ "{{" }} .Permalink }}">{{ "{{" }} .Title }}</a>
      </div>
    {{ "{{" }}- end -}}
  </div>
{{ "{{" }} end }}
~~~

ちょっと長いですが、大まかには次のようなことを行っています。

1. 同じ階層にあるセクションのリスト (`$sections`) と、通常ページのリスト (`$pages`) を取得する。
2. それらをまとめてループ処理して、現在のページの前後のページを見つける（`"prev"` と `"next"`）。
3. 前後のページが見つかったら、それらをリンクとして出力。

<div class="note">
現時点の Hugo バージョン v0.47.1 は、<code>range</code> ループの <code>break</code> をサポートしていなかったため、前後のページを発見した後も最後までループ処理が継続してしまいます。
Hugo（が使用している Go テンプレート）が <code>break</code> に対応すれば、もう少し効率的な処理が可能になります。とはいえ、そのままでも十分速いですが。
</div>

上記の `prev-next-siblings` パーシャルテンプレートは、通常ページ (regular page) 専用の Page 変数である `.PrevInSection` や `.NextInSection` を使用せずに実装されているため、下記のようにセクションテンプレートなどからも使用することができます。
このセクションテンプレートは、[ベーステンプレートと組み合わせて使用する](base-template.html)ことを想定しています。

#### layouts/_default/section.html （使用例）

~~~
{{ "{{" }} define "article" }}
  <article class="xArticle">
    {{ "{{" }} partial "prev-next-siblings" . }}
    {{ "{{" }} .Render "inc-article" }}
  </article>
{{ "{{" }} end }}
~~~

Hugo がネイティブでこのようなリンク出力用の Page 変数を用意してくれれば楽なんですけどね(^^;


参考
----
- [Hugo の Page 変数一覧](https://gohugo.io/variables/page/)

