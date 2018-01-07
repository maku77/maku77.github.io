---
title: ある文字のコードポイントに対応するユニコード名を調べる
date: "2014-11-20"
---

Java 1.7 の Character クラスを使用すると、ユニコードポイントを指定して、対応する文字のユニコード名を調べることができます。
`Character.getName` は、対応する文字がない場合は `null` を返すことに注意してください。

~~~ java
System.out.println(Character.getName('a'));   // LATIN SMALL LETTER A
System.out.println(Character.getName('あ'));  // HIRAGANA LETTER A
System.out.println(Character.getName('国'));  // CJK UNIFIED IDEOGRAPHS 56FD
System.out.println(Character.getName('-'));   // HYPHEN-MINUS
System.out.println(Character.getName('+'));   // PLUS SIGN
System.out.println(Character.getName('.'));   // FULL STOP
System.out.println(Character.getName(';'));   // SEMICOLON
System.out.println(Character.getName('\t'));  // CHARACTER TABULATION
System.out.println(Character.getName('\\'));  // REVERSE SOLIDUS
System.out.println(Character.getName(' '));   // SPACE（半角スペース）
System.out.println(Character.getName('　'));  // IDEOGRAPHIC SPACE（全角スペース）
~~~

