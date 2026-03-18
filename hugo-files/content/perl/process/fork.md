---
title: "Perlメモ: fork で子プロセスを作成する"
url: "p/m47kc5g/"
date: "2008-04-14"
tags: ["perl"]
aliases: ["/perl/process/fork.html"]
---

`fork` 関数を呼び出すと、`fork` システムコールによって子プロセスを作成することができます。
`fork` は次のような値を返します。

- <b>子プロセスID</b>: 親プロセス（プロセスを作る方）から見たときの戻り値
- <b>0</b>: 子プロセス（作られた方）から見たときの戻り値
- <b>undef</b>: `fork` に失敗したときの戻り値

{{< code lang="perl" title="fork によるプロセス生成の例" >}}
defined(my $pid = fork) or die "Cannot fork: $!";
unless ($pid) {
    # 子プロセスの処理
} else {
    # 親プロセスの処理
    waitpid($pid, 0);
}
{{< /code >}}
