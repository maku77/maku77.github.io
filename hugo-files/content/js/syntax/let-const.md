---
title: "JavaScriptメモ: 変数定義には var ではなく let や const を使用する"
url: "p/3qq8jdn/"
date: "2018-01-05"
description: "ECMAScript 2015 に対応した JavaScript では、var よりもスコープの狭い let や const を使用して変数定義を行うことができます。"
tags: ["javascript"]
aliases: [/js/syntax/let-const.html]
---

従来の JavaScript で変数定義をするには、`var` キーワードを使用するのが唯一の選択肢でした。
しかし、`var` による変数定義には、下記のような欠点がありました。

* 変数のスコープとして関数スコープしか存在せず、`if` ブロック内や `for` ループの中で定義した変数も、ブロック外からアクセスできてしまう。
* 同じ名前の変数を再度 `var` で定義できてしまい、保守性に問題がある。
* 再代入できない変数（定数）を定義することができない。

ECMAScript 2015 で導入された `let` や `const` を使用して変数（定数）を定義すると、これらの問題を解決することができます。
簡単に言うと、`var` の代わりに `let` や `const` を使用することで、Java や C++ と同じ感覚で変数のスコープや定数を扱うことができるようになります。

**ECMAScript 2015 に対応した JavaScript 環境では、`var` を使用せずに、`let` や `const` を使用することをお勧めします。**
**基本的には、変数が必要な場合はまず `const` を使用することを考え、再代入が必要なケースで `let` を使用するのがよいでしょう。**


変数のスコープがブロック内に限定される
----

`if` ブロックの中で `var` を使って定義した変数は、ブロックの外からも参照できてしまいます。
一方、`let` を使って変数を定義すれば、参照可能な範囲（スコープ）を同じブロック内に限定することができます。

```javascript
if (true) {
  var msg1 = 'Hello';
  let msg2 = 'World';
}
console.log(msg1);  //=> 'Hello'（参照できてしまう）
console.log(msg2);  //=> ReferenceError: msg2 is not defined
```

`for` ループで使用する一時的なインデックス変数も、`let` を使用して定義することで、ループ処理内のみにスコープを限定することができます。

```javascript
for (var i = 0; i < 3; ++i) {
  console.log(i);
}
console.log(i);  //=> 3（参照できてしまう）

for (let x = 0; x < 3; ++x) {
  console.log(x);
}
console.log(x);  //=> ReferenceError: x is not defined
```


再定義した場合にエラーになってくれる
----

`var` を使用して変数定義すると、すでに同じ名前で定義されている変数がある場合にも、上書きする形で再定義できてしまいます。
このような振る舞いは、思いがけない不具合の原因になります。

```javascript
var a = 100;
var a = 200;  // 再定義できてしまう
```

`let` を使用して変数定義すると、同じスコープ内で同じ名前の変数が定義されている場合にエラーになってくれます。

```javascript
let b = 100;
let b = 200;  // SyntaxError: Identifier 'b' has already been declared
```


再代入できない const 定数
----

`const` は `let` とほぼ同様に使用することができますが、`const` を使用して変数を定義すると、その変数には別の値を再代入できないようになります。
つまり、定数を定義することができます。
定数のスコープは `let` と同様です。

```javascript
const name = 'Maku';
console.log(name);  //=> 'Maku'

name = 'Hoge';  //=> TypeError: Assignment to constant variable.
```

`const` により定数定義を行う場合は、定義時に必ず初期値を指定しておく必要があります。

```javascript
const name;  //=> SyntaxError: Missing initializer in const declaration
```
