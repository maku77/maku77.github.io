---
title: "Gitメモ: Git でコミットログを絞り込んで表示する"
url: "p/xsbjsnr/"
date: "2010-07-19"
tags: ["git"]
aliases: [/git/log/filter-logs.html]
---

時間でコミットログを絞り込む
----

`git log` コマンドでは、時間的な条件を加えてログを絞り込むことができます。

```console
$ git log --since="5 hours"      # ここ 5 時間以内にコミットされたもの
$ git log --since="2010-07-15"   # 2010年7月15日以降にコミットされたもの
$ git log --before="5 hours"     # 5 時間前より前にコミットされたもの
```

コミットの範囲を指定してログを絞り込む
----

コミット ID や、タグなどを `..` でつなげて指定することで、2 つのリビジョン間のコミットログを参照できます。
必ず古いリビジョンから指定します。
1 つ目に指定したリビジョンに関しては検索対象に含まれず、そのリビジョンより後にコミットされたものから表示されます。

```console
$ git log 32dfeg..7ab5191    # 32dfeg 以降 ～ 7ab5191 までのコミット
$ git log 32dfeg..HEAD       # 32dfeg 以降すべてのコミット
$ git log 32dfeg..           # 同上
$ git log tag1..tag2         # タグ tag1 以降 ～ tag2 までのコミット
```

コミットした人を指定してログを絞り込む
----

Author や Committer で絞り込んで表示することも可能です。

{{< code lang="console" title="例: author あるいは committer に Sean を含むコミットだけを表示する" >}}
$ git log --author=Sean
$ git log --committer=Sean
{{< /code >}}

