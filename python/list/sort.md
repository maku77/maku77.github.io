---
title: "リストを昇順ソート／降順ソートする (list.sort/sorted)"
date: "2013-04-25"
---

リストを昇順／降順ソートする (list.sort)
----

### 昇順ソート

```python
a = [3, 5, 2, 1, 4]
a.sort()
print(a)  #=> [1, 2, 3, 4, 5]
```

list オブジェクトの `sort()` メソッドを呼び出すと、要素が昇順ソートされます。
そのリスト自身の内容が変更されることに注意してください（破壊的メソッド）。
なお、tuple は不変なので `sort()` できません。


### 降順ソート

```python
a = [3, 5, 2, 1, 4]
a.sort(reverse=True)
print(a)  #=> [5, 4, 3, 2, 1]
```

list を `sort()` するときに、`reverse` パラメータを `True` に設定すると、降順にソートされます。


ソート結果を新しいリストで取得する (sorted)
----

`list.sort()` メソッドの代わりに `sorted()` 関数を使用すると、元のリストを変更せずに、ソートされた新しいリストを生成することができます。

```python
>>> a = [3, 5, 2, 1, 4]
>>> b = sorted(a)
>>> a
[3, 5, 2, 1, 4]
>>> b
[1, 2, 3, 4, 5]
```

`sorted()` 関数は、immutable なタプルに対しても適用することができます。

```python
>>> t = (3, 1, 2)
>>> sorted(t)
[1, 2, 3]
```


独自クラスのオブジェクトのリストをソートする
----

### lt 演算子を定義する方法

自分で定義したクラスを `list.sort()` や `sorted()` でソートできるようにするには、 __`__lt__`__ メソッドを実装します（Less Than (`<`) の略です）。
次の `Person` クラスは、ソート時に `age` プロパティの値で大小比較するように実装しています。

```python
class Person:
    def __init__(self, name, age):
        self.name = name
        self.age = age

    def __str__(self):
        return '%s(%d)' % (self.name, self.age)

    def __lt__(self, other):
        return self.age < other.age


if __name__ == '__main__':
    p1 = Person('maku', 14)
    p2 = Person('chiko', 5)
    p3 = Person('ponyo', 8)

    persons = [p1, p2, p3]
    persons.sort()  # __lt__ の実装に従ってソート

    for p in persons:
        print(p)
```

#### 実行結果

```
chiko(5)
ponyo(8)
maku(14)
```

`age` プロパティの値が等しかったら `name` プロパティの値で比較する、という実装は次のようになります（Python では文字列の大小比較も `<` で行えます）。

```python
def __lt__(self, other):
    if self.age == other.age:
        return self.name < other.name
    return self.age < other.age
```

ソート実行時に `reverse` オプションを指定すれば、降順ソートに切り替えられます。

```python
persons.sort(reverse=True)
```

定義済みの `Person` クラスに対して、後付けで比較メソッドを追加することもできます。

```python
Person.__lt__ = lambda self, other: self.age < other.age
```


### key パラメータを指定する方法

`__lt__` メソッドを実装していないクラスのオブジェクトであっても、ソート時に __`key`__ パラメータを指定すればソートが可能です。
次の例では、`Person` オブジェクトのリストを、`age` プロパティの値で昇順ソートするように指定しています。

```python
persons.sort(key=lambda x: x.age)
```

このようにプロパティの値をそのまま比較に使用する場合は、`operator` モジュールの __`attergetter`__ を使用すると、より簡潔に記述できるようになります。

```python
from operator import attrgetter

persons.sort(key=attrgetter('age'))
```

さらに次のようにすれば、`age` プロパティの値が等しい場合は、`name` プロパティの値で比較する、という指定が可能です。

```python
persons.sort(key=attrgetter('age', 'name'))
```

