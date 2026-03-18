---
title: "Perlメモ: ディレクトリ内のファイルの合計サイズを調べる"
url: "p/sg2p7d6/"
date: "2008-05-18"
tags: ["perl"]
aliases: ["/perl/file/dir-size.html"]
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

