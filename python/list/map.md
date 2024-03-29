---
title: "リスト内の要素をまとめて変更する (map)"
date: "2014-04-22"
---


`map()` 関数を使用すると、タプルやリスト内の各要素に同じ処理を施した結果を取得することができます。
`map()` 関数の戻り値は `map` オブジェクトなので、下記のように `list()` 関数でリストに変換する必要があります。

#### 例: 各要素をそれぞれ２乗した要素からなるリストを作成する

```python
arr = [1, 2, 3, 4, 5]
m = map(lambda x: x**2, arr)
arr2 = list(m)  #=> [1, 4, 9, 16, 25]
```

下記のように、変換用の関数の中で条件を指定することもできます。

#### 例: 偶数値の要素を 0 に書き換えたリストを作成する

```python
arr = [1, 2, 3, 4, 5]
m = map(lambda x: 0 if x%2==0 else x, arr)
arr2 = list(arr)  #=> [1, 0, 3, 0, 5]
```

もちろん、`map()` には普通に定義した関数を渡すこともできます。

```python
def filter(x):
    return 0 if x%2==0 else x

arr = [1, 2, 3, 4, 5]
arr2 = list(map(filter, arr))
```

自分自身のリストを書き換えてしまってよい場合は、普通の `for` ループで下記のように記述できますね。

```python
arr = [1, 2, 3, 4, 5]
for i in range(len(arr)):
    if arr[i] % 2 == 0: arr[i] = 0
```

文字列も sequence の一種なので、`map()` 関数を適用することができます。
下記の例では、文字列中の各文字の文字コードをリストで取得しています。

#### 例: 文字ごとのコードを取得する

```python
s = 'ABCDE'
arr = list(map(lambda x: ord(x), s))  #=> [65, 66, 67, 68, 69]
```

