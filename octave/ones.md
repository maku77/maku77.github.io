---
title: すべての要素が１の行列を作成する (ones)
created: 2017-03-24
---

組み込み関数 `ones` を使用して、すべての要素が１である行列を作成することができます。

~~~ matlab
>> ones(3)
ans =

   1   1   1
   1   1   1
   1   1   1

>> ones(2, 4)
ans =

   1   1   1   1
   1   1   1   1
~~~

スカラー値を掛けることで、１以外の任意の値にすることができます。

~~~ matlab
>> ones(2, 4) * 7
ans =

   7   7   7   7
   7   7   7   7
~~~


ones 関数のドキュメント
----

~~~
>> help ones
'ones' is a built-in function from the file libinterp/corefcn/data.cc

 -- ones (N)
 -- ones (M, N)
 -- ones (M, N, K, ...)
 -- ones ([M N ...])
 -- ones (..., CLASS)
     Return a matrix or N-dimensional array whose elements are all 1.

     If invoked with a single scalar integer argument N, return a square
     NxN matrix.

     If invoked with two or more scalar integer arguments, or a vector
     of integer values, return an array with the given dimensions.

     To create a constant matrix whose values are all the same use an
     expression such as

          val_matrix = val * ones (m, n)

     The optional argument CLASS specifies the class of the return array
     and defaults to double.  For example:

          val = ones (m,n, "uint8")
~~~

