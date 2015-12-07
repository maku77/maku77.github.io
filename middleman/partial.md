---
title: パーシャルでページを分割して管理する
created: 2015-12-07
---

パーシャル機能を使用することで、複数のレイアウトで共通する部分をファイルに分離して使い回すことができます（C/C++ 言語の include のようなものです）。
パーシャルファイルのファイル名には、プレフィックスとしてアンダースコア (`_`) を付ける必要があります。
また、パーシャルファイルは、ERB フォーマットで作成する必要があります（拡張子も `.erb` にする必要があります）。

ここでは、下記のように、`includes` ディレクトリ内に、`_header.erb` と `_footer.erb` を作成することにします。

#### source/includes/_header.erb

```html
<header>これはヘッダーだよ</header>
```

#### source/includes/_footer.erb

```html
<footer>これはフッターだよ</footer>
```

これらのパーシャルファイルをレイアウトから読み込むには、任意の場所に `<%= partial 'パーシャル名' %>` と記述します。
`パーシャル名` には、アンダースコアを含めないことに注意してください。

#### source/layouts/layout.erb

```html
<!DOCTYPE html>
<html lang="ja">
  ...
  <body>
    <%= partial 'includes/_header' %>
    <%= yield %>
    <%= partial 'includes/_footer' %>
  </body>
</html>
```

上記は、最終的に下記のような感じで出力されます。

```html
<!DOCTYPE html>
<html lang="ja">
  ...
  <body>
    <header>これはヘッダーだよ</header>
    <p>Hello World</p>
    <footer>これはフッターだよ</footer>
  </body>
</html>
```

