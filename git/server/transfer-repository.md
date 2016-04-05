---
title: Git リポジトリを移行する
created: 2014-05-19
---


GitHub 内のリポジトリを GitHub の別のリポジトリへ移行する
----

GitHub 内のリポジトリを別のリポジトリへ移動させるときは下記のようにします。
GitHub 上で、空のリポジトリ dst.git を作成済みだと仮定しています。

### src.git から dst.git への移行

```sh
$ git clone --mirror https://github.com/user/src.git
$ cd src.git
$ git push --mirror https://github.com/user/dst.git
```

ここでは、GitHub 内でのリポジトリの移行を行っていますが、異なるサーバ間の移行（例えば、GitLab から GitHub への移行）も可能です。
タグやブランチなどもまとめてお引越しできます。


GitHub のリポジトリを社内サーバ（イントラ）の Git サーバへ移行する
----

### 1. バックアップ

```
$ git clone --mirror git@github.com:maku/proj.git
$ zip -r proj-git.zip proj.git
```

### 2. 新規サーバ内に共有リポジトリとして展開

```
$ unzip proj-git.zip
$ cd proj.git
$ git --bare init
```

