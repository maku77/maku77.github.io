---
title: "Pythonメモ: ランダムな文字列を生成する (random.choice)"
url: "p/vfp5zx9/"
date: "2019-10-22"
lastmod: "2021-07-05"
tags: ["python"]
aliases: ["/python/numstr/random-string.html"]
---

## rand_str 関数を実装する

{{< code lang="python" title="rand-str.py" >}}
import random

def rand_str(length):
    chars = '23456789abcdefghijkmnopqrstuvwxzy'
    return ''.join([random.choice(chars) for _ in range(length)])

if __name__ == '__main__':
    print(rand_str(7))
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ python rand-str.py
f873u5y
{{< /code >}}

### 解説

__`random.choice()`__ を使用すると、指定した文字列シーケンスの中から、ランダムに 1 文字を取り出すことができます。
これを使って 7 つのランダムな文字を生成して 1 つの文字列に結合すれば、長さ 7 文字分のランダム文字列を作成することができます。


## 参考

- [Ruby で n 文字のランダム文字列を生成する](/p/vt7ws7t/)
- [ランダムな文字列を生成する Web サイト](https://maku.blog/p/3nx9is3/)
