---
title: "単位行列を作成する (eye)"
date: "2017-03-24"
---

$$
  I = \begin{pmatrix}
    1 & 0 & 0 & \ldots & 0 \\
    0 & 1 & 0 & \ldots & 0 \\
    0 & 0 & 1 & \ldots & 0 \\
      & \vdots &  & \ddots & \vdots \\
    0 & 0 & 0 & \ldots & 1
  \end{pmatrix}
$$

組み込み関数 `eye` を使用して、任意のサイズの単位行列 $$I$$ を生成することができます。

~~~ matlab
>> eye(3)
ans =

Diagonal Matrix

   1   0   0
   0   1   0
   0   0   1

>> eye(5)
ans =

Diagonal Matrix

   1   0   0   0   0
   0   1   0   0   0
   0   0   1   0   0
   0   0   0   1   0
   0   0   0   0   1
~~~

