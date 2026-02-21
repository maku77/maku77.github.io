---
title: "SQLメモ: MySQL でデータベースの一覧を表示する (SHOW DATABASES)"
url: "p/e377edc/"
date: "2012-07-14"
tags: ["sql"]
aliases: /sql/mysql/show-databases.html
---

MySQL の **`SHOW DATABASES`** コマンドを実行することで、使用可能なデータベースの一覧を確認することができます。

```console
$ mysql -u root
mysql> SHOW DATABASES;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| cdcol              |
| mysql              |
| sample_db          |
| test               |
+--------------------+
5 rows in set (0.00 sec)
```
