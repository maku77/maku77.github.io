---
title: "CSV ファイルや TSV ファイルを読み込む (csv.reader)"
update: "2019-04-09"
date: "2013-05-09"
---

CSV ファイルを読み込む
----

### csv.reader モジュールを使用する方法

Python には、CSV を処理するためのモジュール **`csv.reader`** が標準で搭載されています。
下記の例では、このモジュールを使用して、CSV ファイル `input.csv` を読み込んでいます。
改行の処理は `csv` モジュールが担うため、`open` 関数のオプションで常に `newline=''` を指定しておく必要があります。

~~~ python
#!/usr/bin/env python
import csv

with open('input.csv', encoding='utf-8', newline='') as f:
    for cols in csv.reader(f):
        print(cols)
~~~

#### 入力ファイル (input.csv)

~~~ csv
AAA, 100
BBB, 200
CCC, 300
~~~

#### 実行結果

~~~
['AAA', ' 100']
['BBB', ' 200']
['CCC', ' 300']
~~~

カンマの前後の空白はキープされます。


### str.split を使って自力で処理する方法

もちろん、以下のように普通のテキストファイルとして処理する方法もあります。

~~~ python
with open('input.csv', encoding='utf-8') as f:
    for line in f:
        cols = [x.strip() for x in line.split(',')]  # カンマで分割＆前後の空白削除
        print(cols)
~~~


TSV ファイルを読み込む
----

CSV ファイルの代わりに TSV ファイル（タブで区切られたテキスト）を読み込む場合は、下記のように `csv.reader` のオプションで **`delimiter='\t'`** を指定します。

~~~ python
import csv

with open('input.tsv', encoding='utf-8', newline='') as f:
    for cols in csv.reader(f, delimiter='\t'):
        print(cols)
~~~


ヘッダを飛ばして CSV/TSV ファイルを読み込む
----

一行目がヘッダになっている CSV や TSV ファイルを読み込むには、下記のように **`next`** を使って一行目だけを別に処理します。
`csv.reader` のオプションではヘッダを読み飛ばす指定はできないようです。

~~~ python
import csv

with open('input.csv', encoding='utf-8', newline='') as f:
    reader = csv.reader(f)

    # ヘッダ行だけを読み込んで、スペース区切りで表示
    header = next(reader)
    print(' '.join(header))

    # 残りの行を一行ずつ読み込み
    for cols in reader:
        print(cols)
~~~


参考
----

- [テキストファイルを読み込む](read-text-file.html)
- [文字列をデリミタで分割する (split)](../numstr/split-string.html)
- [csv --- CSV ファイルの読み書き — Python 3 ドキュメント](https://docs.python.org/ja/3/library/csv.html)
