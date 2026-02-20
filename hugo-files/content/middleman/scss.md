---
title: "Middlemanメモ: Middleman で Sassy CSS (SCSS) を使用する"
url: "p/uroyec9/"
date: "2015-11-04"
tags: ["middleman"]
aliases: /middleman/scss.html
---

SCSS ファイルから CSS ファイルを生成する
====
Middleman ではデフォルトで Sassy CSS (SCSS) を使ってスタイルシートを記述できるようになっています。
例えば、下記のように `sample.scss` を作成します。

{{< code lang="scss" title="source/stylesheets/sample.scss" >}}
$font_size_normal: 18pt;

body {
  font-size: $font_size_normal;
}
{{< /code >}}

あとは、`middleman build` コマンドで Web サイトをビルドすれば、`sample.scss` ファイルから `sample.css` ファイルが生成されます（Middleman サーバを立ち上げて開発している最中はこの作業を行う必要はありません。ビルドが必要なのは、あくまでデプロイ前だけです）。

```console
$ bundle exec middleman build
   identical  build/stylesheets/all.css
   identical  build/stylesheets/normalize.css
      create  build/stylesheets/sample.css
              ...
```

レイアウトファイルに CSS のリンクを追加する
====
HTML ファイルの中で、`sample.css` を読み込む link 要素を追加するのを忘れないようにしましょう。
レイアウトファイルを ERB テンプレートとして作成する場合は、下記のようなヘルパーメソッドで link 要素を追加できます。

```erb
<%= stylesheet_link_tag "sample" %>
```

下記は、デフォルトで用意されているレイアウトファイル `layout.erb` を編集して、`sample.css` を追加で読み込むようにした例です。

{{< code lang="erb" title="source/layouts/layout.erb" >}}
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <title><%= current_page.data.title || "まくメモ" %></title>
    <%= stylesheet_link_tag "normalize", "all", "sample" %>
    <%= javascript_include_tag  "all" %>
  </head>
  <body class="<%= page_classes %>">
    <%= yield %>
  </body>
</html>
{{< /code >}}
