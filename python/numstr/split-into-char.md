---
title: "文字列を１文字ずつに分割してリストにする"
date: "2009-11-25"
---

`list` 関数や `tuple` 関数に文字列を渡すと、文字列を１文字ごとに分割したリスト、タプルを作成することができます。

```python
>>> list('abc')
['a', 'b', 'c']

>>> tuple('abc')
('a', 'b', 'c')
```

ただ、Python の文字列は以下のように `for-in` 構文で１文字ずつループ処理させることができるので、実際にリストやタプルへの変換が必要になるケースは少ないでしょう。

```python
for ch in s:
    print(ch)

for i in range(len(s)):
    print(s[i])
```

１文字ごとにセパレータ文字列を挟んだ文字列を作成したいときは、いったん文字列を分割してから結合するといった処理を記述する必要はなく、下記のように一発で作成することができます。

```python
>>> ', '.join('abcde')
'a, b, c, d, e'
```
