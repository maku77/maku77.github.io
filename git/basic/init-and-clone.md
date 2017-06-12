---
title: Git の作業ツリーを作成する (git init, git clone)
created: 2010-07-17
---

Git の作業ツリーの作成方法は２つ
----

Git の作業ツリーを作成するには、以下の２種類の方法があります。

- 新しくローカルリポジトリを作成する (`git init`)
- リモートの Git リポジトリをコピーしてきて、作業ディレクトリとローカルリポジトリを作成する (`git clone`)


git init で新しく作業ツリーを作成する
----

完全に新規の作業ツリーを作成する場合と、既存のファイル群を Git で管理する場合は、`git init` コマンドを使用して、あるディレクトリ以下を Git の管理下に置きます。

~~~
$ mkdir myproject
$ cd myproject
$ git init
Initialized empty Git repository in /home/john/myproject/.git
~~~

この `git init` コマンドにより、カレントディレクトリに `.git` という Git の管理ディレクトリが作成され、カレントディレクトリ以下が Git の管理対象に入ります。
この時点ではローカルリポジトリの中は空っぽなので、このディレクトリ（作業ツリー）内でファイルを作成し、`git add` や `git commit` コマンドなどを使用してリポジトリにファイルをコミットしていきます。

既にソースコードの入っているディレクトリを Git で管理する場合も、まずは上記と同様の手順でリポジトリ (`.git`) を作成します。
ただし、`git init` しただけでは、既存のファイルはリポジトリの管理下に入らないので、以下のように既存のファイル群をコミットしてやる必要があります。

~~~
$ cd myproject
$ git init
$ git add .
$ git commit -m "First commit."
~~~

コミットの履歴は `git log` で確認できます。


git clone で別のマシンのリポジトリから作業ツリーを作成する（コードをコピーしてくる）
----

#### 例: Android のコードを取得してみる

~~~
$ cd ~/gitwork
$ git clone git://android.git.kernel.org/platform/development.git
Initialized empty Git repository in /home/john/gitwork/development/.git
got 36a38df99cd7485db6a83c4f8c7b329c24b9b6e8
...
~~~

上記のようにすると、カレントディレクトリに `development` ディレクトリがローカルにコピーされます。
`development` ディレクトリは作業ツリーであり、そのルートに更新履歴を示すリポジトリのコアとなる `.git` ディレクトリも入っています。

`git clone` を実行しようとして以下のようなエラーが出る場合、git プロトコルの代わりに `http` プロトコルを指定するとうまく取得できたりします。

~~~
Initialized empty Git repository in /home/john/gitwork/development/.git
fatal: Unable to look up android.git.kernel.org (port 9418) (Name or service not known)
~~~

