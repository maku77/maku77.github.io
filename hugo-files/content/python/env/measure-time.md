---
title: "Python コードの実行時間を計測する (datetime.now)"
url: "p/ubtbs9p/"
date: "2014-05-01"
tags: ["Python"]
aliases: /python/misc/measure-time.html
---

下記は `datetime` モジュールを使って、Python コードの一部の実行時間を計測する例です。

{{< code lang="python" title="main.py" >}}
from datetime import datetime
start = datetime.now()

# ここに実行時間を計測したい処理を記述する

print(datetime.now() - start)
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ python3 main.py
00:00:01.547393
{{< /code >}}

