---
title: "Python の実行環境を切り替えて使用する (virtualenv) Python3.2まで"
date: "2018-04-28"
description: "virtualenv コマンドを使用すると、仮想的な Python の実行環境を作成することができます。"
---

（追記）Python 3.3 以降は、virtualenv と同様の機能を提供する __venv__ モジュールが標準搭載されたので、そちらを使うようにしましょう。

- [Python の実行環境を切り替えて使用する (venv)](./venv.html)


virtualenv とは
----

- [Virtualenv](https://virtualenv.pypa.io/)

Python 製のツールを使おうとすると、使用可能な Python のバージョンが制限されていたり、依存する Python パッケージを pip コマンドでたくさんインストールしなければいけなかったりします。
こういったツールを実行するために、パッケージをどんどんインストールしていくと、PC 内の Python 環境がぐちゃぐちゃになってしまいます（システムの `site-packages` ディレクトリに大量のパッケージがインストールされて管理できなくなる）。

そこで便利なのが、独立した Python 環境を作成することができる **virtualenv** です。
virtualenv で作成した仮想環境の中では、特定のバージョンの Python を使用することができ、閉じた環境内に Python パッケージをインストール (`pip install`) することができます。
もし、その環境が必要なくなったら、仮想環境ごと削除してしまえば、PC 内のグローバルな Python 実行環境はクリーンなままです。

例えば、何らかの Python 製ツール app があったとして、そのツールを使用するにはいくつかの Python パッケージをインストールしなければならないとします。
virtualenv を使用して、その Python 製ツールを使用するための環境を作る流れは以下のようになります。

1. app ディレクトリに移動する (`cd app`)
2. app 用の Python 仮想環境を作成する (`virtualenv 環境名`)
3. 仮想環境に入る (`source 環境名/bin/activate`)
4. app に必要な Python パッケージをインストール (`pip install XXX`)
5. app を使う (`python app.py`)
6. 仮想環境を抜ける (`deactivate`)


virtualenv のインストール
----

virtualenv は、pip コマンドで簡単にインストールすることができます。

~~~
$ pip install virtualenv （うまくいかない場合は sudo をつけてみる）
~~~

virtualenv コマンドが実行できるようになればインストール成功です。

~~~
$ virtualenv --version
15.2.0
~~~


virtualenv で Python 実行環境を作成／使用／削除する
----

virtualenv コマンドで新しい Python 実行環境を作成するには、下記のように実行します。
`ENV` というのは任意の環境名で、ここで指定した名前のディレクトリがカレントディレクトリに作成されます。

~~~
$ virtualenv ENV
~~~

この環境を使用するには、`ENV` ディレクトリの中に作成された `bin/activate` スクリプトを読み込みます（Windows の場合はバッチファイルになっています）。
仮想環境に入ると、プロンプトに `(環境名)` と表示されるようになります。

~~~
$ source ENV/bin/activate  （source の代わりに . でもOK）
(ENV)$
~~~

仮想環境に入った状態で `python` コマンドを実行すると、その仮想環境内に配置された `ENV/bin/python` が参照されます（この処理系の切り替え方法は後述）。

~~~
(ENV)$ which python
/Users/maku/ENV/bin/python
~~~

また、`pip` コマンドでインストールする Python パッケージも、仮想環境内の `ENV/lib/python3.6/site-packages` に格納されるようになるので、たくさんパッケージをインストールしても、仮想環境の外の Python 実行環境を汚染することがありません。

仮想環境を抜けて元の世界に戻るには、`deactivate` コマンドを実行します。
このコマンドは、`ENV/bin/deactivate` として配置されているのですが、仮想環境に入るときに `ENV/bin` にパスが通されるので、どこからでも実行することができます。

~~~
(ENV)$ deactivate
$
~~~

仮想環境自体が不要になったら、ディレクトリごと削除してしまえば OK です。

~~~
$ rm -r ENV
~~~


virtualenv の高度な使い方
----

### 仮想環境で使用する Python のバージョンを指定する

仮想環境で使用する Python (`ENV/bin/python`) のバージョンは、デフォルトでは virtualenv のインストールに使用した python 処理系と同じバージョンになります（要するに pip コマンドが使用する python のバージョンということ）。

仮想環境内から使用する python 処理系のバージョンを指定したい場合は、仮想環境の作成時に、`--python` オプションで指定します（`ENV/bin/python` として配置する処理系を指定する）。

~~~
$ virtualenv --python=python ENV
$ virtualenv --python=python2.7 ENV
$ virtualenv --python=python3 ENV
$ virtualenv --python=/opt/python-2.7/bin/python ENV
~~~

この例からも分かるように、指定できる Python 処理系は、あらかじめインストールされているものだけだということに注意してください。
Python 2.7 しかインストールされていない環境で、Python 3 を使用する仮想環境を作成したいときは、先に Python 3 をインストールしておく必要があります。


### 仮想環境からグローバルな site-packages を参照できるようにする

システムにインストールされている Python パッケージ群を、仮想環境からも参照できるようにするには、仮想環境を作成するときに `--system-site-packages` オプションを指定します。

~~~
$ virtualenv --system-site-packages ENV
~~~

<div class="note">
過去のバージョンの virtualenv では、デフォルトで仮想環境からグローバルな site-packages を参照できるようになっており、これを抑制するためには <code>--no-site-packages</code> オプションが必要でした。現在は、デフォルトでグローバルな site-package は参照できないようになっており、<code>--no-site-packages</code> オプションも deprecated（廃止予定）となっています。
</div>


### 仮想環境内に Linux コマンドなどをインストールする

virtualenv の仮想環境に入ると、`ENV/bin` ディレクトリに自動的にパスが通されるようになっています。

~~~
$ . ENV/bin/activate
(ENV)$ echo $PATH
/Users/maku/ENV/bin:...
~~~

つまり、この `ENV/bin` ディレクトリ内に新しいコマンドをインストールすれば、仮想環境内でのみ使用可能なコマンドとして扱うことができます。
仮想環境が必要なくなった場合は、`ENV` ディレクトリごと削除してしまえばよいので、外部の環境に影響を与えずに済みます。

