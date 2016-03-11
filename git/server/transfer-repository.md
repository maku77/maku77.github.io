---
title: Git リポジトリを移行する
created: 2014-05-19
---

GitHub で管理していたリポジトリを、イントラの Git サーバに移行するときなどの例です。

#### 1. バックアップ

```
$ git clone --mirror git@github.com:maku/proj.git
$ zip -r proj-git.zip proj.git
```

#### 2. 新規サーバ内に共有リポジトリとして展開

```
$ unzip proj-git.zip
$ cd proj.git
$ git --bare init
```

