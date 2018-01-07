---
title: git-svn を使って Git と Subversion を連携する
date: "2010-12-07"
---

git-svn とは
----

git-svn を使用すると、Subversion のリポジトリに対して、Git クライアントからチェックアウト、コミットなどを実行できるようになります。
Subversion 上でコミットされた変更履歴などを、そのまま Git クライアントから参照可能なログとしてアクセスすることができます。
Subversion から Git へのリポジトリ移行などに使用することができます。


Subversion のサーバから Git にインポートする
----

~~~
$ git svn clone -s --prefix svn/ svn://repo/myproject .
~~~

上記のようにすることで、Subversion で行われたすべてのコミット履歴を `git log` で参照できるようになります。
最後のカレントディレクトリを示す `.` を省略すると、URL で指定した `myproject` という名前のディレクトリが自動的に作成されます。

上記のように `--prefix` オプションを指定することで、わかりやすいリモートトラッキングブランチ名を付けることができます。
ここでは、`svn/` と指定することで、Subversion から clone してきたことを簡単に判別できるようにしています。

~~~
$ git branch -a
* master
  remotes/svn/trunk
  remotes/svn/branch1
  remotes/svn/branch2
  ...
~~~

`git svn clone` が完了すると、以下のように `git log` コマンドを使用して、Subversion で行われてきたコミットの履歴を参照することができるようになります。

~~~
$ git log svn/trunk
~~~

ただし、`git svn clone` した時点ではリモートトラッキングブランチが作成されるだけなので、ローカルファイルとしてコードを参照した場合は、以下のような感じでローカルブランチを作成し、チェックアウトする必要があります。

~~~
$ git checkout -b trunk svn/trunk
~~~


### インポートするリビジョン範囲の絞り込み

以下のように、Subversion から取り込むリビジョンの範囲を `-r` オプションで指定することもできます。
このようにすると、HEAD の内容は rev.2000 になり、`git log` で表示される履歴の範囲は rev.1000～2000 に絞り込まれます。

~~~
$ git svn clone -s --prefix svn/ svn://repo/myproject -r1000:2000    # 取り込むリビジョン履歴を 1000～2000 に制限
~~~


Subversion から取り込んだリポジトリのサイズを圧縮する
----

Subversion の情報を Git にインポートしたら、ディスク容量をたくさん食っている状態になります。
なので、すぐに `git gc` するのがおすすめです。

~~~
$ git gc --aggressive
~~~

オプションで `--aggressive` を指定すると、非常に時間がかかります（数万のコミットで数時間）が、ディスクの使用量はさらに最適化されます。


Subversion サーバに新たにコミットされた変更を取り込む
----

Subversion サーバ上に新しくコミットされた変更を取り込むには、`git svn rebase` コマンドを使用します。

~~~
$ git svn rebase
~~~

この作業は、`git svn fetch` をした後に、`git rebase` を実行するのと同様です。


Git 上にコミットした変更を Subversion にプッシュする
----

git-svn を使用するのであれば、Subversion から Git に完全に移行してしまうのをオススメしますが、何らかの理由で Subversion を使い続けなければならないこともあります。
Git 上での変更を Subversion 側にコミットするには、`git svn dcommit` を実行します。
`git svn dcommit` を実行すると、Subversion の情報を取得するために、自動的に手元のコピーが rebase されます。

このような作業を慎重に行いたいのであれば、まずは `--dry-run` オプションをつけて実行し、何が起こるかのシミュレートをしてみるとよいでしょう。

