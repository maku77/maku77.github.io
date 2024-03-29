---
title: "配列の要素を print で出力する"
date: "2008-02-28"
---

`print` の引数に配列変数をそのまま渡すと、要素を続けて表示してしまいます。

~~~ perl
my @arr1 = qw/ one two three /;
print @arr1;  #=> 'onetwothree'

my @arr2 = (100, 'ABC', 200);
print @arr2;  #=> '100ABC200'
~~~

配列変数を、ダブルクォート文字列として展開すると、各要素の間に特殊変数 `$"` の値を挟んだ文字列に展開してくれます。デフォルトでは `$"` の値はスペースになっているので、スペースを挟んだ文字列に展開されます。

~~~ perl
print "@arr\n";  # 'one two three'
~~~

配列の各要素を改行しながら表示したい場合は、次のようにすると簡潔に記述できます。

~~~ perl
print "$_\n" for @arr;
~~~

変数名とその後ろに続く文字の境界を示すには、変数名をブレース `{ }` で囲みます。

~~~ perl
my $food = 'apple';
print "I want some ${food}s.";
~~~

