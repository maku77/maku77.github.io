---
title: "JavaScriptメモ: 配列内にある要素が存在するか調べる (includes, indexOf)"
url: "p/qk75km2/"
date: "2017-10-22"
lastmod: "2020-04-09"
tags: ["javascript"]
aliases: [/js/array/has.html]
---

配列内に特定の要素が存在するかを調べるには以下のような方法を使います。

* `Array` クラスの __`includes()`__ メソッドを使う方法（ECMAScript 2016 以降）
* `Array` クラスの __`indexOf()`__ メソッドを使う方法（昔からある方法）
* jQuery の `$.inArray()` を使う方法


Array#includes を使う方法
----

ECMAScript 2016 以降では、[Array#includes() メソッド](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Array/includes) を使うのが直感的でよいです。
指定した要素が配列内に含まれているとき、このメソッドは `true` を返します。

```js
const arr = ['aaa', 'bbb', 'ccc', 'ddd'];
if (arr.includes('ccc')) {
    console.log('ccc が見つかりました');
}
```


Array#indexOf を使う方法
----

古くからある方法です。
[Array#indexOf() メソッド](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Array/indexOf) は、指定した要素が配列内のどのインデックス位置に存在するかを調べることができます。
要素が存在しない場合は `-1` を返すので、これを利用してある要素が配列内に存在するかを調べることができます。

```js
const arr = ['aaa', 'bbb', 'ccc', 'ddd'];
if (arr.indexOf('ccc') != -1) {
    console.log('ccc が見つかりました');
}
```


jQuery の $.inArray を使う方法
----

おまけ。

```js
const arr = ['aaa', 'bbb', 'ccc', 'ddd'];
if ($.inArray('ccc', arr)) {
    alert('ccc が見つかりました');
}
```

