---
title: 三項演算子（条件演算子）を扱う
date: "2014-03-22"
---

```python
if condition:
    X = A
else:
    X = B
```

のような条件分岐は、Python 2.5 以降では以下のように記述できます。
**条件演算 (Conditional Expressions)** と呼ばれている記述方法です。

```
X = A if condition else B
```

評価結果が先頭と末尾に分かれるため、慣れるまでは読みにくいかもしれません。
ちなみに、下記のように if-else の改行を減らす記述方法もあります。

```python
if condition: X = A
else: X = B
```

