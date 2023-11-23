---
title: "python コマンドでワンライナーを実行する (python -c)"
url: "p/oi67p5c/"
date: "2011-05-13"
lastmod: "2023-11-23"
tags: ["Python"]
changes:
  - 2023-11-23: ワンライナーの例を追加
aliases: /python/one-liner.html
---

`python` コマンドを実行するときに __`-c`__ オプションを指定すると、コマンドラインで指定した Python コードを直接実行することができます。
このように 1 行で記述したコードは、ワンライナーと呼ばれています。

{{< code lang="console" title="python のワンライナー実行の例" >}}
# Hello World
$ python -c "print('Hello World')"
Hello World

# 連番の表示
$ python -c "print([x for x in range(5)])"
[0, 1, 2, 3, 4]

# 現在時刻の表示
$ python -c "import datetime; print(datetime.datetime.now())"
2023-11-23 16:22:37.530499

# JSON ファイルの特定のキーの値を取得する
$ python -c "import json; print(json.load(open('file.json'))['key'])"
value

# CSV ファイルの特定の列を表示する
$ python -c "import csv; print([row['col1'] for row in csv.DictReader(open('file.csv'))])"
['100', '200', '300']
{{< /code >}}

