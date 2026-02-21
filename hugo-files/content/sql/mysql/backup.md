---
title: "SQLメモ: MySQL でデータベースをバックアップ／リストアする (mysqldump)"
url: "p/ws9oyhc/"
date: "2005-04-03"
tags: ["sql"]
aliases: /sql/mysql/backup.html
---

MySQL データベースをバックアップする
----

MySQL データベースの内容をファイルにバックアップするには、MySQL に付属している **`mysqldump`** コマンドを使用します。

{{< code lang="console" title="例: mydb データベース内のテーブルをバックアップ" >}}
$ mysqldump -u myuser -p mydb > mydb_backup.sql
Enter password: ********
{{< /code >}}


MySQL データベースをリストアする
----

`mysqldump` で作成したファイルがあれば、データベースをリストアすることができます。
データベースの作成（`CREATE DATABASE`）は自動では行われないので、リストア先のデータベースを手動で作成してからリストアを実行する必要があります。

{{< code lang="console" title="例: mydb データベース内のテーブルをリストア" >}}
$ mysqladmin create mydb
$ mysql -u myuser -p mydb < mydb_backup.sql
Enter password: ********
{{< /code >}}

データベースの作成は以下のようにして行う方法もあります。

```console
C:\> mysql -u root -p
mysql> CREATE DATABASE newdb;
```


応用: バックアップスクリプトを作る
----

Linux や macOS で MySQL を使っている場合は、以下のような DB バックアップ用のスクリプトを作っておくと便利です。

{{< code lang="sh" title="mydb_backup.sh" >}}
#!/bin/bash
mysqldump -u myuser -pmypass mydb > mydb-`date +%Y%m%d`.sql
{{< /code >}}

上記を実行すれば、`mydb-20120919.sql` のような日付入りのバックアップファイルが作成されます。
