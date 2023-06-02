---
title: "Python でパスを指定したディレクトリからの相対パスに変換する (os.path.relpath)"
url: "p/o4a37zc/"
date: "2023-06-02"
tags: ["Python"]
---

os.path.relpath の使い方
----

Python 標準の [os.path.relpath 関数](https://docs.python.org/ja/3/library/os.path.html#os.path.relpath) を使うと、パスを表す文字列を、特定のディレクトリからの相対パスに変換することができます。

```python
import os

# 元が絶対パスの場合
print(os.path.relpath("/a/b/c", "/"))           # => a/b/c
print(os.path.relpath("/a/b/c", "/a"))          # => b/c
print(os.path.relpath("/a/b/c", "/a/"))         # => b/c
print(os.path.relpath("/a/b/c", "/a/b"))        # => c
print(os.path.relpath("/a/b/c", "/a/b/"))       # => c
print(os.path.relpath("/a/b/c", "/a/b/c"))      # => .
print(os.path.relpath("/a/b/c", "/a/b/c/"))     # => .
print(os.path.relpath("/a/b/c", "/a/b/c/d"))    # => ..
print(os.path.relpath("/a/b/c", "/a/b/c/d/e"))  # => ../..

# 元が相対パスの場合（基本的に同上）
print(os.path.relpath("a/b/c", "a"))          # => b/c
print(os.path.relpath("a/b/c", "a/"))         # => b/c
print(os.path.relpath("a/b/c", "a/b"))        # => c
print(os.path.relpath("a/b/c", "a/b/"))       # => c
print(os.path.relpath("a/b/c", "a/b/c"))      # => .
print(os.path.relpath("a/b/c", "a/b/c/"))     # => .
print(os.path.relpath("a/b/c", "a/b/c/d"))    # => ..
print(os.path.relpath("a/b/c", "a/b/c/d/e"))  # => ../..
```


カレントディレクトリからの相対パス
----

`os.path.relpath` 関数の第 2 引数 (`start`) を省略すると、カレントディレクトリからの相対パスを返します。

```python
import os

os.chdir("C:/Users/maku")  # ホームディレクトリに移動しておく

print(os.path.relpath("C:/"))                  # => ..\..
print(os.path.relpath("C:/Users"))             # => ..
print(os.path.relpath("C:/Users/"))            # => ..
print(os.path.relpath("C:/Users/maku"))        # => .
print(os.path.relpath("C:/Users/maku/a/b/c"))  # => a\b\c
```


使用例
----

具体的な使用例としては、あるディレクトリ内のファイルを列挙するときに、そのディレクトリからの相対パスで出力するケースなどがあります。

{{< code lang="python" title="os.walk でファイル列挙する場合" hl_lines="7 8" >}}
import os

BASE_DIR = "my-dataset"

for dirpath, dirs, files in os.walk(BASE_DIR):
    for name in files:
        # BASE_DIR ディレクトリからの相対パスを構築
        path = os.path.relpath(os.path.join(dirpath, name), BASE_DIR)
        print(path)
{{< /code >}}

{{< code lang="python" title="Path#glob によるファイル列挙" hl_lines="7 8" >}}
import os
from pathlib import Path

BASE_DIR = "my-dataset"

for filename in Path(BASE_DIR).glob("**/*.dat"):
    # BASE_DIR ディレクトリからの相対パスを構築
    path = os.path.relpath(filename, BASE_DIR)
    print(path)
{{< /code >}}

