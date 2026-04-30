---
title: "Windowsメモ: バッチファイルが管理者として実行されているか調べる"
url: "p/5xohfdp/"
date: "2021-01-07"
tags: ["windows"]
aliases: /windows/batch/run-as-admin.html
---

バッチファイルの中から管理者権限の必要なコマンドを実行したいときは、バッチファイルの起動元のコマンドプロンプトが「管理者として実行」で起動されている必要があります（あるいは、バッチファイルを右クリックして「管理者として実行」）。

次のバッチファイルでは、自分自身が「管理者として実行」で起動されているかを調べ、そうでなければバッチファイルを終了するようにしています。

{{< code lang="bat" title="sample.bat" >}}
@echo off

REM Exit if not admin
net session >null 2>&1
if %ERRORLEVEL% neq 0 (
    echo Error: Need to run as Administrator
    exit /b
)

...管理者権限が必要な処理...
{{< /code >}}

管理者権限があるかどうかを調べるための構文はなさそうなので、ここでは管理者権限が必要な `net session` コマンドを実行してみて、エラーにならない (`ERRORLEVEL==0`) かどうかを調べています。
