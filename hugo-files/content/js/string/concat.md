---
title: "JavaScriptメモ: 文字列と文字列、数値を結合する"
url: "p/egfww25/"
date: "2012-10-21"
tags: ["javascript"]
aliases: /js/string/concat.html
---

文字列同士の結合
----

JavaScript の文字列は、`+` 演算子で結合できます。単項演算子の `+=` も使えます。

```javascript
let s1 = 'AAA' + 'BBB';
s1 += 'CCC';
console.log(s1);  //=> AAABBBCCC
```

文字列と数値の結合
----

文字列と数値を `+` 演算子で結合すると、文字列として結合されます。

```javascript
const val = 100 + '200';
console.log(val);         //=> 100200
console.log(typeof val);  //=> string
```

これを利用したイディオムとして、次のように数値を文字列に変換する方法があります。

```javascript
const num = 100;
const str = num + '';
console.log(typeof str);  //=> string
```
