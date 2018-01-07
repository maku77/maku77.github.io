---
title: 数値と文字列の変換方法いろいろ
date: "2010-06-29"
---


int → 10 進数文字列
----

```java
int i = 123;
String s = String.valueOf(i);
```


int → 16 進数文字列
----

```java
int i = 0x0000ef1a;
System.out.println(Integer.toHexString(i));      // "ef1a"
System.out.println(String.format("%1$x", i));    // "ef1a"
System.out.println(String.format("%1$8x", i));   // "    ef1a"
System.out.println(String.format("%1$8X", i));   // "    EF1A"
System.out.println(String.format("%1$08x", i));  // "0000ef1a"
System.out.println(String.format("%1$08X", i));  // "0000EF1A"
```


10 進数文字列 → int
----

```java
String s = "123";
int i = Integer.parseInt(s);
```


int → 文字 (char)
----

```java
int i = 7;
char ch = (char)(i + '0');  // '7'（10進表記）
```

```java
int i = 10;
char ch = Character.forDigit(i, 16);  // 'a'（16進表記）
```

16 進表現の文字に変換したい場合は、単純に `'0'` を足すだけではダメなので、`Character.forDigit()` を使用します。


文字 (char) → int
----

#### `0` ～ `9` までの文字であることが保証されている場合

```java
char ch = '7';
int val = ch - '0';
```

#### 16進数の文字を int に変換する場合

```java
char ch = 'd';
int val = Character.digit(ch, 16);  // 16進表記 ==> 13
if (val == -1) {
    // Error
}
```

変換前の文字が `0` ～ `9` の範囲の文字であることが保証できるのであれば、単純に `0` を引くだけで int に変換することができます。
ただし、不正な文字（例えば `'z'` など）が渡された場合のエラー処理を行うために、通常は `Character.digit()` を使用して変換するのがよいでしょう。
`Character.digit()` は正しく変換できない文字を渡された場合に、-1 を返すようになっています。

16 進表記の文字を `int` に変換するときは、単純に `'0'` を引くという計算方法は使えないので、必ず `Character.digit()` を使用して変換することになります。

