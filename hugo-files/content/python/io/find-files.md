---
title: "Python で指定した拡張子や名前のファイルを列挙する (glob.iglob, glob.glob)"
url: "p/6vpyp4z/"
date: "2015-10-30"
lastmod: "2023-06-02"
tags: ["Python"]
changes:
  - 2023-06-02: コードを微修正
aliases: /python/find-files.html
---

指定した拡張子を持つファイルを列挙する
----

ここでは、Python でカレントディレクトリ以下の `.java` ファイルを列挙する例を示します。

### glob を使用する方法（オススメ）

__`glob.iglob`__ 関数や __`glob.glob`__ 関数を使用すると、いわゆるグロブ（シェルのワイルドカード）によるパターン指定でファイルを列挙することができます。
`glob.iglob` は for ループでイテレーションしたいとき、`glob.glob` はリストで取得したいときに使います。

{{< code lang="python" title="グロブによるイテレーション処理" >}}
import glob

for name in glob.iglob("*.java"):
    print(name)
{{< /code >}}

Python 3.5 以降では、ディレクトリを再帰的にたどるための __`**`__ も使用できます。
この場合は __`recursive`__ パラメータを `True` に設定します。

{{< code lang="python" title="ディレクトリを再帰的にたどる場合" >}}
import glob

for name in glob.iglob("**/*.java", recursive=True):
    print(name)
{{< /code >}}

### os.walk を使用する方法

glob を使用せずに、__`os.walk`__ などを使用して自力でファイルを検索することもできます。
ここでは、`os.walk` ですべてのファイルを再帰的に列挙しつつ、[fnmatch.fnmatch](https://docs.python.org/ja/3/library/fnmatch.html) を使って指定した拡張子に一致するかを調べています。

{{< code lang="python" title="os.walk で再帰的に .java ファイルを列挙" >}}
import fnmatch
import os

for dirpath, dirs, files in os.walk("."):
    for name in files:
        if fnmatch.fnmatch(name, "*.java"):
            print(os.path.join(dirpath, name))
{{< /code >}}

実は、`glob.iglob` の実装もこのように `os.walk` や `fnmatch` を組み合わせて実装されています。


指定した名前に完全に一致するファイルを検索する
----

ファイル名が完全に一致するファイルを検索したい場合も、`glob.iglob` を使って検索することができます。
ファイルを１つだけ見つけたい場合は、下記のようにループを `break` で抜けてしまえばよいでしょう。

{{< code lang="python" title="カレントディレクトリ以下から指定した名前のファイルを検索" >}}
import glob

for name in glob.iglob("**/SampleApp.java", recursive=True):
    print(name)
    break
{{< /code >}}


応用例: 指定した拡張子を持つファイルを grep する
----

下記の Python スクリプトは、カレントディレクトリ以下のすべての `.java` ファイルの内容を読み込み、`import` で始まる行を抽出して出力します。

{{< code lang="python" title="extract_imports.py（.java ファイル内の import 行を抽出）" >}}
import glob

for filename in glob.iglob("**/*.java", recursive=True):
    with open(filename, encoding="utf-8") as f:
        for i, line in enumerate(f, start=1):
            if line.startswith("import "):
                print("{} [{}]: {}".format(filename, i, line.rstrip("\r\n")))
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ python extract_imports.py
com/example/myapp/Main.java [1]: import java.util.concurrent.Callable;
com/example/myapp/Main.java [2]: import java.util.concurrent.ExecutionException;
com/example/myapp/Main.java [3]: import java.util.concurrent.FutureTask;
com/example/myapp/Db.java [1]: import java.sql.Connection;
com/example/myapp/Db.java [2]: import java.sql.PreparedStatement;
...
{{< /code >}}

