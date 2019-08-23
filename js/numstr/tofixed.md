---
title: "数値の小数点以下 N 桁までに丸めて表示する (toFixed)"
date: "2019-08-23"
---

[Number#toFloat(n)](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Number/toFixed) メソッドを使用すると、数値の小数点以下 n 桁まで表示するための文字列（固定小数点表記）を生成できます。

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
数値型のまま小数点以下を丸めた整数を取得したい場合は、`toFixed()` の代わりに、[floor()、ceil()、round() など](./real-to-int.html) を使用してください。

