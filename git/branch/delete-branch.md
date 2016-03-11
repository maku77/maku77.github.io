---
title: Git でブランチを削除する
created: 2010-07-17
---

不要になったブランチを削除するには、`git branch` のオプションに `-d` を指定します。削除対象のブランチが作業対象になっていると削除できないので、先に `git checkout` コマンドで `master` ブランチに切り替えてから削除を実行します。

```
$ git checkout master
$ git branch -d MY_BRANCH
Deleted branch MY_BRANCH.
```

削除しようとしているブランチの変更内容が、ブランチ元にマージされていない場合、以下のようなエラーが表示されます。

```
error: The branch 'MY_BRANCH' is not an ancestor of your current HEAD.
If you are sure you want to delete it, run 'git branch -D MY_BRANCH'
```

この場合は、先にブランチ元にマージを実行するか、あるいは、変更内容が完全に消えても構わない場合は `-d` オプションの代わりに `-D` オプションを指定して実行します。

#### ブランチの強制削除

```
$ git branch -D MY_BRANCH
```

