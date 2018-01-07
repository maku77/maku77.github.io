---
title: Systrace をコマンド化して簡単に実行できるようにする (systrace.cmd)
date: "2016-07-13"
---

- [Android - Systrace](https://developer.android.com/studio/profile/systrace-commandline.html)
- [Android - Analyzing UI Performance with Systrace](https://developer.android.com/studio/profile/systrace.html)

Android SDK に付属している `systrace.py` スクリプトを使用すると、Android デバイス全体のプロセスに関するプロファイリングを行うことができます（I/O アクセスや UI スレッドの状況など）。

`systrace` は Python スクリプトとして提供されているため、実行するときは下記のような感じでパラメータを指定して実行します。

```
$ cd android-sdk/platform-tools/systrace
$ python systrace.py --time=10 -o trace.html sched gfx view wm
```

同じようなパラメータを毎回指定するのは面倒ですし、Windows 環境ですと PATH を通したりするのも若干面倒ですので、下記のようなバッチファイルを作成しておくと便利です。

#### systrace.cmd

```bat
@echo off
setlocal

REM ========================================
REM 下記のパスは環境に応じて変更してください
REM ========================================
set PYTHON=C:\app\Python27\python.exe
set SYSTRACE=C:\app\android-sdk\platform-tools\systrace\systrace.py

%PYTHON% %SYSTRACE% --time=10 -o trace.html sched gfx view wm
```

このバッチファイルを PATH の通ったディレクトリに置いておけば、どのディレクトリからでも `systrace` と打つだけでプロファイルを取得できるようになります。
プロファイルの取得が完了すると、プロファイル結果を表示するための HTML ファイルがカレントディレクトリに作成されます。

```
C:\> systrace

Capturing trace......................................................Done.
Downloading trace....Done.

    wrote file://C:\trace.html
```

