---
title: "Git でブランチをチェックアウトする（作業対象のブランチを切り替える）(git switch/checkout)"
url: "p/8cwbp3e/"
date: "2010-07-17"
lastmod: "2022-12-09"
tags: ["Git"]
aliases: /git/branch/checkout-branch.html
---

作業対象のブランチを切り替えるには、__`git switch`__ コマンドで切り替え先のブランチ名を指定します（昔は `git checkout` コマンドが使われていましたが、現在は役割を明確にした `git switch` コマンドが使われています）。

ブランチを切り替える前に、 `git branch` コマンドを使用して、現在チェックアウトしているブランチと、切り替え先のブランチ名を確認しておくとよいです。

{{< code lang="console" title="ブランチの一覧を確認" >}}
$ git branch
* main
  my-branch
{{< /code >}}

上記の出力で、現在の作業対象が `main` であることが分かります。
次のようにすると、作業対象を `my-branch` ブランチに切り替えることができます。

{{< code lang="console" title="my-branch ブランチに切り替える" >}}
$ git switch my-branch
Switched to branch "my-branch"
{{< /code >}}

{{< code lang="console" title="切り替わっていることを確認する" >}}
$ git branch
  main
* my-branch
{{< /code >}}

これで、作業対象のブランチが `my-branch` に切り替わったので、これ以降の変更作業 (`git commit`) は、`my-branch` ブランチだけに反映され、`main` ブランチには反映されなくなります。

