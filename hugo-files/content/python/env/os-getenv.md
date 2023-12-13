---
title: "Python で環境変数を参照する (os.getenv, os.environ)"
url: "p/y8hs5w6/"
date: "2020-01-29"
lastmod: "2023-12-13"
changes:
  - 2023-12-13: 環境変数の値として空文字が設定されている場合の扱いについて
  - 2023-06-07: 記事をリファイン
tags: ["Python"]
aliases: /python/env/os-getenv.html
---

指定した環境変数を取得する
----

Python で OS の環境変数を取得するには、__`os.getenv`__ 関数を呼び出すか、__`os.environ`__ 辞書オブジェクトを参照します。

{{< code lang="python" title="例: 環境変数 PATH の値を表示" >}}
import os

print(os.getenv("PATH"))
print(os.environ["PATH"])
{{< /code >}}

両者の違いは、存在しないキーを指定した場合に `os.getenv` がデフォルトで __`None`__ を返すのに対し、`os.environ` は __`KeyError`__ 例外を投げるという点です（辞書オブジェクト参照時の共通の振る舞いです）。
通常は `os.getenv` 関数を使った方が、例外をハンドルせずに済むのでシンプルなコードになります。

{{< code lang="python" title="環境変数が設定されているかどうかで分岐" >}}
import os

key = os.getenv("ACCESS_KEY")

if key:
    print("環境変数が見つかった場合の処理")

if not key:
    print("環境変数が見つからない場合の処理")
{{< /code >}}

ただし、複数の環境変数を参照する場合は、`os.environ` を使って一度に例外処理した方が分かりやすくなることがあります。

{{< code lang="python" title="いずれかの環境変数が設定されていない場合に終了する" >}}
try:
    openai.api_base = os.environ["OPENAI_API_BASE"]
    openai.api_key = os.environ["OPENAI_API_KEY"]
except KeyError as e:
    sys.exit(f"Environment variable not set: {e}")
{{< /code >}}

`os.getenv` 関数の第 2 パラメータ (__`default`__) では、キーが存在しな場合のデフォルト値を指定することが可能です。

{{< code lang="python" title="環境変数が設定されていないときにデフォルト値を使う" >}}
import os

env = os.getenv("MYAPP_ENV", default="development")
print(env)
{{< /code >}}

ちなみに、`os.environ.get()` という関数もありますが、これは `os.getenv()` 関数のエイリアスです。


すべての環境変数を列挙する
----

単一の環境変数を参照するときは `os.getenv` 関数が便利ですが、すべての環境変数をループ処理したい場合は `os.environ` オブジェクトをループ処理するとよいです。

{{< code lang="python" title="環境変数を列挙する（キーを取り出す方法）" >}}
import os

for key in os.environ:
    val = os.environ[key]
    print(f"{key}: {val}")
{{< /code >}}

{{< code lang="python" title="環境変数を列挙する（キーと値を取り出す方法）" >}}
import os

for key, val in os.environ.items():
    print(f"{key}: {val}")
{{< /code >}}


より実践的なサンプル
----

### ある環境変数が設定されていない場合にプログラムを終了する

次の `config.py` モジュールは、環境変数 `MYAPP_USER` と `MYAPP_PASS` の値を参照し、同じ名前の変数にセットします。
環境変数が見つからない場合は、対応方法を出力してプログラムを終了します。

{{< code lang="python" title="config.py" >}}
import os
import sys

def load_env_or_exit(env_name: str) -> str:
    """
    環境変数の値を取得します。
    設定されていない場合はエラーメッセージを表示してプログラムを終了します。
    """
    env_val = os.getenv(env_name)
    if not env_val:
        sys.exit(f"Error: {env_name} not set. Please consider adding a .env file with {env_name}.")
    return env_val

# 環境変数を参照
MYAPP_USER = load_env_or_exit("MYAPP_USER")
MYAPP_PASS = load_env_or_exit("MYAPP_PASS")
{{< /code >}}

{{% note %}}
環境変数の値として明示的に空文字 (`""`) がセットされているときにエラーにしたくない場合は、上記の `if not env_val:` の部分を `if env_val is None:` に変更します。
`env_val` が文字列型の場合、`if not env_val:` という条件指定は、`if env_val is None or env_val == "":` と同じ意味になります。
{{% /note %}}

さらに、[.env ファイルを使って環境変数を設定できるようにしておく](/p/gzo8d7y/)と、よりユーザーにとって扱いやすいプログラムになります。

上記の `config.py` モジュールは、別のモジュールから次のような感じ使用できます。

{{< code lang="python" title="main.py" >}}
import config

print(config.MYAPP_USER)
print(config.MYAPP_PASS)
{{< /code >}}

`config.MYAPP_USER` や `config.MYAPP_PASS` の値は、`config.py` をインポートしたときに自動的に初期化されるので、定数のように参照できます。
複数のモジュールから `config.py` をインポートする場合は、最初にインポートしたときのキャッシュが使われるので、何度も `load_env_or_exit` 関数が呼ばれてしまう心配はありません。

### あるプログラム専用のプロキシ環境変数を用意する

プロキシの設定を行う場合、一般的に `http_proxy` 環境変数が使用されますが、この環境変数を参照するのが適切ではないケースがあります。
例えば、`http_proxy` 環境変数を設定することにより、依存する別のプログラムが動作しなくなってしまうようなケースです。

このような場合は、次のようにアプリ特有の環境変数でプロキシを設定できるようにすることで対処できます。

{{< code lang="python" title="config.py" >}}
import os

# 独自の MYAPP_PROXY 環境変数が設定されていたら http_proxy よりも優先的に使う
PROXY = os.getenv("MYAPP_PROXY") or os.getenv("http_proxy")
{{< /code >}}

