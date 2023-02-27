---
title: "Hugo で次のページ、前のページへのリンクを表示する"
url: "p/sc9t737/"
date: "2018-04-22"
tags: ["Hugo"]
description: "ページ変数の .NextInSection や .PrevInSection を使用すると、同じセクション内の次のページ、前のページを参照することができます。"
aliases: /hugo/template/prev-next-link.html
---

ページ変数の __`.NextInSection`__ や __`.PrevInSection`__ を使用すると、同じセクション内の次のページ、前のページを参照することができます。

同じ階層にある「前のページ」「次のページ」へのリンクを表示する
----

下記のテンプレートコードは、前のページへのリンクと、次のページへのリンクを表示するシンプルな例です。

{{< code lang="go-html-template" title="テンプレートコード（抜粋）" >}}
{{ with .PrevInSection }}
  <a href="{{ .RelPermalink }}">{{ .LinkTitle }}</a>
{{ end }}

{{ with .NextInSection }}
  <a href="{{ .RelPermalink }}">{{ .LinkTitle }}</a>
{{ end }}
{{< /code >}}

Page 変数の `.PrevInSection` や `.NextInSection` には、__同じセクション内にある通常ページ (regular page) の内、前のエントリと次のエントリにあたるページ__ が格納されています。
これらの変数は、通常ページ (regular page) でしか有効でないことに注意してください（つまり、シングルページレイアウトで使用します）。
セクションページなどで参照すると、`nil` が返されます。

{{% note title=".Title ではなく .LinkTitle を出力する" %}}
リンクのタイトルをなるべく短く表示するため、`.Title` ではなく __`.LinkTitle`__ を参照していることに注意してください。
こうしておくと、ページのフロントマターで __`linkTitle`__ プロパティが設定されている場合に、`title` プロパティよりも優先的に参照されるようになります。
{{% /note %}}

体裁を整えるのであれば、前のページへのリンクは左寄せ、次のページへのリンクは右寄せで表示するとよいでしょう。
表示イメージとしては次のような感じです。

<div style="margin: 0.5em; padding: 0.5em; background: #eee; font-weight: bolder; display: flex; justify-content: space-between;">
  <span>≪前のページへ</span>
  <span>次のページへ≫</span>
</div>

さらに、コードは `prev-next` パーシャルテンプレートとして、使い回しがきくようにしておきましょう。
下記は完成版のパーシャルテンプレートです。

{{< code lang="go-html-template" title="layouts/partials/prev-next.html" >}}
<div class="xPrevNextLink">
  {{ with .PrevInSection }}
    <div class="xPrevNextLink_prev">
      <a href="{{ .RelPermalink }}">{{ .LinkTitle }}</a>
    </div>
  {{ end }}
  {{ with .NextInSection }}
    <div class="xPrevNextLink_next">
      <a href="{{ .RelPermalink }}">{{ .LinkTitle }}</a>
    </div>
  {{ end }}
</div>
{{< /code >}}

左寄せ、右寄せ用のスタイルも定義しておきます。
リンクタイトルの前後に表示される ≪ 記号や ≫ 記号も、CSS で表示するように設定します。

{{< code lang="css" title="static/assets/css/main.css（例）" >}}
/* 前ページ、次ページへのリンク */
.xPrevNextLink {
  display: flex;
  justify-content: space-between;
}

.xPrevNextLink_prev::before {
  content: "≪";
}

.xPrevNextLink_next::after {
  content: "≫";
}
{{< /code >}}

`prev-next` パーシャルテンプレートを使用するには、任意のテンプレートファイル内で次のように呼び出します。

{{< code lang="go-html-template" title="layouts/_default/single.html（抜粋）" >}}
<body>
  <main>
    ...
    {{ partial "prev-next" . }}
    ...
  </main>
</body>
{{< /code >}}


セクションページも含めて「前のページ」「次のページ」へのリンクを表示する
----

上記の `.PrevInSection` や `.NextInSection` は、__通常ページ (regular page) のみを対象__ として、「前のページ」や「次のページ」へのリンクを実現するための機能でした。
例えば、content ディレクトリ内に次のような構成で記事を作成したとすると、

- section-0/_index.md
  - section-1/_index.md
  - section-2/_index.md
  - section-3/_index.md
  - __page-1.md__
  - __page-2.md__
  - __page-3.md__

__page-1__、__page-2__、__page-3__ の 3 つの通常ページを前後に移動するためのリンクとして働きます。
セクションページは対象にならないため、__page-1__ から __section-3__ へのリンク（およびその逆のリンク）は張られません。

__section-1__、__section-2__、__section-3__ などのセクションページも含めて、前後に移動できるようにするには、`.PrevInSection` や `.NextInSection` を使用せず、自力で前後のページへの参照を求める必要があります。
下記のサンプルコードは、セクションページを含めた「前のページ」「次のページ」へのリンクを出力する例です。

{{< code lang="go-html-template" title="layouts/partials/prev-next-siblings.html" >}}
{{ if .Parent }}
  {{- /* まずは同じ階層のセクションとページから prev と next を決める */}}
  {{- range .Parent.Pages }}
    {{ if eq . $ }}
      {{ $.Scratch.Set "found" true }}
    {{ else }}
      {{ if $.Scratch.Get "found" }}
        {{ if not ($.Scratch.Get "next") }}
          {{/* Set "next" entry as just after the current page. */}}
          {{ $.Scratch.Set "next" . }}
        {{ end }}
      {{ else }}
        {{/* Update "prev" entry until the current page is found. */}}
        {{ $.Scratch.Set "prev" . }}
      {{ end }}
    {{ end }}
  {{ end }}

  {{/* 見つかった prev と next のリンクを表示 */}}
  <div class="xPrevNextLink">
    {{- with ($.Scratch.Get "prev") -}}
      <div class="xPrevNextLink_prev">
        <a href="{{ .RelPermalink }}">{{ .Title }}</a>
      </div>
    {{- end -}}
    {{- with ($.Scratch.Get "next") -}}
      <div class="xPrevNextLink_next">
        <a href="{{ .RelPermalink }}">{{ .Title }}</a>
      </div>
    {{- end -}}
  </div>
{{ end }}
{{</ code >}}

ちょっと長いですが、大まかには次のようなことを行っています。

1. 同一階層にある通常ページおよびセクションページをループ処理（前から順番に見ていく）
2. カレントページにたどり着いた時点で、その前後のページが判明する（`"prev"` と `"next"`）
3. `"prev"` と `"next"` のページへのリンクを出力

{{% note %}}
現時点の Hugo バージョン v0.47.1 は、`range` ループの `break` をサポートしていなかったため、前後のページを発見した後も最後までループ処理が継続してしまいます。
Hugo（が使用している Go テンプレート）が `break` に対応すれば、もう少し効率的な処理が可能になります。
とはいえ、そのままでも十分速いですが。
{{% /note %}}

上記の `prev-next-siblings` パーシャルテンプレートは、通常ページ (regular page) 専用の Page 変数である `.PrevInSection` や `.NextInSection` を使用せずに実装されているため、下記のようにセクションテンプレートなどからも使用することができます。
このセクションテンプレートは、[ベーステンプレートと組み合わせて使用する](/p/bbxj5pa/) ことを想定しています。

{{< code lang="go-html-template" title="layouts/_default/section.html（使用例）" >}}
{{ define "article" }}
  <article class="xArticle">
    {{ partial "prev-next-siblings" . }}
    {{ .Render "inc-article" }}
  </article>
{{ end }}
{{< /code >}}

Hugo がネイティブでこのようなリンク出力用の `Page` 変数を用意してくれれば楽なんですけどね(^^;

