---
title: print 関数の改行を抑制する
date: "2013-04-10"
---

`print` 関数による改行の出力を止めたい場合は、`end` パラメータに空文字列 `''` を指定します（デフォルトは `end='\n'`）。

```python
print(line, end='')
```

別の方法として、`sys.stdout.write()` を使う方法もあります。

```python
#!/usr/bin/env python
import sys

for line in sys.stdin:
    sys.stdout.write(line)
```

