---
title: Python で HTTP を扱う方法いろいろ
created: 2015-05-17
---

Python 3 で HTTP リクエストを扱うには以下のようなパッケージを使用する方法があります。

* [urllib.request](http://docs.python.jp/3/library/urllib.request.html) -- Python 3 の標準パッケージ
* [requests](http://requests.readthedocs.org/) -- オープンソースのパッケージ

urllib.request パッケージは、比較的低レベルな処理を行うように設計されているため、手っ取り早く HTTP リクエストでデータを取得したい場合は、requests パッケージを使うとよいでしょう。
requests パッケージは、Python の公式サイトでも推奨されています。

