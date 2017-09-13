---
title: "文字列から部分文字列を取得する (substr)"
created: 2011-12-05
---

`string` クラスの `substr` 関数を使用すると、文字列の中からインデックス指定で部分文字列を抽出することができます。
２番目のパラメータは、終了インデックスではなく、抜き出す文字数を指定することに注意してください。

~~~
substr(size_type pos = 0, size_type n = npos)
~~~

#### 例: １文字目から３文字分を抜き出す

~~~ cpp
string s1 = "ABCDEF";
string s2 = s1.substr(1, 3);  // "BCD"
~~~

