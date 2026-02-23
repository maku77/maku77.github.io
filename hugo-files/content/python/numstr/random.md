---
title: "Pythonメモ: ランダムな数値（乱数）を生成する (randrange, randint, random, uniform)"
url: "p/nx5xama/"
date: "2019-11-25"
tags: ["python"]
aliases: ["/python/numstr/random.html"]
---

**`random`** モジュールを使用して、ランダムな整数や浮動小数点数を生成することができます。
`random` モジュールの関数を使用するには、次のようにインポートしておく必要があります。

```python
import random
```


## ランダムな整数を生成する (randrange, randint)

### 0 以上 n 未満の整数

```python
random.randrange(5)  #=> 0～4 の整数
```

### m 以上 n 未満の整数

```python
random.randrange(5, 10)   #=> 5 ～ 9 の整数
random.randrange(-3, 3))  #=> -3 ～ 2 の整数
random.randrange(5, 5))   #=> ValueError
random.randrange(5, 1))   #=> ValueError
```

ステップを指定することも可能です。

```python
random.randrange(10, 50, 5)  #=> 10, 15, 20, ... 45 のいずれか
```


### m 以上 n 以下の整数

```python
random.randint(5, 10)  #=> 5 ～ 10 の整数
random.randint(10, 5)  #=> ValueError
```

`random.randint(m, n)` は、`random.randrange(m, n+1)` のエイリアスです。


## ランダムな浮動小数点数を生成する (random, uniform)

### 0 以上 1 未満の浮動小数点数

```python
random.random()  #=> [0.0, 1.0) の浮動小数点数
```

### a 以上 b 未満の浮動小数点数

```python
random.uniform(1.0, 2.0))  # [1.0, 2.0] の浮動小数点数
random.uniform(2.0, 1.0))  # 同上
random.uniform(1.0, 1.0))  # 必ず 1.0
```

`random.uniform(a, b)` で浮動小数点数を生成する場合、2 番目の値で指定した値も、値域に含まれることに注意してください（`random.randrange(m, n)` で `n` が値域に含まれないのとは対照的）。
つまり、`a` と `b` で同じ値を指定した場合にはその値が必ず返されることになります。

`a` と `b` の大小関係が逆転しても同様に動作します。
