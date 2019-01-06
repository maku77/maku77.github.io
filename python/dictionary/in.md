---
title: "dictionary に指定したキーが存在するか調べる (in)"
date: "2005-10-20"
---

dictionary オブジェクトが指定したキーを持っているかを判別するには、`in` キーワードを使用して下記のようにします。

```python
if 'key1' in d:
    ...
```

Python 2 の頃は、下記のように `has_key` メソッドを使用することができましたが、Python 3 以降は `in` キーワードしか使用できません。

```python
if d.has_key('key1'):
    ...
```

