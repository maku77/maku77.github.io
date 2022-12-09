---
title: "Git の detached HEAD 状態へのチェックアウトとは"
url: "p/whv8ues/"
date: "2010-08-23"
lastmod: "2022-12-09"
tags: ["Git"]
aliases: /git/branch/detached-head.html
---

ある Git ブランチのコードをチェックアウトするには、

```console
$ git switch <ブランチ名>
```

のように実行しますが、そのブランチの最新コードではなく、ある特定のバージョン（コミット ID）のソースコードを少しだけ覗いてみたいということがあります。
そのような場合は、ブランチ名の代わりにコミット ID を指定してチェックアウトすることができます。

コミット名を指定して `git checkout` を実行すると、どのローカルブランチもチェックアウトしていない __detached HEAD__ という状態になります（`HEAD` が detached されているということ）。
例えば、以下のように `git checkout` すると、この状態になります。

```console
$ git checkout 0d3ce61      # コミット ID を指定
$ git checkout HEAD^0       # 最新のコミットを指定
$ git checkout HEAD^1       # １つ前のコミットを指定
$ git checkout v2.6.18      # タグで示されるコミットを指定
$ git checkout origin/main  # リモートトラッキングブランチを指定
```

`HEAD` が detached された状態で `git branch` を実行すると、どのブランチにもいないことを示す `(no branch)` が表示されます。

```
$ git branch
* (no branch)
  main
  mybranch
```

detached HEAD 状態でも `git commit` などのコマンドを実行できますが、どのブランチにもいない状態なので、どのブランチの head も更新されません。
再びローカルブランチ名を指定してチェックアウトすると、detached HEAD で行っていた変更を破棄してブランチの最新状態に戻ることができます。

