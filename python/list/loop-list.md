---
title: リスト／タプルをループで処理する
created: 2005-10-19
---

リスト／タプルのループ処理の基本
----

Python でリスト要素やタプル要素をループ処理するには `for ... in` 構文を使用します。

```python
a = ['AAA', 'BBB', 'CCC']
for x in a:
    print(x)
```

#### 実行結果

```
AAA
BBB
CCC
```

入れ子になったリスト／タプルをループ処理する
----

タプルを要素に持つリストなどをループ処理する場合は、下記のようにすると、要素を展開しながら処理することができます。

```python
tuple_list = [('AAA', 100), ('BBB', 200), ('CCC', 300)]
for a, b in tuple_list:
    print(a, b)
```

