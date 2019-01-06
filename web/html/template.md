---
title: "HTML ファイルの雛形（テンプレート）"
date: "2012-01-15"
---

ひな形（テンプレート）
====

最小限のテンプレート
----

```html
<!DOCTYPE html>
<meta charset="UTF-8">
<title>Page Title</title>
<p>Hello HTML5!</p>
```

HTML5 では、`html`、`head`、`body` タグの記述を省略できるので、上記のような簡潔な記述でも正しい HTML 文書となります。


CSS や JavaScript コードを追記したバージョン
----

```html
<!DOCTYPE html>
<meta charset="UTF-8">
<link rel="stylesheet" href="style.css">
<title>Page Title</title>
<style>
  body {
    background-color: khaki;
  }
</style>

<p>Hello HTML5!</p>
<script>
  document.write('Goodbye!');
</script>
```

`style` タグも、`script` タグも、基本は属性なしで記述できます。


あまり省略しないバージョン
----

```html
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="style.css">
    <title>Page Title</title>
    <style>
      body { background-color: khaki; }
    </style>
  </head>
  <body>
    <p>Hello HTML5!</p>
    <script>
      document.write('Goodbye!');
    </script>
  </body>
</html>
```

HTML5 の各要素の省略について
====

HTML5 では、簡潔な記述が行えるように、様々な省略記法が用意されています。

DOCTYPE
----

HTML5 では、`DOCTYPE` は以下のように簡単に記述できるようになりました。

```html
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
↓
<!DOCTYPE html>
```

本当は `DOCTYPE` 自体を削除することも検討されたのですが、Internet Explorer がうまくモード認識できないことがあるということで、最低限の宣言だけ残されています。
DTD は誰も使わないのでなくなりました。

html、head、body 要素の省略
----

`html`、`head`、`body` タグの記述は省略できます。
記述の省略はできますが、もちろん内部的には DOM ツリー上にこれらの要素は存在しています。

Charset
----

`Charset` の定義は、以下のように簡単になりました。

```html
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
↓
<meta charset="UTF-8">
```

style 要素
----

`style` 要素の `type` 属性は省略できるようになりました。
また、`media` タイプは省略すると `all` として処理されます。

```html
<style type="text/css" media="all"> ... </style>
↓
<style> ... </style>
```

script 要素
----

JavaScript のコードを記述するとき、`script` 要素に `type="text/javascript"` という属性が必要でしたが、省略するとデフォルトで `text/javascript` と判断されるようになりました。

```html
<script type="text/javascript"> ... </script>
↓
<script> ... </script>
```

W3C の資料に下記のように記載があります。

> The default, which is used if the attribute is absent, is "text/javascript".

