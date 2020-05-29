---
title: "Python から環境変数を参照する (os.getenv, os.environ)"
date: "2020-01-29"
---

指定した環境変数を取得する
----

Python から OS の環境変数を取得するには、**`os.getenv()`** 関数を使用するか、**`os.environ`** を辞書オブジェクトとして参照します。

#### 例: 環境変数 PATH の値を表示

```python
import os

print(os.getenv('PATH'))
print(os.environ['PATH'])
```

違いは、存在しないキーを指定した場合に `os.getenv()` がデフォルトで `None` を返すのに対し、`os.environ` は **`KeyError`** 例外を投げるという点です。

```python
import os

try:
    print(os.environ['ACCESS_KEY'])
except KeyError as err:
    print('Environment variable not found:', err)
```

このような例外ハンドルは面倒なので、 **通常は `os.getenv()` 関数を使えばよい** と思います。

```python
import os

key = os.getenv('ACCESS_KEY')

if key:
    print('環境変数が見つかった場合の処理')

if not key:
    print('環境変数が見つからない場合の処理')
```

`os.getenv()` の第 2 パラメータ (`default`) では、 **キーが存在しな場合のデフォルト値** を指定することが可能です。

```python
import os

env = os.getenv('MYAPP_ENV', default='development')
print(env)
```

ちなみに、`os.environ.get()` という関数もありますが、これは `os.getenv()` 関数のエイリアスです。


すべての環境変数を列挙する
----

単一の環境変数を参照するときは `os.getenv()` 関数が便利ですが、すべての環境変数をループ処理したい場合は `os.environ` の方を使うとよいです。

#### 方法 1

```python
import os

for key in os.environ:
    val = os.environ[key]
    print('{}: {}'.format(key, val))
```

#### 方法 2

```python
import os

for key, val in os.environ.items():
    print('{}: {}'.format(key, val))
```


より実践的なサンプル
----

### ある環境変数が設定されていない場合にプログラムを終了する

次の `config.py` モジュールは、環境変数 `MYAPP_USER` と `MYAPP_PASS` の値を参照し、同じ名前の変数にセットします。
環境変数が見つからない場合は、対応方法を出力してプログラムを終了します。

#### config.py

```python
import os
import sys

# 環境変数の値を取得します。
# 見つからなければエラーメッセージを表示してプログラムを終了します。
def load_env_or_exit(env_name):
    val = os.getenv(env_name)
    if val is None:
        print('Error: %s not found. Please consider adding a .env file with %s.'
            % (env_name, env_name), file=sys.stderr)
        sys.exit(1)
    return val

# 環境変数を参照
MYAPP_USER = load_env_or_exit('MYAPP_USER')
MYAPP_PASS = load_env_or_exit('MYAPP_PASS')
```

さらに、[.env ファイルを使って環境変数を設定できるようにしておく](./dotenv.html) と、よりユーザーにとって扱いやすいプログラムになります。

上記の `config.py` モジュールは、別のモジュールから次のように使用することを想定しています。

```python
import config

print(config.MYAPP_USER)
print(config.MYAPP_PASS)
```

### あるプログラム専用のプロキシ環境変数を用意する

プロキシの設定を行う場合、一般的に `http_proxy` 環境変数が使用されますが、この環境変数を参照するのが適切ではないケースがあります。
例えば、`http_proxy` 環境変数を設定することにより、依存する別のプログラムが動作しなくなってしまうようなケースです。

このような場合は、次のようにアプリ特有の環境変数でプロキシを設定できるようにすることで対処できます。

#### config.py

```python
import os

# MYAPP_PROXY 環境変数が設定されていたら http_proxy よりも優先的に使う
PROXY = os.getenv('MYAPP_PROXY') or os.getenv('http_proxy')
```

