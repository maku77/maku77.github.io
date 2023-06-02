---
title: "Python で ZIP ファイルを作成・展開する (shutil.make_archive, shutil.unpack_archive)"
url: "p/euevcsa/"
date: "2019-10-22"
lastmod: "2023-05-30"
tags: ["Python", "ZIP"]
changes:
  - 2023-05-30: 説明を洗練
aliases: /python/io/create-zip.html
---

Python の標準ライブラリである __`shutil`__ を使用すると、複数のファイルを含むディレクトリから ZIP アーカイブを簡単に作成することができます（1 ファイル単位でアーカイブに追加するファイルを細かく制御したいときは、`zipfile` モジュールを使用します）。


ディレクトリを ZIP 圧縮する
----

### ZIP ファイルの作成

[shutil.make_archive 関数](https://docs.python.org/ja/3/library/shutil.html#shutil.make_archive) を使用すると、指定したディレクトリ内のファイルを丸ごと ZIP 圧縮できます。
次の例では、`src` ディレクトリ内のファイルを `backup.zip` ファイルに圧縮しています。

{{< code lang="python" title="zip.py（src ディレクトリから backup.zip を作成）" >}}
import shutil
import sys

try:
    shutil.make_archive('backup', format='zip', root_dir='src')
except Exception as e:
    print(e, file=sys.stderr)
{{< /code >}}


### ZIP ファイルの上書き確認をする

`shutil.make_archive` 関数は、すでに ZIP ファイルがある場合には、問答無用で上書き保存してしまうようです。
下記の `create_zip` 関数は、同名の ZIP ファイルが存在している場合に確認プロンプトを表示するようにしています。

{{< code lang="python" title="上書き確認付きの ZIP アーカイブ化関数" >}}
import shutil
import sys
import os

def create_zip(zip_filename, src_dir):
    """
    src_dir ディレクトリ内のファイル群を、
    zip_filename で指定した名前の ZIP ファイルにアーカイブします。
    すでに同名の ZIP ファイルが存在する場合は、確認プロンプトを表示します。
    """

    # 上書き確認
    if os.path.exists(zip_filename):
        answer = input(zip_filename + ' already exists. Overwrite? (y/n): ')
        if (answer != 'y' and answer != 'Y'):
            return

    # ZIP アーカイブの作成
    try:
        name = os.path.splitext(zip_filename)[0]
        shutil.make_archive(name, format='zip', root_dir=src_dir)
    except Exception as e:
        print(e, file=sys.stderr)

if __name__ == '__main__':
    create_zip('backup.zip', 'src')
{{< /code >}}


ZIP ファイルを展開する
----

[shutil.unpack_archive 関数](https://docs.python.org/ja/3/library/shutil.html#shutil.unpack_archive) を使用すると、指定した ZIP ファイルの中身を、指定したディレクトリに展開することができます。
下記の例では、`backup.zip` アーカイブ内のファイル群を `dst` ディレクトリ以下に展開しています。
`dst` ディレクトリが存在しない場合は、自動的に生成されます。

{{< code lang="python" title="unzip.py" >}}
import shutil
import sys

try:
    shutil.unpack_archive('backup.zip', extract_dir='dst')
except Exception as e:
    print(e, file=sys.stderr)
{{< /code >}}

