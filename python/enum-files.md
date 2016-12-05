---
title: ディレクトリ内のファイルを列挙する
created: 2009-01-06
---

カレントディレクトリ以下のファイルを列挙（再帰なし）
====

`os.listdir()` を使うと、指定したディレクトリ内のファイル、ディレクトリをリストで取得できます。
カレントディレクトリや親ディレクトリを表す '.' や '..' は、列挙の対象に含まれません。
列挙されたものが、ディレクトリかファイルかを調べたい場合は、`os.path.isfile(path)` が使えます。

#### カレントディレクトリ内のディレクトリとファイルを列挙（一階層のみ）

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

### os.listdir でがんばる方法

`os.listdir` で列挙した要素がディレクトリだった場合に、そのディレクトリに対しても `os.listdir` を呼び出すようにすれば、ディレクトリ内のすべてのファイルを再帰的に列挙することができます。

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

取得したパスを絶対パスで表示したい場合は、`os.path.abspath` で変換してやれば OK です。

```python
print(os.path.abspath(path))  # 絶対パスに変換して出力
```

### os.walk で簡単にファイル列挙

`os.walk()` を使用すると、再帰的なファイル列挙をさらに簡単に記述できます。
下記は１つのループ処理で記述していますが、下位のディレクトリにあるファイルまですべて列挙してくれます。

```python
for dirpath, dirs, files in os.walk('.'):
    print(dirpath)
    print(dirs)
    print(files)
    print('-------------')
```

`os.walk` によって取得されるタプル要素には、それぞれ下記のような情報が格納されています。

* `dirpath` -- 現在検索中のディレクトリパス
* `dirnames` -- そのディレクトリに含まれているディレクトリのリスト
* `filenames` -- そのディレクトリに含まれているファイルのリスト

例えば、カレントディレクトリ以下のすべてのファイルのパスを表示するには次のようにすればよいでしょう。

```python
for dirpath, dirs, files in os.walk('.'):
    for f in files:
        print(os.path.join(dirpath, f))
```


参考
====

* [os.walk — Python 3 documentation](https://docs.python.org/3/library/os.html#os.walk)

