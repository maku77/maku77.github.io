---
title: "Windowsメモ: コマンドプロンプトからインターネット上のファイルをダウンロードする (curl, Invoke-WebRequest, bitsadmin)"
url: "p/xd3b8uo/"
date: "2018-04-11"
description: "Windows に標準で搭載されている Bitsadmin.exe ユーティリティを使用すると、コマンドプロンプト、あるいはバッチファイルを使用して、インターネット上のファイルをダウンロードすることができます。"
tags: ["windows"]
aliases: /windows/cmd/download.html
---

curl コマンド（Windows 10 バージョン 1803 以降）
----

Windows 10 バージョン 1803 以降では `curl` コマンドが標準搭載されています。
`-o` オプションで保存先のファイル名を指定します。

{{< code title="例: ファイルをダウンロードして保存" >}}
C:\> curl -o sample.png https://example.com/sample.png
{{< /code >}}

`-O`（大文字）を使うと、URL のファイル名をそのまま保存先のファイル名として使用します。

{{< code title="例: URL のファイル名のままダウンロード" >}}
C:\> curl -O https://example.com/sample.png
{{< /code >}}

プロキシ経由でダウンロードする場合は `-x` オプションでプロキシを指定できます。

```
C:\> curl -x http://proxy.example.com:8080 -o sample.png https://example.com/sample.png
```


PowerShell を使う方法
----

PowerShell が使える環境では、より柔軟なダウンロードが可能です。

### Invoke-WebRequest コマンドレット

`Invoke-WebRequest`（エイリアス: `iwr`、`wget`、`curl`）を使う方法です。

{{< code lang="powershell" title="例: ファイルをダウンロードして保存" >}}
Invoke-WebRequest -Uri "https://example.com/sample.png" -OutFile "C:\dir\sample.png"
{{< /code >}}

### Start-BitsTransfer コマンドレット

BITS（Background Intelligent Transfer Service）を利用するため、大きなファイルのダウンロードに向いています。
バックグラウンドでの転送や、中断・再開にも対応しています。

{{< code lang="powershell" title="例: ファイルをダウンロードして保存" >}}
Start-BitsTransfer -Source "https://example.com/sample.png" -Destination "C:\dir\sample.png"
{{< /code >}}

バッチファイルから PowerShell コマンドを呼び出すこともできます。

{{< code lang="bat" title="例: バッチファイルから Invoke-WebRequest を呼び出す" >}}
@echo off
powershell -Command "Invoke-WebRequest -Uri 'https://example.com/sample.png' -OutFile '%CD%\sample.png'"
{{< /code >}}


bitsadmin コマンド（レガシー）
----

`bitsadmin` は Windows に標準搭載されているファイルダウンロード用のコマンドラインツールです。
ただし、**Windows 10 以降では非推奨**となっており、後述の PowerShell や `curl` を使用することが推奨されています。

`bitsadmin` コマンドの構文は以下の通りです。

```
bitsadmin /transfer <ジョブ名> <URL> <保存先ファイル名>
```

- **`<ジョブ名>`**: ジョブを識別するための任意の文字列です。例: `myjob`
- **`<URL>`**: ダウンロードするファイルの URL。例: `https://example.com/sample.png`
- **`<保存先ファイル名>`**: ダウンロードしたファイルの保存先を示すローカルパスです。**フルパスで指定する必要があります**。例: `C:\dir\sample.png`

{{< code title="例: カレントディレクトリに logo.png という名前でダウンロード" >}}
C:\> bitsadmin /transfer myjob https://example.com/sample.png %CD%\sample.png
{{< /code >}}

保存先のファイルパスはフルパスで指定しなければいけないため、変数 `%CD%` を使用してカレントディレクトリのパスを結合しているところがポイントです。

