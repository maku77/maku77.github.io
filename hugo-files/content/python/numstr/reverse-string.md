---
title: "Pythonメモ: 文字列を逆順にする（反転させる）"
url: "p/x4zi6b4/"
date: "2017-03-17"
tags: ["python"]
aliases: ["/python/numstr/reverse-string.html"]
---

Python の文字列を反転させるには、`[]` 演算子を利用できます。

```python
s1 = 'ABCDE'
s2 = s1[::-1]

print(s1)  #=> "ABCDE"
print(s2)  #=> "EDCBA"
```

`[]` 演算子では、部分シーケンスを `[開始インデックス:終了インデックス+1:ステップ数]` という形で指定できるので、このステップ数部分を -1 にすることで、全体の文字列を反転した文字列を取得できます。
