---
title: プリプロセッサで文字列を結合する
date: "2009-07-20"
---

~~~ cpp
#ifdef __STDC__
#  define CAT(a, b) a##b
#else
#  define CAT(a, b) a/**/b
#endif
~~~

ANSI 以前の `##` 演算子に対応していない C コンパイラでも、上記のように空のコメント `/**/` を利用すれば、マクロでトークンを結合できます。

