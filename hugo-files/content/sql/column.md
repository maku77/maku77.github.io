---
title: "SQLメモ: SQL によるカラム操作の基本（カラム名の変更、カラムの追加・削除）"
url: "p/e577znu/"
date: "2013-01-06"
tags: ["sql"]
aliases:
  - /sql/rename-column.html
  - /sql/add-column.html
  - /sql/drop-column.html
---

テーブル内のカラム名を変更する (`ALTER TABLE` ～ `RENAME COLUMN`)
----

テーブル内の特定のカラムの名前を変更するには以下のようにします。

### SQLite の場合

SQLite の **`ALTER TABLE`** はカラム名の変更に対応していません。
SQLite でカラム名を変更するには、テーブルを作り直す必要があります。

{{< code lang="sql" title="例: カラム名を col2 から col2_new に変更する" >}}
BEGIN TRANSACTION;

-- 既存のテーブルをリネーム
ALTER TABLE tbl_name RENAME TO tbl_temp;
-- 新しいテーブルを作成（元のテーブル名と同じ名前で）
CREATE TABLE tbl_name(col1 TEXT, col2_new TEXT);
-- レコードをすべて移す
INSERT INTO tbl_name(col1, col2_new) SELECT col1, col2 FROM tbl_temp;
-- 元のテーブルを削除
DROP TABLE tbl_temp;

COMMIT;
{{< /code >}}

### Oracle の場合

Oracle データベースでは **`ALTER TABLE ~ RENAME COLUMN`** コマンドを使用してカラム名を変更することができます。

```sql
ALTER TABLE tbl_name RENAME COLUMN col_old TO col_new;
```


テーブルにカラムを追加する (`ALTER TABLE` ～ `ADD COLUMN`)
----

### カラムを（末尾に）追加する

{{< code lang="sql" title="例: 末尾にカラム new_col を追加する（MySQL、SQLite の場合）" >}}
ALTER TABLE tbl_name ADD COLUMN new_col VARCHAR(10);
{{< /code >}}


### 任意の位置にカラムを追加する

#### MySQL の場合

MySQL の場合は、`FIRST` キーワードあるいは `AFTER` キーワードを使用することで、任意の位置にカラムを追加することができます。

{{< code lang="sql" title="例: 先頭にカラム new_col を追加する" >}}
ALTER TABLE tbl_name ADD COLUMN new_col VARCHAR(10) FIRST;
{{< /code >}}

{{< code lang="sql" title="例: col1 の後ろにカラム new_col を追加する" >}}
ALTER TABLE tbl_name ADD COLUMN new_col VARCHAR(10) AFTER col1;
{{< /code >}}

#### SQLite の場合（MySQL 以外）

SQLite では新規カラムの追加位置を指定できません。
以下のようにテーブルを作り直して、レコードをすべてコピーする必要があります。

{{< code lang="sql" title="例: col1、col2 の間にカラム col_new を追加する" >}}
BEGIN TRANSACTION;

-- 既存のテーブルをリネーム
ALTER TABLE tbl_name RENAME TO tbl_temp;
-- 新しいテーブルを作成（元のテーブル名と同じ名前で）
CREATE TABLE tbl_name(col1 TEXT, col_new TEXT DEFAULT '', col2 TEXT);
-- レコードをすべて移す
INSERT INTO tbl_name(col1, col2) SELECT col1, col2 FROM tbl_temp;
-- 元のテーブルを削除
DROP TABLE tbl_temp;

COMMIT;
{{< /code >}}


テーブルからカラムを削除する (`ALTER TABLE` ～ `DROP COLUMN`)
----

### SQLite の場合

SQLite の `ALTER TABLE` は、カラムの削除に対応していません。
SQLite でカラムを削除したい場合は、テーブルを作り直す必要があります。

{{< code lang="sql" title="例: カラム col2 を削除する" >}}
BEGIN TRANSACTION;

-- 既存のテーブルをリネーム
ALTER TABLE tbl_name RENAME TO tbl_temp;
-- 新しいテーブルを作成（元のテーブル名と同じ名前で）
CREATE TABLE tbl_name(col1 TEXT, col3 TEXT);
-- レコードをすべて移す
INSERT INTO tbl_name(col1, col3) SELECT col1, col3 FROM tbl_temp;
-- 元のテーブルを削除
DROP TABLE tbl_temp;

COMMIT;
{{< /code >}}

### MySQL の場合

MySQL では、`ALTER TABLE` コマンドを使用して特定のカラムを削除することができます。

{{< code lang="sql" title="例: カラム col_name を削除する" >}}
ALTER TABLE tbl_name DROP COLUMN col_name;
{{< /code >}}

