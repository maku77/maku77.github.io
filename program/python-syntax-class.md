---
title: Python のクラス構文
created: 2012-06-07
---

コンストラクタ
====

```python
class MyClass:
    def __init__(self, param1, param2):
        self.param1 = param1
        self.param2 = param2

# インスタンス化
obj = MyClass(10, 20)
```

オブジェクトを文字列に変換できるようにする
====

```python
class MyClass:
    def __str__(self):
        return "Name=%s, Age=%d" % (self.name, self.age)
```

オブジェクトを ```str()``` や ```print()``` に渡したときに、
自動的に ```__str__(self)``` メソッドが呼ばれるようになっています。
Java の ```toString()``` メソッドのようなものです。

メソッドやアトリビュートを private にする
====

メソッドを private にするには、メソッド名の前にアンダースコア ```_``` を１つ付けます。
これは単なる慣習で、実際には外からアクセスできます。

```python
def _calc_internal(self):
    ...
```

外からアクセスできないようにするには、メソッド名の前にアンダースコアを 2 つ付けます。

```python
def __calc_internal(self):
    ...
```

アンダースコアを 2 つ付けると、Python の内部的には名前が修飾され、サブクラスで定義されたメソッドの名前との衝突を防ぐ効果があります。

クラス定数を定義する
====

```python
class MyClass:
    NAME = 'maku'
    AGE = 100

    def print_name(self):
        print(self.NAME)
```
