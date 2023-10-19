---
title: "scikit-learn で特徴量の標準化を行う"
url: "p/btov27k/"
date: "2023-10-19"
tags: ["scikit-learn"]
draft: true
---

<script>
MathJax = {
  tex: {
    inlineMath: [['$', '$'], ['\\(', '\\)']]
  }
};
</script>
<script id="MathJax-script" async
  src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js">
</script>

標準化とは
----

標準化 (Standardization) は、__平均値が 0、分散が 1__ になるようにデータの単位やスケールを揃える変換処理です。
多くの機械学習アルゴリズムでは、前処理としてデータの標準化が必要です。
次のように偏差（平均との差）を標準偏差 $\sigma$ で割ることで変換できます。

$$z_{i} = \frac{x_{i} - \bar{x}}{\sigma}$$

<div style="text-align:right;">
$z_{i}$：標準化後の値、$x_{i}$：標準化前の値、$\bar{x}$：平均値、$\sigma$：標準偏差
</div>

ちなみに、分散が 1 であることを __単位分散__ と呼びます。
分散が 1 である場合、その平方根である標準偏差も 1 になります。

scikit-learn で標準化
----

scikit-learn が提供する __`preprocessing.scale()`__ 関数を使うと、任意の配列データ（`np.array` など）を簡単に標準化することができます。

{{< code lang="python" title="main.py" >}}
import numpy as np
from sklearn import preprocessing

X = np.array([1., -1., 2.])
print("=== 標準化前 ===")
print("データ:", X)
print("平均:", X.mean())
print("標準偏差:", X.std())

X_scaled = preprocessing.scale(X)
print("\n=== 標準化後 ===")
print("データ:", X_scaled)
print("平均:", X_scaled.mean())
print("標準偏差:", X_scaled.std())
{{< /code >}}

{{< code title="実行結果" >}}
=== 標準化前 ===
データ: [ 1. -1.  2.]
平均: 0.6666666666666666
標準偏差: 1.247219128924647

=== 標準化後 ===
データ: [ 0.26726124 -1.33630621  1.06904497]
平均: 1.4802973661668753e-16
標準偏差: 1.0
{{< /code >}}

標準化後の平均値がほぼ 0 で、標準偏差が 1 になっていることが分かります（標準偏差が 1 であれば分散も 1 です）。

多次元配列（行列）であってもほぼ同様に処理できます。

```python
import numpy as np
from sklearn import preprocessing

X = np.array([
  [ 12.5, -12.7, 20.1, 33.0],
  [ -20.3, 45.0, -5.0, -7.7],
  [ -80.1, 25.3, 59.0, 67.1]
])

print("=== 標準化前 ===")
print(X)
print("平均:", X.mean())
print("標準偏差:", X.std())

X_scaled = preprocessing.scale(X)
print("\n=== 標準化後 ===")
print(X_scaled)
print("平均:", X_scaled.mean(axis=0))
print("標準偏差:", X_scaled.std(axis=0))
```

{{< code title="実行結果" >}}
=== 標準化前 ===
[[ 12.5 -12.7  20.1  33. ]
 [-20.3  45.   -5.   -7.7]
 [-80.1  25.3  59.   67.1]]
平均: 11.35
標準偏差: 38.54171981978317

=== 標準化後 ===
[[ 1.09036718 -1.33207605 -0.17470846  0.07195052]
 [ 0.23476805  1.07735304 -1.12800897 -1.25913401]
 [-1.32513523  0.25472301  1.30271743  1.1871835 ]]
平均: [0.00000000e+00 5.55111512e-17 0.00000000e+00 0.00000000e+00]
標準偏差: [1. 1. 1. 1.]
{{< /code >}}

上記の例では、平均と標準偏差を計算するときに `axis=0` パラメーターを指定することで、各特徴量ごとの平均が 0、標準偏差が 1 であることを確認しています。
`axis=0` パラメーターを外すと、すべての値の平均と標準偏差をスカラー値で取得できます。
その場合も、平均は 0、標準偏差は 1 になります。

