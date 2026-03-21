---
title: "Gitメモ: Git やり直し: 直前のコミットのコメントを修正する (git commit --amend)"
url: "p/wyq4tea/"
date: "2015-05-17"
tags: ["git"]
aliases: [/git/git-modify-comment.html]
---

直前の `git commit` において、コメントを typo してしまった場合などは、下記のようにして手っ取り早く修正することができます。

```console
$ git commit --amend
```

とするとエディタ上でコミット内容が表示されるので、コメント部分を修正してエディタを閉じれば OK です。
コメントが一行だけの場合は、下記のようにコマンドラインから直接コメント入力することができます。

```console
$ git commit --amend -m 'Introduce a zip code validator'
```

