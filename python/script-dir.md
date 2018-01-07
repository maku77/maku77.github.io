---
title: Python スクリプトが格納されているディレクトリのパスを取得する
date: "2012-06-04"
---

下記は、実行中の Python スクリプトが格納されているディレクトリの絶対パスを取得するサンプルです。

```python
#!/usr/bin/env python
import os.path

BASE_DIR = os.path.realpath(os.path.dirname(__file__))
print(BASE_DIR)
```

Python 2.3 より古いバージョンの場合は、`__file__` の代わりに `sys.argv[0]` を使用します。
シンボリックリンクを展開したくない場合は、`os.path.realpath()` の代わりに、`os.path.abspath()` を使用してください。

