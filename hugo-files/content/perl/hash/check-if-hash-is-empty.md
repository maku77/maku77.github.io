---
title: "Perlメモ: ハッシュが空かどうか調べる"
url: "p/v5wrm9m/"
date: "2008-03-02"
tags: ["perl"]
aliases: ["/perl/hash/check-if-hash-is-empty.html"]
---

ハッシュ変数をスカラーコンテキストで評価すると、ハッシュが空でないときに真 (true) となります。

```perl
if (%hash) {
    # ハッシュは空でない
}
```
