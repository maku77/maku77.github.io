---
title: "インデックス指定で文字列を置換する (substr)"
date: "2008-04-13"
---

`substr` を左辺値 (lvalue) として使用すると、数値で位置指定した部分を変更することができます。
次の例では、文字列の先頭（インデックス = 0）から 3 文字分を 'Hoge' で置換します。

~~~ perl
my $s = 'ABCDE';
substr($s, 0, 3) = 'Hoge';
~~~

`index` と組み合わせて使用すると、ある文字を置換範囲の目印にして置換できます。
次の例では、文字列内の `:` の位置を検索し、`:` より後ろの文字列を変更します。

~~~ perl
my $str = 'aaa:bbb';
my $pos = index($str, ':');

if ($pos != -1) {
    substr($str, $pos + 1) = 'ccc';  # $str は 'aaa:ccc' になる
}
~~~

