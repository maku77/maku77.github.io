---
title: "Perlメモ: 数値を３桁ごとにカンマを入れて表示する"
url: "p/662ksxe/"
date: "2008-04-15"
tags: ["perl"]
aliases: ["/perl/string/number-with-comma.html"]
---

```perl
sub add_comma {
    my ($num) = @_;
    1 while $num =~ s/^(-?\d+)(\d\d\d)/$1,$2/;
    $num;
}

print &add_comma(-1234567890), "\n";
```

{{< code title="実行結果" >}}
-1,234,567,890
{{< /code >}}
