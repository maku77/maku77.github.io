---
title: "Python のコーディングスタイル"
date: "2007-02-06"
---

Python のコーディングスタイルは、PEP 8 や PEPで指針が示されています。

- [PEP 8 -- Style Guide for Python Code](https://www.python.org/dev/peps/pep-0008/)
- [PEP 257 -- Docstring Conventions](https://www.python.org/dev/peps/pep-0257/)


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

外部に公開しないクラスや関数は、プレフィックスとしてアンダースコアを付けます。
こうすることにより、`from M import *` の構文でインポートされないようになります。

パッケージ名とモジュール名は、ファイル名に直結するため、小文字のみで構成します。
モジュール名は、必要があれば単語の区切りのためにアンダースコアを含めてもよいとされています。

- パッケージ名にはアンダースコアを含めない
- モジュール名にはアンダースコアを含んでもよい

これらのアンダースコアの扱いに関しては、[PEP 0008 (Style Guide for Python Code) の Package and Module Names](https://www.python.org/dev/peps/pep-0008/#package-and-module-names) のセクションにおいて、下記のように記述されています。

> Modules should have short, all-lowercase names. Underscores can be used in the module name if it improves readability. Python packages should also have short, all-lowercase names, although the use of underscores is discouraged.


### その他の命名規則

- 独自の Exception クラスは、Exception を継承して作成し、サフィックスに `Error` を付ける（例: `HogeHogeError`）。
- インスタンスメソッドの最初の引数名は `self`、クラスメソッドの最初の引数名は `cls`。
- 予約語と被る attribute 名を使用したい場合は、変に省略名を付けるのではなく、サフィックスとしてアンダースコアを付ける。ただし、`class` という名前に関しては、`class_` でなく `cls` を用いる。


インデント／スペース
----

- インデントはスペース 4 文字。タブは使用しない。
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

- クラス内のメソッドは 1 行の空白行で区切る。トップレベルのクラス定義やメソッドは 2 行の空白行で区切る。
- 開き括弧 (, [ の前後に空白スペースを入れない。
- コロン `:`、セミコロン `;` の前に空白スペースを入れない。
- 算術演算子の前後には空白スペースを入れる。
- 代入文が複数行に渡って続く場合に、`=` の位置を空白スペースで揃えたりしない。


エンコーディング形式
----

- Python 3.0 からはエンコーディング形式に UTF-8 が推奨される。
- コメントや docstring 意外の文字列リテラルで ASCII 意外のエンコーディング形式を使用する場合は、`\x`, `\u`, `\U` でエスケープすること。


import
----

- import は次の順番で宣言する。各セクションは空白行で区切る。
  1. 標準ライブラリ
  2. サードパーティ・ライブラリ
  3. ローカル・ライブラリ
- import は次のように 1 行ずつ分離して行う。

```python
import os
import sys
```

- ただし、次のような `from -- import --` の形式は OK。

```python
from subprocess import Popen, PIPE
```


コメント
----

- コメントはセンテンスになっているべきで、最初の文字は大文字で始める。短いセンテンスは最後のピリオドを省略してよいが、通常はピリオドを省略してはならない。
- コメントは英語。
- ブロックコメントの中のパラグラフは、1 つの `#` を含む行で区切ること。
- インラインコメントの `#` は、ステートメントの後ろに 2 つ以上のスペースを置いてから記述すること。
- docstring の内容については [PEP 0257](http://www.python.org/dev/peps/pep-0257/) を参照。
- docstring のクォーテーションは、シングルクォート `'` ではなく、ダブルクォート `"` を使用する。
- docstring の最後の `"""` は単独行に記述する。その前の行は空白行を 1 行入れる。
- docstring が 1 行だけの場合は、最後の `"""` を同じ行に記述してよい。

