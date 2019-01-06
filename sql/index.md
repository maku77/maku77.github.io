---
title: "SQL"
layout: category-index
---

SQL 全般
----

### テーブル
* [テーブルを作成する (CREATE TABLE)](create-table.html)
* [テーブルを削除する (DROP TABLE)](drop-table.html)
* [テーブル名を変更する (ALTER TABLE ～ RENAME TO)](rename-table.html)
* [テーブルの構成を確認する (DESCRIBE)](describe-table.html)
* [テーブルを作成したときのコマンドを表示する (SHOW CREATE TABLE)](show-create-table.html)

### カラム
* [テーブル内のカラム名を変更する (ALTER TABLE ～ RENAME COLUMN)](rename-column.html)
* [テーブルにカラムを追加する (ALTER TABLE ～ ADD COLUMN)](add-column.html)
* [テーブルからカラムを削除する (ALTER TABLE ～ DROP COLUMN)](drop-column.html)

### レコードの取得 (SELECT)
* [テーブルからランダムでレコードを取得する](random-record.html)

### その他
* [リレーショナルデータベース (RDB) でツリー構造や階層構造を表現する](hierarchy.html)

SQLite
----
* [SQL ファイルを読み込んで実行する](load-file.html)
* [SQLite のロックによる SQLITE_BUSY に備える](sqlite/sqlite-busy.html)
* [SQLite で瞬断などによるロールバック処理に備える (journaling)](sqlite/journaling.html)
* [(FAQ) SQLite で書き込みエラーが出た場合の対処](sqlite/write-error.html)
* [SQLite でテーブルの内容をダンプする (.dump)](sqlite/dump-table.html)
* [SQLite の対話型プロンプトでテーブルのカラム名を表示する (.header)](sqlite/show-header.html)
* [SQLite でワンライナー実行する](sqlite/oneliner.html)

MySQL
----
* [MySQL でデータベースの一覧を表示する (SHOW DATABASES)](mysql/show-databases.html)
* [MySQL でデータベースのユーザを作成／削除する (CREATE USER, DROP USER)](mysql/create-user.html)
* [MySQL で登録されているユーザの一覧を表示する](mysql/list-users.html)
* [MySQL で新しいデータベースを作成する (CREATE DATABASE)](mysql/create-database.html)
* [MySQL でデータベースをバックアップ／リストアする (mysqldump)](mysql/backup.html)
* [MySQL の TIMESTAMP NOT NULL な時刻データの振る舞い](mysql/timestamp-not-null.html)
* [MySQL で各種 SQL コマンドのヘルプを表示する](mysql/help.html)

