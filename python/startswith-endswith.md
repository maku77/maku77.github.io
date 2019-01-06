---
title: "文字列がある文字列で始まっている／終わっているかを調べる"
date: "2012-09-28"
---

ある文字列で始まっているか調べる
====

```python
if s.startswith('AAA'):
    print('YES')
```

ある文字列で終わっているか調べる
====

```python
if s.endswith('AAA'):
    print('YES')
```

OR 条件の指定
====
`str#startswith()`、`str#endswith()` 共に、文字列パターンをタプルで渡すことで、複数の一致条件 (or) を指定することができます。

```python
if s.startswith(('AAA', 'BBB')):
    print('It starts with AAA or BBB')
```

