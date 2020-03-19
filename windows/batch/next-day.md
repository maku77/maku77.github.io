---
title: "バッチファイル内で1日後の日時を取得する（時刻の演算）"
date: "2020-03-19"
---

バッチファイルの中で日時の演算を行う
----

Windows の `%DATE%` 変数や `%TIME%` 変数を使えば、[バッチファイル内で日付や時刻の文字列を取得する](../batch/display-time.html) ことはできるのですが、書式を指定できなかったり、時刻の演算を行うこともできません。
そこで、PowerShell の `Get-Date` コマンドレットをバッチファイルから呼び出すことでこの問題を解決します。

次のバッチファイルでは、現在時刻を基準にして 1 日後の日時を取得＆表示しています（`AddMonths` の代わりに `AddDays` や `AddHours` などを使った演算も可能です）。

#### sample.bat

```
@echo off
setlocal

set ps_command=`powershell "(Get-Date).AddMonths(1).ToString('yyyy-MM-dd HH:mm:ss')"`
FOR /F "usebackq delims=" %%A IN (%ps_command%) DO set tomorrow=%%A
echo %tomorrow%
```

#### 実行結果

```
C:\> sample
2020-03-19 21:35:58
```

ポイントは、**`FOR /F` コマンドで外部コマンドの実行結果を受け取る** ところですね。
詳しくは下記のサイトを参考にしてください。

- 参考: [バッチファイルから PowerShell を呼び出して結果を変数に格納する- まくろぐ](https://maku.blog/p/372dx46/)


PowerShell スクリプトでやる場合
----

まぁ、そもそもここまでやるなら最初から PowerShell スクリプトにしておけってことですね。
PowerShell で書くならこんな感じです。

#### sample.ps1

```
$tomorrow = (Get-Date).AddMonths(1).ToString('yyyy-MM-dd HH:mm:ss')
Write-Host $tomorrow
```

#### 実行結果

```
C:\> powershell ./sample.ps1
2020-03-19 21:35:58
```

