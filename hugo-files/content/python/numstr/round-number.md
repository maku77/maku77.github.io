---
title: "Pythonメモ: 小数点以下を四捨五入する/切り捨てる/切り上げる (round, math.floor, math.ceil)"
url: "p/73g4beh/"
date: "2009-11-18"
tags: ["python"]
aliases: ["/python/numstr/round-number.html"]
---

## 小数点以下を四捨五入する

```python
>>> round(1.5)
2

>>> round(1.4)
1
```

## 小数点以下を切り捨てる

```python
>>> int(1.9)
1

>>> import math
>>> math.floor(1.9)
1
```

## 小数点以下を切り上げる

```python
>>> math.ceil(1.3)
2
```
