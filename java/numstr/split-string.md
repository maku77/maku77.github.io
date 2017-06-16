---
title: 文字列をデリミタで分割する (String.split)
created: 2012-09-18
---

String クラスの `split` メソッドを使用すると、文字列を任意のデリミタ（区切り文字）で分割することができます。
デリミタとして使用する文字列は、正規表現で指定します。

~~~ java
public String[] split(String regex)
public String[] split(String regex, int limit)
~~~

#### 例: 空白文字で分割（ここでは先に trim() で前後の空白を削除）

~~~ java
String s = "  AAA  BBB CCC  ";
String[] arr = s.trim().split("\\s+");

for (int i = 0; i < arr.length; ++i) {
    System.out.println(arr[i]);
}
~~~

#### 実行結果

~~~
AAA
BBB
CCC
~~~

