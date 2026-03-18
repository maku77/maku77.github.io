---
title: "Perlメモ: プログラムの起動パラメータ (-t filename) などを処理する"
url: "p/4gj2ve4/"
date: "2008-07-09"
tags: ["perl"]
aliases: ["/perl/misc/command-line-params.html"]
---

下記の例では、Perl スクリプトを起動するときに渡されたコマンドラインパラメータをループ処理しています。

```perl
while ($_ = shift) {  # @ARGV から 1 つ取り出す
    if (/^-(.*)/) {
        process_option($1);  # オプション（-t など）を処理
    } else {
        process_file($_);  # それ以外のパラメータはファイル名のはず
    }
}
```

参考: Effective Perl
