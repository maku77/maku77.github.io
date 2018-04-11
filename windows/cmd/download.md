---
title: "コマンドプロンプトからインターネット上のファイルをダウンロードする (bitsadmin)"
date: "2018-04-11"
description: "Windows に標準で搭載されている Bitsadmin.exe ユーティリティを使用すると、コマンドプロンプト、あるいはバッチファイルを使用して、インターネット上のファイルをダウンロードすることができます。"
---

bitsadmin コマンドの構文は以下の通りです。

~~~
bitsadmin /transfer ＜ジョブ名＞ ＜URL＞ ＜保存先ファイル名＞
~~~

- **＜ジョブ名＞**: 任意ジョブ名。ジョブを識別するための任意の文字列です。例: `myjob`
- **＜URL＞**: ダウンロードするファイルのURL。例: `https://example.com/sample.png`
- **＜保存先ファイル名＞**: ダウンロードしたファイルの保存先を示すローカルパスです。フルパスで指定する必要があります。例: `C:\dir\sample.png`

下記は、Google の検索ページのロゴをダウンロードする例です。

#### カレントディレクトリに logo.png という名前でダウンロード

~~~
C:\> bitsadmin /transfer download https://www.google.co.jp/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png %CD%\logo.png
~~~

保存先のファイルパスはフルパスで指定しなければいけないため、変数 `%CD%` を使用してカレントディレクトリのパスを結合しているところがポイントです。

