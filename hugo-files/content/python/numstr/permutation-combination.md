---
title: "Pythonメモ: 順列の数 (nPr) を求める／組み合わせの数 (nCr) を求める"
url: "p/vbrsmed/"
date: "2013-05-12"
tags: ["python"]
aliases: 
  - /python/numstr/number-of-permutation.html
  - /python/numstr/number-of-combination.html
---

順列の数 (nPr) を求める
----

```python
def nPr(n, r):
    """
    Calculate the number of permutation (nPr = n!/(n-r)!).
    The parameters need to meet conditions of n >= r >= 0.
    It returns 1 if r == 0, which means there is one pattern
    to choice 0 items out of the number of n.
    """
    num = 1
    for i in range(n, n-r, -1):
        num *= i
    return num

# test
for n,r in [(0,0), (0,1), (1,0), (1,1), (2,2), (3,1), (3,2), (5,2), (5,3), (10, 3)]:
    print('nPr({},{})={}'.format(n, r, nPr(n, r)))
```

{{< code title="実行結果" >}}
nPr(0,0)=1
nPr(0,1)=0
nPr(1,0)=1
nPr(1,1)=1
nPr(2,2)=2
nPr(3,1)=3
nPr(3,2)=6
nPr(5,2)=20
nPr(5,3)=60
nPr(10,3)=720
{{< /code >}}


組み合わせの数 (nCr) を求める
----

下記の `nCr` 関数は、n 個の要素のうち r 個の要素を選ぶ方法が何通りあるかを計算します。
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

{{< code lang="python" title="テスト" >}}
for n,r in [(0,0), (0,1), (1,0), (1,1), (2,2), (3,1), (3,2), (5,2), (5,3), (10, 3)]:
    print('nCr({},{})={}'.format(n, r, nCr(n, r)))
{{< /code >}}

{{< code title="実行結果" >}}
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
{{< /code >}}

