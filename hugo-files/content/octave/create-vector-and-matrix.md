---
title: "Octave で行列／ベクトルを作成する"
url: "p/65afkmg/"
date: "2017-03-23"
tags: ["Octave"]
useMath: true
aliases: /octave/create-vector-and-matrix.html
---

行列の定義
----

### ３行２列

$$
  A = \begin{bmatrix}
    1 & 2 \\\\
    3 & 4 \\\\
    5 & 6
  \end{bmatrix}
$$

```matlab
A = [1 2; 3 4; 5 6];
```

行ごとにセミコロンで区切ります。

### ２行３列

$$
  A = \begin{bmatrix}
    1 & 2 & 3 \\\\
    4 & 5 & 6
  \end{bmatrix}
$$

```matlab
>> A = [1 2 3; 4 5 6]
A =

   1   2   3
   4   5   6
```


ベクトルの定義
----

### 行ベクトル (row vector)

$$
  v = \begin{bmatrix} 1 & 2 & 3 \end{bmatrix}
$$

```matlab
>> v = [1 2 3]  % 行ベクトル
v =

   1   2   3
```

### 列ベクトル (column vector)

$$
  v = \begin{bmatrix} 1 \\\\ 2 \\\\ 3 \end{bmatrix}
$$

```matlab
>> v = [1; 2; 3]  % 列ベクトル
v =

   1
   2
   3
```

範囲指定でベクトルを生成する
----

コロン __`:`__ を使用して、数値の範囲を指定してベクトルを生成することができます。

### 1 から 5 までの連番

```matlab
>> v = 1:5
v =

   1   2   3   4   5
```

### 1 から 10 までの範囲で、ステップ数 2 の連番

```matlab
>> v = 1:2:10
v =

   1   3   5   7   9
```

