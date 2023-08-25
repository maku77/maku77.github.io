---
title: "NumPy 配列 (ndarray) の作成方法まとめ"
url: "p/htducs9/"
date: "2017-02-27"
lastmod: "2023-08-23"
tags: ["Python", "NumPy"]
changes:
  - 2023-08-23: arange 関数以外の ndarray 生成関数をまとめ
  - 2023-05-28: 略称に関する説明を追加
aliases: /python/numpy/np-arange.html
---

NumPy 配列 (`ndarray`) は、`np.array()` 関数を使って次のように生成するのが基本ですが、

```python
import numpy as np
a = np.array([[1, 2, 3], [4, 5, 6]])
```

NumPy は `ndarray` インスタンス生成用の様々なユーティリティ関数を提供しています。
以下、`numpy` 部分は `np` というエイリアス名で記述します。


全要素が 0 の NumPy 配列 (np.zeros)
----

### 零ベクトル

__`np.zeros()`__ 関数で、すべての要素が 0 の配列データ（ベクトル）を作成できます。
データタイプ (`dtype`) はデフォルトで `float64` になりますが、__`dtype`__ パラメーターで変更することが可能です。

```python
>>> np.zeros(3)
array([0., 0., 0.])

>>> np.zeros(3, dtype="int64")
array([0, 0, 0], dtype=int64)
```

### 零行列

すべての要素（成分）が 0 である行列のことを零行列（ゼロ行列; zero matrix）と呼びます。
NumPy で零行列を生成するには、__`np.zeros`__ 関数の第 1 引数 (__`shape`__) にタプル形式で `(行サイズ, 列サイズ)` を渡します。

```python
>>> np.zeros((2, 3))
array([[ 0.,  0.,  0.],
       [ 0.,  0.,  0.]])
```

データタイプ (`dtype`) はデフォルトで `float64` になりますが、次のように明示的に指定することが可能です。

```python
a = np.zeros((2, 3), dtype="int32")
```

全要素が 1 の NumPy 配列 (np.ones)
----

全ての要素が 1 である NumPy 配列を生成するには __`np.ones`__ 関数を使用します。
第 1 引数 (`shape`) で要素数を指定します。
多次元配列の場合は、タプル形式で各次元の要素数を指定します。
データタイプ (`dtype`) はデフォルトで `float64` になります。

```python
>>> np.ones(3)
array([1., 1., 1.])

>>> np.ones((2, 3))
array([[ 1.,  1.,  1.],
       [ 1.,  1.,  1.]])

>>> np.ones((2, 3), dtype="int32")
array([[1, 1, 1],
       [1, 1, 1]])
```


連番からなる NumPy 配列 (np.arange)
----

__`np.arange`__ 関数を使用して、連番からなる NumPy 配列 (`ndarray`) を生成することができます。
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

>>> np.arange(1, step=0.1)
array([0. , 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9])
{{< /code >}}

データタイプ (`dtype`) は基本的に `int32` になりますが、ステップ数を小数点数で刻むと `float64` になります。
関数名は `arrange` ではなく、`arange` であることに注意してください（array range の略です）。

`np.arange` は 1 次元配列を作成しますが、__`reshape`__ メソッドを使えば多次元配列の形に変換できます。

```python
>>> np.arange(9).reshape((3, 3))
array([[0, 1, 2],
       [3, 4, 5],
       [6, 7, 8]])
```


ランダムな NumPy 配列 (np.random)
----

__`np.random.rand()`__ は 0 以上 1 未満のランダム `float` 値を生成する関数ですが、引数で各次元のサイズを指定することで、ランダムな値を持つ NumPy 配列を生成できます。
似た関数に `np.random.randn()` がありますが、こちらは標準正規分布に従ったランダム値を生成します。

```python
>>> np.random.rand()  # スカラー値
0.5926286463704019

>>> np.random.rand(3)  # 1 次元 NumPy 配列
array([0.88829851, 0.22662687, 0.30925015])

>>> np.random.rand(2, 3)  # 2 次元 NumPy 配列　
array([[0.05874757, 0.31040971, 0.09503772],
       [0.99130323, 0.34005123, 0.57641306]])
```

整数のランダム値が欲しいときは、__`np.random.randint()`__ を使います。
__`size`__ パラメーターを指定することで、任意の次元の NumPy 配列を生成できます。

```python
>>> np.random.randint(10)  # スカラー値（0 以上 10 未満の整数）
7

>>> np.random.randint(-3, 3)  # スカラー値（-3 以上 3 未満の整数）
-3

>>> np.random.randint(5, size=3)  # 1 次元 NumPy 配列（0 以上 5 未満の整数）
array([2, 0, 4])

>>> np.random.randint(5, size=(2, 3))  # 2 次元 NumPy 配列（0 以上 5 未満の整数）
array([[0, 4, 3],
       [2, 4, 4]])
```

ランダム関数のシード値は、__`np.random.seed()`__ で設定することができます。
同じシード値を設定すると、生成されるランダム値を再現することができます。

```python
>>> np.random.seed(252525)  # シード値を設定
>>> np.random.randint(100)
43
>>> np.random.randint(100)
75

>>> np.random.seed(252525)  # 同じシード値を再設定
>>> np.random.randint(100)
43
>>> np.random.randint(100)
75
````


単位行列 (np.identity)
----

単位行列（identity matrix）は、対角線上の要素（対角成分）が全て 1 で、それ以外の要素が全て 0 の正方行列です。
単位行列は、行列の乗法の単位元としての役割を果たします。
__`np.identity`__ 関数で単位行列の `ndarray` インスタンスを生成することができます。
行と列のサイズは等しいため、サイズ指定のためのパラメーターは 1 つだけであることに注意してください。

```python
>>> np.identity(3)
array([[ 1.,  0.,  0.],
       [ 0.,  1.,  0.],
       [ 0.,  0.,  1.]])
```

`np.identity()` 関数の戻り値は、必ず 2 次元の NumPy 配列になります（`shape` としては `(1, 1)` や `(2, 2)` や `(3, 3)` のようになります）。

