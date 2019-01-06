---
title: "dictionary の要素を参照する ([], get)"
date: "2005-10-20"
---

Python の dictionary オブジェクトの要素を参照するには下記のようにします。

```python
val = d['key']  # 要素の参照
d['key'] = val  # 要素の追加
```

キーが存在しない場合に、デフォルト値を返したい場合は `get` メソッドを使用します。

```
d.get(key, default=None)
```

