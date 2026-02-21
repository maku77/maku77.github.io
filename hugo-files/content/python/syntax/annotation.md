---
title: "Python の型アノテーションのまとめ"
url: "p/rx77njz/"
date: "2025-04-19"
tags: ["Python"]
---

{{% private %}}
- [typing — Support for type hints](https://docs.python.org/3/library/typing.html)
- [Class type assignability — typing documentation](https://typing.readthedocs.io/en/latest/spec/class-compat.html#classvar)
- [Specification for the Python type system — typing documentation](https://typing.readthedocs.io/en/latest/spec/)
- PEP: Python Enhancement Proposals
  - [PEP 484 – Type Hints](https://peps.python.org/pep-0484/)
  - [PEP 526 – Syntax for Variable Annotations](https://peps.python.org/pep-0526/)
  - [PEP 585 – Type Hinting Generics In Standard Collections](https://peps.python.org/pep-0585/)
  - [PEP 591 – Adding a final qualifier to typing](https://peps.python.org/pep-0591/)
{{% /private %}}

Python で変数や関数の戻り値に型アノテーションを付ける方法のまとめです。
Visual Studio Code 上で型チェックを有効にするには、[Mypy 拡張](https://marketplace.visualstudio.com/items?itemName=ms-python.mypy-type-checker) などの type checker をインストールしておく必要があります。


組み込み型 (Built-in types)
----

### 基本型

下記は基本型の変数に型アノテーションを付ける例です。

```python
count: int = 1
average: float = 1.0
is_ok: bool = True
label: str = "Hello"
data: bytes = b"Hello"
```

このように初期値を与える場合は、型アノテーションを省略可能です。

```python
count = 1
average = 1.0
is_ok = True
label = "Hello"
data = b"Hello"
```

### コレクション

Python 3.9 以降では、コレクションの型を示すための **`list`**、**`set`**、**`dict`**、**`tuple`** を使用できます。
何もインポートせずに使用することができます。

{{% note title="古い書き方（Python 3.8 以前）" %}}
Python 3.8 以前は `typing` モジュールの `List`、`Set`、`Dict`、`Tuple` を使っていました。
```python
from typing import List
nums: List[int] = [1, 2, 3]
```
{{% /note %}}

#### リスト (list)

{{< code lang="python" title="整数型 (int) の要素を持つリスト" >}}
my_list: list[int] = [1, 2, 3]
{{< /code >}}

#### セット (set)

{{< code lang="python" title="文字列型 (str) の要素を持つセット" >}}
my_set: set[str] = {"AAA", "BBB", "CCC"}
{{< /code >}}

### 辞書 (dict)

{{< code lang="python" title="文字列型のキーと int 型の値を持つ辞書" >}}
my_dict: dict[str, int] = {"key1": 10, "key2": 20}
{{< /code >}}

#### タプル (tuple)

{{< code lang="python" title="2 つの int 要素と 1 つの str 要素を持つタプル" >}}
my_tuple: tuple[int, int, str] = (3, 7, "Hello")
{{< /code >}}

`tuple` は型パラメーターの数と要素数が一致している必要があります。
任意の数の要素を持つタプルを表現するには、以下のように **`...`** を使います。

{{< code lang="python" title="任意の数の int 要素を持つタプル" >}}
my_tuple: tuple[int, ...] = (1, 2, 3, 4, 5)
{{< /code >}}

名前付きタプルを定義するときは、`typing` モジュールの **`NamedTuple`** を使用します。

{{< code lang="python" title="名前付きタプル" >}}
from typing import NamedTuple

class Player(NamedTuple):
    name: str
    id: int = 100

p = Player("Maku")
assert p.name == "Maku"
assert p.id == 100
{{< /code >}}


Union と Optional
----

### Union（いずれかの型）

**`Union`** を使うと、複数の型のうち、いずれかを格納できる型を表現できます。
Python 3.10 以降では、`Union` の代わりに **`|`** 演算子を使ってシンプルに記述できるようになりました。

{{< code lang="python" title="int あるいは str を格納できるリスト型" >}}
# Python 3.10 以降
my_list: list[int | str] = [100, "AAA", 200, "BBB"]

# Python 3.9 以前
from typing import List, Union
my_list: List[Union[int, str]] = [100, "AAA", 200, "BBB"]
{{< /code >}}

### Optional（省略可能な型）

**`Optional`** を使うと、`None` が許容される型を表現できます。
Python 3.10 以降では、`Optional` の代わりに **`| None`** とより短く記述できます。

{{< code lang="python" title="int あるいは None を格納できる型" >}}
# Python 3.10 以降
limit: int | None = 10 if is_limited() else None
if x is not None:
    print(f"Limit: {x}")

# Python 3.9 以前
from typing import Optional
limit: Optional[int] = 10 if is_limited() else None

# Union でも表現可能
limit: Union[int, None] = 10 if is_limited() else None
{{< /code >}}


関数
----

### パラメーターと戻り値の型アノテーション

関数定義では、パラメーターと戻り値の型をアノテートできます。

```python
# 2 つの int を引数に取り、int を返す関数
def sum_two_numbers(a: int, b: int) -> int:
   return a + b

# 1 つの float を引数に取り、str を返す関数
def surface_area_of_cube(edge_length: float) -> str:
    return f"The surface area of the cube is {6 * edge_length ** 2}."

# 戻り値を返さない関数
def greet(name: str) -> None:
    print(f"Hello, {name}!")
```

ちなみに、クラスのコンストラクター `__init__()` は戻り値を持たないので、戻り値の型アノテーションを付けるときは `-> None` と記述します。

```python
class User:
    # __init__ メソッドの戻り値は必ず None （self の型は省略可能）
    def __init__(self, name: str) -> None:
        self.name = name
```

### ジェネレーター関数

`yield` を使ったジェネレーター関数は、戻り値の型として **`Iterator`** を指定します。
`Iterator` 型のオブジェクトは、`for-in` ループや `next()` 関数で要素を 1 つずつ取り出すことができます。

```python
from collections.abc import Iterator

def fib(n: int) -> Iterator[int]:
    a, b = 0, 1
    while a < n:
        yield a
        a, b = b, a + b

for i in fib(100):
    print(i, end=" ")

# => 0 1 1 2 3 5 8 13 21 34 55 89
```

### Callable（関数を表す型）

`typing` モジュールの **`Callable`** を使うと、関数として呼び出しが可能な型を表現できます。
例えば、`f: Callable[[str], int]` のように定義した変数 `f` には、1 つの `str` 型の引数を取り、`int` 型の戻り値を返す関数を代入できます。

```python
from typing import Callable

def my_func(x: str) -> int:
    return len(x)

f: Callable[[str], int] = my_func

print(f("Hello"))  # => 5
```

下記は、リスナー（コールバック関数）をセットできるクラスの実装例です。

{{< accordion title="リスナーをセットできるクラスの実装例" >}}
{{< code lang="python" >}}
from typing import Callable


class MyClass:
    # Define a type alias for the listener function
    Listener = Callable[[str], None]

    def __init__(self) -> None:
        self._listeners: list[MyClass.Listener] = []

    def add_listener(self, listener: Listener) -> None:
        self._listeners.append(listener)

    def remove_listener(self, listener: Listener) -> None:
        self._listeners.remove(listener)

    def clear_listeners(self) -> None:
        self._listeners.clear()

    def notify(self, arg: str) -> None:
        for listener in self._listeners:
            listener(arg)


# Example usage
if __name__ == "__main__":

    def my_listener(arg: str) -> None:
        print(f"Listener called with argument: {arg}")

    my_class = MyClass()
    my_class.add_listener(my_listener)
    my_class.notify("Hello")  # コールバック関数が呼び出される
    my_class.remove_listener(my_listener)
    my_class.notify("Goodbye")  # 何も呼び出されない
{{< /code >}}
{{< /accordion >}}


クラス変数とインスタンス変数
----

### クラス変数

`typing` モジュールの **`ClassVar`** を使うと、インスタンス経由では参照できないクラス変数を表現できます。

{{< code lang="python" hl_lines="3 8 13" >}}
class User:
    # クラス変数の型アノテーションの例
    __count: ClassVar[int] = 0

    def __init__(self, name: str) -> None:
        self.name = name
        # クラス変数の参照方法1
        User.__count += 1

    @classmethod
    def get_count(cls) -> int:
        # クラス変数の参照方法2
        return cls.__count

user1 = User("Alice")
user2 = User("Bob")
user3 = User("Charlie")
print(User.get_count())  # 3
{{< /code >}}

`__count` はクラス変数としてアノテートされているので、上記のように **`User.__count`** や **`cls.__count`** でアクセスします。
`self.__count` のようにインスタンス変数としてアクセスすることはできません。

### インスタンス変数

インスタンス変数は、コンストラクタ内で初期化するときに型アノテーションを付けることができます。

{{< code lang="python" hl_lines="4" >}}
class ScoreBoard:
    def __init__(self) -> None:
        # インスタンス変数の型アノテーションの例
        self.scores: list[int] = []

    def append_score(self, score: int) -> None:
        self.scores.append(score)
{{< /code >}}


定数 (Final)
----

`typing` モジュールの **`Final`** を使うと、変更不可な（再代入できない）定数を表現できます。

{{< code lang="python" title="モジュールレベルの定数" >}}
from typing import Final

FINAL_INT: Final = 100
FINAL_STR: Final = "Hello"
FINAL_LIST: Final = [1, 2, 3]
FINAL_DICT: Final = {"key1": 10, "key2": 20}
FINAL_SET: Final = {"AAA", "BBB", "CCC"}
FINAL_TUPLE: Final = ("a", "b", 100)
{{< /code >}}

もう少し具体的に `Final[int]` や `Final[str]` と定義することもできますが、通常は上記のように `Final` とだけ書けば十分です。

クラス定数も同様に `Final` を使って表現できます。

{{< code lang="python" title="クラス定数" >}}
from typing import Final

class Config:
    INVALID_ID: Final = -1
{{< /code >}}


列挙型 (Enum)
----

列挙型は、`enum` モジュールの **`Enum`** クラスを継承したクラスとして表現します。

```python
import enum

class Colors(enum.Enum):
    RED = 1
    GREEN = 2
    BLUE = 3

print(Colors.RED)  # => Colors.RED
print(Colors.RED.name)  # => RED
print(Colors.RED.value)  # => 1
```

列挙型クラスを `for-in` ループで回すと、値を 1 つずつ参照できます。

```python
for c in Colors:
    print(f"{c}\t{c.name}\t{c.value}")
```

{{< code title="出力結果" >}}
Colors.RED      RED     1
Colors.GREEN    GREEN   2
Colors.BLUE     BLUE    3
{{< /code >}}


ループ処理できることを示す型 (Iterable)
----

リストやタプルなどのコレクションは、`for-in` でループ処理することができます。
こういった、ループ処理が可能なオブジェクトを表す型として、**`Iterable`** が用意されています。
次の例では、ループ処理可能な数値コレクションを受け取る `squares()` 関数を定義しています。


```python
from typing import Iterable

# define Number type which can be int or float
Number = int | float


def squares(numbers: Iterable[Number]) -> None:
    """Print the squares of the given numbers."""
    for num in numbers:
        print(num**2)


if __name__ == "__main__":
    my_list = [1, 2, 3]
    my_tuple = (1, 2, 3)
    my_set = {1, 2, 3}

    # squares 関数は、リスト、タプル、セットを同じように処理できる
    squares(my_list)   # => 1 4 9
    squares(my_tuple)  # => 1 4 9
    squares(my_set)    # => 1 4 9
```

引数として受け取ったオブジェクトを `for-in` ループで処理するだけなら、`list` 型のパラメーターとして定義するより、上記のように `Iterable` 型として定義した方が汎用性が高くなります。
一方で、関数の戻り値の型としては、具体的な `list` 型などのオブジェクトを返すようにします。

```python
def squares(numbers: Iterable[Number]) -> list[Number]:
    """Return the squares of the given numbers."""
    return [num**2 for num in numbers]
```

独自クラスを `Iterable` な型として扱えるようにするには、以下のように **`__iter__()`** メソッドを実装します。

```python
class MyNumbers:
    def __init__(self, *args: Number):
        self.numbers = args

    def __iter__(self):
        return iter(self.numbers)


if __name__ == "__main__":
    obj = MyNumbers(1.5, 2, 3)
    squares(obj)  # => 2.25 4 9
```

