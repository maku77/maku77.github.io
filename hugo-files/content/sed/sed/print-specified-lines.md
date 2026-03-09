---
title: "sed/awkメモ: sed で指定した範囲の行を表示する"
url: "/p/boo82nz/"
date: "2010-08-26"
tags: ["sed/awk"]
aliases: [/sed/sed/print-specified-lines.html]
---

sed の `-n` オプションと、`p` コマンドを組み合わせると、指定した範囲の行だけ表示することができます。

{{< code title="入力ファイル (input.txt)" >}}
001 One
002 Two
003 Three
004 Four
005 Five
{{< /code >}}

{{< code lang="console" title="例: １～３行目を表示" >}}
$ sed -n -e '1,3p' input.txt
001 One
002 Two
003 Three
{{< /code >}}

sed コマンドは、基本的には読み込んだ行をそのまま出力しようとします。
上記の `-n` オプションはこの動きを抑制し、１～３行目だけを `p` コマンドによって出力するように命令しています。
`-n` オプションを付け忘れて実行すると、すべての行が無条件で一度ずつ出力され、さらに `p` コマンドによる出力が行われることになります。

{{< code lang="console" title="例: -n オプションを指定し忘れたとき" >}}
$ sed -e "1,3p" input.txt
001 One
001 One
002 Two
002 Two
003 Three
003 Three
004 Four
005 Five
{{< /code >}}

