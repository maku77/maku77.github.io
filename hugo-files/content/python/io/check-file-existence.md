---
title: "Python でファイル／ディレクトリの存在を確認する (os.path.exists, os.path.isfile, os.path.isdir)"
url: "p/tr3cmu5/"
date: "2013-05-08"
lastmod: "2021-08-10"
tags: ["Python"]
aliases: /python/check-file-existence.html
---

ファイルあるいはディレクトリが存在するかを調べる
----

Python で、指定した名前のファイル、あるいはディレクトリが存在しているかを調べるには、[os.path.exists 関数](https://docs.python.org/ja/3/library/os.path.html#os.path.exists) を使用します。
シンボリックリンクに対して実行した場合、`os.path.exists` 関数は、リンク先のファイルの有無を調べます。

{{< code lang="python" title="sample.txt という名前のファイルかディレクトリが存在するかを調べる" >}}
import os

if os.path.exists('sample.txt'):
    print('Found!')
{{< /code >}}


ファイルの存在、ディレクトリの存在を調べる
----

前述の `os.path.exists` は、ファイルあるいはディレクトリの存在を調べますが、ファイルのみ、ディレクトリのみに絞って存在を調べたいときは、[os.path.isfile 関数](https://docs.python.org/ja/3/library/os.path.html#os.path.isfile) や [os.path.isdir 関数](https://docs.python.org/ja/3/library/os.path.html#os.path.isdir) を使用します。

```python
import os

if os.path.isfile('sample'):
    print('sample というファイルが見つかりました')

if os.path.isdir('sample'):
    print('sample というディレクトリが見つかりました')
```

