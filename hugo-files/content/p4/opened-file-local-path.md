---
title: "Perforceメモ: オープンされていて、かつ変更されているファイルの一覧をローカルパスで表示する"
url: "p/3cuyz8w/"
date: "2008-03-25"
tags: ["perforce"]
aliases: ["/p4/opened-file-local-path.html"]
---

次のようにすると、編集状態（オープン状態）にあるファイルのうち、実際に変更されているファイルの一覧をローカルパスで表示できます。

```console
$ p4 diff -sa
/home/maku/MyApp/Sample.cpp
/home/maku/MyApp/Sample.h
```

ただし、`p4 add` で新規に追加されたファイルは表示されないので注意してください。
