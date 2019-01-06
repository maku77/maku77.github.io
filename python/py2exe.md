---
title: "Python スクリプトを Windows で動く実行ファイル形式に変換する"
date: "2007-04-10"
---

py2exe というツールを使用すると、Python スクリプトから Windows 上で実行可能な exe 形式のファイルを作成することができます。

- [py2exe.org](http://www.py2exe.org/)


py2exe の基本的な使い方
----

下記は 2007 年 4 月現在のバージョン py2exe 0.6.6 (December 31, 2006) で確認しています。

### コンソール用のスクリプトの場合

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


### wxPython などの GUI アプリケーションの場合

#### setup.py

```python
from distutils.core import setup
import py2exe
setup(windows=['test.py'])
```

GUI を持つアプリケーションで、実行時にコマンドプロンプトを表示したくない場合は、`setup` メソッドのパラメータとして、`console` の代わりに `windows` を使用します。

