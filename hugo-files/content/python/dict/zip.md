---
title: "Python でキーのリストと値のリストを組み合わせて dictionary を生成する (zip)"
url: "p/cmy6ar3/"
date: "2005-10-20"
tags: ["Python"]
aliases: /python/dictionary/zip.html
---

Python の __`zip`__ 関数と __`dict`__ 関数を組み合わせることで、「キーのリスト」と「値のリスト」から、新しい dictionary オブジェクトを生成することができます。

```python
>>> keys = ['one', 'two', 'three']
>>> values = [1, 2, 3]
>>> d = dict(zip(keys, values))
>>> d
{'one': 1, 'two': 2, 'three': 3}
```

2 つのリストの要素数が異なる場合は、少ない方に合わせて生成されます。

```python
>>> keys = ['one', 'two', 'three']
>>> values = [1, 2]
>>> d = dict(zip(keys, values))
>>> d
{'one': 1, 'two': 2}
```

