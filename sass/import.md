---
title: "SCSS ファイルから別の SCSS ファイルをインポートする (@import)"
date: "2018-12-17"
---

インポートの基本
----

SCSS の **`@import`** ディレクティブを使用すると、SCSS ファイルから別の SCSS ファイルをインポートすることができます。

#### 入力 (SCSS)

~~~ scss
@import "colors.scss";  // 拡張子は省略可能

main {
  color: $main-color;
}
~~~

#### インポートされるファイル (colors.scss)

~~~ scss
// 色の定義
$main-color: black;
~~~

#### 出力 (CSS)

~~~ css
main {
  color: black; }
~~~


複数の SCSS ファイルをインポートしたいときは、複数行に分けて `@import` ディレクティブを記述してもよいですし、次のようにカンマ区切りでまとめてインポートすることもできます。

~~~ scss
@import "foo", "bar", "hoge";
~~~


CSS 標準の @import ディレクティブとして使用する
----

`@import` ディレクティブ自体は、通常の CSS に搭載されている機能であり、上記のように SCSS ファイルをその場所にインポートして展開する機能は、Sass によって追加された拡張機能です。

次のような一定のルールに従って `@import` ディレクティブを使用すると、従来の CSS の `@import` ディレクティブとして使用されたとみなされ、`@import` の行がそのまま出力されます（インポートしたファイルの内容がその場に展開されることはありません）。

#### 入力 (SCSS)

~~~ scss
// 拡張子が .css である
@import "colors.css";

// メディアクエリが付いている
@import "colors" screen;

// URLで指定されている
@import "http://example.com/colors";

// url() 関数で指定されている
@import url(colors);
~~~

#### 出力 (CSS)

~~~ css
@import url(colors.css);
@import "colors" screen;
@import "http://example.com/colors";
@import url(colors);
~~~


パーシャル SCSS ファイル (Partials)
----

インポートして使用する SCSS ファイルの名前を、アンダースコア (`_`) で始まる名前にしておくと、Sass プロセッサによってそのファイル自体が CSS にコンパイルされるのを防ぐことができます。

例えば、インポート用の色定義ファイルを `_colors.scss` として作成しておけば、Sass プロセッサは `colors.css` を生成しません。
`_colors.scss` ファイルをインポートして使用するときは、アンダースコアを除いた名前でインポートすることができます。

~~~ scss
@import "colors";  // "_colors.scss" のインポート
~~~


ネストされたインポート (Nested @import)
----

ネストされたルール定義の中で `@import` ディレクティブを使用して SCSS ファイルをインポートすると、その階層に SCSS ファイルの内容が展開されます。

#### 入力 (SCSS)

~~~ scss
main {
  @import "emphasize";
}
~~~

#### インポートされるファイル (_emphasize.scss)

~~~ scss
.emphasize {
  font-weight: bolder;
  color: red;
}
~~~

#### 出力 (CSS)

~~~ css
main .emphasize {
  font-weight: bolder;
  color: red; }
~~~

セレクタが `main .emphasize` と、同一階層に展開されているので最初は分かりにくいかもしれませんが、下記のように `@import` の位置にそのままインポートされてから、最終的に上記のような結果になると考えると理解できると思います。

~~~ scss
main {
  .emphasize {
    font-weight: bolder;
    color: red;
  }
}
~~~


インデックスファイル (Index file)
----

あるディレクトリの中に `_index.scss` ファイルを作成しておくと、そのファイルは `@import "ディレクトリ名"` という指定でインポートできるようになります。

この仕組みを使用すれば、複数の SCSS ファイルから構成される SCSS ライブラリを、ひとつのディレクトリ内にまとめて提供することができます（パッケージのように扱える）。

下記は、`mylib` という SCSS パッケージ（ディレクトリ）を作成して使用する例です。

#### 入力 (SCSS)

~~~ scss
@import "mylib"
~~~

#### インポートされるファイル (mylib/_index.scss)

~~~ scss
// 同じディレクトリにあるサブ SCSS ファイル群をインポート
@import "sub1";
@import "sub2";
@import "sub3";
~~~

`_sub1.scss`、`_sub2.scss`、`_sub3.scss` は全て `mylib` ディレクトリに格納しておきます（内容は任意なので省略）。

このように一連の SCSS ファイルをパッケージングしておくと、上記のようにインデックスファイル経由でまとめてインポートすることもできるし、次のように個別にインポートすることもできるようになります。

~~~ scss
@import "mylib/sub1";
~~~

