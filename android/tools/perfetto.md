---
title: "Perfetto でシステム全体のパフォーマンスを計測する"
date: "2021-03-09"
lastmod: "2021-03-15"
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
SELinux のセキュリティ制限のため、出力先 (`-o/--out`) は `/data/misc/perfetto-traces` ディレクトリしか指定できません（`errno: 13, Permission denied` が発生します）。

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


Perfetto のトレース設定
----

`perfetto` コマンドを実行するときにパラメーターを指定することで、計測時間や取得するデータのカスタマイズを行えます。
設定方法は大きく分けて、下記の 2 通りの方法があります。

(1) lightweight mode
: `-t/--time` などの個々のパラメーターを 1 つずつ設定する方法。従来の `systrace` と同様の指定 (atrace、ftrace) を行うだけでよければこの方法が使えます。

(2) normal mode
: `-c/--config` オプションでコンフィグファイルを指定する方法。lightweight mode より詳細なトレース設定を行うことができます。

参考: [perfetto コマンドのコマンドラインオプション](https://perfetto.dev/docs/reference/perfetto-cli)。

### lightweight mode（個別パラメーターで指定する方法）

`perfetto` コマンドを実行するときに、次のようなオプションを使って、トレース方法を設定する方法です。

```
adb shell perfetto [--time TIMESPEC] [--buffer SIZE] [--size SIZE]
                   [ATRACE_CAT | FTRACE_GROUP/FTRACE_NAME]...
```

- `-t/--time TIMESPEC` ... トレース時間。使える単位は `s`、`m`、`h`（デフォルト: `10s`）。
- `-b/--buffer SIZE` ... リングバッファサイズ。使える単位は `mb`、`gb`（デフォルト: `32mb`)。
- `-s/--size SIZE` ... 最大ファイルサイズ。使える単位は `mb`、`gb`（デフォルトはリングバッファサイズ？）。
- `ATRACE_CAT` ... トレースする atrace カテゴリ（例: `am wm gfx view` など）を指定。参考: [Tracing categories（実装）](https://android.googlesource.com/platform/frameworks/native/+/master/cmds/atrace/atrace.cpp#100)
- `FTRACE_GROUP/FTRACE_NAME` ... トレースする ftrace グループ（カーネル系のトレース）（例: `sched/sched_switch`、`sched/*`）

#### 実行例（3秒間、gfx、input、view、sched のトレース）

```
$ adb shell perfetto -o /data/misc/perfetto-traces/trace -t 3s gfx input view sched
```

### normal mode（コンフィグファイルで指定する方法）

`perfetto` 用の設定をコンフィグファイルの形で保存しておけば、`--config` オプションでそのファイル名を指定するだけで毎回同じ設定でトレースできます。
`perfetto` コマンドはデバイス上で実行する都合上、コンフィグファイルはデバイス上に作成しなければいけないことに注意してください。
ただ、ファイル名として `-` を指定すると標準入力から設定テキストを流し込むことができるので、これを使えばローカル PC 上のコンフィグファイルの内容をそのまま渡せます。

```
$ cat config.pbtx | adb shell perfetto -o /data/misc/perfetto-traces/trace --txt -c -
```

Windows の場合は `cat` を `type` に置き換えてください。
下記はコンフィグファイルの記述例です。
このようなテキスト形式のコンフィグファイルを指定する場合は、`-c/--config` オプションに加えて、__`--txt`__ オプションも同時に指定する必要があります。

#### コンフィグファイルの例 (perfetto-config.pbtx)

```
duration_ms: 5000

buffers: {
    size_kb: 8960
    fill_policy: DISCARD
}

data_sources {
  config {
    name: "linux.ftrace"
    ftrace_config {
      # Enables events for a specific app.
      atrace_apps: "com.example.myapp"

      # Enables all events for all apps.
      # atrace_apps: "*"

      # Enables specific system events tags.
      atrace_categories: "gfx"
      atrace_categories: "input"
      atrace_categories: "view"
      atrace_categories: "sched"

      # ftrace_events: "sched/sched_switch"
      # ftrace_events: "power/suspend_resume"
      # ftrace_events: "sched/sched_process_exit"
      # ftrace_events: "sched/sched_process_free"
      # ftrace_events: "task/task_newtask"
      # ftrace_events: "task/task_rename"
      # ftrace_events: "ftrace/print"
    }
  }
}
```


（応用）簡単にトレース取得できるようスクリプト化
----

下記のバッチファイルを実行すると、__`perfetto-20210309-213055.pb`__ のようなタイムスタンプ入りのトレースファイルを作成し、ローカル PC にダウンロードします。
トレースの設定は、先頭部分の __`DURATION`__ 変数や __`ATRACE`__ 変数である程度柔軟にカスタマイズできます。
perfetto デーモンが起動していない場合は、設定のヒントを表示して終了するようにしています。

#### get-perfetto.cmd（Windows用）

```
@echo off
setlocal

REM Perfetto configuration
set DURATION=3s
set ATRACE=gfx view input sched
set FTRACE=

REM Check if the perfetto daemon is enabled
for /f "usebackq delims=" %%A in (
  `adb shell getprop persist.traced.enable`
) do set PERFET_ENABLED=%%A

if not "%PERFET_ENABLED%"=="1" (
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
adb shell perfetto --out /data/misc/perfetto-traces/%filename% -t %DURATION% %ATRACE% %FTRACE%

REM Download the trace file
adb pull /data/misc/perfetto-traces/%filename%
```

