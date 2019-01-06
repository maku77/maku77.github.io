---
title: "ADB でスリープ状態に入る／スリープから抜ける"
date: "2016-07-07"
---

#### スリープ状態に入る

```
$ adb shell "echo mem > /sys/power/state"
```

#### スリープ状態から抜ける（通常状態に戻る）

```
$ adb shell "echo on > /sys/power/state"
```

