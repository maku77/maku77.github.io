---
title: "Mixin でスタイルを組み合わせる (@mixin)"
date: "2018-12-19"
description: "Sass のもっとも強力な機能とされている Mixin 機能を使用すると、部分的なスタイル定義をインクルードして使用できるようになります。"
---

Mixin の基本
----

Sass の Mixin 機能は、ひとまとまりのスタイルを Mixin として定義 (`@mixin`) しておき、複数の場所からそのスタイルをインクルード (`@include`) して使用する仕組みです。
Mixin は、スタイルセットを提供する関数のようなものと考えるとわかりやすいです。

便利な Mixin を SCSS ファイルにまとめて定義しておけば、再利用可能な Sass ライブラリとして使用できます。
定義した Mixin は `@include` して使用しない限り CSS に出力されることはないので、Sass ライブラリの中の Mixin 定義が増えても、最終的な CSS が肥大化する心配はありません。

下記の例では、`round-box` という名前の Mixin を定義して、`pre` 要素と `.ad-widget` クラスのスタイル定義からインクルードしています。

#### 入力 (SCSS)

~~~ scss
@mixin round-box {
  border: solid 1px gray;
  border-radius: 5px;
  padding: 10px;
}

pre {
  @include round-box;
  line-height: 1.3;
}

.ad-widget {
  @include round-box;
  background: #ccc;
}
~~~

#### 出力 (CSS)

~~~ css
pre {
  border: solid 1px gray;
  border-radius: 5px;
  padding: 10px;
  line-height: 1.3;
}

.ad-widget {
  border: solid 1px gray;
  border-radius: 5px;
  padding: 10px;
  background: #ccc;
}
~~~

Mixin で定義したスタイルセットが、`@include` した位置に展開されていることが分かります。

<div class="note">
同じコードが2か所に展開されているので、このようなシンプルな例では、<code>@extend</code> を使用した場合より非効率なコードになってしまいます。
このあたりは Sass のバージョンアップによって改善されるかもしれません。
とはいえ、Mixin には後述のような、引数を扱う仕組みなどがあり、<code>@extend</code> より使用する機会は多いでしょう。
</div>


Mixin の中で親セレクタの参照も可能
----

Sass のコードの中で `&` は親セレクタを参照するために使用することができますが、Mixin の定義の中でも `&` を使用することができます。
Mixin の定義のトップレベルで `&` を参照すると、呼び出し元（`@include` したところ）のセレクタに置き換えられます。

下記は、`::after` 疑似要素を使用した典型的な clearfix イディオムを、Mixin で定義する例です。

#### 入力

~~~ scss
@mixin clearfix {
  &::after {
    content: '';
    display: block;
    clear: both;
  }
}

.breadcrumb {
  @include clearfix;
  float: left;
  margin: 10px;
}
~~~

#### 出力 (CSS)

~~~ css
.breadcrumb {
  float: left;
  margin: 10px;
}

.breadcrumb::after {
  content: '';
  display: block;
  clear: both;
}
~~~


ルートレベルでの Mixin の @include
----

下記のように、入れ子スタイルで定義した Mixin は、ルートレベルで `@include` して使用することができます。

#### 入力 (SCSS)

~~~ scss
@mixin pretty-link {
  a {
    font-weight: bolder;
    text-decoration: none;

    &:link { color: #36f; }  // 未訪問
    &:visited { color: #03c; }  // 訪問済
    &:hover { background: #def; }  // マウスフォーカス
    &:focus { background: #fdd; }  // 選択中
  }
}

// ルートレベルでインクルードできる
@include pretty-link;
~~~

#### 出力 (CSS)

~~~ css
a {
  font-weight: bolder;
  text-decoration: none;
}
a:link {
  color: #36f;
}
a:visited {
  color: #03c;
}
a:hover {
  background: #def;
}
a:focus {
  background: #fdd;
}
~~~


引数付きの Mixin を定義する
----

Mixin には任意の数の引数を渡すことができます。
下記の `color-box` は、2つの引数 (`$color`、`$border-width`) を受け取るように定義しています。

#### 入力 (SCSS)

~~~ scss
@mixin color-box($color, $border-width) {
  color: $color;
  background: lighten($color, 45%);
  border: solid $color $border-width;
  padding: 0.5em;
}

.warn {
  @include color-box(darkorange, 3px);
}

.error {
  @include color-box(red, 3px);
}
~~~

#### 出力 (CSS)

~~~ css
.warn {
  color: darkorange;
  background: #fff4e6;
  border: solid darkorange 3px;
  padding: 0.5em;
}

.error {
  color: red;
  background: #ffe6e6;
  border: solid red 3px;
  padding: 0.5em;
}
~~~


### デフォルト引数

Mixin の引数名の後ろに `: デフォルト値` と記述することで、引数ごとのデフォルト値を設定しておくことができます。

#### 入力 (SCSS)

~~~ scss
@mixin color-box($color: black, $border-width: 1px) {
  color: $color;
  background: lighten($color, 45%);
  border: solid $color $border-width;
  padding: 0.5em;
}

.error {
  @include color-box(red);  // 2番目のパラメータのみ省略
}
~~~

#### 出力 (CSS)

~~~ css
.error {
  color: red;
  background: #ffe6e6;
  border: solid red 1px;
  padding: 0.5em;
}
~~~

すべてのパラメータを省略する場合は、下記のいずれの書き方でも OK です。

~~~ scss
@include color-box();
@include color-box;
~~~


### 名前付き引数 (Keyword arguments)

Mixin をインクルード (`@include`) するとき、パラメータの値を名前付きで指定することができます。
名前付き引数として値を指定するときは、引数の指定順序は任意となります。

~~~ scss
@include color-box($border-width: 3px, $color: red);
~~~

名前付き引数を使用すると記述量は若干増えますが、それぞれのパラメータが何を表しているか明確になるというメリットがあります。

また、名前付き引数を使用することで、任意の位置のデフォルト引数のみを省略することが可能になります。
次の例では、1番目の引数 `$color` を省略し、2番目の引数 `$border-width` のみを指定して Mixin をインクルードしています。

~~~ scss
@include color-box($border-width: 3px);
~~~


### 可変長引数

Mixin の引数の末尾に `...` を付けると、その引数を可変長引数として扱うことができます。
例えば、CSS の `box-shadow` プロパティはもともと可変長のパラメータを受け付けますが、このようなプロパティを扱う Mixin は次のように定義することができます。

#### 入力 (SCSS)

~~~ scss
@mixin box-shadow($shadows...) {
  -moz-box-shadow: $shadows;
  -webkit-box-shadow: $shadows;
  box-shadow: $shadows;
}

.box {
  @include box-shadow(0px 4px 5px #666, 2px 6px 10px #999);
}
~~~

#### 出力 (CSS)

~~~ css
.box {
  -moz-box-shadow: 0px 4px 5px #666, 2px 6px 10px #999;
  -webkit-box-shadow: 0px 4px 5px #666, 2px 6px 10px #999;
  box-shadow: 0px 4px 5px #666, 2px 6px 10px #999;
}
~~~

上記のように、**Mixin はベンダープレフィックスの必要なプロパティをまとめて定義するためにも利用することができます**。

ちなみに、`...` というキーワードは、リスト変数の値を展開して、複数のパラメータとして Mixin や関数に渡すためにも使用します。

#### 入力 (SCSS)

~~~ scss
@mixin fancy-border($color, $width, $style) {
  border: $width $style $color;
  border-radius: 5px;
}

$value-list: green, 1px, solid;
$value-map: (color: red, width: 3px, style: dashed);

.success {
  @include fancy-border($value-list...);
}
.failure {
  @include fancy-border($value-map...);
}
~~~

#### 出力 (CSS)

~~~ css
.success {
  border: 1px solid green;
  border-radius: 5px;
}

.failure {
  border: 3px dashed red;
  border-radius: 5px;
}
~~~


スタイルセットを Mixin のパラメータとして渡す（コンテントブロック） (@content)
----

Mixin の定義の中で `@content` を参照すると、呼び出し側から渡されたスタイル定義をそこに展開することができます。
呼び出し側では、次のような構文でスタイル定義のブロックを Mixin に渡します。

~~~
@include <Mixin名> {
  スタイル定義
}
~~~

この仕組みは、**同じ条件のメディアクエリを複数のスタイルに対して適用するときに便利**です。

#### 入力 (SCSS)

~~~ scss
@mixin media-large {
  // スクリーンの横幅が広い端末（PCなど）の場合
  @media screen and (min-width: 800px) {
    @content;
  }
}

main {
  margin: 1em;  // 幅が狭いときはマージンは小さく
  @include media-large {
    margin: 2em;  // 幅が広いときはマージンを大きく
  }
}

#xMenu {
  display: none;  // 幅が狭いときはメニューは表示しない
  @include media-large {
    display: block;  // 幅が広いときはメニューを表示する
    width: 20rem;
  }
}
~~~

#### 出力 (CSS)

~~~ css
main {
  margin: 1em;
}
@media screen and (min-width: 800px) {
  main {
    margin: 2em;
  }
}

#xMenu {
  display: none;
}
@media screen and (min-width: 800px) {
  #xMenu {
    display: block;
    width: 20rem;
  }
}
~~~

スクリーンサイズのブレイクポイントとする横幅などは、下記のように変数に定義しておくとメンテナンスしやすいかもしれません。

~~~ scss
$LARGE_SCREEN_WIDTH: 800px;

@mixin media-large {
  @media screen and (min-width: $LARGE_SCREEN_WIDTH) {
    @content;
  }
}
~~~

ちなみに、Mixin の中で `content-exists()` 関数を使用すると、コンテントブロックが Mixin に渡されたかどうかを調べることができます。


Mixin 名の中のハイフンとアンダースコアは同じ
----

変数名や関数名も同様ですが、Mixin の名前の中で使用したハイフン (`-`) と、アンダースコア (`_`) は相互に置き換えが可能になっています。

~~~ scss
@mixin foo-bar {
  color: red;
}

main {
  @include foo_bar;  // ハイフンをアンダースコアに変えても参照できる
}
~~~

ただし、この振る舞いは歴史的な理由によるものなので、ハイフンで定義したものはハイフンを使って参照すべきでしょう。


Mixin と @extend ディレクティブの違い
----

Sass の [@extend ディレクティブ](extend.html)も似たような機能を提供していますが、`@extend` はあくまで**セレクタを継承**する機能であり、Mixin 機能より若干複雑な振る舞いをします。
Mixin 機能は**スタイルそのものを使いまわす**ものであり、`@extend` ディレクティブの振る舞いよりシンプルです（機能としては強力ですが）。

Mixin と `@extend` の違いをざっとあげると下記のような感じでしょうか。

- Mixin は `@extend` とは異なり、セレクタの組み合わせによるルールが継承されない。
- Mixin はパラメータを扱うことができる。
- Mixin をインクルードした場所にスタイル定義がコピーされて展開されるため、出力後の CSS は `@extend` を使ったものよりも冗長になる可能性がある。


Mixin の利用例: ベンダープレフィックス
----

Mixin は、ベンダープレフィックスが必要なプロパティをまとめて指定するためにも活用できます。

#### border-radius 用の Mixin

~~~ scss
@mixin border-radius($radius: 5px) {
  -moz-border-radius: $radius;
  -webkit-border-radius: $radius;
  border-radius: $radius;
}

.box {
  @include border-radius(10px);
}
~~~

#### box-shadow 用の Mixin

~~~ scss
@mixin box-shadow($shadows...) {
  -moz-box-shadow: $shadows;
  -webkit-box-shadow: $shadows;
  box-shadow: $shadows;
}

.box {
  @include box-shadow(0px 4px 5px #666, 2px 6px 10px #999);
}
~~~

