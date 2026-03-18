---
title: "Perlメモ: 配列の末尾の要素を取得する"
url: "p/bwyg8ye/"
date: "2008-02-28"
tags: ["perl"]
aliases: ["/perl/list/get-last-element.html"]
---

配列のインデックスとして `-1` を指定すると、配列の末尾の要素を参照することができます。

```perl
my @arr = ('AAA', 'BBB', 'CCC');
print $arr[-1];    # 'CCC'
```

また、配列の末尾のインデックスは `#$arr` で取得できるので、これを利用して次のように書くこともできます。

```perl
print $arr[#$arr];
```
