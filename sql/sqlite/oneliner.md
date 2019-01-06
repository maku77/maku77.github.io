---
title: "SQLite でワンライナー実行する"
date: "2011-08-17"
---

`sqlite3` コマンドを実行するときに、データベースファイル名に続けて、実行したい SQLite コマンドを指定することができます。

#### 例: データベース内のテーブル一覧を表示する

~~~
$ sqlite3 settings.db '.tables'
android_metadata   bookmarks    system
bluetooth_devices  secure
~~~

SQL 文もワンライナーで実行できます。

#### 例: データベース内の secure テーブルの情報を表示する

~~~
$ sqlite3 settings.db 'SELECT * FROM secure'
...
~~~

