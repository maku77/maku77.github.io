---
title: 零行列を作成する
date: "2017-03-24"
---

$$
  O = \begin{pmatrix}
    0 & 0 & \ldots & 0 \\
    0 & 0 & \ldots & 0 \\
    \vdots & \vdots & \ddots & \vdots \\
    0 & 0 & \ldots & 0
  \end{pmatrix}
$$

ゼロ行列 (zero vector) を作成するには、組み込みの `zeros` 関数を使用します。

~~~ matlab
>> O = zeros(3)
O =

   0   0   0
   0   0   0
   0   0   0

>> O = zeros(2, 4)
O =

   0   0   0   0
   0   0   0   0
~~~

zeros のドキュメント
----

~~~ matlab
>> help zeros
'zeros' is a built-in function from the file libinterp/corefcn/data.cc

 -- zeros (N)
 -- zeros (M, N)
 -- zeros (M, N, K, ...)
 -- zeros ([M N ...])
 -- zeros (..., CLASS)
     Return a matrix or N-dimensional array whose elements are all 0.

     If invoked with a single scalar integer argument, return a square
     NxN matrix.

     If invoked with two or more scalar integer arguments, or a vector
     of integer values, return an array with the given dimensions.

     The optional argument CLASS specifies the class of the return array
     and defaults to double.  For example:

          val = zeros (m,n, "uint8")
~~~

