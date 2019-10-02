---
title: "Systrace をコマンド化して簡単に実行できるようにする (systrace.cmd)"
date: "2016-07-13"
lastmod: "2019-10-02"
---

systrace コマンドの基本
----

- [Android - Systrace](https://developer.android.com/studio/profile/systrace-commandline.html)
- [Android - Analyzing UI Performance with Systrace](https://developer.android.com/studio/profile/systrace.html)

Android SDK に付属している `systrace.py` スクリプトを使用すると、Android デバイス全体のプロセスに関するプロファイリングを行うことができます（I/O アクセスや UI スレッドの状況など）。

`systrace` は Python スクリプトとして提供されているため、実行するときは下記のような感じでパラメータを指定して実行します。

#### 例: 10 秒間の systrace 実行する

```
$ cd android-sdk/platform-tools/systrace
$ python systrace.py --time=10 -o trace.html sched gfx view wm
```

プロファイルの取得が完了すると、プロファイル結果を表示するための HTML ファイルがカレントディレクトリに作成されます。

```
Capturing trace......................................................Done.
Downloading trace....Done.

    wrote file://C:\trace.html
```

この HTML を Web ブラウザで開いてボトルネックの分析を行うことができます。
Web ブラウザ上で `?` キーを押すと、使用できるショートカットキーの一覧を確認できます。


バッチファイル化
----

`systrace` コマンドを実行するときに、同じようなパラメータを毎回指定するのは面倒ですし、Windows 環境ですと PATH を通したりするのも若干面倒ですので、下記のようなバッチファイルを作成しておくと便利です。

#### systrace.cmd

```bat
@echo off
setlocal

REM ========================================
REM 下記のパスは環境に応じて変更してください
REM ========================================
set PYTHON=C:\app\Python27\python.exe
set SYSTRACE=%ANDROID_SDK%\platform-tools\systrace\systrace.py

@echo on
%PYTHON% %SYSTRACE% --time=10 -o trace.html sched gfx view wm
```

ここでは、Android SDK のディレクトリパスが `ANDROID_SDK` 環境変数に設定されていると想定しています。
環境変数の設定が面倒であれば、バッチファイル内の `SYSTRACE` 変数の内容を直接いじってしまっても OK です。

このバッチファイルを PATH の通ったディレクトリに置いておけば、どのディレクトリからでも `systrace` と打つだけでプロファイルを取得できるようになります。

```
C:\> systrace
Capturing trace..............................................Done.
```

（応用）バッチファイルにパラメータを追加する
----

下記は上記のバッチファイルをちょっと応用して、計測時間（秒）と、出力ファイル名をパラメータで指定できるようにしたものです。

```bat
@echo off
setlocal

REM ========================================
REM 下記のパスは環境に応じて変更してください
REM ========================================
set PYTHON=C:\app\Python27\python.exe
set SYSTRACE=%ANDROID_SDK%\platform-tools\systrace\systrace.py

REM 第1パラメータは計測時間（デフォルトは 3 秒）
set DURATION=%~1
if "%DURATION%"=="" (set DURATION=3)

REM 第2パラメータは出力ファイル名（デフォルトは trace.html）
set OUT_FILE=%~2
if "%OUT_FILE%"=="" (set OUT_FILE=trace.html)

@echo on
%PYTHON% %SYSTRACE% --time=%DURATION% -o %OUT_FILE% sched gfx view wm
```

例えば、5 秒間計測して、output.html というファイル名で保存するには次のように実行します。

```
C:\> systrace 5 output.html
```

パラメータを省略した場合は、計測時間は 3 秒間、出力ファイル名は trace.html になるようにしています。

ちなみに、Windows バッチファイルのコマンドライン引数の扱い方は、下記の記事で詳しく説明しています。

- [バッチファイルでコマンドライン引数を扱う｜まくまくWindowsノート](/windows/io/command-line-params.html)

