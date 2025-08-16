---
title: "Androidメモ: ADB でディレクトリ内のファイルをすべて取得する (adb pull)"
url: "p/36huwqe/"
date: "2011-07-29"
tags: ["android"]
aliases: ["/android/adb/adb-pull-dir.html"]
---

Android SDK の __`adb pull`__ コマンドは、単一のファイルだけではなく、ディレクトリごとファイルを取得するのにも使用できます。
下記の例では、Android デバイス上の `/system/lib` ディレクトリ内のファイルをすべて取得しています。

```console
$ adb pull /system/lib
pull: building file list...
pull: /system/lib/libcutils.so -> ./libcutils.so
pull: /system/lib/libssl.so -> ./libssl.so
...
245 files pulled. 0 files skipped.
1575 KB/s (97775523 bytes in 60.596s)
```

