---
title: "子プロセス（外部プログラム）への入出力を取り込む（パイプ）"
date: "2008-04-17"
---

子プロセスをパイプオープン (pipe open) すると、ファイルハンドル経由で子プロセスに対して入出力を行うことができます。
子プロセスの標準出力をファイルハンドル経由で取り込むには次のようにします。

#### 子プロセスの出力を取得する

~~~ perl
open NETSTAT, 'netstat |'
    or die "Cannot open process: $!";
while (<NETSTAT>) {
    chomp;
    print "--- $_\n";
}
close NETSTAT;
~~~

バッククォート文字列を使っても出力を得ることができますが、バッククォートを使った場合は、子プロセスが終了するまで処理がブロッキングされます。
パイプを使えば、子プロセスの出力を逐次読み出すことができます。

子プロセスの標準入力へ、ファイルハンドル経由で出力するには次のようにします。

#### 子プロセスへ入力する

~~~ perl
open SORT, '| sort'
    or die "Cannot open process: $!";
print SORT "BBB\n";
print SORT "AAA\n";
print SORT "CCC\n";
close SORT;
~~~

