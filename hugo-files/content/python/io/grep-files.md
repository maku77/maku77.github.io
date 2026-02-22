---
title: "Pythonメモ: 応用サンプル: 複数ファイルをまとめて grep する (re.search, glob.iglob)"
url: "p/vcozyix/"
date: "2018-01-07"
tags: ["python"]
aliases: /python/io/grep-files.html
---

以下の Python スクリプトは、カレントディレクトリ以下の Markdown ファイル (.md) から、下記のようなタイトル行を抽出するサンプルです。

```
title: "記事タイトル"
```

{{< code lang="python" title="grep-files.py" >}}
#!/usr/bin/env python3
import glob
import re

GLOB = '**/*.md'
PATTERN = r'title:\s*(.+)'

def process_file(f):
    for line in f:
        match = re.search(PATTERN, line)
        if match:
            print('  ' + match.group(1))
            return

if __name__ == '__main__':
    for filename in glob.iglob(GLOB, recursive=True):
        print(filename)
        with open(filename, encoding='utf-8') as f:
            process_file(f)
{{< /code >}}

検索対象のファイル拡張子や、検索する文字列のパターンは定数になっているので、ある程度使い回しが効くようになっています。
スクリプトを実行すると、検索したファイル名と、見つかった記事タイトル（見つかった場合のみ）を順に出力します。

{{< code lang="console" title="実行結果" >}}
$ ./grep-files.py
python/dictionary/del.md
  dictionary の要素を削除する
python/dictionary/in.md
  dictionary に指定したキーが存在するか調べる
python/dictionary/sort.md
  dictionary の要素をソートして出力する
python/dictionary/len.md
  dictionary の要素数を取得する
...
{{< /code >}}
