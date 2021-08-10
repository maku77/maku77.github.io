---
title: "ファイル／ディレクトリの名前を変更する (os.rename)"
date: "2007-11-09"
---

`os.rename` を使用して、ファイルやディレクトリの名前を変更することができます。

```python
import os
os.rename('src.txt', 'dst.txt')
```

ファイル名のプレフィックスに日付 ('-YYYYMMDD') を付加する
----
下記のサンプルでは、ディレクトリ内のすべての .png ファイルのファイル名を変更し、拡張子の前に `-20071109` のような日付を表す文字列を挿入します。
例えば、`sample.png` というファイル名は `sample-20071109.png` というファイル名に置換されます。

#### rename.py
```python
import glob
import os
import re
import time

time_str = time.strftime('%Y%m%d')

for old_name in glob.glob('*.png'):
    new_name = re.sub(r'(\.png)$', '-' + time_str + r'\1', old_name)
    os.rename(old_name, new_name)
```

### 実行例（ディレクトリ内に a.png, b.png, c.png がある場合）
```
$ ls
a.png
b.png
c.png

$ python rename.py
$ ls
a-20071109.png
b-20071109.png
c-20071109.png
```

