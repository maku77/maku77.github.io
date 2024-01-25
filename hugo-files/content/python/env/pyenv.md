---
title: "python コマンドのバージョンを切り替える (pyenv, py)"
url: "p/x4z298a/"
date: "2023-12-19"
tags: ["python"]
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
| `pyenv versions` | インストール済みの Python バージョンの一覧を表示します。現在選択しているバージョンには `*` 印が付きます。 |
| `pyenv version` | 現在選択している Python のバージョンを表示します。 |
| `pyenv install --list` | インストール可能な Python のバージョンを表示します。 |
| `pyenv install 3.10` | 指定したバージョンの Python をインストールします。マイナーバージョンやパッチバージョンは省略できます。 |
| `pyenv uninstall 3.10.13` | 指定したバージョンの Python をアンインストールします。バージョンは省略できません。 |
| `pyenv global 3.10` | Python のバージョンを切り替えます（グローバル設定）。 |
| `pyenv local 3.10` | Python のバージョンを切り替えます（カレントディレクトリ以下）。 |
| `pyenv shell 3.10` | Python のバージョンを切り替えます（現在のシェルのみ）。 |

### 使用例

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

ここで、少し古い Python 3.10.X を使いたくなった場合は、次のように __`pyenv install`__ コマンドでインストールします。

```console
$ pyenv install 3.10
```

インストールが終わったら、もう一度 `pyenv versions` コマンドを実行して、正しくインストールされていることを確認します。

```console
$ pyenv versions
  system
  3.10.13
* 3.12.0 (set by /Users/maku/.pyenv/version)
```

次に、__`pyenv global`__ コマンドを使って、使用する Python バージョンを切り替えます。

```console
$ pyenv global 3.10

$ pyenv versions
  system
* 3.10.13 (set by /Users/maku/.pyenv/version)
  3.12.0

$ python --version
Python 3.10.13
```

無事 `python` コマンドの実体が、Python 3.10 に切り替わっていることを確認できました。

上記の例では、`pyenv global` コマンドを使って `python` コマンドのバージョンを切り替えましたが、他にも __`pyenv local`__ や __`pyenv shell`__ で切り替えることができます。
切り替え方法によって、次のように有効期間が異なります。

- `pyenv global` ... グローバルに `python` コマンドのバージョンを切り替えます。別のシェルを開いた場合にも有効です。バージョン情報は、`~/.pyenv/version` に保存されます。
- `pyenv local` ... カレントディレクトリ以下で使用する `python` コマンドのバージョンを切り替えます。バージョン情報は、カレントディレクトリの `.python-version` ファイルに保存されます。
- `pyenv shell` ... カレントシェルでのみ `python` コマンドのバージョンを切り替えます。


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

