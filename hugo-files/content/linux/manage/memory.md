---
title: "Linuxメモ: Linux のメモリ情報を調べるコマンド"
url: "p/2tumm9k/"
date: "2011-01-20"
tags: ["linux"]
aliases: /linux/memory.html
---

free コマンド
----

`free` コマンドを使用して、Linux システム全体の空きメモリ容量 (KByte) を調べることができます。
内部的には、`/proc/meminfo` の内容を整形して表示しています。

{{< code lang="console" title="実行例（Ubuntu 10.04 の場合）" >}}
$ free
             total       used       free     shared    buffers     cached
Mem:       4057716    3110204     947512          0     255708    2091224
-/+ buffers/cache:     763272    3294444★ ←実質的な空き容量はここを見る
Swap:     11884536      60600   11823936
{{< /code >}}

Linux では、ディスクアクセスの負荷軽減のために、空きメモリをバッファ領域（ファイルのメタ情報などを保持）とキャッシュ領域（ファイルの内容そのものをキャッシュ）に割り当てています。
上記の **Mem:** の行の **free** 値 `947512` は、空き容量からバッファ領域 (**buffers**) とキャッシュ領域 (**cached**) に割り当てた分を差し引いた値を示しているので、free の値はいつも小さくなります。
実質的な空き容量を求めるには、free の値に buffers と cached の分を足してやる必要があります。
その下の行の **-/+ buffers/cache:** というところがこの計算をした結果を示しており、一行目の `free`、`buffers`、`cached` をすべて足した、実質的な空き容量が表示されています。

```
3294444（実質 free） = 947512 (mem free) + 255708 (buffers) + 2091224 (cached)
```

### おまけ: free コマンドの help やオプション

下記は `free` コマンドのヘルプ説明です。

```
free - Display amount of free and used memory in the system
  free displays the total amount of free and used physical and swap memory
  in the system, as well as the buffers used by the kernel.
  The shared memory column should be ignored; it is obsolete.
```

`free` コマンドは、下記のようなオプションを組み合わせて使用することが多いです。

```console
$ free -m    # KByte ではなく MByte 単位で表示
$ free -s 5  # 5 秒間隔でポーリングして表示
```

- 参考: [Linuxトラブルシューティング探偵団　番外編（1）：減り続けるメモリ残量！ 果たしてその原因は!? (2/3) - ＠IT](http://www.atmarkit.co.jp/ait/articles/0810/01/news134_2.html)


/proc/meminfo を直接見る
----

`free` コマンドなどは、`proc/meminfo` の内容を整形して出力していますが、`proc/meminfo` を直接参照すると下記のような情報を取得することができます。

{{< code lang="console" title="実行例（Ubuntu 10.04 の場合）" >}}
$ cat /proc/meminfo
MemTotal:        4057720 kB
MemFree:         1622196 kB
Buffers:          337768 kB
Cached:          1236440 kB
SwapCached:         1008 kB
Active:           938236 kB
Inactive:         861756 kB
Active(anon):      26748 kB
Inactive(anon):   205540 kB
Active(file):     911488 kB
Inactive(file):   656216 kB
Unevictable:           0 kB
Mlocked:               0 kB
SwapTotal:      11884536 kB
SwapFree:       11883456 kB
Dirty:                 0 kB
Writeback:             0 kB
AnonPages:        225056 kB
Mapped:            76716 kB
Shmem:              6504 kB
Slab:             458988 kB
SReclaimable:     435412 kB
SUnreclaim:        23576 kB
KernelStack:        2768 kB
PageTables:        22020 kB
NFS_Unstable:          0 kB
Bounce:                0 kB
WritebackTmp:          0 kB
CommitLimit:    13913396 kB
Committed_AS:     630384 kB
VmallocTotal:   34359738367 kB
VmallocUsed:      336908 kB
VmallocChunk:   34359395836 kB
HardwareCorrupted:     0 kB
HugePages_Total:       0
HugePages_Free:        0
HugePages_Rsvd:        0
HugePages_Surp:        0
Hugepagesize:       2048 kB
DirectMap4k:      190436 kB
DirectMap2M:     4001792 kB
{{< /code >}}


procrank コマンド
----

`procrank` コマンドは、プロセスごとのメモリ使用状況を調べることができます。
下記の項目がよく参照されます。

PSS (Proportional Set Size)
: そのプロセスが実質的に使用しているメモリ容量です。複数のプロセスで共有しているメモリが比例分配されて計算されているため、すべてのプロセスの PSS を合計すれば、システム全体の使用量になります。

USS (Unique Set Size)
: そのプロセスのみが占有しているメモリ容量です。このサイズが時間とともに単調増加していくようですと、そのプロセスはメモリリークしているのだと判断できます。


vmstat コマンド
----

`vmstat` コマンドは、仮想メモリに関する統計情報を表示します。

> vmstat reports information about processes, memory, paging, block IO, traps, disks and cpu activity.  The first report produced gives averages since the last reboot.  Additional reports give information on a sampling period of length delay.  The process and memory reports are instantaneous in either case.

{{< code lang="console" title="実行例（Ubuntu 10.04 の場合）" >}}
$ vmstat
procs -----------memory---------- ---swap-- -----io---- -system-- ----cpu----
 r  b   swpd   free   buff  cache   si   so    bi    bo   in   cs us sy id wa
 0  0   1080 1621568 337804 1236444    0    0     3     4   21   33  0  0 100  0
{{< /code >}}

下記のようなオプションを指定すると、MByte 単位で、５秒間隔でポーリングして表示できます。

```
$ vmstat -SM 5
procs -----------memory---------- ---swap-- -----io---- -system-- ----cpu----
 r  b   swpd   free   buff  cache   si   so    bi    bo   in   cs us sy id wa
 0  0      1   1583    329   1207    0    0     3     4   21   33  0  0 100  0
```

`si`、`so` はスワップイン、スワップアウトを示しており、これが頻発する場合は、スラッシング（仮想メモリとのやりとりに CPU 処理時間のほとんどを割いてしまう状況）が発生している可能性があります。


pmap コマンド
----

`pmap` コマンドは、プロセスのメモリマップ情報を表示します。

