---
title: "Octave で対角行列を作成する (diag)"
url: "p/8tddagn/"
date: "2017-03-27"
tags: ["Octave"]
useMath: true
aliases: /octave/diagonal-matrix.html
---

$$
  diag(c_1, c_2, ..., c_n) = \begin{pmatrix}
    c_1 &     &        & 0 \\\\
        & c_2 &        &   \\\\
        &     & \ddots &   \\\\
     0  &     &        & c_n
  \end{pmatrix}
$$

正方行列のうち、対角成分 (diagonal elements) 以外がすべて零のものを __対角行列 (diagonal matrix)__ といいます。
Octave の組み込み関数 __`diag`__ を使用すると、対角行列からの対角成分の抽出や、対角成分からの対格行列の生成を行うことができます。


対角行列から対角成分を抽出する
----

```matlab
>> A = [3 0 0; 0 -1 0; 0 0 2]
A =

   3   0   0
   0  -1   0
   0   0   2

>> v = diag(A)
v =

   3
  -1
   2
```

抽出された対角成分は、列ベクトルとして返されます。


対角成分から対角行列を生成する
----

```matlab
>> A = diag([3 -1 2])
A =

Diagonal Matrix

   3   0   0
   0  -1   0
   0   0   2
```

指定する対角成分は、行ベクトルでも縦ベクトルでも構いません（上記では行ベクトルを渡しています）。

