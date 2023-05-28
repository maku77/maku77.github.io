---
title: "Python で連番からなる NumPy 配列を作成する (numpy.arange)"
date: "2017-02-27"
lastmod: "2023-05-28"
url: "p/htducs9/"
tags: ["Python", "NumPy"]
changes:
  - 2023-05-28: 略称に関する説明を追加
aliases: /python/numpy/np-arange.html
---

__`numpy.arange`__ 関数を使用して、連番からなる NumPy 配列 (`ndarray`) を生成することができます。
開始番号 (`start`) や、1 ステップあたりの変化数 (`step`) を指定することもできます。

{{< code lang="python" title="連番の ndarray を生成する" >}}
>>> import numpy as np

>>> np.arange(5)
array([0, 1, 2, 3, 4])

>>> np.arange(3, 10)
array([3, 4, 5, 6, 7, 8, 9])

>>> np.arange(2, 10, 2)
array([2, 4, 6, 8])

>>> np.arange(10, 0, -1)
array([10,  9,  8,  7,  6,  5,  4,  3,  2,  1])
{{< /code >}}

関数名は `arrange` ではなく、`arange` であることに注意してください（array range の略です）。

