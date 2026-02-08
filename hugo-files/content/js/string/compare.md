---
title: "JavaScriptメモ: == と === による文字列比較の違い"
url: "p/squitgq/"
date: "2012-10-22"
tags: ["javascript"]
aliases: /js/string/compare.html
---

JavaScript における文字列の比較には、`==` あるいは `===` (strict equal) 演算子が使用できます。

```javascript
const str1 = 'ABC';
const str2 = 'AB' + 'C';
console.log(str1 == str2);   //=> true
console.log(str1 === str2);  //=> true
```

文字列と数値の比較
----

文字列値 (string) 同士の比較であれば、上記のように差は出ませんが、`==` を使用した比較は、型変換を行ってから比較を行うため、異なる型同士の比較をした場合に差が出てきます。

```javascript
const num = 100;
const str = '100';
console.log(num == str);   //=> true （型変換してから比較するため一致）
console.log(num === str);  //=> false（型が異なるので必ず false）
console.log(num != str);   //=> false
console.log(num !== str);  //=> true
```

型変換が伴うと、分かりにくい結果になりやすいので、**できる限り `===` (strict equal) による比較を行う**ことをお勧めします。

```javascript
console.log(123 == 'ABC123');   //=> false
console.log(123 == '123ABC');   //=> false
console.log(123 == '000123');   //=> true !!!!!

console.log(123 === 'ABC123');  //=> false
console.log(123 === '123ABC');  //=> false
console.log(123 === '000123');  //=> false
```

基本型の文字列と String オブジェクトの比較
----

基本型の文字列と、String オブジェクトの比較をする場合、`==` 演算子と `===` 演算子での比較に違いが出ます。

```javascript
const s = "text";
const obj = new String("text");

s == obj   // => true
s === obj  // => false !!!!!
```

保持しているテキストが同じならば、`==` 演算子は true を返しますが、`===` 演算子は型が違うので false を返します。

型の違いは `typeof` で確認できます。

```javascript
typeof s    // => "string"
typeof obj  // => "object"
```

このような分かりにくい振る舞いを抑制するためにも、**文字列を扱うときは String コンストラクタを使用せず、なるべく文字列リテラルを使用する**ようにしましょう。
