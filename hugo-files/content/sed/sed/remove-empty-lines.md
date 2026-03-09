---
title: "sed/awkメモ: sed で空白行を削除する"
url: "/p/fnj6z57/"
date: "2010-08-26"
tags: ["sed/awk"]
aliases: [/sed/sed/remove-empty-lines.html]
---

{{< code title="入力ファイル (input.txt)" >}}
aaa


bbb

ccc
{{< /code >}}

上記の入力ファイルには、3 行の空白行が含まれています。
これらの空白行をまとめて削除するには、下記のように空白行 (`^$`) をパターン指定して `d` コマンドで削除します。

```console
$ sed '/^$/d' input.txt
aaa
bbb
ccc
```

* 参考: [tr で空白行を削除する](/p/kw5aygu/)

