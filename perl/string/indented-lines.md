---
title: "インデントされた行、インデントされていない行を抜き出す"
date: "2008-05-22"
---

下記のサンプルコードは、テキスト中のインデントされた行と、インデントされていない行を抽出する例です。

~~~ perl
while (<>) {
    if (/^(\S.*)/) {
        my $head_val = $1;
        print "==> $head_val\n";
    } elsif (/^\s+(\S.*)/) {
        my $indented_val = $1;
        print "====> $indented_val\n";
    }
}
~~~

例えば以下のようなファイルを読み込むと、

~~~
Jack
    red
    blue
    yellow

Tom
    white
    black
~~~

次のような出力が得られます。空行や、空白文字だけの行は無視されます。

~~~
==> Jack
====> red
====> blue
====> yellow
==> Tom
====> white
====> black
~~~

参考: 『始めての Perl』（第３版）

