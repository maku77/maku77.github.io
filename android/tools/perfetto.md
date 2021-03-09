---
title: "Perfetto でシステム全体のパフォーマンスを計測する"
date: "2021-03-09"
---

Perfetto とは
----

Android 端末のシステム全体のプロファイリングを行うには、__Perfetto__ というツールを使用します。
システム全体の動作を調査する場合、従来は Systrace を使っていましたが、Android 10 (Q) 以降は、より洗練された Perfetto で分析を行うことができます。

- [Perfetto - System profiling, app tracing and trace analysis - Perfetto Tracing Docs](https://perfetto.dev/)

Android には Perfetto 用のトレースデーモンと、トレースデータを取得するための `perfetto` コマンドが標準搭載されています。

取得したトレースデータは、[Perfetto の Web アプリ (Perfetto UI)](https://ui.perfetto.dev/) から読み込んで、グラフィカルな UI で分析を行うことができます。
開発 PC と Android 端末が Bluetooth や USB 接続されていれば、Perfetto UI から直接トレースデータを取得することができます。


Perfetto デーモンの有効化
----

（この設定は、Android 10 (Q) の端末でのみ必要です。Android 11 (R) 以降は必要ありません）

`perfetto` コマンドでトレースを開始するには、Android 端末上で Perfetto デーモンを起動しておく必要があります。
次のようにシステムプロパティを設定すれば、Perfetto デーモンが起動します。

```
$ adb shell setprop persist.traced.enable 1
```

これは `persist` プレフィックスの付いたプロパティなので、設定が不揮発メモリに保存されます。
Android 端末を再起動したときに設定しなおす必要はありません。

正しく Perfetto デーモンが起動していれば、次のような Logcat ログが出力されます。

```
$ adb logcat -s perfetto
--------- beginning of main
03-09 21:38:37.139 21766 21766 I perfetto: probes.cc:56 Starting /system/bin/traced_probes service
03-09 21:38:37.140 21765 21765 W perfetto: service.cc:65 Started traced, listening on /dev/socket/traced_producer /dev/socket/traced_consumer
03-09 21:38:37.141 21766 21766 I perfetto: probes_producer.cc:88 Connected to the service
```


Perfetto トレースデータの取得
----

次のように Android 端末上の __`perfetto`__ コマンドを実行すると、トレース結果が `/data/misc/perfetto-traces/trace` ファイルとして出力されます。
SELinux のセキュリティ制限のため、出力先は `/data/misc/perfetto-traces` ディレクトリしか指定できません（`errno: 13, Permission denied` が発生します）。

```
$ adb shell perfetto --config :test --out /data/misc/perfetto-traces/trace
```

`--config :test` というオプションは、`perfetto` コマンドにハードコードされたテスト設定を使用することを示しています（参考: [Perfetto trace config](https://android.googlesource.com/platform/external/perfetto/+/refs/heads/pie-dev/docs/trace-config.md)）。

トレースファイルは下記のようにローカル PC へダウンロードできます。

```
$ adb pull /data/misc/perfetto-traces/trace

あるいは、

$ adb shell cat /data/misc/perfetto-traces/trace > ~/trace
```

以下のようにして、ディレクトリごとダウンロードすることもできます。

```
$ adb pull /data/misc/perfetto-traces
```

取得したトレースファイルを [Perfetto UI のサイト](https://ui.perfetto.dev/) で開けば、グラフィカルに分析を行うことができます。


（応用）簡単にトレース取得できるようスクリプト化
----

下記のバッチファイルを実行すると、`perfetto-20210309-213055.pb` のようなタイムスタンプ入りのトレースファイルを作成し、ローカル PC にダウンロードします。

`persist.traced.enable` プロパティが正しく設定されていない場合は、設定方法を出力して終了します。

#### get-perfetto.cmd（Windows用）

```
@echo off
setlocal

REM Check the current perfetto daemon settings
for /f "usebackq delims=" %%A in (
  `adb shell getprop persist.traced.enable`
) do set perfet_enabled=%%A

if not "%perfet_enabled%"=="1" (
  echo Trace daemon is not started. Try the following command.
  echo adb shell setprop persist.traced.enable 1
  exit /b
)

REM Generate a filename by the current date and time
set mydate=%DATE%
set mytime=%TIME%
set year=%mydate:~0,4%
set month=%mydate:~5,2%
set day=%mydate:~8,2%
set hour=%mytime:~0,2%
set minute=%mytime:~3,2%
set second=%mytime:~6,2%
set filename=perfetto-%year%%month%%day%-%hour%%minute%%second%.pb

REM Start tracing
adb shell perfetto --config :test --out /data/misc/perfetto-traces/%filename%

REM Download the trace file
adb pull /data/misc/perfetto-traces/%filename%
```

