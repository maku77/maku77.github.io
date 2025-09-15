---
title: "Sassメモ: Sass とは／sassコマンドのインストール／sassコマンドの使い方"
url: "p/tx93n26/"
date: "2018-12-12"
tags: ["sass"]
aliases:
  - "/sass/install.html"
  - "/sass/interactive.html"
---

Sass とは
----

Sass は CSS (Cascading Style Sheets) ファイルを効率的に作成するための変換ツールで、Hampton Catlin (ハンプトン・キャトリン) と Nathan Weizenbaum (ネイサン・バイゼンバウム) によって作成されました。
従来の CSS には存在しない変数の仕組みや、ループ処理、モジュールの仕組みなどを使用できるため、効率的に CSS ファイルを作成することができます。
変数の仕組みなどは、CSS のバージョンアップとともにネイティブでサポートされつつありますが、Web ブラウザによって対応状況がまちまちだったりするため、Sass を使って多くのブラウザがサポートしている CSS フォーマットに変換するという方法はまだまだ有効です。

### SASS 記法と SCSS 記法

Sass へのインプットに使用するファイルは、**「SASS フォーマット」** あるいは **「SCSS フォーマット」** で記述します。
どちらも変換後は CSS ファイルになるのですが、記述方法が若干異なります。

- SASS 記法: **`.sass`** 拡張子でファイルを作成する。Jade や Python のようにインデントによって構造化する。昔使われていた記法。
- SCSS 記法: **`.scss`** 拡張子でファイルを作成する。CSS と同様に括弧 (`{` と `}`) で構造化する。Sassy CSS の略で、Sass 3.0 で導入された。現在主流の記法。

現在は、CSS と互換性のある SCSS フォーマットで記述するのが主流です。
CSS ファイルは SCSS ファイルとしても正しい文法とみなせるため、とりあえず `.scss` 拡張子のファイルに従来の CSS フォーマットでスタイル定義しておいて、徐々に SCSS の機能を使って内容を充実させていく、といった使い方が可能です。


sass コマンドをインストールする
----

scss/sass ファイルを css ファイルに変換するための **`sass`** コマンドは、[Sass: Install Sass](https://sass-lang.com/install) のページに記述された手順に従って簡単にインストールすることができます。
例えば、[Node.js](/nodejs/) の環境がインストールされている環境であれば、npm (node package manager) を使用して、次のようにインストールできます。

```console
$ npm install -g sass
```

インストールが完了したら、下記のように `sass` コマンドを実行できるようになります。

```console
$ sass --version
1.15.2 compiled with dart2js 2.1.0
```

Static Web サイトジェネレータである Hugo、Jekyll (GitHub Pages)、Middleman などには、組み込みで Sass の機能が備えられているため、これらのツール上で Sass の機能を使用するだけであれば、`sass` コマンドを単体でインストールする必要はありません（[参考: まくまく Hugo ノート](/hugo/)）。
ただ、Sass の振る舞いを確認するために単体の `sass` コマンドをインストールしておくのもよいでしょう。


sass コマンドの使い方
----

### scss ファイルを css に変換する

```console
$ sass sass/input.scss css/output.css
```

{{< code lang="scss" title="入力ファイル (sass/input.scss)" >}}
$COLOR_MAIN: #333;

body {
  color: $COLOR_MAIN;
}
{{< /code >}}

{{< code lang="css" title="変換結果 (css/output.css)" >}}
body {
  color: #333;
}

/*# sourceMappingURL=output.css.map */
{{< /code >}}

出力ファイル名を指定しないで実行すると、変換結果は標準出力に出力されます。

```console
$ sass sass/input.scss
body {
  color: #333;
}
```

### scss ファイルの変更を監視する

下記のように **`--watch`** オプションを付けて実行すると、入力ファイル (`input.scss`) を監視するモードで起動します。
`input.scss` の内容が変更されたとき（上書き保存されたとき）に、自動的に変換が行われて出力ファイル (`output.css`) の内容が更新されます。

```console
$ sass --watch input.scss output.css
```

### インタラクティブモード

単体の `sass` コマンドをインストールしておくと、インタラクティブモード（対話モード）で `sass` のプロセッサを起動することができます。
Sass の関数の動作を確認したり、算術演算の結果を確認したいときに便利です。

インタラクティブモードを起動するには、`sass` コマンドの実行時に **`-i`** オプションを指定します。

```console
$ sass -i
>> 10px + 10px
20px
>> 10px * 10
100px
>> 10px * 10px
100px*px
>> lighten(#8cd, 20%)
#d7eef4
>> darken(#8cd, 20%)
#39aac6
```

インタラクティブモードでは、Sass の関数は呼び出せますが、ディレクティブ（`@for` など）は使用できないようです。

```console
>> @for $i from 1 through 3
SyntaxError: Invalid CSS after "": expected expression (e.g. 1px, bold), was "@for $i from 1 ..."
```

