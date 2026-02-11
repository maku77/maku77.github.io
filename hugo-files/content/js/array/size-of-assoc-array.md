---
title: "JavaScriptメモ: 連想配列の要素数を調べる"
url: "p/eqssz44/"
date: "2014-01-06"
tags: ["javascript"]
aliases: [/js/array/size-of-assoc-array.html]
---

JavaScript の連想配列（オブジェクト）は、配列と違って `length` プロパティで要素数を調べることができません。
以下の例では、一度、キーの配列を取得してから、その配列のサイズを調べています。

```javascript
const obj = {a:100, b:200, c:300};
const len = Object.keys(obj).length;
```

もちろん、以下のように地味に for ループでカウントすることもできます。

```javascript
let len = 0;
for (let item in obj) { ++len; }
```

