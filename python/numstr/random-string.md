---
title: "ランダムな文字列を生成する (random.choice)"
date: "2019-10-22"
lastmod: "2021-07-05"
---

rand_str 関数を実装する
----

### rand-str.py

次の `rand_str` 関数は、指定した長さのランダム文字列を生成します。

```python
import random

def rand_str(length):
    chars = '23456789abcdefghijkmnopqrstuvwxzy'
    return ''.join([random.choice(chars) for _ in range(length)])

if __name__ == '__main__':
    print(rand_str(7))
```

### 実行例

```
$ python rand-str.py
f873u5y
```

### 解説

__`random.choice()`__ を使用すると、指定した文字列シーケンスの中から、ランダムに 1 文字を取り出すことができます。
これを使って 7 つのランダムな文字を生成して 1 つの文字列に結合すれば、長さ 7 文字分のランダム文字列を作成することができます。


参考
----

- [Ruby で n 文字のランダム文字列を生成する](/ruby/string/rand-str.html)
- [ランダムな文字列を生成する Web サイト](https://maku.blog/p/3nx9is3/)

