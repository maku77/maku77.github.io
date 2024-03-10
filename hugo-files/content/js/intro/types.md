---
title: "JavaScript の 6 つの型"
url: "p/wur8gmp/"
date: "2012-10-28"
tags: ["JavaScript"]
aliases: /js/intro/types.html
---

JavaScript is a "Loosely Typed Language"
----

JavaScript は弱い型付け言語 (A Loosely Typed Language) です。
変数の宣言時に、型を指定する必要はありませんが、内部的には型情報を保持しています。

```js
const s = 'Hello World';  // 型指定はしていないが、内部的に文字列型として保持される
print(typeof s);        // => string
```


JavaScript の型は 6 種類
----

JavaScript の型には 5 つの基本型 (primitive value) と、プロパティを持つことができるオブジェクト型があります。
ようするに、全部で 6 つの型があります。

* 基本型
  * **`number`（数値）** -- 64 bit 浮動小数点数
  * **`string`（文字列）**
  * **`boolean`（真偽値）** -- true or false
  * **`null`** -- `null` だけが存在する型
  * **`undefined`** -- 未定義を表す
* **オブジェクト型**

5 つの基本型は、ECMAScript の仕様書では、**primitive value** と呼ばれています。
JavaScript には、C/C++ や Java 言語における char のような 1 文字を格納するための型は存在しないため、長さ 1 の `string` で代用することになります。
配列に関してはオブジェクト型（`Array` オブジェクト）に分類されています。
日付を表す `Date` などもすべてオブジェクト型です。


型情報を調べる (typeof)
----

変数が参照している値の型を調べるには、__`typeof`__ を使用します。
リテラルの型を調べることもできます。

```js
print(typeof 100);    //=> number
print(typeof 'xyz');  //=> string
print(typeof true);   //=> boolean
print(typeof null);   //=> object
print(typeof {});     //=> object
print(typeof undefined);  //=> undefined
```

`null` の型が、なぜか `object` になることに注意が必要です。

