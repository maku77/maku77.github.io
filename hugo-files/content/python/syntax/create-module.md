---
title: "Python でモジュールやパッケージを作成する"
url: "p/n4n5m3i/"
date: "2016-12-16"
lastmod: "2023-05-30"
tags: ["Python"]
changes:
  - 2023-05-30: 記事を洗練
aliases: /python/env/create-module.html
---

{{% private %}}
- [Modules — Python 3 documentation](https://docs.python.org/3/tutorial/modules.html)
{{% /private %}}

モジュールとパッケージ
----

Python では、再利用可能な関数などを集めたスクリプトファイルのことを**モジュール**と呼びます。
さらに、同系列のモジュールを集めたものを**パッケージ**と呼びます。

- __モジュール__ ... 再利用可能なスクリプト (.py)
- __パッケージ__ ... 上記のモジュールをディレクトリに集めたもの

モジュールやパッケージを作成しておくと、他のファイルから `import` して使用することができます。

{{% note title="Node.js も同じ名前" %}}
モジュールやパッケージといった名称は、言語によって使われ方が様々ですが、[Node.js](/nodejs/) に関してはほぼ同じ意味で使われています。
Node.js では、`.js` ファイルのことをモジュール、それらをまとめたものをパッケージと呼んでいます。
{{% /note %}}


モジュールを作成する
----

下記は、簡単な関数（`add` と `sub`）を提供する `mymath` モジュールを定義する例です。
といっても、単純に関数を定義するだけです。
モジュールは、__`モジュール名.py`__ というファイル名で作成する必要があります。

{{< code lang="python" title="mymath.py（モジュールの実装例）" >}}
def add(a, b):
    """Return the sum of a and b."""
    return a + b

def sub(a, b):
    """Subtract b from a."""
    return a - b
{{< /code >}}

上記の `mymath.py` で定義されている関数を使用するには、Python 標準のモジュールと同様に、`import mymath` という形でインポートします。

{{< code lang="python" title="main.py（モジュールの使用例）" >}}
import mymath

if __name__ == '__main__':
    print(mymath.add(1, 2))
    print(mymath.sub(1, 2))
{{< /code >}}


パッケージを作成する
----

複数のモジュール (`*.py`) をディレクトリにまとめたものを __パッケージ__ と呼びます。
下記の例では、`mylib` ディレクトリに `mymath.py` を格納することで、`mylib` パッケージを作成しています。
ちなみに、パッケージ名（ディレクトリ名）には、__アンダースコア (`_`) は含めるべきではない__ とされています。
このあたりの命名規則に関しては [Python のコーディングスタイル](/p/pyk3j2h/) を参照してください。

```
+-- main.py
+-- mylib/
    +-- mymath.py
```

パッケージ内のモジュールの実装方法は、通常のモジュールの場合と変わりません。

{{< code lang="python" title="mylib/mymath.py" >}}
def add(a, b): return a + b
def sub(a, b): return a - b
{{< /code >}}

`mylib` パッケージ内の `mymath` モジュールを使用するには、次のように __`mylib.mymath`__ という形でインポートします。

{{< code lang="python" title="main.py" >}}
import mylib.mymath

if __name__ == '__main__':
    print(mylib.mymath.add(1, 2))
    print(mylib.mymath.sub(1, 2))
{{< /code >}}

このようにパッケージ名とモジュール名を繋げた名前でインポートした場合、関数を呼び出すときもその名前をプレフィックスに付けて呼び出す必要があります（例: `mylib.mymath.add(1, 2)`）。
Python の部分インポート (partial import) の構文 (__`from mylib import mymath`__) を使うと、パッケージ名を省略して呼び出せるようになります。

```python
from mylib import mymath

if __name__ == '__main__':
    print(mymath.add(1, 2))
    print(mymath.sub(1, 2))
```

モジュール内の関数などを呼び出すときは、`import` の後ろに記述したシンボル名をプレフィックスとして付けると覚えておけば OK です（例えば上記の場合は、`import mymath` としているので、`add` 関数の呼び出し時は `mymath` プレフィックスを付けて `mymath.add` とします）。


モジュール内の特定メンバーだけをインポートする
----

前述の例では、モジュール単位でインポートしましたが、モジュール内の特定のメンバー（関数、クラス、定数など）を指定してインポートすることもできます。
次の例では、`mymath` モジュール内の、`add` 関数のみをインポートしています。
関数の呼び出し時に、モジュール名のプレフィックスを付ける必要はありません。

{{< code lang="python" title="メンバー単位のインポート" >}}
from mymath import add

if __name__ == '__main__':
    print(add(1, 2))
{{< /code >}}

パッケージ内のモジュール内の関数をインポートする場合も、ほぼ同じ指定方法でいけます。

```python
from mylib.mymath import add

if __name__ == '__main__':
    print(add(1, 2))
```


別名を付ける (as)
----

インポートするモジュールや関数の名前が長すぎて扱いにくい場合や、名前の衝突が起きてしまう場合は、__`as`__ キーワードを使って別名を付けることができます。

{{< code lang="python" title="long_long_module.py をインポートするとき" >}}
import long_long_module as llm

llm.hello()
{{< /code >}}

{{< code lang="python" title="mypackage/long_long_module.py をインポートするとき" >}}
from mypackage import long_long_module as llm

llm.hello()
{{< /code >}}


パッケージ内のモジュールから別のモジュールをインポートするときの注意
----

```
+-- main.py
+-- libs/
    +-- mod1.py
    +-- mod2.py
```

このようなディレクトリ構成で `libs` パッケージを作っていて、`mod1.py` から `mod2.py` をインポートしたいときは、次のように __`from .`__ を付けて、__相対パスインポート__ の形で記述する必要があります（逆に、ドット (`.`) を使わないインポートを絶対パスインポートと呼びます）。

{{< code lang="python" title="libs/mod1.py（パッケージ内モジュールからのインポート）" >}}
from . import mod2

def hello():
    mod2.hello()
{{< /code >}}

{{< code lang="python" title="libs/mod2.py" >}}
def hello():
    print('hello')
{{< /code >}}

上記の `from . import mod2` となっているところを、単純に `import mod2` と書いてしまうと、`main.py` から次のように `mod1` 経由で読み込もうとしたときに __`ModuleNotFoundError: No module named 'mod2'`__ エラーになってしまいます。

{{< code lang="python" title="main.py" >}}
from libs import mod1

mod1.hello()
{{< /code >}}

{{% note title="なぜ相対パスで import しないといけないのか？" %}}
パッケージ内モジュールからの絶対パスインポートが失敗する原因は、[Python の検索パスの仕組み (sys.path)](/p/o4m4jyg/) にあります。
`python` コマンドで何らかのスクリプト (`.py`) を起動すると、そのファイルが存在するディレクトリがモジュールの検索パスに追加されます。
上記の例で言うと、`main.py` と同じディレクトリにあるモジュールであれば絶対パスでインポートできるようになります。
一方で、`libs/mod1.py` が処理されているときも、`libs` ディレクトリは検索パスに含まれていないため、`import mod2` という絶対パスインポートはエラーになってしまいます（`main.py` と同じディレクトリの `mod2.py` を探そうとしてしまう）。
もちろん、`main.py` からの絶対パスで `from libs import mod2` と記述することは可能ですが、それよりは相対パスで `from . import mod2` と記述した方が分かりやすいでしょう。
{{% /note %}}


別の階層のモジュールをインポートする
----

同じパッケージ階層のモジュールをインポートするときは、相対パスで `from . import モジュール名` としましたが、1 つ上の階層にあるモジュールをインポートしたいときは、ドットの数を 1 つ増やして __`from .. import モジュール名`__ とします。
2 つ上の階層のモジュールをインポートしたければ、さらにドットを増やして __`from ... import モジュール名`__ とします。

- 同じ階層のモジュール（内のメンバー）をインポートする場合
  - `from . import mod`
  - `from .mod import hello`
- 1 つ上の階層のモジュール（内のメンバー）をインポートする場合
  - `from .. import mod`
  - `from ..mod import hello`
- 2 つ上の階層のモジュール（内のメンバー）をインポートする場合
  - `from ... import mod`
  - `from ...mod import hello`

例えば、下記のような 2 つのサブパッケージを含むパッケージ `mylib` があったとします。

```
+-- mylib/
    +-- sub1/
    |   +-- mod1.py
    +-- sub2/
        +-- mod2.py
```

`sub1/mod1.py` の中から、`sub2/mod2.py` をインポートするには下記のように記述します。

{{< code lang="python" title="sub1/mod1.py" >}}
from ..sub2 import mod2

def hello():
    mod2.hello()
{{< /code >}}


パッケージの初期化ファイル (\_\_init.py\_\_)
----

Python 3.3 より前のバージョンでは、パッケージディレクトリに __`__init__.py`__ というファイルを置かなければ、そのディレクトリをパッケージとして認識させることができませんでした（`ImportError: No module named ...` というエラーが発生する）。
Python 3.3 以降では単純にディレクトリ内に .py ファイルを放り込んでおけば、そのディレクトリをパッケージとして扱うことができるようになっています（詳細は [PEP 420 -- Implicit Namespace Packages](https://www.python.org/dev/peps/pep-0420/) を参照）。

パッケージディレクトリに置かれた `__init__.py` は、そのパッケージ（あるいはその中のモジュール）をインポートしたときに実行されます。
例えば、`__init__.py` の中で各モジュールをインポートするように記述しておけば、パッケージを利用するときに個々のモジュールを指定してインポートする必要がなくなります（ただし、必要のないモジュールまでデフォルトでインポートしてしまうのは効率が悪いので、オススメはできません）。

{{< code title="ディレクトリ構成" >}}
+-- main.py
+-- mylib/  （パッケージ）
    +-- __init__.py
    +-- mymod1.py  （モジュール）
    +-- mymod2.py  （モジュール）
{{< /code >}}

{{< code lang="python" title="mylib/__init__.py" >}}
from . import mymod1
from . import mymod2
{{< /code >}}

{{< code lang="python" title="main.py" >}}
import mylib   # これだけで mylib.mymod1 と mylib.mymod2 がインポートされる

if __name__ == '__main__':
    mylib.mymod1.foo()
    mylib.mymod2.bar()
{{< /code >}}


モジュールのドキュメンテーションコメントを記述する
----

モジュールの先頭に __`"""コメント"""`__ という形式のドキュメンテーションコメントを記述しておくと、そのモジュールのドキュメントとして認識されます。

{{< code lang="python" title="fibo.py" >}}
"""Fibonacci numbers module."""

def fib(n):
    """Print fibonacci series up to n."""
    a, b = 0, 1
    while b < n:
        print(b, end=' ')
        a, b = b, a+b
{{< /code >}}

このように記述したドキュメントは、`pydoc` コマンドや `help` 関数などで参照することができます。

```console
$ pydoc fibo
Help on module fibo:

NAME
    fibo - Fibonacci numbers module.

FUNCTIONS
    fib(n)
        Print fibonacci series up to n.

FILE
    D:\y\sandbox\python\fibo.py
```

パッケージ単位のドキュメントを記述したいときは、そのパッケージの __`__init__.py`__ の先頭にドキュメンテーションコメントを記述します。

{{< code lang="python" title="mylib/__init__.py" >}}
"""My first package."""
{{< /code >}}

`pydoc` コマンドでパッケージドキュメントを参照すると、そのパッケージに含まれているモジュールのリスト (`PACKAGE CONTENTS`) も表示してくれます。

```console
$ pydoc mylib
Help on package mylib:

NAME
    mylib - My first package.

PACKAGE CONTENTS
    mymod1
    mymod2
```

パッケージ内の個々のモジュールのドキュメントを参照したい場合は、`pydoc mylib.mymod1` のように、パッケージ名の後ろにドットで繋げてモジュールを指定します。

