---
title: "ZIP ファイルを作成する (zipfile, shutil)"
date: "2019-10-22"
---

Python の標準ライブラリである **`shutil`** を使用すると、ZIP アーカイブを簡単に作成することができます。
**`zipfile`** ライブラリを使用すると、1 ファイル単位でアーカイブに追加するファイルを制御することができます。


ディレクトリを ZIP 圧縮する
----

**`shutil.make_archive()`** を使用すると、指定したディレクトリ内のファイルを丸ごと ZIP 圧縮できます。
次の例では、`src` ディレクトリ内のファイルを `backup.zip` ファイルに圧縮しています。

#### zip.py

```python
import shutil
import sys

try:
    shutil.make_archive('backup', format='zip', root_dir='src')
except Exception as e:
    print(e, file=sys.stderr)
```


### （応用）ZIP ファイルの上書き確認をする

`shutil.make_archive()` は、すでに ZIP ファイルがある場合には、問答無用で上書き保存してしまうようです。
下記の `create_zip()` 関数は、同名の ZIP ファイルが存在している場合に確認プロンプトを表示するようにしています。

```python
import shutil
import sys
import os

def create_zip(zip_filename, src_dir):
    """
    src_dir ディレクトリ内のファイル群を、
    zip_filename で指定した名前の ZIP ファイルにアーカイブします。
    すでに同名の ZIP ファイルが存在する場合は、確認プロンプトを表示します。
    """
    if os.path.exists(zip_filename):
        answer = input(zip_filename + ' already exists. Overwrite? (y/n): ')
        if (answer != 'y' and answer != 'Y'):
            return
    try:
        name = os.path.splitext(zip_filename)[0]
        shutil.make_archive(name, format='zip', root_dir=src_dir)
    except Exception as e:
        print(e, file=sys.stderr)

if __name__ == '__main__':
    create_zip('backup.zip', 'src')
```


ZIP ファイルを展開する
----

**`shutil.unpack_archive()`** を使用すると、指定した ZIP ファイルを、指定したディレクトリに展開することができます。
下記の例では、`backup.zip` アーカイブ内のファイル群を `dst` ディレクトリ以下に展開しています。
`dst` ディレクトリが存在しない場合は、自動的に生成されます。

#### unzip.py

```python
import shutil
import sys

try:
    shutil.unpack_archive('backup.zip', extract_dir='dst')
except Exception as e:
    print(e, file=sys.stderr)
```

