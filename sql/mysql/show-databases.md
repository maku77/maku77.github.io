---
title: MySQL データベースの一覧を表示する (SHOW DATABASES)
created: 2012-07-14
---

MySQL の `SHOW DATABASES` コマンドを実行することで、使用可能なデータベースの一覧を確認することができます。

~~~
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
~~~

