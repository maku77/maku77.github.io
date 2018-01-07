---
title: ディレクトリ内のファイルを順に処理する
date: "2011-02-06"
---

find コマンドの結果を使用する方法
====

下記の例では、`find` コマンドによって見つかったファイルを `while` ループで順番に処理しています。

```bash
find . -type f | while read x; do
    echo $x
done
```

