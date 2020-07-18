---
title: "分割代入によりオブジェクトの特定のプロパティだけを単独変数に取得する (Object destructuring)"
date: "2018-01-06"
lastmod: "2020-07-18"
---

オブジェクトの分割代入 (Object destructuring)
----

ECMAScript 2015 で追加された分割代入の構文を使用すると、あるオブジェクトの特定のプロパティのみを、それぞれ単独の変数で参照することができます。
分割代入を効果的に使用すると、コードの見通しをよくすることができます。

下記の例では、`obj` のプロパティ `a`、`b` を、それぞれ同じ名前の変数 `a`、`b` に取得しています。

~~~ javascript
const obj = {a: 1, b: 2, c: 3};
const {a, b} = obj;

console.log(a);  //=> 1
console.log(b);  //=> 2
~~~

通常、変数の名前は、上記のようにオブジェクトのプロパティ名に合わせておく必要がありますが、次のようにすれば別名を付けることも可能です（`a` プロパティの値を変数 `newA`へ、`b` プロパティの値を変数 `newB` に代入しています）。

~~~ javascript
const obj = {a: 1, b: 2, c: 3};
const {a: newA, b: newB} = obj;

console.log(newA);  //=> 1
console.log(newB);  //=> 2
~~~

つまり、最初の例ででてきた分割代入は、下記のようなコードのショートカットとみなせます。

~~~ javascript
const obj = {a: 1, b: 2, c: 3};
const {a: a, b: b} = obj;  // const {a, b} = obj; と省略できる
~~~

分割代入時にデフォルト値を指定することもできます。
分割しようとしているオブジェクトに、プロパティが存在しない (`undefined`) 場合、デフォルト値が使用されます。

~~~ javascript
const obj = {a: 1, b: 2, c: 3};
const {a: newA, x: newX=999} = obj;

console.log(newA);  //=> 1
console.log(newX);  //=> 999
~~~


使用例: 定数フィールドのショートカット
----

分割代入の仕組みを利用すると、あるクラスが提供しているプロパティを、クラス名のプレフィックスを省略した形で参照できるようになります。
下記の例では、`Math.PI` 定数（円周率）を `PI` という短い名前で参照できるようにしています。

~~~ javascript
const {PI} = Math;
console.log(PI * 2);  //=> 6.283185307179586
~~~


使用例: for ループでのオブジェクト分割代入
----

分割代入は for ループでも使用できます。

~~~ javascript
const books = [
  {title: 'Title1', author: 'Author1'},
  {title: 'Title2', author: 'Author2'},
  {title: 'Title3', author: 'Author3'},
];

for (const {title, author} of books) {
  console.log(`${title}, ${author}`);
}
~~~


使用例: 複雑な構造のオブジェクトからプロパティを取り出す
----

分割代入は、複雑な構造のオブジェクトから必要なプロパティだけを個別の変数に取り出したいときに使用できます。
例えば、Web API などで取得した JSON データには、必要のないデータがたくさん含まれていたりしますが、そこから必要なプロパティだけを分割代入で取り出すと、後のコードがシンプルになります。

~~~ javascript
// 複雑な構造のデータ（例えば REST API で取得したデータなど）
const user = {
  id: 1,
  userName: 'Maku',
  avatar: {
    id: 'avatar123',
    url: 'http://example.com/sample.png',
  },
};

// 必要なプロパティだけを個別の変数に取り出す
const {
  userName,
  avatar: {
    id: avatarId
  }
} = user;

console.log(`${userName}, ${avatarId}`);  //=> Maku, avatar123
~~~

上記の例では、`avatar.id` の方のみ `avatarId` という別名の変数に取り出しています。


使用例: export されたプロパティを別々に参照
----

Node.js などの `require` で外部モジュールをロードする際にオブジェクト代入の仕組みを利用すると、公開 (export) されているプロパティをそれぞれ別の変数で参照することができます。

例えば、`mylib` モジュールが `Class1` と `Class2` を公開しているとします。
それらのクラスは通常、下記のように参照します。

~~~ javascript
const mylib = require('mylib');
const obj1 = new mylib.Class1();
const obj2 = new mylib.Class2();
~~~

ここで、`require` の戻り値のオブジェクトを受け取る際に、下記のように分割代入の仕組みを使用すると、export されたプロパティを別々の変数で参照することができます。

~~~ javascript
const {Class1, Class2} = require('mylib');
const obj1 = new Class1();
const obj2 = new Class2();
~~~


変数をオブジェクトにまとめる
----

複数の変数を 1 つのオブジェクトにまとめる場合、単純に記述すると次のようなコードになります。

~~~ javascript
// 変数 title、author を 1 つのオブジェクトにまとめる
const title = 'Title1';
const author = 'Author1';

const obj = {
  title: title,
  author: author
};
~~~

このように、変数名と同じ名前のプロパティを持つオブジェクトを生成する場合は、次のように省略して記述することができます（分割代入とは逆に、個々の変数をまとめる構文です）。

~~~ javascript
const title = 'Title1';
const author = 'Author1';

const obj = {title, author};
~~~


参考
----

- [配列の分割代入で複数の値を同時に代入する (Array destructuring)](../array/destructuring.html)
- [MDN - Destructuring assignment - Object destructuring](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/Destructuring_assignment#Object_destructuring)
- [MDN - 分割代入 - オブジェクトの分割代入](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Operators/Destructuring_assignment#%E3%82%AA%E3%83%96%E3%82%B8%E3%82%A7%E3%82%AF%E3%83%88%E3%81%AE%E5%88%86%E5%89%B2%E4%BB%A3%E5%85%A5)

