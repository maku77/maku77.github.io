---
title: テーブルからランダムでレコードを取得する
created: 2014-01-04
---

SQLite の場合
----

~~~ sql
SELECT * FROM tbl ORDER BY RANDOM() LIMIT 1;
~~~


MySQL の場合
----

~~~ sql
SELECT * FROM tbl ORDER BY RAND() LIMIT 1;
~~~

