---
title: Python の switch-case 構文
created: 2014-11-28
---

Python には switch-case 構文はありません。
次のように、if-elif-else を組み合わせて記述します。

```python
s = 'BBB'

if s in ('AAA', 'BBB'):
    print(1)
elif s == 'CCC':
    print(2)
else:
    print(3)
```

