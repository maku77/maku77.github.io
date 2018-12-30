---
title: "Sass とは？Sass をインストールする"
date: "2018-12-12"
---

Sass とは
----

Sass は CSS (Cascading Style Sheets) ファイルを効率的に作成するための変換ツールで、Hampton Catlin（ハンプトン・キャトリン）と Nathan Weizenbaum（ネイサン・バイゼンバウム）によって作成されました。

従来の CSS には存在しない変数の仕組みや、ループ処理、モジュールの仕組みなどを使用できるため、効率的に CSS ファイルを作成することができます。

変数の仕組みなどは、CSS のバージョンアップとともにネイティブでサポートされつつありますが、Web ブラウザによって対応状況がまちまちだったりするため、Sass を使って多くのブラウザがサポートしている CSS フォーマットに変換するという方法がまだまだ主流でしょう。

### SASS 記法と SCSS 記法

Sass へのインプットに使用するファイルは、SASS フォーマットと SCSS フォーマットのどちらかで記述します。
どちらも変換後は CSS ファイルになるのですが、記述方法が若干異なります。

- SASS記法: **`.sass`** 拡張子でファイルを作成する。Jade や Python のようにインデントによって構造化する。昔使われていた記法。
- SCSS記法: **`.scss`** 拡張子でファイルを作成する。CSS と同様に括弧（`{` と `}`）で構造化する。Sassy CSS の略で、Sass 3.0 で導入された。現在主流の記法。

現在は、CSS と互換性のある SCSS フォーマットで記述するのが主流です。
CSS ファイルは SCSS ファイルとしても正しい文法とみなせるため、とりあえず `.scss` 拡張子のファイルに従来の CSS フォーマットでスタイル定義しておいて、徐々に SCSS の機能を使って内容を充実させていく、といった使い方が可能です。


sass コマンドをインストールする
----

scss/sass ファイルを css ファイルに変換するための `sass` コマンドは、下記のサイトに記述されているように簡単にインストールすることができます。

- [Sass: Install Sass](https://sass-lang.com/install)

例えば、[Node.js](/nodejs/) の環境がインストールされている環境であれば、npm (node package manager) を使用して、次のようにインストールできます。

~~~
$ npm install -g sass
~~~

Static Web サイトジェネレータである、Jekyll（GitHub pages）、Hugo、Middleman などには、組み込みで Sass の機能が備えられているため、これらのツール上で Sass の機能を使用するだけであれば、`sass` コマンドを単体でインストールする必要はありません。

- [参考: まくまくHugoノート](/hugo/)

ただ、Sass の振る舞いを確認するために入れておくのもよいでしょう。
インストールが完了したら、下記のように `sass` コマンドを実行できるようになります。

~~~
$ sass --version
1.15.2 compiled with dart2js 2.1.0
~~~


sass コマンドの基本的な使い方
----

### scss → css の変換

~~~
$ sass sass/input.scss css/output.css
~~~

#### 入力ファイル (sass/input.scss)

~~~ scss
$COLOR_MAIN: #333;

body {
  color: $COLOR_MAIN;
}
~~~

#### 変換結果 (css/output.css)

~~~ css
body {
  color: #333;
}

/*# sourceMappingURL=output.css.map */
~~~

出力ファイル名を指定しないで実行すると、変換結果は標準出力に出力されます。

~~~
$ sass sass/input.scss
body {
  color: #333;
}
~~~


### scss ファイルの変更を監視する

下記のように `--watch` オプションを付けて実行すると、入力ファイルの内容が変更されたとき（上書き保存されたとき）に、自動的に変換が行われて、出力ファイルの内容が更新されます。

~~~
$ sass --watch input.scss output.css
~~~

