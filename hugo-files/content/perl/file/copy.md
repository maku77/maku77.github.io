---
title: "Perlメモ: ファイルをコピーする (copy)"
url: "p/kah7dwo/"
date: "2008-03-24"
tags: ["perl"]
aliases: ["/perl/file/copy.html"]
---

Perl でファイルのコピーを行うには、`File::Copy` モジュールの `copy` 関数を使用します。

{{< code lang="perl" title="src.txt を dst.txt という名前で複製する" >}}
use File::Copy;

copy('src.txt', 'dst.txt') or die "Cannot copy files: $!";
{{< /code >}}

