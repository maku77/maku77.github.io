---
title: "Python スクリプトを Windows の実行ファイル (.exe) に変換する (py2exe)"
url: "p/kyj2izf/"
date: "2007-04-10"
tags: ["Python", "Windows"]
aliases: /python/dev/py2exe.html
---

[py2exe](http://www.py2exe.org/) を使用すると、Python のスクリプトから Windows 上で実行可能な `.exe` ファイルを作成することができます。

基本的な使い方
----

変換対象のスクリプト `hello.py` と、変換設定ファイル `setup.py` を用意します。

{{< code lang="python" title="hello.py" >}}
print 'Hello'
{{< /code >}}

{{< code lang="python" title="setup.py" >}}
from distutils.core import setup
import py2exe
setup(console=['test.py'])
{{< /code >}}

コマンドラインから、次のように実行すると、`dist` ディレクトリに `hello.exe` と、必要なライブラリが生成されます。

```
C:\> python setup.py py2exe
```

配布するときは、`dist` ディレクトリの中身をすべてまとめて配布します。


（応用）wxPython などの GUI アプリケーションの場合
----

{{< code lang="python" title="setup.py" >}}
from distutils.core import setup
import py2exe
setup(windows=['test.py'])
{{< /code >}}

GUI を持つアプリケーションで、実行時にコマンドプロンプトを表示したくない場合は、`setup` メソッドのパラメータとして、`console` の代わりに __`windows`__ を指定します。

