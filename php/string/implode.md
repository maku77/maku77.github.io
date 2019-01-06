---
title: "文字列を結合する (implode)"
date: "2012-01-15"
---

単純な文字列結合
----

PHP では文字列の結合にドット (`.`) 演算子を使用します。

~~~ php
$s = 'Hello';
$con = $s . ' World';  //=> 'Hello World'
~~~

元の文字列を直接変更する場合は `.=` 演算子を使用できます。

~~~
$s = 'Hello';
$s .= ' World';  //=> 'Hello World'
~~~


配列要素を結合して１つの文字列にする
----

[implode 関数](http://php.net/manual/ja/function.implode.php)を使用すると、配列の要素を１つの文字列に結合することができます。
配列の各要素は文字列に変換されて結合されるので、数値を含んでいても構いません。

~~~ php
$arr = array('AAA', 'BBB', 100, 200);
$s = implode($arr);  //=> 'AAABBB100200'
~~~

パラメータを２つ取るバージョンの `implode` を使用すれば、各要素の間に任意のデリミタ文字列を挟んで結合することができます。

~~~ php
$arr = array('AAA', 'BBB', 100, 200);
$s2 = implode(', ', $arr);  //=> 'AAA, BBB, 100, 200'
~~~


参考
----

- [文字列を分割する (explode)](../string/explode.html)

