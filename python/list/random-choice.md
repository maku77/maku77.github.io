---
title: リスト／タプル内の要素をランダムで取得する (random.choice)
date: "2014-04-19"
---

`random.choice` を使用すると、リストやタプルの要素をランダムで 1 つ取り出すことができます。

```python
>>> import random
>>> t = (1, 2, 3)
>>> random.choice(t)
2
```

文字列も sequence の一種であるため、`random.choice` を適用することができます。

```python
>>> import random
>>> s = 'ABCDE'
>>> random.choice(s)
'B'
```

