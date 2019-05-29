---
title: "Python でモジュール／パッケージを作成する"
date: "2016-12-16"
---

モジュールとパッケージ
----

Python では、再利用可能な関数などを集めたスクリプトファイルのことを**モジュール**と呼びます。
さらに、同系列のモジュールを集めたものを**パッケージ**と呼びます。

- **モジュール** -- 再利用可能なスクリプト (.py)
- **パッケージ** -- 上記のモジュールをディレクトリに集めたもの

モジュールやパッケージを作成しておくと、他のファイルから `import` して使用することができます。

コラム: このあたりは、[Node.js](/nodejs/) も同様の命名をしています。


モジュールを作成する
----

下記は、簡単な関数（`add` と `sub`）を提供する `mymath` モジュールを定義する例です。
といっても、単純に関数を定義するだけです。
モジュールは、**モジュール名.py** というファイル名で作成する必要があります。

#### mymath.py（モジュールの例）

```python
def add(a, b):
    return a + b

def sub(a, b):
    return a - b
```

上記の `mymath.py` で定義されている関数を使用するには、Python 標準のモジュールと同様に、`import mymath` という形でインポートします。

#### main.py（モジュールを使う側）

```python
import mymath

if __name__ == '__main__':
    print(mymath.add(1, 2))
    print(mymath.sub(1, 2))
```


パッケージを作成する
----

モジュールをディレクトリまとめると、それはパッケージと呼ばれるようになります。
下記の例では、`mylib` というディレクトリに `mymath.py` を格納することで、`mylib` パッケージを作成しています。

```
+-- main.py
+-- mylib/
    +-- mymath.py
```

パッケージ内のモジュールは、通常のモジュールと同様にコーディングします。

#### mylib/mymath.py

```python
def add(a, b): return a + b
def sub(a, b): return a - b
```

`mylib` パッケージ内のモジュールを使用するには、下記のように `mylib.mymath` という形でインポートします。

#### main.py

```python
import mylib.mymath

if __name__ == '__main__':
    print(mylib.mymath.add(1, 2))
    print(mylib.mymath.sub(1, 2))
```

毎回 `mylib` というプレフィックスをつけてアクセスするのが面倒な場合は、`from mylib import mymath` という形でインポートします。

```python
from mylib import mymath

if __name__ == '__main__':
    print(mymath.add(1, 2))
    print(mymath.sub(1, 2))
```

応用
----

### モジュールの検索パス

`import mymodule` とすると、下記の順で `mymodule.py` が検索され、最初に見つかったモジュールがインポートされます。

1. **ビルトイン・モジュール**の `mymodule`
2. `sys.path` に登録されているディレクトリ内の `mymodule.py`（デフォルトでは下記のディレクトリが登録されます）
  * 実行したスクリプトと**同じディレクトリ**
  * 環境変数 [PYTHONPATH](https://docs.python.org/3/using/cmdline.html#envvar-PYTHONPATH) に指定したディレクトリ
  * Python インストーラに応じたデフォルトディレクトリ

同じディレクトリにあるモジュールよりも、ビルトイン・モジュールが先に検索されるところがポイントですね。
このような優先順位になっていることで、ユーザモジュールによって既存プログラムの動作が破壊されることを防いでいます。

### パッケージの初期化ファイル (\_\_init.py\_\_)

Python 3.3 より前のバージョンでは、パッケージディレクトリに `__init__.py` というファイルを置かなければ、そのディレクトリをパッケージとして認識させることができませんでした（`ImportError: No module named ...` というエラーが発生する）。
Python 3.3 以降では単純にディレクトリ内に .py ファイルを放り込んでおけば、そのディレクトリをパッケージとして扱うことができるようになっています（詳細は [PEP 420 -- Implicit Namespace Packages](https://www.python.org/dev/peps/pep-0420/) を参照）。

パッケージディレクトリに置かれた `__init__.py` は、そのパッケージ（あるいはその中のモジュール）をインポートしたときに実行されます。
例えば、`__init__.py` の中で各モジュールをインポートするように記述しておけば、パッケージを利用するときに個々のモジュールを指定してインポートする必要がなくなります（ただし、必要のないモジュールまでデフォルトでインポートしてしまうのは効率が悪いので、オススメはできません）。

#### ディレクトリ構成

```
+-- main.py
+-- mylib/  （パッケージ）
    +-- __init__.py
    +-- mymod1.py  （モジュール）
    +-- mymod2.py  （モジュール）
```

#### mylib/\_\_init\_\_.py

```python
from . import mymod1
from . import mymod2
```

#### main.py

```python
import mylib   # これだけで mylib.mymod1 と mylib.mymod2 がインポートされる

if __name__ == '__main__':
    mylib.mymod1.foo()
    mylib.mymod2.bar()
```

### 別の階層のモジュールをインポートする

例えば、下記のような２つのサブパッケージを含むパッケージ `mylib` があったとします。

```
+-- mylib/
    +-- sub1/
    |   +-- mod1.py
    +-- sub2/
        +-- mod2.py
```

`sub2/mod2.py` の中から、`sub1/mod1.py` をインポートするには下記のように記述します。

#### sub2/mod2.py

```python
from ..sub1 import mod1

mod1.hello()
```

パッケージを指定するときに、ドットを２つ付けることで上位階層のパッケージを参照することを示しています。


### モジュールのドキュメントを記述する

モジュールの先頭に `""" ... """` という形式のドキュメンテーションコメントを記述しておくと、そのモジュールのドキュメントして認識されます。

#### fibo.py

```python
""" Fibonacci numbers module. """

def fib(n):
    """ Prints fibonacci series up to n. """
    a, b = 0, 1
    while b < n:
        print(b, end=' ')
        a, b = b, a+b
```

このように記述したドキュメントは、`pydoc` コマンドなどで参照することができます。

```
$ pydoc fibo
Help on module fibo:

NAME
    fibo - Fibonacci numbers module.

FUNCTIONS
    fib(n)
        Prints fibonacci series up to n.

FILE
    D:\y\sandbox\python\fibo.py
```

パッケージのドキュメントを記述したい場合は、そのパッケージの `__init__.py` の先頭にドキュメンテーションコメントを記述します。

#### mylib/\_\_init\_\_.py

```python
""" My first package. """
```

パッケージのドキュメントを参照すると、パッケージに含まれているモジュールのリスト (`PACKAGE CONTENTS`) も表示してくれます。

```
$ pydoc mylib
Help on package mylib:

NAME
    mylib - My first package.

PACKAGE CONTENTS
    mymod1
    mymod2
```

パッケージ内の個々のモジュールのドキュメントを参照したい場合は、`pydoc mylib.mymod1` のように、パッケージ名の後ろにドットで繋げてモジュールを指定すれば OK です。


参考
----

* [Modules — Python 3 documentation](https://docs.python.org/3/tutorial/modules.html)

