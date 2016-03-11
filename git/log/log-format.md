---
title: Git でログ形式をカスタマイズして出力する
created: 2010-07-19
---

`git log` コマンドを使用するときに、`pretty` オプションを指定することによってログの出力形式をカスタマイズすることができます。

```
$ git log --pretty=onelie            # 各コミットを一行で表示する
$ git log --pretty=format:"%h %s"    # 短い SHA1 ハッシュ値とコミットログの一行目を表示
```

