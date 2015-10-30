---
title: 指定したファイル名のファイルを検索する
created: 2015-10-30
---

下記のサンプルコードの `find_files` メソッドは、指定したディレクトリから再帰的にファイルを検索します。
指定した名前のファイルが見つかるたびに、標準出力に出力しています。

#### カレントディレクトリ以下の hoge.txt を再帰的に検索
```python
import os

def find_files(search_dir, filename):
    for dir_path, dirs, files in os.walk(search_dir):
        for f in files:
            if f == filename:
                yield os.path.join(dir_path, f)

for path in find_files('.', 'hoge.txt'):
    print(path)
```

#### 実行例
```
$ python sample.py
./hoge.txt
./aaa/hoge.txt
./aaa/1/hoge.txt
./aaa/2/hoge.txt
./bbb/hoge.txt
./bbb/1/hoge.txt
./bbb/2/hoge.txt
```

