---
title: "実行中のスクリプトのファイル名やパスを取得する"
date: "2016-12-05"
---

実行中の Python スクリプトのファイル名は、`__file__` で取得することができます。

#### sample.py
```python
print(__file__)  #=> 'sample.py'
```

`__file__` で取得できるファイル名は、python コマンドのパラメータで指定したファイル名そのものです。
つまり、どのようにスクリプト名を指定するかによって、`__file__` で取得できる値は変わってきます。

```
$ python sample.py
sample.py

$ python ../sandbox/sample.py
../test/sample.py

$ python /Users/maku/sandbox/sample.py
/Users/maku/sandbox/sample.py
```

確実にファイル名だけを取り出したい、確実に絶対パスを取り出したい、といったときは、下記のように `os.path` モジュールの関数を組み合わせて取得するようにします。

```python
import os

print(os.path.basename(__file__))  # 確実にファイル名だけを取得
print(os.path.abspath(__file__))   # 確実にファイルの絶対パスを取得
print(os.path.dirname(os.path.abspath(__file__)))  # 確実にディレクトリパスを取得
print(os.path.splitext(__file__)[1])  # 拡張子だけを取得
```

#### 実行結果

```
sample.py
/Users/maku/sandbox/sample.py
/Users/maku/sandbox
.py
```

