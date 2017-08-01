---
title: MySQL でデータベースをバックアップ／リストアする (mysqldump)
created: 2005-04-03
---

MySQL データベースをバックアップする
----

MySQL データベースの内容をファイルにバックアップするには、MySQL に付属している `mysqldump` コマンドを使用します。

#### 例: mydb データベース内のテーブルをバックアップ

~~~
$ mysqldump -u myuser -p mydb > mydb_backup.sql
Enter password: ********
~~~


MySQL データベースをリストアする
----

`mysqldump` で作成したファイルがあれば、データベースをリストアすることができます。
データベースの作成 (`CREATE DATABASE`) は自動では行われないので、リストア先のデータベースを手動で作成してからリストアを実行する必要があります。

#### 例: mydb データベース内のテーブルをリストア

~~~
$ mysqladmin create mydb
$ mysql -u myuser -p mydb < mydb_backup.sql
Enter password: ********
~~~

データベースの作成は以下のようにして行う方法もあります。

~~~
C:\> mysql -u root -p
mysql> CREATE DATABASE newdb
~~~


応用: バックアップスクリプトを作る
----

Linux や MacOSX で MySQL を使っている場合は、以下のような DB バックアップ用のスクリプトを作っておくと便利です。

#### mydb_backup.sh

~~~ shell
#!/bin/bash
mysqldump -u myuser -pmypass mydb > mydb-`date +%Y%m%d`.sql
~~~

上記を実行すれば、`mydb-20120919.sql` のような日付入りのバックアップファイルが作成されます。

