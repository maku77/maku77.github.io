---
title: "Git で変更をローカルリポジトリにコミットする"
date: "2010-07-17"
---

git commit で変更をローカルリポジトリにコミットする
----

`git add`、`git rm`、`git mv` などのコマンドによってステージされたファイル（の変更箇所）は、`git commit` でローカルリポジトリにコミットされる対象になります。
コミットを行うときは、必ずコミットコメントを記述する必要があります。
コメントは、下記のように `-m` オプションで指定することができます。

~~~
$ git commit -m "Correct typos"
~~~

複数の段落にわたるコメントを付けたい場合は、`-m` オプションを複数指定します。

~~~
$ git commit -m "First paragraph" -m "Long long description."
$ git log
commit c7617b4d92edd946ae2a3358c321a2c6c81240b8
Author: test <test@example.org>
Date:   Sat Jul 17 17:39:57 2010 +0900

    First paragraph

    Long long description.
~~~

`git commit` コマンドに `-m` オプションを指定しなかった場合は、コミットコメントを入力するためにエディタが起動します。
この時、`-v` オプションを指定すると、コミットされるファイルの変更差分 (diff) も同時にエディタ上に表示されます。

~~~
$ git commit -v
~~~

`git add` コマンドなどで変更をステージされていないファイルも、すべてコミットしてしまうには、`git commit` のオプションで `-a` を指定します。
ただし、Git の管理下にない（追跡されていない）ファイルはステージの対象になりません。

~~~
$ git commit -a
~~~

