---
title: "Python の dataclass デコレーターで簡単にデータクラスを定義する"
url: "p/xqkgvb6/"
date: "2023-06-20"
tags: ["Python"]
---

dataclass デコレーターによるデータクラス定義の基本
----

Python の標準ライブラリが提供している [dataclasses](https://docs.python.org/ja/3/library/dataclasses.html) モジュールの __`dataclass`__ デコレーターを使うと、少ないコードでデータクラス（データを扱うクラス）を定義することができます。

下記の `NamedCounter` クラスは 2 つのインスタンス変数（`name` と `counter`）を持つクラスの実装例ですが、通常はこのようにコンストラクタ（`__init__` メソッド）の中でインスタンス変数を初期化すると思います。

{{< code lang="python" title="通常のクラス定義方法" >}}
class NamedCounter:
    def __init__(self, name):
        self.name = name
        self.count = 0

    def increment(self):
        self.count += 1

    def __str__(self):
        return f"{self.name}: {self.count}"

if __name__ == "__main__":
    counter = NamedCounter("hello")
    print(counter)  # hello: 0
    counter.increment()
    print(counter)  # hello: 1
{{< /code >}}

これくらいならよいのですが、インスタンス変数（フィールド）が増えてくると、コンストラクタの記述が面倒になってきます。
クラス定義時に `dataclass` デコレーターを付けると、このような `__init__` の定型処理を自動生成してくれます。
次の例では、`dataclass` デコレーターを使って、2 つのインスタンス変数 (`name`, `count`) を持つクラスを定義しています。
`name` と `count` はクラス変数（クラス属性）と同様の記法で定義していますが、`dataclass` デコレーターを付けた場合はインスタンス変数の定義とみなされることに注意してください。
つまり、`name` と `count` の値は、`NamedCounter` のインスタンスごとに異なる値を保持できます。

{{< code lang="python" title="dataclass デコレーターを使ったクラス定義" hl_lines="3" >}}
from dataclasses import dataclass

@dataclass
class NamedCounter:
    name: str
    count: int = 0

    def increment(self):
        self.count += 1

if __name__ == "__main__":
    counter = NamedCounter("hello")
    print(counter)  # NamedCounter(name='hello', count=0)
    counter.increment()
    print(counter)  # NamedCounter(name='hello', count=1)
{{< /code >}}

内部的には、次のような `__init__` メソッドや `__repr__` メソッドが生成されています。
各変数の出力順は、フィールド定義の順番に従います。

```python
def __init__(self, name: str, count: int = 0):
    self.name = name
    self.count = count

def __repr__(self) -> str:
    return f"NamedCounter(name='{self.name}', count={self.count})"
```

クラス内に明示的に `__init__` メソッドや `__repr__` メソッドが定義されている場合は、そちらの実装が優先的に使われます。


比較可能なデータクラスを定義する
----

`dataclass` デコレーターは、同値比較用の __`__eq__`__ メソッドもデフォルトで生成してくれます。
つまり、次のように `==` 演算子や `!=` 演算子による比較が可能になります。

```python
from dataclasses import dataclass

@dataclass
class Data:
    name: str
    count: int

if __name__ == "__main__":
    print(Data("a", 1) == Data("a", 1))  # True
    print(Data("a", 1) != Data("a", 1))  # False
```

ただし、2 つのインスタンスを大小比較できるようにするには、`dataclass` デコレーターの __`order=True`__ フラグを指定する必要があります。

```python
from dataclasses import dataclass

@dataclass(order=True)
class Data:
    name: str
    count: int

if __name__ == "__main__":
    print(Data("a", 1) < Data("a", 1))  # False
    print(Data("a", 1) < Data("b", 1))  # True
    print(Data("a", 1) < Data("a", 2))  # True
```


不変なデータクラスを定義する
----

`dataclass` デコレーターに __`frozen=True`__ フラグを付けると、そのクラスのインスタンスを不変 (immutable) にすることができます。
つまり、インスタンス生成後にフィールドへの代入ができなくなります。

```python
from dataclasses import dataclass

@dataclass(frozen=True)
class Data:
    name: str
    count: int

if __name__ == "__main__":
    d = Data(name="foo", count=1)
    d.name = "bar"  # dataclasses.FrozenInstanceError
    d.count = 2     # dataclasses.FrozenInstanceError
```

