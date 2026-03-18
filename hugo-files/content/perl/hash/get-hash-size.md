---
title: "Perlメモ: ハッシュのサイズを取得する"
url: "p/vjdyacs/"
date: "2008-03-02"
tags: ["perl"]
aliases: ["/perl/hash/get-hash-size.html"]
---

`keys` 関数をスカラーコンテキストで評価すると、ハッシュのキーの数、つまり、ハッシュの要素数を取得することができます。

```perl
my $n = keys %hash;
```

Perl はキーの数を調べるために、内部でキーのリストを作成したりせずに、効率よく実行できるように実装されています。
