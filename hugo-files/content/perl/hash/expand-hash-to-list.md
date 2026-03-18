---
title: "Perlメモ: ハッシュをリストに展開する"
url: "p/y6hzywa/"
date: "2008-03-05"
tags: ["perl"]
aliases: ["/perl/hash/expand-hash-to-list.html"]
---

ハッシュはリストから作成できますが、逆にハッシュの内容をリストに展開することもできます。

```perl
my @arr = %hash;
```

上記の配列 `@arr` の内容は、ハッシュのキーと値が交互に並んだものになります。
キーがどのような順番で並ぶかは分かりません。
