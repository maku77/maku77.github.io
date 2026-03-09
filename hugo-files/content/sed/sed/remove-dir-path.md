---
title: "sed/awkメモ: sed でパス文字列からディレクトリ名を抽出する"
url: "/p/zqon5aj/"
date: "2009-06-11"
tags: ["sed/awk"]
aliases: [/sed/sed/remove-dir-path.html]
---

下記の例では、パスを表す文字列から、ディレクトリのパスを抽出しています（単純にスラッシュ (`/`) の前を取り出しているだけ）。

```console
$ echo '/aaa/bbb/ccc' | sed 's:\(.*\)/.*:\1:'
```

Linux を使用している場合は、素直に `dirname` を使ったほうが簡単です。

```console
$ dirname '/aaa/bbb/ccc'
/aaa/bbb
```

