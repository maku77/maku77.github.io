---
title: リストをシャッフルする (random.shuffle)
date: "2014-04-19"
---

`random.shuffle` を使用すると、リストの要素をランダムに並び替えることができます。

```python
import random

a = [1, 2, 3, 4, 5]
random.shuffle(a)
print(a)  #=> [2, 5, 1, 3, 4]
```

`random.shuffle` は、渡されたリストそのものを変更するため、immutable（変更不可）なタプルや文字列を渡すことはできません。

```python
t = (1, 2, 3, 4, 5)
random.shuffle(t)    # TypeError!

s = 'abcdefg'
random.shuffle(s)    # TypeError!
```

