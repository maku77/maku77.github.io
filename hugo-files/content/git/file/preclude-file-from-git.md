---
title: "Gitメモ: ファイルを削除せずに Git によるバージョン管理の対象から外す (git rm --cached)"
url: "p/qu3d2f9/"
date: "2010-08-27"
tags: ["git"]
aliases: [/git/file/preclude-file-from-git.html]
---

`git rm` コマンドは、デフォルトでは、指定したファイルをインデックスの登録から解除すると同時に、ローカル PC 上のファイル自体を削除してしまいます。

```console
$ git rm sample.txt    # ローカル PC からも削除されてしまう
```
下記のように `git rm` コマンドに **`--cached`** オプションを付けて実行すると、インデックスの登録から解除するだけで、ファイル自体は untracked 状態になってワーキングツリーに残ります。

```console
$ git rm --cached -f sample.txt
```
