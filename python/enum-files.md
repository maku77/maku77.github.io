---
title: ディレクトリ内のファイルを列挙する
created: 2009-01-06
---

カレントディレクトリ以下のファイルを列挙
====

`os.listdir()` を使うと、指定したディレクトリ内のファイル、ディレクトリをリストで取得できます。
カレントディレクトリや親ディレクトリを表す '.' や '..' は、列挙の対象に含まれません。
列挙されたものが、ディレクトリかファイルかを調べたい場合は、`os.path.isfile(path)` が使えます。

#### カレントディレクトリ内のディレクトリとファイルを列挙
```python
import os

entries = os.listdir('.')
for x in entries:
    if os.path.isdir(x):
        print('DIR:', x)
    else:
        print('FILE:', x)
```

ディレクトリを再帰的にたどってファイルを列挙
====

ディレクトリを再帰的にたどりたい場合は次のようにします。

```python
import os

def enum_files(dir):
    entries = os.listdir(dir)
    for x in entries:
        path = os.path.join(dir, x)
        if os.path.isdir(path):
            for _ in enum_files(path): yield _
        else:
            yield path

if __name__ == '__main__':
    for path in enum_files('.'):
        print(path)
```

複数ディレクトリディレクトリに渡るファイルの列挙を行う場合、ファイルのベースネームだけを出力すると、具体的なファイルの位置を区別できなくなってしまうので、`os.path.join` を使って、上位のディレクトリパスを結合したパスを表示するようにしています。


os.walk を使用したファイルの列挙
====
`os.walk()` を使用すると、再帰的なファイル列挙をさらに簡単に記述できます。

```python
for dirpath, dirs, files in os.walk('.'):
    print(dirpath)
    print(dirs)
    print(files)
    print('-------------')
```

`dirpath` は現在検索中のディレクトリパス、`dirnames` はそのディレクトリに含まれているディレクトリのリスト、`filenames` はそのディレクトリに含まれているファイルのリストを示します。
すべてのファイルのパスを表示するには次のようにすればよいでしょう。

```python
for dirpath, dirs, files in os.walk('.'):
    for f in files:
        print(os.path.join(dirpath, f))
```


参考
====

* https://docs.python.org/3/library/os.html#os-file-dir

