---
title: "リストの生成: 連番のリストを生成する (range)"
date: "2019-11-25"
---

**`range`** 関数で作成した `range` オブジェクトを、`list` 関数に渡すことで、簡単に連番からなるリストを生成することができます。

```python
l1 = list(range(5))          #=> [0, 1, 2, 3, 4]
l2 = list(range(2, 5))       #=> [2, 3, 4]
l3 = list(range(0, 10, 2))   #=> [0, 2, 4, 6, 8]
l4 = list(range(10, 7, -1))  #=> [10, 9, 8]
```

- 参考: [range による数値のループ処理いろいろ](../loop-with-range.html)


各要素の値に対して何らかの演算をした結果のリストを生成したい場合は、**リスト内包表記** を使ってリストを生成します。

```python
# 二乗した値のリスト
l1 = [x**2 for x in range(6)]
print(l1)  #=> [0, 1, 4, 9, 16, 25]

# 3で割り切れない値のリスト
l2 = [x for x in range(1, 10) if x % 3 != 0]
print(l2)  #=> [1, 2, 4, 5, 7, 8]
```

- 参考: [Python のリスト内包表記に慣れる](../column/list-comprehension.html)

