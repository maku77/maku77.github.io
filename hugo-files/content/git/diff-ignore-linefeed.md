---
title: "Gitメモ: git diff で改行コードの違いを無視する (git diff -w)"
url: "p/zm7ezhx/"
date: "2015-12-16"
tags: ["git"]
aliases: [/git/diff-ignore-linefeed.html]
---

`git diff` コマンドを実行するときに、改行コードが違うだけの行を検出しないようにするには、`-w` オプションを使用します。

```console
$ git diff -w
```

