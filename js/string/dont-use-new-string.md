---
title: new String は避ける
date: "2012-10-20"
---

JavaScript の文字列リテラル
----

シングルクォートあるいは、ダブルクォートで囲んだ文字列リテラルから、文字列値を生成することができます。
JavaScript の文字列変数は、基本的にこの形で生成すべきです。

```javascript
var s1 = 'xxx';
var s2 = "yyy";
console.log(typeof(s1));  // "string"
console.log(typeof(s2));  // "string"
```

シングルクォートとダブルクォートのどちらで囲むかによっては違いは出ませんが、例えばダブルクォートで囲んだ場合は、内部にエスケープなしでシングルクォート文字を含められます。

```javascript
var s1 = 'That\'s great!';
var s2 = "That's great!";
```

文字列を new String で作成するのは避ける
----

文字列を `new String` で作成すると、文字列の同値比較ができなってしまうので、文字列は必ず文字列リテラルで生成するようにすべきです。
これは、`new String` で生成した値の型が、`string` ではなく `object` 型になってしまうことが原因です。

#### object 同士の比較は false になる

```javascript
var s1 = new String('ABC');
var s2 = new String('ABC');
console.log(s1 == s2);   //=> false
console.log(s1 === s2);  //=> false
```

#### object と string の比較は == の場合のみ true になる

```javascript
var s1 = 'ABC';
var s2 = new String('ABC');
console.log(s1 == s2);   //=> true
console.log(s1 === s2);  //=> false
```

#### string 同士の比較は == でも === でも true になる（理想形）

```javascript
var s1 = 'ABC';
var s2 = 'ABC';
console.log(s1 == s2);   //=> true
console.log(s1 === s2);  //=> true
```

