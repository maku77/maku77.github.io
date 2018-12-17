---
title: "文字列変数の引用符の展開ルール"
date: "2018-12-17"
---

引用符で囲まれた文字列、囲まれていない文字列
----

Sass の文字列変数は、引用符で囲まれた文字列と、囲まれていない文字列を定義することができます。
文字列変数を `$変数` の形で参照すると、変数定義時の引用府のありなしがそのまま反映されて展開されます。

#### 入力 (SCSS)

~~~ scss
$family1: "Lucida Grande";
$family2: 'Lucida Sans';
$family3: sans-serif;

main {
  font-family: $family1, $family2, $family3;
}
~~~

#### 出力 (CSS)

~~~ css
main {
  font-family: "Lucida Grande", "Lucida Sans", sans-serif; }
~~~


インターポレーション
----

セレクタ名やコメント、文字列の中で文字列変数を参照するには、`#{$変数}` のようなインターポレーションの形式で参照する必要があります。

#### 入力 (SCSS)

~~~ scss
$name: "maku";

/* これはコメントです by #{maku} */
.note-#{$name}  {
  content: "I like #{$name}"
}
~~~

#### 出力 (CSS)

~~~ css
@charset "UTF-8";
/* これはコメントです by maku */
.note-maku {
  content: "I like maku"; }
~~~

入力ファイルのエンコーディング形式を判別して、`@charset` ディレクティブまで自動で挿入してくれるみたいですね(^^)

インターポレーション (`#{}`) で文字列変数を参照すると、変数定義時に付いていた引用符は削除されて展開されます。

#### 入力 (SCSS)

~~~ scss
$family: "sans-serif";

main {
  font-family: $family;
  font-family: #{$family};
}
~~~

#### 出力 (CSS)

~~~ css
main {
  font-family: "sans-serif";
  font-family: sans-serif; }
~~~


文字列の結合
----

文字列は `+` 演算子を使って結合することができます。

~~~ scss
main {
  font-family: "Lucida " + "Grande";  //=> "Lucida Grande"
  font-family: sans- + serif;         //=> sans-serif
}
~~~

引用符で囲まれた文字列と囲まれていない文字列を結合した場合、`+` の左側が引用符で囲まれているかどうかで結合結果が引用符で囲まれるかどうかが決まります。

~~~ scss
main {
  font-family: "Lucida " + Grande;  //=> "Lucida Grande"
  font-family: sans- + "serif";     //=> sans-serif
}
~~~

ただし、文字列以外の値と、引用符で囲まれた文字列の結合結果は、必ず引用符で囲まれた文字列になります。

~~~ scss
main {
  content: red + " ocean";  //=> "red ocean" （color型との結合）
  content: 10 + " miles";   //=> "10 miles" （number型との結合）
}
~~~


引用符を外す関数 (unquote)
----

Sass の **`unquote`** 関数を使用すると、明示的に文字列の引用符 (`"`) を取り除くことができます。

#### 入力 (SCSS)

~~~ scss
$family: unquote("sans-serif");

main {
  font-family: $family;
}
~~~

#### 出力 (CSS)

~~~ css
main {
  font-family: sans-serif; }
~~~

