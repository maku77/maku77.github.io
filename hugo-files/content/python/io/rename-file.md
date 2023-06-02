---
title: "Python でファイルやディレクトリの名前を変更する (os.rename, os.renames)"
url: "p/9aqzppe/"
date: "2007-11-09"
lastmod: "2023-06-02"
tags: ["Python"]
changes:
  - 2023-06-02: エラー処理を追加
aliases: /python/change-filename.html
---

os.rename / os.renames 関数
----

[os.rename 関数](https://docs.python.org/ja/3/library/os.html?highlight=os%20rename#os.rename) を使用して、ファイルやディレクトリの名前を変更することができます。
次の例では、`src.txt` という名前のファイルを `dst.txt` という名前にリネームしています。

```python
import os

try:
    os.rename('src.txt', 'dst.txt')
except Exception as e:
    print('Error: {0}'.format(e), file=sys.stderr)
```

`os.rename` 関数は次のようなエラーを発生させる可能性があります。
通常の使用時にも発生し得るものなので、エラーハンドル処理はちゃんと記述しておくことをお勧めします。

- __`FileExistsError`__ ... Windows で `dst` がすでに存在する場合。
- __`IsADirectoryError`__ ... Unix で、`src` がファイルで `dst` が（存在する）ディレクトリの場合。
- __`NotADirectoryError`__ ... Unix で、`src` がディレクトリで `dst` が（存在する）ファイルの場合。
- __`OSError`__ ... Unix で、`src` がディレクトリで `dst` が（存在する）ディレクトリで、かつ、`dst` が空でない場合。`dst` が空のディレクトリであれば、`dst` へのリネームは成功します（`dst` に `src` の中身が移動した状態になる）。

深いディレクトリ階層にファイルを移動させたいときは、[os.renames 関数](https://docs.python.org/ja/3/library/os.html?highlight=os%20rename#os.renames) を使うのが便利です。
`os.renames` 関数は、移動先のディレクトリが存在しない場合に自動的に作成してくれます。

```python
os.renames("src.txt", "aaa/bbb/ccc/dst.txt")
```

上記のようにすると、`aaa/bbb/ccc` というディレクトリ階層を作ってからファイルを移動してくれます。
`os.renames` ではなく、`os.rename` を使用した場合は、`aaa/bbb/ccc` というディレクトリが存在しないときにエラーになります。


例: ファイル名のプレフィックスに日付 ('-YYYYMMDD') を付加する
----

下記のサンプルスクリプトは、ディレクトリ内のすべての `.png` ファイルのファイル名を変更し、拡張子の前に `-20071109` のような日付を表す文字列を挿入します。
例えば、`sample.png` というファイル名は `sample-20071109.png` というファイル名に置換されます。

{{< code lang="python" title="rename.py" >}}
import glob
import os
import re
import time

time_str = time.strftime('%Y%m%d')

for old_name in glob.iglob('*.png'):
    new_name = re.sub(r'(\.png)$', '-' + time_str + r'\1', old_name)
    os.rename(old_name, new_name)
{{< /code >}}

{{< code lang="console" title="実行例（ディレクトリ内に a.png, b.png, c.png がある場合）" >}}
$ ls
a.png
b.png
c.png

$ python rename.py
$ ls
a-20071109.png
b-20071109.png
c-20071109.png
{{< /code >}}

