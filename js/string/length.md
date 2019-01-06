---
title: "文字列の長さを取得する"
date: "2012-01-20"
---

文字列の長さを調べるには、`length` プロパティを参照します。
マルチバイト文字も 1 文字としてカウントします。

```javascript
var a = 'ABCDE';
console.log(a.length);  // => 5

var b = 'あいうえお';
console.log(b.length);  // => 5
```

