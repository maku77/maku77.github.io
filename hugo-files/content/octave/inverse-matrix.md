---
title: "Octave で逆行列を作成する (inv)"
url: "p/fkau9he/"
date: "2017-03-24"
tags: ["Octave"]
useMath: true
aliases: /octave/inverse-matrix.html
---

$$
  \begin{bmatrix}
    1 & 2 \\\\
    3 & 4
  \end{bmatrix}^{-1} =
  \begin{bmatrix}
    -2 & 1 \\\\
    1.5 & -0.5
  \end{bmatrix}
$$

Octave で行列 \\(A\\) の逆行列 \\(A^{-1}\\) を求めるには、組み込み関数の __`inv`__ を使用します。

```matlab
>> A = [1 2; 3 4]
A =

   1   2
   3   4

>> inv(A)
ans =

  -2.00000   1.00000
   1.50000  -0.50000
```

元の行列と逆行列の積は、単位行列になります（\\(AA^{-1} = A^{-1}A = I\\)）。

```matlab
>> A * inv(A)
ans =

   1.00000   0.00000
   0.00000   1.00000

>> inv(A) * A
ans =

   1.00000   0.00000
   0.00000   1.00000
```

