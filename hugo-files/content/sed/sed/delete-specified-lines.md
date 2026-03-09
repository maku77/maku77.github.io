---
title: "sed/awkメモ: sed で指定した範囲の行を削除する"
url: "/p/gsxojue/"
date: "2010-08-26"
tags: ["sed/awk"]
aliases: [/sed/sed/delete-specified-lines.html]
---

sed の `d` コマンドを使用すると、指定した範囲の行を削除できます。

{{< code title="入力ファイル (input.txt)" >}}
001 Hello
002 Hello
003 Hello
004 Hello
005 Hello
{{< /code >}}

{{< code lang="console" title="例: 3 行目だけ削除" >}}
$ sed -e '3d' input.txt
001 Hello
002 Hello
004 Hello
005 Hello
{{< /code >}}

{{< code lang="console" title="例: 2 ～ 4 行目を削除" >}}
$ sed -e '2,4d' input.txt
001 Hello
005 Hello
{{< /code >}}

{{< code lang="console" title="例: 最終行 ($) だけ削除" >}}
$ sed -e '$d' input.txt
001 Hello
002 Hello
003 Hello
004 Hello
{{< /code >}}
