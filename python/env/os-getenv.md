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

