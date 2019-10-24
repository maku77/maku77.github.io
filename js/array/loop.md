---
title: "配列をループで処理する (for, forEach, for-of)"
date: "2012-01-17"
lastmod: "2019-02-24"
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

ECMAScript 2015 以降では `var` ではなく `let` を使ってループ変数 `i` のスコープを絞りましょう。


forEach を使う方法 (ECMAScript 2015)
----

```javascript
var arr = ['AAA', 'BBB', 'CCC'];
arr.forEach(function(elem, index) {
  console.log(index + ': ' + elem);
});
```

参考: [Array.prototype.forEach() - JavaScript MDN](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Array/forEach)


for-of を使う方法 (ECMAScript 2015)
----

```javascript
var arr = ['AAA', 'BBB', 'CCC'];

for (const elem of arr) {
  console.log(elem);
}
```

- 参考: [for...of - JavaScript｜MDN](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Statements/for...of)


jQuery の $.each を使う方法
----

```javascript
var arr = ['AAA', 'BBB', 'CCC'];
$.each(arr, function(index, elem) {
  console.log(index + ': ' + elem);
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
_.each(arr, function(elem, index) {
  console.log(index + ': ' + elem);
});
```

jQuery の `each` 関数と、Underscore.js の `each` 関数は、パラメータの順番が違うので要注意です。

