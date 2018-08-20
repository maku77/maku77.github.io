---
title: "Sass (SASS/SCSS) スタイルシートを使用する"
date: "2018-08-16"
description: "Hugo 0.46 でついに Sass (SASS/SCSS) がサポートされました。これで、スタイルシートを効率的に記述できるようになります。"
---

Sass とは
---

Sass (SASS/SCSS) は CSS のプリプロセッサで、これを利用すると、柔軟な変数の仕組みや、入れ子構造を用いたスタイルシート記述が可能になります。

- [Sass: Syntactically Awesome Style Sheets](https://sass-lang.com/)

Sass の記述フォーマットとしては、古い SASS 記法（拡張子 `.sass`）と、新しい SCSS 記法（拡張子 `.scss`）がありますが、現在は CSS のフォーマットと互換性のある SCSS 記法が主流になっています。
最新の CSS には、変数の仕組みなどが用意されてきていますが、ブラウザの対応状況もまだまだなので、しばらくは Sass が使われ続けるでしょう。


Hugo で Sass を使用する
----

Hugo で Sass を使用する方法は、下記の本家ドキュメントに記述されています。

- [Hugo Pipes ＞ SASS/SCSS](https://gohugo.io/hugo-pipes/scss-sass/)
- [Hugo Pipes ＞ Hugo Pipes Introduction](https://gohugo.io/hugo-pipes/introduction/)

大まかな手順は次の通りです。

1. ルートの `assets` ディレクトリ以下に `.scss` ファイルを配置する。
2. テンプレート内でパイプコマンドを利用して `.scss` ファイルを `.css` に変換し、変換後の `.css` ファイル名を link 要素で指定する。

### 1. assets ディレクトリに .scss ファイルを配置する

本家のドキュメントでは、`resources.Get` 関数を使って `.scss` ファイルを読み込む方法が紹介されています。
デフォルトの設定では、この関数はプロジェクトのルートにある `assets` ディレクトリ以下のファイルを検索するようになっています。

ここでは、`assets` ディレクトリ以下に `sass` ディレクトリを作成し、その中に `.scss` ファイルを格納していくことにしましょう。
`main.scss` ファイルの中から `_color.scss` ファイルの内容をインポートするようにしています。

#### assets/sass/main.scss

~~~ scss
@import "color.scss";

body {
  background: $BASE_COLOR;
}
~~~

#### assets/sass/_color.scss

~~~ scss
$BASE_COLOR: #f3f3f3;
~~~


### 2. テンプレート内で `.scss` ファイルを `.css` に変換し、link 要素で指定する

これも本家のドキュメントに記載されている通りです。
head 要素を出力するテンプレート（ここでは baseof テンプレート）の中に下記のように記述します。

#### layouts/_default/baseof.html の抜粋

~~~
{{ "{{" }} $style := resources.Get "sass/main.scss" | toCSS | minify | fingerprint {{ "}}" }}
<link rel="stylesheet" href="{{ "{{" }} $style.Permalink {{ "}}" }}">
~~~

`resources.Get` で読み込んだ SCSS をパイプでいくつかの関数に渡していますが、最低限必要なのは `toCSS` による変換です。
その後ろに続く `minify` は CSS のコード圧縮、`fingerprint` は SCSS の変更が反映されないことを防ぐ（ファイル名に毎回違うサフィックスを付加してキャッシュが使用されるのを防ぐ）ための処理です。

これで、Hugo で Sass (SASS/SCSS) を使えるようになります。
あとは、`main.scss` ファイルの内容を更新すれば、自動的に CSS に変換されて反映されるようになります。

