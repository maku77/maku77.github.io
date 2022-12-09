---
title: "Git でブランチを削除する (git branch -d/-D)"
url: "p/stpfje9/"
date: "2010-07-17"
lastmod: "2022-12-09"
tags: ["Git"]
aliases: /git/branch/delete-branch.html
---

不要になったブランチを削除するには、__`git branch -d`__ コマンドでブランチ名を指定します。
削除対象のブランチが作業対象になっていると削除できないので、先に `git switch` コマンドで別のブランチに切り替えてから削除を実行します。

{{< code lang="console" title="mybranch ブランチを削除する" >}}
$ git switch main
$ git branch -d mybranch
Deleted branch mybranch (was 1cc229d3)
{{< /code >}}

削除しようとしているブランチの変更内容が、ブランチ元にマージされていない場合、以下のようなエラーが表示されます。

```
error: The branch 'mybranch' is not an ancestor of your current HEAD.
If you are sure you want to delete it, run 'git branch -D mybranch'
```

この場合は、先にブランチ元にマージを実行するか、あるいは、変更内容が完全に消えても構わない場合は `-d` オプションの代わりに __`-D`__ オプションを指定して実行します。

{{< code lang="console" title="マージされていないブランチを強制的に削除する" >}}
$ git branch -D mybranch
{{< /code >}}

- 参考: [Git でリモートブランチを削除する (`git push origin :xxx`, `git fetch --prune`)](/p/cv5pi7a/)

