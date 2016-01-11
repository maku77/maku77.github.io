---
title: range によるループ処理いろいろ
created: 2007-10-23
---

指定した回数だけループ
----

```python
>>> for x in range(5):
...     print(x)
...
0
1
2
3
4
```

範囲を指定してループ
----

```python
>>> for x in range(10, 15):
...     print(x)
...
10
11
12
13
14
```

範囲とステップを指定してループ
----

```python
>>> for x in range(10, 20, 2):
...     print(x)
...
10
12
14
16
18
```

ステップを負の値にすれば、数値を減らしていくループもできます。

```python
>>> for x in range(10, 5, -1):
...     print(x)
...
10
9
8
7
6
```

