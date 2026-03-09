---
title: "sed/awkメモ: awk で任意のコマンド出力の N 番目のフィールドのみを抜き出す"
url: "/p/zz2cwjo/"
date: "2005-07-20"
tags: ["sed/awk"]
aliases: [/sed/awk/display-specified-field.html]
---

`awk` をフィルタコマンドとして利用すると、任意のコマンドの出力の中から、指定したフィールド（列）のみを抽出することができます。
下記の例では、`ps` コマンドの出力の 1 列目だけを表示しています。

```console
$ ps a | awk '{print $1}'
PID
1538
1546
1936
1937
```

