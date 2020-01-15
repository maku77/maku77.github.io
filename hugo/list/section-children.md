---
title: "同一セクション内のページ／セクションの一覧を表示する"
date: "2017-12-22"
lastmod: "2020-01-14"
---

そのセクション内のページ一覧を取得する (.Pages、.RegularPages)
----

リストテンプレートやセクションテンプレートの中で、**`.Pages`** を参照すると、そのセクション（やタクソノミー）直下の記事ページ (regular page) とセクションページ (list page) の一覧を `Page` オブジェクトの配列として取得することができます。

#### layouts/_default/section.html

~~~ html
<h3>セクションに含まれる記事ページ（あるいはセクションページ）の一覧</h3>
<ul>
  {{ "{{" }} range .Pages }}
    <li><a href="{{ "{{" }} .RelPermalink }}">{{ "{{" }} .Title }}</a>
  {{ "{{" }} end }}
</ul>
~~~

記事ページ (regular page) のみを列挙したい場合は、`.Pages` の部分を **`.RegularPages`** に変更してください。
セクションページ (list page) のみを列挙したい場合は、`.Pages` の部分を **`Sections`** に変更してください。

- `.Pages` -- セクションページと記事ページの一覧
- `.RegularPages` -- 記事ページの一覧
- `.Sections` -- セクションページの一覧

上記のようなテンプレートは、タクソノミーテンプレート（タグの一覧ページ）でも同様に使用することができます。

ただし、`.Pages` や `.RegularPages` は、リスト系のページから参照することが想定されているため、シングルページテンプレート内で参照すると、サイズ 0 の配列が返されることに注意してください。
そのため、基本的には上記のコードはリスト系のテンプレートでのみ使用することができます。

シングルページテンプレートからも、同一セクション内のページの一覧を取得したい場合は、まず `.CurrentSection` でカレントセクションを示す `Page` オブジェクトを取得するとよいでしょう（下記参照)。


同一セクション内のページ一覧を取得する (.CurrentSection.Pages)
----

下記のテンプレートコードは、現在の記事ページ（あるいはセクションページ）が所属するセクションの直下のページ（記事ページおよびセクションページ）の一覧を表示します。

例えば、ホームページを含むトップレベルの階層に置いた記事で実行された場合は、第一階層にあるセクションおよび記事ページの一覧が表示されます。
何らかのセクション内の記事で実行された場合は、その記事が所属するセクション内のサブセクションおよび記事ページの一覧が表示されます。

#### layouts/_default/single.html など

~~~ html
<h3>同じセクション内のページ一覧（セクションページを含む）</h3>
{{ "{{" }} with .CurrentSection }}
  <ul>
    {{ "{{" }} range .Pages }}
      <li><a href="{{ "{{" }} .RelPermalink }}">{{ "{{" }} .Title }}</a>
    {{ "{{" }} end }}
  </ul>
{{ "{{" }} end }}
~~~

`.Pages` 変数はリスト系ページ（セクションページなど）の `Page` オブジェクトにしか要素が格納されない（記事ページでは空配列）になってしまうので、まずは自分自身の記事ページが所属するセクションに対応する `Page` オブジェクトを、`.CurrentSection` で取得してから処理を行うようにしています。

先に説明したのと同様、記事ページのみを列挙したい場合は `.Pages` の代わりに `.RegularPages` を、セクションページのみを列挙したい場合は `.Pages` の代わりに `.Sections` を参照してください。

このコードは、シングルページテンプレートからも、リストテンプレートからも使用できます。


参考
----

- [Hugo | Page Variables](https://gohugo.io/variables/page/)
> a collection of associated pages. This value will be nil for regular content pages. .Pages is an alias for .Data.Pages.

- [hugo/page.go at master · gohugoio/hugo · GitHub](https://github.com/gohugoio/hugo/blob/master/hugolib/page.go#L101)
> Since Hugo 0.18 we got rid of the Node type. So now all pages are pages (regular pages, home page, sections etc.).
> Sections etc. will have child pages. These were earlier placed in .Data.Pages, but can now be more intuitively also be fetched directly from .Pages.
> This collection will be nil for regular pages.


コラム: ホームページでの .Pages 変数の振る舞いについて
----

**（これは Hugo v0.58.0 より前のバージョンの記事です）**

Hugo v0.58.0 で、ホームページにおける `.Pages` 変数、`.RegularPages` 変数の振る舞いが、他のセクションページと同じになりました（そのセクション直下のページだけ返す）。
なので、**v0.58.0 以降を使用する場合は、下記の対応は必要ありません**。

- 参考: [Hugo v0.58.0 Release Note](https://github.com/gohugoio/hugo/releases/tag/v0.58.0)

> home.Pages now behaves like all the other sections, see #6240. If you want to list all the regular pages, use .Site.RegularPages.

### ホームページテンプレートとセクションテンプレートでの .Pages の振る舞いの違い

ホームページテンプレート (`layouts/index.html`) 内で `.Pages` を参照すると、**サイト内のすべてのページ**の `Page` 配列が返されるのですが、セクションテンプレート (`layouts/_default/section.html`) 内で `.Pages` を参照すると、その**セクション直下にあるページ**のみの `Page` 配列が返されるという違いがあるようです。

テンプレート内で `.Pages` 変数を参照するときは、そのテンプレートがホームページテンプレートと、セクションテンプレートのどちらのコンテキストで使用されるかを意識して扱い方を変える必要があります。
特に、`layouts/_default/list.html` のような、ホームページテンプレートとしても、セクションテンプレートとしても使用されるファイルを作成する場合には注意してください。

次の節では、この点を考慮して、カレントセクション直下に配置されたページのみのリストを取得する方法を示します。


### 同一セクションの直下の記事ページのリストを表示する

下記のテンプレートコードは、現在のページが所属するセクションの直下に配置された記事ページのリストを表示します。


#### layouts/partials/subpages.html

~~~ html
{{ "{{" }}- $sec := .CurrentSection }}
{{ "{{" }}- if $sec }}
  {{ "{{" }}- if $sec.IsHome }}
    {{ "{{" }} .Scratch.Set "pages" (where $.Site.RegularPages "Section" "") }}
  {{ "{{" }}- else }}
    {{ "{{" }} .Scratch.Set "pages" $sec.Pages }}
  {{ "{{" }}- end }}
{{ "{{" }}- end }}

<h3>同じセクション内の記事ページ一覧</h3>
<ul>
  {{ "{{" }}- range (.Scratch.Get "pages") }}
    <li><a href="{{ "{{" }} .RelPermalink }}">{{ "{{" }} .Title }}</a>
  {{ "{{" }}- end }}
</ul>
~~~

例えば、"mysection" セクションに所属する記事ページのテンプレートで上記のコードが実行されると、同じ "mysection" セクションに所属する記事ページのリストが表示されます。
また、ホームページを含む、トップレベル（`content` ディレクトリ直下）に配置したページ内で実行した場合は、トップレベルに配置した記事ページのリストが表示されます。
つまり、上記のパーシャルテンプレートは、下記のような様々なテンプレートファイル内から同様に使用することができます。

- ホームページテンプレート (`layouts/index.html`)
- セクションテンプレート (`layouts/_default/section.html`)
- 汎用リストテンプレート (`layouts/_default/list.html`)

~~~
{{ "{{" }} partial "subpages" . }}
~~~

テンプレートコードが若干複雑になっているのは、`.Pages` の振る舞いがコンテキストによって変わってくることが影響しています。
セクションページ内では、単純に `.Pages` を参照すれば、セクション直下の記事ページ一覧を取得できるのですが、ホームページで `.Pages` を参照すると、サイト内のすべてのページが含まれてしまいます。
そこで、現在のページがトップレベルの階層の記事である場合は、ちょっと特殊な方法、セクションを持たない記事の一覧を取得する (`where $.Site.RegularPages "Section" ""`) といった回りくどいことを行っています。

ちなみに、前半部分は次のようにセクション名 (`.Section`) の有無で分岐するように書けそうですが、このような記述はタクソノミーテンプレートのコンテキストではエラーになってしまいます。
なぜなら、`https://example.com/tags/tag1/` のようなタグページにアクセスした場合に、セクション名 (`.Section`) に `tags` という文字列が格納されるのにもかかわらず、`.CurrentSection` の値は `nil` になってしまうからです（`.CurrentSection` の nil dereference エラーが発生する）。

~~~
{{ "{{" }}/* カレントセクション直下の記事ページ一覧を "pages" スクラッチに格納 */}}
{{ "{{" }}- if .Section }}
  {{ "{{" }} .Scratch.Set "pages" .CurrentSection.Pages }}
{{ "{{" }}- else }}
  {{ "{{" }} .Scratch.Set "pages" (where $.Site.RegularPages "Section" "") }}
{{ "{{" }}- end }}
~~~

このような記述をするのであれば、全体を `{{ "{{" }} if nq .Kind "taxonomy" }} 〜 {{ "{{" }} end }}` のように囲んで、タクソノミーテンプレートのコンテキストでは実行されないようにしておくべきでしょう。
そうすると、結局のところ、最初の例とあまりコード量は変わらなくなります。

あと、`$pages` 変数のようなものを作成せずに、わざわざ `.Scratch.Set` を使ってスクラッチ領域にページリストを保存しているのは、Go テンプレートの構文で、変数の条件代入を行ううまい方法が用意されていないからです。
このあたりの話は、[こちらの Hugo のサイトで議論されています](https://github.com/golang/go/issues/10608)。
スクラッチ領域 (`.Scratch`) を使用するのが気持ち悪いという場合は、下記のように、`define` による部分テンプレートを定義してしまえば、変数を用意しなくてすみますね。

~~~ html
{{ "{{" }}- define "showPageList" }}
  <h3>同じセクション内の記事ページ一覧</h3>
  <ul>
    {{ "{{" }}- range . }}
      <li><a href="{{ "{{" }} .RelPermalink }}">{{ "{{" }} .Title }}</a>
    {{ "{{" }}- end }}
  </ul>
{{ "{{" }}- end }}

{{ "{{" }}- with $sec := .CurrentSection }}
  {{ "{{" }}- if $sec.IsHome }}
    {{ "{{" }} template "showPageList" (where $.Site.RegularPages "Section" "") }}
  {{ "{{" }}- else }}
    {{ "{{" }} template "showPageList" $sec.Pages }}
  {{ "{{" }}- end }}
{{ "{{" }}- end }}
~~~


### 条件代入は cond 関数でスッキリかける！？（2017-12-30 追記）

Hugo v0.27 で、３項演算子のようなことが実現できる `cond` 関数が追加されました（参考: [Ternary operator-like function · Issue #3860 · gohugoio/hugo · GitHub](https://github.com/gohugoio/hugo/issues/3860)）。
まだ公式ドキュエントには `cond` 関数の記載がないようですが、下記のように使用できます。

~~~
{{ "{{" }} $変数 := (cond (条件式) 真の場合の値 偽の場合の値) }}
~~~

これで回りくどい `.Scratch` イディオムを使用しなくて済む！やったー！(^o^)/
と思って、下記のように書いてみました。

~~~ html
<h3>同じセクション内の記事ページ一覧</h3>
<ul>
  {{ "{{" }}- $pages := (cond (eq .Section "") (where $.Site.RegularPages "Section" "") .CurrentSection.RegularPages) }}
  {{ "{{" }}- range $pages }}
    <li><a href="{{ "{{" }} .RelPermalink }}">{{ "{{" }} .Title }}</a>
  {{ "{{" }}- end }}
</ul>
~~~

がしかし！
`cond` 関数を使用して上記のように記述すると、条件式が真と判定される場合も、後ろの `.CurrentSection.Pages` の部分が評価されてしまうようです（値が実際に使われなくても実行される）。
`.CurrentSection` は `nil` になるケースがあるので、そのようなコンテキストで実行された場合にエラーになってしまいます。
Go の並列実行の都合なのだと思いますが、惜しい！

