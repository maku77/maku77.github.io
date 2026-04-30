---
title: "Windowsメモ: バッチファイル内で日時を出力する"
url: "p/thinhxh/"
date: "2015-05-21"
tags: ["windows"]
aliases: /windows/batch/display-time.html
---

%DATE% 変数、%TIME% 変数を使用する方法
----

```
C:\> echo %DATE%
2015/05/21

C:\> echo %TIME%
14:58:51.61
```

いろいろ加工して出力することもできます。

{{< code title="例: YYYY-MM-DD 形式で出力" >}}
C:\> echo %DATE:~0,4%-%DATE:~5,2%-%DATE:~8,2%
2015-05-21
{{< /code >}}

{{< code title="例: ログに時刻情報を入れたいときなど" >}}
C:\> echo DATETIME: %DATE% %TIME%
DATETIME: 2015/05/21 14:58:51.61
{{< /code >}}


date コマンド、time コマンドを使用する方法
----

```
C:\> date /t
2015/05/21

C:\> time /t
15:00
```
