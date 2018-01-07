---
title: オブジェクトを明示的に破棄する
date: "2008-06-23"
---

オブジェクトを明示的に破棄するには、bless されたリファレンス変数に対して `undef` を代入します。

```perl
{
    package MyClass;
    sub new { bless {}, shift; }
    sub DESTROY { print "Destruction.\n"; }
}

my $obj = MyClass->new;
$obj = undef;  # $obj->DESTROY が呼び出される
print "End of program.\n";
```

