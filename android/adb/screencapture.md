---
title: "ADB 経由でスクリーンキャプチャを取得する"
date: "2015-04-01"
---

ADB 接続ができている状態で下記のように実行すると、（PC 側の）カレントディレクトリに `capture.png` ファイルを取得できます。

~~~
$ adb shell screencap -p /sdcard/capture.png
$ adb pull /sdcard/capture.png
$ adb shell rm /sdcard/capture.png
~~~

