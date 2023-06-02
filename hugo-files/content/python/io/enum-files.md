---
title: "Python でディレクトリ内のファイルを列挙する (os.listdir, os.walk, glob)"
url: "p/thhwr4b/"
date: "2009-01-06"
lastmode: "2023-06-02"
tags: ["Python"]
changes:
  - 2023-06-02: 記述内容を洗練
aliases: /python/io/enum-files.html
---

ファイルを列挙する（再帰なし）
----

Python の __`os.listdir`__ 関数を使うと、指定したディレクトリ内のファイルやディレクトリのパスをリストで取得できます。
カレントディレクトリや親ディレクトリを表す `.` や `..` は、列挙の対象に含まれません。
列挙されたパスが、ディレクトリかどうかを調べたいときは `os.path.isdir(path)`、ファイルかどうかを調べたいときは `os.path.isfile(path)` で確認できます。

{{< code lang="python" title="カレントディレクトリ内のディレクトリとファイルを列挙（1 階層のみ）" >}}
import os

for x in os.listdir('.'):
    if os.path.isdir(x):
        print('DIR:', x)
    else:
        print('FILE:', x)
{{< /code >}}


ファイルを列挙する（再帰あり）
----

### os.listdir で列挙する方法

`os.listdir` 関数で列挙した要素がディレクトリだった場合に、そのディレクトリに対しても `os.listdir` 関数を呼び出すようにすれば、ディレクトリ内のすべてのファイルを再帰的に列挙することができます。

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
        # print(os.path.abspath(path))  # 絶対パスで出力する場合
```

### os.walk で列挙する方法 {#os-walk}

[os.walk 関数](https://docs.python.org/3/library/os.html#os.walk) を使用すると、再帰的なファイル列挙をさらに簡単に記述できます。
下記は 1 つのループ処理で記述していますが、下位のディレクトリにあるファイルまですべて列挙してくれます。
__`os.walk` はデフォルトで再帰的にディレクトリを辿ってくれる__ ということです。

{{< code lang="python" title="カレントディレクトリ以下、全ディレクトリの内容を表示" >}}
import os

for dirpath, dirs, files in os.walk("."):
    print("-" * 60)
    print(f"{dirpath} ディレクトリの情報:")
    print(f"dirs = {dirs}")
    print(f"files = {files}")
{{< /code >}}

`os.walk` によって取得されるタプル要素には、それぞれ以下のような情報が格納されています。

- __`dirpath`__ ... 現在検索中のディレクトリパス
- __`dirs`__ ... `dirpath` のディレクトリに含まれているディレクトリのリスト
- __`files`__ ... `dirpath` のディレクトリに含まれているファイルのリスト

つまり、ディレクトリごとに上記のタプルが返されながらループが進んでいきます。
カレントディレクトリ以下のすべてのファイルのパスだけを列挙するには次のように `files`（タプルの 3 番目の要素）の内容だけ出力すれば OK です。
起点となるディレクトリのパス (`dirpath`) と `join` してやれば、そこからの相対パスを構築できます。

{{< code lang="python" title="カレントディレクトリ以下のすべてのファイルの相対パスを表示" >}}
import os

for dirpath, dirs, files in os.walk('.'):
    for f in files:
        print(os.path.join(dirpath, f))
{{< /code >}}

{{< code title="出力例" >}}
./a.txt
./b.txt
./foo/c.txt
./foo/d.txt
./foo/bar/e.txt
./foo/bar/f.txt
./hoge/g.txt
{{< /code >}}

__特定の拡張子のファイルだけ列挙したい場合__ は、ファイル名の末尾を `str.endswith` メソッドでチェックすればよいでしょう。

{{< code lang="python" title=".png ファイルのみを列挙" hl_lines="2" >}}
for f in files:
    if f.lower().endswith('.png'):
        print(os.path.join(dirpath, f))
{{< /code >}}

`endswith()` にはタプルを渡せるので、複数の拡張子を OR 条件で列挙することもできます。

{{< code lang="python" title=".png、.jpg、.svg ファイルを列挙" hl_lines="2" >}}
for f in files:
    if f.lower().endswith(('.png', '.jpg', '.svg')):
        print(os.path.join(dirpath, f))
{{< /code >}}

[os.path.splitext()](https://docs.python.org/3/library/os.path.html#os.path.splitext) を使用すれば、ファイル名をベースネームと拡張子に分離することができますが、拡張子のチェックだけであれば、`endswith()` を使った方がシンプルです。

### glob で列挙する方法

ディレクトリ内の、特定の拡張子を持つファイルをすべて列挙したいときは、[glob モジュール](https://docs.python.org/3/library/glob.html) の __`glob.glob`__ 関数を使うのが一番簡単です。
次のようにすると、カレントディレクト以下の `.png` ファイルをすべて列挙できます。

{{< code lang="python" title=".png 拡張子を持つファイルを列挙" >}}
import os
import glob

for x in glob.glob('**/*.png', recursive=True):
    print(x)
{{< /code >}}

ただし、__グロブでは複数の拡張子をまとめて処理できない__ ので、複数の拡張子のファイルを列挙したい場合は、その数だけ `glob.glob()` を呼び出さなければいけません。

{{< code lang="python" title=".png あるいは .jpg 拡張子を持つファイルを列挙" >}}
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
{{< /code >}}

このような実装をするのであれば、[os.walk を使って列挙](#os-walk)した方が早いかもしれません。

- 参考: [Python で指定した拡張子、名前のファイルを列挙する](/p/6vpyp4z/)

