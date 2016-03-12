---
title: ディレクトリ内のファイルの合計サイズを調べる
created: 2008-05-18
---

```perl
use File::Find;

my $total_size = 0;
sub sum_size {
    $total_size += -s if -f;
}

find(\&sum_size, '.');
print "Total size is $total_size\n";
```

