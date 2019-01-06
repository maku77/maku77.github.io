---
title: "リモートリポジトリに略称を付ける"
date: "2010-07-22"
---

例えば、何度もあるリポジトリから次のように `git pull` したい場合、

~~~
$ git pull git://example.com/project_a.git
~~~

次のように別名を付けてタイプ数を減らすことができます。

~~~
$ git remote add project_a git://example.com/project_a.git
~~~

これで、`git pull` するときのコマンドを以下のように省略できるようになります。

~~~
$ git pull project_a HEAD
~~~

