---
title: "TeX メモ"
date: "2017-03-24"
---

記号
----

| TeX | 表示 | 説明 |
| ---- | ---- | ---- |
| `{}^t\!A` | $${}^t\!A$$ | 転置行列 |
| `A^{\mathrm{T}}` | $$A^{\mathrm{T}}$$ | 転置行列 |


行列
----

$$
  A = \begin{bmatrix}
    1 & 2 \\
    3 & 4 \\
    5 & 6
  \end{bmatrix}
$$

~~~ tex
A = \begin{bmatrix}
  1 & 2 \\
  3 & 4 \\
  5 & 6
\end{bmatrix}
~~~

あるいは、

~~~ tex
A = \left[
  \begin{array}{cc}
    1 & 2 \\
    3 & 4 \\
    5 & 6
  \end{array}
\right]
~~~

行列の各要素を右寄せ
----

$$
  A = \left[
    \begin{array}{rrr}
      134 & 30 & 7 \\
      -44 & -2 & 1000 \\
      3 & 9 & -7
    \end{array}
  \right]
$$

~~~ tex
a = \left[
  \begin{array}{rrr}
    134 & 30 & 7 \\
    -44 & -2 & 1000 \\
    3 & 9 & -7
  \end{array}
\right]
~~~


括弧なしの行列
----

$$
\begin{matrix} a & b \\ c & d \end{matrix}
$$

~~~ tex
\begin{matrix}
  a & b \\
  c & d
\end{matrix}
~~~

ドットで要素を省略
----

$$
  A = \begin{pmatrix}
    a_{11} & a_{12} & \ldots & a_{1n} \\
    a_{21} & a_{22} & \ldots & a_{2n} \\
    \vdots & \vdots & \ddots & \vdots \\
    a_{m1} & a_{m2} & \ldots & a_{mn}
  \end{pmatrix}
$$

~~~ tex
A = \begin{pmatrix}
  a_{11} & a_{12} & \ldots & a_{1n} \\
  a_{21} & a_{22} & \ldots & a_{2n} \\
  \vdots & \vdots & \ddots & \vdots \\
  a_{m1} & a_{m2} & \ldots & a_{mn}
\end{pmatrix}
~~~

逆行列
----

$$
  \begin{bmatrix}
    1 & 2 \\
    3 & 4
  \end{bmatrix}^{-1} =
  \begin{bmatrix}
    -2 & 1 \\
    1.5 & -0.5
  \end{bmatrix}
$$

~~~ tex
\begin{bmatrix}
  1 & 2 \\
  3 & 4
\end{bmatrix}^{-1} =
\begin{bmatrix}
  -2 & 1 \\
  1.5 & -0.5
\end{bmatrix}
~~~

行列式
----

$$
  \mathrm{det}A = |A| = \left|
    \begin{array}{ccc}
      a_{11} & a_{12} & a_{13} \\
      a_{21} & a_{22} & a_{23} \\
      a_{31} & a_{32} & a_{33}
    \end{array}
  \right|
$$

~~~ tex
\mathrm{det}A = |A| = \left|
  \begin{array}{ccc}
    a_{11} & a_{12} & a_{13} \\
    a_{21} & a_{22} & a_{23} \\
    a_{31} & a_{32} & a_{33}
  \end{array}
\right|
~~~


場合分け
----

$$
  x^n = \left\{ \begin{array}{ll}
    1 & (n=0) \\
    x \cdot x^{n-1} & (otherwise)
  \end{array} \right.
$$

~~~ tex
x^n = \left\{ \begin{array}{ll}
  1 & (n=0) \\
  x \cdot x^{n-1} & (otherwise)
\end{array} \right.
~~~


その他
----

$$
  \begin{align*}
  \frac{\partial \theta}{\partial t}= \frac{\partial}{\partial z}
  \left[ K(\theta) \left (\frac{\partial \psi}{\partial z} + 1 \right) \right]\
  \end{align*}
$$


