---
title: "Java で Linux の CPU 使用率を調べる"
date: "2012-09-21"
---

ここでは、Linux の `/proc/stat` の出力情報を使って、CPU 使用率を求めてみます（Linux ベースの Android でも同じコードで取得できます）。
以下のような情報を取得するのを目標とします。

```
User:12.6%  Nice:0.0%  Sys:0.0%  Idle:87.4%
```


/proc/stat の出力について
====

Linux で `/proc/stat` を参照すると、最初の数行に CPU の使用状況が表示されます。

```
$ cat /proc/stat
cpu  2893427 5487 213189 221186084 163134 0 2616 0 0 0
cpu0 375908 2060 32886 27533420 96580 0 2506 0 0 0
cpu1 369684 938 25433 27650431 11890 0 48 0 0 0
cpu2 366153 462 34942 27646404 9092 0 30 0 0 0
cpu3 371330 1281 24558 27652952 8144 0 16 0 0 0
cpu4 350421 208 24267 27673190 13633 0 10 0 0 0
cpu5 354010 357 23104 27675956 8385 0 1 0 0 0
cpu6 352039 72 24314 27678106 7206 0 2 0 0 0
cpu7 353879 105 23681 27675622 8200 0 0 0 0 0
...
```

マルチコアの場合、２行目以降に、cpu0、cpu1、cpu2 のようにそれぞれの情報が表示されます。
１行目はこれらの値を合計したものになっていますので、ここでは１行目だけに注目することにします。

```
cpu  2893427 5487 213189 221186084 163134 0 2616 0 0 0
```

最初の 4 つの数字が、それぞれ、

- **user**: ユーザプログラムの実行に使用された CPU 時間
- **nice**: nice による優先度を処理に使用された CPU 時間
- **system**: kernel がカーネルが使用した CPU 時間
- **idle**: Idle 時間

を表しています。

**「CPU 使用時間」**を取得するには、user + nice + system を求めればよいでしょう。
**「CPU 使用率」**を取得するには、「CPU 使用時間」を上記４つの合計値で割ります。
これらの時間はシステムが起動してからの合計時間なので、ある一定期間内の CPU 使用時間を調べるには、２回 `/proc/stat` の値を見て、差分を取ってやる必要があります。


サンプルコードと実行結果
====

- [LinuxProcStat.java](parse-proc-stat/LinuxProcStat.java)
- [Main.java](parse-proc-stat/Main.java)

プログラムを実行すると、１秒おきに CPU 使用率を表示します。

#### 実行例

```
$ java Main
User:12.5%  Nice:0.0%  Sys:0.1%  Idle:87.4%
User:12.5%  Nice:0.0%  Sys:0.0%  Idle:87.5%
User:12.6%  Nice:0.1%  Sys:0.0%  Idle:87.3%
...
```
