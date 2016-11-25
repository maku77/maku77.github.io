---
title: Python の最大再帰数を調べる／変更する
created: 2013-04-21
---

Python 処理系の最大再帰数は以下のように調べることができます。
**デフォルトは 1000** になっているようです。
この数を超えて再帰関数呼び出しを行うと、**RuntimeError: maximum recursion depth exceeded in comparison** が発生します。

```python
import sys
print(sys.getrecursionlimit())  #=> 1000
```

実は、最大再帰数は以下のように変更することができます。

```python
import sys
sys.setrecursionlimit(5000)
```

ただし、この値をいくら大きくしても、システムで実行できる再帰数の限界は超えられません。
再帰数の限界を超えて実行しようとすると、Python の処理系がクラッシュします。

