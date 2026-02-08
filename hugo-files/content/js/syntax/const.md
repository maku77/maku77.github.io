---
title: "JavaScriptメモ: JavaScript で定数を定義する (const)"
url: "p/7wrpahv/"
date: "2012-10-27"
tags: ["javascript"]
aliases: [/js/syntax/const.html]
---

const による定数定義
----

ECMAScript 2015 に対応した JavaScript では、**`const`** キーワードを使用して定数（再代入できない変数）を定義することができます。

```javascript
const msg = 'Hello';
console.log(msg);  //=> 'Hello';

msg = 'World';  //=> TypeError: Assignment to constant variable.
```

`var` や `let` の代わりに `const` を使用して定義した変数に、別の値を代入しようとするとコンパイルエラーが発生します。
そのため、実質的に「定数」として扱うことができます。

変数を使用するときは、積極的に `const` を使って定義するようにし、必要な場合のみ `let` を使用するようにすると、保守性の高いコードを記述できるでしょう。


昔は const は使えなかった
----

（注: 下記は ES2015 より前の JavaScript に関する説明です。**ES2015 からは、正式に const キーワードを使用することができます**）

ECMAScript の規格には、定数を定義するための構文はありません。
JavaScript の独自実装として、定数を定義するための `const` キーワードが用意されていることがあります。

SpiderMonkey (FireFox) や v8 (Chrome) では `const` が使用できます。

```javascript
js> const HOGE = 100;
js> console.log(HOGE);
100
```

Rhino (jrunscript) では `const` は使用できません。

```javascript
$ jrunscript
js> const HOGE = 100;
js> print(HOGE);
undefined
```

いずれにしても、`const` は独自拡張なので、使わないほうがよいでしょう。
