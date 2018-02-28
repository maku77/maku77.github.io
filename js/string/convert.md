---
title: 文字列と数値を変換する
date: "2017-03-20"
---


演算による数値と文字列の暗黙変換
----

JavaScript では文字列と数値の四則演算を行うと、暗黙的な型の変換が行われるようになっているため、通常は数値が必要な場所で文字列をそのまま使うことができます。
演算を行う場合は、基本的に数値に変換されて計算されるのですが、例外的に `+` 演算子だけは常に文字列同士の結合として扱われます。

#### 文字列 '100' と数値の演算

~~~ javascript
console.log('100' + 7);  //=> '1007' (string) ★ここだけ文字列結合
console.log('100' - 7);  //=> 93 (number)
console.log('100' * 7);  //=> 700 (number)
console.log('100' / 7);  //=> 14.285714285714286 (number)
console.log('100' % 7);  //=> 2 (number)
~~~

数値とみなせない文字列と数値を四則演算した場合は、`NaN` となります。
ただし、この場合も `+` 演算だけは文字列結合になります。

#### 数値と数値とみなせない文字列 '30a' の演算

~~~ javascript
console.log(50 + '30a');  //=> '5030a' (string) ★ここだけ文字列結合
console.log(50 - '30a');  //=> NaN (number)
console.log(50 * '30a');  //=> NaN (number)
console.log(50 / '30a');  //=> NaN (number)
console.log(50 % '30a');  //=> NaN (number)
~~~

<div class="note">
あまり意識する必要はありませんが、<code>NaN</code> の型も number です（<code>typeof(NaN) === 'number'</code>）。
</div>


文字列を数値に変換する
----

`Number` 関数を使用すると、明示的に文字列を数値に変換することができます。

~~~ javascript
var s = '123';
var n = Number(s);
console.log(typeof n);  //=> number
~~~

`Number` 関数は、数値とみなせない文字列が渡されると `NaN` を返します。
`NaN` は偽値として扱われるので、数値に変換できない場合に `0` として扱うようにするには、下記のように `||` 演算子を利用できます。

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

`String` 関数を使用すると、明示的に数値を文字列に変換することができます。

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

ちなみに、基数は 2～36 の範囲で指定することができます。
つまり、0～9、a～z の 36 文字を使った 36 進数にまで対応しています。

