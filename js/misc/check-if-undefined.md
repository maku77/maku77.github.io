---
title: ある変数が初期化済みかどうか確認する
created: 2012-12-02
---

変数 `a` が初期化されていることを判断するには、`typeof` を使って、以下のように判断します。
変数 `a` が初期化されていない場合は、変数 `b` にデフォルト値として `100` を設定しています。

```javascript
var b = (typeof a !== 'undefined') ? a : 100;
console.log(b);  //=> 100
```

先に変数を `a` を初期化しておくと、変数 `b` には `a` の値が代入されます。

```javascript
var a = 0;
var b = (typeof a !== 'undefined') ? a : 100;
console.log(b);  //=> 0
```

`typeof` を使わずに、直接 `undefined` 値と比較しようとすると、`a` が未定義のときに `ReferenceError` が発生してしまいます（Strict mode で動作する場合）。

以下の例は、変数 `a` の「定義」は行っていますが、「初期化」は行っていないので、結果として `b` の値は `100` になります。

```javascript
var a;  // これも未初期化
var b = (typeof a !== 'undefined') ? a : 100;
console.log(b);  //=> 100
```

