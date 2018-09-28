---
title: "プログラムの起動パラメータ (-t filename) などを処理する"
date: "2008-07-09"
---

下記の例では、Perl スクリプトを起動するときに渡されたコマンドラインパラメータをループ処理しています。

```perl
while ($_ = shift) {  # @ARGV から 1 つ取り出す
    if (/^-(.*)/) {
        process_option($1);  # オプション（-t など）のを処理
    } else {
        process_file($_);  # それ以外のパラメータはファイル名のはず
    }
}
```

参考: Effective Perl

