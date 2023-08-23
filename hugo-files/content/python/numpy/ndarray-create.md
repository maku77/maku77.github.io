---
title: "NumPy 配列 (ndarray) の作成方法まとめ"
date: "2017-02-27"
lastmod: "2023-08-23"
url: "p/htducs9/"
tags: ["Python", "NumPy"]
changes:
  - 2023-08-23: arange 関数以外の ndarray 生成関数をまとめ
  - 2023-05-28: 略称に関する説明を追加
aliases: /python/numpy/np-arange.html
---

NumPy 配列 (`ndarray`) は、`np.array()` 関数を使って次のように生成するのが基本ですが、

```python
a = np.array([[1, 2, 3], [4, 5, 6]])
```

NumPy は `ndarray` インスタンス生成用の様々なユーティリティ関数を提供しています。


零行列や単位行列を生成する (numpy.zeros, numpy.identity, numpy.ones)
----

### 零行列 (numpy.zeros)

すべての要素（成分）が 0 である行列のことを零行列（ゼロ行列; zero matrix）と呼びます。
NumPy で零行列を生成するには、`np.array` 関数の代わりに、__`np.zeros`__ 関数を使用します。

```python
>>> import numpy as np
>>> np.zeros((2, 3))
array([[ 0.,  0.,  0.],
       [ 0.,  0.,  0.]])
```

データタイプ (`dtype`) はデフォルトで `float64` になりますが、次のように明示的に指定することが可能です。

```python
a = np.zeros((2, 3), dtype="int32")
```

### 単位行列 (numpy.identity)

単位行列（identity matrix）は、対角線上の要素（対角成分）が全て 1 で、それ以外の要素が全て 0 の正方行列です。
単位行列は、行列の乗法の単位元としての役割を果たします。
__`numpy.identity`__ 関数で単位行列の `ndarray` インスタンスを生成することができます。
行と列のサイズは等しいため、サイズ指定のためのパラメーターは 1 つだけであることに注意してください。

```python
>>> np.identity(3)
array([[ 1.,  0.,  0.],
       [ 0.,  1.,  0.],
       [ 0.,  0.,  1.]])
```

### 全要素が 1 の行列 (numpy.ones)

全ての要素が 1 である行列を生成する __`numpy.ones`__ 関数も用意されています。

```python
>>> np.ones((2, 3))
array([[ 1.,  1.,  1.],
       [ 1.,  1.,  1.]])
```


連番からなる NumPy 配列を作成する (numpy.arange)
----

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

