---
title: "SQLメモ: SQLite でテーブルの内容をダンプする (.dump)"
url: "p/k4qjm3p/"
date: "2010-10-25"
tags: ["sql"]
aliases: /sql/sqlite/dump-table.html
---

SQLite の **`.dump`** コマンドを使うと、定義済みのテーブルの内容をダンプできます。
出力結果には、`CREATE TABLE` や `INSERT` コマンドなどが含まれます。

{{< code lang="sqlite" title="すべてのテーブルの内容をダンプする" >}}
sqlite> .dump
{{< /code >}}

{{< code lang="sqlite" title="テーブル名を指定してダンプする（複数指定可能）" >}}
sqlite> .dump tbl_aaa
sqlite> .dump tbl_aaa tbl_bbb
{{< /code >}}

{{< code lang="sqlite" title="tbl_ で始まる名前のテーブルの内容をダンプ" >}}
sqlite> .dump tbl_%
{{< /code >}}

ダンプ結果をファイルに出力したい場合は、**`.output`** コマンドで出力先を `stdout` からファイルに切り替えてから `.dump` コマンドを実行します。

```sqlite
sqlite> .output sample.sql
sqlite> .dump
```
