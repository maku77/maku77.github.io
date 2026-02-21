---
title: "SQLメモ: MySQL で登録されているユーザの一覧を表示する"
url: "p/dgiscz7/"
date: "2012-07-14"
tags: ["sql"]
aliases: /sql/mysql/list-users.html
---

MySQL のユーザは、`mysql` データベースの `user` テーブルで管理されています。
以下のようにすると、登録されているユーザの一覧を確認することができます。

```console
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
```
