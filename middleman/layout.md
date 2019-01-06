---
title: "レイアウト機能でページのレイアウトを統一する"
date: "2015-12-07"
---

Middleman のレイアウト機能を使用すると、ページ全体の構成を統一することができます。
各ページで適用するレイアウトは自由に指定することができるので、コンテンツの種類ごとに適切なレイアウトを割りあてることができます。

![layout.svg](layout.svg)

レイアウトファイルは、ERB フォーマットで記述し、`source/layouts` フォルダに格納しておきます。例えば、下記では `sample` というレイアウトを作成しています。

#### source/layouts/sample.erb

```html
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
```

このレイアウトファイルを使用するページを作りたいときは、各ページの YAML Frontmatter（`---` で囲まれた部分）の `layout` パラメータで `sample` と指定します。

#### source/hello.html.erb

```erb
---
title: "Hello World"
layout: sample
---

<p>Middleman で Hello World してみたよ！</p>
```

こうすると、上記ファイルのコンテンツが、レイアウトファイル内の `<%= yield %>` という部分に展開されます。
また、Frontmatter 内の任意のデータは、レイアウトファイル内から `<%= current_page.data.{パラメータ名} %>` という形で参照することができます。
結果的に上記のページは下記のように出力されます。

#### build/hello.html

```html
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
```

レイアウトファイルの指定を行わなかった場合は、デフォルトで `source/layouts/layout.erb` というテンプレートファイルが使用されるようになっています。

