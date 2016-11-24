---
title: dictionary からキー、値のリストを作成する (keys, values, items)
created: 2005-10-20
---

dictionary から、キーだけのリスト、値だけのリスト、両者をペアにしたリストを取り出すには、それぞれ下記のようなメソッドを使用します。

* `d.keys()` -- キーのリスト
* `d.values()` -- 値のリスト
* `d.items()` -- (キー, 値) というタプルのリスト

```python
>>> d = {'one':1, 'two':2, 'three':3}
>>> d.keys()
['three', 'two', 'one']

>>> d.values()
[3, 2, 1]

>>> d.items()
[('three', 3), ('two', 2), ('one', 1)]
```

