---
title: "関数を定義する (@function)"
date: "2018-12-20"
description: "Sass では独自の関数を定義することができ、任意の場所から呼び出すことできます。"
---

Sass (SCSS) で独自関数を定義するには、下記のような構文の **`@function`** ディレクティブを使用します。
関数名は、組み込みの関数とコンフリクトしないように、何らかのプレフィックスを付けることが推奨されています（ライブラリなどを作っているのでなければ、`my-` とか `x-` とか付けておけば大丈夫だと思います）。

~~~
@function 関数名($引数1, $引数2) {
  @return 戻り値;
}
~~~

Mixin と似ているように思われるかもしれませんが、Mixin がひとまとまりのルールセットを呼び出し側にインクルードするのに対し、関数は何らかの値を受け取って、その演算結果を値として返すために使用します（関数の方が粒度が小さい）。

下記の `my-hover-color` 関数は、パラメータで指定された色より若干明る目の色を返します。
ボタン要素にマウスカーソルを重ねたときの背景色を作成するために使用しています。

#### 入力 (SCSS)

~~~ scss
$LIGHTEN_RATE: 20%;

@function my-hover-color($color) {
  @return lighten($color, $LIGHTEN_RATE);
}

button {
  color: white;
  background: black;

  &:hover {
    background: my-hover-color(black);
  }
}
~~~

#### 出力 (CSS)

~~~ css
button {
  color: white;
  background: black;
}

button:hover {
  background: #333333;
}
~~~

Mixin と同様に、パラメータは名前付き引数として設定することができます。
パラメータが複数あるときは、この形式で呼び出すようにすると可読性を上げられるかもしれません。

~~~ scss
background: my-hover-color($color: black);
~~~


デフォルト引数
----

関数の引数にはデフォルト値を設定しておくことができます。これも Mixin と同様です。

下記の例では、`my-add` 関数の2番目のパラメータのデフォルト値を 100 に設定しています。
呼び出しに2番目のパラメータの指定を省略すると、100 が使用されます。

#### 入力 (SCSS)

~~~
@function my-add($a, $b: 100) {
  @return $a + $b;
}

main {
  margin-left: my-add(1px, 2px);
  margin-right: my-add(1px);
}
~~~

#### 出力 (CSS)

~~~ css
main {
  margin-left: 3px;
  margin-right: 101px;
}
~~~


可変長引数
----

関数の引数名の後ろに `...` を付けると、可変長引数として扱うことができます。

下記の `my-sum` 関数は、任意の数の数値を受け取り、合計値を返します。
ここでは、その結果を `@debug` ディレクティブを使用して出力しています。

~~~ scss
@function my-sum($values...) {
  $sum: 0;
  @each $x in $values {
    $sum: $sum + $x;
  }
  @return $sum;
}

@debug my-sum(1, 2, 3, 4, 5);  //=> 15
~~~


関数名の中のハイフンとアンダースコアは同じ
----

Mixin や変数の名前と同様に、関数の名前の中で使用したハイフン (`-`) と、アンダースコア (`_`) は相互に置き換えが可能になっています。
`my-func` という名前で定義した関数は、`my_func` という名前で呼び出すことができます。

ただし、この振る舞いは歴史的な理由によるものなので、定義した通りの名前で呼び出すようにしましょう。

