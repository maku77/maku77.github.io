---
title: "Git でブランチやタグの作者を調べる"
date: "2014-09-18"
---

トピックブランチを使った開発手法を適用していると、トピックブランチの削除忘れなどにより、ブランチが散乱してくることがあります。
このような場合は、誰が作成したブランチが放置されているのか調べる必要があります。
下記は、手っ取り早くブランチの作者を調べる例です。

まずは、サーバ上で削除済みのブランチを、ローカルのリモートトラッキングブランチからも削除しておきます。

```
$ git fetch --prune
```

次にブランチやタグの作者を調べます。

```
$ git for-each-ref --format=%(authorname)%09%09%(refname) | sort
```
