---
title: "fork で子プロセスを作成する"
date: "2008-04-14"
---

`fork` 関数を呼び出すと、`fork` システムコールによって子プロセスを作成することができます。
`fork` は次のような値を返します。

- <b>子プロセスID</b>: 親プロセス（プロセスを作る方）から見たときの戻り値
- <b>0</b>: 子プロセス（作られた方）から見たときの戻り値
- <b>undef</b>: `fork` に失敗したときの戻り値

#### fork によるプロセス生成の例

~~~ perl
defined(my $pid = fork) or die "Cannot fork: $!";
unless ($pid) {
    # 子プロセスの処理
} else {
    # 親プロセスの処理
    waitpid($pid, 0);
}
~~~

