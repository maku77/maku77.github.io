---
title: "文字列の小文字と大文字を変換する (toLowerCase, toUpperCase)"
date: "2012-01-20"
---

String オブジェクトの `toLowerCase()` 関数、`toUpperCase()` 関数を使用すると、文字列全体を小文字、大文字に変換することができます。

~~~ javascript
var s = "Hello World";
console.log(s.toLowerCase());  // "hello world"
console.log(s.toUpperCase());  // "HELLO WORLD"
console.log(s);                // "Hello World"
~~~

あくまで変換後の結果が戻り値として返されるだけで、元の文字列は変更されないことに注意してください。
同じ変数で、変換後の文字列を参照したい場合は、次のように代入する必要があります。

~~~ javascript
var s = "Hello World";
s = s.toUpperCase();  // 変換後の文字列を代入
~~~

