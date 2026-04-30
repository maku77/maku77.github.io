---
title: "Windowsメモ: 複数のコマンドの出力をまとめてリダイレクトする"
url: "p/mwo8tt5/"
date: "2005-07-30"
tags: ["windows"]
aliases: /windows/io/collect-output.html
---

コマンドを括弧 `( )` で囲むことによって、標準出力をまとめて扱うことができるようになります。
下記の例では、3 つのコマンドの標準出力を hostinfo.txt というファイルに保存しています。

```
C:\> (hostname & ipconfig & netstat -a) > hostinfo.txt
```
