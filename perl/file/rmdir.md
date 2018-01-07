---
title: ディレクトリを削除する
date: "2008-03-25"
---

ディレクトリを削除するには、`rmdir` 関数を使用します。

```perl
rmdir 'tempdir' or warn "Cannot delete 'tempdir': $!";
```

