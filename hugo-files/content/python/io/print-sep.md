---
title: "Pythonメモ: print 関数に複数のパラメータを渡した時の接続文字列を変更する (sep)"
url: "p/29vbxrz/"
date: "2009-11-18"
tags: ["python"]
aliases: /python/io/print-sep.html
---

`print` 関数に複数のパラメータをカンマ区切りで渡した場合、デフォルトではそれぞれの値が 1 つのスペースで接続されて出力されます。

```python
>>> print(1, 2, 3)
1 2 3
```

スペース以外の文字（あるいは文字列）で接続して出力するには、次のように **`sep`** パラメータを指定します。

```python
>>> print(1, 2, 3, sep='')
123

>>> print(1, 2, 3, sep='---')
1---2---3
```
