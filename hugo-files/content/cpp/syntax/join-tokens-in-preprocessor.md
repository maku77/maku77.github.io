---
title: "C++メモ: プリプロセッサで文字列を結合する"
url: "p/d9hc2ee/"
date: "2009-07-20"
tags: ["cpp"]
aliases: /cpp/syntax/join-tokens-in-preprocessor.html
---

```cpp
#ifdef __STDC__
#  define CAT(a, b) a##b
#else
#  define CAT(a, b) a/**/b
#endif
```

ANSI 以前の `##` 演算子に対応していない C コンパイラでも、上記のように空のコメント `/**/` を利用すれば、マクロでトークンを結合できます。

