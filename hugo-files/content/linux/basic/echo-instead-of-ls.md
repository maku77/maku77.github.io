---
title: "Linuxメモ: ls コマンドが使えないときに echo コマンドで代用する"
url: "p/z3dxcch/"
date: "2005-05-23"
tags: ["linux"]
aliases: /linux/basic/echo-instead-of-ls.html
---

Linux 環境でパスが狂ってしまったりして、外部コマンドである `ls` が使えないときは、内部コマンドの `echo` とシェルのワイルドカード展開機能を使って、ディレクトリ内のファイル一覧を表示できます。

```console
$ echo *
$ echo dir/*
$ echo test*
```
