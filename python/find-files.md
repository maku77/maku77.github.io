---
title: 指定した拡張子、名前のファイルを列挙する
created: 2015-10-30
---


指定した拡張子を持つファイルを列挙する
----

ここでは、カレントディレクトリ以下の .java ファイルを列挙する例を示します。

### glob を使用する方法（オススメ）

`glob.iglob` や `glob.glob` を使用すると、いわゆる glob（シェルのワイルドカード）によるパターン指定でファイルを列挙することができます。
`glob.iglob` は for ループでイテレーションしたいとき、`glob.glob` はリストで取得したいときに使います。

```python
import glob

for name in glob.iglob('*.java'):
    print(name)
```

Python 3.5 以降では、ディレクトリを再帰的にたどるための `**` も使用できます。
この場合は `recursive` パラメータを `True` に設定します。

```python
import glob

for name in glob.iglob('**/*.java', recursive=True):
    print(name)
```

### os.walk を使用する方法

glob を使用せずに、`os.walk` などを使用して自力でファイルを検索することもできます。
ここでは、`os.walk` ですべてのファイルを再帰的に列挙しつつ、`fnmatch.fnmatch` を使って指定した拡張子に一致するかを調べています。

```python
import fnmatch
import os

for dirpath, dirs, files in os.walk('.'):
    for name in files:
        if fnmatch.fnmatch(name, '*.java'):
            print(os.path.join(dirpath, name))
```

`glob.iglob` の実装も、実はこのように `os.walk` や `fnmatch` を組み合わせて実装されています。


指定した名前に完全に一致するファイルを検索する
----

ファイル名が完全に一致するファイルを検索したい場合も、`glob.iglob` を使って検索することができます。
ファイルを１つだけ見つけたい場合は、下記のようにループを `break` で抜けてしまえばよいでしょう。

```python
import glob

for name in glob.iglob('**/Sample.java', recursive=True):
    print(name)
    break
```


応用例: 指定した拡張子を持つファイルを grep する
----

下記のサンプルコードは、カレントディレクトリ以下の .java ファイルのうち、`import` で始まる行を抽出して出力します。

#### sample.py

```python
import glob

for filename in glob.iglob('**/*.java', recursive=True):
    with open(filename, encoding='utf-8') as f:
        for i, line in enumerate(f, start=1):
            if line.startswith('import '):
                print('{}: {}: {}'.format(filename, i, line.rstrip('\r\n')))
```

#### 実行結果

```
$ python sample.py
com/example/myapp/Main.java: 1: import java.util.concurrent.Callable;
com/example/myapp/Main.java: 2: import java.util.concurrent.ExecutionException;
com/example/myapp/Main.java: 3: import java.util.concurrent.FutureTask;
com/example/myapp/Db.java: 1: import java.sql.Connection;
com/example/myapp/Db.java: 2: import java.sql.PreparedStatement;
...
```

