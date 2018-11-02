---
title: リストとタプルの違い
date: "2009-11-24"
---

リストとタプル
----

tuple と list は一連の要素を保持する sequence の一種です。
tuple と list は似ていますが、tuple は immutable でその内容を変更できません。
一方で、list は作成後に各要素の値を変更したり、要素を追加したりすることができます。

```python
l = ['xxx', 100, 'yyy']  # list の作成（後から変更可能）
t = ('xxx', 100, 'yyy')  # tuple の作成（変更不可）
```

string も tuple の仲間
----

ちなみに string も tuple 同様 immutable で変更できません。
string は、character を要素に持つ sequence と考えることができます。
tuple や list と同様に、インデックス指定で個々の要素にアクセスすることができます。

```python
s = 'Hello'  # string の作成（変更不可）
print(s[1])  #=> 'e'
```


空の sequence の作成
----

変数定義のときに括弧の中を空にすれば、空の sequence を作成できます。

```python
a = []  # 空の list
b = ()  # 空の tuple
c = ''  # 空の string
```

