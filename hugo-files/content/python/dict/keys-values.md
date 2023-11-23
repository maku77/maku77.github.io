---
title: "Python で dictionary からキーや値のリストを作成する (keys, values, items)"
url: "p/83e3wrw/"
date: "2005-10-20"
lastmod: "2023-11-23"
changes:
  - 2023-11-23: サンプルコードを追加
tags: ["python"]
aliases: /python/dictionary/keys-values.html
---

Python の dictionary オブジェクトから、「キーのリスト」、「値のリスト」、「キーと値をペアにしたリスト」を取り出すには、それぞれ下記のようなメソッドを使用します。

- __`d.keys()`__ ... キーのリスト（戻り値は `dict_keys` オブジェクト）
- __`d.values()`__ ... 値のリスト（戻り値は `dict_values` オブジェクト）
- __`d.items()`__ ... (キー, 値) というタプルのリスト（戻り値は `dict_items` オブジェクト）

```python
>>> d = {'one':1, 'two':2, 'three':3}

>>> d.keys()
dict_keys(['one', 'two', 'three'])

>>> d.values()
dict_values([1, 2, 3])

>>> d.items()
dict_items([('one', 1), ('two', 2), ('three', 3)])
[('three', 3), ('two', 2), ('one', 1)]
```

それぞれの戻り値は `dict_*` という型のオブジェクトになっており、元のディクショナリ内のデータを参照するためのビューとして働きます。
このビューを介してディクショナリの内容を変更することはできませんが、ディクショナリ側の変更はビューに反映されます。
それぞれのオブジェクトは `for` ループでイテレートできます。

```python
>>> for k in d.keys():
...     print(k)
...
one
two
three

>>> for k, v in d.items():
...     print("{} -> {}".format(k, v))
...
one -> 1
two -> 2
three -> 3
```

