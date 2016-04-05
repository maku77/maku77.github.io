---
title: detached HEAD 状態へのチェックアウトとは
created: 2010-08-23
---

ブランチの最新コードをチェックアウトする場合は、

```
$ git checkout ＜ブランチ名＞
```

としますが、そのブランチの最新コードではなく、ある特定のバージョンのソースコードを少しだけ覗いてみたいということがあります。
そのような場合は、ブランチ名の代わりにコミット名を指定してチェックアウトすることができます。

コミット名を指定して `git checkout` を実行すると、どのローカルブランチもチェックアウトしていない **detached HEAD** という状態になります。
例えば、以下のように `git checkout` すると、この状態になります。

```
$ git checkout 0d3ce61        # コミット ID を指定
$ git checkout HEAD^0         # 最新のコミットを指定
$ git checkout HEAD^1         # １つ前のコミットを指定
$ git checkout v2.6.18        # タグで示されるコミットを指定
$ git checkout origin/master  # リモートトラッキングブランチを指定
```

`HEAD` が detached された状態で `git branch` を実行すると、どのブランチにもいないことを示す `(no branch)` が表示されます。

```
$ git branch
* (no branch)
  master
  mybranch
```

detached HEAD 状態でも `git commit` などのコマンドを実行できますが、どのブランチにもいない状態なので、ブランチの head は更新されません。
再びローカルブランチ名を指定してチェックアウトすると、detached HEAD で行っていた変更を破棄してブランチの最新状態に戻ることができます。

