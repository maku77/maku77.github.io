---
title: root ユーザで adb 接続する
created: 2011-12-14
---

（下記はデバイス開発者向けの解説であり、root を取得する権限があることを前提としています）

Android 端末の `/system` 領域を `rw` で `remount` したのに、

```
$ adb push sample.apk /system/app
```

が Permission denied でエラーになる場合は、ADB 接続が system ユーザで実行されている可能性があります。
以下のようすると root ユーザで ADB 接続できます。

```
# adb root
# adb kill-server
# adb start-server
```

