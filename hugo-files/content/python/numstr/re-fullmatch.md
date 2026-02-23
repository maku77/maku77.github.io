---
title: "Pythonメモ: 文字列が正規表現に完全一致するか調べる (re.fullmatch)"
url: "p/dbzfge7/"
date: "2020-01-09"
tags: ["python"]
aliases: ["/python/numstr/re-fullmatch.html"]
---

ある文字列が、指定した正規表現パターンに完全に一致するかどうかを調べるには、**`re.fullmatch()`** 関数を使用します。

下記の例では、文字列が年月日 (`YYYY-MM-DD`) のフォーマットに一致するかを調べています。

```python
import re

PATTERN = r'\d{4}-\d{2}-\d{2}'

if re.fullmatch(PATTERN, '2020-01-09'):
    print('First string is correct')

if re.fullmatch(PATTERN, '2020-1-9'):
    print('Second string is correct')
```

{{< code title="実行結果" >}}
First string is correct
{{< /code >}}
