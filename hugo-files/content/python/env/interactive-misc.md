---
title: "Python の対話型シェルで使える小技"
url: "p/dsbsaq7/"
date: "2007-03-07"
tags: ["Python"]
aliases: /python/column/interactive.html
---

Python のインタラクティブシェルで __`_`__（アンダースコア）を使用すると、 前回評価した値を参照することができます。

```python
>>> [1, 2, 3]
[1, 2, 3]

>>> len(_)
3
```

