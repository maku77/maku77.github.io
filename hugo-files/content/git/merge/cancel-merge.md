---
title: "Gitメモ: マージ作業に失敗したときにやり直す (git reset)"
url: "p/49yjwoe/"
date: "2010-11-16"
tags: ["git"]
aliases: [/git/merge/cancel-merge.html]
---

例えば、トピックブランチでの変更を master ブランチに以下のように間違えてマージしたとします。

```console
$ git merge mytopic
```
この場合、十分に古いコミットまで master ブランチを `git reset` してから、origin/master にリセットすれば元に戻すことができます。

```console
$ git reset a60676b   ← 十分に古いコミット
$ git reset origin    ← 中央リポジトリの内容相当にリセット
```
実際に master ブランチの内容が、origin/master と同じ内容に戻ったかを調べるには以下のようにします。

```console
$ git diff master...origin
```
もっとスマートなやり方がありそう。。。

