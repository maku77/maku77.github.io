---
title: "Windowsメモ: Windows の DNS キャッシュをクリアする"
url: "p/kwbibnj/"
date: "2010-05-09"
tags: ["windows"]
aliases: /windows/network/clear-dns-cache.html
---

DNS のキャッシュ情報をクリアする
----

Windows クライアントの DNS キャッシュをクリアするには、下記のように実行します。

```
C:\> ipconfig /flushdns
```

あるいは PC を再起動してもキャッシュ情報はクリアされます。


DNS のキャッシュ情報を表示する
----

現在の DNS キャッシュ情報を表示するには以下のようにします。

```
C:\> ipconfig /displaydns
```

ちなみに、DNS キャッシュ情報の TTL (Time To Live) の最大値は 86400 秒（一日）です。
