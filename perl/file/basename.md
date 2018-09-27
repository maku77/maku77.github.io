---
title: "フルパス（絶対パス）からファイル名やディレクトリ名を抽出する (basename, dirname)"
date: "2008-04-05"
---

`File::Basename` モジュールの `basename` 関数、`dirname` 関数を使用すると、ファイルパスからファイル名に相当する部分、ディレクトリ名に相当する部分を抜き出すことができます。

~~~ perl
use File::Basename qw/ basename dirname /;

my $base = basename('/home/maku/sample.txt');  #=> 'sample.txt'
my $dir = dirname('/home/maku/sample.txt');    #=> '/home/maku'
~~~

