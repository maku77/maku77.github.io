---
title: "階乗を求める (math.factorial)"
date: "2013-05-04"
---

math.factorial() を使う方法
----

```python
import math
val = math.factorial(5)  #=> 120 (= 1x2x3x4x5)
```

自力で計算する fact(n)
----

下記の `fact(n)` 関数は、与えられた整数 n に対して、n, n-1, n-2, ..., 3, 2 までを順番に掛け合わせて階乗の計算を行っています。

```python
def fact(n):
    val = 1
    for i in range(2, n + 1):
        val *= i
    return val
```

#### テスト

```python
for i in range(1, 10):
    print('fact({})={}'.format(i, fact(i)))
```

#### 実行結果

```
fact(1)=1
fact(2)=2
fact(3)=6
fact(4)=24
fact(5)=120
fact(6)=720
fact(7)=5040
fact(8)=40320
fact(9)=362880
```

