---
title: "ディレクトリ内のファイルを列挙する"
date: "2009-01-06"
lastmode: "2019-11-06"
---

カレントディレクトリ以下のファイルを列挙する（再帰なし）
----

`os.listdir()` を使うと、指定したディレクトリ内のファイル、ディレクトリをリストで取得できます。
カレントディレクトリや親ディレクトリを表す '.' や '..' は、列挙の対象に含まれません。
列挙されたものが、ディレクトリかファイルかを調べたい場合は、`os.path.isfile(path)` が使えます。

### カレントディレクトリ内のディレクトリとファイルを列挙（一階層のみ）

```python
import os

entries = os.listdir('.')
for x in entries:
    if os.path.isdir(x):
        print('DIR:', x)
    else:
        print('FILE:', x)
```


カレントディレクトリ以下のファイルを列挙する（再帰あり）
----

### os.listdir でがんばる方法

`os.listdir()` で列挙した要素がディレクトリだった場合に、そのディレクトリに対しても `os.listdir()` を呼び出すようにすれば、ディレクトリ内のすべてのファイルを再帰的に列挙することができます。

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

取得したパスを絶対パスで表示したい場合は、`os.path.abspath()` で変換してやれば OK です。

```python
print(os.path.abspath(path))  # 絶対パスに変換して出力
```

### os.walk で簡単にファイル列挙

[os.walk()](https://docs.python.org/3/library/os.html#os.walk) を使用すると、再帰的なファイル列挙をさらに簡単に記述できます。
下記は 1 つのループ処理で記述していますが、下位のディレクトリにあるファイルまですべて列挙してくれます。
**`os.walk()` はデフォルトで再帰的にディレクトリを辿ってくれる** ということです。

```python
import os

for dirpath, dirs, files in os.walk('.'):
    print(dirpath)
    print(dirs)
    print(files)
    print('-------------')
```

`os.walk` によって取得されるタプル要素には、それぞれ下記のような情報が格納されています。

* `dirpath` -- 現在検索中のディレクトリパス
* `dirnames` -- dirpath のディレクトリに含まれているディレクトリのリスト
* `filenames` -- dirpath のディレクトリに含まれているファイルのリスト

つまり、1 つのディレクトリごとに上記のタプルが返されながらループが進んでいきます。
例えば、カレントディレクトリ以下のすべてのファイルのパスを表示するには次のようにします。

```python
import os

for dirpath, dirs, files in os.walk('.'):
    for f in files:
        print(os.path.join(dirpath, f))
```

**特定の拡張子のファイルだけ列挙したい場合** は、ファイル名の末尾を `str.endswith()` でチェックすればよいでしょう。

```python
for f in files:
    if f.lower().endswith('.png'):
        # ...
```

`endswith()` にはタプルを渡せるので、複数の拡張子を OR 条件で列挙することもできます。

```python
for f in files:
    if f.lower().endswith(('.png', '.jpg', '.svg')):
        # ...
```



[os.path.splitext()](https://docs.python.org/3/library/os.path.html#os.path.splitext) を使用すれば、ファイル名をベースネームと拡張子に分離することができますが、拡張子のチェックだけであれば、`endswith()` を使った方がシンプルです。


### グロブでファイル列挙する方法 (glob.glob)

ディレクトリ内の、特定の拡張子を持つファイルをすべて列挙したいときは、[glob モジュール](https://docs.python.org/3/library/glob.html) の **`glob.glob()`** 関数を使うのが一番簡単です。

次のようにすると、カレントディレクト以下の `.png` ファイルをすべて列挙できます。

#### .png 拡張子を持つファイルを列挙

```python
import os
import glob

for x in glob.glob('**/*.png', recursive=True):
    print(x)
```

ただし、グロブでは複数の拡張子をまとめて処理できないので、複数の拡張子のファイルを列挙したい場合は、その数だけ `glob.glob()` を呼び出さなければいけません。

#### .png あるいは .jpg 拡張子を持つファイルを列挙

```python
import os
import glob

PATTERNS = ('**/*.png', '**/*.jpg')

def multi_glob(patterns):
    files = []
    for p in PATTERNS:
        files.extend(glob.glob(p, recursive=True))
    return files

for f in multi_glob(PATTERNS):
    print(f)
```

こうするくらいであれば、`os.walk()` を使った方が速いかもしれません。

