---
title: "Git でブランチの作成とチェックアウトを同時に行う (git switch -c, git checkout -b)"
url: "p/ivbss76/"
date: "2010-07-19"
lastmod: "2022-12-09"
tags: ["Git"]
aliases: /git/branch/create-and-checkout-branch.html
---

__`git switch -c`__ コマンドを使用すると、ブランチの作成と、そのブランチへの切り替え（チェックアウト）を同時に行うことができます（昔は同様の振る舞いをする `git checkout -b` コマンドが使っていました）。

{{< code lang="console" title="ブランチの作成と切り替えを一度に行う" >}}
$ git switch -c mybranch  # ブランチ mybranch の作成＆チェックアウト
Switched to a new branch 'mybranch'

$ git branch  # 現在チェックアウトしているブランチの確認
  main
* mybranch
{{< /code >}}

`git branch` コマンドでのブランチ作成と同様、ブランチ元のブランチ名を指定することもできます。

```console
$ git switch -c mybranch main
```

