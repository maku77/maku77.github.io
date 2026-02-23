---
title: "Pythonメモ: 現在時刻から YYYY-MM-DD のような日付文字列を生成する (strftime)"
url: "p/sh36h2z/"
date: "2019-10-22"
tags: ["python"]
aliases: ["/python/datetime/date-string.html"]
---

strftime 関数
----

下記の `make_date_str()` は、現在の日時をもとに `2019-10-22 21:15:30` という形式の文字列を生成します。

{{< code lang="python" title="sample.py" >}}
import datetime

def make_date_str():
    now = datetime.datetime.now()
    return now.strftime('%Y-%m-%d %H:%M:%S')

if __name__ == '__main__':
    print(make_date_str())
{{< /code >}}

{{< code title="実行例" >}}
$ python sample.py
2019-10-22 21:15:30
{{< /code >}}


使用例: バックアップファイル名を自動生成する
----

バックアップファイルを作成するときに、ファイル名を日時から自動生成する場合などに便利です。

```python
import datetime

def make_zip_filename(prefix):
    """
    現在時刻をもとに ZIP ファイル名を生成します。
    """
    now = datetime.datetime.now()
    return now.strftime(prefix + '-%Y%m%d%H%M%S.zip')

if __name__ == '__main__':
    print(make_zip_filename('backup'))  #=> 'backup-20191022211530.zip'
```

