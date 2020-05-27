---
title: "Python の実行環境を切り替えて使用する (venv)"
date: "2020-05-27"
description: "venv モジュールは、Python の仮想実行環境を作成するための標準モジュールです。"
---

Python 3.2 までは [virtualenv という外部パッケージを使用していました](./virtualenv.html)が、Python 3.3 以降は標準で同等機能を提供する [venv パッケージ](https://docs.python.org/ja/3/tutorial/venv.html) を使用できます。


なぜ仮想環境が必要か？
----

Python で作成されたプログラムは、`pip install` で外部モジュールをインストールすることを前提に作成されたものがたくさんあります。
Python プログラムを実行するために、何も考えずに `pip install` を繰り返していると、いろいろなバージョンのモジュールがインストールされてしまい、実行環境がぐちゃぐちゃになってしまいます。
あるプログラムを動作させるために `pip install` すると、別のプログラムが動作しなくなるといったことが起こります。

`venv` を使って仮想環境を作ると、その環境内に閉じて `pip install` することができるようになります。
例えば、Python プログラム1 のために `env-1` という仮想環境を作り、Python プログラム2 のために `env-2` という仮想環境を作る、といったことができます。


仮想環境を作成する／削除する
----

仮想環境を作成するには、次のようなコマンドを実行します。
仮想環境はディレクトリの形で作成され、その中に必要なファイルが自動生成されます。

```
$ python -m venv 仮想環境ディレクトリ
```

ここでは、ホームディレクトリに仮想環境用のディレクトリを作成してみましょう。
次のように実行すると、ホームディレクトリに `python-venv/myapp` という名前のディレクトリが作成されます（ディレクトリ階層が深くても大丈夫です）。

```
$ python -m venv ~/python-venv/myapp

$ ls ~/python-venv/myapp
bin/    include/    lib/    pyvenv.cfg
```

仮想環境が必要なくなった場合は、このディレクトリを丸ごと削除してしまえば OK です。

```
$ rm -Rf ~/python-venv/myapp
```


仮想環境に入る (activate) / 仮想環境から抜ける (deactivate)
----

作成した仮想環境を使用するには、仮想環境のディレクトリ内に作成された __`activate`__ スクリプトを実行します。
このとき、カレントディレクトリはどこでもいいことに注意してください（つまり、実行したい Python スクリプトが置いてあるディレクトリから、任意の仮想環境に入ることができます）。

#### Linux や macOS での実行例

```
$ source ~/python-venv/myapp/bin/activate
```

#### Windows での実行例

```
C:\> C:\python-venv\myapp\Scripts\activate.bat
```

仮想環境に入ると、次のように、プロンプトの先頭に仮想環境名が表示されます。

```
(myapp) $
```

この状態で `pip install` コマンドを実行すると、仮想環境内に閉じてモジュールがインストールされます。
そのモジュールが有効なのは、この仮想環境内で `python` コマンドを実行した場合のみです。

次の例では、いくつかの外部モジュール（`requests` と `python-dotenv`）をインストールし、python スクリプト (`app.py`) を実行しています。

```
(myapp) $ pip install requests python-dotenv
(myapp) $ python app.py
```

仮想環境から抜けるには、単純に __`deactivate`__ コマンドを実行します。
このコマンドは、仮想環境に入っている状態であれば、どのディレクトリからでも実行できるようになっています。

```
(myapp) $ deactivate
$
```

