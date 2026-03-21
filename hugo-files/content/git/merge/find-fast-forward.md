---
title: "Gitメモ: マージベースとなったコミットを見つける（fast-forward の関係になっているか調べる） (git merge-base)"
url: "p/2u3igu6/"
date: "2010-08-23"
tags: ["git"]
aliases: [/git/merge/find-fast-forward.html]
---

ある 2 つのコミットのブランチ元となるコミット（マージベース）を調べるには、**`git merge-base`** コマンドを使用できます。

```console
$ git merge-base master feature
fff0c132210d4d1c4517d78802c7fd5c93d4f975
```

上記の例では、master ブランチと feature ブランチは、共通の `fff0c13` というコミットから分岐していることを示しています。

```console
$ git merge-base HEAD 0d3ce61
0d3ce6138f57590d67f7543435d03972be3b3d4d
```

上記の例では、2 番目に指定したコミット番号と同じものがマージベースとして表示されています。
このような場合、2 つのコミットは **fast forward** の関係にあり、コミット `0d3ce61` へのリファレンスを前に進めるだけで、HEAD と同じ状態になることを示しています。

