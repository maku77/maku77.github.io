---
title: "Python でコマンドライン引数を扱う (sys.argv)"
url: "p/ybxfwev/"
date: "2013-10-20"
lastmod: "2023-06-01"
tags: ["Python"]
changes:
  - 2023-06-01: Python 3.6 の f-string を使った記述に修正
aliases: /python/command-line-params.html
---

ここでは、`sys.argv` でコマンドライン引数を参照する初歩的な方法を説明しますが、ある程度複雑なコマンドライン引数を扱いたいときは、Python の標準ライブラリの [argparse モジュールを使う](/p/o6q8p6m/) ことをおすすめします。


コマンドライン引数を参照する
----

Python スクリプト起動時にコマンドライン引数として渡された値は、__`sys.argv`__ で文字列リストの形で参照することができます。
先頭の要素 `sys.argv[0]` には、`python` コマンドで指定したスクリプト自身の名前が格納されています。

{{< code lang="python" title="コマンドライン引数を参照する (sample.py)" >}}
import sys

print(sys.argv)
print(len(sys.argv))
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ python sample.py aaa bbb ccc
['sample.py', 'aaa', 'bbb', 'ccc']
4
{{< /code >}}

`sys.argv` の先頭にスクリプト自身の名前が入っているので、`len(sys.argv)` は少なくとも 1 以上の値になります。


コマンドライン引数が正しく指定されていないときに Usage 表示する
----

下記は、コマンドライン引数を 1 つも指定せずにスクリプトを実行した場合に使い方 (usage) を表示して終了するサンプルです。

{{< code lang="python" title="コマンドライン引数が不正なときに終了する (hello.py)" >}}
import sys
import os

if len(sys.argv) < 2:
    # コマンドライン引数が指定されなかった場合は終了
    print(f"Usage: python {os.path.basename(sys.argv[0])} <name>")
    sys.exit(1)

print(f"Hello, {sys.argv[1]}!")
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ python hello.py
Usage: python hello.py <name>

$ python hello.py Maku
Hello, Maku!
{{< /code >}}


スクリプト名を除いてコマンドライン引数を取得する
----

`sys.argv[0]` には自身のスクリプトファイル名が含まれています。
純粋にコマンドライン引数だけを取り出したい場合は、__`sys.argv[1:]`__ のようにインデックス 1 以降を取り出してしまうと分かりやすくなります。
コマンドライン引数がひとつも渡されなかった場合は、`sys.argv[1:]` は空リストになります。

{{< code lang="python" title="コマンドライン引数のみを取り出す (sample.py)" >}}
import sys

args = sys.argv[1:]
for x in args:
    print(x)
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ python sample.py 100 200
100
200
{{< /code >}}


（おまけ）ハッカー流のコマンドライン引数の処理方法
----

下記は、高校生ハッカーが主人公のドラマ『ブラッディ・マンデイ』の中で、主人公であるファルコンが使ってた方法です。
なんらかの攻撃ツールを即席で実装してたシーン。
強引に `sys.argv` をスライスしてしまって、正しい数のパラメータを指定されていない場合に、`ValueError` 例外を発生させてしまうやり方です。
なるほどねー

```python
import sys

try:
    host, frm, to = sys.argv[1:4]
except ValueError:
    print("Usage: %s <host> <from> <to>" % (sys.argv[0]))
    sys.exit(1)
```

