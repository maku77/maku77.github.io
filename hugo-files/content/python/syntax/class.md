---
title: "Python のクラス定義の基本 (class)"
url: "p/ru9mxam/"
date: "2012-06-07"
lastmod: "2023-06-05"
tags: ["Python"]
changes:
  - 2023-06-05: 全体的に内容を拡充
aliases: /python/syntax/class.html
---

__`class`__ キーワードを使って、Python のクラスを定義することができます。


コンストラクタとインスタンスメソッド
----

メソッドは通常の関数と同様に __`def`__ キーワードで定義していくのですが、__`__init__`__ という名前のメソッドを定義すると、インスタンス生成時（`クラス名()`）に呼び出されるコンストラクタとして扱われます。
コンストラクタの中では、自分自身のインスタンスを参照するための __`self`__ を使って、属性の初期化などを行います。

{{< code lang="python" title="Person クラスを定義してみる" >}}
class Person:
    """クラスの説明をここに書く"""

    def __init__(self, name: str):
        """コンストラクタの説明をここに書く"""
        self.name = name

    def hello(self):
        """メソッドの説明をここに書く"""
        print(f"Hello, {self.name}!")
{{< /code >}}

コンストラクタやメソッドの第 1 引数には、必ず自分自身のインスタンスを参照するための __`self`__ 引数を配置します。
実際には、名前は何でもよいのですが、公式のスタイルガイドで `self` という名前を使うことが推奨されています（参考: [PEP 8](https://pep8.org/#function-and-method-arguments)）。

上記のように定義したクラスは、次のように使用することができます。

{{< code lang="python" >}}
person = Person("maku")  # インスタンスを生成する
person.hello()           # メソッドを呼び出す
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ python main.py
Hello, maku!
{{< /code >}}


クラス属性 (class attribute)
----

__クラス属性__ は、インスタンスを生成せずにアクセスできる属性です。
次のクラスは、`num` というクラス属性を持っています。
クラス属性には、`クラス名.クラス属性名` という形でアクセスできます。

{{< code lang="python" title="クラス属性を定義する" >}}
# クラス属性を持つクラス
class Holder:
    num: int = 0

# クラス属性を参照する
print(Holder.num)  # => 0
Holder.num = 100
print(Holder.num)  # => 100
{{< /code >}}

クラス定数も同様に定義できます。
名前を大文字にするだけです。

{{< code lang="python" title="クラス定数を定義する" >}}
# クラス定数を持つクラス
class Author:
    NAME: str = "Maku"
    AGE: int = 14

# クラス定数を参照する
print(Author.NAME)  # => Maku
print(Author.AGE)   # => 14
{{< /code >}}


クラスメソッド (class method)
----

__クラスメソッド__ は、インスタンスを生成せずに呼び出せるメソッドで、__`@classmethod`__ デコレーターを使って定義します。
クラスメソッドの第 1 引数には、自身のクラスメソッドを示す __`cls`__ を配置します（この名前も、[PEP 8](https://pep8.org/#function-and-method-arguments) で推奨されています）。
クラスメソッドの中からは、__`cls.クラス属性名`__ という形で、クラス属性にアクセスできます。

{{< code lang="python" title="クラスメソッドを定義する" >}}
class Holder:
    num = 0

    @classmethod
    def print(cls):
        print(cls.num)

if __name__ == "__main__":
    Holder.num = 1
    Holder.print()  # => 1
{{< /code >}}

クラスメソッドは、ファクトリーメソッドの実装に使われることがあります。
次の `mysterios_person` メソッドは、特殊な `Person` インスタンスを生成するためのファクトリーメソッドです。

```python
class Person:
    def __init__(self, name: str, age: int):
        self.name = name
        self.age = age

    @classmethod
    def mysterious_person(cls) -> "Person":
        return cls(name="John Doe", age=20)

# 使用例: ファクトリーメソッドでインスタンス生成
person = Person.mysterious_person()
```


静的メソッド (static method)
----

__静的メソッド__ は、インスタンスを生成せずに呼び出せるメソッドで、__`@staticmethod`__ デコレーターを使って定義します。
静的メソッドはクラスメソッドと異なり、第 1 引数で `cls` を受け取りません。
単なる関数とほぼ同等ですが、クラス内で静的メソッドとして定義することにより、そのクラスに関連するユーティリティ関数であることが明確になります。
また、クラスをインポートするだけで静的メソッドにアクセスできるようになるなどの利点もあります。

```python
class Formatter:
    # ...

    @staticmethod
    def make_indent(level: int) -> str:
        return " " * 4 * level
```


メソッドや属性を private にする
----

クラス内のメソッドや属性を private 扱いにするには、メソッド名の前にアンダースコア __`_`__ を 1 つ付けます。
ただし、これは単なる慣習で、実際には外からアクセスできてしまいます。

```python
class Person:
    def __init__(self, name: str, age: int):
        self._name = name
        self._age = age

if __name__ == "__main__":
    p = Person("Maku", 14)
    print(p._name)  # 見えちゃう
    print(p._age)   # 見えちゃう
```

外からアクセスできないようにするには、メソッド名の前にアンダースコアを 2 つ付けます。

```python
class Person:
    def __init__(self, name: str, age: int):
        self.__name = name
        self.__age = age

if __name__ == "__main__":
    p = Person("Maku", 14)
    print(p.__name)  # エラー！
    print(p.__age)   # エラー！
```

{{% note title="Name Mangling" %}}
アンダースコアを 2 つ付けたときに外から属性にアクセスできなくなるのは、Python の __Name Mangling（名前修飾）__ という仕組みが働いているからです。
属性名がアンダースコア 2 つで始まっていると、Python は内部的にその属性名を __`_クラス名__属性名`__ という名前に置き換えます。
この仕組みにより、外部からの不用意なアクセスを避けるとともに、サブクラスで定義された属性の名前との衝突を防ぐ効果があります。
{{% /note %}}

