---
title: クライアントの have list を表示する
created: 2008-06-06
layout: p4
---

Perforce では、各クライアントがどのようなファイルをワークスペースに sync して持っているかをサーバ側で管理しています。
このファイルリストのことを **have list** と呼びます。

現在の have list の情報を確認するには、以下のようにします。

```
$ p4 have
```

