---
title: "Androidメモ: ADB 経由でスクリーンキャプチャを取得する (screencap)"
url: "p/kp3ubcu/"
date: "2015-04-01"
tags: ["android"]
aliases: ["/android/adb/screencapture.html"]
---

Android デバイス上で **`screencap`** コマンドを使うと、スクリーンキャプチャを取得できます。
ADB 接続ができている状態であれば、下記のように実行することで PC 側のカレントディレクトリに `capture.png` ファイルを取得できます。

```console
$ adb shell screencap -p /sdcard/capture.png
$ adb pull /sdcard/capture.png
$ adb shell rm /sdcard/capture.png
```

