---
title: "grep で OR 検索する"
date: 2015-04-15
---

`grep` コマンドのオプション **`-e`** を複数回使用することで、複数ワードの OR 検索をすることができます。

#### aaa か bbb を含む行を検索する

```
$ grep -e 'aaa' -e 'bbb' file.txt
```

正規表現を使用して OR 検索する方法もあります。

```
$ grep -E 'aaa|bbb' file.txt
$ egrep 'aaa|bbb' file.txt
```

