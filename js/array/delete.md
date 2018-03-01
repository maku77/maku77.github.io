---
title: ""
date: "2018-03-01"
---

JavaScript の連想配列から、指定したキーの要素を削除するには、`delete` 演算子を使用します。

~~~ javascript
var obj = {a:100, b:200, c:300};
delete obj['b'];   // あるいは delete obj.b でも OK
console.log(obj);  //=> { a: 100, c: 300 }
~~~

ちなみに、ここでは連想配列と呼んでいますが、JavaScript の連想配列は、すなわちオブジェクトのことです。
`delete` 演算子で連想配列の要素を削除するということは、言い換えると、オブジェクトのプロパティを削除するということです。

