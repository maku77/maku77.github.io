---
title: "Javaメモ: コードポイントに対応するユニコード文字 (char) を取得する"
url: "p/9srjnk4/"
date: "2014-11-20"
tags: ["java"]
aliases: ["/java/numstr/code-point-to-char.html"]
---

下記は、ひらがなの「あ」を表すコードポイントから、char 変数を取得する方法です。

```java
char ch1 = '\u3042';  // 文字リテラルで指定する方法（シングルクォートであることに注意）
char ch2 = (char) 0x3042;  // 数値で指定する方法
```

