---
title: "キーのリストと値のリストから dictionary を生成する (zip)"
date: "2005-10-20"
---

`zip` 関数と `dict` 関数を組み合わせることで、キーのリストと値のリストから dictionary オブジェクトを生成することができます。

```python
>>> keys = ['one', 'two', 'three']
>>> vals = [1, 2, 3]
>>> d = dict(zip(keys, vals))
>>> d
{'two': 2, 'three': 3, 'one': 1}
```

２つのリストの要素数が異なる場合は、少ない方に合わせて生成されます。

