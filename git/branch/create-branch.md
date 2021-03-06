---
title: "Git でブランチを作成する"
date: "2010-07-17"
---

デフォルトのブランチ `master` から、リリース用のブランチを作成するには以下のように `git branch` コマンドを使用します。

```
$ git branch REL-1.0 master
```

`REL-1.0` というのが新しいブランチ名、`master` が元になるブランチを示しています。
2 番目の引数を省略すると、現在チェックアウトされているブランチを基に新しいブランチを作成することができます。

次のようにして、現在存在するブランチの一覧を確認できます。

```
$ git branch
  REL-1.0
* master
```

上記の例では、今回作成された `REL-1.0` と、デフォルトの `master` ブランチが存在することが分かります。`master` の前の `*` は、現在の作業対象（チェックアウトされているブランチ）が `master` ブランチであることを示しています。

リモートのサーバに、作成したブランチを `push` するには以下のように明示的に指定する必要があります。

#### origin サーバに新しいブランチ REL-1.0 を push

```
$ git push origin REL-1.0
```

