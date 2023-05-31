---
title: "Hugo でサーチエンジン用に sitemap.xml や robots.txt ファイルを配置する"
url: "p/qr4eox7/"
date: "2020-04-26"
lastmod: "2023-05-31"
tags: ["Hugo"]
description: "サーチエンジンに対して、インデックス登録させたいページを伝えるには、Web サイトに sitemap.xml や robots.txt というファイルを配置します。Google の検索結果に影響するため、正しく理解して配置しましょう。"
changes:
  - 2023-04-17: 設定ファイル名を config.toml から hugo.toml に変更
  - 2023-05-31: フロントマターでそのページをサイトマップに列挙しない設定方法
aliases: /hugo/advanced/sitemap.html
---

サーチエンジンに対して、インデックス登録させたいページを伝えるには、Web サイトに __`sitemap.xml`__ や __`robots.txt`__ というファイルを配置します。
Google の検索結果に影響するため、正しく理解して配置しましょう。


sitemap.xml と robots.txt の概要
----

Web サイトのルートに `sitemap.xml` や `robots.txt` を置くと、下記のようなヒント情報を Google などの検索エンジンに伝えることができます。

- __`sitemap.xml`__ ... サイト内のページ一覧情報を記述します。検索エンジンは、サイト全体をクロールしなくても、この情報でページのインデックス情報を作成できるようになります。ページごとに更新日時 (YYYY-MM-DD) や優先度 (0.0〜1.0) を指定できます。
- __`robots.txt`__ ... クロールしなくてもよいディレクトリやファイルを指定します。上記の `sitemap.xml` の URL を指定することもできます。

もちろん、これらのファイルを用意したところで、どこまで反映されるかは検索エンジン側の実装によりますが、SEO 対策として少しでもヒントとなる情報を与えておくのが望ましいでしょう。


sitemap.xml を作成する
----

`sitemap.xml` ファイルには、Google などの検索エンジンにインデックス登録してもらいたいページの一覧を記述します。

### sitemap.xml のデフォルト出力

Hugo はデフォルトで Web サイトのルートに、[Sitemap protocol](https://www.sitemaps.org/protocol.html) に基づいた `sitemap.xml` ファイルを出力するようになっています。
下記はデフォルト設定で出力される `sitemap.xml` の例です。

{{< code lang="xml" title="sitemap.xml" >}}
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9" xmlns:xhtml="http://www.w3.org/1999/xhtml">
  <url>
    <loc>https://example.com/page1/</loc>
    <lastmod>2020-04-26T00:00:00+00:00</lastmod>
  </url>
  <url>
    <loc>https://example.com/page2/</loc>
    <lastmod>2020-04-25T00:00:00+00:00</lastmod>
  </url>
</urlset>
{{< /code >}}

この出力は、Hugo にデフォルトで組み込まれている `sitemap.xml` テンプレートによって生成されています。
デフォルトのテンプレート定義は下記を見ると分かります。

- [Hugo’s sitemap.xml](https://gohugo.io/templates/sitemap-template/#hugos-sitemapxml)
- [gohugoio/hugo/tpl/tplimpl/embedded/templates/_default/sitemap.xml (GitHub)](https://github.com/gohugoio/hugo/blob/master/tpl/tplimpl/embedded/templates/_default/sitemap.xml)

このままで問題なければ、特に何も設定する必要はありませんが、出力内容をカスタマイズしたいときは、次のように `sitemap.xml` 用のテンプレートファイルを生成する必要があります。
Hugo はデフォルトですべてのページを `sitemap.xml` にリスト化しようとするので、Google などの検索結果に載せたくないページがある場合は、`sitemap.xml` のカスタマイズが必要です。


### sitemap.xml のカスタマイズ {#customize}

下記のいずれかのテンプレートファイルを作成することで、Web サイトのルートディレクトリに出力される `sitemap.xml` の内容をカスタマイズできます。

- __`layouts/sitemap.xml`__
- __`layouts/_default/sitemap.xml`__

ここでは、下記のようにカスタマイズしてみます。

- タクソノミーリスト（あるタグのページ一覧）のページを出力しない（`/tags/*` や `/categories/*` などをリストに載せない）
- 特定の URL 以下のページを出力しない (URL が `/search/` で始まるページをリストに載せない）
- Markdown ファイルのフロントマターで `noindex: true` と記述されていたら出力しない
- セクションページの優先度を上げる（デフォルトで `priority=0.5` のところを `priority=1.0` とする。ただしフロントマターでの指定があればそちらを優先）

{{< code lang="go-html-template" title="layouts/sitemap.xml" >}}
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9" xmlns:xhtml="http://www.w3.org/1999/xhtml">
  {{- range .Data.Pages }}
  {{- if ne .Kind "term" }}
  {{- if not (hasPrefix .RelPermalink "/search/") }}
  {{- if ne .Params.noindex true }}
  <url>
    <loc>{{ .Permalink }}</loc>
    {{- if not .Lastmod.IsZero }}
    <lastmod>{{ .Lastmod.Format "2006-01-02" }}</lastmod>
    {{- end }}
    {{- with .Sitemap.ChangeFreq }}
    <changefreq>{{ . }}</changefreq>
    {{- end }}
    {{- if ge .Sitemap.Priority 0.0 }}
    <priority>{{ .Sitemap.Priority }}</priority>
    {{- else if eq .Kind "section" }}
    <priority>1.0</priority>
    {{- end }}
  </url>
  {{- end }}
  {{- end }}
  {{- end }}
  {{- end }}
</urlset>
{{< /code >}}

ここでは、ページ変数の `.Kind` の値を見ることで、セクションやタクソノミーリスト (`/tags/*`) のページであるかどうかを判断しています。
このあたりのページ変数がどのような値を取るかに関しては、下記の記事を参考にしてください。

- 参考: [各種ページにおいて .Kind や .IsPage、.IsNode の値がどうなるかの一覧 ｜ まくまく Hugo/Go ノート](/p/co8p6n4/)


各ページのフロントマターでサイトマップ出力を制御する
----

ページごとにサイトマップ出力を制御したい場合は、フロントマターのプロパティを設定します。

### サイトマップに列挙されないようにする

サイトマップには列挙したくない特殊なページがあるかもしれません。
簡単な方法として、Hugo 組み込みの __`_build`__ プロパティで、__`list: never`__ を指定してしまう方法があります（参考: [Hugo でページリスト（記事一覧）に列挙されないページを作る](/p/4ziyhxe/)）。
このフラグを設定すると、Hugo の `.Pages` や `.RegularPages` などのコレクションに含まれなくなるので、結果的に `sitemap.xml` にも列挙されなくなります（サイトマップテンプレート内で `.Data.Pages` をループ処理していたことに着目してください）。

{{< code lang="yaml" title="content/page.md（Hugo の _build プロパティで出力抑制）" hl_lines="4 5" >}}
---
title: "ページタイトル"
date: "2023-05-31"
_build: { list: never }
---
{{< /code >}}

別の方法として、独自の __`noindex`__ プロパティを定義してしまう方法もあります。
`.Pages` コレクションなどには含めたいけれど、サイトマップには列挙したくないといった事情があれば、このような独自プロパティを使って制御します。
前述の[カスタマイズされた sitemap.xml 用テンプレート](#customize)では、この `noindex` プロパティがセットされていたら、そのページの URL を列挙しないようにしています。

{{< code lang="yaml" title="content/page.md（独自の noindex プロパティで出力抑制）" hl_lines="4" >}}
---
title: "ページタイトル"
date: "2023-05-31"
noindex: true
---
{{< /code >}}

ちなみに、独自の `noindex` プロパティを導入するのであれば、それと連動させて HTML の `meta` 要素を出力するようにしておくのがよいでしょう（`noindex` 用の `meta` 要素については[後述](#noindex-meta)）。

{{< code lang="go-html-template" title="layouts/_default/baseof.html（ベーステンプレートの抜粋）" >}}
<head>
  <meta charset="UTF-8">
  {{- if .Params.noindex }}
  <meta name="robots" content="noindex" />
  {{- end }}
{{< /code >}}


### サイトマップに出力される情報を調整する (changefreq, priority)

Hugo 組み込みの __`sitemap`__ プロパティを設定することで、`sitemap.xml` に出力する __`<changefreq>`__ や __`<priority>`__ 要素の値を制御することができます。
これらの値は、前述のテンプレート内で __`.Sitemap.ChangeFreq`__、__`.Sitemap.Priority`__ といった変数で参照しています。

{{< code lang="yaml" title="content/page.md（ページのプライオリティなどを調整）" hl_lines="4-6" >}}
---
title: "ページタイトル"
date: "2020-04-26"
sitemap:
  changefreq: monthly
  priority: 0.8
---
{{< /code >}}


sitemap.xml の出力先パスを変更する
----

`sitemap.xml` はデフォルトで Web サイトのルートに出力されますが、Hugo の設定ファイルで出力パスを変更することができます。

{{< code lang="toml" title="hugo.toml" >}}
[sitemap]
  filename = "assets/my-sitemap.xml"
{{< /code >}}


robots.txt によるクロールの抑制
----

Web サイトのルートに __`robots.txt`__ を配置しておくと、検索エンジンに対して、そのサイト内でクロールされたくないディレクトリやファイルを知らせることができます（参考: [robotstxt.org](https://www.robotstxt.org/)）。
また、上記で作成した `sitemap.xml` の URL を知らせることもできます。

### robots.txt を出力するための設定

静的なファイルとして `robots.txt` を作成するのであれば、単純に Hugo プロジェクト内に __`/static/robots.txt`__ というファイルを配置しておけば OK です（`robots.txt` が出力ディレクトリのルートにコピーされます）。
テンプレート機能を使って、動的に `robots.txt` を生成したいのであれば、設定ファイルで下記のように指定しておく必要があります。

{{< code lang="toml" title="hugo.toml" >}}
enableRobotsTXT = true
{{< /code >}}

この設定により、下記のテンプレートファイルを使って `robots.txt` を生成してくれるようになります。

- __`/layouts/robots.txt`__
- __`/themes/<THEME>/layouts/robots.txt`__

下記のようなテンプレートを作成すると、すべての種類のクローラーに対して `sitemap.xml` の URL を知らせるための `robots.txt` を生成できます。

{{< code lang="go-html-template" title="/layouts/robots.txt" >}}
User-agent: *
Sitemap: {{ .Site.BaseURL }}sitemap.xml
{{< /code >}}

`sitemap.xml` のパスは、FQDN（完全修飾 URL）で指定しなければいけないので、上記のようにテンプレート内で `.Site.BaseURL` 変数を使って完全な URL を構築しています。

### robots.txt の Disallow ディレクティブはあまり使わない

よくある `robots.txt` の例として、__`Disallow`__ ディレクティブを使ったクロールの防止方法が示されていたりしますが、実際に `Disallow` ディレクティブが必要になるケースはそれほど多くありません（それよりは後述の `meta` 要素を使う方が目的と合っていることが多いです）。
特に、JavaScript や CSS、画像ファイルなどは、検索エンジンがページ内容を正しく処理するために必要なので、`Disallow` 指定してクロール対象から外してはいけません。


Google の検索結果から除外する（meta 要素の noindex 設定） {#noindex-meta}
----

### robots.txt の Disallow ではなく meta 要素の noindex を使う

特定のページを Google の検索結果に表示されないようにするには、HTML の `head` 要素内に下記のような `meta` 要素を記述するのが正解みたいです。

```html
<meta name="robots" content="noindex" />
```

`robots.txt` に下記のように指定すれば、`/search/` 以下をクロールしないように知らせることにはなるのですが、すでにインデックスされてしまったページは検索結果に表示され続けることになってしまいます。

{{< code title="robots.txt" >}}
User-agent: *
Disallow: /search/
{{< /code >}}

また、クロールを防いだとしても、他のページからリンクされている場合はインデックスされてしまう可能性があります。

よって、検索エンジンに特定のページがインデックスされることを防ぐには、

1. __`meta` 要素で `noindex` を指定する__
2. `robots.txt` の `Disallow` は指定しない

とするのがよいようです。

- 参考: [noindex を使用して検索インデックス登録をブロックする - Search Console ヘルプ](https://support.google.com/webmasters/answer/93710)
  > 重要: noindex ディレクティブが有効に機能するようにするために、robots.txt ファイルでページをブロックしないでください。ページが robots.txt ファイルでブロックされると、クローラは noindex ディレクティブを認識しません。そのため、たとえば他のページからリンクされていると、ページは検索結果に引き続き表示される可能性があります。

### （コラム）Google Search Console の sitemap.xml に関するエラー

`sitemap.xml` にリストアップされているにも関わらず、HTML ファイルの __`<meta name="robots" content="noindex">`__ でインデックス登録を抑制しているようなページがあると、[Google Search Console](https://search.google.com/search-console?hl=ja) にエラーが表示されることがあります。

{{< image w="800" src="img-001.png" title="送信された URL に noindex タグが追加されています" >}}

これは、「`sitemap.xml` でインデックス登録しろと指定されているけど、実際にページの内容を見ると `meta` 要素で `noindex` 指定されてるのでインデックス登録しないよ。それでいいの？」という意味の警告です。
インデックス登録されたくないページは、`sitemap.xml` にもリストアップされないように整合性を取りましょう。

