---
title: "Python の実行環境を venv で切り替える"
url: "p/wozpogm/"
date: "2020-05-27"
lastmod: "2021-11-02"
changes:
  - 2023-12-08: 仮想環境の中では python3 ではなく python を使うように変更
tags: ["Python"]
aliases: /python/env/venv.html
---

__`venv`__ モジュールは、Python の仮想実行環境を作成するための標準モジュールです。
Python 3.2 までは [virtualenv という外部パッケージを使用していました](/p/yqjs3aw/)が、Python 3.3 以降は標準で同等機能を提供する [venv パッケージ](https://docs.python.org/ja/3/tutorial/venv.html) を使用できます。


なぜ仮想環境が必要か？
----

Python で作成されたプログラムは、`pip install` で外部モジュールをインストールすることを前提に作成されたものがたくさんあります。
Python プログラムを実行するために、何も考えずに `pip install` を繰り返していると、いろいろなバージョンのモジュールがインストールされてしまい、実行環境がぐちゃぐちゃになってしまいます。
あるプログラムを動作させるために `pip install` すると、別のプログラムが動作しなくなるといったことが起こります。

`venv` を使って仮想環境を作ると、その環境内に閉じて `pip install` することができるようになります。
例えば、app1 と app2 という 2 つの Python プログラム用に別々の仮想環境を作って、それぞれのプログラムに必要なパッケージをインストールするということができます。


プロジェクト用の仮想環境を作成する／削除する
----

### 仮想環境を作成する

仮想環境を作成するには、次のようなコマンドを実行します。
仮想環境はディレクトリの形で作成され、その中に必要なファイルが自動生成されます。
ここでは、`python3` を使っていますが、環境によっては `python` に置き換えてください（Windows の場合は `py` だったりします）。

{{< code lang="console" title="仮想環境の作成" >}}
$ python3 -m venv <仮想環境ディレクトリ>
{{< /code >}}

仮想環境ディレクトリの名前としては、一般的に __`venv`__ や __`.venv`__ が使われます。
このディレクトリは Git にコミットするものではないので、好きな方を選べばよいです。
プロジェクトごとに仮想環境を作成するのであれば、プロジェクトのルートに仮想環境ディレクトリを作成するのがよいでしょう。
例えば、`myapp` プロジェクト用の仮想環境を作るには次のようにします。

{{< code lang="console" title="仮想環境ディレクトリ venv を作成" >}}
$ mkdir ~/myapp
$ cd ~/myapp
$ python3 -m venv venv
{{< /code >}}

この仮想環境ディレクトリには、次のようなファイル群が格納されていて、仮想環境内でインストールしたパッケージなどはこの中で管理されます。

```console
$ ls venv
bin/    include/    lib/    pyvenv.cfg
```

### 仮想環境を削除する

仮想環境が必要なくなった場合は、仮想環境ディレクトリを丸ごと削除してしまえば OK です。

{{< code lang="console" title="仮想環境の削除" >}}
$ rm -Rf venv
{{< /code >}}

### .gitignore を修正する

仮想環境ディレクトリは他のユーザーと共有する仕組みにはなっていないので、`.gitignore` ファイルに次のように記述して、Git にコミットしないようにします。

{{< code title=".gitignore" >}}
.venv/
venv/
{{< /code >}}

ここでは、仮想環境ディレクトリ名が `.venv` あるいは `venv` であると想定しています。
このあたりはプロジェクト内でルール化して、`README.md` などに仮想環境の構築方法を記述しておきましょう。


仮想環境に入る / 抜ける
----

### 仮想環境に入る (activate)

作成した仮想環境を使用するには、仮想環境のディレクトリ内に作成された __`activate`__ スクリプトを実行します。
Linux と Windows で呼び出すスクリプトのパスが異なるので注意してください。

{{< code lang="console" title="仮想環境に入る（Linux や macOS の場合）" >}}
$ source venv/bin/activate
{{< /code >}}

{{< code title="仮想環境に入る（Windows の場合）" >}}
venv\Scripts\activate.bat
{{< /code >}}

仮想環境に入ると、次のように、プロンプトの先頭に仮想環境名が表示されます。

```
(.venv) $
```

この状態で `pip install` コマンドを実行すると、仮想環境内に閉じてパッケージがインストールされます。
そのパッケージが有効なのは、この仮想環境内で `python` コマンドを実行した場合のみです。

{{< code title="仮想環境にパッケージをインストール" >}}
(.venv) $ pip install requests
(.venv) $ pip install python-dotenv
(.venv) $ pip install ...
{{< /code >}}

### 仮想環境から抜ける (deactivate)

仮想環境から抜けるには、単純に __`deactivate`__ コマンドを実行します。
このコマンドは、仮想環境に入っている状態であれば、どのディレクトリからでも実行できるようになっています。

{{< code title="仮想環境から抜ける" >}}
(.venv) $ deactivate
{{< /code >}}


特定のバージョンの Python を使う仮想環境を作成する
----

`venv` 環境内で実行される `python` のバージョンは、`venv` 環境を作成するときに実行した `python` のバージョンと同じになります。
仮想環境内で任意のバージョンの `python` コマンドを使いたい場合は、`venv` 環境を作るときにそのバージョンの `python` コマンドを使用する必要があります。

`python` コマンドのバージョンを切り替えるには、`pyenv` コマンド (Linux/macOS) や `py` コマンド (Windows) を使うと便利です。

- 参考: [python コマンドのバージョンを切り替える (pyenv, py)](/p/x4z298a/)

下記は任意の Python バージョン（ここでは 3.10）の `venv` 仮想環境を作成する方法の例です。

{{< code lang="console" title="pyenv を使う方法 (Linux/macOS)" >}}
# Python 3.10.X をインストール
$ pyenv install 3.10

# カレントシェルで Python 3.10 を使うように切り替え
$ pyenv shell 3.10

# venv 環境を作成
$ python -m venv venv
{{< /code >}}

{{< code title="py を使う方法 (Windows)" >}}
C:\> py -3.10 -m venv venv
{{< /code >}}


プロジェクトの依存パッケージを requirements.txt で管理する
-----

### requirements.txt とは

これは `venv` の仕組みではなくて、パッケージ管理ツールの [pip の仕組み](https://pip.pypa.io/en/latest/user_guide/#requirements-files) ですが、__`requirements.txt`__ というファイルに依存パッケージを記述しておくと、一撃で依存パッケージをインストールできるようになります。
Python アプリのソースコードと一緒に、この `requirements.txt` を Git にコミットしておくことで、チーム内の開発環境を簡単に揃えることができます。
`venv` による仮想環境を使って開発を進める場合、初期状態では何もパッケージがインストールされていない状態からパッケージをインストールしていくことになるため、`requirements.txt` によるパッケージ管理は非常に相性がよいといえます。

### requirements.txt を生成する

__`pip freeze`__ コマンドを使うと、現在の仮想環境にインストールされているパッケージのリストをもとに `requirements.txt` を自動生成することができます。
このコマンドは、仮想環境に入っている状態で（`source venv/bin/activate` コマンドを実行した後に）実行してください。

```
(.venv) pip freeze > requirements.txt
```

例えば、`requires` と `python-dotenv` パッケージをインストールした後の `requirements.txt` の内容は次のような感じになります（間接的に依存するパッケージも列挙されます）。

{{< code title="requirements.txt" >}}
certifi==2021.10.8
charset-normalizer==2.0.7
idna==3.3
python-dotenv==0.19.1
requests==2.26.0
urllib3==1.26.7
{{< /code >}}

`requirements.txt` はプロジェクトのルートディレクトリに配置して、Git にコミットしておきましょう。

### requirements.txt を使ってパッケージをインストールする

別の PC 環境で開発を始めるときは、次のようにセットアップすることができます。

```console
$ python3 -m venv venv      # 仮想環境を作成する
$ source venv/bin/activate  # 仮想環境に入る
(.venv) $ pip install -r requirements.txt  # 依存パッケージをインストールする
```

これで、依存パッケージがすべてインストールされた状態で `python` コマンドを実行することができます。

