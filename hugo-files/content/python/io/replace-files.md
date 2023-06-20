---
title: "Python サンプル: 複数ファイルの文字列をまとめて置換する (glob, re)"
url: "p/xwog7ip/"
date: "2018-01-07"
tags: ["Python"]
description: "複数のテキストファイル内の文字列を一気に置換してしまう Python スクリプトを紹介します。"
aliases: /python/io/replace-files.html
---

replace_files.py
----

複数のテキストファイル内の文字列を一気に置換してしまう Python スクリプトの実装例を紹介します。
ここでは、カレントディレクトリ以下の Markdown ファイル (`.md`) 内の時刻情報を書き換えてみます。
置換対象のテキストは、下記のような日付情報を表すテキストです（`created:` の部分を `date:` に置き換えます）。

- （置換前のテキスト）`created: "2018-01-01"`
- （置換後のテキスト）`date: "2018-01-01"`

{{< code lang="python" title="replace_files.py" >}}
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
{{< /code >}}

このスクリプトを実行すると、既存のファイル自体を書き換えることに注意してください（実行する前に、必ずバックアップを取ってください）。
置換パターンに一致する行が見つからなかった場合は、そのファイルに対する書き換え処理は行われません（同じ内容を書き戻すような無駄な処理はしません）。

{{< code lang="console" title="実行例" >}}
$ python replace_files.py
python/io/basename.md: CHANGED
python/io/yaml.md: CHANGED
python/io/user-input.md: CHANGED
python/io/script-path.md: CHANGED
...
{{< /code >}}

{{% note %}}
上記のサンプルコードでは、パターンに一致する部分のうち、最初に見つかった部分だけを置換します。
パターンに一致する全ての文字列を置換したい場合は、`sub` メソッドの代わりに `gsub` メソッドを使用してください。
{{% /note %}}


参考
----

- [Python で文字列を置換する (str.replace, re.sub, re.subn)](/p/wnpquuy/)

