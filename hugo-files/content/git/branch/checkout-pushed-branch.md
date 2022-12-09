---
title: "Git で他の人が作成したブランチ上で作業する"
url: "p/ewvaoe3/"
date: "2016-04-05"
lastmod: "2022-12-09"
tags: ["Git"]
aliases: /git/branch/checkout-pushed-branch.html
---

GitHub などの共有リポジトリを使用して複数のメンバーで開発を進めている場合、他のメンバーが作成したブランチ（`git push` されたブランチ）をチェックアウトして共同作業したいことがあります。
そのような場合は、下記のような手順でローカルブランチを作成して編集作業を進めます。


1. 共有リポジトリに push されているブランチをフェッチする (`git fetch`)
2. 作業用のローカルブランチを作成する (`git branch`)
3. ローカルブランチをチェックアウトして編集作業を行う (`git checkout`, `git commit`)
4.  変更内容を共有リポジトリに push する (`git push`)


共有リポジトリに push されているブランチをフェッチする
----

まずは、他の人が共有リポジトリ上に作成したブランチをローカルに持ってくる必要があります（`--prune` オプションを付けると、共有リポジトリ側で削除されているブランチをローカルからも削除してくれます）。

```console
$ git fetch --prune
```

リモートトラッキングブランチの一覧を表示し、対象のブランチをうまく fetch してこれたかを確認しておきます。

```console
$ git branch -r
  origin/HEAD -> origin/main
  origin/change-date-format
  origin/fix-lint-warnings
  origin/main
```

リモートトラッキングブランチ名は、`<リポジトリ>/<ブランチ>` という構成になっていますが、clone 元のリポジトリ名は `origin` というエイリアス名で指定できるようになっています。


作業用のローカルブランチを作成する
----

リモートトラッキングブランチは、あくまでリモートリポジトリ側の内容を追跡するためのものなので、ここに直接修正を加えていくことはできません。
そこで、リモートトラッキングブランチをベースにして、ローカルブランチを作成する必要があります。
次の例では、リモートトラッキングブランチ `origin/change-date-format` と同名の `change-date-format` というローカルブランチを作成しています。

```console
$ git branch change-date-format origin/change-date-format
Branch 'change-date-format' set up to track remote branch 'change-date-format' from 'origin'.
```

次のようにして、正しくローカルブランチが作成されていることを確認しておきます。

```console
$ git branch
  change-date-format
* main
```


ローカルブランチをチェックアウトして編集作業を行う
----

あとは、普段通りブランチをチェックアウトして編集作業を行い、コミットしていけば OK です。

```console
$ git checkout change-date-format
（編集…）
$ git commit
```


変更内容を共有リポジトリに push する
----

ローカルブランチでの変更作業が終了したら、共有リポジトリ（GitHub など）へ `git push` して、他のメンバーに編集内容を共有します。

```console
$ git push origin change-date-format
```

ちなみに、リモートトラッキングブランチから作成したブランチの内容を `git push` する場合は、`-u (--set-upstream)` オプションを指定してブランチ名を対応付ける必要はありません。
ブランチを作成した時点で、どのリモートブランチと対応付けるべきかが分かっているからです。

