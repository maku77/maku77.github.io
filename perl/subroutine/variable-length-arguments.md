---
title: "サブルーチンを可変長引数に対応させる"
date: "2008-01-29"
---

Perl のサブルーチンは、引数を `@_` という配列変数で受け取るので、そもそも最初から可変長引数の受け取りに対応しています。
あとは、`foreach` (`for`) ループなどで `@_` 内の要素を順に処理するだけです。

```perl
# リストで与えられた数値の合計を求める
sub sum {
    my $result;
    $result += $_ for @_;
    $result;
}

print &sum(1..10);  #=> 55
```

