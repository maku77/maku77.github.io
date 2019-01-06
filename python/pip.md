---
title: "Python のパッケージ管理ツール (pip)"
date: "2015-05-17"
---

pip とは
====

Python のパッケージ管理ツールである pip コマンドは、従来の setuptools で提供されていた easy_install コマンドを置き換えるものです。
pip コマンドは、主に [Python Package Index](https://pypi.python.org/pypi) からパッケージをダウンロードしてインストールします。

pip のインストール
====

Python 3.4 以降の Python インストーラを使用すると、pip コマンドは標準でインストールされます。
下記のいずれかの方法ですでにインストールされているか確認しましょう。

```
$ pip --version
$ python -m pip --version
$ python3 -m pip --version
```

単体でインストールする必要がある場合は、基本的には下記のサイトに従ってインストールします。

* https://pip.pypa.io/en/stable/installing.html

pip 1.5.1 までは、setuptools の easy_install コマンドを使用してインストールしていましたが、現在は ```get-pip.py``` スクリプトを使ってどの OS 環境でも統一された方法で簡単にインストールすることができます。

```sh
$ curl -kL https://bootstrap.pypa.io/get-pip.py | python3
```

プロキシ環境で使用する場合は、```--proxy=proxy.example.com:8080``` のようにオプション指定します。


pip によるパッケージのインストール、アンインストール
====

```sh
$ pip install <package>
$ pip install -U <package>  # パッケージを最新版にアップグレード
$ pip install -I <package>  # パッケージをインストールしなおす
$ pip uninstall <package>   # パッケージのアンインストール
```

プロキシ環境で使用する場合は、```--proxy=proxy.example.com:8080``` のようにオプション指定します。

pip コマンド自体のアップデート
====

```sh
$ pip install -U pip
```
