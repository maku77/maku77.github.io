---
title: "JavaScriptメモ: 文字列の長さを取得する"
url: "p/f9anam8/"
date: "2012-01-20"
tags: ["javascript"]
aliases: /js/string/length.html
---

文字列の長さを調べるには、`length` プロパティを参照します。
マルチバイト文字も 1 文字としてカウントします。

```javascript
const a = 'ABCDE';
console.log(a.length);  // => 5

const b = 'あいうえお';
console.log(b.length);  // => 5
```
