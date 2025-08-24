---
title: "python コマンドのバージョンを切り替える (pyenv, py)"
url: "p/x4z298a/"
date: "2023-12-19"
lastmod: "2025-08-24"
tags: ["python"]
changes:
  - 2025-08-24: pyenv install --list の説明を追加
---

互換性の問題のために、少し古いバージョンの `python` コマンドを使わなければいけないことがあります（機械学習系のライブラリなど）。
そのようなケースでは、次のようなツールを使って `python` コマンド自体のバージョンを切り替えると便利です。

- [pyenv](https://github.com/pyenv/pyenv) ... Linux または macOS 用の `python` バージョン切り替えコマンドです。Windows 用の移植もありますが、シェルスクリプトで作られているので、本質的には Linux または macOS 用です。
- [Python ランチャー (py)](https://docs.python.org/ja/3/using/windows.html#launcher) ... Windows 用の Python インストーラーを使うと、Python ランチャー（`py` コマンド）をインストールすることができます。


pyenv コマンド
----

### インストール

{{< code lang="console" title="macOS の場合 (Homebrew)" >}}
$ brew update
$ brew install pyenv
{{< /code >}}

{{< code lang="console" title="Linux の場合" >}}
$ curl https://pyenv.run | bash
{{< /code >}}

### チートシート

| コマンド | 説明 |
| ---- | ---- |
| **`pyenv versions`** | インストール済みの Python バージョンの一覧を表示します。現在選択しているバージョンには `*` 印が付きます。 |
| `pyenv version` | 現在選択している Python のバージョンを表示します。 |
| **`pyenv install --list`** | インストール可能な Python のバージョンを表示します。 |
| **`pyenv install 3.10`** | 指定したバージョンの Python をインストールします。マイナーバージョンやパッチバージョンは省略できます。 |
| `pyenv uninstall 3.10.13` | 指定したバージョンの Python をアンインストールします。バージョンは省略できません。 |
| **`pyenv global 3.10`** | Python のバージョンを切り替えます（グローバル設定）。 |
| `pyenv local 3.10` | Python のバージョンを切り替えます（カレントディレクトリ以下）。 `--unset` を指定するとバージョン指定を解除します。|
| `pyenv shell 3.10` | Python のバージョンを切り替えます（現在のシェルのみ）。`--unset` を指定するとバージョン指定を解除します。 |

### 現在の Python バージョンを確認

まず、__`pyenv versions`__ コマンドで、現在インストールされている Python のバージョン一覧を調べます。

```console
$ pyenv versions
  system
* 3.12.0 (set by /Users/maku/.pyenv/version)
```

`system` と `3.12.0` がインストールされていることが分かります。
`system` の方は、`pyenv` の外の世界でインストールされた `python` コマンドを示しており、`pyenv` 管轄の `python` コマンドを使う場合は使用しません。
`3.12.0` の横には __`*`__ 印が付いているので、この状態で `python` コマンドを実行すると、バージョン `3.12.0` の Python が起動します。

```console
$ python --version
Python 3.12.0
```

### インストール可能な Python バージョンを確認

インストール可能な Python のバージョンは、__`pyenv install --list`__ コマンドで確認できます。
そのまま実行するとたくさん表示されるので、適宜 `grep` コマンドをなどを使って絞り込みます。

{{< code lang="console" title="インストール可能な Python バージョンの一覧（最新の3.x系だけ表示）" >}}
$ pyenv install --list | grep -E "\s+3" | tail
  3.11.5
  3.11.6
  3.11.7
  3.11.8
  3.12.0
  3.12-dev
  3.12.1
  3.12.2
  3.13.0a3
  3.13-dev
{{< /code >}}

### 指定したバージョンの Python をインストール

例えば、少し古い Python 3.10.X を使いたくなった場合は、次のように __`pyenv install`__ コマンドでインストールします。
パッチバージョンを省略すると、その時点での最新のパッチバージョン（例えば 3.10.13）がインストールされます。

{{< code lang="console" title="Python 3.10 系の最新バージョンをインストール" >}}
$ pyenv install 3.10
{{< /code >}}

`3.13-dev` のような開発バージョンをインストールしたい場合は、バージョン名全体を指定する必要があります。

インストールが終わったら、もう一度 `pyenv versions` コマンドを実行して、正しくインストールされていることを確認します。

```console
$ pyenv versions
  system
  3.10.13
* 3.12.0 (set by /Users/maku/.pyenv/version)
```

### 使用する python バージョンを切り替える

普段使用する Python バージョンを切り替えるには、__`pyenv global`__ コマンドを使います。

```console
$ pyenv global 3.10

$ pyenv versions
  system
* 3.10.13 (set by /Users/maku/.pyenv/version)
  3.12.0

$ python --version
Python 3.10.13
```

無事 `python` コマンドのバージョンが 3.10 系に切り替わっていることを確認できました。
上記の例では、`pyenv global` コマンドを使って `python` コマンドのバージョンを切り替えましたが、他にも __`pyenv local`__ や __`pyenv shell`__ でスコープを絞って切り替えることができます。
切り替え方法によって、次のように有効期間が異なります。

- <b>`pyenv global 3.12`</b>
  - グローバルに使用する `python` コマンドのバージョンを 3.12 に切り替えます。別のシェルを開いた場合にも有効です。内部的には、**`~/.pyenv/version`** に指定されたバージョンが保存されているだけです。
- <b>`pyenv local 3.12`</b>
  - カレントディレクトリ以下で使用する `python` コマンドのバージョンを 3.12 に切り替えます。プロジェクト内で指定された Python バージョンが必要な場合に指定しておくと便利です。内部的には、カレントディレクトリの **`.python-version`** に指定されたバージョンが保存されているだけです。
- <b>`pyenv shell 3.12`</b>
  - カレントシェルで使用する `python` コマンドのバージョンを 3.12 に切り替えます。シェルを終了すると元に戻ります。内部的には **`PYENV_VERSION`** 環境変数を設定しているだけなので、`export PYENV_VERSION=3.12` のようにするのと同じです。

`pyenv` で複数バージョンの Python をインストールすると、`python3.10`、`python3.12` のようなシンボリックリンクが自動的に作成されます（`~/.pyenv/shims/` ディレクトリを参照）。
一時的に使用する Python バージョンを切り替えたいときは、`pyenv shell` コマンドで切り替えるのではなく、このシンボリックリンクを使った方が早いかもしれません。

{{< code lang="console" title="3.10 系の python コマンドを起動" >}}
$ python3.10 main.py
{{< /code >}}


Python ランチャー（py コマンド）
----

Windows の Python 環境では Python ランチャー (`py`) を使って `python` コマンドのバージョンを切り替えることができます。

### チートシート

| コマンド | 説明 |
| ---- | ---- |
| `py --list` | インストール済みの Python バージョンの一覧を表示します。現在選択しているバージョンには `*` 印が付きます。 |
| `py --list-paths` | 各バージョンの `python.exe` のフルパスを表示します。 |
| `py -3.8` | Python 3.8 を起動します。 |
| `py -3.8 -m pip install pandas` | Python 3.8 の環境にパッケージをインストールします。 |
| `py -3.8 -m venv venv` | Python 3.8 の環境で `venv` 仮想環境を作成します。 |

