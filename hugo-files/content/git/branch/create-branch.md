---
title: "Git でブランチを作成する (git branch)"
url: "p/x9s8k2e/"
date: "2010-07-17"
lastmod: "2022-12-09"
tags: ["Git"]
aliases: /git/branch/create-branch.html
---

現在チェックアウトしているブランチから、新しいブランチを作成するには次のコマンドを使用します。

```console
$ git branch <新しいブランチ名>
```

ブランチ元のブランチを指定して、新しいブランチを作成するには次のようにします。

```console
$ git branch <新しいブランチ名> <既存のブランチ名>
```

{{< code lang="console" title="例: main ブランチから REL-1.0 ブランチを作成する" >}}
$ git branch REL-1.0 main
{{< /code >}}

`git branch` コマンドを引数なしで実行すると、ブランチの一覧を確認できます（`git branch --list` と同等）。

```console
$ git branch
  REL-1.0
* master
```

上記の例では、今回作成された `REL-1.0` と、デフォルトの `master` ブランチが存在することが分かります。`master` の前の `*` は、現在の作業対象（チェックアウトされているブランチ）が `master` ブランチであることを示しています。
作業対象を新しいブランチに切り替えるには、__`git switch`__ コマンドを使用する必要があります。

- 参考: [Git でブランチをチェックアウトする（作業対象のブランチを切り替える）(`git switch/checkout`)](/p/8cwbp3e/)
- 参考: [Git でブランチの作成とチェックアウトを同時に行う (`git switch -c`, `git checkout -b`)](/p/ivbss76/)
- 参考: [Git で他の人が作成したブランチ上で作業する](/p/ewvaoe3/)

ちなみに、ローカルで新しく作成したブランチの内容を GitHub などの中央リポジトリに `git push` するには、次のように実行します。

{{< code lang="console" title="origin リポジトリへ新しいブランチ REL-1.0 を push" >}}
$ git push origin REL-1.0
{{< /code >}}

このとき、__`-u (--set-upstream)`__ オプションを付けて実行すると、リモートブランチ名とローカルブランチ名が対応付けられて、次回からはパラメーターなしで `git push` できるようになります。

```console
git push -u origin REL-1.0
```

