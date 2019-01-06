---
title: "リスト／タプルをループで処理する"
date: "2005-10-19"
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

タプルを要素に持つリストなどをループ処理する場合は、下記のようにすると、要素を展開 (unpack) しながら処理することができます。

```python
tuple_list = [('AAA', 100), ('BBB', 200), ('CCC', 300)]
for a, b in tuple_list:
    print(a, b)
```

インデックス番号を振りながらループ処理する
----

`enumerate()` を使用すると、インデックスを付けたイテレーションを行うことができます。

```python
a = ['AAA', 'BBB', 'CCC']
for i, val in enumerate(a):
    print(i, val)
```

#### 実行結果

```
0 AAA
1 BBB
2 CCC
```

要素がタプルなどの複雑な構造の場合も同様です（ループ変数の括弧の付け方に注意してください）。

```python
tuple_list = [('AAA', 100), ('BBB', 200), ('CCC', 300)]
for i, (a, b) in enumerate(tuple_list):
    print(i, a, b)
```

#### 実行結果

```
0 AAA 100
1 BBB 200
2 CCC 300
```

開始インデックスはデフォルトでは 0 ですが、以下のようにして開始インデックスを変更できます。

```python
t = ('AAA', 'BBB', 'CCC')
for i, val in enumerate(t, start=1):
    print(i, val)
```

#### 実行結果

```
1 AAA
2 BBB
3 CCC
```

