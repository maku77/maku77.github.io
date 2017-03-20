---
title: 文字列と数値を変換する
created: 2017-03-20
---

文字列を数値に変換する
----

`Number` 関数を使用すると、文字列を数値に変換することができます。

~~~ javascript
var s = '123';
var n = Number(s);
console.log(typeof n);  //=> number
~~~

`Number` 関数は、数値とみなせない文字列が渡されると `NaN` を返します。
このようなケースで強制的に `0` として扱うようにするには、下記のように `||` 演算子を使用します。

~~~ javascript
var s = 'foo';
var n = Number(s) || 0;  // 数値に変換できない場合は 0 とする
~~~

この書き方は、下記のようなコードの代わりに使用することができます。

~~~ javascript
var s = 'foo';
var n = Number(s);
if (isNaN(n)) {
  n = 0;
}
~~~


数値を文字列に変換する
----

`String` 関数を使用すると、数値を文字列に変換することができます。

~~~ javascript
var n = 123;
var s = String(n);
console.log(typeof s);  //=> string
~~~

`Number` オブジェクトの `toString` メソッドを使用すると、基数を指定して文字列に変換することができます。

~~~ javascript
console.log((255).toString(2));   //=> '11111111'（２進数表記）
console.log((255).toString(8));   //=> '377'（８進数表記）
console.log((255).toString(10));  //=> '255'（10進数表記）
console.log((255).toString(16));  //=> 'ff'（16進数表記）
~~~

