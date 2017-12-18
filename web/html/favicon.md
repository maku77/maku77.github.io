---
title: "Web サイトに favicon を設定する"
created: 2017-12-18
description: "Web サイトの favicon は、Web ブラウザのタブのアイコンや、ブックマークのアイコンとして使用されます。"
---

favicon の基本
----

favicon 用の画像は、PNG、ICO、GIF のいずれかのフォーマットで作成します（PNG 形式が推奨）。
favicon のパスは、HTML の head 要素内で下記のように指定します。

~~~ html
★ルートからの相対 URL で指定する場合
<link rel="icon" href="/favicon.ico" />

★ドメインを含めた URL で指定する場合
<link rel="icon" href="https://example.com/favicon.ico" />
~~~

ICO ファイルは複数のサイズのアイコンを含むことができるので、どのようなサイズを含めておくべきか迷うところですが、Microsoft は下記のようなサイズで作っておくとよいと[述べています](https://msdn.microsoft.com/library/gg491740(v=vs.85).aspx)。

- 16 x 16
- 24 x 24
- 32 x 32
- 48 x 48
- 64 x 64

[X-Icon Editor のサイト](http://www.xiconeditor.com/) を使用すると、Web ブラウザ上で複数サイズのアイコンを含む ICO ファイルを作成することができます。


PNG ファイルで favicon を用意する方法
----

ICO 形式は古いフォーマットのため、現在では **PNG 形式のファイルで favicon を作成するのが一般的**です。
PNG ファイルは、ICO ファイルと異なり、複数サイズのアイコンを含むことができないので、特定のサイズに最適されたアイコンを用意する場合は、複数の PNG ファイルとして作成しておく必要があります。

link 要素の `type` 属性、`sizes` 属性を使用すると、クライアントに対して、favicon に関する付加的な情報を与えることができます。
PNG フォーマットで favicon ファイルを用意する場合は、下記のように属性指定を行い、サイズ別にどのファイルを使用するか明示しておくとよいでしょう。

~~~ html
<link rel="icon" type="image/png" href="/favicon-16x16.png" sizes="16x16" />
<link rel="icon" type="image/png" href="/favicon-32x32.png" sizes="32x32" />
<link rel="icon" type="image/png" href="/favicon-96x96.png" sizes="96x96" />
~~~

上記で指定した PNG ファイルのうち、複数のファイルが適用可能な場合は、最後に宣言されたものが選択されることが一般的なため、上記のように**解像度の低いものから順番に指定する**ようにしましょう。

sizes 属性には複数のサイズを指定することができるため、下記のように同一のアイコンで複数サイズの表示に対応させることもできます（この場合、きれいに拡大・縮小されて表示されるかどうかはクライアントの実装依存になります）。

~~~ html
<link rel="icon" type="image/png" href="/favicon.png" sizes="16x16 24x24 32x32 48x48 64x64" />
~~~


スマホ（Android、iPhone）用の高解像度アイコンの指定
----

Android や iPhone などのスマホ、Windows 10、Android TV（旧: Google TV）のような様々なデバイスでは、より高解像度なアイコンを表示するようになってきています。
これらのアイコンは Web サイトの顔（エントリポイント）となるものですから、できるだけ最適なサイズで用意しておくとよいでしょう。

例えば、iOS version 8 までの全ての Apple Touch icon サイズに対応するのであれば、下記のようなサイズのアイコンを用意しておく必要があります。

~~~ html
<!-- Android & iPhone -->
<link rel="apple-touch-icon" sizes="57x57"   href="/asset/favicon/apple-touch-icon-57x57.png" />
<link rel="apple-touch-icon" sizes="60x60"   href="/asset/favicon/apple-touch-icon-60x60.png" />
<link rel="apple-touch-icon" sizes="72x72"   href="/asset/favicon/apple-touch-icon-72x72.png" />
<link rel="apple-touch-icon" sizes="76x76"   href="/asset/favicon/apple-touch-icon-76x76.png" />
<link rel="apple-touch-icon" sizes="114x114" href="/asset/favicon/apple-touch-icon-114x114.png" />
<link rel="apple-touch-icon" sizes="120x120" href="/asset/favicon/apple-touch-icon-120x120.png" />
<link rel="apple-touch-icon" sizes="144x144" href="/asset/favicon/apple-touch-icon-144x144.png" />
<link rel="apple-touch-icon" sizes="152x152" href="/asset/favicon/apple-touch-icon-152x152.png" />
<link rel="apple-touch-icon" sizes="180x180" href="/asset/favicon/apple-touch-icon-180x180.png" />
~~~


最低限どのようなアイコンファイルを用意しておくべき？
----

デバイスごとに最適なサイズのアイコンは、多種多様なため、すべてのサイズのアイコンを用意するのは大変です。
てっとり早く、ある程度きれいな表示を行いたいのであれば、とりあえずは下記のサイズのアイコンを用意しておけばよいでしょう。

- **32x32** の PNG アイコン（favicon 用）
- **152x152** の PNG アイコン（Apple Touch icon 用）

HTML 内では下記のように指定します。

~~~ html
<head>
  ...
  <link rel="icon" type="image/png" href="/asset/favicon/favicon-32x32.png" />
  <link rel="apple-touch-icon" href="/asset/favicon/apple-touch-icon-152x152.png">
  ...
</head>
~~~


ルートに favicon.ico を置く方法
----

HTML ファイルの link 要素で favicon の URL を指定する方法が推奨されていますが、Web サイトのルートに `/favicon.ico` を置くことでも favicon を認識させることができます。


shortcut icon という指定はダメ
----

IE8 以前は、link 要素に下記のように rel 属性として `shortcut icon` を指定していましたが、これは非標準な指定方法なので、rel 属性は `icon` とだけ指定するようにしましょう。

#### 間違った指定方法

~~~ html
<link rel="shortcut icon" href="/path/favicon.ico">
~~~

link 要素の rel 属性に指定可能な値の一覧は下記にまとまっています。

- [Link types - HTML｜MDN](https://developer.mozilla.org/ja/docs/Web/HTML/Link_types)

`icon` の説明欄に、`shortcut` という指定が間違いであることが示されています。

> icon より以前はリンクタイプ shortcut がよく使用されていましたが、これは非準拠で無視されますので Web 作者は今後使用してはいけません。

どうしても、IE8 以下のバージョンにも対応したいのであれば、下記のように IE 用の条件分岐を使用してファイルを指定しておくのがよいでしょう。

~~~ html
<link rel="icon" href="/asset/favicon.ico"/>
<!--[if IE]>
<link rel="shortcut icon" href="/asset/favicon.ico"/>
<![endif]-->
~~~


参考になるサイト
----

### favicon の説明

- [Favicons, Touch Icons, Tile Icons, etc. Which Do You Need? ｜ CSS-Tricks](https://css-tricks.com/favicon-quiz/)
- [All About Favicons (And Touch Icons)](https://bitsofco.de/all-about-favicons-and-touch-icons/)

### favicon 作成サイト

- [様々なファビコンを一括生成。favicon generator](https://ao-system.net/favicongenerator/)

