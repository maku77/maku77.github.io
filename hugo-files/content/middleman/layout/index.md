---
title: "Middlemanメモ: レイアウト機能でページのレイアウトを統一する"
url: "p/j9fwthb/"
date: "2015-12-07"
tags: ["middleman"]
aliases: /middleman/layout.html
---

Middleman のレイアウト機能を使用すると、ページ全体の構成を統一することができます。
各ページで適用するレイアウトは自由に指定することができるので、コンテンツの種類ごとに適切なレイアウトを割り当てることができます。

{{< image src="img-001.svg" >}}

レイアウトファイルは、ERB フォーマットで記述し、`source/layouts` フォルダに格納しておきます。例えば、下記では `sample` というレイアウトを作成しています。

{{< code lang="html" title="source/layouts/sample.erb" >}}
<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="UTF-8">
    <title><%= current_page.data.title %></title>
  </head>
  <body>
    <h1><%= current_page.data.title %></h1>
    <%= yield %>
  </body>
</html>
{{< /code >}}

このレイアウトファイルを使用するページを作りたいときは、各ページの YAML Frontmatter（`---` で囲まれた部分）の `layout` パラメータで `sample` と指定します。

{{< code lang="erb" title="source/hello.html.erb" >}}
---
title: "Hello World"
layout: sample
---

<p>Middleman で Hello World してみたよ！</p>
{{< /code >}}

こうすると、上記ファイルのコンテンツが、レイアウトファイル内の `<%= yield %>` という部分に展開されます。
また、Frontmatter 内の任意のデータは、レイアウトファイル内から `<%= current_page.data.{パラメータ名} %>` という形で参照することができます。
結果的に上記のページは下記のように出力されます。

{{< code lang="html" title="build/hello.html" >}}
<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="UTF-8">
    <title>Hello World</title>
  </head>
  <body>
    <h1>Hello World</h1>
    <p>Middleman で Hello World してみたよ！</p>
  </body>
</html>
{{< /code >}}

レイアウトファイルの指定を行わなかった場合は、デフォルトで `source/layouts/layout.erb` というテンプレートファイルが使用されるようになっています。
