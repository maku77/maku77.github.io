---
title: "Python でファイルやディレクトリをコピー、移動する (shutil.copyfile, shutil.copy, shutil.copytree, shutil.move)"
url: "p/wyxzbdw/"
date: "2021-08-10"
lastmod: "2023-06-02"
tags: ["Python"]
changes:
  - 2023-06-02: 記事をリファイン
aliases: /python/io/copy-move-files.html
---

ファイルやディレクトリのコピーや移動を行うには、Python 標準ライブラリの [shutil モジュール](https://docs.python.org/ja/3/library/shutil.html) が提供する高水準のファイル操作 API を利用するのが簡単です。


ファイルをコピーする (shutil.copyfile, shutil.copy)
----

### shutil.copyfile 関数

[shutil.copyfile 関数](https://docs.python.org/ja/3/library/shutil.html#shutil.copyfile) で単一のファイルをコピーできます。

```python
import shutil
import sys

try:
    shutil.copyfile('src.txt', 'dst.txt')
except Exception as e:
    print('Error: {0}'.format(e), file=sys.stderr)
```

同名のファイルがすでに存在する場合（上記の場合は `dst.txt`）は上書きされます。
`shutil.copyfile` 関数は次のようなエラーを発生させる可能性があります。

- __`IsADirectoryError`__ ... コピー先として指定したファイル名と同名のディレクトリが存在する場合。
- __`OSError`__ ... 指定したファイル名で書き込めない場合（バージョン 3.3 以降）。
- __`SameFileError`__ ... コピー元とコピー先のファイル名が同じ場合（バージョン 3.4 以降）。
- __`FileNotFoundError`__ ... 存在しないディレクトリ階層（`aaa/bbb/dst.txt` など）にコピーしようとした場合。

### shutil.copy 関数

既存のディレクトリ以下に同じファイル名でコピーしたいときは、`shutil.copyfile` の代わりに [shutil.copy 関数](https://docs.python.org/ja/3/library/shutil.html#shutil.copy) を使用します。

```python
# dst という名前でコピー、あるいは dst ディレクトリ以下にコピー
shutil.copy('src.txt', 'dst')
```

上記のように実行すると、`dst` ディレクトリがない場合は `dst` というファイル名でコピーされ、`dst` ディレクトリが存在する場合は `dst/src.txt` としてコピーされます。

### shutil.copyfile と shutil.copy の使い分け

`copyfile` 関数と `copy` 関数の振る舞いの違いをまとめておきます。
違いは、ターゲットと同じ名前のディレクトリが存在しているときの振る舞いです。

- `shutil.copyfile('src.txt', 'dst')`
    - `dst` が存在しないとき: `dst` としてコピー
    - `dst` ファイルが存在するとき: `dst` ファイルを上書き
    - `dst` ディレクトリが存在するとき: エラー (`IsADirectoryError`)
- `shutil.copy('src.txt', 'dst')`
    - `dst` が存在しないとき: `dst` としてコピー
    - `dst` ファイルが存在するとき: `dst` ファイルを上書き
    - `dst` ディレクトリが存在しないとき: `dst/src.txt` としてコピー

なお、`shutil.copyfile` も `shutil.copy` もディレクトリをコピーすることはできません（`IsADirectoryError` が発生します）。


ディレクトリをコピーする (shutil.copytree)
----

[shutil.copytree 関数](https://docs.python.org/ja/3/library/shutil.html#shutil.copytree) を使用すると、指定したディレクトリを丸ごとコピーできます。
次の例では、`src` ディレクトリを `dst` ディレクトリとしてコピーします。

```python
import shutil

shutil.copytree('src', 'dst')
```

すでにカレントディレクトリに `dst` という名前のファイルやディレクトリが存在する場合は __`FileExistsError`__ が発生します。
ただし、次のように __`dirs_exist_ok`__ フラグを `True` に設定すると、`dst` ディレクトリが存在していてもそのままコピーしようとします（`dst` 内のファイル群が上書きされるという動作になります）。

```python
import shutil

shutil.copytree('src', 'dst', dirs_exist_ok=True)
```

`shutil.copytree` 関数には面白い機能が付いていて、次のように __`ignore`__ パラメーターを利用すると、グロブパターンに一致したファイルやディレクトリをコピー対象から外すことができます（バージョン 3.8 以降）。

```python
from shutil import copytree, ignore_patterns

copytree(src, dst, ignore=ignore_patterns('*.pyc', 'tmp*'))
```


ファイルやディレクトリを移動する (shutil.move)
----

ファイルやディレクトリの移動は単純で、[shutil.move 関数](https://docs.python.org/ja/3/library/shutil.html#shutil.move) 一種類だけで実現できます。

```python
import shutil

shutil.move('src', 'dst')
```

上記のようにすると、`src` ファイル（あるいはディレクトリ）を、`dst` という名前に変更します。
すでに `dst` という名前のディレクトリが存在している場合は、`dst` ディレクトリ内に移動させようとします（`dst/src` ができる）。
つまり、Linux の `mv` コマンドのように振る舞います。

確実にリネームだけを実行したい場合は、__`os.rename`__ 関数を使ってください。

- 参考: [Python でファイルやディレクトリの名前を変更する (os.rename, os.renames)](/p/9aqzppe/)


ファイルの存在を確認しておくのが安全 (os.path.exists)
----

基本的には、ファイルやディレクトリの移動やコピーを行う場合は、[os.path.exists 関数]() を使って、あらかじめ同名のファイルやディレクトリが存在していないかをチェックしておくのが安全です。

```python
import os
import shutil

def check_and_copy(src, dst):
    """
    ファイルをコピーします。
    すでに同名のファイルが存在する場合は、確認プロンプトを表示します。
    """
    if os.path.exists(dst):
        if input('Overwrite?(y/n): ') != 'y':
            return
    shutil.copyfile(src, dst)

check_and_copy('src', 'dst')
```
