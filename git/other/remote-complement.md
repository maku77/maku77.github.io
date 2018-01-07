---
title: git pull の引数省略の仕組み
date: "2010-08-23"
---

`git pull` を実行するときに、

~~~
$ git pull git://git.example.com/aaa/bbb.git master
~~~

としないで、リモートリポジトリの URL と、ブランチ名を省略し、

~~~
$ git pull
~~~

とした場合に何が行われるかは、`.git/config` ファイルの設定によります。

~~~
$ cat .git/config
...
[remote "origin"]
    url = git://git.example.com/aaa/bbb.git
    fetch = +refs/heads/*:refs/remotes/origin/*
[branch "master"]
    remote = origin
    merge = refs/heads/master
~~~

上記の `[branch "master"]` というセクションは、現在チェックアウトしているブランチが `master` の場合に使用する設定を示しています。
例えば、この例では `branch.master.remote` 変数が `origin` となっているので、`git pull` するときにリポジトリの指定を省略すると、`origin` というリモートリポジトリがデフォルトで使用されます。
つまり、`master` ブランチがカレントのときは、以下の２つが同じ意味になります。

~~~
$ git pull
$ git pull origin
~~~

さらに、この `origin` というリモートリポジトリ名が実際にどの URL を示すのかは、`remote.origin.url` 変数に設定されています。
つまり、以下の２つが同じ意味になります。

~~~
$ git pull origin
$ git pull git://git.example.com/aaa/bbb.git
~~~

カレントブランチが `master` の場合に、リモートリポジトリのどのブランチをマージするかは、`branch.master.merge` 変数に指定されています。
`.git/config` ファイルの中では、ブランチ名はフルネームで `refs/heads/master` と指定されています（正確には `.git` ディレクトリから見た場合の、ブランチを示すリファレンスファイルへの相対パスです）。

これらの判断が裏で行われているため、現在のブランチが `master` の場合に以下のコマンドは同じ意味になります。

~~~
$ git pull
$ git pull git://git.example.com/aaa/bbb.git master
~~~

