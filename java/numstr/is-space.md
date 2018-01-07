---
title: ある文字が空白文字（スペース）かどうかを判別する
date: "2014-11-20"
---

ある文字（コードポイント）がスペースを表す文字かどうかを調べるには、Character クラスの下記のメソッドを使用できます。

~~~ java
Character.isWhitespace(char ch)
Character.isWhitespace(int codePoint)
Character.isSpaceChar(char ch)
Character.isSpaceChar(int codePoint)
~~~

`isWhitespace` の方は、改行文字やタブ文字を指定した場合にも `true` を返します。

~~~ java
// 半角スペース
Character.isWhitespace(' ');  // true
Character.isSpaceChar(' ');   // true

// 全角スペース
Character.isWhitespace('　');  // true
Character.isSpaceChar('　');   // true

// 水平タブ
Character.isWhitespace('\t');  // true
Character.isSpaceChar('\t');   // false

// 改行
Character.isWhitespace('\n');  // true
Character.isSpaceChar('\n');   // false
~~~

