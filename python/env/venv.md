---
title: "Python の実行環境を切り替えて使用する (venv) Python3.3以降"
date: "2020-05-27"
lastmod: "2021-11-02"
description: "venv モジュールは、Python の仮想実行環境を作成するための標準モジュールです。"
---

Python 3.2 までは [virtualenv という外部パッケージを使用していました](./virtualenv.html)が、Python 3.3 以降は標準で同等機能を提供する [venv パッケージ](https://docs.python.org/ja/3/tutorial/venv.html) を使用できます。


なぜ仮想環境が必要か？
----

Python で作成されたプログラムは、`pip install` で外部モジュールをインストールすることを前提に作成されたものがたくさんあります。
Python プログラムを実行するために、何も考えずに `pip install` を繰り返していると、いろいろなバージョンのモジュールがインストールされてしまい、実行環境がぐちゃぐちゃになってしまいます。
あるプログラムを動作させるために `pip install` すると、別のプログラムが動作しなくなるといったことが起こります。

`venv` を使って仮想環境を作ると、その環境内に閉じて `pip install` することができるようになります。
例えば、app1 と app2 という 2 つの Python プログラム用に別々の仮想環境を作って、それぞれのプログラムに必要なパッケージをインストールするということができます。

（注意）以下、`python3` というコマンドを使っていますが、これはバージョン 3 以降の Python を使用することを示しています。
環境によっては、`python` に置き換えて読んでください（Windows の場合は `py` だったりします）。


プロジェクト用の仮想環境を作成する／削除する
----

仮想環境を作成するには、次のようなコマンドを実行します。
仮想環境はディレクトリの形で作成され、その中に必要なファイルが自動生成されます。

```
$ python3 -m venv <仮想環境ディレクトリ>
```

プロジェクトごとに仮想環境を作成するのであれば、プロジェクトのルートに仮想環境ディレクトリを作成するのがよいでしょう。
例えば、`myapp` プロジェクト用の仮想環境を作るには次ようにします（仮想環境ディレクトリ名を `.venv` としています）。

```
$ mkdir ~/myapp
$ cd ~/myapp
$ python3 -m venv .venv
```

この仮想環境ディレクトリには、次のようなファイル群が格納されていて、仮想環境内でインストールしたパッケージなどはこの中で管理されます。

```
$ ls .venv
bin/    include/    lib/    pyvenv.cfg
```

仮想環境が必要なくなった場合は、仮想環境ディレクトリを丸ごと削除してしまえば OK です。

```
$ rm -Rf .venv
```

### .gitignore に .venv を追加

仮想環境ディレクトリは他のユーザーと共有する仕組みにはなっていないので、`.gitignore` ファイルに次のように記述して、Git にコミットしないようにします。

#### .gitignore

```
.venv/
```

ここでは、仮想環境ディレクトリ名が `.venv` であると想定していますが、このあたりはプロジェクト内でルール化して、`README.md` などに記述しておきましょう。


仮想環境に入る (activate) / 抜ける (deactivate)
----

### activate

作成した仮想環境を使用するには、仮想環境のディレクトリ内に作成された __`activate`__ スクリプトを実行します。

```
### Linux や macOS の場合
$ source .venv/bin/activate

### Windows の場合
.venv\Scripts\activate.bat
```

仮想環境に入ると、次のように、プロンプトの先頭に仮想環境名が表示されます。

```
(.venv) $
```

この状態で `python3 -m pip install` コマンドを実行すると、仮想環境内に閉じてパッケージがインストールされます。
そのパッケージが有効なのは、この仮想環境内で `python3` コマンドを実行した場合のみです。

```
(.venv) $ python3 -m pip install requests
(.venv) $ python3 -m pip install python-dotenv
(.venv) $ python3 -m pip install ...
```

### deactivate

仮想環境から抜けるには、単純に __`deactivate`__ コマンドを実行します。
このコマンドは、仮想環境に入っている状態であれば、どのディレクトリからでも実行できるようになっています。

```
(myapp) $ deactivate
```


プロジェクトの依存パッケージを requirements.txt で管理する
-----

### requirements.txt とは

これは `venv` の仕組みではなくて、パッケージ管理ツールの [pip の仕組み](https://pip.pypa.io/en/latest/user_guide/#requirements-files) ですが、__`requirements.txt`__ というファイルに依存パッケージを記述しておくと、一撃で依存パッケージをインストールできるようになります。
Python アプリのソースコードと一緒に、この `requirements.txt` を Git にコミットしておくことで、チーム内の開発環境を簡単に揃えることができます。
`venv` による仮想環境を使って開発を進める場合、初期状態では何もパッケージがインストールされていない状態からパッケージをインストールしていくことになるため、`requirements.txt` によるパッケージ管理は非常に相性がよいといえます。

### requirements.txt を作成する

__`python3 -m pip freeze`__ コマンドを使うと、現在の仮想環境にインストールされているパッケージのリストをもとに `requirements.txt` を作成することができます。
このコマンドは、仮想環境に入っている状態 (`source .venv/bin/activate`) で実行してください。

```
(.venv) python3 -m pip freeze > requirements.txt
```

例えば、`requires` と `python-dotenv` パッケージをインストールした後の `requirements.txt` の内容は次のような感じになります（間接的に依存するパッケージも列挙されます）。

```
certifi==2021.10.8
charset-normalizer==2.0.7
idna==3.3
python-dotenv==0.19.1
requests==2.26.0
urllib3==1.26.7
```

`requirements.txt` はプロジェクトのルートディレクトリに配置して、Git にコミットしておきましょう。

### requirements.txt を使ってパッケージをインストールする

別の PC 環境に開発を始めるときは、次のようにセットアップすることができます。

```
$ python3 -m venv .venv      # 仮想環境の作成
$ source .venv/bin/activate  # 仮想環境に入る
(.venv) $ python3 -m pip install -r requirements.txt  # 依存パッケージのインストール
```

これで、依存パッケージがすべてインストールされた状態で `python3` コマンドを実行することができます。

