---
title: "Git でブランチやタグの作者を調べる (git for-each-ref)"
url: "p/o2m2ft7/"
date: "2014-09-18"
lastmod: "2022-12-09"
tags: ["Git"]
aliases: /git/branch/who-created-branch.html
---

トピックブランチを使った開発手法を適用していると、トピックブランチの削除忘れなどにより、ブランチが散乱してくることがあります。
このような場合は、誰が作成したブランチが放置されているのか調べる必要があります。
下記は、手っ取り早くブランチの作者を調べる方法です。

まずは、GitHub（中央リポジトリ）上で削除済みのブランチは、ローカルのリモートトラッキングブランチからも削除しておきます。

{{< code lang="console" title="不要なリモートトラッキングブランチを削除" >}}
$ git fetch --prune
{{< /code >}}

あとは、次のようにすれば、ブランチやタグ (refs) の作者を調べることができます。

{{< code lang="console" title="ブランチやタグの作者を調べる" >}}
$ git for-each-ref --format=%(authorname)%09%09%(refname) | sort
maku        refs/heads/main
maku        refs/heads/my-branch
john        refs/remotes/origin/apply-eslint
ripper      refs/remotes/origin/gh-pages
maku        refs/remotes/origin/HEAD
maku        refs/remotes/origin/main
{{< /code >}}

