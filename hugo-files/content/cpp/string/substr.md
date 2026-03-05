---
title: "C++メモ: 文字列から部分文字列を取得する (substr)"
url: "p/bsxaqvv/"
date: "2011-12-05"
tags: ["cpp"]
aliases: /cpp/string/substr.html
---

`string` クラスの `substr` 関数を使用すると、文字列の中からインデックス指定で部分文字列を抽出することができます。
2 番目のパラメータは、終了インデックスではなく、抜き出す文字数を指定することに注意してください。

```
substr(size_type pos = 0, size_type n = npos)
```

{{< code lang="cpp" title="例: １文字目から３文字分を抜き出す" >}}
string s1 = "ABCDEF";
string s2 = s1.substr(1, 3);  // "BCD"
{{< /code >}}

