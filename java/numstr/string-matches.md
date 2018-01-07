---
title: 文字列が正規表現に完全に一致するか調べる (String.matches)
date: "2005-05-25"
---

`String#matches(regex)` を使用すると、その文字列が指定した正規表現に完全に一致するかどうかを調べることができます。

#### 例: 文字列が Test で始まって Class で終わっているか調べる

~~~ java
String str = "TestBugtrackClass";
boolean match = str.matches("Test.*Class");  // true
~~~

`String#matches(regex)` は、`java.util.regex.Pattern.matches(regex, str)` と同じ結果を返します。

