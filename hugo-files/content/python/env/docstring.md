---
title: "Python でドキュメンテーションコメント (docstring) を記述する"
url: "p/y2biqz7/"
date: "2019-06-04"
lastmod: "2021-11-03"
tags: ["Python"]
aliases: /python/env/docstring.html
---

Docstring とは？
----

Python のコードに __docstring__ フォーマットでコメントを記述しておくと、いろいろなツールから API ドキュメントとして参照できるようになります。
例えば、Python 用の統合開発環境 (IDE) におけるコード補完時に、ヒントとして関数の使用方法が表示されます。

下記は [PEP 257 -- Docstring Conventions](https://www.python.org/dev/peps/pep-0257/) に記述されている docstring の書き方の例です。

```python
def complex(real=0.0, imag=0.0):
    """Form a complex number.

    Keyword arguments:
    real -- the real part (default 0.0)
    imag -- the imaginary part (default 0.0)
    """
    if imag == 0.0 and real == 0.0:
        return complex_zero
    ...
```

docstring はこのように 3 つのクォート (__`"""`__) で囲んで記述します。
__docstring コメントは関数の内部に記述する__ ところがポイントです。
このあたりは、関数の外側にドキュメンテーションコメントを記述する Java や C# などとは異なっています。


Docstring の書き方
----

- `"""` で始まり `"""` で終わる。
- 一行目はサマリであり、ピリオドで終わるフレーズで記述する。
- 一行目は `"""` と同じ行に記述してもよいし、改行してから記述してもよい。
- 最後の `"""` は行末に書いてもよいし、単独の行に書いてもよい。
- 一行だけで記述する場合は、開始クォートと終了クォートを同じ行に記述してもよい。
  ```python
  def complex(real=0.0, imag=0.0):
      """Form a complex number."""
  ```
- 一行当たり __72 文字以内__ で記述する。
    - クラスメソッドであれば、最初から 8 文字インデントして書き始めることになるので、結局、一行あたりのサイズを 80 文字にするということですね。
- __`r"""`__ で始めると Raw docstring となり、バックスラッシュを含めることができる。
- サマリは命令形で記述する。動詞で始め、**三単現の s は付けない**（例: `Return ...`）。
    - これは、Javadoc が三単現の s を付けるのを慣例しているのとは対照的です。下記は、[PEP 257 -- Docstring Conventions](https://www.python.org/dev/peps/pep-0257/) からの抜粋です。
      > The docstring is a phrase ending in a period. It prescribes the function or method's effect as a command ("Do this", "Return that"), not as a description; e.g. don't write "Returns the pathname ...".
- 詳細なコメントを記述する場合は、一行目にサマリ、二行目は空行、三行目以降に詳細コメントを記述する。この場合も一行目のサマリは、`"""` と同じ行に記述してよい。
  ```python
  def complex(real=0.0, imag=0.0):
      """Form a complex number.

      Keyword arguments:
      real -- the real part (default 0.0)
      imag -- the imaginary part (default 0.0)
      """
  ```
- パッケージのドキュメントは __`__init__.py`__ に書く。自分が export するモジュールやサブパッケージをリスト化し、それぞれ一行のサマリを記述する。


Docstring の記述例
----

{{< code lang="python" title="os モジュールの例" >}}
def normcase(s):
    """Normalize case of pathname.

    Makes all characters lowercase and all slashes into backslashes."""
{{< /code >}}

{{< code lang="python" title="urllib.request モジュールの例" >}}
def _open_generic_http(self, connection_factory, url, data):
    """Make an HTTP connection using connection_class.

    This is an internal method that should be called from
    open_http() or open_https().

    Arguments:
    - connection_factory should take a host name and return an
      HTTPConnection instance.
    - url is the url to retrieval or a host, relative-path pair.
    - data is payload for a POST request or None.
    """
{{< /code >}}

{{< code lang="python" title="json モジュールの例" >}}
def load(fp, *, cls=None, object_hook=None, parse_float=None,
        parse_int=None, parse_constant=None, object_pairs_hook=None, **kw):
    """Deserialize ``fp`` (a ``.read()``-supporting file-like object containing
    a JSON document) to a Python object.

    ``object_hook`` is an optional function that will be called with the
    result of any object literal decode (a ``dict``). The return value of
    ``object_hook`` will be used instead of the ``dict``. This feature
    can be used to implement custom decoders (e.g. JSON-RPC class hinting).

    ``object_pairs_hook`` is an optional function that will be called with the
    result of any object literal decoded with an ordered list of pairs.  The
    return value of ``object_pairs_hook`` will be used instead of the ``dict``.
    This feature can be used to implement custom decoders.  If ``object_hook``
    is also defined, the ``object_pairs_hook`` takes priority.

    To use a custom ``JSONDecoder`` subclass, specify it with the ``cls``
    kwarg; otherwise ``JSONDecoder`` is used.
    """
{{< /code >}}


（コラム）Docstring はなぜ 72 文字までなのか？
----

[PEP 8 -- Style Guide for Python Code](https://www.python.org/dev/peps/pep-0008/) では、docstring コメントは一行あたり __72 文字まで__ にすることを推奨しています。

> For flowing long blocks of text with fewer structural restrictions (docstrings or comments), the line length should be limited to **72** characters.
> ...
>
> The Python standard library is conservative and requires limiting lines to 79 characters (and docstrings/comments to **72**).

72 文字という数字は、パンチカードの印刷幅から来ているという説や、ツールでの出力を考慮しているという説などがあります。
現実的にはツールの出力を考慮しているのだと考えるのが自然でしょう。
例えば、Python 付属の `pydoc` コマンドで、`main.py` 内のドキュメンテーションコメントを表示すると下記のように表示されます。

```console
$ pydoc main
Help on module main:

NAME
    main

FUNCTIONS
    complex(real=0.0, imag=0.0)
        Form a complex number.

        Keyword arguments:
        real -- the real part (default 0.0)
        imag -- the imaginary part (default 0.0)

FILE
    /User/maku/main.py
```

関数のドキュメンテーション部分には 8 文字分のインデントが入っています。
よって、ドキュメンテーションコメント自体は 72 文字に収めておかないと、合計で 80 文字を超えることになってしまいます。

