---
title: "Pythonメモ: リスト要素の合計値を求める (sum)"
url: "p/6ta64b8/"
date: "2013-04-13"
tags: ["python"]
aliases: ["/python/numstr/sum.html"]
---

## リスト要素の合計値を求める

```python
arr = [1, 2, 3, 4, 5, 6, 7, 8, 9]
total = sum(arr)  #=> 45
```

## 入れ子リスト（2 次元配列）の合計値を求める

```python
arr = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
total = 0
for i in arr:
    total += sum(i)
print(total)  #=> 45
```

もっとスマートに次のように書くこともできます。

```python
arr = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
total = sum(map(sum, arr))    #=> 45
```

最初の `map(sum, arr)` の部分で子要素の合計値をそれぞれ求め、それをさらに `sum()` で合計しています。

他にも、内包表記を使って記述することができます（中間オブジェクトの生成が抑制されるため、こちらのほうが若干高速になるようです）。

```python
arr = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
total = sum(sum(x) for x in arr)  #=> 45
```
