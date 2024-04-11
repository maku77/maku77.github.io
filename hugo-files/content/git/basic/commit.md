---
title: "Git で変更をローカルリポジトリにコミットする"
url: "p/ez3knei/"
date: "2010-07-17"
tags: ["Git"]
aliases: /git/basic/commit.html
---

git commit で変更をローカルリポジトリにコミットする
----

`git add`、`git rm`、`git mv` などのコマンドによってステージされたファイル（の変更箇所）は、`git commit` でローカルリポジトリにコミットされる対象になります。
コミットを行うときは、必ずコミットコメントを記述する必要があります。
コメントは、下記のように __`-m`__ オプションで指定することができます。

{{< code lang="console" title="コメントを付けてコミット" >}}
$ git commit -m "Correct typos"
{{< /code >}}

複数の段落にわたるコメントを付けたい場合は、`-m` オプションを複数指定します。

{{< code lang="console" title="複数段落のコメントを付ける" >}}
$ git commit -m "First paragraph" -m "Long long description."
$ git log
commit c7617b4d92edd946ae2a3358c321a2c6c81240b8
Author: test <test@example.org>
Date:   Sat Jul 17 17:39:57 2010 +0900

    First paragraph

    Long long description.
{{< /code >}}

`git commit` コマンドに `-m` オプションを指定しなかった場合は、コミットコメントを入力するためにエディタが起動します。

{{< code lang="console" title="エディタを開いてコミットコメントを入力する" >}}
$ git commit
{{< /code >}}

このとき __`-v`__ オプションを指定すると、コミットされるファイルの変更差分 (diff) も同時にエディタ上に表示されます。
ただ、一般的には `git commit` の実行前に `git diff --staged` で差分確認します。

{{< code lang="console" title="エディタ上で差分も表示" >}}
$ git commit -v
{{< /code >}}

`git add` コマンドなどで変更をステージされていないファイルも、すべてコミットしてしまうには、`git commit` のオプションで __`-a`__ を指定します。
ただし、Git の管理下にない（追跡されていない）ファイルはステージの対象になりません。

{{< code lang="console" title="変更されているファイルをすべてコミット" >}}
$ git commit -a
{{< /code >}}

