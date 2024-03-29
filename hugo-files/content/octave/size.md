---
title: "Octave で行列／ベクトルのサイズ（形状）を確認する (size, length)"
url: "p/aodnfsx/"
date: "2017-03-24"
tags: ["Octave"]
aliases: /octave/size.html
---

行列のサイズを調べる (size)
----

Octave の組み込み関数 __`size`__ を使用すると、行列（あるいはベクトル）の形状（サイズ）を調べることができます。

```matlab
>> A = [1 2 3; 4 5 6]
A =

   1   2   3
   4   5   6

>> size(A)
ans =

   2   3
```

`size` メソッドの戻り値自体も、1 行 2 列の行列（行ベクトル）になっています。

```matlab
>> size(size(A))
ans =

   1   2
```


ベクトルのサイズを調べる (length)
----

ベクトルのサイズを調べるときにも、`size` 関数を使用することができますが、代わりに __`length`__ 関数を使用することで、スカラ値でベクトルのサイズを取得することができます。
`length` 関数は、行ベクトルの場合でも、列ベクトルの場合でも同様な値を返してくれます。

{{< code lang="matlab" title="行ベクトルの場合" >}}
>> v = [1 2 3 4 5];
>> length(v)
ans =  5
{{< /code >}}

{{< code lang="matlab" title="列ベクトルの場合" >}}
>> v = [1; 2; 3; 4; 5];
>> length(v)
ans =  5
{{< /code >}}

上記のように、行ベクトルでも列ベクトルでも共通の値を返してくれるのは、`length` 関数が __「行のサイズと列のサイズのうち大きい方を返す」__ という振る舞いをするからです。
行列に対して `length` 関数を適用すると、下記のようにあまり意味のない結果を返すことになるので、`length` 関数はベクトル用だと思って使うのがよいでしょう。

```matlab
>> length([1 2; 3 4; 5 6; 7 8])
ans =  4

>> length([1 2 3 4 5; 6 7 8 9 10])
ans =  5
```

