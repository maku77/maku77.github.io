---
title: "Perlメモ: ディレクトリを作成する"
url: "p/xipywm6/"
date: "2008-03-25"
tags: ["perl"]
aliases: ["/perl/file/mkdir.html"]
---

ディレクトリを新しく作成するには、`mkdir` 関数を使用します。

```perl
mkdir 'datadir', 0755 or die "Cannot create 'datadir': $!";
```

`mkdir` の第 2 引数には作成するディレクトリのパーミッションを指定します。

