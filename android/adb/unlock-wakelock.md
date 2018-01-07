---
title: ADB で WakeLock を強制的に外す
date: "2016-07-07"
---

`/sys/power/wake_unlock` に対して、WakeLock 名を書き込むと、その WakeLock を強制キャンセルすることができます。

現在取得されている WakeLock（カーネル・ウェイクロック）の一覧は下記のようにして調べることができます。

```
$ adb shell cat /sys/power/wake_lock
PowerManagerService PowerManagerService.Display PowerManagerService.WakeLocks
```

上記で表示された WakeLock 名を参考にして、下記のように `wake_unlock` に書き込みます。

```
$ adb shell "echo PowerManagerService > /sys/power/wake_unlock"
```

