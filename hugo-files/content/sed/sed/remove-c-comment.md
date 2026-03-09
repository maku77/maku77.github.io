---
title: "sed/awkメモ: sed で C 言語のコメントを削除する"
url: "/p/idstams/"
date: "2009-04-05"
tags: ["sed/awk"]
aliases: [/sed/sed/remove-c-comment.html]
---

下記の例では、C 言語のソースコード (sample.c) から、コメント (`/* comment */`) を削除しています。

```console
$ sed 's/\/\*\+\([^*]\|\*[^/]\)*\*\+\///g' sample.c
```

手抜きなので、複数行にまたがるコメントはうまく処理できません。
正規表現は `/\*+([^*]|\*[^/])*\*+/` ですが、sed 用のエスケープ処理が入っているため見にくくなっています。

