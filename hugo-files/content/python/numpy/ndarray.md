---
title: "Python の NumPy 配列 (ndarray) で行列を扱う"
url: "p/jwfxfvd/"
date: "2017-01-01"
lastmod: "2023-05-28"
tags: ["Python", "NumPy"]
changes:
  - 2023-05-28: 詳細な説明を追加
aliases: /python/numpy/ndarray.html
---

NumPy 配列を生成する（numpy.array 関数）
----

NumPy には配列（行列）を高速に扱うための __`ndarray`__ クラスが用意されています。
`ndarray` インスタンスは、__`numpy.array`__ 関数で生成することができます（よく `np` という別名でインポートするので、__`np.array`__ と記述されていることが多いです）。
下記の例では、2 行 3 列の行列を表現する `ndarray` インスタンスを生成しています。

{{< code lang="python" title="2x3 の ndarray を生成" >}}
>>> import numpy as np
>>> a = np.array([[1, 2, 3], [4, 5, 6]])

>>> a
array([[1, 2, 3],
       [4, 5, 6]])

>>> type(a)
<type 'numpy.ndarray'>
{{< /code >}}

NumPy 配列の形状（各次元のサイズ）は、__`shape`__ 属性を参照することで調べることができます。

{{< code lang="python" title="ndarray の形状を調べる" >}}
>>> a.shape
(2, 3)  # 2 行 3 列ということ
{{< /code >}}

行列として扱うには、各行のサイズ（列数）は揃えておく必要があります。
次のように、異なるサイズで初期化してしまうと、リストオブジェクトを要素に持つ一次元の配列として扱われてしまいます。

```python
>>> a = np.array([[1, 2, 3], [4, 5]])
>>> a
array([[1, 2, 3], [4, 5]], dtype=object)
>>> a.shape
(2,)   # 1 次元で要素数 2 つということ
```


NumPy 配列は 1 つのデータタイプの要素のみを持つ
----

効率化のため、NumPy 配列に格納される要素の型（データタイプ）は統一されます。
データタイプは `ndarray` インスタンスの初期化時の要素の値によって自動的に設定されます。
`ndarray` インスタンスが保持している要素のデータタイプを調べるには、__`dtype`__ 属性を参照します。

{{< code lang="python" title="ndarray のデータタイプを調べる" >}}
>>> a = np.array([[1, 2, 3], [4, 5, 6]])  # 整数値のみで初期化
>>> a.dtype
dtype('int64')

>>> a = np.array([[1, 2, 3], [1.5, 2.5, 3.5]])  # 浮動小数点数を含む値で初期化
>>> a.dtype
dtype('float64')
{{< /code >}}

データタイプは、明示的に指定することもできます。
次の例では、整数値のみで NumPy 配列を初期化していますが、浮動小数点数の要素として扱うように指定しています。

{{< code lang="python" title="ndarray のデータタイプを指定する" >}}
>>> a = np.array([[1, 2, 3], [4, 5, 6]], dtype='float64')
>>> a.dtype
dtype('float64')
>>> a
array([[ 1.,  2.,  3.],
       [ 4.,  5.,  6.]])
{{< /code >}}


零行列や単位行列を生成する (numpy.zeros, numpy.identity)
----

### 零行列 (numpy.zeros)

すべての要素（成分）が 0 である行列のことを零行列（ゼロ行列; zero matrix）と呼びます。
NumPy で零行列を生成するには、`np.array` 関数の代わりに、__`np.zeros`__ 関数を使用します。

{{< code lang="python" title="零行列を生成する" >}}
>>> np.zeros((2, 3))
array([[ 0.,  0.,  0.],
       [ 0.,  0.,  0.]])
{{< /code >}}

### 単位行列 (numpy.identity)

単位行列（identity matrix）は、対角線上の要素（対角成分）が全て 1 で、それ以外の要素が全て 0 の正方行列です。
単位行列は、行列の乗法の単位元としての役割を果たします。
__`numpy.identity`__ 関数で単位行列の `ndarray` インスタンスを生成することができます。

{{< code lang="python" title="単位行列を生成する" >}}
>>> np.identity(3)
array([[ 1.,  0.,  0.],
       [ 0.,  1.,  0.],
       [ 0.,  0.,  1.]])
{{< /code >}}

### 全要素が 1 の行列 (numpy.ones)

全ての要素が 1 である行列を生成する __`numpy.ones`__ 関数も用意されています。

{{< code lang="python" title="全ての要素が 1 の行列を生成する" >}}
>>> np.ones((2, 3))
array([[ 1.,  1.,  1.],
       [ 1.,  1.,  1.]])
{{< /code >}}


NumPy 配列に対する演算（ブロードキャスト）
----

NumPy 配列 (ndarray) に対して四則演算を行うと、__全ての要素に対してその演算を適用した結果__ が NumPy 配列として返されます（この仕組みを __ブロードキャスト__ と呼びます）。
これは非常に強力な仕組みで、この仕組みをうまく活用することで、行列演算を行う関数などを、ループ処理を使わずに実装することができます。

{{< code lang="python" title="ndarray のブロードキャスト演算" >}}
>>> a = np.array([[1, 2, 3], [4, 5, 6]])

>>> a + 1
array([[2, 3, 4],
       [5, 6, 7]])

>>> a - 1
array([[0, 1, 2],
       [3, 4, 5]])

>>> a * 2
array([[ 2,  4,  6],
       [ 8, 10, 12]])

>>> a / 2
array([[ 0.5,  1. ,  1.5],
       [ 2. ,  2.5,  3. ]])  # Python2 ではデータタイプは int64 になる

>>> a ** 2
array([[ 1,  4,  9],
       [16, 25, 36]])
{{< /code >}}

NumPy 配列同士の四則演算も、それぞれ対応する要素に対して演算が行われます。

{{< code lang="python" title="2 つの ndarray の四則演算" >}}
>>> a = np.array([[1, 2, 3], [4, 5, 6]])

>>> a + a
array([[ 2,  4,  6],
       [ 8, 10, 12]])

>>> a - a
array([[0, 0, 0],
       [0, 0, 0]])

>>> a * a
array([[ 1,  4,  9],
       [16, 25, 36]])

>>> a / a
array([[ 1.,  1.,  1.],
       [ 1.,  1.,  1.]])

>>> a ** a
array([[    1,     4,    27],
       [  256,  3125, 46656]])
{{< /code >}}

形状の異なる NumPy 配列同士で演算しようとすると、__`ValueError`__ が発生することに注意してください。

```python
>>> a = np.array([[1, 2, 3], [4, 5, 6]])
>>> b = np.array([[1, 2], [3, 4]])

>>> a + b
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
ValueError: operands could not be broadcast together with shapes (2,3) (2,2)
```

ただし、1 次元の NumPy 配列と演算処理を行うと、ブロードキャストの仕組みがうまく働いて演算処理が成功します（単独の数値との演算がうまくいくのと同じ仕組みです）。

```python
>>> a = np.array([[1, 2, 3], [4, 5, 6], [7, 8, 9]])
>>> b = np.array([1, 2, 3])

>>> a + b
array([[ 2,  4,  6],
       [ 5,  7,  9],
       [ 8, 10, 12]])

>>> a * b
array([[ 1,  4,  9],
       [ 4, 10, 18],
       [ 7, 16, 27]])
```


行列演算を行う（内積、外積、転置）
----

行列の内積、外積、転置などの演算を行うために、次のような専用メソッドが用意されています。

### 内積 (dot)

```python
>>> a = np.array([[1, 2, 3], [4, 5, 6]])
>>> b = np.array([1, 2, 3])

>>> a.dot(b)
array([14, 32])
```

### 外積 (cross)

```python
>>> a = (1, 2, 3)
>>> b = (10, 10, 10)

>>> np.cross(a, b)
array([-10,  20, -10])
```

### 転置 (transpose)

```python
>>> a = np.array([[1, 2, 3], [4, 5, 6]])

>>> a.transpose()
array([[1, 4],
       [2, 5],
       [3, 6]])
```

