---
title: dictionary の要素をソートして出力する
date: "2013-04-21"
---

dictionary の要素は順序付けして格納されているわけではないので、dictionary のソートというのは、キーによって順序付けして要素を取り出していくという意味になります。

キーでソートして出力
----

```python
>>> d = {'AAA':300, 'CCC':100, 'BBB':200}
>>> for key in sorted(d.keys()):
...     print(key, d[key])

AAA 300
BBB 200
CCC 100
```


値でソートして出力
----

```python
>>> d = {'AAA':300, 'CCC':100, 'BBB':200}
>>> for key in sorted(d, key=lambda x:d[x]):
...     print(key, d[key])

CCC 100
BBB 200
AAA 300
```

