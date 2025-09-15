---
title: "Sassメモ: SCSS ファイルの書き方の基本"
url: "p/34uu8u7/"
date: "2018-12-12"
tags: ["sass"]
description: "Sass (SCSS) を使用すると、CSS では扱えないネスト構造でのスタイル記述が可能になります。Sass には多くの機能がありますが、このネストの仕組みだけでも Sass 導入の価値があります。"
aliases: ["/sass/basic.html"]
---

Sass (SCSS) を使用すると、CSS では扱えないネスト構造でのスタイル記述が可能になります。
Sass には多くの機能がありますが、このネストの仕組みだけでも Sass 導入の価値があります。

入れ子構造
----

従来の CSS は、一階層のセレクタブロック（`{` ～ `}`）でしかルールセットを記述できませんが、SCSS では下記のような入れ子構造で定義していくことができます。

{{< code lang="scss" title="入力 (SCSS)" >}}
#main {
  width: 97%;

  p, div {
    font-size: 2em;
    a { font-weight: bold; }
  }

  pre { font-size: 3em; }
}
{{< /code >}}

上記のような入れ子構造は子孫セレクタを表現しているとみなされ、Sass プロセッサにより、下記のようにフラットな構造の CSS に展開されます。

{{< code lang="css" title="出力 (CSS)" >}}
#main {
  width: 97%;
}
#main p, #main div {
  font-size: 2em;
}
#main p a, #main div a {
  font-weight: bold;
}
#main pre {
  font-size: 3em;
}
{{< /code >}}

子孫セレクタ以外のセレクタシーケンス（子セレクタ (`>`) や隣接セレクタ (`+`)）なども、同様に入れ子の形で定義することができます。

{{< code lang="scss" title="入力 (SCSS)" >}}
.xArticle {
  > h2 {
    font-size: 1.4rem;
    margin-top: 1.5em;
  }
}

p {
  + ul {
    margin-top: 2em;
  }
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
.xArticle > h2 {
  font-size: 1.4rem;
  margin-top: 1.5em;
}

p + ul {
  margin-top: 2em;
}
{{< /code >}}


親セレクタの参照 (&)
----

SCSS のネストされたスタイル定義において、`&` 記号を使用すると、親セレクタの名前を省略して記述することができます。
この記法は、下記のような `:hover` 擬似クラスなどでよく使用されます。

{{< code lang="scss" title="入力 (SCSS)" >}}
#main {
  color: #333;
  a {
    font-weight: bold;
    &:hover { color: red; }
  }
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
#main {
  color: #333;
}
#main a {
  font-weight: bold;
}
#main a:hover {
  color: red;
}
{{< /code >}}

`&` を使用して定義したルールは、階層としては親セレクタと同じレベルに出力されることに注意してください（SCSS の記述では入れ子になっていますが、CSS に変換された後はひとつ上のレベルに出力されます）。

この仕組みは、クラス名のプレフィックスとして親クラスの名前を付けるときにも使用することができます。
[BEM のようなネーミングルール](http://getbem.com/introduction/)を採用している場合は、この仕組みを使用すると、簡潔にネスト構造を表現できるようになります。

{{< code lang="scss" title="入力 (SCSS)" >}}
.menu {
  color: #333;
  border: 1px solid #D5D5D5;

  &__section {
    font-weight: bold;
  }

  &__item {
    color: blue;
  }
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
.menu {
  color: #333;
  border: 1px solid #D5D5D5;
}

.menu__section {
  font-weight: bold;
}

.menu__item {
  color: blue;
}
{{< /code >}}


ネームスペース機能
----

CSS にはネームスペースという概念があり、例えば、`font` ネームスペースには、`font-family`、`font-size`、`font-weight` などのプロパティが定義されています（ハイフンでネームスペースの区切りを表しています）。
SCSS では、このような特定のネームスペース下のプロパティを、入れ子の形でまとめて定義することができます。

{{< code lang="scss" title="入力 (SCSS)" >}}
.funky {
  font: {
    family: fantasy;
    size: 30em;
    weight: bold;
  }
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
.funky {
  font-family: fantasy;
  font-size: 30em;
  font-weight: bold;
}
{{< /code >}}

下記のように記述すれば、`font` プロパティそのものに対して値を設定することも可能です。

{{< code lang="scss" title="入力 (SCSS)" >}}
.funky {
  font: 20px/24px fantasy {
    weight: bold;
  }
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
.funky {
  font: 20px/24px fantasy;
  font-weight: bold;
}
{{< /code >}}


@media ディレクティブの入れ子
----

通常の CSS では、`@media` ディレクティブはルートレベルに記載する必要がありますが、SCSS ではセレクタブロックの中に記述することができます。
これにより、同じセレクタを複数個所に定義する必要がなくなります。

{{< code lang="scss" title="入力 (SCSS)" >}}
#sidebar {
  display: none;  // 幅が狭いときはサイドバーは表示しない
  float: none;

  @media screen and (min-width: 800px) {
    display: block;  // 幅が広いときはサイドバーを表示
    float: right;
    width: 20rem;
    margin-left: 1rem;
    background: #ccc;
  }
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
#sidebar {
  display: none;
  float: none;
}
@media screen and (min-width: 800px) {
  #sidebar {
    display: block;
    float: right;
    width: 20rem;
    margin-left: 1rem;
    background: #ccc;
  }
}
{{< /code >}}

`@media` ディレクティブを入れ子で使用すると、それぞれのクエリが `and` で接続された状態で出力されます。

{{< code lang="scss" title="入力 (SCSS)" >}}
@media screen {
  #sidebar {
    @media (orientation: landscape) {
      width: 500px;
    }
  }
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
@media screen and (orientation: landscape) {
  #sidebar {
    width: 500px;
  }
}
{{< /code >}}

ちなみに、メディアクエリを使用したスタイル定義は、[Mixin の機能](/p/awmebxk/)でコンテントブロック (`@content`) を使用するとよりきれいに記述することができます。

