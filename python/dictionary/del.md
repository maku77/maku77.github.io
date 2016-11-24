---
title: dictionary の要素を削除する (del)
created: 2005-10-20
---

dictionary から指定したキーの要素を削除するには、下記のようにします。

```python
del d['key']
```

次のサンプルでは、実際に要素が削除されているかを `in` で確認しています。

```python
>>> d = {'key1': 100, 'key2': 200}
>>> 'key1' in d
True
>>> del d['key1']
>>> 'key1' in d
False
```

削除済みの要素を参照しようとするとエラー (`KeyError`) になります。

```python
>>> d['key1']
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
KeyError: 'key1'
```

