---
title: "Python の import 時のモジュールの検索パスを調べる (sys.path)"
url: "p/o4m4jyg/"
date: "2023-05-29"
tags: ["Python"]
---

モジュールの検索パスのリストは sys.path
----

Python でモジュールをインポートするには、`import モジュール名` のように記述しますが、このときにどのディレクトリのモジュールが検索されるかは、__`sys.path`__ 変数の値を見ると分かります。
`sys.path` は検索パスを示す文字列のリスト (`list[str]`) です。

{{< code lang="python" title="モジュールの検索パスを列挙する" >}}
import sys
import pprint

pprint.pprint(sys.path)
{{< /code >}}

{{< code lang="python" title="実行結果" >}}
['/Users/maku/myapp',
 '/opt/homebrew/Cellar/python@3.10/3.10.8/Frameworks/Python.framework/Versions/3.10/lib/python310.zip',
 '/opt/homebrew/Cellar/python@3.10/3.10.8/Frameworks/Python.framework/Versions/3.10/lib/python3.10',
 '/opt/homebrew/Cellar/python@3.10/3.10.8/Frameworks/Python.framework/Versions/3.10/lib/python3.10/lib-dynload',
 '/Users/maku/myapp/.venv/lib/python3.10/site-packages']
{{< /code >}}


sys.path にはどのような検索パスが含まれているか？
----

`sys.path` 変数には、自動的に次のようなパスが登録されます。

1. __起動したスクリプトと同じディレクトリ__（ただし、`python` をインタラクティブモードで起動したときは、カレントディレクトリを示す空文字 `''` になります）
2. 環境変数 [PYTHONPATH](https://docs.python.org/3/using/cmdline.html#envvar-PYTHONPATH) に指定したディレクトリ
3. Python インストーラに応じたデフォルトディレクトリ（標準ライブラリのパスなど）

注目すべきは、`sys.path` の先頭に追加されるパスで、これは起動されたスクリプトが格納されているディレクトリのパスになっています。
例えば、`python ~/myapp/main.py` と実行したときは、自動的に `~/myapp` が検索パスに追加されるため、`~/myapp` 以下に配置したモジュール (`*.py`) をインポートできるようになっています。

{{< code lang="python" title="main.py" >}}
import mymod  # ~/myapp/mymod.py が検索される
{{< /code >}}


モジュールの検索パスを追加する
----

### PYTHONPATH 環境変数を設定する方法

__`PYTHONPATH`__ 環境変数に設定したディレクトリパスは、自動的に `sys.path` に反映されてモジュールの検索パスとして使われます。

{{< code lang="console" title="Linux や macOS の場合" >}}
$ export PYTHONPATH=/path/to/lib1:/path/to/lib2:$PYTHONPATH
{{< /code >}}

パスの区切り文字は Linux や macOS ではコロン (`:`) ですが、Windows の場合はセミコロン (`;`) になることに注意してください（`PATH` 環境変数の指定方法と同じです）。

{{< code title="Windows の場合" >}}
C:\> set PYTHONPATH=C:\path\to\lib1;C:\path\to\lib2;%PYTHONPATH%
{{< /code >}}

### sys.path 変数を変更する方法

__`sys.path`__ 変数の内容はスクリプト内で変更可能なので、このリストに任意のパスを追加することで、モジュールの検索パスを動的に拡張できます。

```python
sys.path.append("/path/to/lib1")
sys.path.append("/path/to/lib2")
```

モジュールやパッケージを ZIP 化したアーカイブのパスを指定することもできます。

```python
sys.path.append("./mypkg.zip")
```

