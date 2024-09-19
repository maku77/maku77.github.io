---
title: "Python プロジェクト用の爆速パッケージマネージャ uv を導入する"
url: "p/fjsfjpw/"
date: "2024-09-10"
tags: ["Python", "uv"]
---

uv とは
----

Python 用のツール [uv](https://github.com/astral-sh/uv) は、超高速なプロジェクト＆パッケージマネージャです。
パッケージマネージャと説明されていることが多いですが、どちらかというと Python プロジェクトを総合的に管理するツールです（単なる `pip` の置き換えではないです）。

`uv` は Rust で実装されており、従来の `pip` コマンドを使うよりも数十倍高速だと言われています。
提供元の [Astral 社](https://astral.sh/) は、Python 用の高速なツールチェインを作成することをミッションとしており、Python 用の超高速 Linter & Formatter である [Ruff](https://github.com/astral-sh/ruff) を提供している会社でもあります。

### `uv` の特徴

- Rust で実装されていてとにかく高速
- インストールが簡単で使い方も簡単
- もちろんマルチプラットフォーム対応 (macOS/Linux/Windows)
- モダンな `pyproject.toml` ベースのプロジェクト管理が可能
  - `uv` があれば、`Poetry` や `Rye` は必要ありません。`uv` は `Rye` の後継という位置づけとのこと。
- `python` コマンド自体のバージョン切り替えが可能
  - `uv` があれば、`pyenv` や `py` は必要ありません。
- 意識せずに仮想環境を扱える
  - `python` コマンドの代わりに `uv run` を使うだけで仮想環境 (`.venv`) を作って入って依存解決して実行して抜けてくれます。
  - `python -m venv .venv` で仮想環境を作ったり、`source .venv/bin/activate` で仮想環境に入ったりする必要はありません。
- 様々な CI 環境での実行が考慮されている
  - 例えば GitHub Actions 用の `astral-sh/setup-uv` アクションが公式に用意されていて安心です。
- その他の特徴は、[公式ページを参照](https://docs.astral.sh/uv)

高速かつ全部入りで使いやすいので、2024 年時点の Python プロジェクト管理ツールとしては最も期待が持てそうです。
特に、`venv` などの仮想環境を意識せずに Python プログラムを 1 コマンドで実行できるのはとても快適です。
若干 Rust の `cargo` コマンドを意識しすぎな感じもありますが、Astral が Rust ラブなのでしょうがないです（別に悪いことではないです）。


uv のインストール
----

`uv` のインストール方法は[公式ドキュメント](https://docs.astral.sh/uv/getting-started/installation/)に書かれている通りですが、基本的にはどの環境でも 1 行のコマンドを実行するだけです。
事前に Python がインストールされている必要はありません。

### インストール

```console
$ # macOS/Linux の場合
$ curl -LsSf https://astral.sh/uv/install.sh | sh

$ # macOS (Homebrew) の場合
$ brew install uv

$ # Windows の場合
$ powershell -ExecutionPolicy ByPass -c "irm https://astral.sh/uv/install.ps1 | iex"
```

`uv` コマンドが実行できるようになれば成功です。

```console
$ uv version
uv 0.4.8 (956cadd1a 2024-09-09)
```

{{% note title="uv のインストール先" %}}
`uv` コマンド（および `uvx` コマンド）は `~/.cargo/bin` 以下にインストールされます。
Rust 製のコマンドラインツールはこのディレクトリにインストールされることが決まっています。
1 ファイル（`uvx` を入れると 2 ファイル）がインストールされるだけなので、とてもシンプルです。
アンインストールは `~/.cargo/bin/uv` を削除するだけです。
{{% /note %}}


### バージョンアップ

インストール済みの `uv` コマンドをバージョン更新するには次のようにします。
コマンド自身にアップデートの仕組みが入っていると楽ですね。

```console
$ uv self update
```

### シェルの補完機能を有効化

bash/zsh/Powershell などのコマンドラインで入力補完機能 (shell autocompletion) を有効化しておくと便利です。

- [Installation - Shell autocompletion | uv](https://docs.astral.sh/uv/getting-started/installation/#shell-autocompletion)


Python プロジェクトを作成する (uv init)
----

`pyproject.toml` ベースの Python プロジェクトを作成するには、__`uv init <アプリ名>`__ コマンドを使用します。

```console
$ uv init myapp
Initialized project `myapp` at `/Users/maku/myapp`
```

`myapp` ディレクトリが作成され、次のようなファイルが自動生成されます。

```
myapp/
  - .python-version  # uv run コマンド（後述）で使う python のバージョン
  - README.md        # 空っぽの README
  - hello.py         # Hello World プログラム
  - pyproject.toml   # プロジェクトの config ファイル
```

生成される `pyproject.toml` の内容は次のような感じになっています。

{{< code lang="toml" title="pyproject.toml" >}}
[project]
name = "myapp"
version = "0.1.0"
description = "Add your description here"
readme = "README.md"
requires-python = ">=3.12"
dependencies = []
{{< /code >}}

プロジェクト内で Python スクリプトを実行するときは、基本的に __`python` コマンドの代わりに `uv run` コマンドを使用します。__
これにより、自動的にプロジェクト用の仮想環境 (`.venv`) に入り、依存関係を解決し、`uv` 管轄の `python` コマンドでスクリプトを実行してくれます。

{{< code lang="console" title="Python スクリプトの実行" >}}
$ uv run hello.py
Using Python 3.12.0 interpreter at: /Users/maku/.pyenv/versions/3.12.0/bin/python3.12
Creating virtualenv at: .venv
Hello from myapp!
{{< /code >}}

プロジェクト内で最初に `uv run` コマンドを実行したときは、上記のように自動的に仮想環境用のディレクトリ (`.venv`) が作成されます。
このメッセージを抑制したいときは __`-q`__ オプションを指定してください。
プログラムの実行が完了すると、自動的に仮想環境から抜けた状態に戻ります。
つまり、`uv run` を使うと、仮想環境内で実行しているということすら意識しなくて済むようになります。

{{% note title="uv run が気持ち悪い？" %}}
Rust プログラマーであれば、`uv run` は `cargo run` に相当するコマンドであると考えると分かりやすいです。
最初は `python` コマンドで実行しないことに違和感があるかもしれませんが、`uv run` を使うことで多くの恩恵を得られます。
{{% /note %}}


依存パッケージの管理 (uv add/remove)
----

`uv` 管理下の Python プロジェクトで外部パッケージへの依存を追加するときは、__`pip install/uninstall` の代わりに `uv add/remove` を使用します。__
ここでは、例として `toml` パッケージをインストールしてみます。

{{< code lang="console" title="例: toml パッケージを追加" >}}
$ uv add toml
{{< /code >}}

これにより、仮想環境 (`.venv`) に `toml` パッケージがインストールされ、`pyproject.toml` の `dependencies` に依存情報が追記されます。

{{< code lang="toml" title="pyproject.toml（抜粋）" >}}
dependencies = [
    "toml>=0.10.2",
]
{{< /code >}}


既存の `hello.py` ファイルを編集して、`toml` パッケージを使うコードに書き換えます。
次の例では、`pyproject.toml` ファイルの内容をパースして辞書オブジェクト (`dict`) として取得しています。

{{< code lang="python" title="hello.py" >}}
import toml

def main():
    # Load the configuration file and convert it into a dict
    config = toml.load("pyproject.toml")
    print(config)

if __name__ == "__main__":
    main()
{{< /code >}}

実行してみます。

{{< code lang="console" >}}
$ uv run hello.py
{'project': {'name': 'myapp', 'version': '0.1.0', 'description': 'Add your description here', 'readme': 'README.md', 'requires-python': '>=3.12', 'dependencies': ['toml>=0.10.2']}}
{{< /code >}}

うまく動きました！ ٩(๑❛ᴗ❛๑)۶ わーぃ

ちなみに、他の PC 上でこのプログラムを実行したいときは、プロジェクトのディレクトリに移動して、おもむろに `uv run hello.py` とするだけで実行できます。
その裏では、uv が (1) 仮想環境の生成、(2) 仮想環境に入る、(3) 依存パッケージのインストール、(4) プログラムの実行、(5) 仮想環境を抜ける、ということを自動でやってくれています。
とっても楽ですね！


（応用）Git にどのファイルをコミットするか？
----

下記のファイルにはプロジェクトの実行環境の情報が含まれているので Git にコミットします。

- `.python-version` ... python コマンド自体のバージョン
- `pyproject.toml` ... プロジェクトの情報（依存パッケージなど）
- `uv.lock` ... 依存パッケージのより詳細なバージョン情報

一方、仮想環境ディレクトリ (`.venv`) はコミットしないように、`.gitignore` に次のように記述しておきます。

{{< code lang="ini" title=".gitignore" >}}
# Ignore Python virtual environment directory
.venv/
{{< /code >}}


（応用）uv.lock ファイルとは？
----

`uv.lock` ファイルには、`pyproject.toml` の `dependencies` で表現しきれない __詳細なパッケージ依存情報__ が記述されています （Node.js プロジェクトにおける、`packages-lock.json` や `yaml.lock` のようなものです）。

例えば、`pyproject.toml` に `toml>=0.10.2` と記述されている場合、`toml` パッケージのバージョンは `0.10.2` 以上であれば何でもよいということを示していますが、それだけでは実行環境の再現性がなくなってしまうので、`uv.lock` に実際に使用する具体的なバージョンが記述されます。

{{< accordion title="uv.lock ファイルの例" >}}
{{< code lang="toml" >}}
version = 1
requires-python = ">=3.12"

[[package]]
name = "myapp"
version = "0.1.0"
source = { virtual = "." }
dependencies = [
    { name = "toml" },
]

[package.metadata]
requires-dist = [{ name = "toml", specifier = ">=0.10.2" }]

[[package]]
name = "toml"
version = "0.10.2"
source = { registry = "https://pypi.org/simple" }
sdist = { url = "https://files.pythonhosted.org/packages/be/ba/1f744cdc819428fc6b5084ec34d9b30660f6f9daaf70eead706e3203ec3c/toml-0.10.2.tar.gz", hash = "sha256:b3bda1d108d5dd99f4a20d24d9c348e91c4db7ab1b749200bded2f839ccbe68f", size = 22253 }
wheels = [
    { url = "https://files.pythonhosted.org/packages/44/6f/7120676b6d73228c96e17f1f794d8ab046fc910d781c8d151120c3f1569e/toml-0.10.2-py2.py3-none-any.whl", hash = "sha256:806143ae5bfb6a3c6e736a764057db0e6a0e05e338b5630894a5f779cabb4f9b", size = 16588 },
]
{{< /code >}}
{{< /accordion >}}

`uv.lock` ファイルは、`uv add` コマンドで依存パッケージを追加したときに、`pyproject.toml` と一緒に更新されます。
`uv run` によるプログラム実行時に更新されることもありますが、それは `uv.lock` に記述されたバージョンが、`pyproject.toml` で指定されたバージョン条件を満たしていない場合に限られます（プログラムを実行するたびにパッケージが更新されると大変なので）。
例えば、`uv.lock` にバージョン `0.10.2` と書かれていて、`pyproject.toml` に `>=0.10.1` と書かれている場合は、`0.10.2 >= 0.10.1` で条件を満たすので `uv.lock` は更新されないし、新しいパッケージがインストールされることもありません。

明示的に最新のパッケージをインストールして `uv.lock` ファイルを更新するには、次のように __`uv lock`__ コマンドを使用します。

{{< code lang="console" title="uv.lock ファイルの更新" >}}
$ uv lock --upgrade               # 全パッケージを更新する場合
$ uv lock --upgrade-package toml  # パッケージを指定して更新する場合
{{< /code >}}

これにより、最新のパッケージを使用できるようになりますが、これはあくまで `uv.lock` ファイルの更新なので、`pyproject.toml` で指定している `>=0.10.1` などの情報は更新されないことに注意してください。
`pyproject.toml` 側の `dependencies` 情報を更新したいときは、`uv add toml`（あるいは明示的に `uv add toml>=0.10.2`）などのコマンドを実行してください。


{{% note title="uv.lock だけなぜ独自形式なの？" %}}
パッケージ管理ツールは大体このような lock ファイルの仕組みを持っていますが、lock ファイルにはまだ標準規格のようなものがないらしいです。
そのため、uv も独自のフォーマットで `uv.lock` ファイルを作成しています。
{{% /note %}}


（応用）uv で Ruff などのツールをインストールする
----

Python 用の高速 Linter & Formatter である Ruff も `uv` でインストールできます。
Ruff は開発時に使用するツールなので、__`uv add --dev`__ で dev 環境用の依存パッケージとしてインストールします。

{{< code lang="console" title="ruff コマンドのインストール" >}}
$ uv add --dev ruff
{{< /code >}}

`pyproject.toml` の __`dev-dependencies`__ に次のように追記されます。

{{< code lang="toml" title="pyproject.toml" hl_lines="4-7" >}}
[project]
# ...プロジェクトの設定...

[tool.uv]
dev-dependencies = [
    "ruff>=0.6.5",
]
{{< /code >}}

{{% note title="他の PC で依存パッケージをインストールするには" %}}
別の PC 環境などで `git clone` した直後はこれらの依存パッケージはインストールされていません。
`dev` 環境用の依存パッケージをまとめてインストールするには、__`uv sync --dev`__ コマンドを使用します（`uv run` や `uv sync` では dev 環境用の依存パッケージはインストールされません）。
これは、GitHub Actions などの CI 環境上でテストツールをインストールするときにも使用します。
{{% /note %}}

インストールしたコマンドを実行するには、__`uv run`__ コマンドを使用します。

{{< code lang="console" title="ruff コマンドの実行" >}}
$ uv run ruff check           # Lint チェック
$ uv run ruff format --check  # フォーマットチェック
$ uv run ruff format          # 自動フォーマット
{{< /code >}}

エディタとして Visual Studio Code を使っているのであれば、Ruff 拡張を入れてしまうのが手っ取り早かったりします。

- 参考: [VS Code で Python 用の Linter ＆フォーマッターの Ruff を使う｜まくろぐ](https://maku.blog/p/6hnm4hy/)


（応用）GitHub Actions のワークフローに組み込む
----

`uv` によるパッケージのインストールは高速なので、GitHub Actions などの CI 環境でも `uv` を使うとよいです。

- 本家ドキュメント: [Using uv in GitHub Actions](https://docs.astral.sh/uv/guides/integration/github/)
- astral-sh/setup-uv アクション: [Python setup uv · Actions · GitHub Marketplace](https://github.com/marketplace/actions/python-setup-uv)

基本的な流れは次のようになります。

1. `astral-sh/setup-uv` アクションを使って `uv` をインストール
2. `uv python install` で `python` をインストール
3. `uv sync --dev` で dev 環境用の依存パッケージをインストール
4. `uv run pytest tests` など任意のテストツールや Lint を実行

