---
title: 順列の数 (nPr) を求める
created: 2013-05-12
---

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

#### 実行結果

```
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
```

