---
title: "Python で定数を定義する"
date: "2018-01-08"
description: "Python には、C/C++ の const のような、定数を定義するためのキーワードは用意されていません。"
---

Python で定数（として扱う変数）を定義するときは、定数名をすべて大文字（＋アンダースコア）で構成するのが慣例となっています。
このあたりの標準的な命名規則は PEP8 に記載されています。

#### 参考: [PEP 8 -- Style Guide for Python Code｜Python.org](https://www.python.org/dev/peps/pep-0008/#constants)

> Constants are usually defined on a module level and written in all capital letters with underscores separating words. Examples include MAX_OVERFLOW and TOTAL.

#### 例: ファイル内の定数

~~~ python
MAX_FILE_NUM = 20

print(MAX_FILE_NUM)
~~~

クラス定数も下記のようにして同様に定義することができます。

#### 例: クラス定数

~~~ python
class MyClass:
    NAME = 'maku'
    AGE = 100

    def show_info(self):
        print(self.NAME)
        print(self.AGE)
~~~


定数を扱うためのモジュール (const.py) を作成する
----

下記のような、定数を扱うためのクラスが [Python Recipes で紹介されています](http://code.activestate.com/recipes/414140-constant-types-in-python/)（Python3 の構文に合わせて若干書き換えてます）。

#### const.py

~~~ python
"""
Constant types in Python.
"""

class _const:
    class ConstError(TypeError):
        pass

    def __setattr__(self, name, value):
        if name in self.__dict__:
            raise self.ConstError("Can't rebind const (%s)" % name)
        self.__dict__[name] = value

import sys
sys.modules[__name__]=_const()
~~~

元ネタは書籍の『Python Cookbook』ですね。
この定数モジュールは下記のように定数の入れ物として使用します。

#### sample.py

~~~ python
import const

const.FOO = 100
const.BAR = 'Hello'
~~~

下記のように、同じ名前の定数に再代入しようとすると、ランタイムエラー (独自定義の const.ConstError) が発生して、プログラムが停止します。

~~~ python
const.FOO = 100
const.FOO = 200  # const.ConstError: Can't rebind const (HOGE)
~~~

