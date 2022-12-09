---
title: "Git でブランチ名を変更する (git branch -m)"
url: "p/9bh2ody/"
date: "2010-07-19"
lastmod: "2022-12-09"
tags: ["Git"]
aliases: /git/branch/rename-branch.html
---

Git のブランチ名を変更するには以下のように __`git branch -m`__ コマンドを使用します。

{{< code lang="console" title="ブランチ名を変更する" >}}
$ git branch -m new      # 現在チェックアウトしているブランチの名前を new に変更
$ git branch -m old new  # old ブランチの名前を new に変更
{{< /code >}}

GitHub などのリモートリポジトリに `old` リポジトリがすでに push されている場合は、次のようにして中央リポジトリ側のリポジトリ名も変更する必要があります。

{{< code lang="console" title="GitHub 側のブランチ名を変更する" >}}
$ git push origin new   # 新しいブランチ new を push
$ git push origin :old  # 古いブランチ old を削除
{{< /code >}}

- 参考: [Git でリモートブランチを削除する (`git push origin :xxx`, `git fetch --prune`)](/p/cv5pi7a/)

