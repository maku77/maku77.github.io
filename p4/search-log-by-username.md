---
title: "あるユーザが行った submit のログを調べる"
date: "2008-10-07"
---

下記の例では、ユーザ `joe` が、カレントディレクトリ以下で submit したチェンジリストのログを表示しています。

~~~
$ p4 changes -u joe ... | awk '{ system("p4 describe -s " $2) }'
~~~

最初に `p4 changes` コマンドで、チェンジリスト番号の一覧を取得し、`p4 describe` コマンドの引数としてそのチェンジリスト番号を渡しています。

