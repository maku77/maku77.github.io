---
title: 小数点以下を四捨五入する／切り捨てる／切り上げる
created: 2009-11-18
---

小数点以下を四捨五入する
----

```python
>>> round(1.5)
2

>>> round(1.4)
1
```

小数点以下を切り捨てる
----

```python
>>> int(1.9)
1

>>> import math
>>> math.floor(1.9)
1
```

小数点以下を切り上げる
----

```python
>>> math.ceil(1.3)
2
```

