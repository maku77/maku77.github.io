---
title: Swing - JEditorPane で画像ファイルを表示する
date: "2011-03-28"
---

例えば、`image` ディレクトリの中にある画像ファイルを、相対パスで指定して表示したい場合は以下のように指定します。

~~~ java
editorPane.setText("<img src='file:image/hoge.jpg'>");
~~~

Eclipse の場合はプロジェクトのルートディレクトリ（`src` ディレクトリがあるディレクトリ）に `image` ディレクトリを置きます。

`img` タグの `src` 属性には必ず `file:` というプレフィックスを付ける必要があるようです。
`<img src='image/hoge.jpg'>` としてしまうと、壊れたアイコンが表示されてしまいます。

