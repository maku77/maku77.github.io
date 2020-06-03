---
title: "ディレクトリを作成する (os.mkdir, os.makedirs)"
date: "2013-05-08"
---

ディレクトリの作成は、`os.mkdir()` あるいは `os.makedirs()` で行えます。
後者の `os.makedirs()` を使用すると、深いディレクトリ階層を一度に作ることができます。


ディレクトリ作成の基本
====

`os.mkdir()` を使用して、空のディレクトリを作成することができます。

#### os.mkdir の説明
> os.mkdir(path, mode=0o777, *, dir_fd=None)
>
> Create a directory.
>
> If dir_fd is not None, it should be a file descriptor open to a directory, and path should be relative; path will then be relative to that directory.
> dir_fd may not be implemented on your platform.
> If it is unavailable, using it will raise a NotImplementedError.
>
> The mode argument is ignored on Windows.

下記は、カレントディレクトリに `aaa` というディレクトリを作成する例です。

```python
import os

os.mkdir('./aaa')
```

複数階層のディレクトリを一気に作成する
====

階層の深いディレクトリ構造を一度に作成するには、`os.mkdir` の代わりに `os.makedirs` を使用します。

#### os.makedirs の説明
> makedirs(path [, mode=0o777][, exist_ok=False])
>
> Super-mkdir; create a leaf directory and all intermediate ones. Works like mkdir, except that any intermediate path segment (not just the rightmost) will be created if it does not exist. If the target directory with the same mode as we specified already exists, raises an OSError if exist_ok is False, otherwise no exception is raised. This is recursive.

下記の例では、カレントディレクトリ以下に、`aaa/bbb/ccc` という深い階層のディレクトリを作成しています。
`aaa` ディレクトリや、`aaa/bbb` ディレクトリが存在しない場合は、ついでに作成してくれます。

#### 使用例
```python
import os

os.makedirs('aaa/bbb/ccc')
```


すでにディレクトリが存在している場合の FileExistsError
====

すでに存在するディレクトリと同じ名前のディレクトリを作成しようとすると、`os.mkdir()` や `os.makedirs()` は **FileExistsError** を発生させます（ディレクトリだけど **FileExistsError** です）。
`os.makedirs()` で、深い階層のディレクトリを作成する場合は、上位のディレクトリは存在していても大丈夫ですが、最下位のディレクトリが存在している場合に **FileExistsError** になります。

下記の例では、この **FileExistsError** エラーをハンドルしています。

```python
import os
import sys

try:
    os.mkdir('foo')
except FileExistsError as e:
    print(e.strerror)  # エラーメッセージ ('Cannot create a file when that file already exists')
    print(e.errno)     # エラー番号 (17)
    print(e.filename)  # 作成できなかったディレクトリ名 ('foo')
    sys.exit(1)
```

上記の例では、エラーオブジェクトの各プロパティを個別に出力していますが、エラーオブジェクトをそのまま文字列に変換 `str(e)` して出力すると、まともなエラーメッセージとして使用できます。

```python
except FileExistsError as e:
    print(e)  #=> [WinError 183] Cannot create a file when that file already exists: 'foo'
    sys.exit(1)
```

FileExistsError の抑制
----

`FileExistsError` エラーの発生を抑制する（すでに存在する場合は何も起こらないようにする）には、以下のような方法があります。

* あらかじめ `os.path.exists()` などを使ってディレクトリが存在していない場合だけ作成する
* `os.makedirs()` の場合は、`exist_ok=True` オプションを指定してエラーを抑制可能

`os.mkdir()` の方には `exist_ok` オプションは用意されていないので、このオプションを使いたい場合は、深い階層のディレクトリを作成しない場合でも、`os.makedirs()` を使用する必要があります。

#### ディレクトリが既にある場合にエラーを発生させない
```python
os.makedirs('aaa', exist_ok=True)
```

