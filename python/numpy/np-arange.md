---
title: 連番からなる NumPy 配列を作成する (np.arange)
date: "2017-02-27"
---

`numpy.arange` 関数を使用して、連番からなる NumPy 配列を生成することができます。
開始番号 (`start`) や、1 ステップあたりの変化数値 (`step`) を指定することもできます。

~~~ python
>>> import numpy as np
>>> np.arange(5)
array([0, 1, 2, 3, 4])
>>> np.arange(3, 10)
array([3, 4, 5, 6, 7, 8, 9])
>>> np.arange(2, 10, 2)
array([2, 4, 6, 8])
>>> np.arange(10, 0, -1)
array([10,  9,  8,  7,  6,  5,  4,  3,  2,  1])
~~~

関数名は `arrange` ではなく、`arange` であることに注意してください。

