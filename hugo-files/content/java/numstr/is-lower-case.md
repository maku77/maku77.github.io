---
title: "Javaメモ: 小文字と大文字の判別を行う (isLowerCase, isUpperCase)"
url: "p/97r9p79/"
date: "2014-11-20"
tags: ["java"]
aliases: ["/java/numstr/is-lower-case.html"]
---

ある文字が、大文字と小文字のどちらであるかを判別するには、`Character.isLowerCase()` や `Character.isUpperCase()` を使用します。

```java
Character.isLowerCase('a');  // true
Character.isLowerCase('A');  // false
Character.isUpperCase('a');  // false
Character.isUpperCase('A');  // true
```

