---
title: "ランダムな正規分布を生成する (rnorm)"
date: "2014-12-09"
---

`rnorm()` を使用すると、指定した数のランダムな正規分布データを作成します。

```r
x <- rnorm(1000)
hist(x, breaks=30)
```

![normal-distribution](normal-distribution.svg){: .center }

標準偏差 (standard deviation) はデフォルトで 1 となりますが、`sd` パラメータで任意の標準偏差を指定することもできます。

```r
x <- rnorm(1000, sd=2)
hist(x, breaks=30)
```

![normal-distribution2](normal-distribution2.svg){: .center }

