---
title: "Python の関数やクラスのソースコードを確認する (inspect)"
url: "p/xbucsaq/"
date: "2016-12-05"
tags: ["Python"]
aliases: /python/dev/get-source.html
---

関数のコードを表示する
----

Python のプログラムを作成しているときに、ある関数のソースコードを確認したくなったときは、__`inspect`__ モジュールの __`getsource`__ 関数を使用して簡単に調べることができます。

次の例では、Python の対話型シェル（`python3` コマンド）を起動して、`os.path.abspath` 関数の実装コードを確認しています。

{{< code lang="python" title="例: os.path.abspath の実装コードを確認する" >}}
>>> import inspect
>>> import os
>>> print(inspect.getsource(os.path.abspath))
def abspath(path):
    """Return an absolute path."""
    path = os.fspath(path)
    if not isabs(path):
        if isinstance(path, bytes):
            cwd = os.getcwdb()
        else:
            cwd = os.getcwd()
        path = join(cwd, path)
    return normpath(path)
{{< /code >}}

### （おまけ）シェルスクリプト化しておく

次のようなシェルスクリプト (__`pycode`__) を作っておくと、Python の関数の実装コードを簡単に確認できるようになります。
このシェルスクリプトは、PATH の通ったディレクトリに置いて、`chmod +x pycode` で実行権限を付けておきます。

{{< accordion title="/Users/maku/bin/pycode" >}}
{{< code lang="bash" >}}
#!/bin/bash
#
# pycode - Python の関数やクラスの実装コードを表示します
#
# Usage:
#   $ pycode <モジュール名> <関数名|クラス名>
#
# 使用例:
#   $ pycode os path.abspath
#

# 引数の数を確認
if [ "$#" -ne 2 ]; then
  echo "Usage: $(basename $0) <モジュール名> <関数名|クラス名>"
  exit -1
fi

# 引数からモジュール名と関数名を取得
module_name="$1"
function_name="$2"

# 関数のコードを表示するための Python コード
python_code=$(cat <<END
import $module_name
import inspect

try:
    function_code = inspect.getsource($module_name.$function_name)
    print(function_code)
except (ImportError, AttributeError):
    print(f"シンボルが見つかりません: {module_name}.{function_name}")
END
)

# Python コマンドを実行して関数のコードを表示
python3 -c "$python_code"
{{< /code >}}
{{< /accordion >}}

例えば、`os` モジュールの `path.abspath` 関数のコードを確認するには次のように実行します。

{{< code lang="console" title="pycode の使用例" >}}
$ pycode os path.abspath
{{< /code >}}


関数が定義されているファイル全体を表示する
----

`inspect.getsource` 関数でモジュール名を指定すれば、そのモジュール全体のコードを取得することができるのですが、ハイライト表示なしで長いコードが表示されると非常に読みにくいです。
ここでは、モジュールが定義されているファイルそのものをテキストエディタで開く方法を紹介します。

__`inspect.getfile`__ 関数を使用すると、指定したクラス、関数、オブジェクトなどが定義されているファイルのパスを取得することができます。

{{< code lang="python" title="例: os モジュールのソースコードのパスを調べる" >}}
>>> import inspect
>>> import os
>>> inspect.getfile(os)
'C:\\Python35\\lib\\os.py'
{{< /code >}}

あとは、そのファイルを任意のエディタで開けば OK です。
エディタの実行ファイルにパスが通っているのであれば、__`os.system`__ 関数を使って、そのエディタでさくっと開くことができます。

{{< code lang="python" title="例: os モジュールのソースコードを gvim エディタで開く" >}}
>>> import inspect
>>> import os
>>> os.system('gvim ' + inspect.getfile(os))
{{< /code >}}

