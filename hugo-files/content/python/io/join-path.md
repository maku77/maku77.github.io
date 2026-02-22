---
title: "Pythonメモ: パスを結合する (join)"
url: "p/qogtc4k/"
date: "2012-06-04"
tags: ["python"]
aliases: /python/io/join-path.html
---

`os.path.join` によるパス結合
----

`os.path` モジュールの `join` メソッドを使用して、複数のパス文字列を結合することができます。

```python
import os.path

path = os.path.join('/foo', 'file.txt')        #=> /foo/file.txt
path = os.path.join('foo', 'bar', 'file.txt')  #=> foo/bar/file.txt
path = os.path.join('/aaa/bbb', 'ccc/ddd')     #=> /aaa/bbb/ccc/ddd
path = os.path.join('/aaa/bbb', '/ccc')        #=> /ccc （要注意: 第 2 引数がフルパスの場合、結合されない）
```

ディレクトリセパレータとして使用される文字は `os.sep` で定義されており、OS ごとに異なります（Linux 系 OS の場合は `/` です）。
`join` メソッドが内部で使用するので、通常は意識する必要はありません。

{{< code lang="python" title="応用例: スクリプトと同じディレクトリにあるファイルの絶対パスを作成する" >}}
basedir = os.path.abspath(os.path.dirname(__file__))
path = os.path.join(basedir, 'file.txt')
{{< /code >}}


末尾のディレクトリセパレータの扱い
----

第 1 パラメータで指定するパスの末尾には、ディレクトリセパレータが付いていても付いていなくても構いません（同じ結果になります）。

```python
os.path.join('foo', 'bar')   #=> 'foo/bar'
os.path.join('foo/', 'bar')  #=> 'foo/bar'
```

結合結果の末尾に必ずディレクトリセパレータが付いていることを保証したい場合は、最後のパラメータに空文字を設定します。

```python
os.path.join('aaa', 'bbb', 'ccc', '')   #=> 'aaa/bbb/ccc/'
os.path.join('aaa', 'bbb', 'ccc/', '')  #=> 'aaa/bbb/ccc/'
```
