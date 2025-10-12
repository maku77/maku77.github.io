---
title: "Linuxメモ: あるファイルがテキストファイルかどうか調べる (file)"
url: "p/ams75pd/"
date: "2008-09-08"
tags: ["linux"]
aliases: /linux/basic/check-if-text-file.html
---

テキストファイルを `file` コマンドにかけると、`... text ...` のような結果が出力されます。
それを `grep` することで、そのファイルがテキストファイルかどうか調べることができます。

```console
$ file sample.txt | grep text
```

