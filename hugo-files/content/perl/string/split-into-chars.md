---
title: "Perlメモ: 文字列を１文字ずつに分割する (split)"
url: "p/76ce72k/"
date: "2008-03-14"
tags: ["perl"]
aliases: ["/perl/string/split-into-chars.html"]
---

`split` 演算子の分割デリミタ文字として `//` を指定すると、文字列を各文字ごとに分割することができます。

```perl
$_ = 'Hello world';
my @arr = split //;  # ('H', 'e', 'l', 'l', 'o', ' ', 'w', 'o', 'r', 'l', 'd')
```
