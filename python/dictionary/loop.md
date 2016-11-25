---
title: dictionary の要素をループで処理する
created: 2005-10-20
---

単純なループ
----

dictionary オブジェクトを `for-in` ステートメントに渡すと、ループ処理でキーを１つずつ取り出すことができます。

```python
>>> d = {'one': 1, 'two': 2, 'three': 3}
>>> for key in d:
...     print(key, d[key])
...
two 2
three 3
one 1
```


キーと値のペアを取り出しながらループ
----

```python
>>> d = {'one': 1, 'two': 2, 'three': 3}
>>> for key, val in d.items():
...     print(key, val)
...
two 2
three 3
one 1
```

Python 2 の頃は、`iteritems()` メソッドが使用できましたが、Python 3 からは `items()` のみ使用可能です。


連番を振りながらループ
----

list と同様に、`enumerate` と組み合わせることで、0 から始まる連番を振りながらループ処理することができます。

```
>>> for i, key in enumerate(d):
...     print(i, key, d[key])
...
0 two 2
1 three 3
2 one 1
```


キーでソートしてループ処理する
----

* [dictionary の要素をソートして出力する](./sort.html)

