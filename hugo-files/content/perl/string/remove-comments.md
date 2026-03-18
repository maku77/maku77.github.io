---
title: "Perlメモ: サンプル: スクリプトや設定ファイルのコメント（#以降）を削除する"
url: "p/sjpcor9/"
date: "2009-04-05"
tags: ["perl"]
aliases: ["/perl/string/remove-comments.html"]
---

{{< code lang="perl" title="sample.pl" >}}
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
{{< /code >}}

{{< code title="実行例" >}}
$ ./sample.pl < sample.pl

while (<>) {
    if (/^[^

        print;
    } elsif (/^([^

        print "$1\n";
    }
}
{{< /code >}}

ありゃ、余計なところまで消えてる。。。このままだと使えないですね(^^;
少なくとも文字列リテラル（正規表現リテラル）の中の `#` は無視しなきゃですね。
