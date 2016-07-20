---
title: 配列をループで処理する
created: 2012-01-17
---

for ループを使う方法
----

```javascript
var arr = ['AAA', 'BBB', 'CCC'];
for (var i = 0; i < arr.length; ++i) {
  console.log(arr[i]);
}
```

JavaScript での配列のループ処理としてはこれが一番基本的な方法です。
`for ... in` によるループを行ってしまうと、オブジェクトのプロパティの列挙になってしまうので、上記のように愚直にインデックスで回すのが正解です。


ECMA-262 標準の Array.prototype.forEach を使う方法
----

```javascript
var arr = ['AAA', 'BBB', 'CCC'];
arr.forEach(function(val, i) {
   ...
});
```

参考: [Array.prototype.forEach() - JavaScript MDN](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Array/forEach)


jQuery の $.each を使う方法
----

```javascript
var arr = ['AAA', 'BBB', 'CCC'];
$.each(arr, function(index, item) {
  console.log(index + ': ' + item);
});
```

#### 実行結果
```
0: AAA
1: BBB
2: CCC
```

Underscore.js の _.each を使う方法
----

```javascript
var arr = ['AAA', 'BBB', 'CCC'];
_.each(arr, function(item, index) {
  console.log(index + ': ' + item);
});
```

jQuery の `each` 関数と、Underscore.js の `each` 関数は、パラメータの順番が違うので要注意です。

