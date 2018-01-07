---
title: Git でブランチの作成とチェックアウトを同時に行う
date: "2010-07-19"
---

`git checkout` を `-b` オプションをつけて実行すると、ブランチの作成と、そのブランチのチェックアウトを同時に行うことができます。

```
$ git checkout -b mybranch master （ブランチ mybranch の作成＆チェックアウト）
Switched to a new branch 'mybranch'

$ git branch （現在チェックアウトしているブランチの確認）
  master
* mybranch
```

```git branch``` でブランチを作成する場合と同様に、ブランチ元のブランチ名を省略すると、現在チェックアウトされているブランチをもとに新しいブランチを作成します。つまり、上記のコマンドは以下のようにするのと同様です。

```
$ git checkout master （master ブランチをチェックアウト）
Switched to branch 'master'

$ git checkout -b mybranch （現在チェックアウトしているブランチから mybranch を作成してチェックアウト）
Switched to a new branch 'mybranch'

$ git branch （現在チェックアウトしているブランチの確認）
  master
* mybranch
```

