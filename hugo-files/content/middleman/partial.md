---
title: "Middlemanメモ: パーシャルでページを分割して管理する"
url: "p/9nr276n/"
date: "2015-12-07"
tags: ["middleman"]
aliases: /middleman/partial.html
---

パーシャル機能を使用することで、複数のレイアウトで共通する部分をファイルに分離して使い回すことができます（C/C++ 言語の include のようなものです）。
パーシャルファイルのファイル名には、プレフィックスとしてアンダースコア (`_`) を付ける必要があります。
また、パーシャルファイルは、ERB フォーマットで作成する必要があります（拡張子も `.erb` にする必要があります）。

ここでは、下記のように、`includes` ディレクトリ内に、`_header.erb` と `_footer.erb` を作成することにします。

{{< code lang="html" title="source/includes/_header.erb" >}}
<header>これはヘッダーです</header>
{{< /code >}}

{{< code lang="html" title="source/includes/_footer.erb" >}}
<footer>これはフッターです</footer>
{{< /code >}}

これらのパーシャルファイルをレイアウトから読み込むには、任意の場所に `<%= partial 'パーシャル名' %>` と記述します。
`パーシャル名` には、アンダースコアを含めないことに注意してください。

{{< code lang="html" title="source/layouts/layout.erb" >}}
<!DOCTYPE html>
<html lang="ja">
  ...
  <body>
    <%= partial 'includes/header' %>
    <%= yield %>
    <%= partial 'includes/footer' %>
  </body>
</html>
{{< /code >}}

上記は、最終的に下記のような感じで出力されます。

```html
<!DOCTYPE html>
<html lang="ja">
  ...
  <body>
    <header>これはヘッダーです</header>
    <p>Hello World</p>
    <footer>これはフッターです</footer>
  </body>
</html>
```
