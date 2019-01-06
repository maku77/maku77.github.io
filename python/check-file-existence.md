---
title: "ファイル／ディレクトリの存在を確認する"
date: "2013-05-08"
---

ファイルやディレクトリの存在を確認したい場合は、`os.path.exists` を使用します。
シンボリックリンクに対して実行した場合で、リンク先のファイルが存在しない場合は、`os.path.exists` は `False` を返します。

#### 使用例
```python
import os

if os.path.exists('sample.txt'):
    print('Yeah!')
```

