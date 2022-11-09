---
title: "Windows のバッチファイルでプロンプトを表示してユーザー入力を促す (set /p)"
url: "p/2dn6njw/"
date: "2022-11-09"
tags: ["Windows", "バッチファイル"]
---

set /p コマンドの使い方
----

Windows のコマンドプロンプトで __`SET /P`__ コマンドを実行すると、任意のプロンプトを表示して、ユーザーにキーボード入力を促すことができます。

```
SET /P 変数=[プロンプト文字列]
```

ユーザーが入力したテキストは、指定した変数に格納されるので、後から `%変数名%` という形で参照することができます。
次の例では、ユーザーにプロジェクト名の入力を促し、入力結果を `PjName` という変数に格納しています。

{{< code lang="bat" title="sample.cmd" >}}
@echo off
setlocal

set /p PjName="Project name? "
echo 入力したプロジェクト名は %PjName% です
{{< /code >}}

{{< code title="実行例" >}}
Project name? HelloWorld
入力したプロジェクト名は HelloWorld です
{{< /code >}}


具体的な使用例
----

`set /p` コマンドを実行する前に、入力候補を表示するという利用方法も考えられます。
次のサンプルバッチファイルは、Android のエミュレーター (AVD) を起動するためのものですが、起動可能なデバイス (AVD) の候補を表示して、ユーザーにどのデバイスを起動するかを選択させています。

{{< code lang="bat" title="emu.cmd" >}}
@echo off
setlocal

echo Available AVDs:
emulator -list-avds

set /p AvdName="Which AVD do you want to start? "
if "%AvdName%"=="" (
    echo ERROR: No AVD specified.
    exit /b
)
emulator @%AvdName%
{{< /code >}}

{{< code title="実行例" >}}
Available AVDs:
phone-s
phone-t
tv-s
tv-t
Which AVD do you want to start? phone-t
{{< /code >}}

- 参考: [Windows のバッチファイルの実行を途中で終了する (exit /b)](/p/66q7m2j/)

