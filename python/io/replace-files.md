---
title: "応用サンプル: 複数ファイルの文字列をまとめて置換する"
date: "2018-01-07"
description: "複数のテキストファイル内の文字列を一気に置換してしまう Python スクリプトを紹介します。"
---

ここでは、カレントディレクトリ以下の Markdown ファイル (.md) のヘッダ部分の情報を書き換えてみます。
置換対象のテキストは、下記のような日付情報を表すテキストです。

- （置換前のテキスト）`created: "2018-01-01"`
- （置換後のテキスト）`date: "2018-01-01"`

#### replace-files.py

~~~ python
#!/usr/bin/env python3
import glob
import re

GLOB = '**/*.md'
REPLACE_FROM = re.compile(r'created: (\d{4}-\d{2}-\d{2})')
REPLACE_TO = r'date: "\1"'

def write_lines(filename, lines):
    with open(filename, 'w', encoding='utf-8') as f:
        f.write(lines)

def read_lines(filename):
    with open(filename, encoding='utf-8') as f:
        return f.read()

def process_file(filename):
    src_lines = read_lines(filename)
    dst_lines = REPLACE_FROM.sub(REPLACE_TO, src_lines)
    if src_lines == dst_lines:
        print(filename)
    else:
        write_lines(filename, dst_lines)
        print(filename + ': CHANGED')

if __name__ == '__main__':
    for filename in glob.iglob(GLOB, recursive=True):
        process_file(filename)
~~~

このスクリプトを実行すると、既存のファイル自体を書き換えることに注意してください（実行する前に、必ずバックアップを取ってください）。
置換パターンに一致する行が見つからなかった場合は、そのファイルに対する書き換え処理は行われません（同じ内容を書き戻すような無駄な処理はしません）。

#### 実行例

~~~
$ ./replace-files.py
python/io/basename.md: CHANGED
python/io/yaml.md: CHANGED
python/io/user-input.md: CHANGED
python/io/script-path.md: CHANGED
...
~~~

<div class="note">
上記のサンプルコードでは、パターンに一致する部分のうち、最初に見つかった部分だけを置換します。
パターンに一致する全ての文字列を置換したい場合は、<code>sub</code> メソッドの代わりに <code>gsub</code> メソッドを使用してください。
</div>

