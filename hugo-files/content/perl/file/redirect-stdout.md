---
title: "Perlメモ: 標準出力 (STDOUT) の出力先をファイルに切り替える"
url: "p/pgucgep/"
date: "2008-07-08"
tags: ["perl"]
aliases: ["/perl/file/redirect-stdout.html"]
---

`open` 演算子を下記のように使用すると、`print` などで標準出力へ出力した内容をファイルに書き込むことができるようになります。

{{< code lang="perl" title="例: 標準出力の出力先を output.txt に切り替える" >}}
#!/bin/perl

open STDOUT, ">output.txt" or die;
print "Hello";
print "World";
close STDOUT;
{{< /code >}}

{{< code title="実行結果" >}}
$ ./sample.pl
$ cat output.txt
HelloWorld
{{< /code >}}

