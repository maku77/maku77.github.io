---
title: "Python の対話型シェルのスタートアップ処理を設定する (PYTHONSTARTUP)"
url: "p/6k5m4jy/"
date: "2016-12-05"
tags: ["Python"]
aliases: /python/dev/python-startup.html
---

`python` コマンドを単独で実行したときのインタラクティブシェルは、環境変数 __`PYTHONSTARTUP`__ に指定したスタートアップ・スクリプトを最初に実行します。
これを利用して、日常的に使用したい関数などを自動的に定義することができます。

例えば、下記の例では、ホームディレクトリにある `.pythonstartup` スクリプトを読み込むように設定しています。

{{< code lang="bash" title="~/.bash_profile (Linux や Mac OSX の場合）" >}}
export PYTHONSTARTUP=~/.pythonstartup
{{< /code >}}

Windows の場合は、システムのプロパティから環境変数を設定してください（`~/` という表記は使用できないので、スタートアップ・スクリプトの位置はフルパスで指定する必要があります）。
コマンドラインから環境変数を設定することもできます。

```
C:\> setx PYTHONSTARTUP D:/x/myconf/pythonstartup.py
（コマンドプロンプトを再起動して反映）
```

下記のスタートアップ・スクリプトでは、モジュールのソースコードをさくっと確認するための `DEV.code` 関数を定義しています。`DEV.file` 関数の方は、指定したモジュールのコードを Vim エディタで開きます。

{{< code lang="python" title="~/.pythonstartup" >}}
class DEV:
    @staticmethod
    def code(obj):
        import inspect
        print(inspect.getsource(obj))

    @staticmethod
    def file(obj):
        import inspect
        import os
        os.system('gvim ' + inspect.getfile(obj))
{{< /code >}}

あとは、Python のインタラクティブシェルを起動すれば、上記の関数が定義された状態で起動します。

```console
$ python
...
>>> import os
>>> DEV.code(os.path.split)
def split(p):
    """Split a pathname.

    Return tuple (head, tail) where tail is everything after the final slash.
    Either part may be empty."""

    seps = _get_bothseps(p)
    d, p = splitdrive(p)
    # set i to index beyond p's last slash
    i = len(p)
    while i and p[i-1] not in seps:
        i -= 1
    head, tail = p[:i], p[i:]  # now tail has no slashes
    # remove trailing slashes from head, unless it's all slashes
    head = head.rstrip(seps) or head
    return d + head, tail
```

