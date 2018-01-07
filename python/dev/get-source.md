---
title: 関数／メソッドのソースコードを確認する (inspect)
date: "2016-12-05"
---

関数のコードを表示する
----

Python のプログラムを作成しているときに、ある関数のソースコードを確認したくなったときは、`inspect` モジュールの `getsource` 関数を使用して簡単に調べることができます。

#### 例: os.path.abspath の実装コードを確認する

```python
>>> import inspect
>>> import os
>>> print(inspect.getsource(os.path.abspath))
    def abspath(path):
        """Return the absolute version of a path."""

        if path: # Empty path must return current working directory.
            try:
                path = _getfullpathname(path)
            except OSError:
                pass # Bad path - return unchanged.
        elif isinstance(path, bytes):
            path = os.getcwdb()
        else:
            path = os.getcwd()
        return normpath(path)
```


関数が定義されているファイル全体を表示する
----

`inspect.getsource` 関数でモジュール名を指定すれば、そのモジュール全体のコードを取得することができるのですが、ハイライト表示なしで長いコードが表示されると非常に読みにくいです。
ここでは、モジュールが定義されているファイルそのものをテキストエディタで開く方法を紹介します。

`inspect.getfile` 関数を使用すると、指定したクラス、関数、オブジェクトなどが定義されているファイルのパスを取得することができます。

#### 例: os モジュールのソースコードのパスを調べる

```python
>>> import inspect
>>> import os
>>> inspect.getfile(os)
'C:\\Python35\\lib\\os.py'
```

あとは、そのファイルを任意のエディタで開けば OK です。
エディタの実行ファイルにパスが通っているのであれば、`os.system` 関数を使って、そのエディタでさくっと開くことができます。

#### 例: os モジュールのソースコードを gvim エディタで開く

```python
>>> import inspect
>>> import os
>>> os.system('gvim ' + inspect.getfile(os))
```

