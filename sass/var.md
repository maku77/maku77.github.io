---
title: "SCSS で変数を使用する"
date: "2018-12-17"
---

変数定義の基本
----

SCSS ファイル内で変数を定義するには、下記のような構文を使用します。

~~~
$変数名: 初期値;
~~~

下記はカラー値を変数として定義する簡単な例です。

#### 入力 (SCSS)

~~~ scss
$main-color: #333;
body {
  color: $main-color;
}
~~~

#### 出力 (CSS)

~~~ css
body {
  color: #333;
}
~~~

変数の値をそのままプロパティの値として使用する場合は、上記のように `$変数名` の形で参照すればOKです。
何らかのキーワードの一部（セレクタ名の一部など）や、コメントの中で変数の値を参照する場合は、インターポレーション（`#{$変数名}`）の形式で参照する必要があります。
インターポレーションで文字列変数の値を参照すると、引用符は削除されて展開されます。

#### 入力 (SCSS)

~~~ scss
$prefix: ".maku";

#{$prefix}-shout {
  font-size: larger;
}
~~~

#### 出力 (CSS)

~~~ css
.maku-shout {
  font-size: larger;
}
~~~


変数のスコープ（ローカル変数）
----

入れ子になったルール定義の中で変数を定義すると、その変数はそのブロック内でのみ参照可能なローカル変数となります。
グローバルに定義した変数と同じ名前のローカル変数を定義することもできます。
その場合、そのブロック内ではローカル変数が参照されることになります。

#### 入力 (SCSS)

~~~ scss
$color: black;  // グローバル変数の定義

body {
  color: $color;  // グローバル変数の参照
}

main {
  $color: red;    // ローカル変数の定義
  color: $color;  // ローカル変数の参照
}
~~~

#### 出力 (CSS)

~~~ css
body {
  color: black;
}

main {
  color: red;
}
~~~


変数名のハイフンとアンダースコアは同一視される
----

歴史的な理由により、SCSS の中で定義した変数名に含まれるハイフン (`-`) と、アンダースコア (`_`) は同一のものとして扱われます。
例えば、`$main-color` という名前で定義した変数は、`$main_color` という名前でも参照できます。

~~~ scss
$main-color: #333;

body {
  color: $main_color;
}
~~~

このルールは、Mixin の名前や関数の名前でも同様に適用されます。


変数に値を代入する
----

定義した変数に別の値を代入するには、変数の定義と同じ `$変数名: 値;` という構文を使用します。

下記の例では、`@while` ループのブロックの中で、`$i` 変数の値を 1 ずつデクリメントしています。

#### 入力 (SCSS)

~~~ scss
$i: 1;

@while $i <= 5 {
  h#{$i} {
    font-size: 1.6rem - (0.1rem * $i);
  }
  $i: $i + 1;
}
~~~

#### 出力 (CSS)

~~~ css
h1 {
  font-size: 1.5rem; }

h2 {
  font-size: 1.4rem; }

h3 {
  font-size: 1.3rem; }

h4 {
  font-size: 1.2rem; }

h5 {
  font-size: 1.1rem; }
~~~


変数のデータタイプ
----

Sass で扱える変数のデータタイプは、下記の8種類です。

- <b>number</b>: 単位なしの数値 (`13`, `0.3`) や単位ありの数値 (`5em`, `10px`)
- <b>string</b>: クォートされていない文字列 (`sans-serif`, `bar`) やクォートされた文字列 (`"foo"`, `"こんにちは"`)
- <b>color</b>: 色を表す値 (`red`, `#0cf2f9`, `rgba(255, 255, 0, 0.5)`)
- <b>boolean</b>: 真値あるいは偽値 (`true`, `false`)
- <b>null</b>: `null`
- <b>list</b>: スペースやカンマで区切られた値のリスト (`1em 2em 1em 0.5em`, `Helvetica, Arial, sans-serif`)
- <b>map</b>: キー＆バリューのリスト。全体を括弧で囲んで定義する (`(key1:value1, key2:value2)`
- <b>function</b>: `get-function("関数名")` で返される関数への参照。`call($func, $args...)` で呼び出せる

Sass の `type-of` 関数を使用すると、ある変数がどのデータタイプとして扱われているかを調べることができます。

#### 入力 (SCSS)

~~~ scss
$color: red;

#message {
  content: "red is " + type-of($color);
}
~~~

#### 出力 (CSS)

~~~ css
#message {
  content: "red is color"; }
~~~

