---
title: "Hugo で独自のテーマを作成する"
url: "p/s4qbuez/"
date: "2017-09-10"
tags: ["Hugo"]
aliases: /hugo/basic/create-theme.html
---

独自テーマの新規作成
----

Hugo には、ネット上に公開されているテンプレートがたくさんありますが、思い通りのデザインをするときは、自分でテーマを作成することになります。
テーマを作成するときは、まずは下記のコマンドで雛形を出力します。

```console
$ hugo new theme <テーマ名>
```

例えば下記のように実行すると、__`themes/my-theme`__ ディレクトリが生成されます。

```console
$ hugo new theme my-theme
```

生成されるファイル群は下記のようになっていて、ディレクトリ構成はバッチシできていますが、ファイル内の記述はほとんど空っぽです。
このファイル群をベースにして、テンプレートを作成していきます。

{{< code title="独自テーマの雛形" >}}
themes/my-theme/
   ├── LICENSE.md （MIT ライセンス）
   ├── archetypes/
   │   └── default.md （ほぼ空っぽ）
   ├── layouts/
   │   ├── 404.html （空っぽ）
   │   ├── _default/
   │   │   ├── list.html （空っぽ）
   │   │   └── single.html （空っぽ）
   │   ├── index.html （空っぽ）
   │   └── partials/
   │       ├── footer.html （空っぽ）
   │       └── header.html （空っぽ）
   ├── static/
   │   ├── css/
   │   └── js/
   └── theme.toml （デフォルトの設定ファイル）
{{< /code >}}


トップページのレイアウト (layouts/index.html) を作成する
----

{{< image border="true" w="600" src="img-001.png" title="独自テーマを使ったはじめてのページ" >}}

テーマディレクトリ内の __`layouts/index.html`__ は、サイトのトップページ用のテンプレートファイルです。
もし、ひとつの HTML ファイルだけで構成されるサイト (SPA: Single Page Application) を作成するのであれば、このファイルだけを作成すればよいことになります。
初期状態では何も記述されていないので、まずは手始めに、サイト名だけを表示するように修正してみましょう。

{{< code lang="go-html-template" title="themes/my-theme/layouts/index.html" >}}
<h1>{{ .Site.Title }}</h1>
{{< /code >}}

上記のようにすると、サイト情報を保持する __`.Site`__ 変数の、__`Title`__ フィールドの値を出力することができます。
ここには、サイトの設定ファイル (`config.toml`) の `title` に設定した値が展開されます。

ここまで作成したら、このテーマを使ってサイトを表示してみましょう。
`hugo` コマンドで使用するテーマを指定するには、__`-t <テーマ名>`__ オプションを使用します。

```console
$ hugo server -t my-theme
```

上記のように Hugo サーバを立ち上げたら、`http://localhost:1313/` にアクセスすれば、表示を確認できます。


トップページに全ページのリストを表示する
----

{{< image border="true" w="600" src="img-002.png" >}}

トップページのテンプレート (`layouts/index.html`) 内で、__`.Data.Pages`__ 変数を参照すると、すべてのページの情報 ([Page 変数](https://gohugo.io/variables/page/)) を取得することができます。
この情報を `range` を使ってループ処理すれば、すべてのページへのリンクを出力することができます。

{{< code lang="go-html-template" title="themes/my-theme/layouts/index.html" >}}
<h1>{{ .Site.Title }}</h1>
<ul>
  {{ range .Data.Pages }}
    <li><a href="{{ .RelPermalink }}">{{ .Title }}</a> ({{ .Date.Format "2006-01-02" }})</li>
  {{ end }}
</ul>
{{< /code >}}


各ページ用のレイアウトを作成する (layouts/_default/single.html)
----

リンク先の各ページは、__`layouts/_default/single.html`__ テンプレートファイルを元に生成されるので、このファイルもあらかじめ作成しておく必要があります。
このテンプレートの中では、[Page 変数](https://gohugo.io/variables/page/) のフィールドを参照することができます。
例えば、__`.Title`__ でページタイトル、__`.Content`__ でページ本文を参照できます。

{{< code lang="go-html-template" title="themes/my-theme/layouts/_default/single.html" >}}
<h1>{{ .Title }}</h1>

{{ .Content }}
{{< /code >}}

各ページの内容は、`content/` ディレクトリ内に下記のような感じで作成しておきます。

{{< code lang="yaml" title="content/page1.md" >}}
---
title: "Page1 Title"
date: "2017-09-10"
---

Page1 Content
{{< /code >}}

上記のようにレイアウトファイルとコンテンツを作成しておけば、各ページの内容が下記のように表示されるはずです。

{{< image border="true" w="600" src="img-003.png" title="シングルページテンプレートによる出力" >}}

あとは、同様にして `layouts` ディレクトリ内のテンプレートコードを育てていくことで独自テーマが完成します。
Markdown ファイルから使える [ショートコード](/p/ttfyk5o/) なども独自テーマの付属品 (`layouts/shortcodes`) として提供できます。

