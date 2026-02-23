---
title: "Pythonメモ: デコレーター (decorator) で関数やクラスを拡張する (`@wrap`)"
url: "p/ma54wkb/"
date: "2026-02-23"
tags: ["python"]
---

デコレーターとは
----
Python のデコレーター (decorator) は、もとの関数やクラスの定義を変更せずにそれらの機能を拡張するための関数です。
デコレーターは、関数やクラスの前に **`@`** 記号を使用して指定されます。

{{< code lang="python" title="関数にデコレーターを適用する例" >}}
@my_func_decorator
def hello():
    pass
{{< /code >}}

{{< code lang="python" title="クラスにデコレーターを適用する例" >}}
@my_class_decorator
class MyClass:
    pass
{{< /code >}}

Python は標準でいくつかの便利なデコレーターを提供しています。

| デコレーター | 用途 | モジュール |
| ---- | ---- | ---- |
| `@property` | メソッドをプロパティ化 (getter/setter) | ビルトイン |
| `@classmethod` | クラス内でクラスメソッドを定義（`cls`引数あり） | ビルトイン |
| `@staticmethod` | クラス内で静的メソッドを定義（`cls`引数なし） | ビルトイン |
| `@abstractmethod` | クラス内で抽象メソッドを定義 | `abc` |
| `@functools.lru_cache` | 関数結果をメモ化（キャッシュ） | `functools` |
| `@dataclasses.dataclass` | データクラスを定義（Python 3.7+） | `dataclasses` |


デコレーターを定義する
----

デコレーターの正体は、関数やクラスを引数として受け取り、拡張されたバージョンを返す関数です。
つまり、以下のようなデコレーターの設定は、

```python
@my_decorator
def hello():
    pass
```

実際には、以下のようにデコレーター関数を呼び出すことと同じです。

```python
def hello():
    pass

hello = my_decorator(hello)
```

下記の関数デコレーター (`my_decorator`) は、関数の呼び出し前後にメッセージを表示するデコレーターの実装例です。
ポイントは、ラッパー関数である `wrapper` を戻り値として返しているところです。
ラッパー関数の中では、元の関数 `func` を呼び出さなければいけません（ラッパーなので当然ですが）。

```python
from datetime import datetime
from functools import wraps

# 関数デコレーターは、関数を受け取り、拡張されたラッパー関数を返すように実装します
def my_decorator(func):
    @wraps(func)
    def wrapper(*args, **kwargs):
        print(f"呼び出し時の時刻: {datetime.now()}")
        result = func(*args, **kwargs)
        print("呼び出し終了")
        return result

    return wrapper

@my_decorator
def hello(name):
    print(f"Hello, {name}!")

hello("Alice")
```

{{< code title="実行結果" >}}
呼び出し時の時刻: 2026-02-23 17:27:27.661955
Hello, Alice!
呼び出し終了
{{< /code >}}

ラッパー関数に付けている **`@wraps`** デコレーターに関しては次のセクションで説明します。
ラッパー関数のパラメーターの `*args, **kwargs` は、任意の引数を受け取るためのイディオムだと理解しておけばよいでしょう。


ラッパー関数定義用の `@wraps` デコレーター
----

デコレーター内で定義するラッパー関数は、**`@wraps`** デコレーターを付けて定義します。
これは、ラッパー関数が元の関数の属性（名前や docstring の内容）を引き継ぐためのものです。
次のコードは、**`@wraps`** デコレーターを使用せずに定義した関数デコレーターと、**`@wraps`** デコレーターを使用して定義した関数デコレーターの違いを示しています。

```python
from functools import wraps

# wraps デコレーターを使わずに定義した関数デコレーター（悪い例）
def deco1(func):
    def wrapper(*args, **kwargs):
        return func(*args, **kwargs)
    return wrapper

# wraps デコレーターを使って定義した関数デコレーター（よい例）
def deco2(func):
    @wraps(func)
    def wrapper(*args, **kwargs):
        return func(*args, **kwargs)
    return wrapper

@deco1
def hello1():
    """This is hello1 function"""
    print("Hello1")

@deco2
def hello2():
    """This is hello2 function"""
    print("Hello2")

if __name__ == "__main__":
  print(hello1.__name__)  # => wrapper
  print(hello1.__doc__)  # => None
  print(hello2.__name__)  # => hello2
  print(hello2.__doc__)  # => This is hello2 function
```

`hello1` 関数の `__name__` や `__doc__` 属性の値がラッパー関数のものに置き換わってしまっているのに対して、`hello2` 関数の方は元の関数のものが保持されていることがわかります。
`@wraps` デコレーターは必ずしも必要ではありませんが、基本的には付けておくべきです。


クラス用のデコレーターを定義する
----

関数用のデコレーターほど利用頻度は高くありませんが、クラス用のデコレーターを定義することもできます。
クラスデコレーターは、クラスオブジェクト (`cls`) を引数として受け取り、拡張されたクラスオブジェクトを返す関数です。
下記の実装例では、もとのクラス `MyClass` にデコレーターを適用することで、新しい属性 `x` と新しいメソッド `new_method` を追加しています。

```python
def my_class_decorator(cls):
    cls.x = 100

    class WrappedClass(cls):
        def new_method(self):
            print("これは新しいメソッドです")

    return WrappedClass

@my_class_decorator
class MyClass:
    def original_method(self):
        print("これは元のメソッドです")

obj = MyClass()
obj.original_method()  # => これは元のメソッドです
obj.new_method()       # => これは新しいメソッドです
print(obj.x)           # => 100
```

mypy などで型チェックを行っている場合、追加された `new_method` や `x` を参照している部分でエラーが発生する可能性があります。
今のところ、あまりエレガントな解決策はありませんが、とりあえず下記のように型ヒントを追加することで型チェックエラーを回避することができます。

{{< code lang="python" hl_lines="3-5" >}}
@my_class_decorator
class MyClass:
    # 型チェッカー用のヒント
    x: int
    def new_method(self): ...

    def original_method(self):
        print("これは元のメソッドです")
{{< /code >}}


デコレーターの実装例
----

### 関数呼び出しログ

{{< code lang="python" title="libs/decorators.py" >}}
from functools import wraps

def log(func):
    """関数呼び出しの前後にログを出力するデコレータ"""

    @wraps(func)
    def wrapper(*args, **kwargs):
        print(f">> {func.__name__}: {args} {kwargs}")
        result = func(*args, **kwargs)
        print(f"<< {func.__name__}: {result}")
        return result

    return wrapper
{{< /code >}}

{{< code lang="python" title="main.py（使用例）" >}}
from libs import decorators

@decorators.log
def hello(name: str) -> str:
    return f"Hello, {name}!"

if __name__ == "__main__":
    hello("Maku")
{{< /code >}}

{{< code title="実行結果" >}}
>> hello: ('Maku',) {}
<< hello: Hello, Maku!
{{< /code >}}

### 関数の実行時間計測

{{< code lang="python" title="libs/decorators.py" >}}
from functools import wraps
import time

def benchmark(func):
    """関数の実行時間を計測するデコレータ"""

    @wraps(func)
    def wrapper(*args, **kwargs):
        start = time.time()
        result = func(*args, **kwargs)
        end = time.time()
        print(f"{func.__name__} executed in {end - start:.3f}s")
        return result

    return wrapper
{{< /code >}}

{{< code lang="python" title="main.py（使用例）" >}}
import time
from libs import decorators

@decorators.benchmark
def slow_function():
    time.sleep(1)  # 1秒待機

if __name__ == "__main__":
    slow_function()
{{< /code >}}

{{< code title="実行結果" >}}
slow_function executed in 1.001s
{{< /code >}}

### メモ化（関数実行結果のキャッシュ）

関数の実行結果をメモリ上にキャッシュし、同じ引数で呼び出されたときにキャッシュされた結果を返すデコレータの実装例です（後述する Python 標準の `functools.cache` デコレーターを使う方法もあります）。

{{< code lang="python" title="libs/decorators.py" >}}
from functools import wraps

def cache(func):
    """関数の結果をキャッシュするデコレータ（メモ化）"""
    cache = {}

    @wraps(func)
    def wrapper(*args, **kwargs):
        # キャッシュキーを作成（argsとkwargsの両方を考慮）
        cache_key = (args, tuple(sorted(kwargs.items())))
        if cache_key in cache:
            return cache[cache_key]
        result = func(*args, **kwargs)
        cache[cache_key] = result
        return result

    return wrapper
{{< /code >}}


{{< code lang="python" title="main.py（使用例）" >}}
from libs import decorators

@decorators.cache
def fib(n: int):
    if n < 2:
        return n
    return fib(n - 1) + fib(n - 2)

if __name__ == "__main__":
    print(fib(100))  # => 354224848179261915075
{{< /code >}}


Python3 標準で使用できるデコレーターの使用例
----

### `cache`, `lru_cache` （メモ化） {#lru_cache}

- [`@functools.cache` — Python 3 documentation](https://docs.python.org/3/library/functools.html#functools.cache)
- [`@functools.lru_cache` — Python 3 documentation](https://docs.python.org/3/library/functools.html#functools.lru_cache)

**`functools.cache`**（および **`functools.lru_cache`**）デコレーターは、関数の実行結果をキャッシュするためのデコレーターです。
スレッドセーフ設計になっているので、マルチスレッド環境でも安全に使用できます。

{{< code lang="python" title="cache デコレーターの使用例" >}}
from functools import cache

@cache  # lru_cache(maxsize=None) と同様
def fib(n: int):
    if n < 2:
        return n
    return fib(n - 1) + fib(n - 2)

if __name__ == "__main__":
    print(fib(100))  # 354224848179261915075
    print(fib.cache_info())  # CacheInfo(hits=98, misses=101, maxsize=None, currsize=101)
{{< /code >}}

デコレートした関数オブジェクトには **`cache_info()`** メソッドが追加され、上記のようにキャッシュのヒット数やミス数などの情報を取得することができます。
キャッシュを不揮発（ファイル）に保存したい場合は、[`diskcache` などのサードパーティ製のライブラリを使用する](/p/msu6xd9/) 必要があります。

### `dataclass` （データクラスの定義）

- [`@dataclasses.dataclass` — Python 3 documentation](https://docs.python.org/3/library/dataclasses.html)

**`dataclasses.dataclass`** デコレーターは、データクラスを定義するためのデコレーターです。
init メソッドや repr メソッドなどの特殊メソッドを自動生成してくれるため、データクラスを簡単に定義することができます。

{{< code lang="python" title="dataclass デコレーターの使用例" >}}
from dataclasses import dataclass

@dataclass
class Person:
    name: str
    age: int
    city: str = "Tokyo"  # デフォルト値

p = Person("太郎", 30)
print(p)  # Person(name='太郎', age=30, city='Tokyo')
{{< /code >}}

- 参考: [Python の `dataclass` デコレーターで簡単にデータクラスを定義する](/p/xqkgvb6/)


### `property`（getter/setter のプロパティ化）

- [`@property` — Python 3 documentation](https://docs.python.org/3/library/functions.html#property)

**`@property`** デコレーターは、クラスのメソッドをプロパティ化するためのデコレーターです。
プロパティ化されたメソッドは、属性のようにアクセスすることができます。

{{< code lang="python" title="property デコレーターの使用例" >}}
class Circle:
    def __init__(self, radius):
        self._radius = radius  # 内部変数（非公開）

    @property
    def radius(self):
        return self._radius

    # radius プロパティはセットも可能
    @radius.setter
    def radius(self, value):
        if value < 0:
            raise ValueError("半径は正の値でなければなりません")
        self._radius = value

    @property
    def area(self):
        return 3.14 * self._radius**2


if __name__ == "__main__":
    c = Circle(5)
    print(c.radius)  # => 5
    print(c.area)    # => 78.5

    c.radius = 10    # setter 経由で更新
    print(c.radius)  # => 10
    print(c.area)    # => 314.0
{{< /code >}}

上記の例では、`radius` プロパティは読み書き可能で、`area` プロパティは読み取り専用になっています。
`@property` デコレーターを使うと、クラスの属性をうまくカプセル化できます。

