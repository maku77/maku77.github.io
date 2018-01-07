---
title: ディレクトリを作成する
date: "2008-03-25"
---

ディレクトリを新しく作成するには、`mkdir` 関数を使用します。

```perl
mkdir 'datadir', 0755 or die "Cannot create 'datadir': $!";
```

`mkdir` の第 2 引数には作成するディレクトリのパーミッションを指定します。

