---
title: "ファイルをコピーする (copy)"
date: "2008-03-24"
---

Perl でファイルのコピーを行うには、`File::Copy` モジュールの `copy` 関数を使用します。

#### src.txt を dst.txt という名前で複製する

~~~ perl
use File::Copy;

copy('src.txt', 'dst.txt') or die "Cannot copy files: $!";
~~~

