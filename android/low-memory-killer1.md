---
title: LowMemoryKiller によるプロセス kill の優先順位の仕組み
created: 2011-09-07
layout: android
---

Android 独自の仕組みである **Low Memory Killer** は、空きメモリが一定以下になると、アプリプロセスを自動的に kill します。
実体は下記のカーネルに含まれているネイティブプログラムです。

* kernel/drivers/staging/android/lowmemorykiller.c

どのプロセスが kill されるかは、プロセスごとに設定された **adj** の値によって決まります。
この値が小さい方が優先度の高いプロセスであることを示し、Low Memory Killer によって殺されにくくなります。
メモリサイズと、adj の関連は、`init.rc` の中で以下のような感じで設定されます。

```
# Write value must be consistent with the above properties.
write /sys/module/lowmemorykiller/parameters/adj 0,1,2,3,4,5,6,7,14,15
write /sys/module/lowmemorykiller/parameters/minfree 2048,4096,8192,8192,16384,20000,25000,30000,35000,40000
```

上記の設定では、空きメモリが 8192 page (= 8192 * 4K = 32768K) 以下になったときに、adj が 3 以上であるプロセスが kill されることを示しています。
adj の具体的な値として、以下のようなものがあらかじめ定義されています (Honeycomb)。

| ActivityManagerService 内変数 | System property | Default | Description |
| ---- | ---- | ---- | ---- |
| SYSTEM_ADJ          |                       |-16|The system process runs at the default adjustment.|
| CORE_SERVER_ADJ     |                       |-12|This is a process running a core server, such as telephony. Definitely don't want to kill it, but doing so is not completely fatal.|
| FOREGROUND_APP_ADJ  |ro.FOREGROUND_APP_ADJ    |0|Foreground app|
| VISIBLE_APP_ADJ     |ro.VISIBLE_APP_ADJ       |1|Visible app|
| PERCEPTIBLE_APP_ADJ |ro.PERCEPTIBLE_APP_ADJ   |2|This is a process only hosting components that are perceptible to the user, and we really want to avoid killing them, but they are not immediately visible. An example is background music playback.|
| HEAVY_WEIGHT_APP_ADJ|ro.HEAVY_WEIGHT_APP_ADJ  |3|This is a process with a heavy-weight application. It is in the background, but we want to try to avoid killing it.|
| SECONDARY_SERVER_ADJ|ro.SECONDARY_SERVER_ADJ  |4|This is a process holding a secondary server -- killing tit will not have much of an impact as far as the user is concerned.|
| BACKUP_APP_ADJ      |ro.BACKUP_APP_ADJ        |5|This is a process currently hosting a backup operation. Killing it is not entirely fatal but is generally a bad idea.|
| HOME_APP_ADJ        |ro.HOME_APP_ADJ          |6|This is a process holding the home application -- we want to try avoiding killing it, even if it would normally be in the background, because the user interacts with it so much.|
| HIDDEN_APP_MIN_ADJ  |ro.HIDDEN_APP_MIN_ADJ    |7|This is a process only hosting activities that are not visible, so it can be killed without any disruption.|
|                     |ro.CONTENT_PROVIDER_ADJ |14||
| EMPTY_APP_ADJ       |ro.EMPTY_APP_ADJ        |15|This is a process without anything currently running in it. Definitely the first to go!|
| HIDDEN_APP_MAX_ADJ  |ro.EMPTY_APP_ADJ        |15||
| (GTV) |ro.CHROME_CRITICAL_OOM_ADJ|0||
| (GTV) |ro.CHROME_HIGH_OOM_ADJ|5||
| (GTV) |ro.CHROME_MEDIUM_OOM_ADJ|5||
| (GTV) | ro.CHROME_LOW_OOM_ADJ|6||


プログラム内で参照する adj に関するシステムプロパティは、`init.rc` で以下のような感じで設定されています。

```
on boot
    ...
    setprop ro.FOREGROUND_APP_ADJ 0
    setprop ro.VISIBLE_APP_ADJ 1
    setprop ro.PERCEPTIBLE_APP_ADJ 2
    setprop ro.HEAVY_WEIGHT_APP_ADJ 3
    setprop ro.SECONDARY_SERVER_ADJ 4
    setprop ro.BACKUP_APP_ADJ 5
    setprop ro.HOME_APP_ADJ 6
    setprop ro.HIDDEN_APP_MIN_ADJ 7
    setprop ro.CONTENT_PROVIDER_ADJ 14
    setprop ro.EMPTY_APP_ADJ 15
    ...
```

動作中のプロセスに対してどのような adj 値が割り当てられるかは、`com.android.server.am.ActivityManagerService` の `computeOomAdjLocked()` で計算されます。
このメソッドを呼び出した後に、パラメータで渡した `ProcessRecord` オブジェクトの内容（`curAdj`、`adjType` フィールドなど）をダンプすれば、そのプロセスがどのように評価されたかが分かります。

```java
Log.i("HOGE", "[" + i + "] " + app.processName + "(pid=" + app.pid + ", uid=" + app.info.uid + ")");
Log.i("HOGE", "        curAdj=" + app.curAdj + ", adjType=" + app.adjType +
              ", hidden=" + (app.hidden ? 1 : 0) + ", keeping=" + (app.keeping ? 1 : 0));
```

#### 出力結果

```
08-30 10:07:30.023   433   451 I HOGE    : [25] com.android.inputmethod.latin(pid=553, uid=10046)
08-30 10:07:30.023   433   451 I HOGE    :         curAdj=1, adjType=service, hidden=0, keeping=1
08-30 10:07:30.023   433   451 I HOGE    : [24] system(pid=433, uid=1000)
08-30 10:07:30.023   433   451 I HOGE    :         curAdj=-16, adjType=fixed, hidden=0, keeping=1
08-30 10:07:30.023   433   451 I HOGE    : [23] com.google.tv.launcher(pid=547, uid=10014)
08-30 10:07:30.023   433   451 I HOGE    :         curAdj=0, adjType=top-activity, hidden=0, keeping=1
08-30 10:07:30.023   433   451 I HOGE    : [22] com.google.tv.chrome(pid=1006, uid=1098)
08-30 10:07:30.023   433   451 I HOGE    :         curAdj=0, adjType=broadcast, hidden=0, keeping=1
```

