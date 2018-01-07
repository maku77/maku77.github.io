---
title: 他の人が作成したブランチ上で作業する
date: "2016-04-05"
---

GitHub などの共有リポジトリを使用して複数人数で開発を進めている場合、他のメンバが作成したブランチ（`git push` されたブランチ）をチェックアウトして共同作業したいことがあります。
そのような場合は、下記のような手順で、ローカルブランチとして作業できるようにします。


### 1. 共有リポジトリに push されているブランチを持ってくる

まずは、他の人が共有リポジトリ上に作成したブランチをローカルに持ってくる必要があります。

```
$ git fetch --prune
```

（`--prune` を付けると、共有リポジトリ側で削除されているブランチをローカルからも削除してくれます）


### 2. 対象のブランチを持ってこれたか確認

ブランチの一覧を表示し、対象のブランチをうまく fetch してこれたかを確認します。

```
$ git branch -r
```


### 3. 作業用のローカルブランチを作成

ここでは、他の人が作成した `topic-branch` というブランチをもとに、同名のブランチを作成します。

```
$ git branch topic-branch origin/topic-branch
Branch topic-branch set up to track remote branch topic-branch from origin.
```


### 4. 作成したローカルブランチをチェックアウトとして編集

あとは、普段通り `topic-branch` ブランチ上で編集作業を行い、コミットしていけば OK です。

```
$ git checkout topic-branch
（編集…）
$ git commit
```


### 5. 共有リポジトリに push

共同作業している人に変更内容を共有するために `git push` します。

```
$ git push origin topic-branch
```

