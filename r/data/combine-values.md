---
title: "2 つのデータを結合する (c)"
date: "2014-12-10"
---

`c()` を使えば、任意の数のベクトルを結合することができます。

```r
a <- 1:3
c(a, a)     #=> 1, 2, 3, 1, 2, 3
c(a, a*2)   #=> 1, 2, 3, 2, 4, 6
c(a, a^2)   #=> 1, 2, 3, 1, 4, 9
c(a, a, 7)  #=> 1, 2, 3, 1, 2, 3, 7
```

