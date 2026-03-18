---
title: "Perlメモ: サブルーチンがリストコンテキストで呼び出されたか調べる"
url: "p/zaj6axe/"
date: "2008-02-10"
tags: ["perl"]
aliases: ["/perl/subroutine/want-array.html"]
---

`wantarray` 関数を使うと、そのサブルーチンがリストコンテキストで呼び出されたかどうかを調べることができます。

```perl
sub hoge {
    return wantarray ? (100, 200, 300) : 3;
}
```
