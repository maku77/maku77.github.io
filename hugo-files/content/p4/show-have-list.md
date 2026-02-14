---
title: "Perforceメモ: クライアントの have list を表示する (p4 have)"
url: "p/x2auai4/"
date: "2008-06-06"
tags: ["perforce"]
aliases: ["/p4/show-have-list.html"]
---

Perforce では、各クライアントがどのようなファイルをワークスペースに sync して持っているかをサーバ側で管理しています。
このファイルリストのことを **have list** と呼びます。

現在の have list の情報を確認するには、以下のようにします。

```console
$ p4 have
```
