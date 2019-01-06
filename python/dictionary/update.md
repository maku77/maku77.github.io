---
title: "2 つの dictionary をマージする (update)"
date: "2005-10-20"
---

dictionary  オブジェクト `d1` に、`d2` の要素をマージするには下記のようにします。

```python
d1.update(d2)
```

`update` のパラメータとして渡した `d2` の内容は変化しません。

```python
>>> d1 = {'one':1, 'two':2}
>>> d2 = {'three':3, 'four':4}
>>> d1.update(d2)
>>> d1
{'two': 2, 'four': 4, 'three': 3, 'one': 1}
>>> d2
{'three': 3, 'four': 4}
```

