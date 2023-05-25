---
title: "Python のパッケージ管理ツール (pip) の使い方"
url: "p/7o9q8p6/"
date: "2015-05-17"
tags: ["Python"]
aliases: /python/pip.html
---

pip とは
----

Python のパッケージ管理ツールである __`pip`__ コマンドは、従来の `setuptools` で提供されていた `easy_install` コマンドを置き換えるものです。
`pip` コマンドは、主に [Python Package Index](https://pypi.python.org/pypi) からパッケージをダウンロードしてインストールします。


pip のインストール
----

Python 3.4 以降の Python インストーラを使用すると、`pip` コマンドは標準でインストールされます。
下記のいずれかの方法ですでにインストールされているか確認しましょう。

```console
$ pip --version
$ python -m pip --version
$ python3 -m pip --version
```

単体でインストールする必要がある場合は、基本的には下記のサイトに従ってインストールします。

- [Installation - pip documentation](https://pip.pypa.io/en/stable/installation/)

pip 1.5.1 までは、`setuptools` の `easy_install` コマンドを使用してインストールしていましたが、現在は __`get-pip.py`__ スクリプトを使ってどの OS 環境でも統一された方法で簡単にインストールすることができます。

{{< code lang="console" title="pip コマンドのインストール" >}}
$ curl -kL https://bootstrap.pypa.io/get-pip.py | python3
{{< /code >}}

プロキシ環境で使用する場合は、`--proxy=proxy.example.com:8080` のようにオプション指定します。


pip によるパッケージのインストール、アンインストール
----

```console
$ pip install <package>
$ pip install -U <package>  # パッケージを最新版にアップグレード
$ pip install -I <package>  # パッケージをインストールしなおす
$ pip uninstall <package>   # パッケージのアンインストール
```

プロキシ環境で使用する場合は、`--proxy=proxy.example.com:8080` のようにオプション指定します。


pip コマンドのアップデート
----

`pip` コマンド自身のバージョン更新は、`pip` コマンドを使って行うことができます。

```console
$ pip install -U pip
```

