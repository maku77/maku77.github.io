---
title: SQLite でテーブルの内容をダンプする (.dump)
created: 2010-10-25
---

SQLite の `.dump` コマンドを使うと、定義済みのテーブルの内容をダンプできます。
出力結果には、`CREATE TABLE` や `INSERT` コマンドなどが含まれます。

#### 全てのテーブルの内容をダンプする

~~~
sqlite> .dump
~~~

#### テーブル名を指定してダンプする（複数指定可能）

~~~
sqlite> .dump tbl_aaa
sqlite> .dump tbl_aaa tbl_bbb
~~~

#### tbl_ で始まる名前のテーブルの内容をダンプ

~~~
sqlite> .dump tbl_%
~~~

ダンプ結果をファイルに出力したい場合は、`.output` コマンドで出力先を `stdout` からファイルに切り替えてから `.dump` コマンドを実行します。

~~~
sqlite> .output sample.sql
sqlite> .dump
~~~

