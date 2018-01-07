---
title: 小文字と大文字の判別を行う
date: "2014-11-20"
---

ある文字が、大文字と小文字のどちらであるかを判別するには、`Character.isLowerCase()` や `Character.isUpperCase()` を使用します。

~~~ java
Character.isLowerCase('a');  // true
Character.isLowerCase('A');  // false
Character.isUpperCase('a');  // false
Character.isUpperCase('A');  // true
~~~

