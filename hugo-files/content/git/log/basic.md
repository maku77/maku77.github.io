---
title: "Gitメモ: Git でのログ表示の基本"
url: "p/hsavjib/"
date: "2010-07-17"
tags: ["git"]
aliases: [/git/log/basic.html]
---

ローカルリポジトリにおけるコミットログを参照するには、`git log` コマンドを使用します。

```console
$ git log           # すべてのコミットログを表示
$ git log -5        # 最近の 5 つのコミットログを表示
$ git log -p        # パッチ作成用の diff（変更内容）も表示
$ git log 613e3de   # 指定したリビジョンのコミットログを表示
```

`git log` コマンドは、現在チェックアウトしているブランチのログを表示します。

