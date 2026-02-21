---
title: "SQLメモ: SQLite の対話型プロンプトでテーブルのカラム名を表示する (.header)"
url: "p/kt4fedc/"
date: "2010-09-05"
tags: ["sql"]
aliases: /sql/sqlite/show-header.html
---

SQLite の対話型プロンプトで **`.header ON`** と入力すると、`SELECT` クエリの実行結果にヘッダ（カラム名）が表示されるようになります。

```sqlite
sqlite> .header ON
sqlite> .mode column
sqlite> SELECT id, title FROM tbl;
id          title
----------  ----------
1           title 1
2           title 2
3           title 3
```

さらに、以下のように `SELECT` クエリを実行すると、任意のカラム名に置き換えて表示することができます。
ここでは、`id` カラムを「識別子」、`title` カラムを「タイトル」というラベルで表示しています。

```sqlite
sqlite> SELECT id "識別子", title "タイトル" FROM tbl;
識別子      タイトル
----------  ----------
1           title 1
2           title 2
3           title 3
```
