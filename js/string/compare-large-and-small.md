---
title: 文字列を大小比較すると何が起こるか
created: 2012-10-24
---

文字列同士を `>`, `<`, `>=`, `<=` などで大小比較すると、Unicode のコードポイントベースで大小比較されます。

```javascript
console.log(50 > 100);      // false
console.log('50' > '100');  // true !!
```

`'5'` という文字のコードポイントは `'1'` という文字のコードポイントより大きいので、結果的に `'50'` という文字列は `'100'` より大きいと判断されます。
数値の比較にはならないことに注意してください。
