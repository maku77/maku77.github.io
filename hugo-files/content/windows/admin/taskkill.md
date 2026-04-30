---
title: "Windowsメモ: Windows で実行中のプロセス（タスク）を終了 (kill) する (taskkill)"
url: "p/agnokb9/"
date: "2017-10-10"
description: "taskkill コマンドを使用すると、実行ファイル名 (.exe) やプロセス ID (PID) を指定してタスクを終了することができます。"
tags: ["windows"]
aliases: /windows/admin/taskkill.html
---

実行ファイル名を指定してプロセスを終了する (/im)
----

{{< code title="例: 実行ファイル名が cmd.exe のプロセス（タスク）をすべて終了する" >}}
C:\> taskkill /im cmd.exe
{{< /code >}}

上記のようにすると、実行ファイル名（イメージ名）が `cmd.exe` である**すべてのプロセスが終了する**ことに注意してください。


プロセス ID を指定してプロセスを終了する (/pid)
----

{{< code title="例: プロセス ID が 1234 のプロセス（タスク）を終了する" >}}
C:\> taskkill /pid 1234
{{< /code >}}

各プロセスのプロセス ID は、[`tasklist` コマンド](/p/6c6eu8p/)で確認することができます。

下記のように `/pid` パラメータを複数指定すると、複数のプロセスをまとめて終了することができます。

```
C:\> taskkill /pid 100 /pid 200 /pid 300
```


プロセス（タスク）を強制終了する (/f)
----

応答なし状態 (Not Responding) のプロセスを `taskkill` コマンドで終了するには、`/f` オプションをつけて強制終了する必要があります。
まずは、応答なし状態になっているプロセスを `tasklist` コマンドで検索し、`taskkill` コマンドでプロセス ID を指定して強制終了するのがよいでしょう。

{{< code title="手順１）応答なし状態のプロセスの PID を調べる" >}}
C:\> tasklist /fi "status eq not responding"
{{< /code >}}

{{< code title="手順２）PID を指定してプロセスを強制終了" >}}
C:\> taskkill /f /pid 1234
成功: PID 1234 のプロセスは強制終了されました。
{{< /code >}}


プロセスツリーをまるごと終了する (/t)
----

1 つのアプリケーションは複数のプロセスを使って動作している可能性があります。
`/t` オプションを指定して `taskkill` コマンドを実行すると、そのプロセスを頂点とするプロセスをすべて終了することができます。

{{< code title="例: PID 1234 のプロセスを頂点とするプロセスをすべて終了する" >}}
C:\> taskkill /t /pid 1234
{{< /code >}}


高度な条件でプロセスを検索してプロセスを終了する (/fi)
----

`taskkill` コマンドで終了するプロセスを検索するときに、[`tasklist` コマンド](/p/6c6eu8p/)と同様のフィルタオプション (`/fi`) を使用することができます。
このオプションを使用すると、応答なし (Not Responding) 状態のプロセスや、特定の DLL を使用しているプロセスなどを検索してまとめて終了することができます。

{{< code title="例: 応答しなくなった cmd.exe のプロセスをすべて終了する" >}}
C:\> taskkill /im cmd.exe /fi "status eq not responding"
{{< /code >}}

{{< code title="例: メモリ使用量が 200,000KB 以上のプロセスをすべて終了する" >}}
C:\> taskkill /fi "memusage ge 200000"
{{< /code >}}

{{< code title="例: buggy.dll を使用しているプロセスをすべて終了する" >}}
C:\> taskkill /fi "modules eq buggy.dll"
{{< /code >}}
