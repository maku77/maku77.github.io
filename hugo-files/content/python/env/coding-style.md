---
title: "Python のコーディングスタイル"
url: "p/pyk3j2h/"
date: "2007-02-06"
lastmod: "2022-02-17"
tags: ["Python"]
aliases: /python/coding-style.html
---

Python のコーディングスタイルは、PEP 8 や PEP 257 で指針が示されています。

- [PEP 8 -- Style Guide for Python Code](https://www.python.org/dev/peps/pep-0008/)
- [PEP 257 -- Docstring Conventions](https://www.python.org/dev/peps/pep-0257/)

このページでは主に上記の内容についてまとめていますが、実際に Python のプロジェクトにコーディングスタイルを適用するときは、[Black などの Python フォーマッターを導入](https://maku.blog/p/4oybku6/) することをおすすめします。
Black は、PEP 8 や PEP 257 と若干異なるところがありますが、理にかなったスタイルを強制的に適用してくれます。


命名規則
----

### 大文字／小文字

- パッケージ名は小文字のみ（例: `mypackage`）
- クラス名は大文字で始める（例: `MyClass`）
- 関数、あるいは public メソッドは小文字（例: `my_public_method`）
- protected メソッドはアンダースコア 1 つで始める（例: `_my_protected_method`）
- private メソッドはアンダースコア 2 つで始める（例: `__my_private_method`）
- 定数名はすべて大文字（例: `MY_CONSTANT`）

Python の name mangling の仕組みによって、アンダースコア 2 つで始まる名前は、クラス外部、あるいはサブクラスからその名前ではアクセスできないようになっています（正確には `_ClassName__method` という名前でアクセスできますが）。
この特性を利用して、private メソッドをアンダースコア 2 つで始めるようにするのがよい、ということです。


### グローバル領域でのアンダースコアの使用について

トップレベルに定義するクラスや関数で、モジュール（ファイル）内に閉じて外部に公開しないものは、プレフィックスとして 1 つのアンダースコアを付けます。
こうすることで、`from M import *` の形での自動インポートを防ぐことができます。

```python
# モジュール内部で使う関数
def _parse_timestamp_with_tzinfo(value, tzinfo):
    """Parse timestamp with pluggable tzinfo options."""
    ...省略...

# モジュール内部で使うクラス
class _RetriesExceededError(Exception):
    """Internal exception used when the number of retries are exceeded."""
    pass
```

モジュール名（ファイル）とパッケージ名（ディレクトリ）は、小文字のみで構成します。
モジュール名は、必要があれば単語の区切りのためにアンダースコアを含めてもよいとされていますが、パッケージ名（ディレクトリ）にはアンダースコアを含めてはいけません。

- モジュール名にはアンダースコアを含んでもよい (OK: `user.py`, `user_info.py`)
- パッケージ名にはアンダースコアを含めない (OK: `game/`, NG: `game_db/`)

これらのアンダースコアの扱いに関しては、[PEP 0008 (Style Guide for Python Code) の Package and Module Names](https://www.python.org/dev/peps/pep-0008/#package-and-module-names) のセクションにおいて、下記のように記述されています。

> Modules should have short, all-lowercase names. Underscores can be used in the module name if it improves readability.
>
> Python packages should also have short, all-lowercase names, although the use of underscores is discouraged.


### その他の命名規則

- 独自の Exception クラスは、Exception を継承して作成し、サフィックスに __`Error`__ を付ける（例: `HogeHogeError`）。
- インスタンスメソッドの最初の引数名は __`self`__、クラスメソッドの最初の引数名は __`cls`__。
- 予約語と被る attribute 名を使用したい場合は、変に省略名を付けるのではなく、サフィックスとしてアンダースコアを付ける。ただし、`class` という名前に関しては、`class_` でなく `cls` を用いる。


インデント／スペース
----

- インデントは __スペース 4 文字__。タブは使用しない。
  - python のコマンドライン引数で `-t` を指定すればスペースとタブが混在しているときに警告を表示してくれます（`-tt` オプションならエラーにしてくれる）。
- 一行は 79 文字（+ 改行）まで。ただし、ドキュメント (docstring) やコメント行は 72 文字まで。
- デフォルトパラメータの `=` の前後にはスペースを入れない。
  ```python
  def complex(real, imag=0.0):
      return magic(r=real, i=imag)
  ```
- 長い行の途中で改行する場合は、次の行のインデントは前の行の開き括弧の位置に合わせる。演算子の後ろで改行する。

  ```python
  if (width == 0 and height == 0 and
      color == 'red' and emphasis == 'strong' or
      highlight > 100):
  ```

- クラス内のメソッド間は 1 行の空白行で区切る。トップレベルのクラス定義やメソッドは 2 行の空白行で区切る。
  - ▽このあたりのルールは有名どころの OSS のコードは [ちゃんと守ってます](https://github.com/boto/botocore/tree/develop/botocore) が、ドキュメンテーションコメントの最初の動詞に三単現の s を付けない、というルールはあまり守られていないっぽいです。
- 開き括弧 `(`、`[` の前後に空白スペースを入れない。
- コロン `:`、セミコロン `;` の前に空白スペースを入れない。
- 算術演算子の前後には空白スペースを入れる。
- 代入文が複数行に渡って続く場合に、`=` の位置を空白スペースで揃えたりしない。


エンコーディング形式
----

- Python 3.0 からはエンコーディング形式に UTF-8 が推奨される。
- コメントや docstring 以外の文字列リテラルで ASCII 意外のエンコーディング形式を使用する場合は、`\x`, `\u`, `\U` でエスケープすること。


インポート
----

- `import` は次の順番で宣言し、__各セクションを空白行で区切る__。
  1. 標準ライブラリ
  2. サードパーティ・ライブラリ
  3. ローカル・ライブラリ
  ```python
  import random
  import os
  import socket
  
  import dateutil.parser
  from dateutil.tz import tzutc
  
  import foo
  import foo.bar
  ```
- `import` は次のように 1 行ずつ分離して行う。
  ```python
  import os
  import sys
  ```
- ただし、次のような `from -- import --` の形式は 1 行で書いても OK。
  ```python
  from subprocess import Popen, PIPE
  ```
- 上記の `import` 以降が複数行に渡るときは、次のように括弧で囲めば OK。
  ```python
  from botocore.compat import (
      json, quote, zip_longest, urlsplit, urlunsplit, OrderedDict,
      six, urlparse, get_tzinfo_options, get_md5, MD5_AVAILABLE,
      HAS_CRT
  )
  ```


コメント
----

- コメントはセンテンスになっているべきで、最初の文字は大文字で始める。短いセンテンスは最後のピリオドを省略してよいが、通常はピリオドを省略してはならない。
- コメントは基本的には英語で。
- ブロックコメントの中のパラグラフは、1 つの `#` を含む行で区切ること。
- インラインコメントの `#` は、ステートメントの後ろに 2 つ以上のスペースを置いてから記述すること。

モジュール（ファイル）や、クラス、関数などのドキュメンテーションコメントは、__docstring 形式__ での記述が推奨されています。下記記事を参考にしてください。

- [Docstring でドキュメンテーションコメントを記述する](/p/y2biqz7/)

