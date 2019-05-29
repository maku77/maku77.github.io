---
title: "Python で HTTP を扱う方法いろいろ"
date: "2015-05-17"
---

Python3 の標準 HTTP ライブラリ
====
Python3 には HTTP を扱う、下記のような標準ライブラリが用意されています。

* [http.client](https://docs.python.org/3/library/http.client.html)
  - HTTP (RFC 2616) を実装する**低レベルライブラリ**。

* [urllib.request](https://docs.python.org/3/library/urllib.request.html)
  - 上記を利用した**高レベルライブラリ**。認証やリダイレクトをサポート。


オープンソースの HTTP ライブラリ
====

Python に標準で付属している `urllib.request` パッケージは、比較的低レベルな処理を行うように設計されているため、手っ取り早く HTTP リクエストでデータを取得したい場合は、オープンソースの **requests パッケージ** を使うとよいでしょう。
requests パッケージは、Python の公式サイトでも推奨されています。

* [requests パッケージ](http://requests.readthedocs.org/)

