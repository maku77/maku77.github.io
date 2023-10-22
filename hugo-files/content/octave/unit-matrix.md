---
title: "Octave で単位行列を作成する (eye)"
url: "p/h24njtn/"
date: "2017-03-24"
tags: ["Octave"]
useMath: true
aliases: /octave/unit-matrix.html
---

eye 関数
----

Octave で任意のサイズの __単位行列 \\(I\\)__ を作成するには、組み込み関数の __`eye`__ を使用します。

$$
  I = \begin{pmatrix}
    1 & 0 & 0 & \ldots & 0 \\\\
    0 & 1 & 0 & \ldots & 0 \\\\
    0 & 0 & 1 & \ldots & 0 \\\\
      & \vdots &  & \ddots & \vdots \\\\
    0 & 0 & 0 & \ldots & 1
  \end{pmatrix}
$$

```matlab
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
```

