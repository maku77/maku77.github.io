---
title: ローカルディレクトリをリモートリポジトリとして使用する
created: 2010-08-23
---

以下のように、ディレクトリ名指定でリポジトリを `git clone` することで、物理的にはローカルにあるリポジトリをリモートリポジトリとして扱うことができます。

```
$ git clone $HOME/gitwork/myrep
Initialized empty Git repository in /home/joe/test/myrep/.git/
```

これを利用して、1 つのマシン上で `git push` や `git pull` などの動作をテストすることも可能です。

上記のように `clone` して出来たリポジトリの `.git/config` の内容は以下のようになっています。
`remote.origin.url` 変数の値が、`clone` 元のローカルディレクトリを示していることが分かります。

```
[core]
    repositoryformatversion = 0
    filemode = true
    bare = false
    logallrefupdates = true
[remote "origin"]
    url = /home/joe/gitwork/myrep
    fetch = +refs/heads/*:refs/remotes/origin/*
[branch "master"]
    remote = origin
    merge = refs/heads/master
```

