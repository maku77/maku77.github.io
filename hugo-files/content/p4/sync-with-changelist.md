---
title: "Perforceメモ: チェンジリスト番号を指定して p4 sync する"
url: "p/iusp9p7/"
date: "2008-08-29"
tags: ["perforce"]
aliases: ["/p4/sync-with-changelist.html"]
---

{{< code title="例: Change 179939 にすべてのファイルを sync" >}}
$ p4 sync //...@179939
$ p4 sync @179939
{{< /code >}}

リビジョン番号ではなく、チェンジリストの番号であることに注意してください。
上記の記法は、ラベル名を使って `p4 sync` する場合にも使用できます。

```console
$ p4 sync @LABEL_NAME
```
