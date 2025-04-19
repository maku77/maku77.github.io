---
title: "Python で定数を定義する (typing.Final)"
url: "p/37hwzxi/"
date: "2018-01-08"
tags: ["Python"]
aliases: /python/syntax/const.html
---

Python 3.8 以降では、**`typing.Final`** による型アノテーションを使って、再代入できない変数（定数）であることを表現できます（参考: [PEP 591](https://peps.python.org/pep-0591/)）。
定数名は **すべて大文字（+ アンダースコア）** で構成するのが慣例となっています。
このあたりの標準的な命名規則は PEP8 に記載されています。

- 参考: [PEP 8 -- Style Guide for Python Code｜Python.org](https://www.python.org/dev/peps/pep-0008/#constants)
  > Constants are usually defined on a module level and written in **all capital letters with underscores** separating words. Examples include MAX_OVERFLOW and TOTAL.

{{< code lang="python" title="例: ファイル内（モジュールレベル）の定数" >}}
from typing import Final

MAX_FILE_NUM: Final = 10
ORIGIN_COORD: Final = (0, 0)
DEFAULT_NAME: Final = "Player"
{{< /code >}}

クラス定数も下記のようにして同様に定義することができます。

{{< code lang="python" title="例: クラス定数" hl_lines="4-5" >}}
from typing import Final

class Player:
    DEFAULT_NAME: Final = "Player"
    DEFAULT_LIFE: Final = 0

    def __init__(self, name: str = DEFAULT_NAME, life: int = DEFAULT_LIFE) -> None:
        self.name = name
        self.life = life
{{< /code >}}

`Final` アノテーションは、**Mypy などの型チェッカーが再代入を検出するためのヒントとして使用するものです**。
例えば、Visual Studio Code に [Mypy 拡張](https://marketplace.visualstudio.com/items?itemName=ms-python.mypy-type-checker) をインストールしておくと、コーディング中に不正な再代入を検出してくれるようになります。
ただし、**コードの実行時には、`Final` を付けた変数に再代入できてしまうことに注意してください**。


コラム: 定数を扱うためのモジュール (const.py) を作成する
----

下記のような、定数を扱うためのクラスが [Python Recipes で紹介されています](https://code.activestate.com/recipes/414140-constant-types-in-python/)（Python3 の構文に合わせて若干書き換えてます）。

{{< code lang="python" title="const.py" >}}
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
sys.modules[__name__] = _const()
{{< /code >}}

元ネタは書籍の『Python Cookbook』ですね。
この定数モジュールは下記のように定数の入れ物として使用します。

{{< code lang="python" title="sample.py" >}}
import const

const.FOO = 100
const.BAR = 'Hello'
{{< /code >}}

下記のように、同じ名前の定数に再代入しようとすると、ランタイムエラー (独自定義の `const.ConstError`) が発生して、プログラムが停止します。

```python
const.HOGE = 100
const.HOGE = 200  # const.ConstError: Can't rebind const (HOGE)
```

