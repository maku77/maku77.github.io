---
title: "テーブルからカラムを削除する (ALTER TABLE ～ DROP COLUMN)"
date: "2013-01-06"
---

SQLite の場合
----

SQLite の `ALTER TABLE` は、カラムの削除に対応していません。
SQLite でカラムを削除したい場合は、テーブルを作り直す必要があります。

#### 例: カラム col2 を削除する

~~~ sql
BEGIN TRANSACTION;

-- 既存のテーブルをリネーム
ALTER TABLE tbl_name RENAME TO tbl_temp;
-- 新しいテーブルを作成（元々のテーブル名と同じ名前で）
CREATE TABLE tbl_name(col1 TEXT, col3 TEXT);
-- レコードを全て移す
INSERT INTO tbl_name(col1, col3) SELECT col1, col3 FROM tbl_temp;
-- 元のテーブルを削除
DROP TABLE tbl_temp;

COMMIT;
~~~


MySQL の場合
----

MySQL では、`ALTER TABLE` コマンドを使用して特定のカラムを削除することができます。

#### 例: カラム col_name を削除する

~~~ sql
ALTER TABLE tbl_name DROP COLUMN col_name;
~~~

