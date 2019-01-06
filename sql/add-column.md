---
title: "テーブルにカラムを追加する (ALTER TABLE ～ ADD COLUMN)"
date: "2013-01-06"
---

カラムを（末尾に）追加する
----

### MySQL、SQLite の場合


#### 例: 末尾にカラム new_col を追加する

~~~ sql
ALTER TABLE tbl_name ADD COLUMN new_col VARCHAR(10);
~~~


任意の位置にカラムを追加する
----

### MySQL の場合

MySQL の場合は、`FIRST` キーワードあるいは、`AFTER` キーワードを使用することで、任意の位置にカラムを追加することができます。

#### 例: 先頭にカラム new_col を追加する

~~~ sql
ALTER TABLE tbl_name ADD COLUMN new_col VARCHAR(10) FIRST;
~~~

#### 例: col1 の後ろにカラム new_col を追加する

~~~ sql
ALTER TABLE tbl_name ADD COLUMN new_col VARCHAR(10) AFTER col1;
~~~

### SQLite の場合（MySQL 以外）

MySQL 以外の場合は、新規カラムの追加位置を指定できません。
以下のようにテーブルを作り直して、レコードを全てコピーする必要があります。

#### 例: col1、col2 の間にカラム col_new を追加する

~~~ sql
BEGIN TRANSACTION;

-- 既存のテーブルをリネーム
ALTER TABLE tbl_name RENAME TO tbl_temp;
-- 新しいテーブルを作成（元のテーブル名と同じ名前で）
CREATE TABLE tbl_name(col1 TEXT, col_new TEXT DEFAULT '', col2 TEXT);
-- レコードを全て移す
INSERT INTO tbl_name(col1, col2) SELECT col1, col2 FROM tbl_temp;
-- 元のテーブルを削除
DROP TABLE tbl_temp;

COMMIT;
~~~

