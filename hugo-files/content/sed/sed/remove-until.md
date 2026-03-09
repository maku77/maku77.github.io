---
title: "sed/awkメモ: sed でパターンに一致する行が現れるまで削除する"
url: "/p/3ms2jj2/"
date: "2010-08-26"
tags: ["sed/awk"]
aliases: [/sed/sed/remove-until.html]
---

{{< code title="入力ファイル (input.txt)" >}}
AAA
BBB

CCC
DDD
{{< /code >}}

削除コマンド `d` の直前で、削除対象となる行範囲 (`<開始行>,<終了行>`) を指定することができます。
行指定には、行番号や、検索パターンを指定します。
下記の例では、一行目から、最初に空白行（正規表現で `^$`）が現れる行までを削除しています。

```console
$ sed '1,/^$/d' input.txt
CCC
DDD
```

