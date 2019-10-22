---
title: "ランダムな文字列を生成する (random.choice)"
date: "2019-10-22"
---

**`random.choice()`** を使用すると、指定した文字列シーケンスの中から、ランダムに 1 文字を取り出すことができます。
例えば、これで 7 つのランダム文字を生成して 1 つの文字列に結合すれば、長さ 7 文字分のランダム文字列を作成することができます。

下記の `random_str()` 関数は、指定した長さのランダム文字列を生成します。

#### random.py

```python
import random

def random_str(length):
    chars = '23456789abcdefghijkmnopqrstuvwxzy'
    return ''.join([random.choice(chars) for _ in range(length)])

if __name__ == '__main__':
    print(random_str(7))
```

#### 実行例

```
$ python random.py
f873u5y
```

- 参考: [ランダムな文字列を生成する Web サイト](https://maku.blog/p/3nx9is3/)

