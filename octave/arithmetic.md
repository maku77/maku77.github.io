---
title: "行列／ベクトルの四則演算（加算、減算、乗算、除算）を行う"
date: "2017-03-24"
---

行列同士の四則演算
----

下記のような２行３列の行列を２つ用意します。

~~~ matlab
>> A = [1 2 3; 4 5 6]
A =

   1   2   3
   4   5   6

>> B = [10 20 30; 40 50 60]
B =

   10   20   30
   40   50   60
~~~

各要素同士の四則演算を行うには、`+`、`-`、`.*`、`./` という演算子を使用します（`.*` と `./` は、ドットが必要なことに注意してください）。

~~~ matlab
>> A + B
ans =

   11   22   33
   44   55   66

>> A - B
ans =

   -9  -18  -27
  -36  -45  -54

>> A .* B
ans =

    10    40    90
   160   250   360

>> A ./ B
ans =

   0.10000   0.10000   0.10000
   0.10000   0.10000   0.10000
~~~


行列とスカラ値の演算
----

行列とスカラ値との四則演算を行うと、各要素の演算でそのスカラ値が使用されます。

~~~ matlab
>> A + 100
ans =

   101   102   103
   104   105   106

>> A * 100
ans =

   100   200   300
   400   500   600

>> A / 100
ans =

   0.010000   0.020000   0.030000
   0.040000   0.050000   0.060000

>> A - 100
ans =

  -99  -98  -97
  -96  -95  -94
~~~

単項演算子を使えば、各要素の符号を反転できます。

~~~ matlab
>> -A
ans =

  -1  -2  -3
  -4  -5  -6
~~~

次のようにすると、各要素を２乗できます。

~~~ matlab
>> A .^ 2
ans =

    1    4    9
   16   25   36
~~~

各要素の逆数を取ることもできます。

~~~ matlab
>> 1 ./ A
ans =

   1.00000   0.50000   0.33333
   0.25000   0.20000   0.16667
~~~


各行、各列ごとにベクトルを足しこむ
----

行列に対してベクトルを用いた四則演算を行うと、縦方向、あるいは横方向にベクトルの要素が拡張（コピー）されて演算が行われます。

~~~ matlab
>> A = [1 2 3; 4 5 6]
A =

   1   2   3
   4   5   6

>> A + [100 200 300]
ans =

   101   202   303
   104   205   306

>> A + [100; 200]
ans =

   101   102   103
   204   205   206
~~~
