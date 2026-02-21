---
title: "Pythonメモ: 条件分岐いろいろ（if, switch, 三項演算子）"
url: "p/se5cwgm/"
date: "2009-11-18"
tags: ["python"]
aliases:
  - /python/syntax/if.html
  - /python/syntax/switch.html
  - /python/syntax/conditional-expressions.html
---

if 分岐
----

### 下限／上限をまとめて指定

Python では、以下のように 2 つの不等号をまとめて記述できます。

```python
if 0 < x < 100:
    print('Gotcha')
```

### 一行野郎

`if` による分岐後の処理が短い場合は、下記のように記述すれば一行で済ませることができます。

```python
if x == 1: print('Hello')
```


switch 構文
----

Python には **`switch-case` 構文はありません**。
次のように、`if-elif-else` を組み合わせて記述します。

```python
s = 'BBB'

if s in ('AAA', 'BBB'):
    print(1)
elif s == 'CCC':
    print(2)
else:
    print(3)
```


三項演算子（条件演算子）
----

```python
if condition:
    X = A
else:
    X = B
```

のような条件分岐は、Python 2.5 以降では以下のように記述できます。
**条件演算 (Conditional Expressions)** と呼ばれている記述方法です。

```python
X = A if condition else B
```

評価結果が先頭と末尾に分かれるため、慣れるまでは読みにくいかもしれません。
ちなみに、下記のように `if-else` の改行を減らす記述方法もあります。

```python
if condition: X = A
else: X = B
```
