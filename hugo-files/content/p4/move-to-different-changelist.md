---
title: "Perforceメモ: 編集中のファイルを別のチェンジリストに移動する (p4 reopen)"
url: "p/ewf4dkt/"
date: "2006-08-22"
tags: ["perforce"]
aliases: ["/p4/move-to-different-changelist.html"]
---

すでに編集状態になっている（オープンされている）ファイルを、別のチェンジリストに移動して管理したいときは、`p4 reopen` コマンドで下記のように移動先のチェンジリストを指定します。

```console
$ p4 reopen -c <ChangeListNumber> <FileName>
```

新しくファイルを編集状態にするときに、指定したチェンジリストで管理するようにするには、`p4 edit` コマンドを使用します → [指定したチェンジリストでファイルをオープンする](/p/dfywfu4/)。
