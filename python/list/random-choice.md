---
title: リスト／タプル内の要素をランダムで取得する (random.choice, random.choices, random.sample)
date: "2018-11-02"
---

リストからランダムに 1 つだけ取得する
----

**`random.choice`** を使用すると、リストやタプルの要素をランダムで 1 つ取り出すことができます。

```python
>>> import random
>>> t = (1, 2, 3)
>>> random.choice(t)
2
```

文字列も sequence の一種であるため、`random.choice` を適用することができます。

```python
>>> import random
>>> s = 'ABCDE'
>>> random.choice(s)
'B'
```


リストからランダムに n 個取得する
----

`random.choice` の代わりに、**`random.choices`** や **`random.sample`** を使用すると、リストから指定した数の要素をランダムに取得することができます。
取り出す要素数は `k` パラメータで指定します。

- `random.choices(t, k=3)`: タプル t からランダムに 3 つ取り出す（重複を許す）
- `random.sample(t, k=3)`: タプル t からランダムに 3 つ取り出す（重複しない）

下記の例では、1～9 の数値の中からランダムに 5 つの値を取得しています。
`random.choices` を使用した場合は、取得した要素が重複する可能性があることに注意してください。

```python
>>> import random
>>> t = range(1, 10)
>>> random.choices(t, k=5)
[4, 2, 2, 8, 1]

>>> random.sample(t, k=5)
[5, 9, 6, 3, 2]
```


リストからランダムに 1 つずつ全て取り出す
----

リスト内の要素をランダムな順番で 1 つずつ取り出したいときは、単純に **`random.shuffle`** したリストをループ処理すればよいですね。

```python
import random

arr = [1, 2, 3, 4, 5]
random.shuffle(arr)
for x in arr:
    print(x)
```

#### 実行結果

```
2
5
3
1
4
```

