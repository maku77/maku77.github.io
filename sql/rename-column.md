---
title: テーブル内のカラム名を変更する (ALTER TABLE ～ RENAME COLUMN)
date: "2013-01-06"
---

テーブル内の特定のカラムの名前を変更するには以下のようにします。

SQLite の場合
----

SQLite の `ALTER TABLE` はカラム名の変更に対応していません。
SQLite でカラム名を変更するには、テーブルを作り直す必要があります。

#### 例: カラム名を col2 から col2_new に変更する

~~~ sql
BEGIN TRANSACTION;

-- 既存のテーブルをリネーム
ALTER TABLE tbl_name RENAME TO tbl_temp;
-- 新しいテーブルを作成（元々のテーブル名と同じ名前で）
CREATE TABLE tbl_name(col1 TEXT, col2_new TEXT);
-- レコードを全て移す
INSERT INTO tbl_name(col1, col2_new) SELECT col1, col2 FROM tbl_temp;
-- 元のテーブルを削除
DROP TABLE tbl_temp;

COMMIT;
~~~


Oracle の場合
----

Oracle データベースでは `ALTER TABLE` コマンドを使用してカラム名を変更することができます。

~~~ sql
ALTER TABLE tbl_name RENAME COLUMN col_old TO col_new;
~~~

