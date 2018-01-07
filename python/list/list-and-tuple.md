---
title: リストとタプルの違い
date: "2009-11-24"
---

tuple と list は一連の要素を保持する sequence の一種です。
tuple と list は似ていますが、tuple は immutable でその内容を変更できません。
ちなみに string も tuple 同様 immutable です。

```python
a = ['xxx', 100, 'yyy']  # list の作成（後から変更可能）
b = ('xxx', 100, 'yyy')  # tuple の作成（変更不可）
c = 'this is a pen'      # string の作成（変更不可）
```

string オブジェクトも character を要素に持つ、sequence と考えることができます。
tuple や list と同様に、インデックス指定で個々の要素にアクセスすることができます。

```python
>>> s = 'Hello'
>>> s[1]
e
```

変数定義のときに括弧の中を空にすれば、空の sequence を作成できます。

```python
a = []  # 空の list
b = ()  # 空の tuple
c = ''  # 空の string
```

