---
title: パスを結合する (join)
date: "2012-06-04"
---

`os.path` モジュールの `join` メソッドを使用して、複数のパス文字列を結合することができます。

```python
import os.path

path = os.path.join('/foo', 'file.txt')        #=> /foo/file.txt
path = os.path.join('foo', 'bar', 'file.txt')  #=> foo/bar/file.txt
```

#### 応用例: スクリプトと同じディレクトリにあるファイルの絶対パスを作成する

```
basedir = os.path.abspath(os.path.dirname(__file__))
path = os.path.join(basedir, 'file.txt')
```

