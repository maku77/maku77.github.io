---
title: "ファイルが存在する場合のみ処理するコードを記述する"
date: "2018-06-11"
---

Hugo のテンプレートの中で、`fileExists` 関数を使用すると、指定したファイルが存在する時のみ有効になる処理を記述することができます。
例えば、次のようにすると、`static` ディレクトリに画像ファイルが存在する場合のみ、その画像を表示する `img` 要素を出力します。

~~~
{{ "{{" }} if (fileExists "static/assets/img/site-logo.png") }}
  <img src="{{ "{{" }} "assets/img/site-logo.png" | absURL }}">
{{ "{{" }} end }}
~~~

- 参考: [fileExists｜Hugo](https://gohugo.io/functions/fileexists/)

