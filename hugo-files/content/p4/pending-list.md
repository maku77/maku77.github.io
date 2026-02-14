---
title: "Perforceメモ: あるユーザがどのファイルを submit せずにペンディングしているかを調べる (p4 changes)"
url: "p/hpgj6ka/"
date: "2012-07-09"
tags: ["perforce"]
aliases: ["/p4/pending-list.html"]
---

下記のようにすると、ユーザ john が修正しているファイルのうち、submit せずにペンディングしているファイルの一覧を確認することができます。

```console
$ p4 changes -u john -s pending
```
