---
title: "Pythonメモ: 関数実行結果をファイル（不揮発）にキャッシュする (`diskcache`)"
url: "p/msu6xd9/"
date: "2026-02-23"
tags: ["python"]
---

diskcache ライブラリ
----

Python の関数の実行結果をキャッシュ（メモ化）したいときは、Python に標準搭載されている [`functools.lru_cache` デコレーターを使う](/p/ma54wkb/#lru_cache) のが簡単ですが、これはメモリ上にキャッシュを保存するため、プロセスが終了するとキャッシュも消えてしまいます。

関数の実行結果をファイルなどの不揮発なストレージに保存するためには、OSS ライブラリを使う必要があります。
ここでは、[`diskcache`](https://pypi.org/project/diskcache/) というライブラリを紹介します。

- [DiskCache API Reference](https://grantjenks.com/docs/diskcache/api.html)

最初に `pip` か `uv add` で `diskcache` をインストールしておきます。

{{< code lang="console" title="diskcache のインストール" >}}
# pip の場合
$ pip install diskcache

# uv の場合
$ uv add diskcache
{{< /code >}}


diskcache の使用例
----

`diskcache` ライブラリを使って関数をメモ化するには、**`Cache`** クラスのインスタンスを作成し、その **`memoize`** メソッドをデコレーターとして使用します。
次の例では、`fetch_todo` 関数の実行結果を 1 日（= 24 * 60 * 60 秒）キャッシュするように設定しています。
キャッシュ内容は、`directory` 引数で指定したディレクトリ以下（下記例では `.cache`）に、SQLite データベースのファイルとして保存されます。

{{< code lang="python" title="diskcache を使った関数実行結果のキャッシュ例" >}}
import json
import time
from typing import Any
from urllib.request import urlopen
import diskcache

CACHE_DIR = ".cache"  # キャッシュディレクトリ
CACHE_EXPIRE = 60 * 60 * 24  # キャッシュ有効期限（秒）。デフォルトは None で無制限
cache = diskcache.Cache(directory=CACHE_DIR)

@cache.memoize(expire=CACHE_EXPIRE)
def fetch_todo(id: int) -> dict[str, Any]:
    """指定された ID の TODO を Web API で取得する関数"""

    time.sleep(1)  # キャッシュ効果を見るために意図的に 1 秒スリープ
    url = f"https://jsonplaceholder.typicode.com/todos/{id}"
    with urlopen(url) as response:
        return json.load(response)

if __name__ == "__main__":
    todo1 = fetch_todo(1)
    todo2 = fetch_todo(2)
    print(todo1)
    print(todo2)
{{< /code >}}

{{< code title="実行結果" >}}
{'userId': 1, 'id': 1, 'title': 'delectus aut autem', 'completed': False}
{'userId': 1, 'id': 2, 'title': 'quis ut nam facilis et officia qui', 'completed': False}
{{< /code >}}

`directory` パラメーターを指定しない場合は、一時的なキャッシュディレクトリが自動作成されるようですが、毎回ランダムな名前で作成されるため、次回起動時にキャッシュを再利用できなくなってしまうので要注意です（キャッシュディレクトリのパスは `cache.directory` で確認できます）。

