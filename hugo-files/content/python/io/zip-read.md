---
title: "Python で ZIP/JAR ファイルの内容を読み込む (zipfile)"
url: "p/gxajt4d/"
date: "2016-12-06"
lastmod: "2023-05-30"
tags: ["Python", "ZIP"]
changes:
  - 2023-05-30: ZIP ファイル内のファイルを読み込む方法
aliases: /python/io/zip-file-list.html
---

Python に標準搭載されている [zipfile モジュール](https://docs.python.org/3/library/zipfile.html) を使用すると、ZIP ファイルや JAR ファイルの読み書きを手軽に行うことができます。


ZIP ファイル内のファイル一覧を取得する
----

下記のサンプルでは、指定した ZIP ファイル内のファイルリストとその（展開後の）サイズを出力します。
__`zipfile.ZipFile`__ コンストラクタで `ZipFile` オブジェクトを生成し、__`infolist`__ メソッドで ZIP 内のファイル情報 (`ZipInfo`) を取得できます。

{{< code lang="python" title="dump_zip.py" >}}
import zipfile

with zipfile.ZipFile("sample.zip", "r") as zf:
    for info in zf.infolist():
        if info.is_dir():
            continue  # ディレクトリ情報は出力せずにスキップ
        print(f"{info.filename} ({info.file_size} bytes)")
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ python dump_zip.py
data/sample1.txt (4560 bytes)
data/sample2.txt (2284 bytes)
data/sample3.txt (5712 bytes)
README.txt (1417 bytes)
{{< /code >}}

ファイル名でソートして出力したい場合は、下記のようにいったんリストにしてソートしてしまえば OK です。

{{< code lang="python" hl_lines="2 3" >}}
with zipfile.ZipFile("sample.zip", "r") as zf:
    arr = list(zf.infolist())
    arr.sort(key=lambda x: x.filename)
    for info in arr:
        print(f"{info.filename} ({info.file_size} bytes)")
{{< /code >}}


ZIP ファイル内のファイルを読み込む
---

ZIP ファイル内の個々のファイルの内容を読み込むには、[ZipFile#read メソッド](https://docs.python.org/3/library/zipfile.html#zipfile.ZipFile.read) を使用します。
このメソッドは、ファイルの内容をバイトデータ (`bytes`) として返します。
バイトデータをテキストデータに変換するには、__`bytes#decode`__ メソッドを使用します。

{{< code lang="python" title="ZIP ファイル内のテキストファイルを読み込む" >}}
import zipfile

with zipfile.ZipFile("sample.zip", "r") as zf:
    for info in zf.infolist():
        if info.is_dir():
            continue  # ディレクトリならスキップ

        if not info.filename.endswith(".txt"):
            continue  # .txt 以外のファイルもスキップ

        # ファイルのバイトデータを読み込んでテキストに変換する
        text = zf.read(info).decode("utf-8")
        print(f"=== {info.filename} ===")
        print(text)
{{< /code >}}

{{< code title="実行結果" >}}
=== data/sample1.txt ===
...（省略）...

=== data/sample2.txt ===
...（省略）...

=== data/sample3.txt ===
...（省略）...

=== README.txt ===
...（省略）...
{{< /code >}}


JAR ファイル内のクラス一覧を取得する
----

JAR ファイルもフォーマット上は ZIP ファイルと同じ構造なので、ZIP ファイルを扱うのと同様に処理することができます。
ここでは、JAR ファイル内の `.class` ファイルのみを処理するために、ファイルの拡張子が `.class` であるかを確認しながらループ処理しています。
ここでは、ファイル名から拡張子を抽出するために `os.path.splitext` を使っていますが、もっと単純に `str#endswith` などで代用してもよいかもしれません。

{{< code lang="python" title="dump_classes.py" hl_lines="8-10" >}}
import zipfile
import os
import sys

def iter_classes_in_jar(jar_name):
    """JAR ファイル内の .class ファイルの名前をもとに Java クラス名を列挙します。"""
    with zipfile.ZipFile(jar_name, "r") as zf:
        for info in zf.infolist():
            classname, ext = os.path.splitext(info.filename)
            if ext != ".class":
                continue  # .class 以外のファイルはスキップ
            yield classname

if __name__ == "__main__":
    jar_name = sys.argv[1]
    # List all classes
    for clazz in iter_classes_in_jar(jar_name):
        print(clazz)
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ python dump_classes.py app.jar
com/example/myapp/Bar
com/example/myapp/Foo
com/example/myapp/Main
...
{{< /code >}}

