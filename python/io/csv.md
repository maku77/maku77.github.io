---
title: "CSV ファイルを読み込む (csv.reader)"
date: "2013-05-09"
---

csv.reader モジュールを使用する方法
----

Python には、CSV を処理するためのモジュール **`csv.reader`** が標準で搭載されています。
下記の例では、このモジュールを使用して、CSV ファイル `input.csv` を読み込んでいます。

~~~ python
#!/usr/bin/env python
import csv

with open('input.csv') as f:
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


str.split を使って自力で処理する方法
----

もちろん、以下のように普通のテキストファイルとして処理する方法もあります。

~~~ python
with open('input.csv') as f:
    for line in f:
        cols = [x.strip() for x in line.split(',')]  # カンマで分割＆前後の空白削除
        print(cols)
~~~


参考
----

- [テキストファイルを読み込む](read-text-file.html)
- [文字列をデリミタで分割する (split)](../numstr/split-string.html)

