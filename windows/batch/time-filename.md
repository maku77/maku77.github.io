---
title: "バッチファイル内で日時を元にファイル名を作成する"
date: "2021-03-09"
---

何をするか？
----

Windows のコマンドプロンプトで使用できる特殊変数 __`%DATE%`__ と __`%TIME%`__ の値を利用して、次のようなタイムスタンプ付きのファイル名を自動作成する方法を説明します。

```
log-20210309-213055.txt
```


バッチファイルの例
----

次のバッチファイルを実行すると、`log-20210309-213055.txt` といった名前のファイルを自動生成し、`Hello` と `World` を書き込みます。

#### sample.cmd

```
@echo off
setlocal

REM Generate a filename by the current datetime
set mydate=%DATE%
set mytime=%TIME%
set year=%mydate:~0,4%
set month=%mydate:~5,2%
set day=%mydate:~8,2%
set hour=%mytime:~0,2%
set minute=%mytime:~3,2%
set second=%mytime:~6,2%
set filename=log-%year%%month%%day%-%hour%%minute%%second%.txt

REM Test
echo Hello >> %filename%
echo World >> %filename%
```

#### log-20210309-213055.txt（自動生成されるファイル）

```
Hello
World
```


解説
----

まず、`%DATE%` と `%TIME%` を参照すると、次のような値を取得できることが分かります。

```
C:\> echo %DATE%
2021/03/09

C:\> echo %TIME%
21:30:55.18
```

変数の値を参照するときに、__`%変数名:~n,m%`__ という構文を使うと、n+1 文字目から m 文字分の部分文字列を取り出すことができます。
これを利用して、年/月/日/時/分/秒の情報を抽出することができます。

```
set mydate=%DATE%
set mytime=%TIME%
set year=%mydate:~0,4%
set month=%mydate:~5,2%
set day=%mydate:~8,2%
set hour=%mytime:~0,2%
set minute=%mytime:~3,2%
set second=%mytime:~6,2%

echo %year%
echo %month%
echo %day%
echo %hour%
echo %minute%
echo %second%
```

あとは、これらの値を組み合わせてファイル名を構成するだけです。

```
set filename=log-%year%%month%%day%-%hour%%minute%%second%.txt
echo Hello > %filename%
```

