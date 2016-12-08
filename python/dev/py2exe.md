---
title: Python スクリプトを Windows の実行ファイル (.exe) に変換する (py2exe)
created: 2007-04-10
---

py2exe を使用すると、Python のスクリプトから Windows 上で実行可能な .exe ファイルを作成することができます。

- [py2exe.org](http://www.py2exe.org/)


基本的な使い方
----

変換対象のスクリプト `hello.py` と、変換設定ファイル `setup.py` を用意します。

#### hello.py

```python
print 'Hello'
```

#### setup.py

```python
from distutils.core import setup
import py2exe
setup(console=['test.py'])
```

コマンドラインから、

```
C:\> python setup.py py2exe
```

と実行すると、`dist` ディレクトリに `hello.exe` と、必要なライブラリが生成されます。
配布するときは、`dist` ディレクトリの中身をすべてまとめて配布します。


wxPython などの GUI アプリケーションの場合
----

#### setup.py

```python
from distutils.core import setup
import py2exe
setup(windows=['test.py'])
```

GUI を持つアプリケーションで、実行時にコマンドプロンプトを表示したくない場合は、`setup` メソッドのパラメータとして、`console` の代わりに `windows` を指定します。

