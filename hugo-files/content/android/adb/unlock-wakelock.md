---
title: "Androidメモ: ADB で WakeLock を強制的に外す"
url: "p/xx3mb8e/"
date: "2016-07-07"
tags: ["android"]
aliases: ["/android/adb/unlock-wakelock.html"]
---

**`/sys/power/wake_unlock`** に対して WakeLock 名を書き込むことで、その WakeLock を強制キャンセルすることができます。

現在取得されている WakeLock（カーネル・ウェイクロック）の一覧は下記のようにして調べることができます。

```console
$ adb shell cat /sys/power/wake_lock
PowerManagerService PowerManagerService.Display PowerManagerService.WakeLocks
```

上記で表示された WakeLock 名を参考にして、下記のように `wake_unlock` に書き込みます。

```console
$ adb shell "echo PowerManagerService > /sys/power/wake_unlock"
```

