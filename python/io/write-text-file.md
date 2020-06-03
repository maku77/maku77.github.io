---
title: "テキストファイルを書き込む (write, writelines)"
date: "2020-06-02"
---

テキストファイルを一行ずつ書き込む (write)
----

ファイル書き込み用のファイルオブジェクトを作成するには、[open 関数](https://docs.python.org/ja/3/library/functions.html#open) の `mode` パラメータで下記のいずれかの値を設定してファイルをオープンします。

| 文字 | 意味 | ファイルが存在しない場合の動き | ファイルが存在する場合の動き |
| :--: | :--: | ---- | ---- |
| `w` | 上書き | 新規作成 | 内容をクリアして上書き |
| `a` | 追記 | 新規作成 | 末尾にテキストを追加 |
| `x` | 排他生成 | 新規作成 | オープンに失敗する (`FileExistsError`) |

最もよく使うのは、`w` による上書き用のオープンです。
次の例では、`output.txt` ファイルを作成（あるいは上書き）して、2 つの行を書き込んでいます。

```python
f = open('output.txt', 'w', encoding='utf-8', newline='\n')
f.write('Hello\n')
f.write('World\n')
f.close()
```

エンコーディング形式 (`encoding`) は、省略するとプラットフォーム依存になるので、明示的に指定しておいた方がよいでしょう。

改行の扱い方 (`newline`) は、省略すると `write()` 関数での `\n` の書き込みが、プラットフォーム依存の改行コードの書き込みという扱いになります。
必ず Unix の改行コード (`LF`) で書き込みたいということであれば、上記のように `\n` あるいは `''` を指定しておきましょう。
この指定により、`write()` 関数での `\n` が LF、`\r\n` が CR+LF として書き込まれるようになります。

ファイルオブジェクトは最後に `close()` してリソースを解放する必要がありますが、 __`with`__ を使って `close()` を自動化することができます。

```python
with open('output.txt', 'w', encoding='utf-8', newline='\n') as f:
    f.write('Hello\n')
    f.write('World\n')
```


文字列のリストをまとめて書き込む (writelines)
----

文字列のリストを一度に書き込みたい場合は、`write()` の代わりに [writelines()](https://docs.python.org/ja/3/library/io.html#io.IOBase.writelines) を使用すると便利です。

```python
lines = [
    'Hello\n',
    'World\n'
]

with open('output.txt', 'w', encoding='utf-8', newline='\n') as f:
    f.writelines(lines)
```

`writelines()` も `write()` と同様に、行末に改行を入れたりはしません。
改行したい場合は、明示的に改行文字 `\n` を入れる必要があります。


open の排他生成モード (mode=x) は何のためにあるのか？
----

`open()` の一見変わったモードとして、排他生成モード (`mode=x`)があります。
このモードでオープンすると、すでにファイルが存在している場合に [FileExistsError 例外](https://docs.python.org/ja/3/library/exceptions.html#FileExistsError) が発生するようになっています。

例えば次のように、ファイルが存在しない場合だけ、デフォルト値の書き込まれたデータファイルを作成するといったケースで使えます。

```python
try:
    with open('config.yml', 'x', encoding='utf-8', newline='\n') as f:
        f.write('user: anonymous\n')
except FileExistsError:
    # Already exists
    pass
```

このコードは、次のようにファイルの存在チェックをするのと同じように見えるかもしれません。

```python
import os.path

if os.path.exists('config.yml'):
    # Already exists
else:
    with open('config.yml', 'x', encoding='utf-8', newline='\n') as f:
        f.write('user: anonymous\n')
```

しかし、後者の方法では、ファイルの存在チェックから `open()` の実行までのわずかな時間に、ファイル存在状態が変化するというレースコンディションが発生する危険があります。
他にもちょっとした差ですが、後者の方法だと、`os.path` をインポートしないといけないとか、ファイル名を 2 か所で指定しないといけない、といった欠点があります。

こういった理由のため、ファイルが存在しない場合のみファイルを新規作成したいというケースでは、`x` モードを指定して `open()` するのがよいです。

