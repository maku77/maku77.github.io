---
title: "オブジェクトを print で出力できるようにする"
date: "2009-06-06"
---

クラス定義に `__str__` メソッドを追加しておくと、`print()` や `str()` にそのオブジェクトを渡したときの出力をカスタマイズすることができます。

#### sample.py
```python
class MyClass:
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def __str__(self):
        return 'x = ' + str(self.x) + ', y = ' + str(self.y)

if __name__ == '__main__':
    obj = MyClass(100, 200)
    print(obj)
```

#### 実行結果
```
$ python sample.py
x = 100, y = 200
```

