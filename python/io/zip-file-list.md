---
title: ZIP/JAR ファイル内のファイルリストを取得する
date: "2016-12-06"
---

Python に標準搭載されている [zipfile モジュール](https://docs.python.org/3/library/zipfile.html) を使用すると、ZIP ファイルや JAR ファイルの読み書きを手軽に行うことができます。

ZIP ファイル内のファイル一覧を取得する
----

下記のサンプルでは、指定した ZIP ファイル内のファイルリストとその（展開後の）サイズを出力します。

#### dump_zip.py

```python
import zipfile

with zipfile.ZipFile('sample.zip', 'r') as zf:
    for info in zf.infolist():
        print(info.filename + ' (' + str(info.file_size) + ')')
```

#### 実行結果

```
$ python dump_zip.py
data/ (0)
data/sample1.dat (4560)
data/sample2.dat (2284)
data/sample3.dat (5712)
README.txt (1417)
```

ファイル名でソートして出力したい場合は、下記のようにいったんリストにしてソートしてしまえば OK。

```python
with zipfile.ZipFile('sample.zip', 'r') as zf:
    arr = list(zf.infolist())
    arr.sort(key=lambda x: x.filename)
    for info in arr:
        print(info.filename + ' (' + str(info.file_size) + ')')
```

JAR ファイル内のクラス一覧を取得する
----

JAR ファイルもフォーマット上は ZIP ファイルと同じ構造なので、ZIP ファイルを扱うのと同様に処理することができます。
ここでは、JAR ファイル内の .class ファイルのみを処理するために、ファイルの拡張子が .class であるかを確認しながらループ処理しています。

#### dump_classes.py

```python
import zipfile
import os
import sys

def iter_classes_in_jar(jar_name):
    with zipfile.ZipFile(jar_name, 'r') as zf:
        for info in zf.infolist():
            classname, ext = os.path.splitext(info.filename)
            if ext != '.class':
                continue
            yield classname

if __name__ == '__main__':
    jarname = sys.argv[1]
    # List all classes
    for clazz in iter_classes_in_jar(jarname):
        print(clazz)
```

#### 実行結果

```
$ python dump_classes.py app.jar
com/example/myapp/Bar
com/example/myapp/Foo
com/example/myapp/Main
...
```

