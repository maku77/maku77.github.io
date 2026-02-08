---
title: "JavaScriptメモ: 2つのオブジェクトのプロパティをマージする（...スプレッド演算子、Object.assign）"
url: "p/3rofw5q/"
date: "2015-02-11"
lastmod: "2021-06-29"
tags: ["javascript"]
aliases: [/js/object/merge-properties.html]
---

スプレッド構文を使う方法（ECMAScript 2018 以降）
----

オブジェクトリテラル (`{}`) の中で、[スプレッド演算子 (...)](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Operators/Spread_syntax) を使うと、複数のオブジェクトのプロパティをマージしたオブジェクトを生成できます。
同じプロパティキーを持つ場合は、後ろに指定したもので上書きされます。

```javascript
const obj1 = { a: 1, b: 2 };
const obj2 = { b: 3, c: 4 };
const merged = { ...obj1, ...obj2 };  // { a: 1, b: 3, c: 4}
```

特定のプロパティを置き換えたオブジェクトを作りたいときは、次のように記述できます。

```javascript
const obj = { a: 1, b: 2 };
const merged = { ...obj, b: 3, c: 4 };  // { a: 1, b: 3, c: 4}
```

第 1 引数で指定した `obj` の内容が書き変わってしまうことはありません。

ちなみに、スプレッド演算子は、オブジェクトのプロパティを分解するときにも使えます。

```javascript
const obj = { a: 1, b: 2, c: 3, d: 4 };
const { a, b, ...rest } = obj;

console.log(a);     // 1
console.log(b);     // 2
console.log(rest);  // { c: 3, d: 4}
```


Object.assign を使う方法（ECMAScript 2015 (ES6) 以降）
----

[Object.assign 関数](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Object/assign) は、第 1 引数で指定したオブジェクトに、第 2 引数以降で指定したオブジェクトのプロパティをマージします。

```javascript
const obj1 = { a: 1, b: 2 };
const obj2 = { b: 3, c: 4 };
Object.assign(obj1, obj2);

console.log(obj1);  // { a: 1, b: 3, c: 4 }
console.log(obj2);  // { b: 3, c: 4 }
```

__第 1 引数で指定したオブジェクトの内容が書き換わる__ ことに注意してください。
複数のオブジェクトのプロパティをマージして新しいオブジェクトを作りたいときは、次のように第 1 引数で空のオブジェクト `{}` を渡して、その参照を戻り値として受け取ります。

```javascript
const obj1 = { a: 1, b: 2 }
const obj2 = { b: 3, c: 4 }
const merged = Object.assign({}, obj1, obj2)

console.log(merged)  // { a: 1, b: 3, c: 4 }
```


（おまけ）自力でマージ関数を実装する方法
----

下記の `extend` 関数は、第 1 引数で渡されたオブジェクトに、第 2 引数で渡されたオブジェクトのプロパティをすべてコピーします。
Prototype チェーンを辿ってプロパティをコピーしないようにするために、`hasOwnProperty()` を使い、第 2 引数で渡されたオブジェクト自身で定義されたプロパティのみをコピーしています。

```javascript
// Extend the first object with all the properties in the second object.
function extend(obj1, obj2) {
  for (key in obj2) {
    if (obj2.hasOwnProperty(key)) {
      obj1[key] = obj2[key];
    }
  }
  return obj1;
}

// 使用例
let a = {'A':1};
const b = {'B':2, 'C':3};
extend(a, b);
console.log(a);  //=> {'A':1, 'B':2, 'C':3}
```

下記は、参考にした underscore.js の `extend` 関数の抜粋です。

```javascript
// Extend a given object with all the properties in passed-in object(s).
_.extend = function(obj) {
  if (!_.isObject(obj)) return obj;
  var source, prop;
  for (var i = 1, length = arguments.length; i < length; i++) {
    source = arguments[i];
    for (prop in source) {
      if (hasOwnProperty.call(source, prop)) {
        obj[prop] = source[prop];
      }
    }
  }
  return obj;
};

// 上記の hasOwnProperty は、Object.prototype.hasOwnProperty
```
