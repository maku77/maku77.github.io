---
title: "分割代入によりオブジェクトの特定のプロパティだけを単独変数に取得する (Object destructuring)"
date: "2018-01-06"
---

オブジェクトの分割代入 (Object destructuring)
----

ECMAScript 2015 で追加された分割代入の構文を使用すると、あるオブジェクトの特定のプロパティのみを、それぞれ単独の変数で参照することができます。

下記の例では、`obj` のプロパティ `a`、`c` を、それぞれ同じ名前の変数 `a`、`c` に取得しています。

~~~ javascript
let obj = {a: 1, b: 2, c: 3};
let {a, c} = obj;

console.log(a);  //=> 1
console.log(c);  //=> 3
~~~

通常、変数の名前は、上記のようにオブジェクトのプロパティ名に合わせておく必要がありますが、次のようにすれば別名を付けることも可能です。

~~~ javascript
let obj = {a: 1, b: 2, c: 3};
let {a: aaa, c: ccc} = obj;

console.log(aaa);  //=> 1
console.log(ccc);  //=> 3
~~~

### 使用例1: 定数フィールドのショートカット

分割代入の仕組みを利用すると、あるクラスが提供している定数プロパティを、クラス名のプレフィックスを省略した形で参照できるようになります。
下記の例では、`Math.PI` 定数（円周率）を `PI` という短い名前で参照できるようにしています。

~~~ javascript
const { PI } = Math;
console.log(PI * 2);  //=> 6.283185307179586
~~~

### 使用例2: export されたプロパティを別々に参照

Node.js などの `require` で外部モジュールをロードする際にオブジェクト代入の仕組みを利用すると、公開 (export) されているプロパティをそれぞれ別の変数で参照することができます。

例えば、`mylib` モジュールが `Class1` と `Class2` を公開しているとします。
それらのクラスは通常、下記のように参照します。

~~~ javascript
const mylib = require('mylib');
let obj1 = new mylib.Class1();
let obj2 = new mylib.Class2();
~~~

ここで、`require` の戻り値のオブジェクトを受け取る際に、下記のように分割代入の仕組みを使用すると、export されたプロパティを別々の変数で参照することができます。

~~~ javascript
const { Class1, Class2 } = require('mylib');
let obj1 = new Class1();
let obj2 = new Class2();
~~~


参考サイト
----

- [MDN - Destructuring assignment - Object destructuring](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/Destructuring_assignment#Object_destructuring)
- [MDN - 分割代入 - オブジェクトの分割代入](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Operators/Destructuring_assignment#%E3%82%AA%E3%83%96%E3%82%B8%E3%82%A7%E3%82%AF%E3%83%88%E3%81%AE%E5%88%86%E5%89%B2%E4%BB%A3%E5%85%A5)

