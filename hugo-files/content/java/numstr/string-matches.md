---
title: "Javaメモ: 文字列が正規表現に完全に一致するか調べる (`String.matches`)"
url: "p/zanxxvq/"
date: "2005-05-25"
tags: ["java"]
aliases: ["/java/numstr/string-matches.html"]
---

**`String#matches(regex)`** を使用すると、その文字列が指定した正規表現に完全に一致するかどうかを調べることができます。

{{< code lang="java" title="例: 文字列が Test で始まって Class で終わっているか調べる" >}}
String str = "TestBugtrackClass";
boolean match = str.matches("Test.*Class");  // true
{{< /code >}}

`String#matches(regex)` は、`java.util.regex.Pattern.matches(regex, str)` と同じ結果を返します。

