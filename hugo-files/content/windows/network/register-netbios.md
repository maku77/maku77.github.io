---
title: "Windowsメモ: ネットワーク切り替え時に PC 名ですぐに検索できるようにする（NetBios 名の再登録）"
url: "p/uxqyis7/"
date: "2012-12-13"
tags: ["windows"]
aliases: /windows/network/register-netbios.html
---

ノート PC のネットワークを無線 LAN から有線 LAN に切り替えた場合に、一時的に他のマシンから PC 名（NetBios 名）で検索できなくなってしまうことがあります。
そのような場合は、下記のように `netstat` コマンドを一度実行すれば NetBios 名が登録しなおされるため、他のマシンから名前ベースですぐにアクセスできるようになります。

```
C:\> netstat
```
