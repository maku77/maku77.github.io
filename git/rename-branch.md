---
title: Git でブランチ名を変更する
created: 2010-07-19
---

Git のブランチ名を変更するには以下のように `branch` コマンドに `-m` オプションを指定して実行します。

```
$ git branch -m new        # 現在チェックアウトしているブランチの名前を new に変更
$ git branch -m new old    # old ブランチの名前を new に変更
```

