---
title: MySQL で登録されているユーザの一覧を表示する
date: "2012-07-14"
---

MySQL のユーザは、`mysql` データベースの `user` テーブルで管理されています。
下記のようにすると、登録されているユーザの一覧を確認することができます。

~~~
$ mysql -u root
mysql> SELECT user,host FROM mysql.user;
+------+-----------+
| user | host      |
+------+-----------+
| root | 127.0.0.1 |
|      | devmac    |
| root | devmac    |
|      | localhost |
| maku | localhost |
| root | localhost |
+------+-----------+
6 rows in set (0.00 sec)
~~~

