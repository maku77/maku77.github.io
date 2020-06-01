---
title: "リストをフィルタして条件に一致する要素を抽出する (filter)"
date: "2020-06-01"
---

条件に一致する要素を抽出する
----

Python の組み込み関数である [filter 関数](https://docs.python.org/ja/3/library/functions.html#filter) を使用すると、iterable なコンテナ系要素から、条件に一致する要素のみを抽出することができます。

次の例では、いくつかの数値が格納されたタプルをフィルタして、偶数値のみを抽出しています。

```python
nums = (1, 2, 3, 4, 5, 6, 7, 8)
nums2 = filter(lambda x: x % 2 == 0, nums)
print(list(nums2))  #=> [2, 4, 6, 8]
```

`filter` 関数が返すのは `iterator` オブジェクトなので、リストに変換したいときは `list` 関数に通す必要があることに注意してください。
上記の例では、フィルタ用関数としてラムダ式を使っていますが、通常の関数で指定することもできます。

```python
def is_even(num):
    return num % 2 == 0

nums = (1, 2, 3, 4, 5, 6, 7, 8)
nums2 = filter(is_even, nums)
print(list(nums2))  #=> [2, 4, 6, 8]
```

実は `filter` 関数を使わなくても、次のようにリストの内包表記を使って同様にフィルタすることができます。

```python
nums = (1, 2, 3, 4, 5, 6, 7, 8)
nums2 = [x for x in nums if x % 2 == 0]
print(nums2)  #=> [2, 4, 6, 8]
```


None でない要素のみ抽出する
----

`filter` 関数のおまけ的な使い方として、第一引数に `None` を指定して `None` 以外の要素のみを抽出するという使い方があります。

```python
values = ('AAA', None, 100, None, 'BBB')
values2 = filter(None, values)
print(list(values2))  #=> ['AAA', 100, 'BBB']
```

つまり、次のように記述するのと同じ振る舞いをします。

```python
values2 = filter(lambda x: x is not None, values)
```

```python
values2 = [x for x in values if x is not None]
```

