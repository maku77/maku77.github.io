---
title: "Androidメモ: ADB 経由でスクリーンキャプチャを取得する (screencap)"
url: "p/kp3ubcu/"
date: "2015-04-01"
lastmod: "2026-02-19"
tags: ["android"]
changes:
  - 2026-02-19: バッチファイル化の例を追加
aliases: ["/android/adb/screencapture.html"]
---

Android デバイスの screencap コマンド
----

Android デバイス上で **`screencap`** コマンドを使うと、スクリーンキャプチャを取得できます。
PC から ADB 接続ができている状態であれば、下記のように実行することで PC 側のカレントディレクトリに `capture.png` ファイルを取得できます。

```console
$ adb shell screencap -p /sdcard/capture.png
$ adb pull /sdcard/capture.png
$ adb shell rm /sdcard/capture.png
```

Android デバイス内の `/sdcard` ディレクトリを指定しているのは、通常のユーザ権限で書き込みが可能な場所だからです。


バッチファイル化しておく
----

Windows 環境であれば、以下のようにバッチファイルを作成してパスの通ったディレクトリに置いておけば、1 コマンドでスクリーンキャプチャを取得できるようになります。
作成される PNG ファイル名には、自動的に日時を付与するようにしています。

{{< code lang="cmd" title="m-adb-screencap.cmd" >}}
@echo off
setlocal enabledelayedexpansion
if "%~1"=="" (
    for /f "tokens=1-6 delims=/-:. " %%a in ("%date% %time%") do (
        set YYYY=%%a
        set MM=%%b
        set DD=%%c
        set hh=%%d
        set mm=%%e
        set ss=%%f
    )
    set FILENAME=capture-!YYYY!!MM!!DD!T!hh!!mm!!ss!.png
) else (
    set FILENAME=%~1
)
adb shell screencap -p "/sdcard/%FILENAME%"
adb pull "/sdcard/%FILENAME%" "%FILENAME%"
adb shell rm "/sdcard/%FILENAME%"
{{< /code >}}

{{< code title="使用例" >}}
▼ パラメータ指定せずに実行するとファイル名を自動生成
C:\> m-adb-screencap
/sdcard/capture-20264719T144706.png: 1 file pulled, 0 skipped. 41.2 MB/s (2486963 bytes in 0.058s)

▼ ファイル名を指定する場合
C:\> m-adb-screencap mycapture.png
/sdcard/capture.png: 1 file pulled, 0 skipped. 41.2 MB/s (2486963 bytes in 0.058s)
{{< /code >}}

