---
title: "Gitメモ: マージで競合解決が必要なファイルを調べる (git ls-files -u)"
url: "p/jok7ggh/"
date: "2010-09-17"
tags: ["git"]
aliases: [/git/merge/list-conflicted-files.html]
---

マージ作業を行う際に、修正がコンフリクトしているファイルの一覧を表示するには下記のようにします。

```console
$ git ls-files -u
```
