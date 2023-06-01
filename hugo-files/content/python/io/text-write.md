---
title: "Python でテキストファイルを書き込む (write, writelines)"
url: "p/nmv4cjr/"
date: "2020-06-02"
tags: ["Python"]
aliases: /python/io/write-text-file.html
---

テキストファイルを一行ずつ書き込む (write)
----

Python でファイル書き込み用のファイルオブジェクトを作成するには、[open 関数](https://docs.python.org/ja/3/library/functions.html#open) の __`mode`__ パラメータで下記のいずれかの値を設定してファイルをオープンします。

| 文字 | 意味 | ファイルが存在しない場合の動き | ファイルが存在する場合の動き |
| :--: | :--: | ---- | ---- |
| __`w`__ | 上書き | 新規作成 | 内容をクリアして上書き |
| __`a`__ | 追記 | 新規作成 | 末尾にテキストを追加 |
| __`x`__ | 排他生成 | 新規作成 | オープンに失敗する (`FileExistsError`) |

最もよく使うのは、`w` による上書き用のオープンです。
次の例では、`output.txt` という名前のファイルを作成（あるいは上書き）して、2 つの行を書き込んでいます。

{{< code lang="python" title="テキストファイルの作成" >}}
f = open('output.txt', mode='w', encoding='utf-8', newline='\n')
f.write('Hello\n')
f.write('World\n')
f.close()
{{< /code >}}

エンコーディング形式 (__`encoding`__) は、省略するとプラットフォーム依存になるので、明示的に __`utf-8`__ を指定しておいた方がよいでしょう。

改行の扱い方 (__`newline`__) は、省略すると `write()` 関数での `\n` の書き込みが、プラットフォーム依存の改行コードの書き込みという扱いになります。
必ず Unix の改行コード (`LF`) で書き込みたいということであれば、上記のように __`\n`__ あるいは __`''`__ を指定しておきましょう。
この指定により、`write()` 関数での `\n` が LF、`\r\n` が CR+LF として書き込まれるようになります。

ファイルオブジェクトは最後に `close()` してリソースを解放する必要がありますが、 __`with`__ を使って `close()` を自動化することができます。
基本的にはこの書き方をしておけばよいです。

{{< code lang="python" title="close 処理の自動化" >}}
with open('output.txt', mode='w', encoding='utf-8', newline='\n') as f:
    f.write('Hello\n')
    f.write('World\n')
{{< /code >}}


文字列のリストをまとめて書き込む (writelines)
----

文字列のリストを一度に書き込みたい場合は、`write()` の代わりに [writelines()](https://docs.python.org/ja/3/library/io.html#io.IOBase.writelines) を使用すると便利です。

{{< code lang="python" title="複数行の出力" hl_lines="7" >}}
lines = [
    'Hello\n',
    'World\n'
]

with open('output.txt', mode='w', encoding='utf-8', newline='\n') as f:
    f.writelines(lines)
{{< /code >}}

`writelines()` も `write()` と同様に、行末に改行を入れたりはしません。
改行したい場合は、明示的に改行文字 `\n` を入れる必要があります。


open の排他生成モード (mode=x) の用途
----

`open()` の一見変わったモードとして、排他生成モード (__`mode="x"`__)があります。
このモードでオープンすると、すでにファイルが存在している場合に [FileExistsError 例外](https://docs.python.org/ja/3/library/exceptions.html#FileExistsError) が発生するようになっています。
例えば次のように、ファイルが存在しない場合だけ、デフォルト値の書き込まれたデータファイルを作成するといったケースで使えます。

{{< code lang="python" title="すでに config.yml が存在する場合は何もしない" >}}
try:
    with open('config.yml', mode='x', encoding='utf-8', newline='\n') as f:
        f.write('user: anonymous\n')
except FileExistsError:
    # Already exists
    pass
{{< /code >}}

このコードは、次のようにファイルの存在チェックをするのと同じように見えるかもしれません。

{{< code lang="python" title="このコードには不具合がある" >}}
import os.path

if os.path.exists('config.yml'):
    # Already exists
else:
    with open('config.yml', mode='x', encoding='utf-8', newline='\n') as f:
        f.write('user: anonymous\n')
{{< /code >}}

しかし、後者の方法では、ファイルの存在チェックから `open()` の実行までのわずかな時間に、ファイル存在状態が変化するというレースコンディションが発生する危険があります。
また、後者の方法だと、`os.path` をインポートしないといけないとか、ファイル名を 2 か所で指定しないといけない、といった欠点があります。
これらの理由から、ファイルが存在しない場合にのみファイルを新規作成したい場合には、`open` 関数の `x` モードを使用するのが適しています。

