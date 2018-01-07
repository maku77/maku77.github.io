---
title: タイムスタンプ（時刻）を取得する
date: "2011-06-28"
---

time 関数
----

~~~ cpp
#include <time.h>
time_t time(time_t *t)
~~~

* Epoch (00:00:00 UTC, January 1, 1970) からの「秒数」。
* 失敗した場合は -1 が返り、`errno` がセットされる。

clock 関数
----

~~~ cpp
#include <time.h>
clock_t clock(void);
~~~

* プログラムに使用した CPU（プロセッサ）時間を返す。
* 単位はシステムによって異なり、戻り値を `CLOCKS_PER_SEC` で割ると、秒数を求められる。この定数値は、Linux の場合 1,000,000、Windows の場合は 1000 になっている。ミリ秒を求めたい場合は、戻り値を `(CLOCKS_PER_SEC / 1000)` で割ればよい。
* 失敗した場合は -1 を返す。
* 実時間ではなく、CPU 時間を返す。つまり、他に重い処理が裏で走っている場合は実時間は長くなるが、そのプロセスに実際に使用した時間が短ければ `clock()` は短い時間を返す。
* `clock_t` が 32bit になる環境では、`clock()` の戻り値は約 72 秒で一周する。


gettimeofday
----

~~~ cpp
#include <sys/time.h>
int gettimeofday(struct timeval *tv, struct timezone *tz);
~~~

* `timeval` 構造体には、秒 (`tv_sec`)、マイクロ秒 (`tv_usec`)が格納される。
* 第二引数は obsolete なので NULL を指定する。
* 成功したら 0 を返す。失敗したら -1 を返し、`errno` をセットする。

ちなみに、`timeval` 構造体同士の演算を行うには、以下のようなマクロを使用します (see: `man timeradd`)。

~~~ cpp
#include <sys/time.h>
void timeradd(struct timeval *a, struct timeval *b, struct timeval *res);
void timersub(struct timeval *a, struct timeval *b, struct timeval *res);
void timerclear(struct timeval *tvp);
int timerisset(struct timeval *tvp);
int timercmp(struct timeval *a, struct timeval *b)
~~~

`timeval` の比較は `timercmp` マクロを使って以下のようにします。

~~~ cpp
if (timercmp(&a, &b, ==)) { ...等しい場合 ... }
if (timercmp(&a, &b, <)) { ... a < b の場合 ... }
if (timercmp(&a, &b, >)) { ... a > b の場合 ... }
~~~

