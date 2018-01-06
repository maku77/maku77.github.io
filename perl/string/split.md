---
title: "文字列をデリミタで分割する (split)"
created: 2008-03-14
---

`split` 演算子を使うと、文字列を指定したデリミタで分割してリストにすることができます。

~~~ perl
my $line = 'aaa:bbb:ccc';
my @arr = split /:/, $line;  # ('aaa', 'bbb', 'ccc')
~~~

デリミタに囲まれた部分にテキストが存在しない場合は、そこには空文字の要素があるものとしてリストが作成されます。
ただし、末尾の空文字列要素は削除されます（処理の効率化のため）。

~~~ perl
my $line = ':aaa::bbb:ccc:::';
my @arr = split /:/, $line;  # ('', 'aaa', '', 'bbb', 'ccc');
~~~

リストの末尾の空文字列要素を削除しないようにするには、`split` の第３引数に -1 を指定します。

~~~ perl
my $line = ':aaa::bbb:ccc:::';
my @arr = split /:/, $line, -1;  # ('', 'aaa', '', 'bbb', 'ccc', '', '', '');
~~~

空白文字（スペース、タブ、改行）で文字を分割するには、例えば次のようにします。

~~~ perl
my $line = '   aaa  bbb  \t  ccc  ';
my @arr = split /\s+/, $line;  # ('', 'aaa', 'bbb', 'ccc');
~~~

ただし、このようにすると、先頭に空白文字があった場合に空文字の要素が作られてしまいます。
この先頭の空文字要素を作らないようにする特殊なショートカットとして、次のように半角スペース１文字をデリミタにする方法が用意されています。

~~~ perl
my $line = '   aaa  bbb  \t  ccc  ';
my @arr = split ' ', $line;  # ('aaa', 'bbb', 'ccc');
~~~

`split` を引数なしで呼び出した場合は、`$_` に対して同様の処理が行われます。

~~~ perl
my $_ = '   aaa  bbb  \t  ccc  ';
my @arr = split;  # ('aaa', 'bbb', 'ccc');
~~~

文字クラスを使えば、複数のデリミタで区切ることもできます。

~~~ perl
$_ = "aaa,bbb-ccc/ddd";
my @arr = split /[,\-\/]/;  # ('aaa', 'bbb', 'ccc', 'ddd');
~~~

