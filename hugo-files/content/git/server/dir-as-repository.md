---
title: "Gitメモ: ローカルディレクトリをリモートリポジトリとして使用する"
url: "p/jkbezsr/"
date: "2010-08-23"
tags: ["git"]
aliases: [/git/server/dir-as-repository.html]
---

以下のように、ディレクトリ名指定でリポジトリを `git clone` することで、物理的にはローカルにあるリポジトリをリモートリポジトリとして扱うことができます。

```console
$ git clone $HOME/gitwork/myrep
Initialized empty Git repository in /home/joe/test/myrep/.git/
```

これを利用して、1 つのマシン上で `git push` や `git pull` などの動作をテストすることができます。

上記のように `clone` してできたリポジトリの `.git/config` の内容は以下のようになっています。
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

