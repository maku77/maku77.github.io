---
title: "Python でテンポラリファイル／ディレクトリを作成する (tempfile)"
url: "p/co7o5k2/"
date: "2023-06-01"
tags: ["Pythoh"]
---

テンポラリディレクトリを作成する
----

### 自動削除されるテンポラリディレクトリ (TemporaryDirectory)

Python の [tempfile](https://docs.python.org/ja/3/library/tempfile.html) モジュールが提供する __`TemporaryDirectory`__ クラスを使用して、テンポラリディレクトリを作成することができます。
`TemporaryDirectory` オブジェクトを `with` 文に渡すと、`as` 節の変数として、作成されたテンポラリディレクトリのパスを取得できます。

{{< code lang="python" title="テンポラリディレクトリの作成" hl_lines="4" >}}
import os
import tempfile

with tempfile.TemporaryDirectory() as dirname:
    print(dirname)  # => /tmp/tmp98olubfz
    print(os.path.isdir(dirname))  # => True

# with を抜けるとテンポラリディレクトリは既に削除されている
print(os.path.isdir(dirname))  # => False
{{< /code >}}

テンポラリディレクトリは、`with` ブロックの実行コンテキストを抜けるときに自動的に削除されることに注意してください（`__exit__` メソッドの実装で `cleanup` メソッドが呼ばれており、自動的にディレクトリが削除される仕組みになっています）。
よって、Python プログラムの中で、明示的にテンポラリディレクトリを削除する必要はありません。

### 自動削除されないテンポラリディレクトリ (mkdtemp) {#mkdtemp}

実行コンテキストを抜けても自動削除されないテンポラリディレクトリを作成したい場合は、`tempfile.TemporaryDirectory` クラスの代わりに、[tempfile.mkdtemp](https://docs.python.org/ja/3/library/tempfile.html#tempfile.mkdtemp) 関数を使用します。
作成されたテンポラリディレクトリは、プログラムを終了してからユーザーが手動で削除しなければいけないので、分かりやすいディレクトリに作成しておいた方がよいでしょう。
次の例では、カレントディレクトリ (`.`) に、`.tmp` サフィックスの付いたテンポラリディレクトリを作成しています。

{{< code lang="python" title="（自動削除されない）テンポラリディレクトリの作成" hl_lines="3" >}}
import tempfile

dirname = tempfile.mkdtemp(dir=".", suffix=".tmp")
print(dirname)  # => ./tmpu57g67kg.tmp
{{< /code >}}

この Python プログラムを実行終了した後にも、テンポラリディレクトリが残っていることを確認してください。
ちなみに、__`dir`__、__`prefix`__, __`suffix`__ といったパラメーターは、`tempfile.TemporaryDirectory` クラスのコンストラクタにも指定できますが、あまり使用することはないでしょう（自動削除されるのでパスを知る必要がない）。

- 参考: [Linuxコマンド: テンポラリディレクトリを作成する (`mktemp`)](https://maku.blog/p/oo32y2x/)


テンポラリファイルを作成する
----

### 自動削除されるテンポラリファイル (TemporaryFile)

テンポラリファイルを作成するには、[tempfile.TemporaryFile](https://docs.python.org/ja/3/library/tempfile.html#tempfile.TemporaryFile) 関数を使用します。
この関数は、`open` 関数と同様にファイルオブジェクトを返します。
パラメーターにも `open` 関数と同様のものを指定できます。
次の例では、テキスト形式で読み書き可能なテンポラリファイルを作成しています。
同じファイルオブジェクトで書き込みと読み取りを行うため、`mode` パラメーターには __`"w+"`__ を指定しています。

{{< code lang="python" title="テンポラリファイルの作成" hl_lines="3" >}}
import tempfile

with tempfile.TemporaryFile(mode="w+", encoding="utf-8", newline="\n") as fp:
    # テンポラリファイルへ書き込む
    fp.write("Hello\n")
    fp.write("World\n")

    # テンポラリファイルの内容を読み込む
    fp.seek(0)
    lines = fp.readlines()
    print(lines)  # => ['Hello\n', 'World\n']
{{< /code >}}

`TemporaryDirectory` と同様に、`TemporaryFile` で作成したテンポラリファイルは、`with` 文の実行コンテキストを抜けるときに自動的に削除されることに注意してください。

### パスを取得可能なテンポラリファイル (NamedTemporaryFile)

テンポラリファイルの名前が必要な場合は、`TemporaryFile` の代わりに、__`NamedTemporaryFile`__ を使用する必要があります。
こちらを使わないと、ファイルオブジェクトの `name` プロパティでファイル名を参照できません。

{{< code lang="python" hl_lines="8 16" >}}
import tempfile

def cat(filename: str) -> None:
    """指定されたファイル名のファイルの内容を標準出力に出力する"""
    with open(filename, "r", encoding="utf-8") as fp:
        print(fp.read())

with tempfile.NamedTemporaryFile(mode="w", encoding="utf-8", newline="\n") as fp:
    # テンポラリファイルへ書き込む
    fp.write("Line 1\n")
    fp.write("Line 2\n")
    fp.write("Line 3")
    fp.flush()

    # テンポラリファイルの名前を使って何らかの処理
    cat(fp.name)
{{< /code >}}

### 自動削除されないテンポラリファイル (mkstemp)

実行コンテキストを抜けても自動削除されないテンポラリファイルを作るには、[tempfile.mkstemp](https://docs.python.org/ja/3/library/tempfile.html#tempfile.mkstemp) 関数を使用します。
指定できるパラメーターは [mkdtemp](#mkdtemp) 関数によるディレクトリ作成とほぼ同様で、出力先のディレクトリ (__`dir`__) や、プレフィックス (__`prefix`__)、サフィックス (__`suffix`__) を指定できます。
`mkstemp` 関数は、戻り値としてテンポラリファイルのデスクリプター (`int`) とファイル名 (`str`) を返します。

{{< code lang="python" title="（自動削除されない）テンポラリファイルの作成 - 方法 1" hl_lines="4" >}}
import tempfile

# テンポラリファイルの生成
fd, filename = tempfile.mkstemp(dir=".", suffix=".txt")
print(fd)  # => 3
print(filename)  # => /Users/maku/myapp/tmplal4nz7v.txt

# 書き込み
with open(filename, mode="w", encoding="utf-8", newline="\n") as f:
    f.write("Hello\n")
    f.write("World\n")

# 読み込み
with open(filename, mode="r", encoding="utf-8") as f:
    print(f.readlines())  # => ['Hello\n', 'World\n']

# 明示的に削除するなら
# os.remove(filename)
{{< /code >}}

`NamedTemporaryFile` 関数で __`delete=False`__ プロパティを指定することでも、自動削除されないテンポラリファイルを作成できるようです。

{{< code lang="python" title="（自動削除されない）テンポラリファイルの作成 - 方法 2" hl_lines="5" >}}
import tempfile

# テンポラリファイルの生成と書き込み
with tempfile.NamedTemporaryFile(
    mode="w", encoding="utf-8", newline="\n", dir=".", suffix=".txt", delete=False
) as fp:
    filename = fp.name
    fp.write("Hello\n")
    fp.write("World\n")

# 自動削除しないように設定 (delete=False) したので、テンポラリファイルは残っている
with open(filename, mode="r", encoding="utf-8") as f:
    print(f.readlines())  # => ['Hello\n', 'World\n']
{{< /code >}}

