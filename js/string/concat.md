---
title: 文字列と文字列、数値を結合する
date: "2012-10-21"
---

文字列同士の結合
----

JavaScript の文字列は、`+` 演算子で結合できます。単項演算子の `+=` も使えます。

```javascript
var s1 = 'AAA' + 'BBB';
s1 += 'CCC';
print(s1);  //=> AAABBBCCC
```

文字列と数値の結合
----

文字列と数値を `+` 演算子で結合すると、文字列として結合されます。

```javascript
var val = 100 + '200';
print(val);         //=> 100200
print(typeof val);  //=> string
```

