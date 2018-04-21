---
title: "次のページ、前のページへのリンクを表示する"
date: "2018-04-22"
description: "ページ変数の .NextInSection や .PrevInSection を使用すると、同じセクション内の次のページ、前のページを参照することができます。"
---

下記のテンプレートコードは、前のページへのリンクと、次のページへのリンクを表示するシンプルな例です。

~~~
{{ "{{" }} with .PrevInSection }}
  <a href="{{ "{{" }} .Permalink }}">{{ "{{" }} .LinkTitle }}</a>
{{ "{{" }} end }}

{{ "{{" }} with .NextInSection }}
  <a href="{{ "{{" }} .Permalink }}">{{ "{{" }} .LinkTitle }}</a>
{{ "{{" }} end }}
~~~

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

#### layouts/partials/section-link.html

~~~
<div class="xSectionLink">
  {{ "{{" }} with .PrevInSection }}
    <div class="xSectionLink_Prev">
      <a href="{{ "{{" }} .Permalink }}">{{ "{{" }} .LinkTitle }}</a>
    </div>
  {{ "{{" }} end }}
  {{ "{{" }} with .NextInSection }}
    <div class="xSectionLink_Next">
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
.xSectionLink::after {
  /* float による回り込みの解除 */
  display: block;
  content: "";
  clear: both;
}
.xSectionLink_Prev {
  display: block;
  float: left;
}
.xSectionLink_Prev::before {
  content: "≪";
}
.xSectionLink_Next {
  display: block;
  float: right;
  text-align: right;

}
.xSectionLink_Next::after {
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
    {{ "{{" }} partial "section-link" . }}
    ...
  </main>
</body>
...
~~~


参考
----
- [Hugo の Page 変数一覧](https://gohugo.io/variables/page/)

