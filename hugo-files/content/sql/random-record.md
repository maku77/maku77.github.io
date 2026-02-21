---
title: "SQLメモ: テーブルからランダムでレコードを取得する"
url: "p/atujwft/"
date: "2014-01-04"
tags: ["sql"]
aliases: /sql/random-record.html
---

SQLite の場合
----

```sql
SELECT * FROM tbl ORDER BY RANDOM() LIMIT 1;
```


MySQL の場合
----

```sql
SELECT * FROM tbl ORDER BY RAND() LIMIT 1;
```
