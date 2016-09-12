---
title: Python の if 構文
created: 2009-11-18
---

下限／上限をまとめて指定
====
Python では、以下のように 2 つの不等号をまとめて記述できます。

```python
if 0 < x < 100:
    print('Gotcha')
```


一行野郎
====
`if` による分岐後の処理が短い場合は、下記のように記述すれば一行で済ませることができます。

```python
if x == 1: print('Hello')
```
