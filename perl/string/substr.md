---
title: "インデックス指定で文字列を抜き出す (substr)"
created: 2008-04-13
---

`substr` を使用すると、文字列中の部分文字列を位置指定で抽出することができます。

~~~ perl
# インデックス 2 から 3 文字分を抜き出す
my $sub = substr('abcdefg', 2, 3);  # sub は 'cde' になる

# インデックス 2 から末尾まで抜き出す
my $sub = substr('abcdefg', 2);  # sub は 'cdefg' になる
~~~

`index` と組み合わせて使用すれば、ある部分文字列から後ろの部分を抜き出すことができます。

~~~ perl
# コロン以降を抜き出す
my $sub = substr('AAA:BBB', index($s, ':'));  # $sub は ':BBB' になる
~~~

