---
title: テーブル名を変更する (ALTER TABLE ～ RENAME TO)
created: 2013-01-06
---

データベースのテーブル名は、`ALTER TABLE` コマンドを使用して変更することができます。

SQLite、MySQL の場合
----

#### 例: テーブル tbl_old を tbl_new という名前に変更

~~~ sql
ALTER TABLE tbl_old RENAME TO tbl_new;
~~~

