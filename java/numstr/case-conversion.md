---
title: 小文字と大文字の変換を行う
date: "2014-11-20"
---

Character クラスの `toUpperCase()`/`toLowerCase()` を使用すると、指定した文字（あるいはコードポイント）を大文字、小文字に変換することができます。

~~~ java
System.out.println(Character.toUpperCase('a')); // A
System.out.println(Character.toUpperCase('A')); // A
System.out.println(Character.toLowerCase('a')); // a
System.out.println(Character.toLowerCase('A')); // a
~~~

指定された文字に対応する大文字、小文字が存在しない場合は、入力をそのまま返します。

文字単位ではなく、文字列全体を大文字、小文字に変換したい場合は、String クラスのメンバメソッドを使用するのが簡単です。

~~~ java
System.out.println("abcABC".toUpperCase()); // ABCABC
System.out.println("abcABC".toLowerCase()); // abcabc
~~~

