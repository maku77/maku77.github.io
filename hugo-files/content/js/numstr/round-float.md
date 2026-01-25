---
title: "JavaScriptメモ: 実数の小数点以下の切り上げ、切り捨て、整数への変換 (floor, ceil, round, toFixed)"
url: "p/92v7zx8/"
date: "2018-05-17"
lastmod: "2019-08-23"
tags: ["javascript"]
aliases:
  - /js/numstr/real-to-int.html
  - /js/numstr/tofixed.html
---

小数点以下を切り下げる (Math.floor)
----

**`Math.floor`** は、指定した実数を上回らない最大の整数を返します。

```js
Math.floor(3)     //=> 3
Math.floor(3.1)   //=> 3
Math.floor(-3)    //=> -3
Math.floor(-3.1)  //=> -4
```


小数点以下を切り上げる (Math.ceil)
----

**`Math.ceil`** は、指定した実数を下回らない最小の整数を返します。

```js
Math.ceil(3)     //=> 3
Math.ceil(3.1)   //=> 4
Math.ceil(-3)    //=> -3
Math.ceil(-3.1)  //=> -3
```


小数点以下を四捨五入する (Math.round)
----

**`Math.round`** は、小数点以下を四捨五入した整数を返します。

```js
Math.round(3)    //=> 3
Math.round(3.1)  //=> 3
Math.round(3.5)  //=> 4
Math.round(3.7)  //=> 4
```

負の値に対して実行した場合は、小数点以下が 0.5 の場合に切り上げになるので注意してください。
小数点以下が 0.5 に到達するたびに、大きい整数に切り上がると考えるとわかりやすいです。

```js
Math.round(-3)    //=> -3
Math.round(-3.1)  //=> -3
Math.round(-3.5)  //=> -3 （注意）
Math.round(-3.6)  //=> -4
```


小数点以下を削除する (parseInt)
----

**`parseInt`** は、小数点以下を無視して整数値を返します。

```js
parseInt(3.1)   //=> 3
parseInt(3.6)   //=> 3
parseInt(-3.1)  //=> -3
parseInt(-3.6)  //=> -3
```


数値の小数点以下 N 桁までに丸めて表示する (toFixed)
----

[`Number#toFixed(n)`](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Number/toFixed) メソッドを使用すると、数値の小数点以下 n 桁まで表示するための文字列（固定小数点表記）を生成できます。

```js
const num = 12.345;

console.log(num.toFixed());   // '12'
console.log(num.toFixed(0));  // '12'
console.log(num.toFixed(1));  // '12.3'
console.log(num.toFixed(2));  // '12.35'（四捨五入される）
console.log(num.toFixed(3));  // '12.345'
console.log(num.toFixed(4));  // '12.3450'（0 パディングされます）
console.log(num.toFixed(5));  // '12.34500'
```

もともとの数値が小数点以下 n 桁以上の情報を持っていない場合は、上記のように 0 パディングされた文字列が返されます。
つまり、`num.toFixed(5)` とすれば、必ず小数点以下が 5 桁である文字列を取得できます。

引数を省略すると、0 が指定されたのと同様に振る舞い、小数点以下を四捨五入した整数文字列を取得できます。
数値型のまま小数点以下を丸めた整数を取得したい場合は、`toFixed()` の代わりに、`floor()`、`ceil()`、`round()` を使用してください。

