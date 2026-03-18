---
title: "Perlメモ: 無名サブルーチンを定義する"
url: "p/qjckxyq/"
date: "2008-05-20"
tags: ["perl"]
aliases: ["/perl/subroutine/anonymous-subroutine.html"]
---

サブルーチンのリファレンスだけが必要な場合、名前のないサブルーチン（無名サブルーチン）を定義し、そのリファレンスを取得することができます。

```perl
my $ref = sub {
    my $name = shift;
    print "Hello, $name.\n";
};

$ref->('Jack');
```

無名サブルーチンは、サブルーチンのリファレンスが必要なところではいつでも使用できます。
