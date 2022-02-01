---
title: "Git やり直し: main ブランチに入れてしまったコミットを別のブランチに移す"
url: "/p/wdmu3ah"
date: "2022-02-01"
---

何をするか？
----

あるコードをトピックブランチにコミットしないといけないのに、間違えて `main`（旧名 `master`）ブランチに入れてしまった場合の修正方法です。
ここでは、`main` ブランチに入れてしまった直近 2 つのコミットを、`new-feature` というブランチに移動してみます。


手順
----

### (1) main ブランチの現状を確認する

まず、main ブランチのコミットログを確認しておきます。

```console
$ git switch main
$ git log --oneline
3d40884 (HEAD -> main) Commit 4
daba4f2 Commit 3
256b4f0 Commit 2
696a835 (origin/main, origin/HEAD) Commit 1
```

一番上の `Commit 4` が最新のコミットで、一番下の `Commit 1` が GitHub などから pull してきたコミットであることがわかります。
`Commit 2` から `Commit 4` までは、ローカルだけでコミットされた（まだ push していない）ものなので、今回のような方法でコミット履歴を修正する余地があります。

### (2) new-feature ブランチを用意する

ここでは、`main` に入れてしまった __`Commit 3` と `Commit 4` を別のブランチ (`new-feature`) に移動させたい__ とします。
まず、現在の `main` ブランチと同じ情報を持つ `new-feature` ブランチを作成します。

```console
$ git branch new-feature main
```

すでに `new-feature` ブランチがある場合は、そちらへ `main` ブランチの内容をマージします。

```console
$ git switch new-feature
$ git merge main
```

これで、`main` ブランチと同じ状態の `new-feature` ブランチができました。
`git log` コマンドで 2 つのブランチの内容が等しいことを確認しておきます。

```console
$ git log --oneline --all
3d40884 (HEAD -> main, new-feature) Commit 4
daba4f2 Commit 3
256b4f0 Commit 2
696a835 (origin/main, origin/HEAD) Commit 1
```

### (3) main ブランチのコミットをなかったことにする

前述の作業で、`Commit 3` と `Commit 4` は正しいブランチ (`new-feature`) からたどれるようになったので、もう `main` ブランチからは削除しても大丈夫です。
`main` ブランチは `Commit 2` まで入っているようにしたいので、次のようにしてコミットログを `Commit 2` (ID=256b4f0) まで巻き戻します。

```console
$ git switch main
$ git reset --hard 256b4f0
HEAD is now at 256b4f0 Commit 2
```

巻き戻し先のコミットは、`HEAD~2`（2 つ前のコミット）という形で指定することもできますが、上記のように具体的なコミット ID を指定した方がわかりやすいです。

これで、`main` ブランチには `Commit 2` まで、`new-feature` ブランチには `Commit 4` まで入っている状態（目指していた状態）になりました。

```console
$ git log --oneline --all
3d40884 (new-feature) Commit 4
daba4f2 Commit 3
256b4f0 (HEAD -> main) Commit 2
696a835 (origin/main, origin/HEAD) Commit 1
```

