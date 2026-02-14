---
title: "Perforceメモ: p4 sync 時に空のディレクトリを削除する (rmdir, normdir)"
url: "p/sre5kts/"
date: "2006-06-16"
tags: ["perforce"]
aliases: ["/p4/rmdir-after-sync.html"]
---

`p4 sync` したときにワークスペース上から空のフォルダを削除するには、クライアントに下記のオプションを設定します（`p4 client` で設定画面を起動）。

```
Options: rmdir
```

逆に、空のディレクトリをそのままにしておく場合は以下のように設定します。

```
Options: normdir
```
