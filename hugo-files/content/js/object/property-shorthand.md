---
title: "JavaScriptメモ: Property Shorthand の記法で複数の変数をひとつのオブジェクトにまとめる"
url: "p/ynbyx8w/"
date: "2019-06-18"
tags: ["javascript"]
aliases: [/js/object/property-shorthand.html]
---

従来の JavaScript では、下記のように 2 つのスカラー値（`a`、`b`）をひとつのオブジェクトにまとめるには、プロパティ名を明示的に記述する必要がありました。

```js
var a = 100;
var b = 'Hello';
var obj = { a: a, b: b };
```

変数名とプロパティ名が同じなので、この記述方法は冗長です。
ECMAScript 2015 (ES6) では、このような場合はプロパティ名を省略して記述できるようになりました。

```js
const a = 100;
const b = 'Hello';
const obj = { a, b }

console.log(obj);  //=> { a: 100, b: 'Hello' }
```
