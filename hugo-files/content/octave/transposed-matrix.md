---
title: "Octave で転置行列を作成する"
url: "p/a7z6ojh/"
date: "2017-03-23"
tags: ["Octave"]
useMath: true
aliases: /octave/transposed-matrix.html
---

転置行列を求める
----

$$
  {A} = \left[
    \begin{array}{ccc}
      1 & 2 & 3 \\\\
      4 & 5 & 6
    \end{array}
  \right]
$$

$$
  {A}^{\mathrm{T}} = \left[
    \begin{array}{cc}
      1 & 4 \\\\
      2 & 5 \\\\
      3 & 6
    \end{array}
  \right]
$$

__行列 `A` の転置行列 (transposed matrix)__ である __\\(A^T\\)__ は、ダッシュ記号 (__`'`__) を付加するだけで求められます。

```matlab
>> A = [1 2 3; 4 5 6]
A =

   1   2   3
   4   5   6

>> A'
ans =

   1   4
   2   5
   3   6
```


転置行列と共役転置行列
----

`A'` で得られる行列は、正確には __共役転置行列__ であり、転置行列の各要素の複素共役をとったものになっています。
各要素の値が実数の場合は違いはありませんが、複素数が含まれていると結果が変わってきます。

```matlab
>> A = [1+2i 3+4i; 5-6i 7-8i]
A =

   1 + 2i   3 + 4i
   5 - 6i   7 - 8i

>> A'
ans =

   1 - 2i   5 + 6i
   3 - 4i   7 + 8i
```

単純な転置行列を求めたい場合は、`A'` ではなく、__`A.'`__ とします。

```matlab
>> A.'
ans =

   1 + 2i   5 - 6i
   3 + 4i   7 - 8i
```

