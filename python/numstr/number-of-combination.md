---
title: "組み合わせの数 (nCr) を求める"
date: "2013-05-04"
---

$nCr$

下記の `nCr` 関数は、n 個の要素うち r 個の要素を選ぶ方法が何通りあるかを計算します。
`10C7` の計算は、実際には `10C3 = 10*9*8 / 3*2` という計算になることを利用して実装しています。

```python
def nCr(n, r):
    """
    Calculate the number of combination (nCr = nPr/r!).
    The parameters need to meet the condition of n >= r >= 0.
    It returns 1 if r == 0, which means there is one pattern
    to choice 0 items out of the number of n.
    """

    # 10C7 = 10C3
    r = min(r, n-r)

    # Calculate the numerator.
    numerator = 1
    for i in range(n, n-r, -1):
        numerator *= i

    # Calculate the denominator. Should use math.factorial?
    denominator = 1
    for i in range(r, 1, -1):
        denominator *= i

    return numerator // denominator
```

#### テスト

```python
for n,r in [(0,0), (0,1), (1,0), (1,1), (2,2), (3,1), (3,2), (5,2), (5,3), (10, 3)]:
    print('nCr({},{})={}'.format(n, r, nCr(n, r)))
```

#### 実行結果

```
nCr(0,0)=1
nCr(0,1)=1  # Illegal case (n < r)
nCr(1,0)=1
nCr(1,1)=1
nCr(2,2)=1
nCr(3,1)=3
nCr(3,2)=3
nCr(5,2)=10
nCr(5,3)=10
nCr(10,3)=120
```

