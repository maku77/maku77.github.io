---
title: "ファイル／ディレクトリの存在を確認する (os.path.exists, os.path.isfile, os.path.isdir)"
date: "2013-05-08"
lastmod: "2021-08-10"
---

ファイルやディレクトリの存在を確認したい場合は、[os.path.exists 関数](https://docs.python.org/ja/3/library/os.path.html#os.path.exists) を使用します。
シンボリックリンクに対して実行した場合、リンク先のファイルが存在しないときは `os.path.exists` は `False` を返します。

```python
import os

if os.path.exists('sample.txt'):
    print('Found!')
```

上記の `os.path.exists` は、ファイルあるいはディレクトリの存在を調べますが、ファイルのみ、ディレクトリのみに絞って存在を調べたいときは、[os.path.isfile 関数](https://docs.python.org/ja/3/library/os.path.html#os.path.isfile) や [os.path.isdir 関数](https://docs.python.org/ja/3/library/os.path.html#os.path.isdir) を使用します。

```python
import os

if os.path.isfile('sample'):
    print('sample というファイルが見つかりました')

if os.path.isdir('sample'):
    print('sample というディレクトリが見つかりました')
```

