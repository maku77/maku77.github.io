---
title: "Perlメモ: SIGINT シグナル (Ctrl-C) をハンドリングする"
url: "p/8po2yyt/"
date: "2008-04-16"
tags: ["perl"]
aliases: ["/perl/process/sigint.html"]
---

`%SIG` ハッシュに対してサブルーチンの名前を登録すると、そのサブルーチンがシグナルハンドラとして呼び出されるようになります。
次の例では、`SIGINT` シグナルに対するシグナルハンドラを設定しています。

```perl
sub sigint_handler {
    print "SIGINT\n";
}
$SIG{'INT'} = 'sigint_handler';
```

一般的なシェルで <kbd>Ctrl-C</kbd> を入力すると、プロセスに対して `SIGINT` が送られるようになっています。
デフォルトでは、`SIGINT` を受信したプロセスは終了するようになっていますが、上記のようにシグナルハンドラを登録しておけば、代替の処理を行えるようになります。
