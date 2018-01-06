---
title: "数値を３桁ごとにカンマを入れて表示する"
created: 2008-04-15
---

~~~ perl
sub add_comma {
    my ($num) = @_;
    1 while $num =~ s/^(-?\d+)(\d\d\d)/$1,$2/;
    $num;
}

print &add_comma(-1234567890), "\n";
~~~

#### 実行結果

~~~
-1,234,567,890
~~~

