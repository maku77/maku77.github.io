---
title: "サンプル: スクリプトや設定ファイルのコメント（#以降）を削除する"
date: "2009-04-05"
---

#### sample.pl

~~~ perl
#!/usr/bin/perl

while (<>) {
    if (/^[^#]+$/) {
        # There is no '#' in this line.
        print;
    } elsif (/^([^#]+)#/) {
        # Some characters may exist before '#'.
        print "$1\n";
    }
}
~~~

#### 実行例

~~~
$ ./sample.pl < sample.pl

while (<>) {
    if (/^[^

        print;
    } elsif (/^([^

        print "$1\n";
    }
}
~~~

ありゃ、余計なところまで消えてる。。。このままだと使えないですね(^^;
少なくとも文字列リテラル（正規表現リテラル）の中の `#` は無視しなきゃですね。

