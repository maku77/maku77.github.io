---
title: "ハッシュが空かどうか調べる"
date: "2008-03-02"
---

ハッシュ変数をスカラーコンテキストで評価すると、ハッシュが空でないときに真 (true) となります。

```perl
if (%hash) {
    # ハッシュは空でない
}
```

