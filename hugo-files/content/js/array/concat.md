---
title: "JavaScriptメモ: 配列と配列を結合する (...スプレッド演算子, concat)"
url: "p/r8isrjc/"
date: "2018-09-07"
lastmod: "2021-06-29"
tags: ["javascript"]
aliases: [/js/array/concat.html]
---

スプレッド構文を使う方法（ECMAScript 2018 以降）
----

配列リテラル `[]` で [スプレッド演算子 (...)](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Operators/Spread_syntax) を使うと、複数配列の要素をマージした配列を作成することができます。

```javascript
const arr1 = [1, 2, 3];
const arr2 = [4, 5, 6];
const merged = [...arr1, ...arr2];
console.log(merged);  // [1, 2, 3, 4, 5, 6]
```

配列のマージには、後述の `Array#concat()` を使う方法もありますが、スプレッド演算子を使う方法であれば任意の位置で既存の配列の要素を展開できます。

```javascript
const arr = [1, 2, 3];
const merged = [4, 5, ...arr, 6, 7];
console.log(merged);  // [4, 5, 1, 2, 3, 6, 7]
```


concat (push) を使う方法
----

`Array#concat()` メソッドを使用すると、ある配列に別の配列の要素を結合した配列を新しく作成することができます。
__自分自身の配列は変更されない__ ことに気をつけてください。
結合後の配列は戻り値として受け取る必要があります。

```javascript
const a = [1, 2, 3];
const b = a.concat([4, 5, 6]);
console.log(a);  // [1, 2, 3]
console.log(b);  // [1, 2, 3, 4, 5, 6]
```

上記では、`Array#concat()` メソッドのパラメータとして 1 つの配列を渡していますが、次のように個々の要素を複数のパラメータとして渡すこともできます。

```javascript
const a = [1, 2, 3];
const b = a.concat(4, 5, 6);  // 3つのパラメータとして渡す
console.log(b);  // [1, 2, 3, 4, 5, 6]
```

ここまでの例でも分かるように、__パラメータとして渡された配列は、1つ目の階層が展開された形で結合されます__。
ただし、2 階層より深い配列は展開されません。

```javascript
const a = [1, 2];
const b = a.concat(3, 4, [5, 6]);    // [ 1, 2, 3, 4, 5, 6 ]
const c = a.concat([3, 4], [5, 6]);  // [ 1, 2, 3, 4, 5, 6 ]
const d = a.concat([3, 4, [5, 6]]);  // [ 1, 2, 3, 4, [ 5, 6 ] ]  // 配列内の配列はそのまま結合される
```

逆にまったく配列を展開せずに、配列のまま結合したい場合は、`concat` の代わりに __`push`__ メソッドを使います。

```javascript
const a = [1, 2, 3];
const b = a.push([4, 5, 6]);
console.log(b);  //=> [ 1, 2, 3, [ 4, 5, 6 ] ]
```

