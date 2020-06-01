---
title: "オブジェクトを print で出力できるようにする (__str__)"
date: "2009-06-06"
lastmod: "2020-06-01"
---

クラス定義に `__str__` メソッドを追加しておくと、`print()` や `str()` にそのオブジェクトを渡したときの出力をカスタマイズすることができます。

#### sample.py

```python
class Person:
    def __init__(self, name, age):
        self.name = name
        self.age = age

    def __str__(self):
        return '%s(%d)' % (self.name, self.age)

if __name__ == '__main__':
    p = Person('maku', 14)
    print(p)
```

#### 実行結果

```
maku(14)
```

