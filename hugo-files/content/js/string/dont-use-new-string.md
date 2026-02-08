---
title: "JavaScriptメモ: new String は避ける"
url: "p/is2iir3/"
date: "2012-10-20"
tags: ["javascript"]
aliases: /js/string/dont-use-new-string.html
---

JavaScript の文字列リテラル
----

シングルクォートあるいは、ダブルクォートで囲んだ文字列リテラルから、文字列値を生成することができます。
JavaScript の文字列変数は、基本的にこの形で生成すべきです。

```javascript
const s1 = 'xxx';
const s2 = "yyy";
console.log(typeof(s1));  // "string"
console.log(typeof(s2));  // "string"
```

シングルクォートとダブルクォートのどちらで囲むかによっては違いは出ませんが、例えばダブルクォートで囲んだ場合は、内部にエスケープなしでシングルクォート文字を含められます。

```javascript
const s1 = 'That\'s great!';
const s2 = "That's great!";
```

文字列を new String で作成するのは避ける
----

文字列を `new String` で作成すると、文字列の同値比較ができなくなってしまうので、文字列は必ず文字列リテラルで生成するようにすべきです。
これは、`new String` で生成した値の型が、`string` ではなく `object` 型になってしまうことが原因です。

{{< code lang="js" title="object 同士の比較は false になる" >}}
const s1 = new String('ABC');
const s2 = new String('ABC');
console.log(s1 == s2);   //=> false
console.log(s1 === s2);  //=> false
{{< /code >}}

{{< code lang="js" title="object と string の比較は == の場合のみ true になる" >}}
const s1 = 'ABC';
const s2 = new String('ABC');
console.log(s1 == s2);   //=> true
console.log(s1 === s2);  //=> false
{{< /code >}}

{{< code lang="js" title="string 同士の比較は == でも === でも true になる（理想形）" >}}
const s1 = 'ABC';
const s2 = 'ABC';
console.log(s1 == s2);   //=> true
console.log(s1 === s2);  //=> true
{{< /code >}}
