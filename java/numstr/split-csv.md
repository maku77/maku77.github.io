---
title: "CSV 形式の文字列を配列に分割する (String.split)"
date: "2005-05-25"
---

`String#split()` を使用して、文字列をデリミタで区切ることができます。
下記の例では、CSV 形式の文字列をカンマで区切って配列データとして取得しています。

~~~ java
String str = "aaa, bbb, ccc";
String[] arr = str.split("\\s*,\\s*");

for (String token: arr) {
    System.out.println(token);
}
~~~

#### 実行結果

~~~
aaa
bbb
ccc
~~~

ここでは、カンマの前後のスペース (`\\s*`) までデリミタ文字列に含めることで、分割後の各文字列から、余計なスペースを削除するようにしています。
エスケープシーケンスの `\` 記号は、Java の文字列リテラルの中ではエスケープして `\\` にするのがポイントです。
例えば、`\b`（単語境界）は `\\b` のように記述しなければいけません。

参考
----

* [文字列をデリミタで分割する (String.split, Pattern.split)](split-string.html)

